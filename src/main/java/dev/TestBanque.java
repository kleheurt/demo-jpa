package dev;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import dev.banque.entites.Adresse;
import dev.banque.entites.Banque;
import dev.banque.entites.Client;
import dev.banque.entites.Compte;
import dev.banque.entites.Operation;

public class TestBanque {

	public static void main(String[] args) {
			
		// Création des objets
		Banque banque = new Banque();
		banque.setNom("Crédit Nantais");
		
		Adresse adresse = new Adresse();
		adresse.setCodePostal(44000);
		adresse.setNumero(10);
		adresse.setRue("de Strasbourg");
		adresse.setVille("Nantes");
		
		Client client = new Client();
		client.setId(1);
		client.setBanque(banque);
		client.setAdresse(adresse);
		client.setDateNaissance(LocalDate.now());
		client.setNom("Dupond");
		client.setPrenom("Jean");

		Compte compte = new Compte();		
		compte.setNumero("1");
		compte.ajouterClient(client);
		compte.setSolde(1000);
		
		Operation operation = new Operation();
		operation.setDate(LocalDateTime.now());
		operation.setMontant(500);
		operation.setMotif("Motif 1");
		operation.setCompte(compte);
		
		client.ajouterCompte(compte);
		compte.ajouterClient(client);
		
		// Persistance en BDD
		EntityManagerFactory emf = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("pu_banque");
			
			EntityManager em = emf.createEntityManager();
			
			// Insertion des objets
			em.getTransaction().begin();
			em.persist(banque);
			em.persist(client);
			em.persist(compte);
			em.persist(operation);
			em.getTransaction().commit();
			
			// Vérification des insertions par requêtes JPQL
			TypedQuery<Banque> qBanque = em.createQuery("select b from Banque b where b.nom='Crédit Nantais'", Banque.class);
			Banque b = qBanque.getSingleResult();
			if(b != null) System.out.println(b.getNom());
			
			TypedQuery<Client> qClient  = em.createQuery("select c from Client c where c.id=1", Client.class);
			Client c = qClient.getSingleResult();
			if(c != null) System.out.println(c.getNom());
			
			TypedQuery<Compte> qCompte = em.createQuery("select c from Compte c where c.numero='1'", Compte.class);
			Compte co = qCompte.getSingleResult();
			if(co != null) System.out.println(co.getSolde());
			
			TypedQuery<Operation> qOp = em.createQuery("select o from Operation o where o.id= :dateOp", Operation.class);
			LocalDateTime dateOperation = operation.getDate();
			Operation o = qOp.setParameter("dateOp", dateOperation).getSingleResult();
			if(o != null) System.out.println(o.getMotif());
		}
		catch(PersistenceException e) {
			System.err.println("Persistence : "+e.getMessage());
		}
		finally{
			if(emf != null) emf.close();
		}
		
		

	}

}
