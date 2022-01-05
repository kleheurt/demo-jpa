package dev;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import dev.entites.Livre;

public class TestLivre {

	public static void main(String[] args) {

		EntityManagerFactory emf = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("pu_livre");
			
			EntityManager em = emf.createEntityManager();
			
			Livre l1 = em.find(Livre.class, 1);
			
			System.out.println("Livre : "+l1.getId());

			em.close();
			
			System.out.println("OK");
			
			
		} 
		catch (PersistenceException e) {
			System.err.println("Erreur de persistance : " + e.getMessage());
		}
		
		finally {
			if (emf != null) {
				emf.close();
			}

		}
	}
}
	
	
