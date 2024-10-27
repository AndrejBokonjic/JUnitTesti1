package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Racun {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "uporabnik_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Uporabnik uporabnik;


	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "storitev_id")  // ime stolpca za tuj ključ
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Storitev storitev;

	public Racun(Long id, Uporabnik uporabnik, Storitev storitevCollection) {
		this.id = id;
		this.uporabnik = uporabnik;
		this.storitev = storitev;
	}

	public Racun(Uporabnik uporabnik, Collection<Storitev> storitevCollection) {
		this.uporabnik = uporabnik;
		this.storitev = storitev;
	}

	public Racun(Long id, Uporabnik uporabnik) {
		this.id = id;
		this.uporabnik = uporabnik;
	}

	public Racun() {

	}

	public Racun(Uporabnik uporabnik) {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Uporabnik getUporabnik() {
		return uporabnik;
	}

	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}

	public Storitev getstoritev() {
		return storitev;
	}

	public void setStoritev(Storitev storitev){this.storitev = storitev;}


	public Racun(Uporabnik uporabnik, Storitev storitev) {
		this.uporabnik = uporabnik;
		this.storitev = storitev;
	}


	@Override
	public String toString() {
		return "Racun{" +
				"id=" + id +
				", uporabnik=" + uporabnik +
				", storitevCollection=" + storitev +
				'}';
	}
}