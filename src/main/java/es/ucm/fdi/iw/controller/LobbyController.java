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

    // Crear un game con configuración predeterminada
	@PostMapping("/new")
	@Transactional
	public String postGame(
			HttpServletResponse response,
			@ModelAttribute Game edited,
			Model model, HttpSession session) throws IOException {

        Game game = new Game();

        User ucreator = (User)session.getAttribute("u");

		Player creator = new Player();
		creator.initPlayer(game, ucreator, 0);
        game.initGame(ucreator, creator);

		entityManager.persist(creator);
		entityManager.persist(game);
        entityManager.flush();

        long id = game.getId();

		return "redirect:/lobby/"+id;
	}
	
	@PostMapping("/join/{id}") // Game id
	@Transactional
	public String joinGame(@PathVariable long id, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		
		User user = (User)session.getAttribute("u");
		User newUser = entityManager.find(User.class, user.getId());

		Player newPlayer = new Player();
		newPlayer.initPlayer(game, newUser, 0);

		game.getPlayers().add(newPlayer);

		entityManager.persist(newPlayer);
		entityManager.persist(game);
        entityManager.flush();
        
        return "redirect:/lobby/"+id;
	}

	@GetMapping("/j/{id}")
	public String jGame(@PathVariable long id, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		model.addAttribute("game", game);
		
		if (game.playerExist((User) session.getAttribute("u")))
			return "redirect:/lobby/"+id;
			
		return "join";
	}

	@GetMapping("/{id}")
	public String viewGame(@PathVariable long id, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		model.addAttribute("game", game);
		return "lobby";
	}
	
	@PostMapping("/unp/{id}")
	@Transactional
	@ResponseBody
	public String updateNumPlayers(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		int n = o.get("numPlayers").asInt();
		game.setNumPlayers(n);
		model.addAttribute("game", game);
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
		return "{\"result\": \"OK\"}";
	}

	@PostMapping("/uil/{id}")
	@Transactional
	@ResponseBody
	public String updateIfxLength(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		int n = o.get("ifxLength").asInt();
		game.setIfxLength(n);
		model.addAttribute("game", game);
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
		return "{\"result\": \"OK\"}";
	}
}
