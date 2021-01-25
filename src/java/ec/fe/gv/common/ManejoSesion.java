/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.fe.gv.common;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Jose07
 */
public class ManejoSesion implements Serializable{
    
    private HttpSession sesion;
    
    public ManejoSesion(){
        try {
            sesion = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        }catch(Exception e){
            Logger.getLogger(ManejoSesion.class.getName()).log(Level.SEVERE, "Sesión http obtenida no válida");
            sesion = null;
        }
    }

    public HttpSession getSesion() {
        return sesion;
    }

    public void setSesion(HttpSession sesion) {
        this.sesion = sesion;
    }
    
    private String getIdUsuario(){
        return sesion.getAttribute("pkIdUsuario").toString();
    }
    
    private void setIdUsuario(String idUsuario){
        sesion.setAttribute("pkIdUsuario", idUsuario);
    }

    private String getIdPerfil() {
        return sesion.getAttribute("pkIdPerfil").toString();
    }

    private void setIdPerfil(String idPerfil) {
        sesion.setAttribute("pkIdPerfil", idPerfil);
    }


    /**
     * Método que añade los atributos a la sesión http
     * @param idUsuario nombre de usuario
     * @param idPerfil perfil de acceso de usuario
     */
    public void iniciarSesion(String idUsuario, String idPerfil){
        setIdUsuario(idUsuario);
        setIdPerfil(idPerfil);
    }

    /**
     * Método que elimina los atributos de la sesión http
     */
    public void cerrarSesion(){
        sesion.removeAttribute("pkIdUsuario");
        sesion.removeAttribute("pkIdPerfil");        
    }

    /**
     * Obtiene el nombre de usuario del usuario en sesión
     * @return nombre de usuario
     */
    public String nombreUsuarioActual(){
        if (estadoSesion()) return getIdUsuario();
        else return null;
    }
    
    /**
     * Obtiene el id del nivel de seguridad del usuario en sesion
     * @return idNivel de usuario
     */
    public String idPerfilActual(){
        if (estadoSesion()) return getIdPerfil();
        else return null;
    }
    
    /**
     * Obtiene el id del nivel del usuario en sesion
     * @return idUsuario en sesion
     */
    public String idUsuario(){
        if (estadoSesion()) return getIdUsuario();
        else return null;
    }

    /**
     * Obtiene el estado de la sesión
     * @return true si existe sesión, de lo contrario false
     */
    public boolean estadoSesion(){
        boolean success = false;
        if (sesion!=null){
            try {
                if (!getIdUsuario().equals("")){
                    success = true;
                }
            }catch(Exception e){
                success = false;
            }
        }
        return success;
    }
}
