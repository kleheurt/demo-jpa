package dev;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import dev.entites.Livre;

public class TestLivre {

	public static void main(String[] args) {

		EntityManagerFactory emf = null;

		try {
			emf = Persistence.createEntityManagerFactory("pu_livre");

			EntityManager em = emf.createEntityManager();

			// FIND LIVRE BY ID
			Livre l1 = em.find(Livre.class, 2);
			System.out.println("Livre n°" + l1.getId() + " : " + l1.getTitre() + " de " + l1.getAuteur());

			// INSERT LIVRE
			Livre l2 = new Livre();
			l2.setId(6);
			l2.setAuteur("Martin Heidegger");
			l2.setTitre("Être et Temps");
			em.getTransaction().begin();
			em.persist(l2);
			em.getTransaction().commit();

			// MODIFIER LIVRE 5
			em.getTransaction().begin();
			Livre l3 = em.find(Livre.class, 5);
			if(l3 != null)
				l3.setTitre("Du plaisir dans la cuisine");
			em.getTransaction().commit();
		
			// JPQL par titre
			TypedQuery<Livre> q1 = em.createQuery("select l from Livre l where l.titre='Être et Temps'", Livre.class);
			Livre l4 = q1.getSingleResult();
			if(l4 != null)
				System.out.println(l4.getAuteur());

			// JPQL par auteur
			TypedQuery<Livre> q2 = em.createQuery("select l from Livre l where l.auteur='Martin Heidegger'", Livre.class);
			Livre l5 = q2.getSingleResult();
			if(l5 != null)
				System.out.println(l5.getTitre());
			
			// SUPPRIMER LIVRE 6
			em.getTransaction().begin();
			Livre l6 = em.find(Livre.class, 6);
			if(l6 != null)
				em.remove(l6);
			em.getTransaction().commit();

			em.close();


		} catch (PersistenceException e) {
			System.err.println("Erreur de persistance : " + e.getMessage());
		}

		finally {
			if (emf != null) {
				emf.close();
			}

		}
	}
}
