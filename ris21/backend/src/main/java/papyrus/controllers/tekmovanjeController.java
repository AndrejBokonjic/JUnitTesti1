package papyrus.controllers;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papyrus.dao.objavaRepository;
import papyrus.dao.tekmovanjeRepository;
import papyrus.dao.uporabnikRepository;
import papyrus.dao.uporabnikTekmovanjeRepository;
import papyrus.models.Objava;
import papyrus.models.Tekmovanje;
import papyrus.models.Uporabnik;
import papyrus.models.UporabnikTekmovanje;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tekmovanje")
public class tekmovanjeController {

    @Autowired
    private tekmovanjeRepository tekmovanjeRepository;

    @Autowired
    private uporabnikRepository uporabnikRepository;

    @Autowired
    private uporabnikTekmovanjeRepository uporabnikTekmovanjeRepository;

    @Autowired
    private papyrus.dao.objavaRepository objavaRepository;

    public tekmovanjeController(tekmovanjeRepository tekmovanjeRepository){
        this.tekmovanjeRepository = tekmovanjeRepository;
    }

    @GetMapping
    public Iterable<Tekmovanje> pridobiPodatkeOTekmovanju(){
        return tekmovanjeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Tekmovanje>> pridobiPodatkeOTekmovanju(@PathVariable Long id) {
        List<Tekmovanje> tekmovanja = tekmovanjeRepository.pridobiPodatkeOTekmovanju(id);
        return ResponseEntity.ok(tekmovanja);
    }

    @PutMapping("{idTekmovanja}")
    public ResponseEntity<Tekmovanje> urediObjavoTekmovanja(
            @PathVariable Long idTekmovanja,
            @RequestBody String novoBesedilo) {

        Optional<Tekmovanje> tekmovanjeOptional = tekmovanjeRepository.findById(idTekmovanja);

        if (tekmovanjeOptional.isEmpty()) {
            // Če Tekmovanje ni najdeno, vrni ustrezno kodo odgovora (npr. 404 Not Found)
            return ResponseEntity.notFound().build();
        }

        Tekmovanje tekmovanje = tekmovanjeOptional.get();
        Objava objava = tekmovanje.getObjava();

        if (objava == null) {
            // Če objava ni povezana s tekmovanjem, vrni ustrezno kodo odgovora (npr. 404 Not Found)
            return ResponseEntity.notFound().build();
        }

        // Posodobi besedilo objave
        objava.setText(novoBesedilo);
        objavaRepository.save(objava);

        // Vrni posodobljeno tekmovanje
        return ResponseEntity.ok(tekmovanje);
    }


    @GetMapping("/{tekmovanjeId}/uporabniki")
    public ResponseEntity<List<Uporabnik>> pridobiUporabnikeZaTekmovanje(@PathVariable Long tekmovanjeId) {
        Optional<Tekmovanje> tekmovanjeOptional = tekmovanjeRepository.findById(tekmovanjeId);

        if (tekmovanjeOptional.isEmpty()) {
            // Če Tekmovanje ni najdeno, vrni ustrezno kodo odgovora (npr. 404 Not Found)
            return ResponseEntity.notFound().build();
        }

        Tekmovanje tekmovanje = tekmovanjeOptional.get();
        List<Uporabnik> uporabniki = tekmovanje.getUporabniki();

        return ResponseEntity.ok(uporabniki);
    }

    @PostMapping("/{tekmovanjeId}/dodajUporabnika/{uporabnikId}")
    public ResponseEntity<Tekmovanje> dodajUporabnikaNaTekmovanje(
            @PathVariable Long tekmovanjeId,
            @PathVariable Long uporabnikId) {

        Optional<Tekmovanje> tekmovanjeOptional = tekmovanjeRepository.findById(tekmovanjeId);
        Optional<Uporabnik> uporabnikOptional = uporabnikRepository.findById(uporabnikId);

        if (tekmovanjeOptional.isEmpty() || uporabnikOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Tekmovanje tekmovanje = tekmovanjeOptional.get();
        Uporabnik uporabnik = uporabnikOptional.get();

        // Poskusite inicializirati leno naložene podatke
        Hibernate.initialize(tekmovanje.getUporabniki());
        Hibernate.initialize(uporabnik.getTekmovanja());

        // Dodaj uporabnika na tekmovanje
        tekmovanje.getUporabniki().add(uporabnik);
        tekmovanjeRepository.save(tekmovanje);

        // Vrni posodobljeno tekmovanje
        return ResponseEntity.ok(tekmovanje);
    }
}

