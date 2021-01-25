/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.modelo.NovedadActualizacionApp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Jose07
 */
public class NovActualizAppDaoImpl implements NovActualizAppDao {

    @Override
    public NovedadActualizacionApp buscarPorNovedad(NovedadActualizacionApp novedadAct) {
        DAO dao = new DAO() {
        };
        NovedadActualizacionApp model = null;
        String sql = "FROM NovedadActualizacionApp WHERE pkIdNovedadActualizacionApp= '" + novedadAct.getPkIdNovedadActualizacionApp() + "'";
        try {
            model = (NovedadActualizacionApp) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public List<NovedadActualizacionApp> buscarTodasNovedades() {
        DAO dao = new DAO() {
        };
        List<NovedadActualizacionApp> listaNovedades = null;
        String sql = "FROM NovedadActualizacionApp";
        try {
            listaNovedades = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaNovedades;
    }

    @Override
    public boolean crearNovedad(NovedadActualizacionApp novedadAct) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            dao.currentSession.save(novedadAct);
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean actualizarNovedad(NovedadActualizacionApp novedadAct) {
        boolean flag;
        UsuarioActual ua = new UsuarioActual();
        try {
            DAO dao = new DAO() {
            };
            NovedadActualizacionApp novActDB = (NovedadActualizacionApp) dao.currentSession.load(NovedadActualizacionApp.class, novedadAct.getPkIdNovedadActualizacionApp());
            novActDB.setFechaActualizacionAplicacion(novedadAct.getFechaActualizacionAplicacion());
            novActDB.setDescripcionNovedadActualizacion(novedadAct.getDescripcionNovedadActualizacion());
            novActDB.setDescripcionDosNovedad(novedadAct.getDescripcionDosNovedad());
            novActDB.setArchivoConfiguracion(novedadAct.getArchivoConfiguracion());
            novActDB.setUsuarioRegActualizacion(ua.usuarioActual());
            dao.currentSession.update(novActDB);
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
    public boolean eliminarNovedad(Integer idNovedad) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            NovedadActualizacionApp novAct = (NovedadActualizacionApp) dao.currentSession.load(NovedadActualizacionApp.class, idNovedad);
            Query query = dao.currentSession.createQuery("DELETE FROM NovedadActualizacionApp where pkIdNovedadActualizacionApp=:_pkIdNovedadActualizacionApp").setInteger("_pkIdNovedadActualizacionApp", novAct.getPkIdNovedadActualizacionApp());
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println("No se ha eliminado del dao");
            flag = false;
        }
        return flag;
    }

    @Override
    public Date convertirStringADate(String fecha) {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

        try {
            fecha = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public List<NovedadActualizacionApp> buscarNovedadPorUsuario() {
        DAO dao = new DAO() {
        };
        UsuarioActual ua = new UsuarioActual();
        List<NovedadActualizacionApp> listaApps = null;
        String sql = "Select nov FROM Usuario as u join u.usuarioTieneAplicacions as tapp join tapp.aplicacion as app join app.archivoConfiguracions as arc join arc.novedadActualizacionApps as nov where u.pkIdUsuario=:userID";
        try {
            Query query = dao.currentSession.createQuery(sql);
            query.setParameter("userID", ua.usuarioActual());
            listaApps = (List<NovedadActualizacionApp>) query.list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaApps;
    }

}
