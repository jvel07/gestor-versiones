/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.admin;

import ec.fe.gv.common.MD5;
import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.dao.UsuarioDao;
import ec.fe.gv.dao.UsuarioDaoImpl;
import ec.fe.gv.modelo.Usuario;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jose07
 */
@Named(value = "cambiarContrasenaBean")
@RequestScoped
public class CambiarContrasenaBean {

    UsuarioDao usuarioDao = new UsuarioDaoImpl();

    private List<Usuario> usuarios;
    private String idUsuario;
    private String contrasena;
    private String contrasenaActual;
    private Usuario usuarioSeleccionado = new Usuario();

    ;

    /**
     * Creates a new instance of CambiarContrasenaBean
     */
    public CambiarContrasenaBean() {
        this.usuarios = new ArrayList<Usuario>();
        this.usuarios = usuarioDao.buscarTodosUsuarios();
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public void btnCambiarContrasena(ActionEvent actionEvent) {
        UsuarioActual ua = new UsuarioActual();
        this.contrasenaActual = MD5.toMD5(this.contrasenaActual);
        this.idUsuario = ua.usuarioActual();
        if (this.contrasenaActual.equals(ua.contrasenaActual())) {
            if (usuarioDao.cambiarContrasena(this.idUsuario, this.contrasena)) {
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Contraseña actualizada.");
            }
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al cambiar la contraseña, la contraseña actual ingresada no corresponde...");
        }
    }

    public void btnCambiarContrasenaComoAdm(ActionEvent actionEvent) {    
        if (usuarioDao.cambiarContrasena(this.idUsuario, this.contrasena)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Contraseña actualizada.");
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al cambiar la contraseña");
        }
    }

    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Mensaje:", mensaje));
    }

}
