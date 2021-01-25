/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.admin;

import ec.fe.gv.dao.UsuarioDao;
import ec.fe.gv.dao.UsuarioDaoImpl;
import ec.fe.gv.modelo.Usuario;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Jose07
 */
@Named(value = "usuarioBean")
@RequestScoped
public class UsuarioBean implements Serializable {

    
    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado = new Usuario();
    UsuarioDao usuarioDao = new UsuarioDaoImpl();

    private String contrasenaConfirmacion;
    private String contrasena;
    private String idUsuario;
    private List<SelectItem> seleccionarUnUsuario;
    private List<SelectItem> seleccionarUsuarioActual;   

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
        this.usuarios = new ArrayList<Usuario>();
        this.usuarios = usuarioDao.buscarTodosUsuarios();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public String getContrasenaConfirmacion() {
        return contrasenaConfirmacion;
    }

    public void setContrasenaConfirmacion(String contrasenaConfirmacion) {
        this.contrasenaConfirmacion = contrasenaConfirmacion;
    }

    
    public List<SelectItem> getSeleccionarUnUsuario() {
        this.seleccionarUnUsuario = new ArrayList<SelectItem>();
        List<Usuario> listaUsuarios = usuarioDao.seleccionarUsuarios();
        for (Usuario usuario : listaUsuarios) {
            SelectItem selectItem = new SelectItem(usuario.getPkIdUsuario());
            this.seleccionarUnUsuario.add(selectItem);
        }
        return seleccionarUnUsuario;
    }

    public List<SelectItem> getSeleccionarUsuarioActual() {
        this.seleccionarUsuarioActual = new ArrayList<SelectItem>();
        List<Usuario> usuarioActual = usuarioDao.seleccionarUsActual();
        for (Usuario usuario : usuarioActual) {
            SelectItem selectItem = new SelectItem(usuario.getPkIdUsuario());
            this.seleccionarUsuarioActual.add(selectItem);
        }
        return seleccionarUsuarioActual;
    }

    public void setSeleccionarUsuarioActual(List<SelectItem> seleccionarUsuarioActual) {
        this.seleccionarUsuarioActual = seleccionarUsuarioActual;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    
           
    
    

    public void btnCrearUsuario(ActionEvent actionEvent) {
        String msg = "";
        if (usuarioSeleccionado.getContrasena().equals(contrasenaConfirmacion)) {
            String encript = DigestUtils.md5Hex(this.usuarioSeleccionado.getContrasena());
            this.usuarioSeleccionado.setContrasena(encript);
            if (usuarioDao.crearUsuario(this.usuarioSeleccionado)) {
                mostrarMensaje(FacesMessage.SEVERITY_INFO, "Usuario creado exitosamente.");
                usuarios = new ArrayList<Usuario>();
                usuarios.clear();
                usuarios = usuarioDao.buscarTodosUsuarios();
            } else {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al crear el usuario.");
            }
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden");
        }
    }


    public void btnActualizarUsuario(ActionEvent actionEvent) {
        String msg = "";
        if (usuarioDao.actualizarUsuario(this.usuarioSeleccionado)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "La información del usuario se ha actualizado correctamente.");
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al actualizar la información.");
        }
    }

    public void btnEliminarUsuario(ActionEvent actionEvent) {
        if (usuarioDao.eliminarUsuario(this.usuarioSeleccionado.getPkIdUsuario())) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Usuario eliminado.");
            usuarios = new ArrayList<Usuario>();
            usuarios.clear();
            usuarios = usuarioDao.buscarTodosUsuarios();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al eliminar el usuario.");
        }
    }
    
    
    public void btnCambiarContrasenaComoAdm(ActionEvent actionEvent) {
    
       // this.contrasenaActual = MD5.toMD5(this.contrasenaActual);

        // if (this.contrasenaActual.equals(ua.contrasenaActual())) {
        if (usuarioDao.cambiarContrasena(this.idUsuario, this.contrasena)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Contraseña del usuario: "+this.idUsuario+ " actualizada.");
        //    this.usuarios = new ArrayList<Usuario>();
           // this.usuarios = usuarioDao.buscarTodosUsuarios();
            //   }
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al cambiar la contraseña");
        }
    }


    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Mensaje:", mensaje));
    }

}
