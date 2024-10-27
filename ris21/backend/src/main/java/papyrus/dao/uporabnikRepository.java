package papyrus.dao;

import papyrus.models.Uporabnik;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface uporabnikRepository extends CrudRepository<Uporabnik, Long> {
    @Query("select h from Uporabnik h where h.id=?1")
    Uporabnik vrniUporabnikeID(Long id);

    @Query("select h from Uporabnik h, Objava s where s.uporabnik = h and h.id=?1")
    List<Uporabnik> vrniUporabnikeGledeNaObjavo(Long id);

    @Query("select h from Uporabnik h, Objava s where s.uporabnik = h and h.id=?1 and s.id=?2")
    List<Uporabnik> vrniUporabnikeGledeNaObjavoInID(Long id,Long id2);

    @Query("select h from Uporabnik h, Objava s where s.uporabnik = h and h.id=?1 and s.id=?2 and s.text=?3")
    List<Uporabnik> vrniUporabnikeGledeNaObjavoInIDinText(Long id,Long id2,String text);

    //Method that returns uporabnik by their name
    @Query("select h from Uporabnik h where h.name=?1")
    List<Uporabnik> vrniUporabnikeIme(String name);

    Optional<Uporabnik> findByEmail(String email);
}


