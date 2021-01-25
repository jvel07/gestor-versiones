package ec.fe.gv.common;

import ec.fe.gv.util.HibernateUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;

/**
 *
 * @author Jose07
 */
public class HibernateSessionHandler {
    
    private SessionFactory sf;
    
    public HibernateSessionHandler() {
        sf = HibernateUtil.getSessionFactory();
        
        try {
            sf.getCurrentSession().beginTransaction();
        }catch(Exception e){
            Logger.getLogger(ManejoSesion.class.getName()).log(Level.SEVERE, e.getMessage());
            System.out.println("sessionhandler error--> "+e.toString());
        }
    }
    
    public void close(){
        try{
            sf.getCurrentSession().getTransaction().commit();
        }catch(Exception e){
            if (sf.getCurrentSession().getTransaction().isActive()) {
                Logger.getLogger(ManejoSesion.class.getName()).log(Level.SEVERE, "Ejecutando rollback de la transacción");
                sf.getCurrentSession().getTransaction().rollback();
            }
            Logger.getLogger(ManejoSesion.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        
    }
    
    
}
