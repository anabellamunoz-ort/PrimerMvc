package ort.edu.ar.primerMvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ort.edu.ar.primerMvc.model.Login;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByEmail(String email);
}
