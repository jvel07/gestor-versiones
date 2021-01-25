/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.common.MD5;
import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.modelo.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Jose07
 */
public class UsuarioDaoImpl implements UsuarioDao {

    @Override
    public Usuario buscarPorUsuario(Usuario usuario) {
        DAO dao = new DAO() {
        };
        Usuario model = null;
        String sql = "FROM Usuario WHERE pkIdUsuario= '" + usuario.getPkIdUsuario() + "'";
        try {
            model = (Usuario) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public List<Usuario> buscarTodosUsuarios() {
        DAO dao = new DAO() {
        };
        List<Usuario> listaUsuarios = null;
        String sql = "FROM Usuario";
        try {
            listaUsuarios = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaUsuarios;
    }

    @Override
    public boolean crearUsuario(Usuario usuario) {
        boolean flag;
        DAO dao = new DAO() {
        };
        try {
            dao.currentSession.save(usuario);
            flag = true;
        } catch (HibernateException e) {
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        boolean flag;

        try {
            DAO dao = new DAO() {
            };
            Usuario usuarioDB = (Usuario) dao.currentSession.load(Usuario.class, usuario.getPkIdUsuario());
            usuarioDB.setNombreUsuario(usuario.getNombreUsuario());
            usuarioDB.setApellidoUsuario(usuario.getApellidoUsuario());
            usuarioDB.setPerfil(usuario.getPerfil());
            dao.currentSession.update(usuarioDB);
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
    public boolean eliminarUsuario(String idUsuario) {
        boolean flag;
        DAO dao = new DAO() {
        };

        try {
            Usuario usuario = (Usuario) dao.currentSession.load(Usuario.class, idUsuario);
            Query query = dao.currentSession.createQuery("DELETE FROM Usuario where pkIdUsuario=:_pkIdUsuario").setString("_pkIdUsuario", usuario.getPkIdUsuario());
            query.executeUpdate();
            flag = true;
        } catch (HibernateException e) {
            System.out.println("No se ha eliminado del dao");
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Usuario> seleccionarUsuarios() {
        DAO dao = new DAO() {
        };
        List<Usuario> listaUsuarios = null;
        String sql = "FROM Usuario";
        try {
            listaUsuarios = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return listaUsuarios;
    }

    @Override
    public List<Usuario> seleccionarUsActual() {
        DAO dao = new DAO() {
        };
        UsuarioActual usuarioActual = new UsuarioActual();
        List<Usuario> model = null;

        String sql = "FROM Usuario where pkIdUsuario='" + usuarioActual.usuarioActual() + "'";
        try {
            model = dao.currentSession.createQuery(sql).list();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public Usuario loginUsuario(Usuario usuario) {
        Usuario model = this.buscarPorUsuario(usuario);
        String contrasenaMD5 = MD5.toMD5(usuario.getContrasena());
        if (model != null) {
            if (!contrasenaMD5.equals(model.getContrasena())) {
                model = null;
            }
        }
        return model;
    }

    @Override
    public String seleccionarPerfUsActual() {
        DAO dao = new DAO() {
        };
        UsuarioActual ua = new UsuarioActual();
        String model = null;
        String sql = "Select perf.pkIdPerfil FROM Usuario as u join u.perfil as perf where u.pkIdUsuario= '" + ua.usuarioActual() + "'";
        try {
            model = (String) dao.currentSession.createQuery(sql).uniqueResult();
        } catch (HibernateException e) {
            dao.currentSession.beginTransaction().rollback();
        }
        return model;
    }

    @Override
    public boolean cambiarContrasena(String idUsuario, String contrasena) { 
         boolean flag;

        try {
            DAO dao = new DAO() {
            };
            Usuario usuarioDB = (Usuario) dao.currentSession.load(Usuario.class, idUsuario);
            usuarioDB.setContrasena(MD5.toMD5(contrasena));
            dao.currentSession.update(usuarioDB);
            flag = true;
        } catch (HibernateException e) {
            DAO dao = new DAO() {
            };
            flag = false;
            dao.currentSession.beginTransaction().rollback();
        }
        return flag;
    }

}
