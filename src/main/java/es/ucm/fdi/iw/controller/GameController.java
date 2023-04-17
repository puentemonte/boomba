package es.ucm.fdi.iw.controller;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.*;
import es.ucm.fdi.iw.model.Game.GameState;

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
@RequestMapping("game")
public class GameController {
    private static final Logger log = LogManager.getLogger(LobbyController.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/start/{id}")
    @Transactional
    @ResponseBody
	public String startGame(@PathVariable long id, @RequestBody JsonNode o, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
        game.setState(GameState.GAME);
		model.addAttribute("game", game);

        messagingTemplate.convertAndSend("/topic/" + game.getTopicCode(), "{\"type\": \"START\"}");
		return "{\"result\": \"OK\"}";
	}

    @GetMapping("/{id}")
	public String game(@PathVariable long id, Model model, HttpSession session) {
		Game game = entityManager.find(Game.class, id);
		model.addAttribute("game", game);
		return "game";
	}
    
}
