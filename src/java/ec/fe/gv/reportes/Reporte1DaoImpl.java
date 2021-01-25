/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.fe.gv.reportes;

import ec.fe.gv.dao.DAO;
import ec.fe.gv.modelo.Aplicacion;
import ec.fe.gv.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Jose07
 */
public class Reporte1DaoImpl implements Reporte1Dao {

    @Override
    public List<Reporte1> reporteApp() {

        
        Session session = HibernateUtil.getSessionFactory().openSession();
        String queryRepApp = "SELECT fk_usuario_tiene_aplicacion_usuario, nombreAplicacion, numEjecNoPermitidas "
                + "FROM usuario_tiene_aplicacion ua\n"
                + "join aplicacion app on ua.fk_usuario_tiene_aplicacion_aplicacion = app.pk_idAplicacion";
        List<Reporte1> result = null;
        
//        Select app FROM Usuario as u join u.usuarioTieneAplicacions as tapp join 
//                tapp.aplicacion as app where u.pkIdUsuario=:userID

        try {
            Query query = session.createSQLQuery(queryRepApp).setResultTransformer(Transformers.aliasToBean(ec.fe.gv.reportes.Reporte1.class));
            result = query.list();
        } catch (HibernateException e) {
            session.beginTransaction().rollback();
        }

        return result;
    }

}
