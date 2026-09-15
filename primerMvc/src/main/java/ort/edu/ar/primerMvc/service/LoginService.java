package ort.edu.ar.primerMvc.service;

import org.springframework.stereotype.Service;
import ort.edu.ar.primerMvc.dto.LoginDTO;
import ort.edu.ar.primerMvc.model.Elemento;
import ort.edu.ar.primerMvc.model.Login;
import ort.edu.ar.primerMvc.repository.LoginRepository;

@Service
public class LoginService {
    private LoginRepository repository;

    public LoginService(LoginRepository repository) {
        this.repository = repository;
    }

    public Boolean chequearLogin(LoginDTO loginDto) {
        Login login = repository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Simulación de usuario válido
        //  return email.equals("admin@mail.com") && clave.equals("1234");
        return loginDto.getEmail().equals(login.getEmail()) && loginDto.getPassword().equals(login.getClave());

    }

    public String crearLogin(String nombre) {
        //   Elemento elemento = new Elemento(1,nombre);
        //return repository.save(elemento);
        repository.save(Login.builder()
                //   .id(1)
                .email("mail@mail.com")
                .clave("123")
                .build());
        return "";
    }
}
