package org.example.progettosiwtornei.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.example.progettosiwtornei.classiAusiliarie.StatoPartita;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Partita implements Comparable<Partita> {
    @Id @GeneratedValue
    private Long id;
    private LocalDateTime dataOraPartita;
    private String luogo;
    private Integer goalsHome;
    private Integer goalsAway;
    private StatoPartita statoPartita;
    @ManyToOne
    private Torneo torneo;
    @ManyToOne      //per singola partita, ho solo una squadra locale, per la squadra locale invece ho molte partite
    private Squadra squadraCasa;
    @ManyToOne      //per singola partita, ho solo una squadra ospite, per la squadra ospite invece ho molte partite
    private Squadra squadraOspite;
    @ManyToOne
    @JsonIgnoreProperties("listaPartite")
    private Arbitro arbitro;
    @OneToMany(mappedBy = "partita")
    private List<Commento> listaCommenti;
    private int giornata;

    public Partita() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Partita partita = (Partita) o;
        return Objects.equals(dataOraPartita, partita.dataOraPartita) && Objects.equals(luogo, partita.luogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataOraPartita, luogo);
    }

    public int getGiornata() {
        return giornata;
    }

    public void setGiornata(int giornata) {
        this.giornata = giornata;
    }

    public List<Commento> getListaCommenti() {
        return listaCommenti;
    }

    public void setListaCommenti(List<Commento> commento) {
        this.listaCommenti = commento;
    }

    public Squadra getSquadraCasa() {
        return squadraCasa;
    }

    public void setSquadraCasa(Squadra squadraCasa) {
        this.squadraCasa = squadraCasa;
    }

    public Squadra getSquadraOspite() {
        return squadraOspite;
    }

    public void setSquadraOspite(Squadra squadraOspite) {
        this.squadraOspite = squadraOspite;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitroPartita) {
        this.arbitro = arbitroPartita;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataOraPartita() {
        return dataOraPartita;
    }

    public void setDataOraPartita(LocalDateTime data_e_ora_partita) {
        this.dataOraPartita = data_e_ora_partita;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Integer getGoalsHome() {
        return goalsHome;
    }

    public void setGoalsHome(Integer goalsHome) {
        this.goalsHome = goalsHome;
    }

    public Integer getGoalsAway() {
        return goalsAway;
    }

    public void setGoalsAway(Integer goalsAway) {
        this.goalsAway = goalsAway;
    }

    public StatoPartita getStatoPartita() {
        return statoPartita;
    }

    public void setStatoPartita(StatoPartita statoPartita) {
        this.statoPartita = statoPartita;
    }


    @Override
    public int compareTo(Partita that) {
        int confrontoGiornata = Integer.compare(this.getGiornata(), that.getGiornata());

        if (confrontoGiornata != 0) {
            return confrontoGiornata;
        }

        if (this.getDataOraPartita() == null && that.getDataOraPartita() == null) {
            return 0;
        }

        if (this.getDataOraPartita() == null) {
            return 1;
        }

        if (that.getDataOraPartita() == null) {
            return -1;
        }

        return this.getDataOraPartita().compareTo(that.getDataOraPartita());
    }
}
