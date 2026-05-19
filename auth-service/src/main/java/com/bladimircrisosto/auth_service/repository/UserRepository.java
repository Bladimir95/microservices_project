package com.bladimircrisosto.auth_service.repository;

import com.bladimircrisosto.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
/**
 * Interfaz Repository para acceder a la base de datos de los usuarios.
 * Maneja la búsqueda de usuarios en la base de datos.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca un usuario por su identificador único.
     *
     * @param email El email del usuario a buscar.
     * @return El objeto usuario si existe.
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca un usuario por email.
     *
     * @param email El email del usuario a buscar.
     * @return true si el objeto existe, de lo contrario regresa false.
     */
    boolean existsByEmail(String email);
}