
package com.servicios.eao;

import com.entidades.TblSubtema;
import com.entidades.TblUnidad;
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
 * @author mespinoza
 */
@Stateless
@LocalBean
public class SubtemaEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
     /*Metodo ingresa Subtema si no existe*/
    public Long ingresaSubtema(TblSubtema subtema)
    {
        try 
        {
            administradorEntidad.persist(subtema);
            administradorEntidad.flush();
            System.out.println("Subtema Id registrado:"+subtema.getSubtemaId());
            
            return subtema.getSubtemaId();
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaSubtema en SubtemaEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    /*Metodo obtiene Subtema según Unidad.-*/
    public List<TblSubtema> consultaSubtemaUnidad( Long cursoparalelo_cod, Long unidad_id, String estado )
    {
        try {
            System.out.println("consultaSubtemaUnidad Inicio");
            String sql    = " select s.subtema_id, s.unidad_id, s.subtema_desc, s.subtema_sts, s.subtema_clase, s.subtema_icono , u.unidad_nombre " +
                            " From tbl_subtema s " +
                            " Inner join tbl_unidad u on u.unidad_Id = s.unidad_id " +
                            " where ( s.subtema_sts = :estado or :estado = '' ) " +
                            " and ( s.unidad_id   = :unidad_id 	or :unidad_id   = 0   ) " +
                            " and ( u.cursoparalelo_id   = :cursoparalelo_cod 	or :cursoparalelo_cod  = 0   ) " +
                            " order by s.subtema_id "
                            ;
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", estado);
            query.setParameter("unidad_id", unidad_id );
            query.setParameter("cursoparalelo_cod", cursoparalelo_cod );

            System.out.println("Query consultaSubtemaUnidad : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<TblSubtema> listSubTema        = new ArrayList<TblSubtema>();
            lResult                            = query.getResultList();
          
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                TblSubtema subtema = new TblSubtema();
                TblUnidad unidad = new TblUnidad();
                unidad.setUnidadId(Long.valueOf(objects[1].toString()));
                unidad.setUnidadNombre((String) objects[6]);
                
                subtema.setSubtemaId(Long.valueOf(objects[0].toString()));
                subtema.setUnidadId(unidad);
                subtema.setSubtemaDesc((String) objects[2]);
                String estado1 = (String) objects[3];
                subtema.setSubtemaClase((String) objects[4]);
                subtema.setSubtemaIcono((String) objects[5]);
                 
                if(estado1.equals("A"))
                {
                    subtema.setSubtemaSts("ACTIVO");
                }
                else
                {
                    subtema.setSubtemaSts("INACTIVO");
                }
                
                 listSubTema.add(subtema);
            }
            
           return listSubTema; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaSubtemaUnidad en SubtemaEAO: "+e.getMessage());
            return null;
        }
    } 
    
    /*Metodo actualiza Subtema*/
    public void actualizaSubtema(TblSubtema subtema)
    {   
	administradorEntidad.merge(subtema);	
    }
    
    /*Metodo elimina Subtema*/
    public boolean eliminaSubtema(Long subtemaId)
    {
        boolean eliminado = false;
        
        try 
        {
            String sql  = "delete from tbl_subtema where subtema_id = :subtemaId  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("subtemaId", subtemaId);
            query.executeUpdate();
           
            eliminado = true;
        } catch (Exception e) 
        {
            eliminado = false;
            System.out.println("Error en metodo eliminaSubtema en SubtemaEAO: "+e.getMessage());

        }
        return  eliminado;
    }
 
}
