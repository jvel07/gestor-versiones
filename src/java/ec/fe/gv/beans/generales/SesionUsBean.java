/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.fe.gv.beans.generales;

import ec.fe.gv.common.HandlerUsuarios;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jose07
 */
@Named(value = "sesionUsBean")
@ViewScoped
public class SesionUsBean implements Serializable{

     private String idUsuario;
    /**
     * Creates a new instance of SesionUsBean
     */
    public SesionUsBean() {
        idUsuario = HandlerUsuarios.obtenerIdUsuarioActual();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    
}
