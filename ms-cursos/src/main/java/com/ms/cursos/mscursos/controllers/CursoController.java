package com.ms.cursos.mscursos.controllers;

import com.ms.cursos.mscursos.models.Usuario;
import com.ms.cursos.mscursos.models.entity.Curso;
import com.ms.cursos.mscursos.services.ICursoService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {
    @Autowired
    private ICursoService cursoService;

    @GetMapping("/")
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id){
        Optional<Curso> userOptional = cursoService.porIdConUsuarios(id);
        return userOptional.isPresent() ? new ResponseEntity<>(userOptional.get(), HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }

        return new ResponseEntity<>(cursoService.guardar(curso), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }

        Optional<Curso> cursoOptional = cursoService.porId(id);
        if(cursoOptional.isPresent()){
            Curso cursoOptionalGet = cursoOptional.get();
            cursoOptionalGet.setNombre(curso.getNombre());
            return new ResponseEntity<>(cursoService.guardar(cursoOptionalGet), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> usuarioOptional = cursoService.porId(id);
        if(usuarioOptional.isPresent()){
            cursoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        System.out.println("pasa por asignarUsuario");
        Optional<Usuario> o = null;
        try{
            System.out.println("try");
            o = cursoService.asignarusuario(usuario, cursoId);
        } catch (FeignException e){
            System.out.println("catch");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "no existe el usuario por id o error en la comunicacion: "+ e.getMessage()));
        }
        if(o.isPresent()){
            System.out.println("existe uuario");
            return new ResponseEntity<>(o.get(), HttpStatus.CREATED);
        }
        System.out.println("no existe usuario: "+ o);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o = null;
        try{
            o = cursoService.crearUsuario(usuario, cursoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "error en la comunicacion: "+ e.getMessage()));
        }
        if(o.isPresent()){
            return new ResponseEntity<>(o.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o = null;
        try{
            o = cursoService.eliminarUsuario(usuario, cursoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("mensaje", "no existe el usuario por id o error en la comunicacion: "+ e.getMessage()));
        }
        if(o.isPresent()){
            return new ResponseEntity<>(o.get(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{usuarioId}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long usuarioId){
        cursoService.eliminarCursoUsuarioPorId(usuarioId);
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(e -> {
            errores.put(e.getField(), "el campo "+ e.getField() + " " + e.getDefaultMessage());
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
