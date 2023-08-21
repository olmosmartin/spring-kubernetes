package com.ms.cursos.mscursos.services;

import com.ms.cursos.mscursos.models.Usuario;
import com.ms.cursos.mscursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Optional<Curso> porIdConUsuarios(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
    void eliminarCursoUsuarioPorId(Long id);

    Optional<Usuario> asignarusuario(Usuario usuario , Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario , Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario , Long cursoId);

}
