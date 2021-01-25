/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.admin;

import ec.fe.gv.dao.PerfiDao;
import ec.fe.gv.dao.PerfilDaoImpl;
import ec.fe.gv.modelo.Perfil;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Jose07
 */
@Named(value = "perfilBean")
@RequestScoped
public class PerfilBean implements Serializable {

    private List<Perfil> perfiles;
    private Perfil perfilSeleccionado = new Perfil();
    PerfiDao perfilDao = new PerfilDaoImpl();
    
    private List<SelectItem> seleccionarUnPerfil;

    /**
     * Creates a new instance of PerfilBean
     */
    public PerfilBean() {
        this.perfiles = new ArrayList<Perfil>();
        this.perfiles = perfilDao.buscarTodosPerfiles();      
    }

    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    public Perfil getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Perfil perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
    }

    public List<SelectItem> getSeleccionarUnPerfil() {
        this.seleccionarUnPerfil = new ArrayList<SelectItem>();
        List<Perfil> listaPerfiles = perfilDao.seleccionarPerfiles();
        for (Perfil perfil : listaPerfiles) {
            SelectItem selectItem = new SelectItem(perfil.getPkIdPerfil());
            this.seleccionarUnPerfil.add(selectItem);
        }
        return seleccionarUnPerfil;
    }
    
    
    

    public void btnCrearPerfil(ActionEvent actionEvent) {
        if (perfilDao.crearPerfil(this.perfilSeleccionado)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Perfil creado exitosamente.");
            perfiles = new ArrayList<Perfil>();
            perfiles.clear();
            perfiles = perfilDao.buscarTodosPerfiles();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al crear el perfil.");
        }
    }

    public void btnActualizarPerfil(ActionEvent actionEvent) {
        if (perfilDao.actualizarPerfil(this.perfilSeleccionado)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "La información del perfil se ha actualizado correctamente.");
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al actualizar la información.");
        }
    }

    public void btnEliminarPerfil(ActionEvent actionEvent) {
        if (perfilDao.eliminarPerfil(this.perfilSeleccionado.getPkIdPerfil())) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Perfil eliminado.");
            perfiles = new ArrayList<Perfil>();
            perfiles.clear();
            perfiles = perfilDao.buscarTodosPerfiles();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al eliminar el perfil.");
        }
    }

    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Mensaje:", mensaje));
    }

}
