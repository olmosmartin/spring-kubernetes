package com.ms.usuarios.msusuarios.modals.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //depende de q la base de datos qusas lo soporte, mysql lo soporta
    private Long id;
    //@Column() // si fuese necesitario cambiar algunas propiedades como el nombre en la tabla o si es unico, etc, va acá

    @NotBlank // es para no vacio como @NotEmpty pero ademas sin espacios
    private String nombre;
    @Column(unique = true)
    @Email
    @NotEmpty//es para string, notnull es para int o long
    private String email;
    @NotBlank
    private String password;

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
