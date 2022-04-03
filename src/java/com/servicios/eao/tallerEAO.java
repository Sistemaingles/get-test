
package com.servicios.eao;

import com.entidades.TblPresentaciontaller;
import java.math.BigInteger;

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
public class tallerEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    private Query query ;
    private BigInteger cantidad;
    
    public BigInteger cantidadTallerAbierto(Long usuario_cod, Long unidad_Id)
    {
        try 
        {
            cantidad    = BigInteger.ZERO;
            
            String sql  = "select count(1) " +
                        " from tbl_presentaciontaller pt  " +
                        " inner join tbl_unidad u  " +
                        " on u.unidad_Id = pt.unidad_Id  " +
                        " inner join tbl_cursoparalelo cp  " +
                        " on cp.cursoparalelo_id = u.cursoparalelo_id  " +
                        " inner join tbl_docentecurso dc  " +
                        " on dc.cursoparalelo_id = cp.cursoparalelo_id  " +
                        " inner join tbl_docente d  " +
                        " on d.docente_id = dc.docente_id  " +
                        " where d.usuario_cod = :usuarioCod" +
                        " and cp.cursoparalelo_id = (select cpi.cursoparalelo_id " +
                        "			       From tbl_unidad u " +
                        "                     	     inner join tbl_cursoparalelo cpi " +
                        "				on cpi.cursoparalelo_id = u.cursoparalelo_id " +
                        "			     where u.unidad_Id = :unidadId " +
                        "			       and u.unidad_sts = 'A') " +
                        " and pt.taller_sts = 'A'" ;
            
            query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("usuarioCod", usuario_cod);
            query.setParameter("unidadId", unidad_Id);
            
            cantidad = (BigInteger) query.getSingleResult();
           
           
           return cantidad; 
        }
        catch (Exception e) 
        {
            System.out.println("Error en Metodo cantidadTallerAbierto en tallerEAO: "+e.getMessage());
            return BigInteger.ZERO;
        }
        
    }   
    
    /*Metodo ingresa Taller si no existe*/
    public Long ingresaTaller(TblPresentaciontaller taller)
    {
        try 
        {
            administradorEntidad.persist(taller);
            administradorEntidad.flush();
            System.out.println("taller Id registrado:"+taller.getTallerId());

 
            return taller.getTallerId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaTaller en tallerEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    public boolean cierraTaller(Long tallerId)
    {
        boolean cerrado = false;
        
        try 
        {
            String sql  = "update tbl_presentaciontaller t"+
                        " set t.taller_sts  = 'I' ,  " +
                        "     t.taller_dlttim = sysdate() " +
                        " where t.taller_id = :tallerId " +
                        " and t.taller_sts  = 'A'  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("tallerId", tallerId);
            query.executeUpdate();
           
            cerrado = true;
        } catch (Exception e) 
        {
            cerrado = false;
            System.out.println("Error en metodo cierraTaller en tallerEAO: "+e.getMessage());

        }
        return  cerrado;
    }
 
}
