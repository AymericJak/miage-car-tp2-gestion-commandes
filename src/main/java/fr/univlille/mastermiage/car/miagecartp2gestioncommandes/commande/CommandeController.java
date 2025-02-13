package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.IArticle;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;


@Controller
@RequestMapping("store/commande")
public class CommandeController {
    private final ICommande commandeService;
    private final IArticle articleService;

    @Autowired
    public CommandeController(ICommande commandeService, IArticle articleService) {
        this.commandeService = commandeService;
        this.articleService = articleService;
    }

    @GetMapping("")
    public ModelAndView index(HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("store/commande/index");
        Client client = (Client) session.getAttribute("client");

        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Vossus devez être connecté pour accéder à vos commandes.");
            modelAndView.setViewName("redirect:/store/client/login");
            return modelAndView;
        }

        Iterable<Commande> commandes = commandeService.findAllByClientEmail(client.getEmail());
        modelAndView.addObject("commandes", commandes);
        return modelAndView;
    }

    private double calculateTotal(Commande commande) {
        return commande.getArticles().stream()
                .mapToDouble(article -> article.getPrix() * article.getQuantite())
                .sum();
    }

    private ModelAndView getCommandeOrRedirect(Long id, String viewName, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Vous devez être connecté pour accéder à cette commande.");
            modelAndView.setViewName("redirect:/store/client/login");
            return modelAndView;
        }

        Optional<Commande> optionalCommande = commandeService.findById(id);
        if (optionalCommande.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Commande introuvable.");
            modelAndView.setViewName("redirect:/store/commande");
            return modelAndView;
        }

        Commande commande = optionalCommande.get();

        if (!commande.getClient().getEmail().equals(client.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Vous n'avez pas accès à cette commande.");
            modelAndView.setViewName("redirect:/store/commande");
            return modelAndView;
        }

        modelAndView.setViewName(viewName);
        modelAndView.addObject("commande", commande);
        modelAndView.addObject("total", calculateTotal(commande));
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        return getCommandeOrRedirect(id, "store/commande/show", session, redirectAttributes);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = getCommandeOrRedirect(id, "store/commande/show", session, redirectAttributes);
        modelAndView.addObject("editMode", true);
        return modelAndView;
    }

    @PostMapping("/{id}/edit")
    public RedirectView addArticleToCommande(@PathVariable Long id, @RequestParam String libelle, @RequestParam double prix, @RequestParam int quantite, HttpSession session, RedirectAttributes redirectAttributes) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Vous devez être connecté pour modifier une commande.");
            return new RedirectView("store/login");
        }

        Optional<Commande> optionalCommande = commandeService.findById(id);
        if (optionalCommande.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Commande introuvable.");
            return new RedirectView("/store/commande");
        }

        Commande commande = optionalCommande.get();

        if (!commande.getClient().getEmail().equals(client.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas modifier cette commande.");
            return new RedirectView("/store/commande");
        }

        Article newArticle = new Article(libelle, quantite, prix);
        articleService.save(newArticle);
        commande.getArticles().add(newArticle);
        commandeService.save(commande);

        redirectAttributes.addFlashAttribute("success", "Article ajouté avec succès !");
        return new RedirectView("/store/commande/" + id + "/edit");
    }

    @PostMapping("/create")
    public RedirectView create(@RequestParam String nom, HttpSession session, RedirectAttributes redirectAttributes) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Vous devez être connecté pour créer une commande.");
            return new RedirectView("/store/client/login");
        }
        commandeService.create(nom, client);
        redirectAttributes.addFlashAttribute("success", "Commande créée avec succès !");
        return new RedirectView("/store/commande");
    }

    @PostMapping("/{commandeId}/article/{articleId}/delete")
    public RedirectView deleteArticleFromCommande(@PathVariable Long commandeId, @PathVariable Long articleId, HttpSession session, RedirectAttributes redirectAttributes) {
        Client client = (Client) session.getAttribute("client");
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Vous devez être connecté pour modifier une commande.");
            return new RedirectView("/store/client/login");
        }

        Optional<Commande> optionalCommande = commandeService.findById(commandeId);
        if (optionalCommande.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Commande introuvable.");
            return new RedirectView("/store/commande");
        }

        Commande commande = optionalCommande.get();

        if (!commande.getClient().getEmail().equals(client.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas modifier cette commande.");
            return new RedirectView("/store/commande");
        }

        Optional<Article> optionalArticle = commande.getArticles().stream().filter(article -> article.getId() == articleId).findFirst();
        if (optionalArticle.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Article introuvable dans cette commande.");
            return new RedirectView("/store/commande/" + commandeId + "/edit");
        }

        Article article = optionalArticle.get();
        commande.getArticles().remove(article);
        commandeService.save(commande);

        articleService.deleteById(articleId);

        redirectAttributes.addFlashAttribute("success", "Article supprimé avec succès !");
        return new RedirectView("/store/commande/" + commandeId + "/edit");
    }
}
