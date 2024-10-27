package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Komentar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String text;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uporabnik_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Uporabnik uporabnik;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objava_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	Objava objava;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Uporabnik getUporabnik() {
		return uporabnik;
	}

	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}

	public Objava getObjava() {
		return objava;
	}

	public void setObjava(Objava objava) {
		this.objava = objava;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}