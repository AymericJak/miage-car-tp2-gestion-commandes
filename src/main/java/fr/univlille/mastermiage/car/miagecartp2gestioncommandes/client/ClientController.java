package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande.Commande;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande.ICommande;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/store/client")
public class ClientController {

    private final IClient clientService;
    private final ICommande commandeService;

    @Autowired
    public ClientController(IClient clientService, ICommande commandeService) {
        this.clientService = clientService;
        this.commandeService = commandeService;
    }

    @GetMapping("/register")
    public String register() {
        return "store/client/register";
    }

    @PostMapping("/create")
    public ModelAndView create(@RequestParam String email, @RequestParam String password, @RequestParam String nom, @RequestParam String prenom) {
        clientService.create(email, password, nom, prenom);
        ModelAndView modelAndView = new ModelAndView("store/client/successfull-register");
        modelAndView.addObject("prenom", prenom);
        modelAndView.addObject("nom", nom);
        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "store/client/login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Optional<Client> client = clientService.findByEmail(email);

        ModelAndView modelAndView = new ModelAndView("store/client/login");

        if (client.isPresent()) {
            if (client.get().getPassword().equals(password)) {
                session.setAttribute("client", client.get());

                modelAndView = new ModelAndView("store/client/successfull-login");
                modelAndView.addObject("prenom", client.get().getPrenom());
                modelAndView.addObject("nom", client.get().getNom());
                return modelAndView;
            } else {
                modelAndView.addObject("error", "Mot de passe incorrect.");
                return modelAndView;
            }
        } else {
            modelAndView.addObject("error", "Email incorrect.");
            return modelAndView;
        }
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/store/client/login");
    }

    @GetMapping("/{email}/commandes")
    public ModelAndView getUserCommands(@PathVariable String email) {
        ModelAndView modelAndView = new ModelAndView("store/commande/index");
        System.out.println("Test");
        Iterable<Commande> commandes = commandeService.findAllByClientEmail(email);
        modelAndView.addObject("commandes", commandes);
        return modelAndView;
    }

}
