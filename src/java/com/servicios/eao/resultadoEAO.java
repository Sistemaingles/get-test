
package com.servicios.eao;



import com.entidades.TblDestreza;
import com.entidades.TblResultado;
import com.entidades.TblSubtema;
import com.entidades.TblUnidad;
import com.entidades.TblUsuarios;
import com.entidades.listas.ResultadoTallerList;
import com.entidades.listas.UnidadList;
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

public class resultadoEAO 
{
    @PersistenceContext(unitName="project",type = PersistenceContextType.TRANSACTION)
    protected EntityManager administradorEntidad;
    private String sql;

    public void ingresaResultado(TblResultado resultado)
    {
        try 
        {
            System.out.println("ingresaResultado en resultadoEAO");
              
            sql  = " update tbl_resultado t" +
                    " set t.resultado_sts     = 'I' ,"+
                    "     t.resultado_dlttim  = sysdate() " +
                    " where t.usuario_cod = :usuarioCod  " +
                    " and t.unidad_id     = :unidadId " +
                    " and t.subtema_id    = :subtemaId " +
                    " and t.resultado_sts  = 'A' " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("usuarioCod", resultado.getUsuarioCod());
            query.setParameter("unidadId", resultado.getUnidadId());
            query.setParameter("subtemaId", resultado.getSubtemaId());
            query.executeUpdate();       
                    
                    
            administradorEntidad.persist(resultado);
            
        } catch (Exception e) 
        {
            System.out.println("Error al guardar Resultado en EAO: "+e.getMessage());
        }
    }
    
    public void eliminaResultado(TblResultado resultado)
    {
        try 
        {
            sql  = "delete from tbl_resultado where id = :id  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("id", resultado.getResultadoId());
            query.executeUpdate();
            
        } catch (Exception e) 
        {
            System.out.println("Error al eliminar Resultado en EAO: "+e.getMessage());
        }
    }
    
    public List<TblResultado> obtenerResultados() 
    {
        try 
        {
            sql = " select * "+
                  " from tbl_resultado  order by id ";
            
            Query query = administradorEntidad.createNativeQuery(sql,TblResultado.class);
            return query.getResultList();
           
        }catch(Exception ex) 
        {
            System.out.println("ERROR al obtenerResultado en resultadoEAO:"+ex.getMessage());
            return null;
	}	

    }

