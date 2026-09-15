package ort.edu.ar.primerMvc.service;

import org.springframework.stereotype.Service;
import ort.edu.ar.primerMvc.dto.LoginDTO;
import ort.edu.ar.primerMvc.model.Login;
import ort.edu.ar.primerMvc.repository.LoginRepository;

@Service
public class LoginService {
    private LoginRepository repository;

    public LoginService(LoginRepository repository) {
        this.repository = repository;
    }

    public Login chequearLogin(LoginDTO loginDto) {

        Login login = repository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."+loginDto));

        Boolean estaAutorizado = loginDto.getEmail().equals(login.getEmail())
                && loginDto.getClave().equals(login.getClave());
        if (estaAutorizado) {
            return login;
        } else {
            return null;
        }
    }

    public Login crearLogin(LoginDTO loginDto) {
        //   Elemento elemento = new Elemento(1,nombre);
        //return repository.save(elemento);
        Login login = repository.save(Login.builder()
                //   .id(1)
                .email(loginDto.getEmail())
                .clave(loginDto.getClave())
                .build());
        return login;
    }
}
