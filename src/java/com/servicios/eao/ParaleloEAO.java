
package com.servicios.eao;

import com.entidades.TblParalelo;
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
public class ParaleloEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;

    /*Metodo ingresa Paralelo*/
    public void ingresaParalelo(TblParalelo paralelo)
    {
        try 
        {
            administradorEntidad.persist(paralelo);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaCurso en CursoEAO: "+e.getMessage());
        }
    }
    
    /*Metodo obtener Paralelos*/
    public List<TblParalelo> obtenerParalelo(String estado)
    {
        List<Object[]> lResultado    = new ArrayList<Object[]>();
        List<TblParalelo> listParalelo     = new ArrayList<TblParalelo>();
        System.out.println("Inicio obtenerParalelo en ParaleloEAO");
        
        String sql  = " select p.paralelo_id, p.paralelo_num, p.paralelo_nombre , p.paralelto_sts, p.paralelo_instim, p.paralelo_insusr " +
                      "  from tbl_paralelo p "        +
                      " where ( p.paralelto_sts = :estado or :estado = '')  " +
                      " order by p.paralelo_id    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        query.setParameter("estado", estado);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Paralelos: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblParalelo paralelo = new TblParalelo();

            paralelo.setParaleloId(Long.valueOf(objs[0].toString()));
            paralelo.setParaleloNum(objs[1].toString());
            paralelo.setParaleloNombre((String) objs[2]);
            String curso_sts = (String) objs[3];
            paralelo.setParaleloInstim((Date) objs[4]);
            paralelo.setParaleloInsusr((String) objs[5]);
            if(curso_sts.equals("A")){
                paralelo.setParaleltoSts("ACTIVO");
            }
            else{
               paralelo.setParaleltoSts("INACTIVO"); 
            }
                
  
            listParalelo.add(paralelo);   
        }
        
        return listParalelo;
    }
   
    /*Metodo actualizar Paralelo*/
    public void actualizaParalelo(TblParalelo paralelo)
    {   
	administradorEntidad.merge(paralelo);	
    }
    
     /*Metodo elimina Paralelo*/
    public void eliminaParalelo(TblParalelo paralelo)
    {
            try 
            {
                System.out.println("Paralelo a eliminar :"+paralelo.getParaleloId());
                String sql  = "delete from tbl_paralelo where paralelo_id = :paraleloId  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("paraleloId", paralelo.getParaleloId());
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al eliminaParalelo en ParaleloEAO: "+e.getMessage());
            }
    }
    
    
}
