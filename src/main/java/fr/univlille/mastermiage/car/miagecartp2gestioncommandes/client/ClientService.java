package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClient {

    @Autowired
    private ClientRepository clientRepository;

    public void init() {
        Iterable<Client> clients = List.of(new Client[]{
                new Client("miage@univ-lille.fr", "miage", "MonNom", "MonPrenom")
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
}
