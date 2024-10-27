package papyrus.dto;

import papyrus.models.Komentar;
import papyrus.models.Ocena;

import java.util.Collection;
import java.util.List;

public class ObjavaRequest {
    private Long id;
    private String text;
    private byte[] imageData;
    private Long uporabnikId;
    private String name;
    private Collection<CommentRequest> komentarCollection; // Modified type here


    // Add fields for ocena and komentarCollection
    private Collection<Ocena> ocena;

    public String getName() {
        return name;
    }

    public Collection<Ocena> getOcena() {
        return ocena;
    }

    public void setOcena(Collection<Ocena> ocena) {
        this.ocena = ocena;
    }

    public Collection<CommentRequest> getKomentarCollection() {
        return komentarCollection;
    }

    public void setKomentarCollection(Collection<CommentRequest> komentarCollection) {
        this.komentarCollection = komentarCollection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Long getUporabnikId() {
        return uporabnikId;
    }

    public void setUporabnikId(Long uporabnikId) {
        this.uporabnikId = uporabnikId;
    }
}

