/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.fe.gv.dao;

import ec.fe.gv.modelo.ArchivoConfiguracion;
import java.util.List;

/**
 *
 * @author Jose07
 */
public interface ArchivoConfiguracionDao {
    
    public ArchivoConfiguracion buscarPorArchivoConfiguracion(ArchivoConfiguracion archConfig);

    public List<ArchivoConfiguracion> buscarTodosArchivos();

    public boolean crearArchivoConfiguracion(ArchivoConfiguracion archConfig);

    public boolean actualizarArchivoConfiguracion(ArchivoConfiguracion archConfig);

    public boolean eliminarArchivoConfiguracion(Integer idArch);  
    
    public List<ArchivoConfiguracion> seleccionarArchivos();
    
    public List<ArchivoConfiguracion> seleccionarArchivosSegunApp();
    
    public List<ArchivoConfiguracion> seleccionarArchivosSegunUsuario();
}
