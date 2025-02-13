package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;

import java.util.List;

public interface ICommande {
    void init();

    Iterable<Commande> findAll();

    void create(List<Article> articles);
}
