package papyrus.models;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import org.hibernate.annotations.*;

@Entity
public class Zbirka {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uporabnik_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Uporabnik uporabnik;

	@OneToMany(mappedBy = "zbirka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Objava> objavaCollection;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Uporabnik getUporabnik() {
		return uporabnik;
	}

	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}

	public Collection<Objava> getObjave() {
		return objavaCollection;
	}

	public void setObjave(Collection<Objava> objavaCollection) {
		this.objavaCollection = objavaCollection;
	}
}
