package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.ArticleRepository;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CommandeService implements ICommande {

    private final CommandeRepository commandeRepository;
    private final ArticleRepository articleRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository, ArticleRepository articleRepository, ClientRepository clientRepository) {
        this.commandeRepository = commandeRepository;
        this.articleRepository = articleRepository;
        this.clientRepository = clientRepository;
    }


    public void init() {
        Random random = new Random();
        List<Client> clients = (List<Client>) clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new RuntimeException("Aucun client trouvé !");
        }
        List<String> libelles = List.of("Livre", "Ordinateur", "Clavier", "Souris", "Téléphone", "Écran", "Casque");
        List<Commande> commandes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Client client = clients.get(random.nextInt(clients.size()));

            List<Article> articles = new ArrayList<>();
            int nombreArticles = random.nextInt(3) + 1; // Entre 1 et 3 articles par commande

            for (int j = 0; j < nombreArticles; j++) {
                String libelle = libelles.get(random.nextInt(libelles.size()));
                double prix = 5 + (random.nextDouble() * 95); // Prix entre 5€ et 100€
                int quantite = random.nextInt(5) + 1; // Quantité entre 1 et 5
                articles.add(new Article(libelle, quantite, prix));
            }
            articleRepository.saveAll(articles);

            Commande commande = new Commande(client, articles);
            commandes.add(commande);
        }

        commandeRepository.saveAll(commandes);
    }

    public Iterable<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public Iterable<Commande> findAllByClientEmail(String email) {
        return commandeRepository.findAllByClientEmail(email);
    }

    public void create(Client client, List<Article> articles) {
        Commande commande = new Commande(client, articles);
        commandeRepository.save(commande);
    }
}
