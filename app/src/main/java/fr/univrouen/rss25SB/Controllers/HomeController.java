package fr.univrouen.rss25SB.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projectName", "Projet RSS25SB");
        model.addAttribute("version", "1.0");
        model.addAttribute("developer", "Belhocine Juba et Zabili Zakaria"); // Remplace par ton nom
        return "index";
    }

    @GetMapping("/help")
    public String help(Model model) {
        List<Operation> operations = List.of(
                new Operation(
                        "/rss25SB/insert",
                        "POST",
                        "Insertion d’un flux RSS25SB.",
                        "Body : XML conforme au schéma RSS25SB",
                        "XML : <insertResponse><id>...</id><status>INSERTED|ERROR</status></insertResponse>"
                ),
                new Operation(
                        "/rss25SB/resume/xml",
                        "GET",
                        "Liste des articles (résumé) au format XML.",
                        "Aucun",
                        "XML : liste d’articles (id, guid, date)"
                ),
                new Operation(
                        "/rss25SB/resume/html",
                        "GET",
                        "Liste des articles (résumé) au format HTML.",
                        "Aucun",
                        "HTML"
                ),
                new Operation(
                        "/rss25SB/resume/xml/{id}",
                        "GET",
                        "Détail d’un article au format XML.",
                        "Path : id (Long)",
                        "XML : article complet ou <error><id>...</id><status>ERROR</status></error>"
                ),
                new Operation(
                        "/rss25SB/html/{id}",
                        "GET",
                        "Détail d’un article au format HTML.",
                        "Path : id (Long)",
                        "HTML"
                ),
                new Operation(
                        "/rss25SB/delete/{id}",
                        "DELETE",
                        "Suppression d’un article.",
                        "Path : id (Long)",
                        "XML : <deleteResponse><id>...</id><status>DELETED|ERROR</status></deleteResponse>"
                ),
                new Operation(
                        "/rss25SB/search?date=...&category=...",
                        "GET",
                        "Recherche d’articles par date et/ou catégorie. Renvoie les articles faits après la date indiquée",
                        "Query : date (ISO 8601, optionnel, ex: 2024-05-01T00:00:00Z), category (texte exact, optionnel, ex: Informatique)",
                        "XML : liste d’articles ou <status>NONE</status>/<status>ERROR</status>"
                )
        );
        model.addAttribute("operations", operations);
        return "help";
    }

    public static class Operation {
        private String url;
        private String method;
        private String summary;
        private String inputs;
        private String returns;

        public Operation(String url, String method, String summary, String inputs, String returns) {
            this.url = url;
            this.method = method;
            this.summary = summary;
            this.inputs = inputs;
            this.returns = returns;
        }
        public String getUrl() { return url; }
        public String getMethod() { return method; }
        public String getSummary() { return summary; }
        public String getInputs() { return inputs; }
        public String getReturns() { return returns; }
    }
}