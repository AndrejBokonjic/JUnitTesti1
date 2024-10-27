package papyrus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papyrus.dao.*;
import papyrus.models.*;
import papyrus.dto.OcenaRequest;

import java.util.*;

@RestController
@RequestMapping("/ocena")
public class ocenaController {
    @Autowired
    private ocenaRepository OcenaRepository;
    @Autowired
    private uporabnikRepository UporabnikRepository;
    @Autowired
    private objavaRepository ObjavaRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addGrade(@RequestBody OcenaRequest ocenaRequest) {
        // Fetch user and subject based on provided IDs
        Optional<Uporabnik> optionalUporabnik = UporabnikRepository.findById(ocenaRequest.getUporabnikId());
        Optional<Objava> optionalObjava = ObjavaRepository.findById(ocenaRequest.getObjavaId());

        if (optionalUporabnik.isPresent() && optionalObjava.isPresent()) {
            Uporabnik uporabnik = optionalUporabnik.get();
            Objava objava = optionalObjava.get();

            // Create a new grade
            Ocena ocena = new Ocena();
            ocena.setGrade(ocenaRequest.getGrade());
            ocena.setUporabnik(uporabnik);
            ocena.setObjava(objava);

            // Save the grade to the database
            OcenaRepository.save(ocena);

            return new ResponseEntity<>("Grade added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User or subject not found", HttpStatus.NOT_FOUND);
        }
    }
}
