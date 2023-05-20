package es.ucm.fdi.iw.controller;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import es.ucm.fdi.iw.model.*;
import es.ucm.fdi.iw.model.User.Role;

/**
 *  Non-authenticated requests only.
 */
@Controller
public class RootController {

    @Autowired
	private EntityManager entityManager;

	private static final Logger log = LogManager.getLogger(RootController.class);

	@GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

	@GetMapping("/")
    public String index(Model model) {
        return "home";
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
        return "lobby";
    }

    @GetMapping("/game")
    public String game(Model model) {
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
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("u");
        String[] columns = new String[]{"Código", "Ranking", "Número de jugadores","Palabras correctas"};
        List<Player> players = entityManager.createNamedQuery("Player.byUser", Player.class)
		        .setParameter("username", user.getUsername())
		        .getResultList();		
        model.addAttribute("columns", columns);
        model.addAttribute("players", players);
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

    @GetMapping("/join")
    public String join(Model model) {
        return "join";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        // get the list of reported users
        List<User> reportedUsers =  entityManager.createNamedQuery("User.reportedUsers", User.class)
                                    .getResultList();
        for(User u: reportedUsers){
            List<Player> players = entityManager.createNamedQuery("Player.byUser", Player.class)
            .setParameter("username", u.getUsername())
            .getResultList();	
            u.setNumgames(players.size());
        }
        model.addAttribute("reportedUsers",  reportedUsers);
        return "admin";
    }

}
