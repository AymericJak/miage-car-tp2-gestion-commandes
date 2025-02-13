package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article;

import org.springframework.data.repository.CrudRepository;


public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findFirstByLibelle(String libelle);
}