    public TblResultado obtieneResultadosporUnidad( Long usuario_cod, Long unidad_id, Long subtema_id) 
    {
        try 
        {
            int intento = 0;
            
            
            
            sql =   " select e.taller_Id, e.actividad_id, Count(e.resultado_Id) Tot_Respuestas, " +
                    "        a.actividad_pregunta Pregunta, " +
                    "   (  CASE a.actividad_tipopreg " +
                    "	When 'U' Then 'Seleccion Unica'	 " +
                    "	When 'M' Then 'Seleccion Multiple' " +
                    "	When 'R' Then 'Reordenar' " +
                    "	Else 'Otro Tipo' " +
                    "	END)  TipoPreguntas, " +
                    "   (  CASE a.actividad_tipopreg " +
                    "       When 'L' Then " +
                    "	 ( SELECT IF( Count(e.resultado_Id) = Sum( ( CASE  " +
                    "            WHEN r.respuesta_correcta = e.resultado_respuesta THEN 1 " +
                    "           ELSE 0 " +
                    "       END )) , 'V', 'F') ) " +
                    "       When 'U' Then " +
                    "	 ( SELECT IF( Count(e.resultado_Id) = Sum( ( CASE  " +
                    "            WHEN r.respuesta_correcta = e.resultado_respuesta THEN 1 " +
                    "           ELSE 0 " +
                    "       END )) , 'V', 'F') ) " +
                    "       When 'M' Then " +
                    "   ( SELECT IF( Count(e.resultado_Id) = Sum( ( CASE "              +   
                    "      WHEN r.respuesta_correcta = e.resultado_respuesta THEN 1  "  +
                    "      ELSE 0  "                                                    +
                    "     END )) , 'V', 'F') )  "                                       +
                    "	When 'R' Then " +
                    "   ( SELECT IF( Count(e.resultado_Id) = Sum( ( CASE "              +   
                    "      WHEN r.respuesta_correcta = e.resultado_respuesta THEN 1  "  +
                    "      ELSE 0  "                                                    +
                    "     END )) , 'V', 'F') )  "                                       +
                    "	Else" +
                    "	'F' " +
                    "	END)  EsCorrecta ," +
                    "  ( Select  p.PARAM_NUM from tbl_param p where Trim(p.OBSERVACION) = 'PCT_APROBADO') Pct_Aprop , " +
                    "  ( IFNULL( Max(rs.resultado_intento), 0)  + 1 ) intento , "+
                    "  e.subtema_id , a.destreza_Id " +
                    "  from tbl_resultadotaller e " +
                    "  inner join tbl_usuarios u  on u.ID = e.usuario_cod " +
                    "  inner join tbl_respuesta r on r.respuesta_id = e.respuesta_id " +
                    "  inner join tbl_actividad a on a.actividad_id = e.actividad_id " +
                    "  inner join tbl_presentaciontaller p on p.taller_id = e.taller_Id " +
                    "  left join tbl_resultado rs on rs.usuario_cod = e.usuario_cod and rs.unidad_id = p.unidad_Id and rs.subtema_id =  e.subtema_id " +
                    "  where e.usuario_cod = :usuario_cod " +
                    "  and p.unidad_Id = :unidad_id " +
                    "  and e.subtema_id = :subtemaId "+
                    "  and e.resultado_sts = 'A' " +
                    "  group by e.taller_Id, e.actividad_id ";
            
            Query query     =   administradorEntidad.createNativeQuery(sql);
            query.setParameter("usuario_cod", usuario_cod);
            query.setParameter("unidad_id", unidad_id);
            query.setParameter("subtemaId", subtema_id);
            
            System.out.println("Query obtiene Resultado por Unidad: "+query.toString());
    
            List<Object[]> lResult              =  new ArrayList<Object[]>();
            List<TblResultado> listResultado    =  new ArrayList<TblResultado>();
            lResult                             =  query.getResultList();
            
            TblResultado resultado = new TblResultado();
            String estado       =   "";
            
            TblUsuarios usuarios = new TblUsuarios();
            usuarios.setId(usuario_cod);
                
            TblUnidad unidad = new TblUnidad();
            unidad.setUnidadId(unidad_id);
            
            Float cant_verd  = (float) 0;
            Float cant_total = (float) 0;
            Float porcentaje = (float) 0;
            Float pctAprob   = (float) 0;
            
            Long subtemaId  = Long.valueOf("0");
            Long destrezaid  = Long.valueOf("0");
            
            for (Object[] objects : lResult) 
            {
                /*System.out.println("Tot Respuestas: "+objects[2].toString());
                System.out.println("esCorrecta: "+objects[5].toString());
                System.out.println("Porcentaje: "+objects[6].toString());
                System.out.println("Porcentaje Aprob.: "+objects[6].toString());*/
                
                String esCorrecta   =   objects[5].toString();
                pctAprob            =   Float.parseFloat(objects[6].toString());
                subtemaId           =   Long.valueOf(objects[8].toString());
                destrezaid          =   Long.valueOf(objects[9].toString());
                
                if( esCorrecta.equals("V") ){
                    cant_verd = cant_verd + 1;
                }
                
                cant_total =   cant_total + 1;
                intento    =   Integer.parseInt(objects[7].toString()); 
            }
            
            porcentaje =   ( cant_verd / cant_total ) * 100; 

 
            if(Float.compare(porcentaje, pctAprob) >= 0)
            {
                estado  =   "APROBADO";
                System.out.println("EstadoA: "+estado);
            }   
            else
            {
                estado  =   "REPROBADO";
                System.out.println("EstadoR: "+estado);
            }
            
            System.out.println(" Total de Preguntas:"+cant_total+ 
                               " Preguntas Correctas: "+cant_verd+
                               " Porcentaje: "+porcentaje+
                               " Estado: "+estado);
            
            resultado.setResultadoEstado(estado);
            resultado.setResultadoInstim(new Date());
            resultado.setResultadoInsusr("MESPINOZA");
            resultado.setResultado_updusr(" ");
            resultado.setResultadoDltusr(" ");
            resultado.setResultadoSts("A");
            resultado.setUnidadId(unidad);
            resultado.setUsuarioCod(usuarios);
            resultado.setResultadototRespuesta(cant_total);
            resultado.setResultadototAciertos(cant_verd);
            resultado.setResultadoPorcentaje(porcentaje);
            resultado.setResultadoIntento(intento);
            TblSubtema subtema  = new TblSubtema();
            subtema.setSubtemaId(subtemaId);
            resultado.setSubtemaId(subtema);
            
            TblDestreza destreza  = new TblDestreza();
            destreza.setDestrezaId(destrezaid);
            resultado.setDestrezaId(destreza);
            return resultado; 
           
        }catch(Exception ex) 
        {
            System.out.println("ERROR al obtener Resultados por Unidad:"+ex.getMessage());
            return null;
	}	
        

    }
    
