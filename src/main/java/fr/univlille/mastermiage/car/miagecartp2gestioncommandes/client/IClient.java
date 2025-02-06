package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

public interface IClient {
    void init();

    Iterable<Client> findAll();

    void create(String email, String password, String nom, String prenom);
}
