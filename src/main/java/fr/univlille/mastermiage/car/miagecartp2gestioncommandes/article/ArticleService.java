package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService implements IArticle {

    @Autowired
    private ArticleRepository articleRepository;

    public void init() {
        Iterable<Article> articles = List.of(new Article[]{
                new Article("Livre", 100, 19.99),
                new Article("Stylo", 1000, 0.50),
                new Article("Ordinateur", 20, 249.99)
        });
        articleRepository.saveAll(articles);

    }

    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    public void create(String libelle, int quantite, double prix) {
        Article article = new Article(libelle, quantite, prix);
        articleRepository.save(article);
    }
}
