package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.kafka;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande.Commande;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande.CommandeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    private final CommandeService commandeService;

    public KafkaController(KafkaProducer kafkaProducer, CommandeService commandeService) {
        this.kafkaProducer = kafkaProducer;
        this.commandeService = commandeService;
    }

    @GetMapping("/commande/{id}")
    public ModelAndView envoyerEtSupprimerCommande(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        Commande commande = commandeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable avec l'id : " + id));

        for (Article article : commande.getArticles()) {
            String message = article.getLibelle() + " " + article.getQuantite();
            kafkaProducer.produce(message);
        }

        commandeService.deleteById(id);

        modelAndView.setViewName("redirect:/store/commande");
        return modelAndView;
    }
}
