package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.store;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.IClient;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande.ICommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/store")
public class StoreController {

    private final IClient clientService;

    private final ICommande commandeService;

    @Autowired
    public StoreController(IClient clientService, ICommande commandeService) {
        this.clientService = clientService;
        this.commandeService = commandeService;
    }

    @GetMapping(value = {"", "/home"})
    public String home() {
        return "/store/home";
    }

    @GetMapping("/init")
    @ResponseBody
    public RedirectView init(RedirectAttributes redirectAttributes) {
        clientService.init();
        commandeService.init();

        redirectAttributes.addFlashAttribute("success", "Les données ont été initialisées avec succès.");
        return new RedirectView("/store/home");
    }
}
