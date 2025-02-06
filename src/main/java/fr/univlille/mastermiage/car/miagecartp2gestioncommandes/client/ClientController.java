package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/store/client")
public class ClientController {

    @Autowired
    private IClient clientService;

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
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {
        Optional<Client> client = clientService.findByEmail(email);

        ModelAndView modelAndView = new ModelAndView("store/client/login");

        if (client.isPresent()) {
            if (client.get().getPassword().equals(password)) {
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
}
