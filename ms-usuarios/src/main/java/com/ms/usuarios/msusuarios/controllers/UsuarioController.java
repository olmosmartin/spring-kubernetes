package com.ms.usuarios.msusuarios.controllers;

import com.ms.usuarios.msusuarios.modals.entity.Usuario;
import com.ms.usuarios.msusuarios.services.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> listar(){
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id){
        Optional<Usuario> userOptional = usuarioService.porId(id);
        return userOptional.isPresent() ? new ResponseEntity<>(userOptional.get(), HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }

        if(!usuario.getEmail().isEmpty() && usuarioService.existsByEmail(usuario.getEmail())){
            return new ResponseEntity<>(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(usuarioService.guardar(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if(usuarioOptional.isPresent()){
            Usuario usuarioOptionalGet = usuarioOptional.get();

            if(!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioOptionalGet.getEmail()) && usuarioService.findByEmail(usuario.getEmail()).isPresent()){
                return new ResponseEntity<>(Collections.singletonMap("mensaje", "Ya existe un usuario con ese correo"), HttpStatus.BAD_REQUEST);
            }

            usuarioOptionalGet.setNombre(usuario.getNombre());
            usuarioOptionalGet.setEmail(usuario.getEmail());
            usuarioOptionalGet.setPassword(usuario.getPassword());
            return new ResponseEntity<>(usuarioService.guardar(usuarioOptionalGet), HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if(usuarioOptional.isPresent()){
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //asi como esta obtiene los usuarios por ids
    @GetMapping("/usuarios-curso")
    public ResponseEntity<?> getALumnosCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(usuarioService.listarPorIds(ids));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(e -> {
            errores.put(e.getField(), "el campo "+ e.getField() + " " + e.getDefaultMessage());
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
