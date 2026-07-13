package org.example.progettosiwtornei.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity
public class Arbitro {

    @Id @GeneratedValue
    private Long id;
    private String nome;
    private String cognome;
    private Long codiceArbitrale;
    @OneToMany(mappedBy = "arbitro")
    @JsonIgnoreProperties("arbitro")
    private List<Partita> listaPartite;

    public Arbitro() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Arbitro arbitro = (Arbitro) o;
        return Objects.equals(codiceArbitrale, arbitro.codiceArbitrale);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codiceArbitrale);
    }

    public List<Partita> getListaPartite() {
        return listaPartite;
    }

    public void setListaPartite(List<Partita> listaPartite) {
        this.listaPartite = listaPartite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Long getCodiceArbitrale() {
        return codiceArbitrale;
    }

    public void setCodiceArbitrale(Long codiceArbitrale) {
        this.codiceArbitrale = codiceArbitrale;
    }


}
