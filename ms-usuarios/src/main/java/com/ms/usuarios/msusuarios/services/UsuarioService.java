package com.ms.usuarios.msusuarios.services;

import com.ms.usuarios.msusuarios.Respository.IUsuarioRepository;
import com.ms.usuarios.msusuarios.clients.ICursoClientRest;
import com.ms.usuarios.msusuarios.modals.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ICursoClientRest cursoClientRest;

    @Override
    @Transactional(readOnly = true) //importar el de spring
    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true) //importar el de spring
    public Optional<Usuario> porId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
        cursoClientRest.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional(readOnly = true) //importar el de spring
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return usuarioRepository.porEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
