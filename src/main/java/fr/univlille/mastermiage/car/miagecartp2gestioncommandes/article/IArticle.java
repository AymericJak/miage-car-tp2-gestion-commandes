package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article;

public interface IArticle {
    void init();

    Iterable<Article> findAll();

    void create(String libelle, int quantite, double prix);
}
