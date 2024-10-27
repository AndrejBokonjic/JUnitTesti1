package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collection;

/**
 * Represents an entity for storing textual content and associated images.
 */
@Entity
public class Objava {
	/**
	 * Unique identifier for the Objava.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	/**
	 * Textual content associated with the Objava.
	 */
	private Long id;
	private String text;

	/**
	 * Binary data representing the image associated with the Objava.
	 * Large files are annotated by @Lob. Images are stored as byte arrays.
	 */
	@Lob
	@Column(length = 10000000)
	private byte[] imageData;

	// -------------------RELATIONSHIPS-------------------
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zbirka_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Zbirka zbirka;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uporabnik_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Uporabnik uporabnik;

	@OneToMany(mappedBy = "objava", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Ocena> ocena;

	@OneToMany(mappedBy = "objava", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Komentar> komentarCollection;

	@OneToMany(mappedBy = "objava", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Collection<Tekmovanje> tekmovanjeCollection;

	public Objava(Long id, String text) {
		this.id = id;
		this.text = text;
	}
	public Objava (){

	}
	// -------------------METHODS-------------------
	public void publish() {
		throw new UnsupportedOperationException();
	}

	// -------------------GETTERS/SETTERS-------------------
	/**
	 * Retrieves the unique identifier of the Objava.
	 *
	 * @return The unique identifier of the Objava.
	 */
	public Long getId() {
		return id;
	}


	/**
	 * Sets the unique identifier of the Objava.
	 *
	 * @param id The unique identifier to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retrieves the textual content of the Objava.
	 *
	 * @return The textual content of the Objava.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the textual content of the Objava.
	 *
	 * @param text The textual content to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	public Zbirka getZbirka() {
		return zbirka;
	}

	public void setZbirka(Zbirka zbirka) {
		this.zbirka = zbirka;
	}

	public Uporabnik getUporabnik() {
		return uporabnik;
	}

	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}

	public Collection<Ocena> getOcena() {
		return ocena;
	}

	public void setOcena(Collection<Ocena> ocena) {
		this.ocena = ocena;
	}

	public Collection<Komentar> getKomentarCollection() {
		return komentarCollection;
	}

	public void setKomentarCollection(Collection<Komentar> komentarCollection) {
		this.komentarCollection = komentarCollection;
	}

	public Collection<Tekmovanje> getTekmovanjeCollection() {
		return tekmovanjeCollection;
	}

	public void setTekmovanjeCollection(Collection<Tekmovanje> tekmovanjeCollection) {
		this.tekmovanjeCollection = tekmovanjeCollection;
	}

	/**
	 * Retrieves the binary image data of the Objava.
	 *
	 * @return The binary image data of the Objava.
	 */
	public byte[] getImageData() {
		return imageData;
	}

	/**
	 * Sets the binary image data of the Objava.
	 *
	 * @param imageData The binary image data to set.
	 */
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}


	public Tekmovanje ustvariTekmovanje() {
		Tekmovanje tekmovanje = new Tekmovanje();
		tekmovanje.setObjava(this);
		return tekmovanje;
	}
}