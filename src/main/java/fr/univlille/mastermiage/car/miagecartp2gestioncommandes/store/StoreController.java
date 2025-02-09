package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.store;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.IArticle;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.IClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private IClient clientService;

    @Autowired
    private IArticle articleService;

    @GetMapping(value = {"", "/home"})
    public String home() {
        return "/store/home";
    }

    @GetMapping("/init")
    @ResponseBody
    public RedirectView init() {
        clientService.init();
        articleService.init();
        return new RedirectView("/store/home");
    }


}
