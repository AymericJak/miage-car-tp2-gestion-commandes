package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;

import java.util.List;
import java.util.Optional;

public interface ICommande {
    void init();

    Iterable<Commande> findAll();

    Iterable<Commande> findAllByClientEmail(String email);

    Optional<Commande> findById(Long id);

    void create(String nom, Client client, List<Article> articles);

    void create(String nom, Client client);
}
