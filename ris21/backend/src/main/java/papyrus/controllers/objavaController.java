package papyrus.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import papyrus.dao.tekmovanjeRepository;
import papyrus.dao.uporabnikRepository;
import papyrus.dao.objavaRepository;
import papyrus.dto.CommentRequest;
import papyrus.dto.ObjavaRequest;
import papyrus.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import papyrus.models.Objava;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/objava")
public class objavaController {

    @Autowired
    private objavaRepository objavaRepository;
    @Autowired
    private uporabnikRepository uporabnikRepository;

    @Autowired
    private tekmovanjeRepository tekmovanjeRepository;

    public objavaController(papyrus.dao.objavaRepository objavaRepository) {
        this.objavaRepository = objavaRepository;
    }

//-------------------GET-------------------

//    @GetMapping
//    public Iterable<Objava> vrniObjave() {
//        return objavaRepository.findAll();
//    }
@GetMapping
public Iterable<ObjavaRequest> vrniObjave() {
    Iterable<Objava> objave = objavaRepository.findAll();

    // Create a list to hold ObjavaDTO objects
    List<ObjavaRequest> objavaDTOList = new ArrayList<>();

    for (Objava objava : objave) {
        ObjavaRequest objavaDTO = new ObjavaRequest();
        objavaDTO.setId(objava.getId());
        objavaDTO.setText(objava.getText());
        objavaDTO.setImageData(objava.getImageData());
        objavaDTO.setUporabnikId(objava.getUporabnik().getId());
        objavaDTO.setName(objava.getUporabnik().getName());

        // Fetch and map comments for the objava
        Collection<CommentRequest> komentarDTOList = fetchAndMapKomentarCollection(objava);
        objavaDTO.setKomentarCollection(komentarDTOList);

        objavaDTO.setOcena(objava.getOcena());
        // Map other fields as needed

        // Add the mapped DTO to the list
        objavaDTOList.add(objavaDTO);
    }

    return objavaDTOList;
}

    private Collection<CommentRequest> fetchAndMapKomentarCollection(Objava objava) {
        Collection<Komentar> komentarCollection = objava.getKomentarCollection();
        Collection<CommentRequest> komentarDTOList = new ArrayList<>();

        for (Komentar komentar : komentarCollection) {
            CommentRequest komentarDTO = new CommentRequest();
            komentarDTO.setUserId(komentar.getUporabnik().getId());
//            komentarDTO.setId(komentar.getObjava().getId());
            komentarDTO.setPostId(objava.getId());
            komentarDTO.setContent(komentar.getText());
            komentarDTO.setUserName(komentar.getUporabnik().getName());


            // Add other fields as needed

            komentarDTOList.add(komentarDTO);
        }

        return komentarDTOList;
    }


    /**
     * Controller method to handle image retrieval for a specific Objava.
     *
     * @param id The unique identifier of the Objava.
     * @return The image data and appropriate HTTP headers.
     * @throws RuntimeException If the Objava is not found.
     */
    @GetMapping("/{id}")
    public Optional<Objava> vrniObjavo(@PathVariable(name = "id") Long id) {
        return objavaRepository.findById(id);
    }


    @GetMapping("/komentar/{objava_id}")
    public List<CommentRequest> vrniKomentarjeByObjavaId(@PathVariable(name = "objava_id") Long objavaId) {
        List<Komentar> komentarList = objavaRepository.vrniKomentarjeByObjavaId(objavaId);
        List<CommentRequest> komentarDTOList = fetchAndMapKomentarCollection(komentarList);
        return komentarDTOList;
    }

    private List<CommentRequest> fetchAndMapKomentarCollection(List<Komentar> komentarList) {
        List<CommentRequest> komentarDTOList = new ArrayList<>();

        for (Komentar komentar : komentarList) {
            CommentRequest komentarDTO = new CommentRequest();
            komentarDTO.setUserId(komentar.getUporabnik().getId());
//            komentarDTO.setId(komentar.getObjava().getId());
            komentarDTO.setPostId(komentar.getObjava().getId());
            komentarDTO.setContent(komentar.getText());
            komentarDTO.setUserName(komentar.getUporabnik().getName());
            komentarDTO.setId(komentar.getId());

            // Add other fields as needed

            komentarDTOList.add(komentarDTO);
        }

        return komentarDTOList;
    }


