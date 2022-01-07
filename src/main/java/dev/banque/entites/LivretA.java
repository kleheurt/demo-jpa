package dev.banque.entites;

import javax.persistence.Entity;

@Entity
public class LivretA extends Compte {
	
	private double taux = 2;

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}
	
	@Override
	public String toString() {
		return "Livret A n°"+this.numero+super.toString();
	}

}
