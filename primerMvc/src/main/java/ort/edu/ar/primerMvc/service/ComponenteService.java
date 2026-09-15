package ort.edu.ar.primerMvc.service;

import org.springframework.stereotype.Service;
import ort.edu.ar.primerMvc.model.Componente;
import ort.edu.ar.primerMvc.repository.ComponenteRepository;

@Service
public class ComponenteService {
    private ComponenteRepository repository;

    public ComponenteService(ComponenteRepository repository) {
        this.repository = repository;
    }

    public Componente crearComponente(Componente componente) {
        return repository.save(componente);
    }
}
