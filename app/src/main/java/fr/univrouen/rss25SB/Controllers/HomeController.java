package fr.univrouen.rss25SB.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projectName", "Projet RSS25SB");
        model.addAttribute("version", "1.0");
        model.addAttribute("developer", "Nom Prénom"); // Remplace par ton nom
        return "index";
    }
}