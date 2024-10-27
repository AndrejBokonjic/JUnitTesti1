package papyrus.controllers;

import jakarta.persistence.Column;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import papyrus.dao.storitevRepository;
import papyrus.dao.uporabnikRepository;

import papyrus.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import papyrus.models.Objava;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/trgovinaUporabnika")
public class TrgovinaUporabnikaController {

    private papyrus.dao.trgovinaUporabnikaRepository trgovinaUporabnikaRepository;

    @Autowired
    private storitevRepository storitevRepository;

    @Autowired
    public TrgovinaUporabnikaController(papyrus.dao.trgovinaUporabnikaRepository trgovinaUporabnikaRepository){
        this.trgovinaUporabnikaRepository = trgovinaUporabnikaRepository;
    }

    @Column(name = "racun_id")
    private int racun_id;



    @GetMapping
    public List<Trgovina_Umetnika> vrniTrgovinoUmetnika(){
        return trgovinaUporabnikaRepository != null ? (List<Trgovina_Umetnika>) trgovinaUporabnikaRepository.findAll() : Collections.emptyList();

    }

    @Autowired
    private uporabnikRepository uporabnikRepository;

    /*
    primer kak bi dodal trgovino:
    {
       "name": "Trgovaaainica",
    }

    url: http://localhost:8080/trgovinaUporabnika/52
    (more obstaja uporabnik z id 52)
     */

    @PostMapping("/{id_uporabnik}")
    public ResponseEntity<String> dodajTrgovinoUmetnika(@RequestBody Trgovina_Umetnika trgovina_umetnika, @PathVariable(name = "id_uporabnik") Long id) {
        Optional<Uporabnik> optionalUporabnik = uporabnikRepository.findById(id);

        if (optionalUporabnik.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Uporabnik z ID " + id + " ni bil najden.");
        }

        Uporabnik uporabnik = optionalUporabnik.get();
        trgovina_umetnika.setUporabnik(uporabnik);

        try {
            Trgovina_Umetnika savedTrgovinaUmetnika = trgovinaUporabnikaRepository.save(trgovina_umetnika);
            return ResponseEntity.status(HttpStatus.CREATED).body("Trgovina umetnika uspešno dodana. ID trgovine umetnika: " + savedTrgovinaUmetnika.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Prišlo je do napake pri shranjevanju trgovine umetnika." + e.getMessage());
        }
    }

    /*
    primer uporabe:

    url: http://localhost:8080/trgovinaUporabnika/205/storitve

    {
        "naziv": "ImeStoritve"
    }


     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{trgovinaId}/storitve")
    public ResponseEntity<String> dodajStoritev(@PathVariable(name = "trgovinaId") Long trgovinaId, @RequestBody Storitev storitev) {
        Optional<Trgovina_Umetnika> optionalTrgovina = trgovinaUporabnikaRepository.findById(trgovinaId);


        if (optionalTrgovina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trgovina umetnika z ID " + trgovinaId + " ni bila najdena.");
        }
        storitev.setId(racun_id);

        Trgovina_Umetnika trgovinaUmetnika = optionalTrgovina.get();
        storitev.setTrgovinaUmetnika(trgovinaUmetnika);
        trgovinaUmetnika.dodajStoritev(storitev);

        try {
            trgovinaUporabnikaRepository.save(trgovinaUmetnika);
            return ResponseEntity.status(HttpStatus.CREATED).body("Storitev uspešno dodana v trgovino umetnika.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Prišlo je do napake pri shranjevanju storitve v trgovino umetnika.");
        }
    }

    /*
    url: http://localhost:8080/trgovinaUporabnika/103

    {
        "name": "imeeee"
    }

     */

    @PutMapping("/{id}")
    public ResponseEntity<Trgovina_Umetnika> urediTrgovinoUmetnika(@PathVariable Long id, @RequestBody Trgovina_Umetnika updatedTrgovina) {
        Optional<Trgovina_Umetnika> existingTrgovina = trgovinaUporabnikaRepository.findById(id);

        if (existingTrgovina.isPresent()) {
            Trgovina_Umetnika trgovinaToUpdate = existingTrgovina.get();
            // Posodobite želene lastnosti trgovine
            trgovinaToUpdate.setName(updatedTrgovina.getName());
            // Dodajte morebitne dodatne posodobitve

            // Shrani posodobljeno trgovino
            Trgovina_Umetnika savedTrgovina = trgovinaUporabnikaRepository.save(trgovinaToUpdate);
            return ResponseEntity.ok(savedTrgovina);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> izbrisiTrgovinoUmetnika(@PathVariable Long id) {
        Optional<Trgovina_Umetnika> trgovinaUmetnika = trgovinaUporabnikaRepository.findById(id);

        if (trgovinaUmetnika.isPresent()) {
            trgovinaUporabnikaRepository.delete(trgovinaUmetnika.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/uporabnik/{uporabnikId}/trgovina")
    public ResponseEntity<?> getTrgovinaIdByUporabnikId(@PathVariable Long uporabnikId) {
        Optional<Trgovina_Umetnika> trgovina = trgovinaUporabnikaRepository.findByUporabnikId(uporabnikId);
        if (trgovina.isPresent()) {
            return ResponseEntity.ok(trgovina.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{trgovinaId}/storitve")
    public ResponseEntity<List<Storitev>> getStoritveByTrgovinaId(@PathVariable Long trgovinaId) {
        // Pridobi seznam storitev glede na ID trgovine
        List<Storitev> storitve = storitevRepository.vrniStoritvePoTrgoviniId(trgovinaId);

        if (!storitve.isEmpty()) {
            return ResponseEntity.ok(storitve);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
