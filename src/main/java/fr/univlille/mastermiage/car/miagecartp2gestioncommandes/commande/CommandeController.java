package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("store/commande")
public class CommandeController {
    private final ICommande commandeService;

    @Autowired
    public CommandeController(ICommande commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("store/commande/index");
        modelAndView.addObject("commandes", commandeService.findAll());
        return modelAndView;
    }

}
