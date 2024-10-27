package papyrus.models;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Uporabnik {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

	@Column(unique = true)
	private String email;

	private String password;

	@OneToMany(mappedBy = "uporabnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Zbirka> zbirkaCollection;

	@OneToMany(mappedBy = "uporabnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Ocena> ocenaCollection;

	@OneToOne(mappedBy = "uporabnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Trgovina_Umetnika trgovinaUmetnika;

	@OneToMany(mappedBy = "uporabnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Racun> racunCollection;

	@OneToMany(mappedBy = "uporabnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Komentar> komentarCollection;

	@OneToMany(mappedBy = "uporabnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Objava> objavaCollection;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "uporabnik_tekmovanje",
			joinColumns = @JoinColumn(name = "uporabnik_id"),
			inverseJoinColumns = @JoinColumn(name = "tekmovanje_id"))
	private List<Tekmovanje> tekmovanja;

	public Uporabnik(Long id, String name, String email, String password, Collection<Zbirka> zbirkaCollection, Collection<Ocena> ocenaCollection, Trgovina_Umetnika trgovinaUmetnika, Collection<Racun> racunCollection, Collection<Komentar> komentarCollection, Collection<Objava> objavaCollection) {
		this.id = id;
		this.name = name;
		this.email=email;
		this.password=password;
		this.zbirkaCollection = zbirkaCollection;
		this.ocenaCollection = ocenaCollection;
		this.trgovinaUmetnika = trgovinaUmetnika;
		this.racunCollection = racunCollection;
		this.komentarCollection = komentarCollection;
		this.objavaCollection = objavaCollection;
	}

	public Uporabnik(String name, Collection<Zbirka> zbirkaCollection, Collection<Ocena> ocenaCollection, Trgovina_Umetnika trgovinaUmetnika, Collection<Racun> racunCollection, Collection<Komentar> komentarCollection, Collection<Objava> objavaCollection) {
		this.name = name;
		this.zbirkaCollection = zbirkaCollection;
		this.ocenaCollection = ocenaCollection;
		this.trgovinaUmetnika = trgovinaUmetnika;
		this.racunCollection = racunCollection;
		this.komentarCollection = komentarCollection;
		this.objavaCollection = objavaCollection;
	}
	public Uporabnik(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Uporabnik() {

	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Collection<Zbirka> getZbirkaCollection() {
		return zbirkaCollection;
	}

	public void setZbirkaCollection(Collection<Zbirka> zbirkaCollection) {
		this.zbirkaCollection = zbirkaCollection;
	}

	public Collection<Ocena> getOcenaCollection() {
		return ocenaCollection;
	}

	public void setOcenaCollection(Collection<Ocena> ocenaCollection) {
		this.ocenaCollection = ocenaCollection;
	}

	public Trgovina_Umetnika getTrgovinaUmetnika() {
		return trgovinaUmetnika;
	}

	public void setTrgovinaUmetnika(Trgovina_Umetnika trgovinaUmetnika) {
		this.trgovinaUmetnika = trgovinaUmetnika;
	}

	public Collection<Racun> getRacunCollection() {
		return racunCollection;
	}

	public void setRacunCollection(Collection<Racun> racunCollection) {
		this.racunCollection = racunCollection;
	}

	public Collection<Komentar> getKomentarCollection() {
		return komentarCollection;
	}

	public void setKomentarCollection(Collection<Komentar> komentarCollection) {
		this.komentarCollection = komentarCollection;
	}

	public Collection<Objava> getObjavaCollection() {
		return objavaCollection;
	}

	public void setObjavaCollection(Collection<Objava> objavaCollection) {
		this.objavaCollection = objavaCollection;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Uporabnik(Long id) {
		this.id = id;
	}

	public List<Tekmovanje> getTekmovanja() {
		return tekmovanja;
	}
}