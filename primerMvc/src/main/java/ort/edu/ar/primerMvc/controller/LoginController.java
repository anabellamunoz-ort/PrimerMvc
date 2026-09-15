package ort.edu.ar.primerMvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ort.edu.ar.primerMvc.dto.LoginDTO;
import ort.edu.ar.primerMvc.model.Login;
import ort.edu.ar.primerMvc.service.LoginService;

import java.util.Objects;

@Controller
@RequestMapping("/login")
public class LoginController {
    private LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> crearElemento(@RequestBody LoginDTO loginDto) {
        try {
            Login login = service.chequearLogin(loginDto);

            if (Objects.nonNull(login)) {
                return ResponseEntity.ok().body(
                        login
                );
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email o contraseña incorrectos");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<Object> crear(@RequestBody LoginDTO loginDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearLogin(loginDto));
    }
}
