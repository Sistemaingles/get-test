
package com.servicios.eao;

import com.entidades.TblDocente;
import com.entidades.TblDocentecurso;
import com.entidades.listas.DocenteList;
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
public class AsignaDocenteEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo obtiene Docente Registrado y con Curso asignado*/
    public List<DocenteList> consultaDocenteCurso( String identificacion, String nombres, String apellidos)
    {
        try {
            System.out.println("consultaDocentes Inicio");
            String sql   = " select dc.docentecurso_Id, d.docente_ident, d.docente_nombres, d.docente_apellidos, c.curso_descripcion, p.paralelo_nombre , dc.docentecurso_sts ," + 
                            "   dc.cursoparalelo_id, d.docente_fechaNac, d.docente_genero, d.docente_correo , d.docente_id , pe.periodo_descripcion  " +
                            "From tbl_docentecurso dc " +
                            "Inner join tbl_docente d on d.docente_id = dc.docente_id " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = dc.cursoparalelo_id " +
                            "Inner join tbl_periodo pe on pe.periodo_Id = cp.periodo_Id " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id " +
                            "where dc.docentecurso_sts = :estado " +
                            "and ( d.docente_ident     = :identificacion or :identificacion = '' ) " +
                            "and ( d.docente_nombres   = :nombres 	 or :nombres 	    = '' ) " +
                            "and ( d.docente_apellidos = :apellidos 	 or :apellidos      = '' ) ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("identificacion", identificacion);
            query.setParameter("nombres", nombres);
            query.setParameter("apellidos", apellidos);
            
            
            System.out.println("Query consultaDocentes : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<DocenteList> listDocente      = new ArrayList<DocenteList>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                DocenteList docenteList = new DocenteList();
                docenteList.setDocentecursoid(Long.valueOf(objects[0].toString()));
                docenteList.setIdentificacion((String) objects[1]);
                docenteList.setNombres((String) objects[2]);
                docenteList.setApellidos((String) objects[3]);
                docenteList.setCurso_nombre((String) objects[4]);
                docenteList.setParalelo_nombre((String) objects[5]);
                

                estado = (String) objects[6];

                docenteList.setCursoparaleloid(Long.valueOf(objects[7].toString()));
                docenteList.setFecha_nacimiento((Date) objects[8]);
                docenteList.setGenero((String) objects[9]);
                docenteList.setCorreo((String) objects[10]);
                docenteList.setDocente_id(Long.valueOf(objects[11].toString()));
                docenteList.setPeriodo((String) objects[12]);
                if(estado.equals("A"))
                {
                    docenteList.setEstado("ACTIVO");
                }
                else
                {
                    docenteList.setEstado("INACTIVO");
                }
                
                 listDocente.add(docenteList);
            }
            
           return listDocente; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaDocenteCurso en PeriodoEAO: "+e.getMessage());
            return null;
        }
    } 
 
    /*Metodo ingresa Docente si no existe*/
    public Long ingresaDocente(TblDocente docente)
    {
        try 
        {
            administradorEntidad.persist(docente);
            administradorEntidad.flush();
            System.out.println("Docente Id registrado:"+docente.getDocenteId());
            
            return docente.getDocenteId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaDocente en DocenteEAO: "+e.getMessage());
            return null;
        }
        
    }
    
     /*Metodo ingresa Docente-Curso*/
    public void ingresaDocenteCurso(TblDocentecurso docentecurso)
    {
        try 
        {
            administradorEntidad.persist(docentecurso);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaDocenteCurso en DocenteEAO: "+e.getMessage());
        }
        
    }
}
