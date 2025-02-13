package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClient {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void init() {
        Iterable<Client> clients = List.of(new Client[]{
                new Client("miage@univ-lille.fr", "miage", "MonNom", "MonPrenom"),
                new Client("client1@univ-lille.fr", "miage", "nomClient1", "prenomClient1"),
                new Client("client2@univ-lille.fr", "miage", "nomClient2", "prenomClient2"),
                new Client("client3@univ-lille.fr", "miage", "nomClient3", "prenomClient3")
        });
        clientRepository.saveAll(clients);
    }

    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    public void create(String email, String password, String nom, String prenom) {
        Client client = new Client(email, password, nom, prenom);
        clientRepository.save(client);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findById(email);
    }
}
