/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.common;

import ec.fe.gv.common.MD5;
import ec.fe.gv.common.ManejoSesion;
import ec.fe.gv.dao.PerfilDao;
import ec.fe.gv.dao.UsDao;
import ec.fe.gv.dao.UsuarioDao;
import ec.fe.gv.modelo.Perfil;
import ec.fe.gv.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jose07
 */
public class HandlerUsuarios {

    public static boolean autenticarUsuario(String idUsuario, String contrasena) {
        boolean success = false;
        try {
            String contrasenaMD5 = MD5.toMD5(contrasena);
            UsDao dao = new UsDao();
            Usuario usuario = dao.obtenerUsuarioPorID(idUsuario);
            if (usuario.getContrasena().equals(contrasenaMD5)) {
                success = true;
            }
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.toString());
        }
        return success;
    }

    public static boolean iniciarSesion(String idUsuario) {
        boolean success = false;
        try {
            UsDao dao = new UsDao();
            Usuario usuario = dao.obtenerUsuarioPorID(idUsuario);
            ManejoSesion sesion = new ManejoSesion();
            sesion.iniciarSesion(idUsuario, String.valueOf(usuario.getPerfil().getPkIdPerfil()));
            success = true;
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return success;
    }

    public static boolean cerrarSesion() {
        boolean success = false;
        try {
            ManejoSesion sesion = new ManejoSesion();
            UsDao dao = new UsDao();
            Usuario usuario = dao.obtenerUsuario(sesion.idUsuario());
            sesion.cerrarSesion();
            success = true;
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return success;
    }

    public static Usuario obtenerUsuario(String idUsuario) {
        UsDao dao = new UsDao();
        return dao.obtenerUsuario(idUsuario);
    }

    public static String obtenerTipoUsuario() {
        ManejoSesion manejoSesion = new ManejoSesion();
        return manejoSesion.idPerfilActual();
    }

    public static String obtenerIdUsuarioActual() {
        ManejoSesion manejoSesion = new ManejoSesion();
        return manejoSesion.idUsuario();
    }

    public static String obtenerPerfilUsuarioActual() {
        ManejoSesion manejoSesion = new ManejoSesion();
        return manejoSesion.idPerfilActual();
    }

    /**
     * Creación de un registro en la tabla usuario, actualización, eliminación
     *
     * @param nombreUsuario
     * @param idUsuario
     * @param apellidoUsuario
     * @param idPerfil
     * @param contrasena
     * @return true si todo se ha creado correctamente.
     */
    public static boolean crearUsuarioNormal(String idUsuario, String idPerfil, String nombreUsuario, String apellidoUsuario, String contrasena) {
        String msg = "";
        boolean success = false;
        try {
            UsDao dao = new UsDao();
            success = dao.insertarNuevoUsuario(idUsuario, idPerfil, nombreUsuario, apellidoUsuario, contrasena);
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return success;
    }

    public static boolean actualizarUsuario(Usuario u) {
        String msg = "";
        boolean success = false;
        try {
            UsDao dao = new UsDao();
            success = dao.actualizarUsuario(u.getPkIdUsuario(), u.getPerfil().getPkIdPerfil(), u.getNombreUsuario(), u.getApellidoUsuario());
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return success;
    }

    public static boolean eliminarUsuario(Usuario u) {

        boolean success = false;
        try {
            UsDao dao = new UsDao();
            //todo
            success = dao.eliminarUsuario(u.getPkIdUsuario());
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return success;
    }

    public static List<Usuario> obtenerAppPorUsuario(String idUsuario) {
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        try {
            UsDao dao = new UsDao();
            listaUsuarios = dao.obtenerAppsPorIdUsuario(idUsuario);
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return listaUsuarios;
    }

    ////////////////////////////////////////////////////////////////////
    /**
     * Creación de un registro en la tabla perfil
     *
     * @param idPerfil
     * @param descripcionPerfil
     * @param estadoPerfil
     * @return true, si todo es OK
     */
    public static boolean crearPerfil(String idPerfil, String descripcionPerfil, boolean estadoPerfil) {
        String msg = "";
        boolean success = false;
        try {
            PerfilDao daoPerfil = new PerfilDao();
            success = daoPerfil.insertarNuevoPerfil(idPerfil, descripcionPerfil);
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return success;
    }

    public static boolean actualizarPerfil(Perfil p) {
        String msg = "";
        boolean success = false;
        try {
            PerfilDao daoPerfil = new PerfilDao();
            success = daoPerfil.actualizarPerfil(p.getPkIdPerfil(), p.getDescripcionPerfil());
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return success;

    }

    public static Perfil obtenerPerfil(String idPerfil) {
        PerfilDao dao = new PerfilDao();
        return dao.obtenerPerfil(idPerfil);
    }

    public static List<Perfil> obtenerPerfiles() {
        List<Perfil> list = new ArrayList<Perfil>();
        try {
            PerfilDao dao = new PerfilDao();
            list = dao.obtenerPerfiles();
        } catch (Exception e) {
            Logger.getLogger(HandlerUsuarios.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        return list;
    }

}
