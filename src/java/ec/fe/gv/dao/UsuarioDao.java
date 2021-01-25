/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.fe.gv.dao;

import ec.fe.gv.modelo.Usuario;
import java.util.List;

/**
 *
 * @author Jose07
 */
public interface UsuarioDao {
    public Usuario buscarPorUsuario(Usuario usuario);
    public List<Usuario> buscarTodosUsuarios();
    public boolean crearUsuario(Usuario usuario);
    public boolean actualizarUsuario(Usuario usuario);
    public boolean eliminarUsuario(String usuario);
    public boolean cambiarContrasena(String idUsuario, String contrasena);
    public List<Usuario> seleccionarUsuarios();
    public List<Usuario> seleccionarUsActual();
    public String seleccionarPerfUsActual();
    public Usuario loginUsuario(Usuario usuario);       
     
}
