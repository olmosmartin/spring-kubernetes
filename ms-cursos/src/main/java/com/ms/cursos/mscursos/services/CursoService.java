package com.ms.cursos.mscursos.services;


import com.ms.cursos.mscursos.Repository.ICursoRepository;
import com.ms.cursos.mscursos.clients.IUsuarioClientRest;
import com.ms.cursos.mscursos.models.Usuario;
import com.ms.cursos.mscursos.models.entity.Curso;
import com.ms.cursos.mscursos.models.entity.CursoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService implements ICursoService {

    @Autowired
    private ICursoRepository cursoRepository;
    @Autowired
    private IUsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true) //importar el de spring
    public List<Curso> listar() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true) //importar el de spring
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = cursoRepository.findById(id);
        if(o.isPresent()){
            //Usuario usuarioMS = usuarioClientRest.porId(usuario.getId());
            Curso curso = o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream().map((CursoUsuario cu) -> {return cu.getUsuarioId();}).collect(Collectors.toList());
                List<Usuario> usuarios = usuarioClientRest.getALumnosCurso(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso Curso) {
        return cursoRepository.save(Curso);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        System.out.println("eliminar: "+id);
        cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarusuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMS = usuarioClientRest.porId(usuario.getId());
            System.out.println("usuarioMS: "+ usuarioMS.getId());
            Curso curso = o.get();
            System.out.println("curso: "+ curso.getId());
            CursoUsuario cursoUsuario = new CursoUsuario();
            System.out.println("cursoUsuario: "+ cursoUsuario.getId()+ " " + cursoUsuario.getUsuarioId());
            cursoUsuario.setUsuarioId(usuarioMS.getId());
            System.out.println("cursoUsuario: "+ cursoUsuario.getId()+ " " + cursoUsuario.getUsuarioId());

            curso.addCursoUsuario(cursoUsuario);

            System.out.println("curso: "+ curso.getId());
            //estoy guardando el curso que saque de cursoRepository en Curso curso = o.get();
            cursoRepository.save(curso);
            return Optional.of(usuarioMS);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMS = usuarioClientRest.crear(usuario);
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMS.getId());

            curso.addCursoUsuario(cursoUsuario);
            //estoy guardando el curso que saque de cursoRepository en Curso curso = o.get();
            cursoRepository.save(curso);
            return Optional.of(usuarioMS);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if(o.isPresent()){
            Usuario usuarioMS = usuarioClientRest.porId(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMS.getId());

            curso.removeCursoUsuario(cursoUsuario);
            //estoy guardando el curso que saque de cursoRepository en Curso curso = o.get();
            cursoRepository.save(curso);
            return Optional.of(usuarioMS);
        }
        return Optional.empty();
    }
}
