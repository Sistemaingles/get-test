
package com.servicios.mb;

import com.entidades.TblSubtema;
import com.entidades.TblUnidad;
import com.entidades.TblUsuarios;
import com.entidades.listas.CursoAsignadoList;
import com.entidades.listas.UnidadList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author jespinoza
 */
@ManagedBean(name = "regSubtemaMB")
@ViewScoped

public class regSubtemaMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private Long unidad_id;
    private String descripcion;
    private Date fechaActual;
    private List<CursoAsignadoList> detperiodoList;
    private List<TblSubtema>  subtemaList;
    private Long cursoParaleloCod;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    private List<UnidadList>     unidadList;
    @PostConstruct
    public void init()
    {
        unidadList      = servicioAplicacion.consultaUnidad(usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "A");
       
        //Obtiene Lista de Cursos Asignados al Docente.-
        detperiodoList     = servicioAplicacion.consultaPeriodoCursoDocente(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "A");
        cursoParaleloCod   = Long.valueOf("0");
        
        for (int i = 0; i < detperiodoList.size(); i++) {
            if(detperiodoList.get(i).getUsuario_cod().equals(usuarioSession.getUsuarioSession().getId()))
            {
               cursoParaleloCod = detperiodoList.get(i).getCursoparalelo_cod();
            }  
        }
        subtemaList     = servicioAplicacion.consultaSubtemaUnidad(  cursoParaleloCod, Long.valueOf("0") , "A" );
    }
    
    /*
    METODO PARA INGRESAR SUBTEMA.-
    */
    public void ingresaSubtema()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaSubtema <regSubtemaMB>*****************");
        String Estado = "A";
       
        if( (unidad_id           == null) || ( unidad_id   == 0 )    ||   
            (descripcion         == null) || (descripcion.equals("") )          
          )
          {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor, seleccione una Unidad o ingrese Subtema.", " " );
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("No ingreso informacion obligatoria del Formulario: ");
          }
          else
          {
              fechaActual             =   new Date();
              IdUserSession           =   getUserSessionId();
              System.out.println("ingresaSubtema Descripcion: "+descripcion);
              
              TblUnidad unidad = new TblUnidad();
              unidad.setUnidadId(unidad_id);
              
              TblSubtema subtema = new TblSubtema(); 
              subtema.setUnidadId(unidad);
              subtema.setSubtemaDesc(descripcion);
              subtema.setSubtemaClase("small-box bg-yellow");
              subtema.setSubtemaIcono("ion ion-document-text");
              subtema.setSubtemaSts("A");
              servicioAplicacion.ingresaSubtema(subtema);
              
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Subtema ha sido registrado con éxito.", " " );
               FacesContext.getCurrentInstance().addMessage(null, message);
              
              reestableceSubtema();
              buscaSubtema();
          }
    }

    public void buscaSubtema() 
    {
        //Obtiene Detalle de Subtemas registrados por Unidad.-
        if ( unidad_id == null ){
            unidad_id = Long.valueOf("0");
        }
        System.out.println("buscaSubtema unidad "+unidad_id);
        subtemaList     = servicioAplicacion.consultaSubtemaUnidad(cursoParaleloCod,unidad_id , "A");
    }
    

    public void reestableceSubtema()
    {
        unidad_id       =   Long.valueOf("0");
        descripcion     =   "";
        
    }
    
    /*
        METODO PARA ACTUALIZAR SUBTEMA.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            
            TblSubtema subtema = new TblSubtema(); 
            subtema = (TblSubtema) event.getObject();
            System.out.println("Subtema Id a Actualizar: "+subtema.getSubtemaId());
            
            servicioAplicacion.actualizaSubtema(subtema);
            FacesContext context = FacesContext.getCurrentInstance();
            
            context.addMessage(null, new FacesMessage("Actualización realizada con éxito", ""));  
    
            //Obtiene Lista de Vocabulario.-
            buscaSubtema() ;
            
        } catch (Exception e) {
            System.out.println("Ocurrio un error al actualizar Subtema: "+ e.getMessage());
        }
    }
    /*
        METODO PARA CANCELAR ACTUALIZACION.-
    */
    public void cancelar() {
        FacesContext context = FacesContext.getCurrentInstance();          
        context.addMessage(null, new FacesMessage("Ud. Canceló la actualización", "Cancelada Transacción de Actualizar")); 
    }
    
    /*
        METODO PARA ELIMINAR SUBTEMA.-
    */
    public void eliminar(TblSubtema subtema)
    {
        System.out.println("Codigo de Subtema a Eliminar: "+subtema.getSubtemaId());
        boolean eliminado = false;
        eliminado = servicioAplicacion.eliminaVocabulario(subtema.getSubtemaId());
        if(eliminado){
            
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Subtema eliminado con éxito.", ""));
            
            buscaSubtema() ;
        }
    } 
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }

    public List<TblSubtema> getSubtemaList() {
        return subtemaList;
    }

    public void setSubtemaList(List<TblSubtema> subtemaList) {
        this.subtemaList = subtemaList;
    }

    public Long getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(Long unidad_id) {
        this.unidad_id = unidad_id;
    }

    public IUsuarioSession<TblUsuarios> getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(IUsuarioSession<TblUsuarios> usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public String getIdUserSession() {
        return IdUserSession;
    }

    public void setIdUserSession(String IdUserSession) {
        this.IdUserSession = IdUserSession;
    }

    public List<UnidadList> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<UnidadList> unidadList) {
        this.unidadList = unidadList;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<CursoAsignadoList> getDetperiodoList() {
        return detperiodoList;
    }

    public void setDetperiodoList(List<CursoAsignadoList> detperiodoList) {
        this.detperiodoList = detperiodoList;
    }
    
    
}
