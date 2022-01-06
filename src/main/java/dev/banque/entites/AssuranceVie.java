package dev.banque.entites;

import java.time.LocalDate;

public class AssuranceVie extends Compte{
	
	private LocalDate dateFin;
	private double taux;
	
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
	
	

}
