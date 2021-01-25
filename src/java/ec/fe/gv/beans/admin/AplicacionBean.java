/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.admin;

import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.dao.AplicacionDao;
import ec.fe.gv.dao.AplicacionDaoImpl;
import ec.fe.gv.dao.UsuarioAplicacionDao;
import ec.fe.gv.dao.UsuarioAplicacionDaoImpl;
import ec.fe.gv.dao.UsuarioDao;
import ec.fe.gv.dao.UsuarioDaoImpl;
import ec.fe.gv.modelo.Aplicacion;
import ec.fe.gv.modelo.Usuario;
import ec.fe.gv.modelo.UsuarioTieneAplicacion;
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
@Named(value = "aplicacionBean")
@RequestScoped
public class AplicacionBean {

    private List<Aplicacion> aplicaciones;
    private List<Aplicacion> aplicacionesPorUsuario;
    private Aplicacion aplicacionSeleccionada = new Aplicacion();
    private Usuario usuario = new Usuario();
    private UsuarioTieneAplicacion usuarioAplicacion = new UsuarioTieneAplicacion();

    AplicacionDao aplicacionDao = new AplicacionDaoImpl();
    UsuarioDao usuarioDao = new UsuarioDaoImpl();

    String pkUsuario, pkPerfil;
    private List<SelectItem> seleccionarUnaApp;
    private List<SelectItem> seleccionarAppSegunUs;

    /**
     * Crea una nueva instancia de AplicacionBean
     */
    public AplicacionBean() {
        this.aplicaciones = new ArrayList<Aplicacion>();
        this.aplicaciones = aplicacionDao.buscarTodasApps();
        this.aplicacionesPorUsuario = new ArrayList<Aplicacion>();
        this.aplicacionesPorUsuario = aplicacionDao.seleccionarAppsSegunUsuario();

    }

    public List<Aplicacion> getAplicaciones() {
        return aplicaciones;
    }

    public Aplicacion getAplicacionSeleccionada() {
        return aplicacionSeleccionada;
    }

    public void setAplicacionSeleccionada(Aplicacion aplicacionSeleccionada) {
        this.aplicacionSeleccionada = aplicacionSeleccionada;
    }

    public AplicacionDao getAplicacionDao() {
        return aplicacionDao;
    }

    public void setAplicacionDao(AplicacionDao aplicacionDao) {
        this.aplicacionDao = aplicacionDao;
    }

    public List<SelectItem> getSeleccionarUnaApp() {
        this.seleccionarUnaApp = new ArrayList<SelectItem>();
        List<Aplicacion> listaApps = aplicacionDao.seleccionarApps();
        for (Aplicacion aplicacion : listaApps) {
            SelectItem selectItem = new SelectItem(aplicacion.getPkIdAplicacion(), aplicacion.getNombreAplicacion());
            this.seleccionarUnaApp.add(selectItem);
        }
        return seleccionarUnaApp;
    }

    public List<SelectItem> getSeleccionarAppSegunUs() {
        this.seleccionarAppSegunUs = new ArrayList<SelectItem>();
        List<Aplicacion> listaAppsPorUs = aplicacionDao.seleccionarAppsSegunUsuario();
        for (Aplicacion aplicacion : listaAppsPorUs) {
            SelectItem selectItem = new SelectItem(aplicacion.getPkIdAplicacion(), aplicacion.getNombreAplicacion());
            this.seleccionarAppSegunUs.add(selectItem);
        }
        return seleccionarAppSegunUs;
    }

    public List<Aplicacion> getAplicacionesPorUsuario() {
        return aplicacionesPorUsuario;
    }

    public void setAplicacionesPorUsuario(List<Aplicacion> aplicacionesPorUsuario) {
        this.aplicacionesPorUsuario = aplicacionesPorUsuario;
    }

    public UsuarioTieneAplicacion getUsuarioAplicacion() {
        this.usuarioAplicacion = new UsuarioTieneAplicacion(null, aplicacionSeleccionada);
        return usuarioAplicacion;
    }

    public void setUsuarioAplicacion(UsuarioTieneAplicacion usuarioAplicacion) {
        this.usuarioAplicacion = usuarioAplicacion;
    }

    public void btnCrearAplicacion(ActionEvent actionEvent) {
        // String pk = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        //TODO: obtener el usuario con pk

        UsuarioActual usuarioActual = new UsuarioActual();
        pkUsuario = usuarioActual.usuarioActual();
        //TODO: validar si es admin o normal       
        pkPerfil = usuarioDao.seleccionarPerfUsActual();
        switch (pkPerfil) {
            case "administrador":
                if (aplicacionDao.crearAplicacion(this.aplicacionSeleccionada)) {

                    /*
                     admin insertar sin auto asignacion
                     normal insertar la app, obtiene la misma app y genera el insert de la asignación con el usuario y la app obtenida
                     */
                    mostrarMensaje(FacesMessage.SEVERITY_INFO, "App: " + this.aplicacionSeleccionada.getNombreAplicacion() + " registrada exitosamente.");
                    aplicaciones = new ArrayList<Aplicacion>();
                    aplicaciones.clear();
                    aplicaciones = aplicacionDao.buscarTodasApps();
                } else {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al registrar la app "+this.aplicacionSeleccionada.getNombreAplicacion()+" ya existe!");
                }
                break;

            case "normal":
                UsuarioAplicacionDao usuarioAplicacionDao = new UsuarioAplicacionDaoImpl();
                if (aplicacionDao.crearAplicacion(this.aplicacionSeleccionada) && usuarioAplicacionDao.registrarUsuarioApp(pkUsuario, this.aplicacionSeleccionada.getPkIdAplicacion())) {
                    aplicaciones = new ArrayList<Aplicacion>();
                    aplicaciones.clear();
                    aplicaciones = aplicacionDao.seleccionarAppsSegunUsuario();
                    mostrarMensaje(FacesMessage.SEVERITY_INFO, "App: " + this.aplicacionSeleccionada.getNombreAplicacion()
                            + " registrada exitosamente y asignada al usuario: " + pkUsuario);
                } else {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al registrar la app "+this.aplicacionSeleccionada.getNombreAplicacion()+" ya existe!");
                }
                break;

            default:
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al registrar la app.");
                break;
        }
    }

    public void btnActualizarAplicacion(ActionEvent actionEvent) {
        if (aplicacionDao.actualizarAplicacion(this.aplicacionSeleccionada)) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "La información de la app se ha actualizado correctamente.");
            aplicaciones = new ArrayList<Aplicacion>();
            aplicaciones.clear();
            aplicaciones = aplicacionDao.buscarTodasApps();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al actualizar la información.");
        }
    }

    public void btnEliminarAplicacion(ActionEvent actionEvent) {
        // UsuarioAplicacionDao usuarioAplicacionDao = new UsuarioAplicacionDaoImpl();
        // usuarioAplicacionDao.eliminarUsuarioApp(this.usuarioAplicacion.getPkIdUsuarioAplicacion());
        if (aplicacionDao.eliminarAplicacion(this.aplicacionSeleccionada.getPkIdAplicacion())) {
            mostrarMensaje(FacesMessage.SEVERITY_INFO, "App eliminada.");
            aplicaciones = new ArrayList<Aplicacion>();
            aplicaciones.clear();
            aplicaciones = aplicacionDao.buscarTodasApps();
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al eliminar la app.");
        }
    }

    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Mensaje:", mensaje));
    }

}
