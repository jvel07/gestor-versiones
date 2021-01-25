/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.generales;

import ec.fe.gv.dao.UsuarioDao;
import ec.fe.gv.dao.UsuarioDaoImpl;
import ec.fe.gv.modelo.Usuario;
import ec.fe.gv.util.MyUtil;
import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jose07
 */
@Named(value = "loginBean2")
@SessionScoped
public class loginBean2 implements Serializable {

    private Usuario usuario;
    private UsuarioDao usuarioDao;

    public loginBean2() {
        this.usuarioDao = new UsuarioDaoImpl();
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }


       

    public void login(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();

        FacesMessage msg;
        boolean loggedIn;
        String ruta = "";

        this.usuario = this.usuarioDao.loginUsuario(this.usuario);
        if (this.usuario != null) {
            loggedIn = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.usuario.getPkIdUsuario());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrasena", this.usuario.getContrasena());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", this.usuario.getPkIdUsuario());
            if (this.usuario.getPerfil().getPkIdPerfil().equals("administrador")) {
                ruta = MyUtil.basepathlogin() + "vistas/menuAdminTemplate.xhtml";
            } else {
                ruta = MyUtil.basepathlogin() + "vistas/menuUsuarioTemplate.xhtml";
            }
        } else {
            loggedIn = false;
            this.usuarioDao = new UsuarioDaoImpl();
            if (this.usuario == null) {
                this.usuario = new Usuario();
            }
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de inicio de sesión", "Credenciales no válidas");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("ruta", ruta);
    }

    public void logOut() {
        String ruta = MyUtil.basepathlogin() + "login2.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession sesion = (HttpSession) facesContext.getExternalContext().getSession(false);
        sesion.invalidate();

        context.addCallbackParam("loggedOut", true);
        context.addCallbackParam("ruta", ruta);
    }

}
