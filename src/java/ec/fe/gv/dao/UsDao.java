/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.fe.gv.dao;

import ec.fe.gv.common.MD5;
import ec.fe.gv.modelo.Perfil;
import ec.fe.gv.modelo.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jose07
 */
public class UsDao extends DAO{
     public UsDao() {
        super();
    }

    public UsDao(DAO dao) {
        super(dao);
    }

    public UsDao(Session session) {
        super(session);
    }

    public List<Usuario> obtenerUsuarios() {
        Query q = currentSession.createQuery("from Usuario");
        return q.list();
    }

    public Integer obtenerTotalUsuarios() {
        return obtenerUsuarios().size();
    }

    public Usuario obtenerUsuario(String idUsuario) {
        return (Usuario) currentSession.load(Usuario.class, idUsuario);
    }

    public List<Usuario> obtenerAppsPorIdUsuario(String idUsuario) {
        Usuario usuario = (Usuario) currentSession.load(Usuario.class, idUsuario);
        Query q = currentSession.createQuery("from UsuarioTieneAplicacion where usuario=:_usuario");
        q.setParameter("_usuario", usuario);
        return q.list();
    }

    public Usuario obtenerUsuarioPorID(String pkIdUsuario) {
        Query q = currentSession.createQuery("from Usuario where pkIdUsuario=:_pkIdUsuario");
        q.setParameter("_pkIdUsuario", pkIdUsuario);
        return (Usuario) q.uniqueResult();
    }

    public boolean insertarNuevoUsuario(String idUsuario, String idPerfil, String nombreUsuario, String apellidoUsuario, String contrasena) {
        contrasena = MD5.toMD5(contrasena);
        Perfil perfil = (Perfil) currentSession.load(Perfil.class, idPerfil);
        Usuario usuario = new Usuario(idUsuario, perfil, nombreUsuario, apellidoUsuario, contrasena);
        currentSession.save(usuario);
        return true;
    }

    public boolean actualizarUsuario(String idUsuario, String idPerfil, String nombreUsuario, String apellidoUsuario) {
        Usuario usuario = (Usuario) currentSession.load(Usuario.class, idUsuario);
        Perfil perfil = (Perfil) currentSession.load(Perfil.class, idPerfil);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setApellidoUsuario(apellidoUsuario);
        usuario.setPerfil(perfil);
        currentSession.merge(usuario);
        return true;
    }

    public boolean cambiarContrasena(String idUsuario, String contrasena) {
        Usuario usuario = (Usuario) currentSession.load(Usuario.class, idUsuario);
        usuario.setContrasena(MD5.toMD5(contrasena));
        currentSession.merge(usuario);
        return true;
    }

    public boolean eliminarUsuario(String idUsuario) {
        Usuario usuario = (Usuario) currentSession.load(Usuario.class, idUsuario);
        currentSession.delete(usuario);
        return true;
    }

    public List<Perfil> selectPerfilItems() {
        List<Perfil> listaPerfiles = null;
        String sql = "FROM Perfil";
        listaPerfiles = currentSession.createQuery(sql).list();

        return listaPerfiles;
    }
}
