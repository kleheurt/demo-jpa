package dev.banque.entites;

import javax.persistence.Entity;

@Entity
public class Virement extends Operation{

	private String beneficiaire;

	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}
	
	@Override
	public String toString() {
		return "Virement vers "+this.beneficiaire+" pour motif : "+this.motif;
	}
}
