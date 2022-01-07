package dev.banque.entites;

import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class AssuranceVie extends Compte{
	
	private LocalDate dateFin;
	private double taux = 2;
	
	public LocalDate getDateFin() {
		return dateFin;
	}
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	public double getTaux() {
		return taux;
	}
	public void setTaux(double taux) {
		this.taux = taux;
	}
	
	@Override
	public String toString() {
		return "Assurance Vie n°"+this.numero+super.toString();
	}

}