    /*Metodo obtiene Unidades registradas en resultado de Talleres*/
    public List<UnidadList> consultaUnidadResultado( Long usuario_cod)
    {
        try {
            System.out.println("consultaUnidadResultado usuario_cod: "+usuario_cod);
            String sql   =  "select  u.unidad_nombre, c.curso_descripcion, p.paralelo_nombre , u.unidad_sts , " +
                            "        u.cursoparalelo_id , u.unidad_id  " +
                            " From tbl_unidad u " +
                            " Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id " +
                            " Inner join tbl_curso c on c.curso_id = cp.curso_id " +
                            " Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id" +
                            " Inner join tbl_estudiante e on e.usuario_cod = :usuarioCod " +
                            " where u.unidad_sts = :estado" +
                            " and u.unidad_Id  in ( select pt.unidad_Id  " +
                            "			  from tbl_resultado pt where pt.resultado_sts = :estado ) "+
                            " order by u.unidad_Id  ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("usuarioCod", usuario_cod);
            query.setParameter("estado", "A");
            
            
            
            System.out.println("Query consultaUnidadResultado : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<UnidadList> listUnidad        = new ArrayList<UnidadList>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                UnidadList unidadList = new UnidadList();
                unidadList.setNombres((String) objects[0]);
                unidadList.setCurso_nombre((String) objects[1]);
                unidadList.setParalelo_nombre((String) objects[2]);
               

                estado = (String) objects[3];
                unidadList.setCursoparaleloid(Long.valueOf(objects[4].toString()));
                unidadList.setUnidad_id(Long.valueOf(objects[5].toString()));
                
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
            System.out.println("Error al consultaUnidadResultado en resultadoEAO: "+e.getMessage());
            return null;
        }
    } 
    
    
    /*Consulta Resultados de Talleres segun Codigo de Usuario del Estudiante*/
    public List<ResultadoTallerList> consultaResultadoxUnidad( Long usuario_cod, Long unidad_id)
    {
        try {
            System.out.println("consultaResultadoxUnidad usuario_cod: "+usuario_cod+
                                " unidad_id: "+unidad_id);
            String sql = "select r.resultado_id, r.unidad_id , u.unidad_nombre, r.usuario_cod, r.resultado_totRespuesta, r.resultado_totAciertos, r.resultado_porcentaje, " +
                            "	 r.resultado_intento, r.resultado_estado, " +
                            "	 ( Case r.resultado_sts" +
                            "	     When 'A' Then 'Activo'" +
                            "	     When 'I' Then 'Inactivo'" +
                            "	  End ) sts , r.subtema_id, t.subtema_desc	 " +
                            " from tbl_resultado r " +
                            " inner join tbl_unidad u on u.unidad_Id = r.unidad_id " +
                            " inner join tbl_subtema t on t.unidad_id = u.unidad_Id and t.subtema_id = r.subtema_id " +
                            " where r.resultado_sts = :estado" +
                            " and r.usuario_cod     = :UsuarioCod "+
                            " and ( r.unidad_id = :UnidadId or :UnidadId = 0 ) " +
                            " and r.resultado_sts = :estado "
                            ;
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("UsuarioCod", usuario_cod);
            query.setParameter("UnidadId", unidad_id);
            
            System.out.println("Query consultaResultadoxUnidad : "+query.toString());
            List<Object[]> lResult                        = new ArrayList<Object[]>();
            List<ResultadoTallerList> listResultado       = new ArrayList<ResultadoTallerList>();
            lResult                                       = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                TblUnidad unidad = new TblUnidad();
                unidad.setUnidadId(Long.valueOf(objects[1].toString()));
                TblUsuarios usuario = new TblUsuarios();
                usuario.setId(Long.valueOf(objects[3].toString()));
                
                ResultadoTallerList resultado = new ResultadoTallerList();
                resultado.setResultadoId(Long.valueOf(objects[0].toString()));
                resultado.setUnidadId(unidad);
                resultado.setUnidad_nombre(objects[2].toString());
                resultado.setUsuarioCod(usuario);
                resultado.setResultadototRespuesta(Float.parseFloat(objects[4].toString()));
                resultado.setResultadototAciertos(Float.parseFloat(objects[5].toString()));
                resultado.setResultadoPorcentaje(Float.parseFloat(objects[6].toString()));
                resultado.setResultadoIntento(Integer.parseInt(objects[7].toString()));
                resultado.setResultadoEstado(objects[8].toString());
                resultado.setResultadoSts(objects[9].toString());
                resultado.setSubtemaId(Long.valueOf(objects[10].toString()));
                resultado.setSubtemaDesc(objects[11].toString());
                listResultado.add(resultado);
            }
            
            return listResultado;
        }
        catch(Exception e) { 
            System.out.println("Error al consultaResultadoUnidad en resultadoEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    /*Consulta Resultados de Talleres segun Codigo de Usuario del Docente */
    public List<ResultadoTallerList> consultaResultadoEstudiantes( Long usuario_cod, Long unidad_id)
    {
        try {
            System.out.println("consultaResultadoEstudiantes usuario_cod: "+usuario_cod+
                                " unidad_id: "+unidad_id);
            String sql = "select r.resultado_id, r.unidad_id , u.unidad_nombre, r.usuario_cod, r.resultado_totRespuesta, r.resultado_totAciertos, r.resultado_porcentaje, " +
                            "	 r.resultado_intento, r.resultado_estado, " +
                            "	 ( Case r.resultado_sts" +
                            "	     When 'A' Then 'Activo'" +
                            "	     When 'I' Then 'Inactivo'" +
                            "	  End ) sts	, " +
                            "  ( Select Concat(e.estudiante_nom, ' ',  e.estudiante_ape)  " +
                            "	 from tbl_estudiante e " +
                            "	where e.usuario_cod = r.usuario_cod " +
                            "	)  estudiante ,"                       +
                            " cu.curso_id, cu.curso_numero "+
                            " from tbl_resultado r " +
                            " inner join tbl_unidad u on u.unidad_Id = r.unidad_id " +
                            " inner join tbl_docentecurso dc on dc.cursoparalelo_id = u.cursoparalelo_id " +
                            " inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = dc.cursoparalelo_id " +
                            " inner join tbl_curso cu on cu.curso_id = cp.curso_id " +
                            " inner join tbl_docente d on d.docente_id = dc.docente_id " +
                            " where r.resultado_sts = :estado" +
                            " and d.usuario_cod     = :UsuarioCod "+
                            " and ( r.unidad_id = :UnidadId or :UnidadId = 0 ) " +
                            " and r.resultado_sts = :estado "
                            ;
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("UsuarioCod", usuario_cod);
            query.setParameter("UnidadId", unidad_id);
            
            System.out.println("Query consultaResultadoEstudiantes : "+query.toString());
            List<Object[]> lResult                        = new ArrayList<Object[]>();
            List<ResultadoTallerList> listResultado       = new ArrayList<ResultadoTallerList>();
            lResult                                       = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                TblUnidad unidad = new TblUnidad();
                unidad.setUnidadId(Long.valueOf(objects[1].toString()));
                TblUsuarios usuario = new TblUsuarios();
                usuario.setId(Long.valueOf(objects[3].toString()));
                
                ResultadoTallerList resultado = new ResultadoTallerList();
                resultado.setResultadoId(Long.valueOf(objects[0].toString()));
                resultado.setUnidadId(unidad);
                resultado.setUnidad_nombre(objects[2].toString());
                resultado.setUsuarioCod(usuario);
                resultado.setResultadototRespuesta(Float.parseFloat(objects[4].toString()));
                resultado.setResultadototAciertos(Float.parseFloat(objects[5].toString()));
                resultado.setResultadoPorcentaje(Float.parseFloat(objects[6].toString()));
                resultado.setResultadoIntento(Integer.parseInt(objects[7].toString()));
                resultado.setResultadoEstado(objects[8].toString());
                resultado.setResultadoSts(objects[9].toString());
                resultado.setEstudiante(objects[10].toString());
                resultado.setCursoId(Long.valueOf(objects[11].toString()));
                resultado.setCurso(objects[12].toString());
                
                listResultado.add(resultado);
            }
            
            return listResultado;
        }
        catch(Exception e) { 
            System.out.println("Error al consultaResultadoEstudiantes en resultadoEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    /*Consulta Resultados de Actividades segun Codigo de Usuario del Estudiante y Destreza*/
    public List<ResultadoTallerList> consultaResultadoxDestreza( Long usuario_cod, Long destreza_id)
    {
        try {
            System.out.println("consultaResultadoxDestreza usuario_cod: "+usuario_cod+
                                " destreza_id: "+destreza_id);
            String sql = " SELECT tmp.Destreza, " +
                            "    tmp.NoUnidad, " +
                            "    tmp.Unidad, " +
                            "    GROUP_CONCAT(DISTINCT tmp.Subtema) Subtemas, " +
                            "    GROUP_CONCAT(tmp.Puntaje) Resultados, " +
                            "    CONCAT( CONVERT( SUM(tmp.Total_Aciertos) ,char) , ' / ', CONVERT( SUM(tmp.Total_Respuesta ),char) ) Aciertos, " +
                            "    CONCAT( ROUND(SUM(tmp.Puntaje),2), ' / ', COUNT(TMP.ID) * 100 ) Puntaje, " +
                            "    CONCAT( ROUND( ( ROUND( SUM(tmp.Puntaje) / COUNT(TMP.ID), 2 ) / 100 ) * 10, 2 ), ' /10 ') Promedio, " +
                            "    CONCAT( MAX(tmp.Pruebas), ' / ', ( SELECT p.PARAM_NUM FROM tbl_param P WHERE P.OBSERVACION = 'NUMERO_INTENTOS') ) Pruebas " +
                            "FROM " +
                            "    ( " +
                            "    SELECT " +
                            "        r.resultado_id Id, " +
                            "        d.destreza_nombre Destreza, " +
                            "        un.unidad_descripcion NoUnidad, " +
                            "        un.unidad_nombre Unidad, " +
                            "        s.subtema_desc Subtema, " +
                            "        ROUND(r.resultado_porcentaje, 2) Puntaje, " +
                            "        r.resultado_totRespuesta Total_Respuesta, " +
                            "        r.resultado_totAciertos Total_Aciertos, " +
                            "        r.resultado_intento Pruebas " +
                            "    FROM tbl_resultado r " +
                            "    INNER JOIN tbl_usuarios u ON " +
                            "        u.ID = r.usuario_cod " +
                            "    INNER JOIN tbl_destreza d ON " +
                            "        d.destreza_Id = r.destreza_Id " +
                            "    INNER JOIN tbl_unidad un ON " +
                            "        un.unidad_Id = r.unidad_id " +
                            "    INNER JOIN tbl_subtema s ON " +
                            "        s.unidad_id = r.unidad_id AND s.subtema_id = r.subtema_id AND s.subtema_sts = :estado " +
                            "    WHERE r.usuario_cod = :UsuarioCod "+
                            "      AND ( r.destreza_iD = :Destreza_id or :Destreza_id = 0 )  " +
                            "      AND r.resultado_sts = :estado " +
                            " ORDER BY r.destreza_Id "+
                            " ) tmp " +
                            " GROUP BY " +
                            "    tmp.Destreza, " +
                            "    tmp.NoUnidad, " +
                            "    tmp.Unidad "
                        ;
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("UsuarioCod", usuario_cod);
            query.setParameter("Destreza_id", destreza_id);
            
            System.out.println("Query consultaResultadoxDestreza : "+query.toString());
            List<Object[]> lResult                        = new ArrayList<Object[]>();
            List<ResultadoTallerList> listResultado       = new ArrayList<ResultadoTallerList>();
            lResult                                       = query.getResultList();
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                 System.out.println("Destreza: "+objects[0].toString()+
                            " UnidadDesc: "+objects[1].toString()+
                         " Unidad: "+objects[2].toString()+
                         " Subtema: "+objects[3].toString()+
                         " Resultados: "+objects[4].toString()+
                         " Aciertos: "+objects[5].toString()+
                         " Puntaje: "+objects[6].toString()+
                         " Promedio: "+objects[7].toString()+
                         " Pruebas: "+objects[8].toString()
                         );
            
    
                ResultadoTallerList resultado = new ResultadoTallerList();
                resultado.setDestreza(objects[0].toString());
                resultado.setUnidad_descripcion(objects[1].toString());
                resultado.setUnidad_nombre(objects[2].toString());
                resultado.setSubtemaDesc(objects[3].toString());
                resultado.setResultados(objects[4].toString());
                resultado.setAciertos(objects[5].toString());
                resultado.setPuntaje(objects[6].toString());
                resultado.setPromedio(objects[7].toString());
                resultado.setPruebas(objects[8].toString());

                listResultado.add(resultado);
            }
            
            return listResultado;
        }
        catch(Exception e) { 
            System.out.println("Error al consultaResultadoxDestreza en resultadoEAO: "+e.getMessage());
            return null;
        }
        
    }
    
}
