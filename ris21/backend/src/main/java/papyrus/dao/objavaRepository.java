package papyrus.dao;

import org.springframework.data.jpa.repository.Query;
import papyrus.models.Komentar;
import papyrus.models.Objava;
import org.springframework.data.repository.CrudRepository;
import papyrus.models.Uporabnik;
import papyrus.models.Zbirka;

import java.util.List;
import java.util.Optional;

public interface objavaRepository extends CrudRepository<Objava, Long> {
    @Query("select s from Objava s, Zbirka z where s.zbirka=z and z.id=?1 and z.name=?2 and s.id=?3")
    List<Objava> vrniObjaveBySomething(Long id2, String name, Long id);

    @Query("select h from Objava h where h.id=?1")
    List<Uporabnik> vrniObjaveID(Long id);

    List<Objava> findByZbirka(Zbirka zbirka);

    @Query("SELECT k FROM Komentar k WHERE k.objava.id = ?1")
    List<Komentar> vrniKomentarjeByObjavaId(Long id);

    @Query("SELECT o FROM Objava o WHERE o.uporabnik.id = ?1")
    List<Objava> vrniObjavePoUporabnikuId(Long uporabnikId);

}
