package ec.fe.gv.common;

import ec.fe.gv.util.HibernateUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

/**
 *
 * @author Jose07
 */
public class HibernateSessionRequestFilter implements Filter {

    private SessionFactory sf;

    
    public void init(FilterConfig filterConfig) throws ServletException {
        sf = HibernateUtil.getSessionFactory();
    }

    
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            sf.getCurrentSession().beginTransaction();

            // Call the next filter (continue request processing)
            chain.doFilter(request, response);

            // Commit and cleanup
            sf.getCurrentSession().getTransaction().commit();

        } catch (StaleObjectStateException staleEx) {
            //TODO: IDENTIFICAR QUE LANZA ESTA EXCEPCION
            //log.error("This interceptor does not implement optimistic concurrency control!");
            //log.error("Your application will not work until you add compensation actions!");
            // Rollback, close everything, possibly compensate for any permanent changes
            // during the conversation, and finally restart business conversation. Maybe
            // give the user of the application a chance to merge some of his work with
            // fresh data... what you do here depends on your applications design.
            throw staleEx;
        } catch (Throwable ex) {
            Logger.getLogger(ManejoSesion.class.getName()).log(Level.SEVERE, ex.getMessage());
            try {
                if (sf.getCurrentSession().getTransaction().isActive()) {
                    Logger.getLogger(ManejoSesion.class.getName()).log(Level.SEVERE, ex.getMessage());
                    sf.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable rbEx) {
                Logger.getLogger(ManejoSesion.class.getName()).log(Level.SEVERE, rbEx.getMessage());
            }
        }
    }
    @Override
     public void destroy() {
    }
}
