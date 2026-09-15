package ort.edu.ar.primerMvc.service;

import org.springframework.stereotype.Service;
import ort.edu.ar.primerMvc.model.Elemento;
import ort.edu.ar.primerMvc.repository.ElementoRepository;

import java.util.List;

@Service
public class ElementoService {
    private ElementoRepository repository;

    public ElementoService(ElementoRepository repository) {
        this.repository = repository;
    }

    public List<Elemento> getAllElementos() {
        return repository.findAll();
    }

    public Elemento crearElementos(String nombre) {
     //   Elemento elemento = new Elemento(1,nombre);
        //return repository.save(elemento);
        return repository.save(Elemento.builder()
                     //   .id(1)
                        .nombre(nombre)
                .build());
    }
}
