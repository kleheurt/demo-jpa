package dev;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;


public class TestJpa {

	public static void main(String[] args) {

		EntityManagerFactory emf = null;

		try {
			emf = Persistence.createEntityManagerFactory("pu_essai");

			EntityManager em = emf.createEntityManager();
			
			

			em.close();
			
			
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
