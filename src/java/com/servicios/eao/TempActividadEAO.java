
package com.servicios.eao;

import com.entidades.TblTempactiv;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author jespinoza
 */
@Stateless
@LocalBean
public class TempActividadEAO {
    
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo ingresa Temporal de Actividad .-*/
    public Long ingresaTempActiv(TblTempactiv tmp_activ)
    {
        try 
        {
            administradorEntidad.persist(tmp_activ);
            administradorEntidad.flush();
            System.out.println("Temporal de actividad Id registrado:"+tmp_activ.getTempActivId());
            
            return tmp_activ.getTempActivId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaTempActiv en TempActividadEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    /*Metodo elimina Temporal de Actividad por Usuario.-*/
    public boolean eliminaTempActiv(Long usuario_Cod , String tipo)
    {
        boolean eliminado = false;
        try 
        {
            String sql  = "delete from tbl_tempActiv "
                    + " where tempActiv_UsrCod = :UsuarioCod "
                    + " and ( tempActiv_Tipo = :tipo or :tipo = '' ) " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("UsuarioCod", usuario_Cod);
            query.setParameter("tipo", tipo);
            query.executeUpdate();
           
           eliminado = true;
            
        } catch (Exception e) 
        {
            eliminado = false;
            System.out.println("Error en metodo eliminaTempActiv en TempActividadEAO: "+e.getMessage());

        }
         return  eliminado;
    }
 
    /*Metodo consulta Temporal de Actividad según Usuario*/
    public List<TblTempactiv> consultaTmpActividad( Long usuario_cod, String tipo )
    {
        try {
            System.out.println("consultaTmpActividad Inicio");
            String sql   = " select t.tempActiv_Id, t.tempActiv_UsrCod, t.tempActiv_IdRef, t.tempActiv_Sts , t.tempActiv_Tipo "+
                            " From tbl_tempActiv t " +
                            " where t.tempActiv_Sts = :estado " +
                            " and t.tempActiv_UsrCod = :UsuarioCod "+
                            " and ( t.tempActiv_Tipo = :tipo or :tipo = '' ) "
                            ;
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("UsuarioCod", usuario_cod);
            query.setParameter("tipo", tipo);
            
            System.out.println("Query consultaTmpActividad : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<TblTempactiv> listTmpActiv        = new ArrayList<TblTempactiv>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                TblTempactiv tmpActiv = new TblTempactiv();
                tmpActiv.setTempActivId(Long.valueOf(objects[0].toString()));
                tmpActiv.setTempActivUsrCod(Long.valueOf(objects[1].toString()));
                tmpActiv.setTempActivIdRef(Long.valueOf(objects[2].toString()));
                tmpActiv.setTempActivSts((String) objects[3]);
                tmpActiv.setTempActivTipo((String) objects[4]);
                
                 listTmpActiv.add(tmpActiv);
            }
            
           return listTmpActiv; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaTmpActividad en TempActividadEAO: "+e.getMessage());
            return null;
        }
    } 
 

    
}
