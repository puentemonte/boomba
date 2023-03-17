package es.ucm.fdi.iw.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.ucm.fdi.iw.model.*;
import es.ucm.fdi.iw.model.User.Role;

/**
 *  Non-authenticated requests only.
 */
@Controller
public class RootController {

	private static final Logger log = LogManager.getLogger(RootController.class);

	@GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

	@GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @PostMapping("/lobby")
    public String lobby(Model model, HttpSession session) {
        User requester = (User)session.getAttribute("u");
        String[] alphabet = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".split("");
        String[] topics = new String[]{"Verbos", "Sustantivos", "Animales", "Comida"}; 
        model.addAttribute("alphabet", alphabet);
        model.addAttribute("selected_topics", topics);
        model.addAttribute("creator", requester.getId());
        return "lobby";
    }

    @GetMapping("/game")
    public String game(Model model) {
        String[] alphabet = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".split("");
        String[] interfix = new String[]{"I", "T", "O"};
        
        model.addAttribute("alphabet", alphabet);
        model.addAttribute("interfix", interfix);

        return "game";
    }

    @GetMapping("/bystander")
    public String bystander(Model model) {
        String[] alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P",
                                        "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] guessed_word = new String[]{"M", "E", "R", "I", "T", "O"};
        model.addAttribute("alphabet", alphabet);
        model.addAttribute("guessed_word", guessed_word);
        return "bystander";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        String[] columns = new String[]{"Número de partidas", "Resultado", "Palabras", "Rondas"};
        String[] codes = new String[]{"#1111", "#2222", "#3333", "#4444", "#5555"};
        model.addAttribute("columns", columns);
        model.addAttribute("codes", codes);
        return "profile";
    }

    @GetMapping("/summary")
    public String summary(Model model) {
        return "summary";
    }

    @GetMapping("/report")
    public String report(Model model) {
        return "report";
    }

}
