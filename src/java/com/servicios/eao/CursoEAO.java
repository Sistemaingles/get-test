
package com.servicios.eao;

import com.entidades.TblCurso;
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
public class CursoEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo obtener Curso*/
    public List<TblCurso> obtenerCurso(String estado)
    {
        List<Object[]> lResultado    = new ArrayList<Object[]>();
        List<TblCurso> listCurso     = new ArrayList<TblCurso>();
        System.out.println("Inicio obtenerCurso en CursoEAO");
        
        String sql  = " select p.curso_id, p.curso_numero, p.curso_descripcion , p.curso_sts, p.curso_instim, p.curso_insusr " +
                      "  from tbl_curso p "        +
                      " where ( p.curso_sts = :estado or :estado = '')  " +
                      " order by p.curso_id    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        query.setParameter("estado", estado);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Cursps: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblCurso curso = new TblCurso();

            curso.setCursoId(Long.valueOf(objs[0].toString()));
            curso.setCursoNumero(objs[1].toString());
            curso.setCursoDescripcion((String) objs[2]);
            String curso_sts = (String) objs[3];
            curso.setCursoInstim((Date) objs[4]);
            curso.setCursoInsusr((String) objs[5]);
            if(curso_sts.equals("A")){
                curso.setCursoSts("ACTIVO");
            }
            else{
               curso.setCursoSts("INACTIVO"); 
            }
                
  
            listCurso.add(curso);   
        }
        
        return listCurso;
    }
    
    /*Metodo ingresar Curso*/
    public void ingresarCurso(TblCurso curso)
    {
        try 
        {
            administradorEntidad.persist(curso);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresarCurso en CursoEAO: "+e.getMessage());
        }
    }
    
    /*Metodo actualizar Periodo*/
    public void actualizaCurso(TblCurso curso)
    {
	administradorEntidad.merge(curso);	
    }

    public void eliminaCurso(TblCurso curso)
    {
            try 
            {
                System.out.println("Curso a eliminar :"+curso.getCursoId());
                String sql  = "delete from tbl_curso where curso_id = :cursoId  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("cursoId", curso.getCursoId());
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al eliminaCurso en CursoEAO: "+e.getMessage());
            }
    }
    
   
}
