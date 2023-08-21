package com.ms.cursos.mscursos.Repository;

import com.ms.cursos.mscursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long>{
    @Modifying //sino el query no hace nada, porque es un delete, con select funiciona sin modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    void eliminarCursoUsuarioPorId(Long id);
}
