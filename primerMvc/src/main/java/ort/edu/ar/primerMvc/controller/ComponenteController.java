package ort.edu.ar.primerMvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ort.edu.ar.primerMvc.dto.ComponenteDTO;
import ort.edu.ar.primerMvc.model.Componente;
import ort.edu.ar.primerMvc.service.ComponenteService;

@Controller
@RequestMapping("/api/componente")
public class ComponenteController {
    private ComponenteService service;

    public ComponenteController(ComponenteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Componente> crearElemento(@RequestBody ComponenteDTO componenteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearComponente(Componente.builder()
                        .descripcion(componenteDTO.getDescripcion())
                        .color(componenteDTO.getColor())
                        .medida(componenteDTO.getMedida())
                .build()));
    }
}
