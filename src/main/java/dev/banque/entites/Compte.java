package dev.banque.entites;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Compte {

	@Id
	private String numero;
	private double solde;
	
	@OneToMany(mappedBy="compte")
	private Set<Operation> operations;
	
	@ManyToMany
	private Set<Client> clients;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public Set<Operation> getOperations() {
		return operations;
	}
	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}
	public Set<Client> getClients() {
		return clients;
	}
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public void ajouterClient(Client c) {
		this.clients.add(c);
	}
	
	public void ajouterOperation(Operation o) {
		this.operations.add(o);
	}
	
	public Compte() {
		super();
		this.clients = new HashSet<Client>();
		this.operations = new HashSet<Operation>();
	}

}
