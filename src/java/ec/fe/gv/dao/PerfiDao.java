/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.dao;

import ec.fe.gv.modelo.Perfil;
import java.util.List;

/**
 *
 * @author Jose07
 */
public interface PerfiDao {

    public Perfil buscarPorPerfil(Perfil perfil);

    public List<Perfil> buscarTodosPerfiles();

    public boolean crearPerfil(Perfil perfil);

    public boolean actualizarPerfil(Perfil perfil);

    public boolean eliminarPerfil(String perfil);

    public List<Perfil> seleccionarPerfiles();
    
    public Perfil obtenerPerfilUsActual(Perfil perfil);
}
