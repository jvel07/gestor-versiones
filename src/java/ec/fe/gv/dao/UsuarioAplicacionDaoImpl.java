/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.modelo.Aplicacion;
import ec.fe.gv.modelo.Usuario;
import ec.fe.gv.modelo.UsuarioTieneAplicacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Jose07
 */
public class UsuarioAplicacionDaoImpl implements UsuarioAplicacionDao {

    @Override
    public UsuarioTieneAplicacion buscarPorUsuarioApp(UsuarioTieneAplicacion usuarioApp) {
        DAO dao = new DAO() {
        };
        UsuarioTieneAplicacion model = null;
        String sql = "FROM UsuarioTieneAplicacion WHERE pkIdUsuarioAplicacion= '" + usuarioApp.getPkIdUsuarioAplicacion() + "'";
        try {
            model = (UsuarioTieneAplicacion) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public List<UsuarioTieneAplicacion> buscarTodosUsuariosApps() {
        DAO dao = new DAO() {
        };
        List<UsuarioTieneAplicacion> listaUsuariosApps = null;
        String sql = "FROM UsuarioTieneAplicacion";
        try {
            listaUsuariosApps = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaUsuariosApps;
    }

    @Override
    public boolean crearUsuarioApp(UsuarioTieneAplicacion usuarioApp) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            dao.currentSession.save(usuarioApp);
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean actualizarUsuarioApp(UsuarioTieneAplicacion usuarioApp) {
        boolean flag;

        try {
            DAO dao = new DAO() {
            };
            UsuarioTieneAplicacion usuarioAplicacionDB = (UsuarioTieneAplicacion) dao.currentSession.load(UsuarioTieneAplicacion.class, usuarioApp.getPkIdUsuarioAplicacion());
            usuarioAplicacionDB.setAplicacion(usuarioApp.getAplicacion());
            dao.currentSession.update(usuarioAplicacionDB);
            flag = true;
        } catch (HibernateException e) {
            DAO dao = new DAO() {
            };
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean eliminarUsuarioApp(Integer idUsuarioApp) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            UsuarioTieneAplicacion usuarioApp = (UsuarioTieneAplicacion) dao.currentSession.load(UsuarioTieneAplicacion.class, idUsuarioApp);
            Query query = dao.currentSession.createQuery("DELETE FROM UsuarioTieneAplicacion where pkIdUsuarioAplicacion=:_pkIdUsuarioAplicacion").setInteger("_pkIdUsuarioAplicacion", usuarioApp.getPkIdUsuarioAplicacion());
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println("No se ha eliminado del dao");
            flag = false;
        }
        return flag;
    }

    @Override
    public List<UsuarioTieneAplicacion> seleccionarUsuarioTieneApps() {
        DAO dao = new DAO() {
        };
        List<UsuarioTieneAplicacion> listaUsuarioApps = null;
        String sql = "FROM UsuarioTieneAplicacion";
        try {
            listaUsuarioApps = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaUsuarioApps;
    }

    @Override
    public boolean registrarUsuarioApp(String fkIdUsuarioApp, Integer fkIdApp) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            Usuario usuario = (Usuario) dao.currentSession.load(Usuario.class, fkIdUsuarioApp);
            Aplicacion aplicacion = (Aplicacion) dao.currentSession.load(Aplicacion.class, fkIdApp);
            UsuarioTieneAplicacion usuarioApp = new UsuarioTieneAplicacion(usuario, aplicacion);
            dao.currentSession.save(usuarioApp);
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public UsuarioTieneAplicacion buscarAsignaciones(UsuarioTieneAplicacion usuarioApp) {
        DAO dao = new DAO() {
        };
        UsuarioTieneAplicacion model = null;
        String sql = "Select u.pkIdUsuario, app.nombreAplicacion FROM Usuario as u join u.usuarioTieneAplicacions as tapp\n"
                + "join tapp.aplicacion as app where u.pkIdUsuario='"
                + usuarioApp.getUsuario().getPkIdUsuario() + "' and app.nombreAplicacion ='"
                + usuarioApp.getAplicacion().getNombreAplicacion() + "'";
        try {
            model = (UsuarioTieneAplicacion) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

}
