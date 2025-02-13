package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.article.Article;
import fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client.Client;
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

    @ManyToOne
    private Client client;

    public Commande() {
    }

    public Commande(Client client, List<Article> articles) {
        this.client = client;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
