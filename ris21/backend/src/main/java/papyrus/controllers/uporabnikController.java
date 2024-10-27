package papyrus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import papyrus.dao.uporabnikRepository;
import papyrus.dao.zbirkaRepository;
import papyrus.dto.LoginRequest;
import papyrus.models.Objava;
import papyrus.models.Uporabnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import papyrus.models.Zbirka;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/uporabnik")
public class uporabnikController {

    private uporabnikRepository uporabnikRepository;
    private zbirkaRepository zbirkaRepository;

    @Autowired
    private papyrus.dao.objavaRepository objavaRepository;

    @Autowired
    public uporabnikController(papyrus.dao.uporabnikRepository uporabnikRepository) {
        this.uporabnikRepository = uporabnikRepository;
    }

    @Autowired
    public void zbirkaController(papyrus.dao.zbirkaRepository zbirkaRepository) {
        this.zbirkaRepository = zbirkaRepository;
    }

    @GetMapping
    public List<Uporabnik> vrniUporabnike() {
        return (List<Uporabnik>) uporabnikRepository.findAll();
    }

    @GetMapping("/ime/{id}")
    public Iterable<Uporabnik> vrniUporabnikeIme(@PathVariable(name = "id") String name){
        return uporabnikRepository.vrniUporabnikeIme(name);
    }

    /**
     * Controller method to check if a user (Uporabnik) already has a specific Zbirka.
     *
     * @param id         The unique identifier of the user (Uporabnik).
     * @param uporabnikId The unique identifier of the Zbirka to check for.
     * @return True if the user has the specified Zbirka, false otherwise.
     * @throws RuntimeException If the user (Uporabnik) is not found.
     */
    @GetMapping("/{id}/imaZbirko/{uporabnikId}")
    public boolean uporabnikImaZbirko(@PathVariable(name = "id") Long id, @PathVariable(name = "uporabnikId") Long uporabnikId) {
        Uporabnik uporabnik = uporabnikRepository.findById(id).orElse(null);
        System.out.println(uporabnik);

        // Check if uporabnik exists
        if (uporabnik == null) {
            return false; // In case uporabnik doesn't exist...
        }

        // Check if uporabnik already has a zbirka with uporabnikId
        List<Zbirka> zbirkUporabnika = zbirkaRepository.vrniZbirkeUporabnikaID(id, uporabnikId);
        System.out.println(zbirkUporabnika);

        return !zbirkUporabnika.isEmpty();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUporabnik(@PathVariable Long id) {
        return uporabnikRepository.findById(id)
                .map(existingUporabnik -> {
                    uporabnikRepository.delete(existingUporabnik);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Uporabnik> updateUporabnik(@PathVariable Long id, @RequestBody Uporabnik updatedUporabnik) {
        return uporabnikRepository.findById(id).map(existingUporabnik -> {
            existingUporabnik.setName(updatedUporabnik.getName());

            Uporabnik savedUporabnik = uporabnikRepository.save(existingUporabnik);
            return ResponseEntity.ok(savedUporabnik);
        }).orElse(ResponseEntity.notFound().build());
    }

    // -------------------ANDREJ-------------------
    @GetMapping("/{id}")
    public Uporabnik vrniUporabnikeID(@PathVariable(name = "id") Long id){
        return uporabnikRepository.vrniUporabnikeID(id);
    }

    @GetMapping("/id_uporabnikov/{id}")
    public Iterable<Uporabnik> vrniUporabnikeGledeNaObjavo(@PathVariable(name = "id") Long id){
        return uporabnikRepository.vrniUporabnikeGledeNaObjavo(id);
    }

    @GetMapping("/id_uporabnikov/{id}/{id2}")
    public Iterable<Uporabnik> vrniUporabnikeGledeNaObjavoInID(@PathVariable(name = "id") Long id, @PathVariable(name = "id2") Long id2) {
        return uporabnikRepository.vrniUporabnikeGledeNaObjavoInID(id,id2);
    }

    @GetMapping("/id_uporabnikov/{id}/{id2}/{text}")
    public Iterable<Uporabnik>vrniUporabnikeGledeNaObjavoInIDinText(@PathVariable(name = "id") Long id, @PathVariable(name = "id2") Long id2,@PathVariable(name = "text") String text) {
        return uporabnikRepository.vrniUporabnikeGledeNaObjavoInIDinText(id,id2,text);
    }

    @PostMapping
    public Uporabnik dodajUporabnika(@RequestBody Uporabnik uporabnik){
        return uporabnikRepository.save(uporabnik);
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> registerUser(@RequestBody Uporabnik user) {
        // Check if the email is already registered
        if (uporabnikRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
        }

        uporabnikRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Uporabnik> loginUser(@RequestBody LoginRequest loginRequest) {
        // Find the user by email
        Optional<Uporabnik> optionalUser = uporabnikRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isPresent()) {
            Uporabnik user = optionalUser.get();

            // Check if the password matches (you should use a proper password hashing library)
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/objave")
    public List<Objava> getObjaveUporabnika(@PathVariable Long id) {
        return objavaRepository.vrniObjavePoUporabnikuId(id);
    }
}


