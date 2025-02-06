package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

}
