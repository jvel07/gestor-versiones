/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.admin;

import ec.fe.gv.dao.ArchivoConfiguracionDao;
import ec.fe.gv.dao.ArchivoConfiguracionDaoImpl;
import ec.fe.gv.modelo.ArchivoConfiguracion;
import java.awt.event.ActionEvent;
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
@Named(value = "archivoConfiguracionBean")
@RequestScoped
public class ArchivoConfiguracionBean {

    private List<ArchivoConfiguracion> archivosConfig;
    private List<ArchivoConfiguracion> archivosConfigPorUsuario;
    private ArchivoConfiguracion archConfigSeleccionado = new ArchivoConfiguracion();
    ArchivoConfiguracionDao archivoConfigDao = new ArchivoConfiguracionDaoImpl();

    private List<SelectItem> seleccionarUnArchivo;
    private List<SelectItem> seleccionarUnArchivoSegunUs;

    /**
     * Crea una nueva instancia de ArchivoConfiguracionBean
     */
    public ArchivoConfiguracionBean() {
        this.archivosConfig = new ArrayList<ArchivoConfiguracion>();
        this.archivosConfig = archivoConfigDao.buscarTodosArchivos();
        this.archivosConfigPorUsuario = new ArrayList<ArchivoConfiguracion>();
        this.archivosConfigPorUsuario = archivoConfigDao.seleccionarArchivosSegunUsuario();
    }

    public List<ArchivoConfiguracion> getArchivosConfig() {
        return archivosConfig;
    }

    public ArchivoConfiguracion getArchConfigSeleccionado() {
        return archConfigSeleccionado;
    }

    public void setArchConfigSeleccionado(ArchivoConfiguracion archConfigSeleccionado) {
        this.archConfigSeleccionado = archConfigSeleccionado;
    }

    public ArchivoConfiguracionDao getArchivoConfiguracionDao() {
        return archivoConfigDao;
    }

    public void setArchivoConfiguracionDao(ArchivoConfiguracionDao archivoConfigDao) {
        this.archivoConfigDao = archivoConfigDao;
    }

    public List<SelectItem> getSeleccionarUnArchivo() {
        this.seleccionarUnArchivo = new ArrayList<SelectItem>();
        List<ArchivoConfiguracion> listaArchivos = archivoConfigDao.seleccionarArchivos();
        for (ArchivoConfiguracion archivos : listaArchivos) {
            SelectItem selectItem = new SelectItem(archivos.getPkIdArchivoConfiguracion(), archivos.getNombreArchivo());
            this.seleccionarUnArchivo.add(selectItem);
        }
        return seleccionarUnArchivo;
    }

    public List<SelectItem> getSeleccionarUnArchivoSegunUs() {
        this.seleccionarUnArchivoSegunUs = new ArrayList<SelectItem>();
        List<ArchivoConfiguracion> listaArchivos = archivoConfigDao.seleccionarArchivosSegunUsuario();
        for (ArchivoConfiguracion archivos : listaArchivos) {
            SelectItem selectItem = new SelectItem(archivos.getPkIdArchivoConfiguracion(), archivos.getNombreArchivo());
            this.seleccionarUnArchivoSegunUs.add(selectItem);
        }
        return seleccionarUnArchivoSegunUs;
    }

    public List<ArchivoConfiguracion> getArchivosConfigPorUsuario() {
        return archivosConfigPorUsuario;
    }

    public void setArchivosConfigPorUsuario(List<ArchivoConfiguracion> archivosConfigPorUsuario) {
        this.archivosConfigPorUsuario = archivosConfigPorUsuario;
    }

    public void btnCrearArchivo(ActionEvent actionEvent) {
        if (archivoConfigDao.crearArchivoConfiguracion(this.archConfigSeleccionado)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Archivo registrado exitosamente.");
            archivosConfig = new ArrayList<ArchivoConfiguracion>();
            archivosConfig.clear();
            archivosConfig = archivoConfigDao.buscarTodosArchivos();
            this.archivosConfigPorUsuario = new ArrayList<ArchivoConfiguracion>();
            archivosConfigPorUsuario.clear();
            this.archivosConfigPorUsuario = archivoConfigDao.seleccionarArchivosSegunUsuario();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al registrar el archivo.");
        }
    }

    public void btnActualizarArchivo(ActionEvent actionEvent) {
        if (archivoConfigDao.actualizarArchivoConfiguracion(this.archConfigSeleccionado)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "La información del archivo se ha actualizado correctamente.");
            archivosConfig = new ArrayList<ArchivoConfiguracion>();
            archivosConfig.clear();
            archivosConfig = archivoConfigDao.buscarTodosArchivos();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al actualizar la información.");
        }
    }

    public void btnEliminarArchivo(ActionEvent actionEvent) {
        if (archivoConfigDao.eliminarArchivoConfiguracion(this.archConfigSeleccionado.getPkIdArchivoConfiguracion())) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Archivo eliminado.");
            archivosConfig = new ArrayList<ArchivoConfiguracion>();
            archivosConfig.clear();
            archivosConfig = archivoConfigDao.buscarTodosArchivos();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al eliminar el archivo.");
        }
    }

    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Mensaje:", mensaje));
    }

}
