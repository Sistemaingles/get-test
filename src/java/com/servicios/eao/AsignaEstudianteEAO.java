
package com.servicios.eao;


import com.entidades.TblEstudiante;
import com.entidades.TblEstudiantecurso;
import com.entidades.listas.EstudianteList;
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
public class AsignaEstudianteEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo obtiene Estudiantes Registrado y con Curso asignado*/
    public List<EstudianteList> consultaEstudianteCurso( String identificacion, String nombres, String apellidos)
    {
        try {
            System.out.println("consultaEstudianteCurso Inicio");
            String sql   = " select dc.estudiantecurso_Id, d.estudiante_ident, d.estudiante_nom, d.estudiante_ape, c.curso_descripcion, p.paralelo_nombre , dc.estudiantecurso_sts ," + 
                            "   dc.cursoparalelo_id, d.estudiante_fechaNac, d.estudiante_genero, d.estudiante_id  " +
                            "From tbl_estudiantecurso dc " +
                            "Inner join tbl_estudiante d on d.estudiante_id = dc.estudiante_id " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = dc.cursoparalelo_id " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id " +
                            "where dc.estudiantecurso_sts = :estado " +
                            "and ( d.estudiante_ident     = :identificacion or :identificacion = '' ) " +
                            "and ( d.estudiante_nom       = :nombres 	 or :nombres 	    = '' ) " +
                            "and ( d.estudiante_ape       = :apellidos 	 or :apellidos      = '' ) ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("identificacion", identificacion);
            query.setParameter("nombres", nombres);
            query.setParameter("apellidos", apellidos);
            
            
            System.out.println("Query consultaEstudianteCurso : "+query.toString());
            List<Object[]> lResult              = new ArrayList<Object[]>();
            List<EstudianteList> listEstudiante = new ArrayList<EstudianteList>();
            lResult                             = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                EstudianteList estudianteList = new EstudianteList();
                estudianteList.setEstudiantecursoid(Long.valueOf(objects[0].toString()));
                estudianteList.setIdentificacion((String) objects[1]);
                estudianteList.setNombres((String) objects[2]);
                estudianteList.setApellidos((String) objects[3]);
                estudianteList.setCurso_nombre((String) objects[4]);
                estudianteList.setParalelo_nombre((String) objects[5]);
                
                estado = (String) objects[6];

                estudianteList.setCursoparaleloid(Long.valueOf(objects[7].toString()));
                estudianteList.setFechaNac((Date) objects[8]);
                estudianteList.setGenero((String) objects[9]);
                estudianteList.setEstudiantecursoid(Long.valueOf(objects[10].toString()));
                
                System.out.println("consultaEstudianteCurso FechaNac: "+estudianteList.getFechaNac());
                   
                if(estado.equals("A"))
                {
                    estudianteList.setEstado("ACTIVO");
                }
                else
                {
                    estudianteList.setEstado("INACTIVO");
                }
                
                 listEstudiante.add(estudianteList);
            }
            
           return listEstudiante; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaEstudianteCurso en EstudianteEAO: "+e.getMessage());
            return null;
        }
    } 
 
    /*Metodo ingresa Docente si no existe*/
    public Long ingresaEstudiante(TblEstudiante estudiante)
    {
        try 
        {
            administradorEntidad.persist(estudiante);
            administradorEntidad.flush();
            System.out.println("Estudiante Id registrado:"+estudiante.getEstudianteId());
            
            return estudiante.getEstudianteId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaEstudiante en EstudianteEAO: "+e.getMessage());
            return null;
        }
        
    }
    
     /*Metodo ingresa Estudiante-Curso*/
    public void ingresaEstudianteCurso(TblEstudiantecurso estudiantecurso)
    {
        try 
        {
            administradorEntidad.persist(estudiantecurso);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaEstudianteCurso en EstudianteEAO: "+e.getMessage());
        }
        
    }
}