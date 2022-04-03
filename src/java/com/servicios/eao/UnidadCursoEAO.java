
package com.servicios.eao;

import com.entidades.TblUnidad;
import com.entidades.listas.UnidadList;
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
public class UnidadCursoEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo obtiene Unidad Registrado.-*/
    public List<UnidadList> consultaUnidad( Long usuario_cod, Long unidad_id , String estado)
    {
        try {
            System.out.println("consultaUnidad Inicio");
            String sql   = " select pe.periodo_anio, u.unidad_nombre, u.unidad_descripcion, c.curso_numero, p.paralelo_num , u.unidad_sts ," + 
                            "   u.cursoparalelo_id , u.unidad_id , u.unidad_clase, u.unidad_icono  " +
                            "From tbl_unidad u " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id and cp.cursoparalelo_sts = 'A' " +
                            "Inner join tbl_periodo pe on pe.periodo_Id = cp.periodo_Id and pe.periodo_sts = 'A' " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id and c.curso_sts = 'A' " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id and p.paralelto_sts = 'A' " +
                            "Inner join tbl_docentecurso dc on dc.cursoparalelo_id = cp.cursoparalelo_id and dc.docentecurso_sts = 'A' "+ //ADD 21042021
                            "Inner join tbl_docente d on d.docente_id = dc.docente_id and d.usuario_cod = :usuarioCod and d.docente_sts = 'A' "+ //ADD 21042021        
                            "where ( u.unidad_sts = :estado or :estado = '' ) " +
                            "and ( u.unidad_Id             = :unidad 	    or :unidad 	    = 0 ) " +
                            "order by u.unidad_Id";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("usuarioCod", usuario_cod);
            query.setParameter("estado", estado);
            query.setParameter("unidad", unidad_id);
            
            System.out.println("Query consultaUnidad: "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<UnidadList> listUnidad        = new ArrayList<UnidadList>();
            lResult                            = query.getResultList();
 
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                UnidadList unidadList = new UnidadList();
                unidadList.setPeriodo((String) objects[0]);
                unidadList.setNombres((String) objects[1]);
                unidadList.setDescripcion((String) objects[2]);
                unidadList.setCurso_nombre((String) objects[3]);
                unidadList.setParalelo_nombre((String) objects[4]);
               
                String estado1 = (String) objects[5];
                unidadList.setCursoparaleloid(Long.valueOf(objects[6].toString()));
                unidadList.setUnidad_id(Long.valueOf(objects[7].toString()));
                unidadList.setClase((String) objects[8]);
                unidadList.setIcono((String) objects[9]);
                
                if(estado1.equals("A"))
                {
                    unidadList.setEstado("ACTIVO");
                }
                else
                {
                    unidadList.setEstado("INACTIVO");
                }
                
                 listUnidad.add(unidadList);
            }
            
           return listUnidad; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaUnidad en UnidadCursoEAO: "+e.getMessage());
            return null;
        }
    } 
 
    /*Metodo obtiene Unidad Registrado y con Curso asignado*/
    public List<UnidadList> consultaUnidadCurso( Long usuario_cod, Long unidad_id, String nombre, String descripcion)
    {
        try {
            System.out.println("consultaUnidadCurso Inicio");
            String sql   = " select pe.periodo_descripcion, u.unidad_nombre, u.unidad_descripcion, c.curso_descripcion, p.paralelo_nombre , u.unidad_sts ," + 
                            "   u.cursoparalelo_id , u.unidad_id  " +
                            "From tbl_unidad u " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id " +
                            "Inner join tbl_periodo pe on pe.periodo_Id = cp.periodo_Id " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id " +
                            "Inner join tbl_docentecurso dc on dc.cursoparalelo_id = cp.cursoparalelo_id "+ //ADD 21042021
                            "Inner join tbl_docente d on d.docente_id = dc.docente_id and d.usuario_cod = :usuarioCod "+ //ADD 21042021
                            "where u.unidad_sts = :estado " +
                            "and not u.unidad_Id  in (select pt.unidad_Id  from tbl_presentaciontaller pt where pt.taller_sts = 'A'  ) "+
                            "and ( u.unidad_Id             = :unidad 	    or :unidad 	    = 0 ) " +
                            "and ( u.unidad_nombre      like :nombre 	    or :nombre 	    = '' ) " +
                            "and ( u.unidad_descripcion like  :descripcion  or :descripcion    = '' ) ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("usuarioCod",  usuario_cod);
            query.setParameter("unidad", unidad_id);
            query.setParameter("nombre", "%" + nombre + "%" );
            query.setParameter("descripcion", "%" + descripcion + "%");
            
            
            System.out.println("Query consultaUnidadCurso : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<UnidadList> listUnidad        = new ArrayList<UnidadList>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                UnidadList unidadList = new UnidadList();
                unidadList.setPeriodo((String) objects[0]);
                unidadList.setNombres((String) objects[1]);
                unidadList.setDescripcion((String) objects[2]);
                unidadList.setCurso_nombre((String) objects[3]);
                unidadList.setParalelo_nombre((String) objects[4]);
               

                estado = (String) objects[5];
                unidadList.setCursoparaleloid(Long.valueOf(objects[6].toString()));
                unidadList.setUnidad_id(Long.valueOf(objects[7].toString()));
                
                if(estado.equals("A"))
                {
                    unidadList.setEstado("ACTIVO");
                }
                else
                {
                    unidadList.setEstado("INACTIVO");
                }
                
                 listUnidad.add(unidadList);
            }
            
           return listUnidad; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaUnidadCurso en UnidadCursoEAO: "+e.getMessage());
            return null;
        }
    } 
 
    /*Metodo ingresa Unidad si no existe*/
    public Long ingresaUnidad(TblUnidad unidad)
    {
        try 
        {
            administradorEntidad.persist(unidad);
            administradorEntidad.flush();
            System.out.println("Unidad Id registrado:"+unidad.getUnidadId());
            
            return unidad.getUnidadId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaUnidad en UnidadCursoEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    /*Metodo actualiza Unidad*/
    public void actualizaUnidad(TblUnidad unidad)
    {   
	administradorEntidad.merge(unidad);	
    }
    
    /*Metodo obtiene Unidades Registradas de Talleres ingresados*/
    public List<UnidadList> consultaUnidadRegistrada( Long unidad_id )
    {
        try {
            System.out.println("consultaUnidadRegistrada Inicio");
            String sql   = " select pe.periodo_descripcion, u.unidad_nombre, u.unidad_descripcion, c.curso_numero, p.paralelo_num , u.unidad_sts ," + 
                            "   u.cursoparalelo_id , u.unidad_id  " +
                            "From tbl_unidad u " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id " +
                            "Inner join tbl_periodo pe on pe.periodo_Id = cp.periodo_Id " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id " +
                            "where u.unidad_sts = :estado " +
                            "and u.unidad_Id  in (select pt.unidad_Id  from tbl_presentaciontaller pt where pt.taller_sts = 'A' ) "+
                            "and ( u.unidad_Id          = :unidad 	    or :unidad 	    = 0 ) "
                            ;
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("unidad", unidad_id);

            
            System.out.println("Query consultaUnidadRegistrada : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<UnidadList> listUnidad        = new ArrayList<UnidadList>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                UnidadList unidadList = new UnidadList();
                unidadList.setPeriodo((String) objects[0]);
                unidadList.setNombres((String) objects[1]);
                unidadList.setDescripcion((String) objects[2]);
                unidadList.setCurso_nombre((String) objects[3]);
                unidadList.setParalelo_nombre((String) objects[4]);
               

                estado = (String) objects[5];
                unidadList.setCursoparaleloid(Long.valueOf(objects[6].toString()));
                unidadList.setUnidad_id(Long.valueOf(objects[7].toString()));
                
                if(estado.equals("A"))
                {
                    unidadList.setEstado("ACTIVO");
                }
                else
                {
                    unidadList.setEstado("INACTIVO");
                }
                
                 listUnidad.add(unidadList);
            }
            
           return listUnidad; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaUnidadRegistrada en UnidadCursoEAO: "+e.getMessage());
            return null;
        }
    } 
 

    /*Metodo obtiene Unidades Registradas por cada Destreza y Unidad del Paralelo Asignado.-*/
    public List<UnidadList> consultaUnidadporDestreza( Long destrezaId, Long usuarioCod )
    {
        try {
            System.out.println("consultaUnidadporDestreza destrezaId: "+destrezaId + " usuarioCod: "+usuarioCod);
            String sql   = " select 'No. ' num_unidad, u.unidad_Id, u.unidad_nombre, u.unidad_sts , u.unidad_clase, u.unidad_icono " +
                            " from tbl_destrezaunidad du " +
                            " inner join tbl_unidad u on u.unidad_Id = du.unidad_Id  " +
                            " inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id " +
                            " inner join tbl_estudiantecurso ec on ec.cursoparalelo_id = cp.cursoparalelo_id " + 
                            " inner join tbl_estudiante e on e.estudiante_id = ec.estudiante_id " +
                            " where du.destreza_Id = :destrezaId" +
                            " and u.unidad_sts = :estado" +
                            " and du.destrezaunidad_Sts = :estado" +
                            " and e.usuario_cod = :usuarioCod" +
                            " order by du.unidad_Id "
                            ;
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("destrezaId", destrezaId);
            query.setParameter("estado", "A");
            query.setParameter("usuarioCod", usuarioCod);

            System.out.println("Query consultaUnidadporDestreza : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<UnidadList> listUnidad        = new ArrayList<UnidadList>();
            lResult                            = query.getResultList();
            String estado ;
            String unidad_desc;
            int contador = 0;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                contador ++;
                UnidadList unidadList = new UnidadList();
                unidad_desc = (String) objects[0] + String.valueOf(contador);
                unidadList.setDescripcion(unidad_desc);
                unidadList.setUnidad_id(Long.valueOf(objects[1].toString()));
                unidadList.setNombres((String) objects[2]);
                estado = (String) objects[3];
                unidadList.setClase((String) objects[4]);
                unidadList.setIcono((String) objects[5]);
                
                if(estado.equals("A"))
                {
                    unidadList.setEstado("ACTIVO");
                }
                else
                {
                    unidadList.setEstado("INACTIVO");
                }
                
                 listUnidad.add(unidadList);
            }
            
           return listUnidad; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaUnidadporDestreza en UnidadCursoEAO: "+e.getMessage());
            return null;
        }
    } 
 
}
