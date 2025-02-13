package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.ArticleRepository;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;


@Controller
@RequestMapping("store/commande")
public class CommandeController {
    private final ICommande commandeService;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommandeController(ICommande commandeService, ArticleRepository articleRepository) {
        this.commandeService = commandeService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("")
    public ModelAndView index(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("store/commande/index");
        Client client = (Client) session.getAttribute("client");

        if (client == null) {
            modelAndView.setViewName("redirect:/store/client/login");
            return modelAndView;
        }

        Iterable<Commande> commandes = commandeService.findAllByClientEmail(client.getEmail());
        modelAndView.addObject("commandes", commandes);
        return modelAndView;
    }

    private ModelAndView getCommandeOrRedirect(Long id, String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Commande> commande = commandeService.findById(id);

        if (commande.isPresent()) {
            modelAndView.setViewName(viewName);
            modelAndView.addObject("commande", commande.get());
        } else {
            modelAndView.setViewName("redirect:/store/commande");
        }

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        return getCommandeOrRedirect(id, "store/commande/show");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = getCommandeOrRedirect(id, "store/commande/show");
        modelAndView.addObject("editMode", true);
        return modelAndView;
    }

    @PostMapping("/{id}/edit")
    public RedirectView addArticleToCommande(@PathVariable Long id, @RequestParam String libelle, @RequestParam double prix, @RequestParam int quantite, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            return new RedirectView("store/login");
        }

        Optional<Commande> optionalCommande = commandeService.findById(id);
        if (optionalCommande.isEmpty()) {
            return new RedirectView("/store/commande");
        }

        Commande commande = optionalCommande.get();

        if (!commande.getClient().getEmail().equals(client.getEmail())) {
            return new RedirectView("/store/commande");
        }

        Article newArticle = new Article(libelle, quantite, prix);
        articleRepository.save(newArticle);
        commande.getArticles().add(newArticle);
        commandeService.save(commande);

        return new RedirectView("/store/commande/" + id + "/edit");
    }

    @PostMapping("/create")
    public RedirectView create(@RequestParam String nom, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        if (client != null) {
            commandeService.create(nom, client);
        }
        return new RedirectView("/store/commande");
    }

}
