
package com.servicios.eao;

import com.entidades.TblCurso;
import com.entidades.TblCursoparalelo;
import com.entidades.TblParalelo;
import com.entidades.TblPeriodo;
import com.entidades.listas.CursoAsignadoList;
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
public class AsignaCursoEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo obtiene Periodo Lectivo (Año)*/
    public List<TblPeriodo> obtienePeriodo()
    {
        List<Object[]> lResultado         = new ArrayList<Object[]>();
        List<TblPeriodo> listPeriodo     = new ArrayList<TblPeriodo>();
        System.out.println("Inicio obtienePeriodo en PeriodoEAO");
        
        String sql  = " select p.periodo_Id, p.periodo_descripcion " +
                      "  from tbl_periodo p "        +
                      " where p.periodo_sts = 'A' "  +  
                      " order by p.periodo_anio    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Periodos: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblPeriodo periodo = new TblPeriodo();
            
            Long periodo_cod = Long.valueOf(objs[0].toString());
            periodo.setPeriodoId(periodo_cod);
            periodo.setPeriodoDescripcion((String) objs[1]);
            
     
            listPeriodo.add(periodo);   
        }
        
        return listPeriodo;
    }
    
    /*Metodo obtiene Cursos*/
    public List<TblCurso> obtieneCursos(String cursos)
    {
        List<Object[]> lResultado       = new ArrayList<Object[]>();
        List<TblCurso> listCurso     = new ArrayList<TblCurso>();
        System.out.println("Inicio obtieneCursos en PeriodoEAO");
        
        String sql  = " select p.curso_id, p.curso_descripcion , p.curso_numero, p.curso_sts " +
                      "  from tbl_curso p "        +
                      " where ( p.curso_numero = :curso or ( :curso is null or :curso = '' ) )"+ 
                      " and p.curso_sts = 'A' "  +  
                      " order by p.curso_numero    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        query.setParameter("curso", cursos);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Cursos: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblCurso curso = new TblCurso();
            
            Long curso_cod = Long.valueOf(objs[0].toString());
            curso.setCursoId(curso_cod);
            curso.setCursoDescripcion((String) objs[1]);
            curso.setCursoNumero((String) objs[2]);
            String estado = (String) objs[3];
            if (estado.equals("A")) {
               curso.setCursoSts("ACTIVO");
            } else {
                curso.setCursoSts("INACTIVO");
            }
     
            listCurso.add(curso);   
        }
        
        return listCurso;
    }
    
    /*Metodo obtiene Paralelos*/
    public List<TblParalelo> obtieneParalelos(String paralelos)
    {
        List<Object[]> lResultado          = new ArrayList<Object[]>();
        List<TblParalelo> listParalelo     = new ArrayList<TblParalelo>();
        System.out.println("Inicio obtieneParalelos en PeriodoEAO");
        
        String sql  = " select p.paralelo_id, p.paralelo_nombre, p.paralelo_num, p.paralelto_sts " +
                      "  from tbl_paralelo p "        +
                      " where ( p.paralelo_num = :paralelo or ( :paralelo is null or :paralelo = '' ) ) " +
                      " and p.paralelto_sts = 'A' "  +  
                      " order by p.paralelo_num    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        query.setParameter("paralelo", paralelos);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Paralelos: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblParalelo paralelo = new TblParalelo();
            
            Long paralelo_cod = Long.valueOf(objs[0].toString());
            paralelo.setParaleloId(paralelo_cod);
            paralelo.setParaleloNombre((String) objs[1]);
            paralelo.setParaleloNum((String) objs[2]);
            
            String paralelo_sts = (String) objs[3];

            if (paralelo_sts.equals("A")) {
                paralelo.setParaleltoSts("ACTIVO");
            } else {
                paralelo.setParaleltoSts("INACTIVO");
            }
            listParalelo.add(paralelo);   
        }
        
        return listParalelo;
    }
    
    /*Metodo ingresa Periodo*/
    public void ingresaPeriodo(TblCursoparalelo periodo)
    {
        try 
        {
            administradorEntidad.persist(periodo);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaPeriodo en PeriodoEAO: "+e.getMessage());
        }
    }
    
    /*Metodo Consulta Cursos Asignados registrados.-*/
    public List<CursoAsignadoList> consultaPeriodoCurso( Long periodo_cod, Long curso_cod, Long paralelo_cod, String estado)
    {
        try {
            System.out.println("consultaPeriodoCurso Inicio");
            String sql   = " select cp.periodo_id, p.periodo_anio, cp.curso_id, c.curso_numero, " +
                    "		 cp.paralelo_id, pa.paralelo_num, cp.cursoparalelo_sts , cp.cursoparalelo_id" +
                    " from tbl_cursoparalelo cp " +
                    " inner join tbl_periodo   p on p.periodo_Id   = cp.periodo_id and p.periodo_sts = 'A' " +
                    " inner join tbl_curso     c on c.curso_id	  	= cp.curso_id and c.curso_sts = 'A' " +
                    " inner join tbl_paralelo pa on pa.paralelo_id = cp.paralelo_id and pa.paralelto_sts = 'A' " +
                    " where ( cp.periodo_id = :periodo_id or ( :periodo_id is null or :periodo_id = 0)  ) " +
                    " and ( cp.curso_id = :curso_id or ( :curso_id is null or :curso_id = 0)  ) " +
                    " and ( cp.paralelo_id = :paralelo_id or ( :paralelo_id is null or :paralelo_id = 0) ) " + 
                    " and ( cp.cursoparalelo_sts = :estado or :estado = '' ) " +
                    " order by cp.cursoparalelo_id ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("periodo_id", periodo_cod);
            query.setParameter("curso_id", curso_cod);
            query.setParameter("paralelo_id", paralelo_cod);
            query.setParameter("estado", estado);
            
            System.out.println("Query consultaPeriodoCurso : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<CursoAsignadoList> listPeriodo      = new ArrayList<CursoAsignadoList>();
            lResult                            = query.getResultList();
       
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                CursoAsignadoList periodoList = new CursoAsignadoList();
                periodoList.setPeriodo_cod(Long.valueOf(objects[0].toString()));
                periodoList.setPeriodo_descripcion((String) objects[1]);
                periodoList.setCurso_cod(Long.valueOf(objects[2].toString()));
                periodoList.setCurso_descripcion((String) objects[3]);
                periodoList.setParalelo_cod(Long.valueOf(objects[4].toString()));
                periodoList.setParalelo_nombre((String) objects[5]);
                
                String estado1 = (String) objects[6];
                periodoList.setCursoparalelo_cod(Long.valueOf(objects[7].toString()));
                
                if(estado1.equals("A"))
                {
                    periodoList.setEstado("ACTIVO");
                }
                else
                {
                    periodoList.setEstado("INACTIVO");
                }
                
                 listPeriodo.add(periodoList);
            }
            
           return listPeriodo; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaPeriodoCurso en PeriodoEAO: "+e.getMessage());
            return null;
        }
    } 
    
    /*Metodo Consulta Cursos Asignados en el Periodo señalado.-*/
    public List<TblCurso> consultaCursosAsignados( Long periodo_cod)
    {
        try {
            System.out.println(" consultaCursosAsignados del periodo: "+periodo_cod);
            String sql   =  " SELECT cu.curso_id, cu.curso_numero " +
                            " FROM tbl_curso cu " +
                            " WHERE EXISTS( " +
                            "    SELECT cp.curso_id " +
                            "    FROM tbl_cursoparalelo cp" +
                            "    INNER JOIN tbl_periodo p ON p.periodo_Id = cp.periodo_id AND p.periodo_sts = :estado" +
                            "    WHERE ( cp.periodo_id = :periodo_cod OR (:periodo_cod IS NULL OR :periodo_cod = 0) ) "+ 
                            "      AND( cp.cursoparalelo_sts = :estado )" +
                            "    ORDER BY cp.cursoparalelo_id )" +
                            " GROUP BY" +
                            "    cu.curso_id, cu.curso_numero";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("periodo_cod", periodo_cod);
            query.setParameter("estado", "A");
            
            System.out.println("Query consultaCursosAsignados : "+query.toString());
            List<Object[]> lResult      = new ArrayList<Object[]>();
            List<TblCurso> listCurso    = new ArrayList<TblCurso>();
            lResult                     = query.getResultList();
       
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                TblCurso curso = new TblCurso();
                curso.setCursoId(Long.valueOf(objects[0].toString()));
                curso.setCursoNumero((String) objects[1]);

        
                listCurso.add(curso);
            }
            
           return listCurso; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaCursosAsignados en PeriodoEAO: "+e.getMessage());
            return null;
        }
    } 

    
    /*Metodo actualizar Curso Asignado*/
    public void actualizaCursoAsignado(CursoAsignadoList cursoAsignado)
    {   
	   try 
            {
                System.out.println("CursoAsignadoId a actualizar :"+cursoAsignado.getCursoparalelo_cod());
                String sql  = "update tbl_cursoparalelo "
                        + " set cursoparalelo_sts = :estado "
                        + " where cursoparalelo_id = :Id  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("estado", cursoAsignado.getEstado());
                query.setParameter("Id", cursoAsignado.getCursoparalelo_cod());
                
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al actualizaCursoAsignado en AsignaCursoEAO: "+e.getMessage());
            }
    }
   
    /*Metodo Consulta Cursos Asignados al docente.-*/
    public List<CursoAsignadoList> consultaPeriodoCursoDocente( Long periodo_cod, Long curso_cod, Long paralelo_cod, String estado)
    {
        try {
            System.out.println("consultaPeriodoCursoDocente Inicio");
            String sql   = " select cp.periodo_id, p.periodo_anio, cp.curso_id, c.curso_numero, " +
                    "		    cp.paralelo_id, pa.paralelo_num, dc.docentecurso_sts , cp.cursoparalelo_id, " +
                    "               do.docente_id, Concat(do.docente_apellidos, ' ', do.docente_nombres  ) nombres, dc.docentecurso_Id, do.usuario_cod "+
                    " from tbl_docentecurso dc " +
                    " inner join tbl_cursoparalelo   cp on cp.cursoparalelo_id   = dc.cursoparalelo_id and cp.cursoparalelo_sts = :estado  " +
                    " inner join tbl_periodo   p on p.periodo_Id   = cp.periodo_id  " +
                    " inner join tbl_curso     c on c.curso_id	   = cp.curso_id " +
                    " inner join tbl_paralelo pa on pa.paralelo_id = cp.paralelo_id " +
                    " inner join tbl_docente  do on do.docente_id  = dc.docente_id " +
                    " where ( cp.periodo_id = :periodo_id or ( :periodo_id is null or :periodo_id = 0)  ) " +
                    " and ( cp.curso_id = :curso_id or ( :curso_id is null or :curso_id = 0)  ) " +
                    " and ( cp.paralelo_id = :paralelo_id or ( :paralelo_id is null or :paralelo_id = 0) ) " + 
                    //" and ( dc.docentecurso_sts = :estado or :estado = '' ) " +
                    " order by dc.docentecurso_Id ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("periodo_id", periodo_cod);
            query.setParameter("curso_id", curso_cod);
            query.setParameter("paralelo_id", paralelo_cod);
            query.setParameter("estado", estado);
            
            System.out.println("Query consultaPeriodoCursoDocente : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<CursoAsignadoList> listPeriodo      = new ArrayList<CursoAsignadoList>();
            lResult                            = query.getResultList();
            String estado2 = "" ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                CursoAsignadoList periodoList = new CursoAsignadoList();
                periodoList.setPeriodo_cod(Long.valueOf(objects[0].toString()));
                periodoList.setPeriodo_descripcion((String) objects[1]);
                periodoList.setCurso_cod(Long.valueOf(objects[2].toString()));
                periodoList.setCurso_descripcion((String) objects[3]);
                periodoList.setParalelo_cod(Long.valueOf(objects[4].toString()));
                periodoList.setParalelo_nombre((String) objects[5]);
               
                estado2 = (String) objects[6];
                periodoList.setCursoparalelo_cod(Long.valueOf(objects[7].toString()));
                
                if(estado2.equals("A"))
                {
                    periodoList.setEstado("ACTIVO");
                }
                else
                {
                    periodoList.setEstado("INACTIVO");
                }
                
                periodoList.setPersona_cod(Long.valueOf(objects[8].toString()));
                periodoList.setPersona_apeNom((String) objects[9]);
                periodoList.setCursoasignado_cod(Long.valueOf(objects[10].toString()));
                periodoList.setUsuario_cod(Long.valueOf(objects[11].toString()));
                listPeriodo.add(periodoList);
            }
            
           return listPeriodo; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaPeriodoCursoDocente en PeriodoEAO: "+e.getMessage());
            return null;
        }
    } 

    /*Metodo Consulta Cursos Asignados al estudiante.-*/
    public List<CursoAsignadoList> consultaPeriodoCursoEstudiante( Long periodo_cod, Long curso_cod, Long paralelo_cod, Long estudiante_cod, String estado)
    {
        try {
            System.out.println("consultaPeriodoCursoEstudiante Inicio");
            String sql   = " select cp.periodo_id, p.periodo_anio, cp.curso_id, c.curso_numero, " +
                    "		 cp.paralelo_id, pa.paralelo_num, ec.estudiantecurso_sts , cp.cursoparalelo_id, " +
                    "            e.estudiante_id, Concat(e.estudiante_ape, ' ', e.estudiante_nom  ) nombres , ec.estudiantecurso_Id "+
                    " from tbl_estudiantecurso ec "+
                    " inner join tbl_cursoparalelo  cp on cp.cursoparalelo_id = ec.cursoparalelo_id and cp.cursoparalelo_sts = :estado" +
                    " inner join tbl_periodo   p on p.periodo_Id   = cp.periodo_id  " +
                    " inner join tbl_curso     c on c.curso_id	  	= cp.curso_id " +
                    " inner join tbl_paralelo pa on pa.paralelo_id = cp.paralelo_id " +
                    " inner join tbl_estudiante e on e.estudiante_id = ec.estudiante_id " +
                    " where ( cp.periodo_id = :periodo_id or ( :periodo_id is null or :periodo_id = 0)  ) " +
                    " and ( cp.curso_id = :curso_id or ( :curso_id is null or :curso_id = 0)  ) " +
                    " and ( cp.paralelo_id = :paralelo_id or ( :paralelo_id is null or :paralelo_id = 0) ) " + 
                    " and ( e.estudiante_id = :estudiante_id or ( :estudiante_id is null or :estudiante_id = 0) ) " +
                    //" and ( ec.estudiantecurso_sts = :estado  or :estado = '' ) " +
                    " order by ec.estudiantecurso_Id ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("periodo_id", periodo_cod);
            query.setParameter("curso_id", curso_cod);
            query.setParameter("paralelo_id", paralelo_cod);
            query.setParameter("estado", estado);
            query.setParameter("estudiante_id", estudiante_cod);
            
            System.out.println("Query consultaPeriodoCurso : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<CursoAsignadoList> listPeriodo      = new ArrayList<CursoAsignadoList>();
            lResult                            = query.getResultList();
            String estado2 = "" ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                CursoAsignadoList periodoList = new CursoAsignadoList();
                periodoList.setPeriodo_cod(Long.valueOf(objects[0].toString()));
                periodoList.setPeriodo_descripcion((String) objects[1]);
                periodoList.setCurso_cod(Long.valueOf(objects[2].toString()));
                periodoList.setCurso_descripcion((String) objects[3]);
                periodoList.setParalelo_cod(Long.valueOf(objects[4].toString()));
                periodoList.setParalelo_nombre((String) objects[5]);
                
                estado2 = (String) objects[6];
                periodoList.setCursoparalelo_cod(Long.valueOf(objects[7].toString()));
                
                if(estado2.equals("A"))
                {
                    periodoList.setEstado("ACTIVO");
                }
                else
                {
                    periodoList.setEstado("INACTIVO");
                }
                periodoList.setPersona_cod(Long.valueOf(objects[8].toString()));
                periodoList.setPersona_apeNom((String) objects[9]);
                periodoList.setCursoasignado_cod(Long.valueOf(objects[10].toString()));
                listPeriodo.add(periodoList);
            }
            
           return listPeriodo; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaPeriodoCursoEstudiante en PeriodoEAO: "+e.getMessage());
            return null;
        }
    } 

    /*Metodo actualizar Curso Asignado al Docente*/
    public void actualizaCursoAsignadoDocente(CursoAsignadoList cursoAsignado)
    {   
	   try 
            {
                System.out.println("actualizaCursoAsignadoDocente Id :"+cursoAsignado.getCursoasignado_cod());
                String sql  = "update tbl_docentecurso "
                        + " set docentecurso_sts  = :estado "
                        + " where docentecurso_Id = :Id  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("estado", cursoAsignado.getEstado());
                query.setParameter("Id", cursoAsignado.getCursoasignado_cod());
                
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al actualizaCursoAsignadoDocente en AsignaCursoEAO: "+e.getMessage());
            }
    }
   
    /*Metodo actualizar Curso Asignado al Estudiante*/
    public void actualizaCursoAsignadoEstudiante(CursoAsignadoList cursoAsignado)
    {   
	   try 
            {
                System.out.println("actualizaCursoAsignadoEstudiante a actualizar :"+cursoAsignado.getCursoasignado_cod());
                String sql  = "update tbl_estudiantecurso "
                        + " set estudiantecurso_sts = :estado "
                        + " where estudiantecurso_Id = :Id  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("estado", cursoAsignado.getEstado());
                query.setParameter("Id", cursoAsignado.getCursoasignado_cod());
                
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al actualizaCursoAsignadoEstudiante en AsignaCursoEAO: "+e.getMessage());
            }
    }
   
 
}
