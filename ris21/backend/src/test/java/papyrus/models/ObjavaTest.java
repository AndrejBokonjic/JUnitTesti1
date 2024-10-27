package papyrus.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import papyrus.controllers.objavaController;
import papyrus.dao.objavaRepository;
import papyrus.dao.tekmovanjeRepository;
import papyrus.dao.uporabnikRepository;

import java.util.Optional;

@SpringBootTest
public class ObjavaTest {
    @Autowired
    private objavaRepository ObjavaRepository;

    @Autowired
    private objavaController ObjavaController;

    @Autowired
    private tekmovanjeRepository TekmovanjeRepository;

    @Autowired
    private uporabnikRepository UporabnikRepository;

   // @BeforeEach
    //void init() {
      //  ObjavaRepository.deleteAll();
    //}

    @Test
    public void postTest() {
        Assertions.assertEquals(0, ObjavaRepository.count());
        ObjavaController.dodajObjavo(new Objava(14L, "Ime"));
        Assertions.assertEquals(1, ObjavaRepository.count());
    }

    @Test
    public void getObjava() {
        Objava objava = new Objava(17L, "Ime");
        ObjavaRepository.save(objava);

        Optional<Objava> returnedObjava = ObjavaController.vrniObjavo(17L);

        Assertions.assertTrue(returnedObjava.isPresent(), "Objava should be present");
        Assertions.assertEquals(17L, returnedObjava.get().getId());
        Assertions.assertEquals("Ime", returnedObjava.get().getText());
    }
}
