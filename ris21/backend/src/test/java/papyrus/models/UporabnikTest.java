package papyrus.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import papyrus.controllers.objavaController;
import papyrus.controllers.uporabnikController;
import papyrus.dao.objavaRepository;
import papyrus.dao.uporabnikRepository;

import java.util.Optional;

@SpringBootTest
public class UporabnikTest {
    @Autowired
    private uporabnikRepository UporabnikRepository;

    @Autowired
    private uporabnikController UporabnikController;

    //@BeforeEach
    //void init() {
      //  UporabnikRepository.deleteAll();
    //}

    @Test
    public void deleteTest() {

        Uporabnik uporabnik = new Uporabnik(2L, "Ime");
        UporabnikRepository.save(uporabnik);

        Uporabnik returnedUporabnik = UporabnikController.vrniUporabnikeID(2L);
        Assertions.assertNotNull(returnedUporabnik, "Uporabnik obstaja");
        Assertions.assertEquals(2L, returnedUporabnik.getId());
        Assertions.assertEquals("Ime", returnedUporabnik.getName());

        UporabnikController.deleteUporabnik(2L);

        Uporabnik deletedUporabnik = UporabnikController.vrniUporabnikeID(2L);
        Assertions.assertNull(deletedUporabnik, "Uporabnik je zbrisan");
    }
    @Test
    public void updateUporabnikTest() {
        Uporabnik originalUporabnik = new Uporabnik(2L, "OriginalnoIme");

        Uporabnik savedOriginal = UporabnikRepository.save(originalUporabnik);
        Assertions.assertNotNull(savedOriginal, "Originalni Uporabnik je v bazi");

        Uporabnik updatedUporabnik = new Uporabnik();
        updatedUporabnik.setName("PosodobljenoIme");

        ResponseEntity<Uporabnik> response = UporabnikController.updateUporabnik(savedOriginal.getId(), updatedUporabnik);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status bo OK (200)");

        Uporabnik returnedUporabnik = response.getBody();
        Assertions.assertNotNull(returnedUporabnik, "Uporabnik ni null");
        Assertions.assertEquals("PosodobljenoIme", returnedUporabnik.getName(), "Uporabnik's name should be updated");

        Uporabnik savedUpdatedUporabnik = UporabnikRepository.findById(savedOriginal.getId()).orElse(null);
        Assertions.assertNotNull(savedUpdatedUporabnik, "Uporabnik obstaja po posodobitvi");
        Assertions.assertEquals("PosodobljenoIme", savedUpdatedUporabnik.getName(), "Ime je posodobljeno");
    }
    @Test
    public void registracijaUporabnikaTest() {
        // Step 1: Register a new user
        Uporabnik newUser = new Uporabnik();
        newUser.setId(1L);
        newUser.setName("User1");
        newUser.setEmail("user1@example.com");

        ResponseEntity<String> response = UporabnikController.registerUser(newUser);

        // Step 2: Verify that the user was registered successfully
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK (200)");
        Assertions.assertEquals("User registered successfully", response.getBody(), "Success message should be returned");

        // Step 3: Attempt to register a user with the same email
        Uporabnik duplicateUser = new Uporabnik();
        duplicateUser.setId(2L);
        duplicateUser.setName("User2");
        duplicateUser.setEmail("user1@example.com");  // Duplicate email

        ResponseEntity<String> duplicateResponse = UporabnikController.registerUser(duplicateUser);

        // Step 4: Verify that the registration fails with a BAD_REQUEST status
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, duplicateResponse.getStatusCode(), "Response status should be BAD_REQUEST (400)");
        Assertions.assertEquals("Email is already in use", duplicateResponse.getBody(), "Duplicate email error message should be returned");
    }

}
