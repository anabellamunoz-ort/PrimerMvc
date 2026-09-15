package ort.edu.ar.primerMvc.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ort.edu.ar.primerMvc.dto.LoginDTO;
import ort.edu.ar.primerMvc.model.Elemento;
import ort.edu.ar.primerMvc.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {
    private LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public String crearElemento(@RequestBody LoginDTO login, Model model) {
        // ResponseEntity.status(HttpStatus.CREATED).body(usuario)
        boolean valido = service.chequearLogin(
                login
        );

        if (valido) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", true);
            return "login";
        }
       // return null;
        //return service.chequearLogin(login);
    }
    @PostMapping("/crear")
    public String crear(@RequestBody String nombre) {
        // ResponseEntity.status(HttpStatus.CREATED).body(usuario)
        return service.crearLogin(nombre);
    }
}
