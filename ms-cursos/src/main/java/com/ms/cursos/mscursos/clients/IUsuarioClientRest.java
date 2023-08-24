package com.ms.cursos.mscursos.clients;

import com.ms.cursos.mscursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="ms-usuarios", url="${ms.usuarios.url}") //acá va el nombre del otro microservicio igual q como esta en el pom spring.application.name y el nombre ms-usuarios:8001 es porque va a correr dockerizado
public interface IUsuarioClientRest {
    @GetMapping("/{id}")
    public Usuario porId(@PathVariable Long id);

    @PostMapping("/")
    public Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-curso")
    public List<Usuario> getALumnosCurso(@RequestParam Iterable<Long> ids);
}
