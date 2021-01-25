/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.modelo.UsuarioTieneAplicacion;
import java.util.List;

/**
 *
 * @author Jose07
 */
public interface UsuarioAplicacionDao {

    public UsuarioTieneAplicacion buscarPorUsuarioApp(UsuarioTieneAplicacion usuarioApp);
    
    public UsuarioTieneAplicacion buscarAsignaciones(UsuarioTieneAplicacion usuarioApp);

    public List<UsuarioTieneAplicacion> buscarTodosUsuariosApps();

    public boolean crearUsuarioApp(UsuarioTieneAplicacion usuarioApp);
    
    public boolean registrarUsuarioApp(String fkIdUsuarioApp, Integer fkIdApp);

    public boolean actualizarUsuarioApp(UsuarioTieneAplicacion usuarioApp);

    public boolean eliminarUsuarioApp(Integer idUsuarioApp);

    public List<UsuarioTieneAplicacion> seleccionarUsuarioTieneApps(); 
}
