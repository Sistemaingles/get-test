
package com.servicios.eao;

import com.entidades.TblDestrezaunidad;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author jespinoza
 */

@Stateless
@LocalBean
public class DestrezaUnidadEAO {
     @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
     protected EntityManager administradorEntidad;
     
    /*Metodo ingresa Destreza Unidad si no existe*/
    public void ingresaDestrezaUnidad(TblDestrezaunidad destrezaUnidad)
    {
        try 
        {
            administradorEntidad.persist(destrezaUnidad);
            administradorEntidad.flush();
            System.out.println("DestrezaUnidad Id registrado:"+destrezaUnidad.getDestrezaunidadId());

 
     
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaDestrezaUnidad en DestrezaUnidadEAO: "+e.getMessage());
            
        }
        
    }
    
}
