/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.modelo.NovedadActualizacionApp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jose07
 */
public interface NovActualizAppDao {

    public NovedadActualizacionApp buscarPorNovedad(NovedadActualizacionApp novedadAct);

    public List<NovedadActualizacionApp> buscarTodasNovedades();
    
    public List<NovedadActualizacionApp> buscarNovedadPorUsuario();

    public boolean crearNovedad(NovedadActualizacionApp novedadAct);

    public boolean actualizarNovedad(NovedadActualizacionApp novedadAct);

    public boolean eliminarNovedad(Integer idNovedad);
    
    public Date convertirStringADate(String fecha); 
}
