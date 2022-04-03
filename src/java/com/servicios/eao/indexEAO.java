
package com.servicios.eao;

import java.math.BigInteger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author AMORAN
 */

@Stateless
@LocalBean
public class indexEAO 
{
    @PersistenceContext(unitName="project",type = PersistenceContextType.TRANSACTION)
    protected EntityManager administradorEntidad; 
    private String sql  = "";
    private Query query ;
    private BigInteger cantidad;
    
    
    public BigInteger cantidadUsuarios()
    {
        cantidad    = BigInteger.ZERO;
        
        sql  = " select count(1) " +
               "  from tbl_usuarios p "                   +
               " where p.estado      = 'A' ";
    
        query = administradorEntidad.createNativeQuery(sql);
  
        cantidad = (BigInteger) query.getSingleResult();
        
        System.out.println("Cantidad de Usuario: "+cantidad);
        
        return cantidad;
        
    }
    
    public BigInteger cantidadUnidadxDestreza( Long destreza)
    {
        cantidad    = BigInteger.ZERO;
        
        sql  = " select count(1) " +
               "  from tbl_usuarios p "                   +
               " where p.estado      = 'A' ";
    
        query = administradorEntidad.createNativeQuery(sql);
  
        cantidad = (BigInteger) query.getSingleResult();
        
        System.out.println("Cantidad de Usuario: "+cantidad);
        
        return cantidad;
        
    }
  
}
