package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements IArticle {


    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    public void create(String libelle, int quantite, double prix) {
        Article article = new Article(libelle, quantite, prix);
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public void save(Article article) {
        articleRepository.save(article);
    }
}
