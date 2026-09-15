package ort.edu.ar.primerMvc.controller;

import org.springframework.web.bind.annotation.*;
import ort.edu.ar.primerMvc.model.Elemento;
import ort.edu.ar.primerMvc.service.ElementoService;

import java.util.List;

@RestController
@RequestMapping("/api/elemento")
public class ElementoController {

    private ElementoService service;

    public ElementoController(ElementoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Elemento> obtenerElementos() {
        return service.getAllElementos();
    }
    @PostMapping
    public Elemento crearElemento(@RequestBody String nombre) {
        // ResponseEntity.status(HttpStatus.CREATED).body(usuario)
        return service.crearElementos(nombre);
    }
    @PutMapping
    public String modificarElemento(String nombre) {
        return "entra por put";
    }
    @PatchMapping
    public String modificarParcialmenteElemento(String nombre) {
        return "entra por patch";
    }
    @DeleteMapping
    public String borraElemento(String nombre) {
        return "entra por patch";
    }
}