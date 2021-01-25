/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.admin;

import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.dao.NovActualizAppDao;
import ec.fe.gv.dao.NovActualizAppDaoImpl;
import ec.fe.gv.modelo.NovedadActualizacionApp;
import java.awt.event.ActionEvent;
import java.io.Serializable;
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
@Named(value = "novActualizBean")
@RequestScoped
public class NovActualizBean implements Serializable {

    private List<NovedadActualizacionApp> novedades;
    private List<NovedadActualizacionApp> novedadesPorUsuario;
    private NovedadActualizacionApp novedadSeleccionada = new NovedadActualizacionApp();
    NovActualizAppDao novedadDao = new NovActualizAppDaoImpl();

    /**
     * Creates a new instance of NovActualizBean
     */
    public NovActualizBean() {
        this.novedades = new ArrayList<NovedadActualizacionApp>();
        this.novedades = novedadDao.buscarTodasNovedades();
        this.novedadesPorUsuario = new ArrayList<NovedadActualizacionApp>();
        this.novedadesPorUsuario = novedadDao.buscarNovedadPorUsuario();
    }

    public List<NovedadActualizacionApp> getNovedades() {
        return novedades;
    }

    public NovedadActualizacionApp getNovedadSeleccionada() {
        return novedadSeleccionada;
    }

    public void setNovedadSeleccionada(NovedadActualizacionApp novedadSeleccionada) {
        this.novedadSeleccionada = novedadSeleccionada;
    }

    public NovActualizAppDao getNovedadDao() {
        return novedadDao;
    }

    public void setNovedadDao(NovActualizAppDao novedadDao) {
        this.novedadDao = novedadDao;
    }

    public List<NovedadActualizacionApp> getNovedadesPorUsuario() {
        return novedadesPorUsuario;
    }

    public void setNovedadesPorUsuario(List<NovedadActualizacionApp> novedadesPorUsuario) {
        this.novedadesPorUsuario = novedadesPorUsuario;
    }

    public void btnCrearAplicacion(ActionEvent actionEvent) {
        UsuarioActual ua = new UsuarioActual();
        this.novedadSeleccionada.setUsuarioRegActualizacion(ua.usuarioActual());
        if (novedadDao.crearNovedad(this.novedadSeleccionada)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Novedad registrada exitosamente.");
            novedades = new ArrayList<NovedadActualizacionApp>();
            novedades.clear();
            novedades = novedadDao.buscarTodasNovedades();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al registrar la novedad.");
        }
    }

    public void btnActualizarNovedad(ActionEvent actionEvent) {
        UsuarioActual ua = new UsuarioActual();
        this.novedadSeleccionada.setUsuarioRegActualizacion(ua.usuarioActual());
        if (novedadDao.actualizarNovedad(this.novedadSeleccionada)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "La información se ha actualizado correctamente.");
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al actualizar la información.");
        }
    }

    public void btnEliminarNovedad(ActionEvent actionEvent) {
        if (novedadDao.eliminarNovedad(this.novedadSeleccionada.getPkIdNovedadActualizacionApp())) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Registro eliminado.");
            novedades = new ArrayList<NovedadActualizacionApp>();
            novedades.clear();
            novedades = novedadDao.buscarTodasNovedades();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al eliminar el registro.");
        }
    }

    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Mensaje:", mensaje));
    }

}
