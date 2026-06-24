package org.example.progettosiwtornei.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import java.util.Objects;

@Entity
public class Squadra {

    @Id    @GeneratedValue
    private Long id;
    private String nome;
    private String citta;
    private Integer annoFondazione;
    @OneToMany(mappedBy = "squadra")
    private List<Giocatore> giocatori;
    @ManyToMany(mappedBy = "squadrePartecipanti")
    private List<Torneo> torneiIscritti;
    @OneToMany(mappedBy = "squadraCasa")
    private List<Partita> partiteInCasa;
    @OneToMany(mappedBy = "squadraOspite")
    private List<Partita> partiteInTrasferta;





    public Squadra() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Squadra squadra = (Squadra) o;
        return Objects.equals(nome, squadra.nome) && Objects.equals(citta, squadra.citta) && Objects.equals(annoFondazione, squadra.annoFondazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, citta, annoFondazione);
    }

    public List<Partita> getPartiteInCasa() {
        return partiteInCasa;
    }

    public void setPartiteInCasa(List<Partita> partiteInCasa) {
        this.partiteInCasa = partiteInCasa;
    }

    public List<Partita> getPartiteInTrasferta() {
        return partiteInTrasferta;
    }

    public void setPartiteInTrasferta(List<Partita> partiteInTrasferta) {
        this.partiteInTrasferta = partiteInTrasferta;
    }

    public List<Torneo> getTorneiIscritti() {    // per ottenere tutta la lista di tornei a cui la squadra partecipa
        return torneiIscritti;
    }

    public void setTorneiIscritti(List<Torneo> torneiIscritti) {    //per impostare una lista (che magari possiedo gia formata) e metterla come lista di tornei della squadra.
        this.torneiIscritti = torneiIscritti;
    }

    public List<Giocatore> getGiocatori() {      // per ottenere tutta la lista di giocatori della squadra
        return giocatori;
    }

    public void setGiocatori(List<Giocatore> giocatori) {     //per impostare una lista (che magari possiedo gia formata) e metterla come lista di giocatori della squadra.
        this.giocatori = giocatori;
    }

    public void setAnnoFondazione(Integer annoFondazione) {
        this.annoFondazione = annoFondazione;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnoFondazione() {
        return annoFondazione;
    }

    public String getCitta() {
        return citta;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }


}
