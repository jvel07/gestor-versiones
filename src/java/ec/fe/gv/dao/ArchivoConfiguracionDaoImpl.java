/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.common.HandlerUsuarios;
import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.modelo.ArchivoConfiguracion;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Jose07
 */
public class ArchivoConfiguracionDaoImpl implements ArchivoConfiguracionDao {

    @Override
    public ArchivoConfiguracion buscarPorArchivoConfiguracion(ArchivoConfiguracion archConfig) {
        DAO dao = new DAO() {
        };
        ArchivoConfiguracion model = null;
        String sql = "FROM ArchivoConfiguracion WHERE pkIdArchivoConfiguracion= '" + archConfig.getPkIdArchivoConfiguracion() + "'";
        try {
            model = (ArchivoConfiguracion) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public List<ArchivoConfiguracion> buscarTodosArchivos() {
        DAO dao = new DAO() {
        };
        List<ArchivoConfiguracion> listaArchivos = null;
        String sql = "FROM ArchivoConfiguracion";
        try {
            listaArchivos = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaArchivos;
    }

    @Override
    public boolean crearArchivoConfiguracion(ArchivoConfiguracion archConfig) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            dao.currentSession.save(archConfig);
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean actualizarArchivoConfiguracion(ArchivoConfiguracion archConfig) {
        boolean flag;

        try {
            DAO dao = new DAO() {
            };
            ArchivoConfiguracion archConfDB = (ArchivoConfiguracion) dao.currentSession.load(ArchivoConfiguracion.class, archConfig.getPkIdArchivoConfiguracion());
            archConfDB.setNombreArchivo(archConfig.getNombreArchivo());
            archConfDB.setDescripcionArchivo(archConfig.getDescripcionArchivo());
            archConfDB.setDirectorioArchivo(archConfig.getDirectorioArchivo());
            archConfDB.setAplicacion(archConfig.getAplicacion());
            dao.currentSession.update(archConfDB);
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
    public boolean eliminarArchivoConfiguracion(Integer idArch) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            ArchivoConfiguracion archConfig = (ArchivoConfiguracion) dao.currentSession.load(ArchivoConfiguracion.class, idArch);
            Query query = dao.currentSession
                    .createQuery("DELETE FROM ArchivoConfiguracion where pkIdArchivoConfiguracion=:_pkIdArchivoConfiguracion")
                    .setInteger("_pkIdArchivoConfiguracion", archConfig.getPkIdArchivoConfiguracion());
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println("No se ha eliminado del dao");
            flag = false;
        }
        return flag;
    }

    @Override
    public List<ArchivoConfiguracion> seleccionarArchivos() {
        DAO dao = new DAO() {
        };
        List<ArchivoConfiguracion> listaArchivos = null;
        String sql = "FROM ArchivoConfiguracion";
        try {
            listaArchivos = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaArchivos;
    }

    @Override
    public List<ArchivoConfiguracion> seleccionarArchivosSegunApp() {
        DAO dao = new DAO() {
        };
        List<ArchivoConfiguracion> listaArchivos = null;
        String sql = "FROM ArchivoConfiguracion WHERE pkIdUsuarioAplicacion=" + HandlerUsuarios.obtenerIdUsuarioActual() + "'";;
        try {
            listaArchivos = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaArchivos;
    }

    @Override
    public List<ArchivoConfiguracion> seleccionarArchivosSegunUsuario() {
        DAO dao = new DAO() {
        };
        UsuarioActual ua = new UsuarioActual();
        List<ArchivoConfiguracion> listaApps = null;
        String sql = "Select arc FROM Usuario as u join u.usuarioTieneAplicacions as tapp "
                + "join tapp.aplicacion as app join app.archivoConfiguracions as arc where u.pkIdUsuario=:userID";
        try {
            Query query = dao.currentSession.createQuery(sql);
            query.setParameter("userID", ua.usuarioActual());
            listaApps = (List<ArchivoConfiguracion>) query.list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaApps;
    }
}
