package org.example.progettosiwtornei.service;

import org.example.progettosiwtornei.entity.Arbitro;
import org.example.progettosiwtornei.entity.Partita;
import org.example.progettosiwtornei.repository.ArbitroRepository;
import org.example.progettosiwtornei.repository.PartitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArbitroService {
    private final ArbitroRepository arbitroRepository;
    private final PartitaRepository partitaRepository;

    public ArbitroService(ArbitroRepository arbitroRepository, PartitaRepository partitaRepository) {
        this.arbitroRepository = arbitroRepository;
        this.partitaRepository = partitaRepository;
    }

    public Arbitro getArbitro(Long id){
        return this.arbitroRepository.findById(id).orElse(null);
    }

    public void eliminaArbitro(Long idArbitro) {
        Arbitro arbitro = this.arbitroRepository.findById(idArbitro).orElse(null);

        if (arbitro != null) {

            for (Partita partita : arbitro.getListaPartite()) {
                partita.setArbitro(null);
                this.partitaRepository.save(partita);
            }

            this.arbitroRepository.delete(arbitro);
        }
    }
    public List<Arbitro> getAllArbitri(){
        return this.arbitroRepository.findAll();
    }

    public List<Partita> listaPartiteArbitrateDallArbitro(Long id){
    return this.getArbitro(id).getListaPartite();
    }

    public void salvaArbitro(Arbitro arbitro){
        this.arbitroRepository.save(arbitro);
    }
}
