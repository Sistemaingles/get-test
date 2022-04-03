
package com.servicios.eao;

import com.entidades.TblVocabulario;
import com.entidades.listas.VocabularioList;
import java.util.ArrayList;
import java.util.Date;
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
public class VocabularioEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo ingresa Vocabulario si no existe*/
    public Long ingresaVocabulario(TblVocabulario vocabulario)
    {
        try 
        {
            administradorEntidad.persist(vocabulario);
            administradorEntidad.flush();
            System.out.println("Vocabulario Id registrado:"+vocabulario.getVocabularioId());
            
            return vocabulario.getVocabularioId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaVocabulario en VocabularioEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    /*Metodo obtiene Actividad Registrado y con Curso asignado*/
    public List<VocabularioList> consultaVocabulario( Long unidad_id)
    {
        try {
            System.out.println("consultaVocabulario Inicio");
            String sql   = " select v.vocabulario_Id, v.unidad_Id, u.unidad_nombre, v.vocabulario_descripcion, v.vocabulario_sts, v.vocabulario_instim , v.vocabulario_insusr  " +
                            " From tbl_vocabulario v " +
                            " Inner join tbl_unidad u on u.unidad_Id = v.unidad_Id "+
                            " where  " +
                            "  (v.unidad_Id = :UnidadId or :UnidadId = 0 ) "+
                            " order by v.vocabulario_Id ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            //query.setParameter("estado", "A");
            query.setParameter("UnidadId",  unidad_id  );
            
            
            System.out.println("Query consultaVocabulario : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<VocabularioList> listVocabulario  = new ArrayList<VocabularioList>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                VocabularioList vocabulario = new VocabularioList();
                vocabulario.setVocabularioId(Long.valueOf(objects[0].toString()));
                vocabulario.setUnidadId(Long.valueOf(objects[1].toString()));
                vocabulario.setUnidad_nombre((String) objects[2]);
                vocabulario.setVocabularioDescripcion((String) objects[3]);
                estado = (String) objects[4];
         
                if(estado.equals("A"))
                {
                    vocabulario.setVocabularioSts("ACTIVO");
                }
                else
                {
                    vocabulario.setVocabularioSts("INACTIVO");
                }
                
                vocabulario.setVocabularioInstim((Date) objects[5]);
                vocabulario.setVocabularioInsUsr((String) objects[6]);
                listVocabulario.add(vocabulario);
            }
            
           return listVocabulario; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaVocabulario en vocabularioEAO: "+e.getMessage());
            return null;
        }
    } 
    
    /*Metodo actualiza Vocabulario*/
    public void actualizaVocabulario(TblVocabulario vocabulario)
    {   
	administradorEntidad.merge(vocabulario);	
    }
    
    public boolean eliminaVocabulario(Long vocabulario_id)
    {
        boolean eliminado = false;
        
        try 
        {
            String sql  = "delete from tbl_vocabulario where vocabulario_Id = :vocabularioId  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("vocabularioId", vocabulario_id);
            query.executeUpdate();
           
            eliminado = true;
        } catch (Exception e) 
        {
            eliminado = false;
            System.out.println("Error en metodo eliminaVocabulario en vocabularioEAO: "+e.getMessage());

        }
        return  eliminado;
    }
 
}
