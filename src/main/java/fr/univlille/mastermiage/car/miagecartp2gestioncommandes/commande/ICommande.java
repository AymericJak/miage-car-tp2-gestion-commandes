package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;

import java.util.List;

public interface ICommande {
    void init();

    Iterable<Commande> findAll();

    Iterable<Commande> findAllByClientEmail(String email);

    void create(Client client, List<Article> articles);
}
