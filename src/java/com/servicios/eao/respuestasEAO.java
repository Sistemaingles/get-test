
package com.servicios.eao;

import com.entidades.TblRespuesta;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author mespinoza
 */
@Stateless
@LocalBean
public class respuestasEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo ingresa Respuestas si no existe*/
    
    public Long ingresaRespuesta(TblRespuesta respuesta)
    {
        try 
        {
            administradorEntidad.persist(respuesta);
            administradorEntidad.flush();
            System.out.println("Respuesta Id registrado:"+respuesta.getActividadId());
            
            return respuesta.getRespuestaId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error en metodo ingresaRespuesta en respuestasEAO: "+e.getMessage());
            return null;
        }
    }
 
    public boolean eliminaRespuesta(Long actividad_id)
    {
        boolean eliminado = false;
        
        try 
        {
            String sql  = "delete from tbl_respuesta where actividad_id = :actividad_id  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("actividad_id", actividad_id);
            query.executeUpdate();
           
            eliminado = true;
        } catch (Exception e) 
        {
            eliminado = false;
            System.out.println("Error en metodo eliminaRespuesta en respuestasEAO: "+e.getMessage());

        }
        return  eliminado;
    }
 
    
    
    
}
