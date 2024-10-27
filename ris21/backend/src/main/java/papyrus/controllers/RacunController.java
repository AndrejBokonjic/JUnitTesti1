package papyrus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import papyrus.dao.racunRepository;
import papyrus.dao.storitevRepository;
import papyrus.dao.uporabnikRepository;
import papyrus.dto.RacunRequest;
import papyrus.models.Racun;
import papyrus.models.Storitev;
import papyrus.models.Uporabnik;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/racuni")
public class RacunController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private papyrus.dao.racunRepository racunRepository;

    @Autowired
    private papyrus.dao.uporabnikRepository uporabnikRepository;

    @Autowired
    private papyrus.dao.storitevRepository storitevRepository;

    // Endpoint za pridobitev vseh računov
    @GetMapping
    public Collection<Racun> vsiRacuni() {
        return racunRepository.findAll();
    }

    // Endpoint za pridobitev posameznega računa po ID-ju
    @GetMapping("/{racunId}")
    public ResponseEntity<Racun> racunPoId(@PathVariable Long racunId) {
        Optional<Racun> racunOptional = racunRepository.findById(racunId);

        return racunOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint za ustvarjanje novega računa
    @PostMapping("/dodaj/{uporabnikId}/{storitevId}")
    public ResponseEntity<Racun> dodajRacun(
            @PathVariable Long uporabnikId,
            @PathVariable Long storitevId) {

        Optional<Uporabnik> uporabnikOptional = uporabnikRepository.findById(uporabnikId);
        Optional<Storitev> storitevOptional = storitevRepository.findById(storitevId);

        if (uporabnikOptional.isEmpty() || storitevOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Uporabnik uporabnik = uporabnikOptional.get();
        Storitev storitev = storitevOptional.get();

        Racun racun = new Racun(uporabnik, storitev);
        racunRepository.save(racun);

        String vsebinaEmaila = "Pozdravljeni! Hvala za nakup storitve: " + storitev.getNaziv();
        posljiEPosto(uporabnik.getEmail(), "Potrdilo o nakupu", vsebinaEmaila);

        return ResponseEntity.ok(racun);
    }



    private void posljiEPosto(String prejemnik, String zadeva, String vsebina) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(prejemnik);
        msg.setSubject(zadeva);
        msg.setText(vsebina);

        javaMailSender.send(msg);
    }


}


