package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article;

public interface IArticle {
    Iterable<Article> findAll();

    void create(String libelle, int quantite, double prix);

    void deleteById(Long id);

    void save(Article article);
}
