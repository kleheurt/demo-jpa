package dev;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import dev.banque.entites.Adresse;
import dev.banque.entites.AssuranceVie;
import dev.banque.entites.Banque;
import dev.banque.entites.Client;
import dev.banque.entites.Compte;
import dev.banque.entites.LivretA;
import dev.banque.entites.Operation;
import dev.banque.entites.Virement;

public class TestBanque {

	public static void main(String[] args) {
			
		// Création des objets
		Banque banque = new Banque();
		banque.setNom("Crédit Nantais");
		
		Adresse a1 = new Adresse(10, "rue de Strasbourg", 44000, "Nantes");
		Adresse a2 = new Adresse(1, "rue de la Paix", 99999, "Moulinsart");
		
		Client cli1 = new Client("Haddock", "Capitaine", LocalDate.now(), a1, banque);
		cli1.setId(2);
		
		Client cli2 = new Client("Tournesol", "Professeur", LocalDate.now(), a2, banque);
		cli2.setId(3);

		Compte co1 = new LivretA();		
		co1.setNumero("1");
		co1.ajouterClient(cli1);
		co1.ajouterClient(cli2);
		co1.setSolde(1000);
		
		Compte co2 = new AssuranceVie();
		co2.setNumero("2");
		co2.ajouterClient(cli2);
		co2.setSolde(7500);
		
		Virement operation = new Virement();
		operation.setBeneficiaire("Albert Camus");
		operation.setDate(LocalDateTime.now());
		operation.setMontant(500);
		operation.setMotif("paiement quelconque");
		operation.setCompte(co1);
		

		
		// Persistance en BDD
		EntityManagerFactory emf = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("pu_banque");
			
			EntityManager em = emf.createEntityManager();
			
			// Insertion des objets
			em.getTransaction().begin();
			em.persist(banque);
			em.persist(cli1);
			em.persist(cli2);
			em.persist(co2);
			em.persist(co1);
			em.persist(operation);
			em.getTransaction().commit();
			
			// Vérification des insertions par requêtes JPQL
			TypedQuery<Banque> qBanque = em.createQuery("select b from Banque b where b.nom='Crédit Nantais'", Banque.class);
			Banque b = qBanque.getSingleResult();
			if(b != null) System.out.println(b.getNom());
			
			TypedQuery<Client> qClient  = em.createQuery("select c from Client c where c.id=2", Client.class);
			Client c = qClient.getSingleResult();
			if(c != null) System.out.println(c);
			
			TypedQuery<Compte> qCompte = em.createQuery("select c from Compte c where c.numero='1'", Compte.class);
			Compte co = qCompte.getSingleResult();
			if(co != null) System.out.println(co);
			
			qCompte = em.createQuery("select c from Compte c where c.numero='2'", Compte.class);
			co = qCompte.getSingleResult();
			if(co != null) System.out.println(co);
			
			TypedQuery<Operation> qOp = em.createQuery("select o from Operation o where o.id= :dateOp", Operation.class);
			LocalDateTime dateOperation = operation.getDate();
			Operation o = qOp.setParameter("dateOp", dateOperation).getSingleResult();
			if(o != null) System.out.println(o);
		}
		catch(PersistenceException e) {
			System.err.println("Persistence : "+e.getMessage());
		}
		finally{
			if(emf != null) emf.close();
		}
		
		

	}

}
