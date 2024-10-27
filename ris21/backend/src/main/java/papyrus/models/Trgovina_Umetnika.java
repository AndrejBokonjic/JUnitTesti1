package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Trgovina_Umetnika {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	@OneToMany(mappedBy = "trgovina_umetnika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	Collection<Storitev> storitevCollection;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "uporabnik_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Uporabnik uporabnik;
	public void dodajKosarica() {
		throw new UnsupportedOperationException();
	}

	public void odstraniKosarica() {
		throw new UnsupportedOperationException();
	}


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

	public Collection<Storitev> getStoritevCollection() {
		return storitevCollection;
	}

	public void setStoritevCollection(Collection<Storitev> storitevCollection) {
		this.storitevCollection = storitevCollection;
	}

	public Uporabnik getUporabnik() {
		return uporabnik;
	}

	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}


	public void dodajStoritev(Storitev storitev) {
		if (storitevCollection == null) {
			storitevCollection = new ArrayList<>();
		}
		storitevCollection.add(storitev);
		storitev.setTrgovinaUmetnika(this);
	}
}