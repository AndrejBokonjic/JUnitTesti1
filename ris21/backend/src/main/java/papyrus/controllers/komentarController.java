package papyrus.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import papyrus.dao.*;
import papyrus.dto.CommentRequest;
import papyrus.models.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/komentar")
public class komentarController {
    @Autowired
    private komentarRepository KomentarRepository;
    @Autowired
    private objavaRepository ObjavaRepository;
    @Autowired
    private uporabnikRepository UporabnikRepository;

    @GetMapping
    public Iterable<Komentar> pridobiPodatkeOKomentarju(){
        return KomentarRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestBody CommentRequest commentRequest) {
        // Fetch user and post based on provided IDs
        Optional<Uporabnik> optionalUser = UporabnikRepository.findById(commentRequest.getUserId());
        Optional<Objava> optionalPost = ObjavaRepository.findById(commentRequest.getPostId());

        if (optionalUser.isPresent() && optionalPost.isPresent()) {
            Uporabnik user = optionalUser.get();
            Objava post = optionalPost.get();

            // Create a new comment
            Komentar comment = new Komentar();
            comment.setText(commentRequest.getContent());
            comment.setUporabnik(user);
            comment.setObjava(post);

            // Save the comment to the database
            KomentarRepository.save(comment);

            return new ResponseEntity<>("Comment added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User or post not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        // Check if the comment exists
        Optional<Komentar> optionalComment = KomentarRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            // Comment found, delete it
            KomentarRepository.deleteById(commentId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } else {
            // Comment not found
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }

}
