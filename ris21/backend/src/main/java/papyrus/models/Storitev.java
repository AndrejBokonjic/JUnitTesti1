package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Collection;

@Entity
public class Storitev {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String naziv;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "trgovina_umetnika_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Trgovina_Umetnika trgovina_umetnika;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Trgovina_Umetnika getTrgovinaUmetnika() {
		return trgovina_umetnika;
	}

	public void setTrgovinaUmetnika(Trgovina_Umetnika trgovina_umetnika) {
		this.trgovina_umetnika = trgovina_umetnika;
	}


}