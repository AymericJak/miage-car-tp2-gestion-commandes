package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "COMMAND")
public class Commande {
    @Id
    @GeneratedValue
//    @Column(name = "ID")
    private Long id;

    @OneToMany
//    @Column(name = "ARTICLES")
    private List<Article> articles = new ArrayList<>();

    public Commande() {
    }

    public Commande(List<Article> articles) {
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
