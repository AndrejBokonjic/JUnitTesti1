package papyrus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import papyrus.models.Tekmovanje;
import papyrus.models.Uporabnik;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import papyrus.models.UporabnikTekmovanje;

public interface uporabnikTekmovanjeRepository extends JpaRepository<UporabnikTekmovanje, Long> {
    @Query("select t from Tekmovanje t join t.uporabniki u where u = :uporabnik")
    List<Tekmovanje> findByUporabnik(Uporabnik uporabnik);

    @Query("select u from Uporabnik u join u.tekmovanja t where t = :tekmovanje")
    List<Uporabnik> findByTekmovanje(Tekmovanje tekmovanje);
}
