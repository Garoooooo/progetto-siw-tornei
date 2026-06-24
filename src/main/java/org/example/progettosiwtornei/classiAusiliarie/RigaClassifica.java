package org.example.progettosiwtornei.classiAusiliarie;

import org.example.progettosiwtornei.entity.Squadra;

public class RigaClassifica implements Comparable<RigaClassifica>{
    private Integer punteggio;
    private Squadra squadra;

    public RigaClassifica(Squadra squadra, Integer punteggio) {
        this.punteggio = punteggio;
        this.squadra = squadra;
    }

    public Integer getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(Integer punteggio) {
        this.punteggio = punteggio;
    }

    public Squadra getSquadra() {
        return squadra;
    }

    public void setSquadra(Squadra squadra) {
        this.squadra = squadra;
    }


    @Override
    public int compareTo(RigaClassifica o) {
        return o.getPunteggio()-this.getPunteggio();
    }
}
