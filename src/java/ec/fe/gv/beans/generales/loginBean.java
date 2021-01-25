/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.beans.generales;

import ec.fe.gv.dao.PerfilDao;
import ec.fe.gv.common.HandlerUsuarios;
import ec.fe.gv.modelo.Usuario;
import ec.fe.gv.util.HibernateUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 * Clase para el inicio de sesión de los usuarios al sistema
 *
 * @author Jose07
 */
@ManagedBean
@SessionScoped
public class loginBean {

    String usuario; //nombre de usuario
    String password; //contraseña de usuario

    /**
     * Crea una nueva instancia del Constructor LoginBean, inicializador de las
     * variables de la clase
     */
    public loginBean() {
        this.usuario = "";
        this.password = "";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String login() {      
        String salida = "";
        FacesMessage msg = null;
                
        if (!usuario.equals("") && !password.equals("")) {

            boolean bAutenticacion = false;
            boolean bSession = false;

            bAutenticacion = HandlerUsuarios.autenticarUsuario(usuario, password);

            if (bAutenticacion) {
                bSession = HandlerUsuarios.iniciarSesion(usuario);
                if (bSession) {
                    String idTipoUsuario = HandlerUsuarios.obtenerTipoUsuario();
                    switch (idTipoUsuario) {
                        case PerfilDao.$USUARIO_NORMAL:
                            //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario);
                            salida = "vistas/menuUsuarioTemplate.xhtml?faces-redirect=true";
                            break;
                        case PerfilDao.$USUARIO_ADMINISTRADOR:
                            
                            // msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario);
                            salida = "vistas/menuAdminTemplate.xhtml?faces-redirect=true";
                            mostrarMensaje(FacesMessage.SEVERITY_INFO, "Bienvenido"+usuario);
                            break;
                        default:
                            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error de asociación de perfil al usuario");
                            break;
                    }
                } else {
                    mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al iniciar sesión");
                }
            } else {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Verifique el nombre de usuario y/o contraseña");
            }

        } else {
            if (usuario.equals("")) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "No se ha ingresado el usuario");
            }
            if (password.equals("")) {
                mostrarMensaje(FacesMessage.SEVERITY_ERROR, "No se ha ingresado la contraseña");
            }
        }
        return salida;
    }

    public String logout() {
        String ruta = "";

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) HibernateUtil.getSessionFactory();
        session.invalidate();
        ruta = "login.xhtml?faces-redirect=true";

        return ruta;
    }

//     Método para desplegar un mensaje en la interfaz
//     @param severityMessage Nivel de Severidad del Mensaje
//     @param mensaje mensaje a mostrar
    public void mostrarMensaje(FacesMessage.Severity severityMessage, String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severityMessage, "Atención!", mensaje));
    }
}
