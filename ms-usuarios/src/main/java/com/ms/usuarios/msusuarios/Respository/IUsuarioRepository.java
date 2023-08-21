package com.ms.usuarios.msusuarios.Respository;

import com.ms.usuarios.msusuarios.modals.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail (String email);

    @Query("select u from Usuario u where u.email=?1")
    Optional<Usuario> porEmail (String email);
    boolean existsByEmail(String email);

}
