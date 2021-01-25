
import ec.fe.gv.common.HibernateSessionHandler;
import ec.fe.gv.common.UsuarioActual;
import ec.fe.gv.dao.AplicacionDao;
import ec.fe.gv.dao.AplicacionDaoImpl;
import ec.fe.gv.dao.NovActualizAppDao;
import ec.fe.gv.dao.NovActualizAppDaoImpl;
import ec.fe.gv.dao.UsuarioDao;
import ec.fe.gv.dao.UsuarioDaoImpl;
import ec.fe.gv.modelo.Aplicacion;
import ec.fe.gv.modelo.ArchivoConfiguracion;
import ec.fe.gv.modelo.NovedadActualizacionApp;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jose07
 */
public class pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//           java.util.Date date = new java.util.Date();
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
//        String fecha = sdf.format(date);
       HibernateSessionHandler sesion = new HibernateSessionHandler();

//         NovActualizAppDao dao = new NovActualizAppDaoImpl();
//         NovedadActualizacionApp nov = new NovedadActualizacionApp();
//         ArchivoConfiguracion archConfig = new ArchivoConfiguracion();
//         nov.setFechaActualizacionAplicacion(date);
//         nov.setDescripcionNovedadActualizacion("pruebaads");
//         nov.setDescripcionDosNovedad("prueba123");
      
//        AplicacionDao appDao = new AplicacionDaoImpl();
//                
     
//         List<Aplicacion> li = appDao.seleccionarAppsSegunUsuario("jose");
//     
       
          UsuarioDao usuarioDao = new UsuarioDaoImpl();               
     boolean b = usuarioDao.cambiarContrasena("vicente", "1234");
     System.out.println(b);
    //    List<Aplicacion> li = usuarioDao.seleccionarUsActual("jose");
         sesion.close();
//         
//         UsuarioDao usuarioDao = new UsuarioDaoImpl();
//        // Usuario usuario = new Usuario();
//         boolean b = usuarioDao.eliminarUsuario("prueba3");
//         System.out.println(b);
//         sesion.close();
    }
    
}
