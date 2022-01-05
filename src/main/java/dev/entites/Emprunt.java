package dev.entites;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "emprunt")
public class Emprunt {

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@OneToMany
	@JoinColumn(name = "ID_CLIENT")
	private Client client;
	
	@Column(name = "DATE_DEBUT")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date date_debut;
	
	@Column(name = "DATE_FIN")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date date_fin;
	
	@Column(name = "DELAI")
	private Integer delai;
	
	@ManyToMany
	@JoinTable(name="COMPO",
			joinColumns=@JoinColumn(name="ID_EMP", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="ID_LV", referencedColumnName="ID")
	)
	private Set<Livre> livres;

	public Set<Livre> getLivres() {
		return livres;
	}

	public void setLivres(Set<Livre> livres) {
		this.livres = livres;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public Integer getDelai() {
		return delai;
	}

	public void setDelai(Integer delai) {
		this.delai = delai;
	}
	
	
	
}
