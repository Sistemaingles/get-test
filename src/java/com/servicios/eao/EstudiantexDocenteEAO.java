
package com.servicios.eao;

import com.entidades.listas.DocenteList;
import java.math.BigInteger;
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
 * @author jespinoza
 */
@Stateless
@LocalBean
public class EstudiantexDocenteEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    private String sql = "";
    
    /*Metodo obtiene Estudiante por Docente y Periodo asignado*/
    public List<DocenteList> consultaEstudiantesxDocente( Long periodo_cod, Long curso_cod, Long docente_cod, Long paralelo_cod )
    {
        try {
                System.out.println("consultaEstudiantesxDocente Periodo: "+periodo_cod + " Curso: "+curso_cod + " Docente: "+docente_cod+
                                   " Paralelo: "+paralelo_cod);
                sql =   "SELECT p.periodo_anio, (CASE p.periodo_sts  " +
                        "	              		 when 'A' Then 'ACTIVO' " +
                        "		    			 when 'I' Then 'INACTIVO' " +
                        "                        else ' ' " +
                        "                       end ) estado,  " +
                        "      CU.curso_numero, PA.paralelo_num , CONCAT(d.docente_nombres, ' ', D.docente_apellidos) docente,  " +
                        "	  count(ec.estudiante_id) cantidad  " +
                        "FROM  tbl_docentecurso dc, tbl_cursoparalelo cp, tbl_curso cu, tbl_paralelo pa, " +
                        "	  tbl_docente d, tbl_periodo p, tbl_estudiantecurso ec  " +
                        "Where cp.cursoparalelo_id = dc.cursoparalelo_id  " +
                        "and cp.cursoparalelo_sts = :estado " +
                        "and cu.curso_id = cp.curso_id  " +
                        "and cu.curso_sts = :estado " +
                        "and pa.paralelo_id = cp.paralelo_id  " +
                        "and pa.paralelto_sts = :estado " +
                        "AND dc.docentecurso_sts = :estado " +
                        "AND d.docente_id = dc.docente_id " +
                        "AND d.docente_sts = :estado " +
                        "AND p.periodo_Id = cp.periodo_id  " +
                        "AND ec.cursoparalelo_id = cp.cursoparalelo_id " +
                        "and ec.estudiantecurso_sts = :estado " +
                        "AND ( p.periodo_Id =  :periodo_cod or :periodo_cod = 0)  " +
                        "AND ( cu.curso_id = :curso_cod or :curso_cod = 0 ) " +
                        "AND ( d.docente_id = :docente_cod or :docente_cod = 0 )"+
                        "AND ( pa.paralelo_id = :paralelo_cod or :paralelo_cod = 0 )";

                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("estado", "A");
                query.setParameter("periodo_cod", periodo_cod);
                query.setParameter("curso_cod", curso_cod);
                query.setParameter("docente_cod", docente_cod);
                query.setParameter("paralelo_cod", paralelo_cod);

                System.out.println("Query consultaEstudiantesxDocente : "+query.toString());
                List<Object[]> lResult             = new ArrayList<Object[]>();
                List<DocenteList> listEstudianteDocente      = new ArrayList<DocenteList>();
                lResult                            = query.getResultList();
                String estado ;
                System.out.println("Cantidad de Registros :"+lResult.size());

                for (Object[] objects : lResult) 
                {
                    DocenteList docenteList = new DocenteList();
                    docenteList.setPeriodo((String) objects[0]);
                    docenteList.setEstado((String) objects[1]);
                    docenteList.setCurso_nombre((String) objects[2]);
                    docenteList.setParalelo_nombre((String) objects[3]);
                    docenteList.setNombres((String) objects[4]);
                    docenteList.setCant_estudiante((BigInteger) objects[5]);

                    listEstudianteDocente.add(docenteList);
                }
                return listEstudianteDocente; 
            }
            catch(Exception e) { 
                System.out.println("Error al consultaEstudiantesxDocente en EstudiantesxDocenteEAO: "+e.getMessage());
                return null;
            }
    }
}
