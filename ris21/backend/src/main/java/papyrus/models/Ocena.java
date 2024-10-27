package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Ocena {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int grade;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
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
}