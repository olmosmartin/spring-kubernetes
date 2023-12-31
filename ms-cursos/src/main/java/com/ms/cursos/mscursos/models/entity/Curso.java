package com.ms.cursos.mscursos.models.entity;

import com.ms.cursos.mscursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //depende de q la base de datos qusas lo soporte, postgres lo soporta
    private Long id;
    @NotBlank
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;
    @Transient
    private List<Usuario> usuarios;

    public Curso(){
        cursoUsuarios= new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario) {
        cursoUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        cursoUsuarios.remove(cursoUsuario);
    }


}
