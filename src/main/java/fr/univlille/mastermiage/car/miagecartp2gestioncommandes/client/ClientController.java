package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/store/client")
public class ClientController {

    private final IClient clientService;

    @Autowired
    public ClientController(IClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register")
    public String register() {
        return "store/client/register";
    }

    @PostMapping("/create")
    public RedirectView create(@RequestParam String email, @RequestParam String password, @RequestParam String nom, @RequestParam String prenom, RedirectAttributes redirectAttributes) {
        if (clientService.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Cet email est déjà utilisé.");
            return new RedirectView("/store/client/register");
        }

        clientService.create(email, password, nom, prenom);
        redirectAttributes.addFlashAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
        return new RedirectView("/store/client/login");
    }

    @GetMapping("/login")
    public String login() {
        return "store/client/login";
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<Client> client = clientService.findByEmail(email);

        if (client.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email incorrect.");
            return new RedirectView("/store/client/login");
        }

        if (!client.get().getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("error", "Mot de passe incorrect.");
            return new RedirectView("/store/client/login");
        }

        session.setAttribute("client", client.get());
        redirectAttributes.addFlashAttribute("success", "Connexion réussie ! Bienvenue, " + client.get().getPrenom() + ".");

        return new RedirectView("/store/home");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Vous avez été déconnecté avec succès.");
        return new RedirectView("/store/client/login");
    }
}
