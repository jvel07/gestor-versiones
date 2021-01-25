/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.modelo.Aplicacion;
import java.util.List;

/**
 *
 * @author Jose07
 */
public interface AplicacionDao {

    public Aplicacion buscarPorAplicacion(Aplicacion app);

    public List<Aplicacion> buscarTodasApps();

    public boolean crearAplicacion(Aplicacion app);

    public boolean actualizarAplicacion(Aplicacion app);

    public boolean eliminarAplicacion(Integer idApp);
    
    public List<Aplicacion> seleccionarApps();
    
    public List<Aplicacion> seleccionarAppsSegunUsuario();

}
