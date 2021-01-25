/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.modelo.Aplicacion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Jose07
 */
public class AplicacionDaoImpl implements AplicacionDao {

    @Override
    public Aplicacion buscarPorAplicacion(Aplicacion app) {
        DAO dao = new DAO() {
        };
        Aplicacion model = null;
        String sql = "FROM Aplicacion WHERE pkIdAplicacion= '" + app.getPkIdAplicacion() + "'";
        try {
            model = (Aplicacion) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public List<Aplicacion> buscarTodasApps() {
        DAO dao = new DAO() {
        };
        List<Aplicacion> listaApps = null;
        String sql = "FROM Aplicacion";
        try {
            listaApps = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaApps;
    }

    @Override
    public boolean crearAplicacion(Aplicacion app) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            app.setNombreAplicacion(app.getNombreAplicacion().toLowerCase().replaceAll(" ", "_"));
            dao.currentSession.save(app);
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean actualizarAplicacion(Aplicacion app) {
        boolean flag;

        try {
            DAO dao = new DAO() {
            };
            Aplicacion aplicacionDB = (Aplicacion) dao.currentSession.load(Aplicacion.class, app.getPkIdAplicacion());
            aplicacionDB.setNombreAplicacion(app.getNombreAplicacion());
            aplicacionDB.setDescripcionAplicacion(app.getDescripcionAplicacion());
            aplicacionDB.setNombreEjecutableApp(app.getNombreEjecutableApp());
            dao.currentSession.update(aplicacionDB);
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
    public boolean eliminarAplicacion(Integer idApp) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            Aplicacion aplicacion = (Aplicacion) dao.currentSession.load(Aplicacion.class, idApp);
            Query query = dao.currentSession.createQuery("DELETE FROM Aplicacion where pkIdAplicacion=:_pkIdAplicacion").setInteger("_pkIdAplicacion", aplicacion.getPkIdAplicacion());
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println("No se ha eliminado del dao");
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Aplicacion> seleccionarApps() {
        DAO dao = new DAO() {
        };
        List<Aplicacion> listaApps = null;
        String sql = "FROM Aplicacion";
        try {
            listaApps = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaApps;
    }

    @Override
    public List<Aplicacion> seleccionarAppsSegunUsuario() {
        DAO dao = new DAO() {
        };
        UsuarioActual ua = new UsuarioActual();
        List<Aplicacion> listaApps = null;
        String sql = "Select app FROM Usuario as u join u.usuarioTieneAplicacions as tapp join tapp.aplicacion as app where u.pkIdUsuario=:userID";
        try {
            Query query = dao.currentSession.createQuery(sql);
            query.setParameter("userID", ua.usuarioActual());
            listaApps = (List<Aplicacion>)query.list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaApps;
    }   

}
