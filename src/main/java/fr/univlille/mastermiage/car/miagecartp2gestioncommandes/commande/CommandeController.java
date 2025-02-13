package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping("store/commande")
public class CommandeController {
    private final ICommande commandeService;

    @Autowired
    public CommandeController(ICommande commandeService) {
        this.commandeService = commandeService;
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
        return getCommandeOrRedirect(id, "store/commande/edit");
    }


}
