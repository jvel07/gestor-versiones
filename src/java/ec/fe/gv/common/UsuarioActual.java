/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.common;

import ec.fe.gv.util.AutorizationListener;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jose07
 */
public class UsuarioActual {
//Obtiene el usuario de la sesión actual

    public String usuarioActual() {

        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Object usuarioSesion = sesion.getAttribute("usuario");
        return usuarioSesion.toString();
    }

    public String contrasenaActual() {

        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Object usuarioSesion = sesion.getAttribute("contrasena");
        return usuarioSesion.toString();
    }

}
