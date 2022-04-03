
package com.servicios.eao;


import com.entidades.listas.PresentaTallerList;
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
public class presentatallerEAO {
    
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;
    private String sql   = "";
    
    /*Metodo Presenta Listado de Talleres registrados del Docente*/
    public List<PresentaTallerList> listaTallerRegistrado( Long usuarioCod, String taller_nombre, String taller_descripcion)
    {
        try {
            sql   = "select pt.taller_id, u.unidad_nombre, pt.taller_nombre, pt.taller_descripcion, c.curso_numero, p.paralelo_num, " +
                    "      ( Case pt.taller_sts" +
                    "	      When  'A' Then 'Activo'" +
                    "         When  'I' Then 'Inactivo'" +
                    "	      End) status , pt.destreza_Id, de.destreza_nombre " +
                    " from tbl_presentaciontaller pt " +
                    " inner join tbl_destreza de " +
                    " on de.destreza_Id = pt.destreza_Id " +
                    " inner join tbl_unidad u " +
                    " on u.unidad_Id = pt.unidad_Id " +
                    " inner join tbl_cursoparalelo cp " +
                    " on cp.cursoparalelo_id = u.cursoparalelo_id " +
                    " inner join tbl_curso c " +
                    " on c.curso_id = cp.curso_id " +
                    " inner join tbl_paralelo p " +
                    " on p.paralelo_id = cp.paralelo_id " +
                    " inner join tbl_docentecurso dc " +
                    " on dc.cursoparalelo_id = cp.cursoparalelo_id " +
                    " inner join tbl_docente d " +
                    " on d.docente_id = dc.docente_id " +
                    " where d.usuario_cod = :usuarioCod " +
                    " and ( pt.taller_nombre      like  :nombre       or :nombre 	 = '' )  " + 
                    " and ( pt.taller_descripcion like  :descripcion  or :descripcion    = '' ) "
                    ;
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("usuarioCod",  usuarioCod);
            query.setParameter("nombre", "%" + taller_nombre + "%" );
            query.setParameter("descripcion", "%" + taller_descripcion + "%");
            
            System.out.println("Query listaTallerRegistrado : "+query.toString());
            List<Object[]> lResult                  = new ArrayList<Object[]>();
            List<PresentaTallerList> listTaller     = new ArrayList<PresentaTallerList>();
            lResult                                 = query.getResultList();
    
            System.out.println("Cantidad de Registros :"+lResult.size());
           
            for (Object[] objects : lResult) 
            {
                PresentaTallerList taller = new PresentaTallerList();
                taller.setTaller_id(Long.valueOf(objects[0].toString()));
                taller.setUnidad(objects[1].toString());
                taller.setTaller_nombre(objects[2].toString());
                taller.setTaller_descripcion(objects[3].toString());
                taller.setCurso_nombre(objects[4].toString());
                taller.setParalelo_nombre(objects[5].toString());
                taller.setEstado(objects[6].toString());
                taller.setDestreza_id(Long.valueOf(objects[7].toString()));
                taller.setDestreza(objects[8].toString());
                listTaller.add(taller);
            }
            
            return listTaller;
        }
        catch(Exception e) { 
            System.out.println("Error en metodo listaTallerRegistrado en presentaTallerEAO: "+e.getMessage());
            return null;
        }
        
        
    }   
    
    /*Metodo Presenta Taller EAO a Estudiantes*/
    public List<TallerList> consultaTaller( Long usuarioCod, Long destrezaId, Long subtemaId )
    {
        try {
            
            System.out.println("presentaTallerEAO consultaTaller usuarioCod: "+usuarioCod + " destrezaId: "+destrezaId+ " subtemaId: "+subtemaId);
            
            sql   = " select pe.periodo_descripcion, u.actividad_descripcion, u.actividad_pregunta,  u.actividad_archivo, c.curso_descripcion, p.paralelo_nombre , u.actividad_sts ," + 
                            " u.cursoparalelo_id , u.actividad_id , s.subtema_desc , un.unidad_nombre, d.destreza_nombre, pt.taller_id , un.unidad_Id , " +
                            " u.actividad_tipopreg " +
                            "From tbl_actividad u " +
                            "Inner join tbl_destreza d on d.destreza_Id = u.destreza_Id "+
                            "Inner join tbl_unidad un on un.unidad_Id = u.unidad_id " +
                            "Inner join tbl_subtema s on s.unidad_Id = u.unidad_id and s.subtema_id = u.subtema_id " +
                            "and s.subtema_id     = :subtemaId " +
                            "Inner join tbl_cursoparalelo cp on cp.cursoparalelo_id = u.cursoparalelo_id " +
                            "Inner Join tbl_presentaciontaller pt on pt.destreza_Id = u.destreza_Id and pt.unidad_Id = u.unidad_id and pt.taller_sts = 'A' "+
                            "Inner join tbl_periodo pe on pe.periodo_Id = cp.periodo_Id " +
                            "Inner join tbl_curso c on c.curso_id = cp.curso_id " +
                            "Inner join tbl_paralelo p on p.paralelo_id = cp.paralelo_id " +
                            "where u.actividad_sts = :estado " +
                            "and not exists ( Select * from tbl_resultado r " +
                            " where r.usuario_cod  = :usuarioCod "   +
                            " and r.unidad_id      = pt.unidad_Id " +
                            " and r.destreza_Id    = pt.destreza_Id " +
                            " and ( ( r.resultado_intento =  ( Select  p.PARAM_NUM from tbl_param p where Trim(p.OBSERVACION) = 'NUMERO_INTENTOS') ) " +
                            "    or ( r.resultado_estado = 'APROBADO') ) " +
                            " and r.subtema_id = :subtemaId " +
                            " and r.resulTado_sts = :estado ) "+
                            " and u.destreza_Id = :destrezaId "+
                            " order by u.actividad_id ";
            
            Query query = administradorEntidad.createNativeQuery(sql);
            query.setParameter("subtemaId",  subtemaId);
            query.setParameter("estado", "A");
            query.setParameter("usuarioCod",  usuarioCod);
            query.setParameter("destrezaId",  destrezaId);
            
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
                actividadList.setTaller_id(Long.valueOf(objects[12].toString()));
                actividadList.setUnidad_id(Long.valueOf(objects[13].toString()));
                
                if(estado.equals("A"))
                {
                    actividadList.setEstado("ACTIVO");
                }
                else
                {
                    actividadList.setEstado("INACTIVO");
                }
                
                actividadList.setTipo_pregunta(objects[14].toString());
                actividadList.setRespuestas(obtieneRespuestasporActividad( actividad_id, objects[14].toString() ));
                
            
                listTaller.add(actividadList);
            }
            
           return listTaller; 
        }
        catch(Exception e) { 
            System.out.println("Error al consultaTaller en presentaTallerEAO: "+e.getMessage());
            return null;
        }
    } 
   
    public List<RespuestasList> obtieneRespuestasporActividad(Long actividad_id, String tipo_pregunta) 
    {
        try 
        {
            /*Consulta Respuestas de la actividad*/
           sql = " select r.respuesta_id, " +
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
            System.out.println("ERROR al obtener obtieneRespuestasporActividad en presentaTallerEAO:"+ex.getMessage());
            return null;
	}	

    }
  

}
