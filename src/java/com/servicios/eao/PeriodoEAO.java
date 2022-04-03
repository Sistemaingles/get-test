
package com.servicios.eao;

import com.entidades.TblPeriodo;
import java.math.BigInteger;
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
public class PeriodoEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    String sql ="";
    private int cantidad = 0;
    private Query query;
    
    
    /*Metodo verifica si existe Periodo a ingresar.-*/
    public int cantidadPeriodo(String periodo){
        
        sql  =  " select count(1) " +
                " from tbl_periodo p "        +
                " where ( p.periodo_anio  = :periodo or :periodo = '' )" + 
                " and p.periodo_sts = :estado  " 
              ;
        query = administradorEntidad.createNativeQuery(sql);
        query.setParameter("periodo", periodo);
        query.setParameter("estado", "A");
        BigInteger cantidad1 = (BigInteger) query.getSingleResult();
        
        System.out.println("Cantidad de Periodo: "+cantidad);
        cantidad = cantidad1.intValue();
        return cantidad;
    }
    /*Metodo obtener Periodo*/
    public List<TblPeriodo> obtenerPeriodo(String estado)
    {
        List<Object[]> lResultado         = new ArrayList<Object[]>();
        List<TblPeriodo> listPeriodo     = new ArrayList<TblPeriodo>();
        System.out.println("Inicio obtienePeriodo en PeriodoEAO");
        
        sql  = " select p.periodo_Id, p.periodo_anio, p.periodo_descripcion , p.periodo_sts, p.periodo_instim, p.periodo_insusr " +
                      "  from tbl_periodo p "        +
                      " where ( p.periodo_sts = :estado or :estado = '')  " +
                      " order by p.periodo_Id    "  ;
    
        query = administradorEntidad.createNativeQuery(sql);
        query.setParameter("estado", estado);
        
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Periodos: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblPeriodo periodo = new TblPeriodo();

            periodo.setPeriodoId(Long.valueOf(objs[0].toString()));
            periodo.setPeriodoAnio(objs[1].toString());
            periodo.setPeriodoDescripcion((String) objs[2]);
            String periodo_sts = (String) objs[3];
            periodo.setPeriodoInstim((Date) objs[4]);
            periodo.setPeriodoInsusr((String) objs[5]);
            if(periodo_sts.equals("A")){
                periodo.setPeriodoSts("ACTIVO");
            }
            else{
               periodo.setPeriodoSts("INACTIVO"); 
            }
                
  
            listPeriodo.add(periodo);   
        }
        
        return listPeriodo;
    }
    
    /*Metodo ingresar Periodo*/
    public void ingresarPeriodo(TblPeriodo periodo)
    {
        try 
        {
            administradorEntidad.persist(periodo);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaPeriodo en PeriodoEAO: "+e.getMessage());
        }
    }
    
    /*Metodo actualizar Periodo*/
    public void actualizaPeriodo(TblPeriodo periodo)
    {
	administradorEntidad.merge(periodo);	
    }

    public void eliminaPeriodo(TblPeriodo periodo)
    {
            try 
            {
                System.out.println("Periodo a eliminar :"+periodo.getPeriodoId());
                sql  = "delete from tbl_periodo where periodo_Id = :periodoId  " ;
                
                query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("periodoId", periodo.getPeriodoId());
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al eliminaPeriodo en PeriodoEAO_1: "+e.getMessage());
            }
    }
    
    public void reestableceSecuencia(String entidad)
    {
        try 
        {
            System.out.println("Entidad a reestableceSecuencia :"+entidad);
       
            sql  = "alter table "+ entidad +" AUTO_INCREMENT = 1" ;
                
            query = administradorEntidad.createNativeQuery(sql);
            query.executeUpdate();

        } catch (Exception e) 
        {
            System.out.println("Error al reestableceSecuencia en PeriodoEAO: "+e.getMessage());
        }
    }
    
}
