package dev.banque.entites;

import javax.persistence.Embeddable;

@Embeddable
public class Adresse {
	

	private Integer numero;
	private String rue;
	private Integer codePostal;
	private String ville;
	

	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public int getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}

	public Adresse() {
		super();
	}
	
	public Adresse(Integer numero, String rue, Integer codePostal, String ville) {
		this.numero = numero;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

}
