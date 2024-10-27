package papyrus.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name="tekmovanje")
public class Tekmovanje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objava_id", columnDefinition = "BIGINT")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Objava objava;


	@ManyToMany(mappedBy = "tekmovanja")
	@JsonIgnore
	private List<Uporabnik> uporabniki;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Objava getObjava() {
		return objava;
	}

	public void setObjava(Objava objava) {
		this.objava = objava;
	}
	public Tekmovanje(Long id) {
		this.id = id;
	}
	public Tekmovanje(){}

	public List<Uporabnik> getUporabniki() {
		return uporabniki;
	}

}