package papyrus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papyrus.dao.objavaRepository;
import papyrus.dao.uporabnikRepository;
import papyrus.dao.zbirkaRepository;
import papyrus.models.Objava;
import papyrus.models.Uporabnik;
import papyrus.models.Zbirka;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/zbirka")
public class zbirkaController {

    @Autowired
    private zbirkaRepository zbirkaRepository;
    @Autowired
    private objavaRepository objavaRepository;
    @Autowired
    private papyrus.dao.uporabnikRepository uporabnikRepository;

    public zbirkaController(papyrus.dao.zbirkaRepository zbirkaRepository){
        this.zbirkaRepository = zbirkaRepository;
    }
    // ---------------- GET -----------------

    @GetMapping
    public Iterable<Zbirka> vrniZbirke() {
        return zbirkaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Zbirka> vrniZbirke(@PathVariable(name = "id") Long id) {
        return zbirkaRepository.findById(id);
    }

    @GetMapping("/{zbirkaId}/filterObjave")
    public ResponseEntity<List<Objava>> filterObjaveInZbirka(
            @PathVariable Long zbirkaId,
            @RequestParam(required = false) String keyword) {

        // Retrieve the Zbirka
        Zbirka zbirka = zbirkaRepository.findById(zbirkaId).orElse(null);

        if (zbirka == null) {
            return ResponseEntity.notFound().build();
        }

        // Retrieve Objave within the Zbirka
        List<Objava> objaveList = objavaRepository.findByZbirka(zbirka);

        // Filter Objave based on the keyword
        if (keyword != null && !keyword.isEmpty()) {
            objaveList = objaveList.stream()
                    .filter(objava -> objava.getText() != null && objava.getText().contains(keyword))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(objaveList);
    }


//-------------------POST-------------------

    @PostMapping("/createZbirka")
    public ResponseEntity<Zbirka> createZbirka(@RequestBody Zbirka zbirka) {
        try {
            // Find the maximum zbirkaId from the database
            Long maxZbirkaId = zbirkaRepository.findMaxZbirkaId();

            // Calculate the next zbirkaId (increment by one)
            Long nextZbirkaId = (maxZbirkaId != null) ? maxZbirkaId + 1 : 1;

            // Set the next zbirkaId to the Zbirka object
            zbirka.setId(nextZbirkaId);

            // Save the Zbirka to the database
            Zbirka createdZbirka = zbirkaRepository.save(zbirka);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdZbirka);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to assign an Uporabnik to an existing Zbirka
    @PostMapping("/{zbirkaId}/assignUporabnik/{uporabnikId}")
    public ResponseEntity<?> assignUporabnikToZbirka(
            @PathVariable Long zbirkaId,
            @PathVariable Long uporabnikId) {

        // Check if both Zbirka and Uporabnik exist
        Zbirka zbirka = zbirkaRepository.findById(zbirkaId).orElse(null);
        Uporabnik uporabnik = uporabnikRepository.findById(uporabnikId).orElse(null);

        if (zbirka == null || uporabnik == null) {
            return ResponseEntity.notFound().build();
        }

        // Assign the Uporabnik to the Zbirka
        zbirka.setUporabnik(uporabnik);
        zbirkaRepository.save(zbirka);

        return ResponseEntity.ok("Uporabnik assigned to Zbirka successfully");
    }


    // Endpoint to assign an Objave to an existing Zbirka
    @PostMapping("/{zbirkaId}/assignObjave/{objaveId}")
    public ResponseEntity<?> assignObjaveToZbirka(
            @PathVariable Long zbirkaId,
            @PathVariable Long objaveId) {

        // Check if both Zbirka and Objave exist
        Zbirka zbirka = zbirkaRepository.findById(zbirkaId).orElse(null);
        Objava objave = objavaRepository.findById(objaveId).orElse(null);

        if (zbirka == null || objave == null) {
            return ResponseEntity.notFound().build();
        }

        // Assign the Objave to the Zbirka
        objave.setZbirka(zbirka); // Assuming Objave has a 'zbirka' property
        objavaRepository.save(objave);

        return ResponseEntity.ok("Objave assigned to Zbirka successfully");
    }




}
