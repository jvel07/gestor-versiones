/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.modelo.Perfil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Jose07
 */
public class PerfilDaoImpl implements PerfiDao {

    @Override
    public Perfil buscarPorPerfil(Perfil perfil) {
        DAO dao = new DAO() {
        };
        Perfil model = null;
        String sql = "FROM Perfil WHERE pkIdPerfil= '" + perfil.getPkIdPerfil() + "'";
        try {
            model = (Perfil) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public List<Perfil> buscarTodosPerfiles() {
        DAO dao = new DAO() {
        };
        List<Perfil> listaPerfiles = null;
        String sql = "FROM Perfil";
        try {
            listaPerfiles = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaPerfiles;
    }

    @Override
    public boolean crearPerfil(Perfil perfil) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            dao.currentSession.save(perfil);
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean actualizarPerfil(Perfil perfil) {
        boolean flag;

        try {
            DAO dao = new DAO() {
            };
            Perfil perfilDB = (Perfil) dao.currentSession.load(Perfil.class, perfil.getPkIdPerfil());
            perfilDB.setDescripcionPerfil(perfil.getDescripcionPerfil());
            dao.currentSession.update(perfilDB);
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
    public boolean eliminarPerfil(String idPerfil) {
        boolean flag;
        DAO dao = new DAO() {
        };

        try {
            Perfil perfil = (Perfil) dao.currentSession.load(Perfil.class, idPerfil);
            Query query = dao.currentSession.createQuery("DELETE FROM Perfil where pkIdPerfil=:_pkIdPerfil").setString("_pkIdPerfil", perfil.getPkIdPerfil());
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println("No se ha eliminado del dao");
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Perfil> seleccionarPerfiles() {
        DAO dao = new DAO() {
        };
        List<Perfil> listaPerfiles = null;
        String sql = "FROM Perfil";
        try {
            listaPerfiles = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaPerfiles;
    }

    @Override
    public Perfil obtenerPerfilUsActual(Perfil perfil) {
        Perfil model = this.buscarPorPerfil(perfil);        
        if (model != null) {
            if (!(model.getPkIdPerfil().equals(model.getPkIdPerfil()))) {
                model = null;
            }
        }
        return model;
    }

}
