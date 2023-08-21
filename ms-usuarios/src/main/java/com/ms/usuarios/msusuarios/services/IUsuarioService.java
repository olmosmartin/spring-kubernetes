package com.ms.usuarios.msusuarios.services;

import com.ms.usuarios.msusuarios.modals.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    List<Usuario> listarPorIds(Iterable<Long> ids);

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> porEmail (String email);
    boolean existsByEmail(String email);
}
