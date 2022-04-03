
package com.servicios.eao;

import com.entidades.TblActividad;
import com.entidades.listas.ActividadList;
import com.entidades.listas.RespuestasList;
import com.entidades.listas.TallerList;
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
public class ActividadCursoEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    
    /*Metodo obtiene Actividad Registrado y con Curso asignado*/
    public List<ActividadList> consultaActividadCurso( String actividad, String pregunta)
    {
        try {
            System.out.println("consultaActividadCurso Inicio");
            String sql   = " select pe.periodo_descripcion, u.actividad_descripcion, u.actividad_pregunta,  u.actividad_archivo, c.curso_descripcion, p.paralelo_nombre , u.actividad_sts ," + 
                            "   u.cursoparalelo_id , u.actividad_id  " +
                            "From tbl_actividad u " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id " +
                            "Inner join tbl_periodo pe on pe.periodo_Id = cp.periodo_Id " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id " +
                            "where u.actividad_sts = :estado " +
                            "and ( u.actividad_descripcion      like :actividad or :actividad 	= '' ) " +
                            "and ( u.actividad_pregunta         like :pregunta  or :pregunta    = '' ) " +
                            " order by u.actividad_id desc ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("actividad", "%" + actividad + "%" );
            query.setParameter("pregunta", "%" + pregunta + "%");
            
            
            System.out.println("Query consultaActividadCurso : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<ActividadList> listActividad  = new ArrayList<ActividadList>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                ActividadList actividadList = new ActividadList();
                actividadList.setPeriodo((String) objects[0]);
                actividadList.setActividad((String) objects[1]);
                actividadList.setPregunta((String) objects[2]);
                actividadList.setArchivo((String) objects[3]);
                actividadList.setCurso_nombre((String) objects[4]);
                actividadList.setParalelo_nombre((String) objects[5]);

                estado = (String) objects[6];
                actividadList.setCursoparaleloid(Long.valueOf(objects[7].toString()));
                actividadList.setActividad_id(Long.valueOf(objects[8].toString()));
                
                if(estado.equals("A"))
                {
                    actividadList.setEstado("ACTIVO");
                }
                else
                {
                    actividadList.setEstado("INACTIVO");
                }
                
                 listActividad.add(actividadList);
            }
            
           return listActividad; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaActividadCurso en UnidadCursoEAO: "+e.getMessage());
            return null;
        }
    } 
 
    /*Metodo ingresa Actividad si no existe*/
    public Long ingresaActividad(TblActividad actividad)
    {
        try 
        {
            administradorEntidad.persist(actividad);
            administradorEntidad.flush();
            System.out.println("Actividad Id registrado:"+actividad.getActividadId());
            
            return actividad.getActividadId();
            
            
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaUnidad en UnidadCursoEAO: "+e.getMessage());
            return null;
        }
        
    }
    
    public boolean eliminaActividad(Long actividad_id)
    {
        boolean eliminado = false;
        try 
        {
            String sql  = "delete from tbl_actividad where actividad_id = :actividad_id  " ;
                
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("actividad_id", actividad_id);
            query.executeUpdate();
           
           eliminado = true;
            
        } catch (Exception e) 
        {
            eliminado = false;
            System.out.println("Error en metodo eliminaActividad en ActividadCursoEAO: "+e.getMessage());

        }
         return  eliminado;
    }
 
    
    /*Metodo Presenta actividad registrada por el Docente*/
    public List<TallerList> consultaActividad(  Long destrezaId, Long unidadId, Long subtemaId)
    {
        try {
            
            System.out.println("ActividadCursoEAO consultaActividad destrezaId: "+destrezaId + " unidadId: "+unidadId+ " subtemaId: "+subtemaId);
            
            String sql   = " select pe.periodo_descripcion, u.actividad_descripcion, u.actividad_pregunta,  u.actividad_archivo, c.curso_descripcion, p.paralelo_nombre , u.actividad_sts , " +
                            " u.cursoparalelo_id , u.actividad_id , s.subtema_desc , un.unidad_nombre, d.destreza_nombre , un.unidad_Id ,  " +
                            " u.actividad_tipopreg " +
                            "From tbl_actividad u  " +
                            "Inner join tbl_destreza d on d.destreza_Id = u.destreza_Id " +
                            "Inner join tbl_unidad un on un.unidad_Id = u.unidad_id  " +
                            "Inner join tbl_subtema s on s.unidad_Id = u.unidad_id and s.subtema_id = u.subtema_id " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id  " +
                            "Inner join tbl_periodo pe on pe.periodo_Id = cp.periodo_Id  " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id  " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id  " +
                            "where u.actividad_sts = :estado "+
                            "and u.destreza_Id = :destrezaId " +
                            "and u.unidad_id   = :unidadId " +
                            "and ( u.subtema_id  = :subtemaId or :subtemaId = 0 ) " +
                            "order by u.actividad_id ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("estado", "A");
            query.setParameter("destrezaId",  destrezaId);
            query.setParameter("unidadId",  unidadId);
            query.setParameter("subtemaId",  subtemaId);
            
            System.out.println("Query consultaTaller : "+query.toString());
            List<Object[]> lResult             = new ArrayList<Object[]>();
            List<TallerList> listTaller     = new ArrayList<TallerList>();
            lResult                            = query.getResultList();
            String estado ;
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                TallerList actividadList = new TallerList();
                actividadList.setPeriodo((String) objects[0]);
                actividadList.setActividad((String) objects[1]);
                actividadList.setPregunta((String) objects[2]);
                actividadList.setArchivo((String) objects[3]);
                actividadList.setCurso_nombre((String) objects[4]);
                actividadList.setParalelo_nombre((String) objects[5]);

                estado = (String) objects[6];
                actividadList.setCursoparaleloid(Long.valueOf(objects[7].toString()));
                
                Long actividad_id = Long.valueOf(objects[8].toString());
                actividadList.setActividad_id(actividad_id);
                actividadList.setSubtema(objects[9].toString());
                actividadList.setUnidad(objects[10].toString());
                actividadList.setDestreza(objects[11].toString());
                actividadList.setUnidad_id(Long.valueOf(objects[12].toString()));
                
                if(estado.equals("A"))
                {
                    actividadList.setEstado("ACTIVO");
                }
                else
                {
                    actividadList.setEstado("INACTIVO");
                }
                
                actividadList.setTipo_pregunta(objects[13].toString());
                actividadList.setRespuestas( obtieneRespuestasporActividad( actividad_id, objects[13].toString() ));
              
                listTaller.add(actividadList);
            }
            
           return listTaller; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaActividad en ActividadCursoEAO: "+e.getMessage());
            return null;
        }
    } 
   
    public List<RespuestasList> obtieneRespuestasporActividad(Long actividad_id, String tipo_pregunta) 
    {
        try 
        {
            System.out.println("obtieneRespuestasporActividad en ActividadCursoEAO actividad_id: "+actividad_id+
                            " tipo_pregunta: "+tipo_pregunta);
            
            /*Consulta Respuestas de la actividad*/
           String sql = " select r.respuesta_id, " +
                 " (  CASE :tipo_pregunta " +
                 " When 'U' Then r.respuesta_desc " +
                 " When 'M' Then r.respuesta_desc " +
                 " When 'R' Then r.respuesta_desc   " +
                 " Else r.respuesta_desc  " +
                 " END)  respuesta_desc ," +
                 " r.respuesta_correcta, r.respuesta_sts , r.respuesta_orden "  +   
                 " from tbl_respuesta r " +
                 " inner join tbl_actividad a on a.actividad_id = r.actividad_id " +
                 " where r.actividad_id = :actividad_id " +
                 " and r.respuesta_sts = 'A' "   + 
                 " order by respuesta_orden ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("actividad_id", actividad_id);
            query.setParameter("tipo_pregunta", tipo_pregunta);
                
                List<Object[]> lResultados        = new ArrayList<Object[]>();
                List<RespuestasList> listRespuesta  = new ArrayList<RespuestasList>();
                lResultados                       = query.getResultList();
                
                
                System.out.println("Cantidad de Registros Respuesta:"+lResultados.size());
                for (Object[] objectos_resp : lResultados) 
                {
                    RespuestasList respuesta = new RespuestasList();
          
                    respuesta.setId_actividad(actividad_id);
                    //System.out.println("presentaTallerEstado Desc: "+(String) objectos_resp[1]);
                    
                    respuesta.setId(Long.valueOf(objectos_resp[0].toString()));
                    respuesta.setDescripcion((String) objectos_resp[1]);
                    respuesta.setEsCorrecta(objectos_resp[3].toString().charAt(0));
                    respuesta.setTipoPregunta(tipo_pregunta);
                    //respuesta.setStatus((String) objectos_resp[3]);
                    respuesta.setOrden((int) objectos_resp[4]);
                    listRespuesta.add(respuesta);
                    
                    //System.out.println("presentaTallerEstado Desc2: "+(String) objectos_resp[1]);
                    
                }
           
 
            return listRespuesta;
           
        }catch(Exception ex) 
        {
            System.out.println("ERROR al obtener obtieneRespuestasporActividad en ActividadCursoEAO:"+ex.getMessage());
            return null;
	}	

    }
  

}
