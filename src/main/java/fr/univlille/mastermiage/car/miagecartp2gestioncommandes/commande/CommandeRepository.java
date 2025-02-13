package fr.univlille.mastermiage.car.miagecartp2gestioncommandes.commande;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommandeRepository extends CrudRepository<Commande, Long> {
    List<Commande> findAllByClientEmail(String email);
}
