
package com.servicios.eao;

import com.entidades.TblResultadotaller;
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
public class resultadoTallerEao {
    
   @PersistenceContext(unitName="project",type = PersistenceContextType.TRANSACTION)
    protected EntityManager administradorEntidad;
    private String sql;  
    
    public void ingresaResultadoTaller(TblResultadotaller resultado)
    {
        try 
        {
            System.out.println("ingresaResultadoTaller en resultadoTallerEao");
            administradorEntidad.persist(resultado);
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresar ingresaResultadoTaller en resultadoTallerEao: "+e.getMessage());
        }
    }
    
    public void inactivaResultadoTaller(Long tallerId, Long subtemaId)
    {
        try 
        {
            sql  = " update tbl_resultadotaller t" +
                    " set t.resultado_sts     = 'I' , "+
                    "     t.resultado_dlttim  = sysdate() " +
                    " where t.taller_Id = :tallerId " + 
                    " and t.subtema_id = :subtemaId " +
                    " and t.resultado_sts = 'A'  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("tallerId", tallerId);
            query.setParameter("subtemaId", subtemaId);
            query.executeUpdate();       
                    
    
            
        } catch (Exception e) 
        {
            System.out.println("Error en Metodo inactivaResultadoTaller en resultadoTallerEao: "+e.getMessage());
        }
    }
    public void eliminaResultadoTaller(TblResultadotaller resultado)
    {
        try 
        {
          
            sql  = "delete from tbl_resultadotaller where resultado_Id = :id  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("id", resultado.getResultadoId());
            query.executeUpdate();
            
        } catch (Exception e) 
        {
            System.out.println("Error al eliminar eliminaResultadoTaller en resultadoTallerEao: "+e.getMessage());
        }
    }
    
    public List<TblResultadotaller> obtenerResultadoTaller() 
    {
        try 
        {
            sql = " select * "+
                  " from tbl_resultadotaller  order by resultado_Id ";
            
            Query query = administradorEntidad.createNativeQuery(sql,TblResultadotaller.class);
            return query.getResultList();
           
        }catch(Exception ex) 
        {
            System.out.println("ERROR al obtenerResultadoTaller en resultadoTallerEao:"+ex.getMessage());
            return null;
	}	
    }
    

}



