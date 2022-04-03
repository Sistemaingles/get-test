
package com.servicios.mb;



import com.entidades.TblActividad;
import com.entidades.TblCursoparalelo;
import com.entidades.TblDestreza;
import com.entidades.TblRespuesta;
import com.entidades.TblSubtema;
import com.entidades.TblUnidad;
import com.entidades.TblUsuarios;
import com.entidades.listas.ActividadList;
import com.entidades.listas.CursoAsignadoList;
import com.entidades.listas.TallerList;
import com.entidades.listas.UnidadList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import com.servicios.util.UtilJsf;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
//import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author mespinoza
 */
@ManagedBean(name = "actividadCursoMB")
@ViewScoped
public class actividadCursoMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;

    private CursoAsignadoList selectedCurso;
    private Long destreza_id;
    private Long unidad_id;
    private Long subtema_id;
    private String actividad;
    private String tipoActividadPreg;
    private String pregunta;
    private int respuesta_calif;
    private int respuesta_orden;
    private Long cursoparalelo_cod;
    private String archivo;
    private Date fechaActual;
    private String Usuario;
    private String NomApe;
    private StreamedContent file;
    
    private TblRespuesta objRespuesta;
    private ArrayList<TblRespuesta> listRespuestaAgregadas;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    private List<TblDestreza>    destrezaList;
    private List<UnidadList>     unidadList;
    private List<TblSubtema>     subtemaList;
    private List<ActividadList>  actividadList;
    private List<TallerList>     tallerList;  //ADD 24042021
    
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    { 
        //cursoparalelo_cod      = Long.valueOf("0");
        respuesta_calif        = 0;
        respuesta_orden        = 0; 
        destreza_id            = Long.valueOf("0");
        unidad_id              = Long.valueOf("0");
        subtema_id             = Long.valueOf("0");
        pregunta               = "";
        
        //Obtinene Lista de Destrezas Activas  .-
        destrezaList           = servicioAplicacion.obtieneDestreza();
        //unidadList             = servicioAplicacion.consultaUnidadCurso(usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "", "");
        unidadList            = servicioAplicacion.consultaUnidad(usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "A");
        
        //subtemaList            = servicioAplicacion.consultaSubtemaUnidad(Long.valueOf("0"));
        actividadList          = servicioAplicacion.consultaActividadCurso("", "" );
        objRespuesta           = new TblRespuesta();
        listRespuestaAgregadas = new ArrayList<>();
    }
    
    public void btnAgregarRespuesta(){
        
        FacesMessage mensaje = new FacesMessage();
        if( ! objRespuesta.getRespuestaDesc().equals("") && objRespuesta.getRespuestaOrden()!= 0  && objRespuesta.getRespuestaCalif() != -1 && objRespuesta.getRespuestaCorrecta() != null  ){
            System.out.println("btnAgregarRespuesta Respuesta: "+objRespuesta.getRespuestaDesc());
            listRespuestaAgregadas.add(objRespuesta);
            objRespuesta           = new TblRespuesta();
            objRespuesta.setRespuestaDesc("");
            objRespuesta.setRespuestaCalif(0);
            objRespuesta.setRespuestaOrden(0);
          
        }
        else{
            //ERROR
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("Ingrese informacion obligatoria para agregar Respuestas.");
  
            FacesContext.getCurrentInstance().addMessage("Mensaje", mensaje);
            
        }   
    }
            
    public void showActividad(ActionEvent ae) {
        System.out.print("actividadCursoMB Ejecuta showCursoParalelo");
      RequestContext.getCurrentInstance().openDialog("pag_asignacursoU", getDialogOptions(), null);
    }
    

    public Map<String, Object> getDialogOptions() {
      Map<String, Object> options = new HashMap<>();
      options.put("resizable", false);
      options.put("draggable", true);
      options.put("modal", true);
      options.put("contentHeight", 350);
      options.put("contentWidth", 1000);
      
      return options;
  }

    /*
    METODO PARA INGRESAR ACTIVIDAD CON CURSO ASIGNADO.-
    */
    public void ingresaActividadCurso()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaActividadCurso <actividadCursoMB>*****************");
        String Estado = "A";
        int valorcero =0;
        
        cursoparalelo_cod   = unidadList.get(1).getCursoparaleloid();
        System.out.println("ingresaActividadCurso CursoParalelo_cod: "+cursoparalelo_cod);
        
        if( (destreza_id         == null) || ( destreza_id == 0 )               ||
            (unidad_id           == null) || ( unidad_id   == 0 )               ||
            (subtema_id          == null) || ( subtema_id  == 0 )               ||    
            (actividad           == null) || (actividad.equals(""))             || 
            //(pregunta            == null) || (pregunta.equals(""))              ||
            (tipoActividadPreg   == null) || (tipoActividadPreg.equals(""))     ||
            (listRespuestaAgregadas.size() == 0 )                               || 
            (cursoparalelo_cod == null) || ( cursoparalelo_cod == 0 )  
          )
          {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor, llene información obligatoria (*) del formulario para continuar.", " " );
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("No ingreso informacion obligatoria del Formulario: ");
          }
          else
          {
                if (pregunta == null)
                {
                pregunta = "";
                }
                
                buscaActividadCursosAsignados() ;
                int cantidad_actividadCurso = 0;
                int cantidad = 0;
                Long actividad_id = Long.valueOf("0");
                
                cantidad_actividadCurso  = actividadList.size();
                
                System.out.println("cantidad de Unidad Cursos Asignados : "+cantidad);
                
                for (int i = 0; i < actividadList.size(); i++) 
                {
                  Long curso = actividadList.get(i).getCursoparaleloid();
                  actividad_id = actividadList.get(i).getActividad_id();
                  System.out.println("Curso asignado a la Actividad : "+curso);
                  if (curso == cursoparalelo_cod)
                  {
                      cantidad = cantidad + 1;
                       
                  }   
                }
                
                if( cantidad_actividadCurso != 0 && cantidad != 0)
                {
                    //Si existe Unidad con el curso Seleccionado registrado.-
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unidad ya se encuentra registrada y con el curso-paralelo asignado.", "Unidad ya se encuentra registrada y con el curso-paralelo asignado." ) );
                } 
                else 
                {
                    if(cantidad_actividadCurso == 0){
                        //Si no existe Actividad Registrado, lo registra.-
                        System.out.println("Invoca Metodo Guardar Actividad: ");
                        
                        
                        actividad_id = guardaActividad( actividad, pregunta, tipoActividadPreg, cursoparalelo_cod, archivo, Estado);
                        
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se agregó actividad con éxito.", "Se agregó actividad con éxito." ) );
   
                    }
                    else {
                        //Asigna Curso a la Actividad Registrada.-
                        System.out.println("ingresaActividadCurso actividad_id: "+actividad_id);
                        
                        actividad_id = guardaActividad( actividad, pregunta, tipoActividadPreg, cursoparalelo_cod, archivo, Estado);
                        
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Curso asignado a la Actividad con éxito.", "Curso asignado a la Actividad con éxito." ) );
                    }
                    
                    if (actividad_id != 0){
                        guardaRespuestas(actividad_id);
                    }
                    
                    //Consulta Actividad Registrada
                    tallerList  = servicioAplicacion.consultaActividad(destreza_id, unidad_id, subtema_id);
                    subtemaList = servicioAplicacion.consultaSubtemaUnidad(Long.valueOf("0"), unidad_id, "A"); 
                    
                    //buscaActividadCursosAsignados() ;
                    reestableceActividad();
                }
          }
    }
    
    public void buscaActividadRegistrado()
    {
        //Consulta Actividad Registrada
        System.out.println("buscaActividadRegistrado destreza_id: "+destreza_id+ " unidad_id: "+unidad_id + " subtema_id: "+subtema_id);
        tallerList = servicioAplicacion.consultaActividad(destreza_id, unidad_id, subtema_id );
    } 
    
    // METODO PARA SUBIR ARCHIVO AL SERVIDOR.-
    public void subirArchivo(FileUploadEvent event){
        FacesMessage mensaje = new FacesMessage();
        try {
            String path =servicioAplicacion.obtenerRuta("RUTA_ACTIVIDADES");

            archivo = UtilJsf.guardaBlobEnFicheroTemporal(path, event.getFile().getContents(), event.getFile().getFileName());
            mensaje.setSeverity(FacesMessage.SEVERITY_INFO);
            mensaje.setSummary("Archivo subido exitosamente en la Ruta: "+archivo);
            
        } catch (Exception e) {
            mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
            mensaje.setSummary("Problemas al subir el archivo.");
        }
        FacesContext.getCurrentInstance().addMessage("Mensaje", mensaje);
        
    }
    // METODO Busca Unidad-Cursos Paralelos Asignados .-
    
    public void buscaActividadCursosAsignados() 
    {
        //Obtiene Detalle de Actividaddes asignados al docente.-
        /*System.out.println("buscaActividadCursosAsignados actividad "+actividad+
                                       " pregunta: "+pregunta
                                        );*/
        actividadList = servicioAplicacion.consultaActividadCurso( actividad, pregunta );
    }
    
    /*
        METODO PARA GUARDAR ACTIVIDAD Y CURSO SI NO SE ENCUENTRAN REGISTRADA.-
    */
    public Long guardaActividad( String actividad1, String pregunta1, String tipoPregunta, Long cursoparalelo_cod1, String archivo1, String estado1)
    {
        TblActividad actividad  =   new TblActividad();
        fechaActual             =   new Date();
        IdUserSession           =   getUserSessionId();
        
        /*System.out.println("guardaActividad actividad: "+actividad1+
                            " pregunta: "+pregunta1+
                            " tipopregunta: "+tipoPregunta+
                            " archivo: "+archivo1+
                            " estado: "+estado1+
                            " fechaActual: "+fechaActual+
                            " IdUserSession: "+IdUserSession+
                            " cursoparalelo_cod: "+cursoparalelo_cod1
                );*/
        
  
            TblCursoparalelo cursoParalelo = new TblCursoparalelo();
            cursoParalelo.setCursoparaleloId(cursoparalelo_cod1);
            
            TblDestreza destreza = new TblDestreza(); 
            destreza.setDestrezaId(destreza_id);
            actividad.setDestrezaId(destreza);
            
            TblUnidad unidad = new TblUnidad();
            unidad.setUnidadId(unidad_id);
            actividad.setUnidadId(unidad);
            
            TblSubtema subtema = new TblSubtema();
            subtema.setSubtemaId(subtema_id);
            actividad.setSubtemaId(subtema);
            
        
            actividad.setActividadDescripcion(actividad1);
            actividad.setActividadPregunta(pregunta1);
            actividad.setActividadTipopreg(tipoPregunta);
            actividad.setCursoparaleloId(cursoParalelo);
            actividad.setActividadArchivo(archivo1);
            actividad.setActividadSts(estado1);
            actividad.setActividadInsusr(IdUserSession);
            actividad.setActividadInstim(fechaActual);
   
        return servicioAplicacion.ingresaActividad(actividad);

    }
    
    /*
        METODO PARA GUARDAR PREGUNTAS DE LA ACTIVIDAD CREADA.-
    */
    public Long guardaRespuestas(Long actividad_cod){
        Long respuesta_cod = Long.valueOf("0");
        
        for (int i = 0; i < listRespuestaAgregadas.size(); i++) {
            TblRespuesta respuesta = listRespuestaAgregadas.get(i);
            
            TblActividad activ = new TblActividad(); 
            activ.setActividadId(actividad_cod);
            
            respuesta.setActividadId(activ);
            respuesta.setRespuestaInsusr(IdUserSession);
            respuesta.setRespuestaInstim(fechaActual);
            respuesta.setRespuestaUpdusr(" ");
            respuesta.setRespuestaDltusr(" ");
            respuesta.setRespuestaSts("A");
            respuesta_cod = servicioAplicacion.ingresaRespuesta(respuesta);
        }
        
        return respuesta_cod;
    }
         
    public void reestableceActividad()
    {
        /*destreza_id          = Long.valueOf("0");
        unidad_id            = Long.valueOf("0");
        subtema_id           = Long.valueOf("0");
        actividad            = "";*/
        pregunta             = "";
        archivo              = "";
        cursoparalelo_cod    = null;  
        tipoActividadPreg    = "";
        objRespuesta.setRespuestaDesc("");
        objRespuesta.setRespuestaOrden(0);
        listRespuestaAgregadas.clear();
        
    }
    
   /*FUNCION DETECTA VALOR DE COMBOBOX DE UNIDAD*/
    public void onUnidadChange() 
    {
        try 
        {
            if(unidad_id != 0)
            {
                System.out.println("onUnidadChange unidad_id: "+unidad_id);
                subtemaList = servicioAplicacion.consultaSubtemaUnidad(Long.valueOf("0"),unidad_id, "A");        
            }
        }
        catch (Exception ex) 
        {
            System.out.println("Error en Metodo onUnidadChange actividadCursoMB: "+ex.getMessage());
        }      
    }
    
    
    public void actualizarActividad(RowEditEvent event){
        ActividadList activ = new ActividadList(); 
        activ = (ActividadList) event.getObject();
        
        System.out.println("Actividad a Actualizar: "+activ.getActividad());
        
        //servicioAplicacion.actualizaActividad(actividad);
    }
    
    public void cancelarActividad(){
        FacesContext context = FacesContext.getCurrentInstance();          
        context.addMessage(null, new FacesMessage("Ud. Canceló la actualización", "Cancelada Transacción de Actualizar"));
    }
    
    public void eliminar(Long actividad_id){
        System.out.println("Codigo de Actividad a Eliminar: "+actividad_id);
        boolean eliminado = false;
        eliminado = servicioAplicacion.eliminaRespuesta(actividad_id);
        
        if(eliminado){
            servicioAplicacion.eliminaActividad(actividad_id);
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Actividad Eliminada con éxito.", "Actividad Eliminada con éxito."));
            
            buscaActividadCursosAsignados();
        }
            
    }
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }
    

    public Long getCursoparalelo_cod() {
        return cursoparalelo_cod;
    }
    
    
    public void setCursoparalelo_cod(Long cursoparalelo_cod) {
        this.cursoparalelo_cod = cursoparalelo_cod;
    }

    public CursoAsignadoList getSelectedCurso() {
        return selectedCurso;
    }

    public void setSelectedCurso(CursoAsignadoList selectedCurso) {
        this.selectedCurso = selectedCurso;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

  
    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getNomApe() {
        return NomApe;
    }

    public void setNomApe(String NomApe) {
        this.NomApe = NomApe;
    }

    public List<ActividadList> getActividadList() {
        return actividadList;
    }

    public void setActividadList(List<ActividadList> actividadList) {
        this.actividadList = actividadList;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String getTipoActividadPreg() {
        return tipoActividadPreg;
    }

    public void setTipoActividadPreg(String tipoActividadPreg) {
        this.tipoActividadPreg = tipoActividadPreg;
    }

    public TblRespuesta getObjRespuesta() {
        return objRespuesta;
    }

    public void setObjRespuesta(TblRespuesta objRespuesta) {
        this.objRespuesta = objRespuesta;
    }

    public ArrayList<TblRespuesta> getListRespuestaAgregadas() {
        return listRespuestaAgregadas;
    }

    public void setListRespuestaAgregadas(ArrayList<TblRespuesta> listRespuestaAgregadas) {
        this.listRespuestaAgregadas = listRespuestaAgregadas;
    }

    public int getRespuesta_calif() {
        return respuesta_calif;
    }

    public void setRespuesta_calif(int respuesta_calif) {
        this.respuesta_calif = respuesta_calif;
    }

    public int getRespuesta_orden() {
        return respuesta_orden;
    }

    public void setRespuesta_orden(int respuesta_orden) {
        this.respuesta_orden = respuesta_orden;
    }

    public Long getDestreza_id() {
        return destreza_id;
    }

    public void setDestreza_id(Long destreza_id) {
        this.destreza_id = destreza_id;
    }

    public Long getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(Long unidad_id) {
        this.unidad_id = unidad_id;
    }

    public Long getSubtema_id() {
        return subtema_id;
    }

    public void setSubtema_id(Long subtema_id) {
        this.subtema_id = subtema_id;
    }

    public List<TblDestreza> getDestrezaList() {
        return destrezaList;
    }

    public void setDestrezaList(List<TblDestreza> destrezaList) {
        this.destrezaList = destrezaList;
    }

    public List<UnidadList> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<UnidadList> unidadList) {
        this.unidadList = unidadList;
    }

    public List<TblSubtema> getSubtemaList() {
        return subtemaList;
    }

    public void setSubtemaList(List<TblSubtema> subtemaList) {
        this.subtemaList = subtemaList;
    }

    public List<TallerList> getTallerList() {
        return tallerList;
    }

    public void setTallerList(List<TallerList> tallerList) {
        this.tallerList = tallerList;
    }
    
    
    
}
