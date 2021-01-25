/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.modelo.Perfil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jose07
 */
public class PerfilDao extends DAO {

    public static final String $USUARIO_NORMAL = "normal";
    public static final String $USUARIO_ADMINISTRADOR = "administrador";

    public PerfilDao() {
        super();
    }

    public PerfilDao(Session session) {
        super(session);
    }

    public PerfilDao(DAO dao) {
        super(dao);
    }

    public List<Perfil> obtenerPerfiles() {
        Query q = currentSession.createQuery("from Perfil");
        return q.list();
    }

    public Perfil obtenerPerfil(String idPerfil) {
        return (Perfil) currentSession.load(Perfil.class, idPerfil);
    }

    public boolean insertarNuevoPerfil(String idPerfil, String descripcionPerfil) {
        Perfil perfil = new Perfil(idPerfil, descripcionPerfil);
        currentSession.save(perfil);
        return true;
    }

    public boolean actualizarPerfil(String idPerfil, String descripcionPerfil) {
        Perfil perfil = (Perfil) currentSession.load(Perfil.class, idPerfil);
        perfil.setDescripcionPerfil(descripcionPerfil);
        currentSession.merge(perfil);
        return true;
    }

    public boolean eliminarPerfil(String idPerfil) {
        Perfil perfil = (Perfil) currentSession.load(Perfil.class, idPerfil);
        currentSession.delete(perfil);
        return true;
    }

    public List<Perfil> selectPerfilItems() {
        List<Perfil> listaPerfiles = null;
        String sql = "FROM Perfil";
        listaPerfiles = currentSession.createQuery(sql).list();

        return listaPerfiles;
    }

}
