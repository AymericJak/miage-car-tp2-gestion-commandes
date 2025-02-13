package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService implements ICommande {

    private final CommandeRepository commandeRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository, ArticleRepository articleRepository) {
        this.commandeRepository = commandeRepository;
        this.articleRepository = articleRepository;
    }


    public void init() {
        List<Article> articles = List.of(
                articleRepository.findFirstByLibelle("Livre"),
                articleRepository.findFirstByLibelle("Ordinateur")
        );
        Commande commande = new Commande(articles);
        commandeRepository.save(commande);

    }

    public Iterable<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public void create(List<Article> articles) {
        Commande commande = new Commande(articles);
        commandeRepository.save(commande);
    }
}
