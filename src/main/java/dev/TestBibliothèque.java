package dev;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import dev.entites.Emprunt;
import dev.entites.Livre;

public class TestBibliothèque {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = null;
		
		try {
			emf = Persistence.createEntityManagerFactory("pu_livre");
			
			EntityManager em = emf.createEntityManager();
			
			// Extraire un emprunt et afficher ses livres
			Emprunt emp = em.find(Emprunt.class, 1);
			for (Livre l : emp.getLivres())
				System.out.println(l.getTitre());
			em.close();
		}
		catch(PersistenceException e) {
			System.err.println("Erreur de persistance : " + e.getMessage());
		}
		finally {
			if (emf != null)
				emf.close();
		}

	}

}
