/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.admin;

import ec.fe.gv.dao.UsuarioAplicacionDao;
import ec.fe.gv.dao.UsuarioAplicacionDaoImpl;
import ec.fe.gv.modelo.UsuarioTieneAplicacion;
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
@Named(value = "usuarioAplicacionBean")
@RequestScoped
public class UsuarioAplicacionBean {

   private String pkIdUsuario;
   private Integer pkIdApp;
   
    
    private List<UsuarioTieneAplicacion> usuariosApps;
    private UsuarioTieneAplicacion usuarioAppSeleccionada = new UsuarioTieneAplicacion();
    UsuarioAplicacionDao usuarioAplicacionDao = new UsuarioAplicacionDaoImpl();
    


    /**
     * Crea una nueva instancia de UsuarioAplicacionBean
     */
    public UsuarioAplicacionBean() {
        this.usuariosApps = new ArrayList<UsuarioTieneAplicacion>();
        this.usuariosApps = usuarioAplicacionDao.buscarTodosUsuariosApps();
    }

    public List<UsuarioTieneAplicacion> getUsuariosApps() {
        return usuariosApps;
    }

    public UsuarioTieneAplicacion getUsuarioAppSeleccionada() {
        return usuarioAppSeleccionada;
    }

    public void setUsuarioAppSeleccionada(UsuarioTieneAplicacion usuarioAppSeleccionada) {
        this.usuarioAppSeleccionada = usuarioAppSeleccionada;
    }

    public UsuarioAplicacionDao getUsuarioAplicacionDao() {
        return usuarioAplicacionDao;
    }

    public void setUsuarioAplicacionDao(UsuarioAplicacionDao usuarioAplicacionDao) {
        this.usuarioAplicacionDao = usuarioAplicacionDao;
    }    

    public String getPkIdUsuario() {
        return pkIdUsuario;
    }

    public void setPkIdUsuario(String pkIdUsuario) {
        this.pkIdUsuario = pkIdUsuario;
    }

    public Integer getPkIdApp() {
        return pkIdApp;
    }

    public void setPkIdApp(Integer pkIdApp) {
        this.pkIdApp = pkIdApp;
    }
    
    
    
    
     public void btnAsignarUsApp(ActionEvent actionEvent) {
         
        if (usuarioAplicacionDao.registrarUsuarioApp(this.pkIdUsuario, this.pkIdApp)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Asignación de la app realizada exitosamente.");
            usuariosApps = new ArrayList<UsuarioTieneAplicacion>();
            usuariosApps.clear();
            usuariosApps = usuarioAplicacionDao.buscarTodosUsuariosApps();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al asignar la app.");
        }
    }

    public void btnActualizarUsApp(ActionEvent actionEvent) {
        if (usuarioAplicacionDao.actualizarUsuarioApp(this.usuarioAppSeleccionada)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "La información se ha actualizado correctamente.");
            usuariosApps = new ArrayList<UsuarioTieneAplicacion>();
            usuariosApps.clear();
            usuariosApps = usuarioAplicacionDao.buscarTodosUsuariosApps();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al actualizar la información.");
        }
    }

    public void btnEliminarUsApp(ActionEvent actionEvent) {
        if (usuarioAplicacionDao.eliminarUsuarioApp(this.usuarioAppSeleccionada.getPkIdUsuarioAplicacion())) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Registro eliminado.");
            usuariosApps = new ArrayList<UsuarioTieneAplicacion>();
            usuariosApps.clear();
            usuariosApps = usuarioAplicacionDao.buscarTodosUsuariosApps();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al eliminar el registro.");
        }
    }

    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Mensaje:", mensaje));
    }

    
}