    @GetMapping("/id_objava/{id2}/{name}/{id}")
    public Iterable<Objava> vrniObjaveBySomething(@PathVariable(name = "id2") Long id2, @PathVariable(name = "name") String name, @PathVariable(name = "id") Long id) {
        return objavaRepository.vrniObjaveBySomething(id2, name, id);
    }

    /**
     * Controller method to handle image retrieval for a specific Objava.
     *
     * @param id The unique identifier of the Objava.
     * @return The image data and appropriate HTTP headers.
     * @throws RuntimeException If the Objava is not found.
     */
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        // Fetch the Objava by ID
        Objava objava = objavaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objava not found"));

        // Get the image data from the Objava entity
        byte[] imageData = objava.getImageData();

        // Set the appropriate headers for the image response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type

        // Return the image data with headers in the ResponseEntity
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

//-------------------DELETE-------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjava(@PathVariable Long id) {
        return objavaRepository.findById(id)
                .map(existingObjava -> {
                    objavaRepository.delete(existingObjava);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


//-------------------POST-------------------
    @PostMapping("/{id_uporabnik}")
    public Optional<Objava> dodajUporabnika(@RequestBody Objava objava, @PathVariable(name = "id_uporabnik") Long id) {
        return uporabnikRepository.findById(id).map(uporabnik -> {
            objava.setUporabnik(uporabnik);
            return objavaRepository.save(objava);
        });
    }
    @PostMapping
    public Objava dodajObjavo(@RequestBody Objava objava){
        return objavaRepository.save(objava);
    }
    @PostMapping("/{id_uporabnik}/ustvari-tekmovanje")
    public Optional<Tekmovanje> ustvariTekmovanje(
            @PathVariable(name = "id_uporabnik") Long id_uporabnik,
            @RequestBody Objava objava) {

        return uporabnikRepository.findById(id_uporabnik).map(uporabnik -> {
            // Shrani objavo
            Objava novaObjava = objavaRepository.save(objava);
            novaObjava.setUporabnik(uporabnik);
            // Ustvari tekmovanje in nastavi Uporabnika
            Tekmovanje tekmovanje = novaObjava.ustvariTekmovanje();

            // Shrani tekmovanje
            return tekmovanjeRepository.save(tekmovanje);
        });
    }

    /**
     * Controller method to handle image upload and format verification for a specific Objava.
     *
     * @param id   The unique identifier of the Objava.
     * @param file The multipart file containing the image data.
     * @return A message indicating the status of the image upload.
     * @throws RuntimeException If the Objava is not found or an error occurs during file processing.
     */
    @PostMapping("/imageUpload/{id}")
    public String uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Grab the Objava by ID
            Objava objava = objavaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Objava not found"));

            // Check if the file is empty
            if (file.isEmpty()) {
                return "File is empty!";
            }

            // Check if the file is an image (you can adjust this based on accepted formats)
            if (!isImage(file)) {
                return "Invalid file format! Only image files are allowed.";
            }

            // Set the image data from the uploaded file into the entity (which is just the Java table object)
            objava.setImageData(file.getBytes());
            System.out.println("Image data: " + Arrays.toString(objava.getImageData()));
            objavaRepository.save(objava);

            // Check if the file is bigger than 2MB
            if (file.getSize() > 2097152) {
                return "File size too large!";
            }

            return "Image uploaded successfully";

        } catch (RuntimeException e) {
            // If an exception occurs while finding the Objava or processing the file
            throw new RuntimeException(e);
        } catch (IOException e) {
            // If an exception occurs while reading the file
            throw new RuntimeException(e);
        }
    }

    // Method to check if the uploaded file is an image
    private boolean isImage(MultipartFile file) {
        // Define accepted image file extensions or content types (you can adjust this)
        String[] acceptedExtensions = { "jpg", "jpeg", "png", "gif" };
        String contentType = file.getContentType();
        if (contentType != null) {
            for (String extension : acceptedExtensions) {
                if (contentType.toLowerCase().contains(extension)) {
                    return true;
                }
            }
        }
        return false;
    }


//-------------------PUT-------------------
    @PutMapping("/{id}")
    public ResponseEntity<Objava> updateObjava(@PathVariable Long id, @RequestBody Objava updatedObjava) {
        return objavaRepository.findById(id).map(existingObjava -> {
            existingObjava.setText(updatedObjava.getText());

            Objava savedObjava = objavaRepository.save(existingObjava);
            return ResponseEntity.ok(savedObjava);
        }).orElse(ResponseEntity.notFound().build());
    }

}