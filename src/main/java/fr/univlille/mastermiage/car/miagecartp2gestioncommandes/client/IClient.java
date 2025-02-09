package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

import java.util.Optional;

public interface IClient {
    void init();

    Iterable<Client> findAll();

    void create(String email, String password, String nom, String prenom);

    Optional<Client> findByEmail(String email);
}
