package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/store")
public class StoreController {

    @GetMapping("/home")
    public String home() {
        return "store/home";
    }

}
