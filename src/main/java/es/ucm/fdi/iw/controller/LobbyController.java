package es.ucm.fdi.iw.controller;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller()
@RequestMapping("lobby")
public class LobbyController {

    private static final Logger log = LogManager.getLogger(LobbyController.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

    // Crear un game con configuración predeterminada
	@PostMapping("/new")
	@Transactional
	public String postGame(
			HttpServletResponse response,
			@ModelAttribute Game edited,
			Model model, HttpSession session) throws IOException {

        Game game = new Game();

        User ucreator = (User)session.getAttribute("u");
		ucreator.addOneGame();

		Player creator = new Player();
		game.initGame(ucreator, creator);
		creator.initPlayer(game, ucreator, 0);

		entityManager.persist(creator);
		entityManager.persist(game);
        entityManager.flush();

        long id = game.getId();

		return "redirect:/lobby/"+id;
	}
	
	@PostMapping("/join/{id}") // Game id
	@Transactional
	@ResponseBody
	public String joinGame(@PathVariable long id, Model model, @RequestBody JsonNode o, HttpSession session) {
		Game game = entityManager.find(Game.class, id);

		if (game.playerExist((User) session.getAttribute("u")))
			return "{\"result\": \"OK\"}";
		
		User user = (User)session.getAttribute("u");

		User newUser = entityManager.find(User.class, user.getId());
		newUser.addOneGame();

		Player newPlayer = new Player();
		newPlayer.initPlayer(game, newUser, 0);

		game.addPlayer(newPlayer);

		entityManager.persist(newPlayer);
        entityManager.flush();
        
		messagingTemplate.convertAndSend("/topic/" + game.getTopicCode(), 
				"{\"type\": \"JOIN\"}");
		return "{\"result\": \"OK\"}";
	}

	@GetMapping("/j")
	public String jGame(Model model, HttpSession session) {
		return "join";
	}

	@GetMapping("/{id}")
	public String viewGame(@PathVariable long id, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		model.addAttribute("game", game);
		return "lobby";
	}

	/**
     * Posts a message to a game.
     * @param id of target game
     * @param o JSON-ized message, similar to {"message": "text goes here"}
     * @throws JsonProcessingException
     */
    @PostMapping("/{id}/msg")
	@ResponseBody
	@Transactional
	public String postMsg(@PathVariable long id, 
			@RequestBody JsonNode o, Model model, HttpSession session) 
		throws JsonProcessingException {
		
		String text = o.get("message").asText();
		Game g = entityManager.find(Game.class, id);
		User sender = entityManager.find(
				User.class, ((User)session.getAttribute("u")).getId());
		
		// construye mensaje, lo guarda en BD
		Message m = new Message();
		m.setRecipient(g);
		m.setSender(sender);
		m.setDateSent(LocalDateTime.now());
		m.setText(text);
		entityManager.persist(m);
		entityManager.flush(); // to get Id before commit
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(m.toTransfer());

		log.info("Sending a message to {} with contents '{}'", id, json);

		messagingTemplate.convertAndSend("/topic/"+g.getTopicCode(), json);
		return "{\"result\": \"message sent.\"}";
	}
	
	
	@PostMapping("/unp/{id}")
	@Transactional
	@ResponseBody
	public String updateNumPlayers(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		int n = o.get("numPlayers").asInt();
		game.setNumPlayers(n);
		model.addAttribute("game", game);
		messagingTemplate.convertAndSend("/topic/" + game.getTopicCode(), 
				"{\"type\": \"NUMPLAYERS\"}");
		return "{\"result\": \"OK\"}";
	}
	
	@PostMapping("/uet/{id}")
	@Transactional
	@ResponseBody
	public String updateExplodingTime(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		int n = o.get("explodingTime").asInt();
		game.setExplodingTime(n);
		model.addAttribute("game", game);
		messagingTemplate.convertAndSend("/topic/" + game.getTopicCode(), 
				"{\"type\": \"EXPLODINGTIME\"}");
		return "{\"result\": \"OK\"}";
	}

	@PostMapping("/uil/{id}")
	@Transactional
	@ResponseBody
	public String updateIfxLength(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		int n = o.get("ifxLength").asInt();
		game.setIfxLength(n);
		game.setInterfix(game.rndIfx());
		model.addAttribute("game", game);
		messagingTemplate.convertAndSend("/topic/" + game.getTopicCode(), 
				"{\"type\": \"IFXLENGTH\"}");
		return "{\"result\": \"OK\"}";
	}

	@PostMapping("/up/{id}")
	@Transactional
	@ResponseBody
	public String updatePrivacy(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		boolean priv = o.get("priv").asBoolean();
		game.setPriv(priv);
		model.addAttribute("game", game);
		messagingTemplate.convertAndSend("/topic/" + game.getTopicCode(), 
				"{\"type\": \"PRIVACY\"}");
		return "{\"result\": \"OK\"}";
	}

	@PostMapping("/ua/{id}")
	@Transactional
	@ResponseBody
	public String updateAlphabet(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		String letter = o.get("letter").asText();
		game.updateAlphabet(letter);
		model.addAttribute("game", game);
		messagingTemplate.convertAndSend("/topic/" + game.getTopicCode(), 
				"{\"type\": \"ALPHABET\"}");
		return "{\"result\": \"OK\"}";
	}

	@PostMapping("/report")
	@Transactional
	@ResponseBody
	public String report(@RequestBody JsonNode o, Model model, HttpSession session) {
		long msgId = o.get("msg").asLong();
		Message msg = entityManager.find(Message.class, msgId);

		// The message has been reported
		msg.setReported(true);

		// The user has been reported
		msg.getSender().setReported(true);
		
		return "{\"result\": \"OK\"}";
	}
}
