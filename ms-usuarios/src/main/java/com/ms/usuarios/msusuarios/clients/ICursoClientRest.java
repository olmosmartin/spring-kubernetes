package com.ms.usuarios.msusuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-cursos", url = "${ms.cursos.url}") // el nombre ms-cursos es porque va a correr dockerizado
public interface ICursoClientRest {
    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public void eliminarCursoUsuarioPorId(@PathVariable Long id);
}
