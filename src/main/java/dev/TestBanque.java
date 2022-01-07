package dev;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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
		
		Virement op1 = new Virement();
		op1.setBeneficiaire("Albert Camus");
		op1.setDate(LocalDateTime.now());
		op1.setMontant(500);
		op1.setMotif("paiement quelconque");
		op1.setCompte(co1);
		
		//pour éviter la collision entre les deux opérations
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Operation op2 = new Operation();
		op2.setCompte(co2);
		op2.setDate(LocalDateTime.now());
		op2.setMontant(2000);
		op2.setMotif("opération quelconque");
		
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
			em.persist(op1);
			em.persist(op2);
			em.getTransaction().commit();
			
			// Vérification des insertions par requêtes JPQL
			// Présence de la banque
			TypedQuery<Banque> qBanque = em.createQuery("select b from Banque b where b.nom='Crédit Nantais'", Banque.class);
			Banque b = qBanque.getSingleResult();
			if(b != null) System.out.println(b.getNom());
			
			// Présence d'un client
			TypedQuery<Client> qClient  = em.createQuery("select c from Client c where c.id=2", Client.class);
			Client c = qClient.getSingleResult();
			if(c != null) System.out.println(c);
			
			// Présence du compte à deux clients
			TypedQuery<Compte> qCompte = em.createQuery("select c from Compte c where c.numero='1'", Compte.class);
			Compte co = qCompte.getSingleResult();
			if(co != null) System.out.println(co);
			
			// Présence du deuxième compte
			qCompte = em.createQuery("select c from Compte c where c.numero='2'", Compte.class);
			co = qCompte.getSingleResult();
			if(co != null) System.out.println(co);
			
			// Présence de l'opération type virement
			TypedQuery<Operation> qOp = em.createQuery("select o from Operation o where o.id= :dateOp", Operation.class);
			LocalDateTime dateOperation = op1.getDate();
			Operation o = qOp.setParameter("dateOp", dateOperation).getSingleResult();
			if(o != null) System.out.println(o);
			
			// Présence de l'opération type opération
			qOp = em.createQuery("select o from Operation o where o.id= :dateOp", Operation.class);
			dateOperation = op2.getDate();
			o = qOp.setParameter("dateOp", dateOperation).getSingleResult();
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
