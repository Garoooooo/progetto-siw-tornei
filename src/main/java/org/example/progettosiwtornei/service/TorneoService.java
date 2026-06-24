package org.example.progettosiwtornei.service;
import org.example.progettosiwtornei.classiAusiliarie.RigaClassifica;
import org.example.progettosiwtornei.classiAusiliarie.StatoPartita;
import org.example.progettosiwtornei.entity.Partita;
import org.example.progettosiwtornei.entity.Squadra;
import org.example.progettosiwtornei.entity.Torneo;
import org.example.progettosiwtornei.repository.PartitaRepository;
import org.example.progettosiwtornei.repository.SquadraRepository;
import org.example.progettosiwtornei.repository.TorneoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TorneoService {
    private final TorneoRepository torneoRepository;
    private final PartitaRepository partitaRepository;
    private final SquadraRepository squadraRepository;

    public TorneoService(TorneoRepository torneoRepository, PartitaRepository partitaRepository, SquadraRepository squadraRepository) {
        this.torneoRepository = torneoRepository;
        this.partitaRepository = partitaRepository;
        this.squadraRepository = squadraRepository;
    }

    public void aggiungiSquadraAlTorneo(Long idTorneo, Squadra squadra) {
        Torneo torneo = this.torneoRepository.findById(idTorneo).orElse(null);

        if (torneo != null && squadra != null) {
            if (!torneo.getSquadrePartecipanti().contains(squadra)) {
                torneo.getSquadrePartecipanti().add(squadra);
                this.torneoRepository.save(torneo);
            }
        }
    }

    public List<Torneo> getAllTornei() {
        return this.torneoRepository.findAll();
    }

    public Torneo getTorneoById(Long id) {
        return this.torneoRepository.findById(id).orElse(null);
    }

    public List<Squadra> getSquadrePartecipantiAlTorneo(Long idTorneo) {
        Torneo torneo = this.torneoRepository.findById(idTorneo).orElse(null);
        if (torneo == null) {
            return null;
        }
        return torneo.getSquadrePartecipanti();
    }

    public void aggiungiPartita(Partita partita){
        this.partitaRepository.save(partita);
    }

    public List<Partita> getPartiteTorneo(Long id) {
        Torneo torneo = this.torneoRepository.findById(id).orElse(null);
        if (torneo == null)
            return null;
        else {
            List<Partita> list = torneo.getListaPartite();
            Collections.sort(list);
            return list;
        }
    }

    public void rimuoviSquadraDalTorneo(Long idTorneo, Long idSquadra) {
        Torneo torneo = this.torneoRepository.findById(idTorneo).orElse(null);
        Squadra squadraDaEliminare = this.squadraRepository.findById(idSquadra).orElse(null);

        if (torneo != null && squadraDaEliminare != null) {

            List<Squadra> listaSquadrePartecipantiAggiornata = torneo.getSquadrePartecipanti();

            if (listaSquadrePartecipantiAggiornata.contains(squadraDaEliminare)) {

                // rimuovo tutte le partite in cui compare la squadra da eliminare,
                // così calendario e classifica si aggiornano correttamente
                List<Partita> partiteTorneoAggiornate = torneo.getListaPartite();

                List<Partita> partiteDaEliminare = new ArrayList<>();

                for (Partita p : partiteTorneoAggiornate) {
                    if (p.getSquadraCasa().equals(squadraDaEliminare) ||
                            p.getSquadraOspite().equals(squadraDaEliminare)) {

                        //lista parallela per evitare il concurrent modification error
                        partiteDaEliminare.add(p);
                    }
                }


                //remove elimina l oggetto dalla lista
                //delete partitaRepository.delete elimina la partita dal database
                for (Partita p : partiteDaEliminare) {
                    partiteTorneoAggiornate.remove(p);
                    this.partitaRepository.delete(p);
                }

                //qui non faccio anche il delete perchè la squadra nel database la voglio tenere, magari per usarla in un altro torneo
                listaSquadrePartecipantiAggiornata.remove(squadraDaEliminare);

                torneo.setListaPartite(partiteTorneoAggiornate);
                torneo.setSquadrePartecipanti(listaSquadrePartecipantiAggiornata);

                this.torneoRepository.save(torneo);
            }
        }
    }

    public List<RigaClassifica> getClassifica(Long idTorneo) {

        // Prendo tutte le squadre iscritte al torneo
        List<Squadra> listaSquadre = getSquadrePartecipantiAlTorneo(idTorneo);

        // Creo la lista che conterrà le righe della classifica
        List<RigaClassifica> classifica = new ArrayList<>();

        // Prendo tutte le partite del torneo
        List<Partita> listaPartite = getPartiteTorneo(idTorneo);

        // Prima fase:
        // inserisco tutte le squadre in classifica con 0 punti iniziali
        for (Squadra squadra : listaSquadre) {
            RigaClassifica riga = new RigaClassifica(squadra, 0);
            classifica.add(riga);
        }

        // Seconda fase:
        // guardo tutte le partite del torneo e aggiorno i punti
        for (Partita partita : listaPartite) {

            // Considero solo le partite già giocate
            if (partita.getStatoPartita() == StatoPartita.PLAYED) {

                Squadra squadraCasa = partita.getSquadraCasa();
                Squadra squadraOspite = partita.getSquadraOspite();

                Integer goalCasa = partita.getGoalsHome();
                Integer goalOspite = partita.getGoalsAway();

                // Se manca un risultato, salto questa partita per evitare errori
                if (goalCasa == null || goalOspite == null) {
                    continue;
                }

                Integer puntiCasa;
                Integer puntiOspite;

                // Calcolo i punti della partita
                if (goalCasa > goalOspite) {
                    puntiCasa = 3;
                    puntiOspite = 0;
                } else if (goalCasa < goalOspite) {
                    puntiCasa = 0;
                    puntiOspite = 3;
                } else {
                    puntiCasa = 1;
                    puntiOspite = 1;
                }

                // cerco nella classifica la squadra di casa e la squadra ospite
                // e aggiungo i punti calcolati
                for (RigaClassifica rigaClassifica : classifica) {

                    if (rigaClassifica.getSquadra().equals(squadraCasa)) {
                        rigaClassifica.setPunteggio(rigaClassifica.getPunteggio() + puntiCasa);
                    }

                    if (rigaClassifica.getSquadra().equals(squadraOspite)) {
                        rigaClassifica.setPunteggio(rigaClassifica.getPunteggio() + puntiOspite);
                    }
                }
            }
        }

        // Ordino la classifica.
        Collections.sort(classifica);

        return classifica;
    }

    public void salvaTorneo(Torneo torneo) {
        this.torneoRepository.save(torneo);
    }

    public void eliminaTorneo(Long idTorneo) {
        Torneo torneo = this.torneoRepository.findById(idTorneo).orElse(null);

        if (torneo != null) {

            for (Partita p : torneo.getListaPartite()) {
                this.partitaRepository.delete(p);
            }

            torneo.getSquadrePartecipanti().clear();

            this.torneoRepository.delete(torneo);
        }
    }

    public void modificaTorneo(Long idTorneo, String nome, Integer anno, String descrizione){
        Torneo torneo=torneoRepository.findById(idTorneo).orElse(null);
        if (torneo!=null){
            torneo.setAnno(anno);
            torneo.setNome(nome);
            torneo.setDescrizione(descrizione);
            torneoRepository.save(torneo);
        }

    }


}