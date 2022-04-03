
package com.servicios.mb;

import com.entidades.TblUnidad;
import com.entidades.TblVocabulario;
import com.entidades.TblUsuarios;
import com.entidades.listas.UnidadList;
import com.entidades.listas.VocabularioList;
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
@ManagedBean(name = "vocabularioMB")
@ViewScoped

public class VocabularioMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private Long unidad_id;
    private String descripcion;
    private Date fechaActual;
    private List<VocabularioList>  vocabularioList;
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
        vocabularioList = servicioAplicacion.consultaVocabulario( Long.valueOf("0") );
    }
    
    /*
    METODO PARA INGRESAR VOCABULARIO.-
    */
    public void ingresaVocabulario()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaVocabulario <vocabularioMB>*****************");
        String Estado = "A";
       
        if( (unidad_id           == null) || ( unidad_id   == 0 )    ||   
            (descripcion         == null) || (descripcion.equals("") )          
          )
          {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor, llene información obligatoria (*) del formulario para continuar.", " " );
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("No ingreso informacion obligatoria del Formulario: ");
          }
          else
          {
              fechaActual             =   new Date();
              IdUserSession           =   getUserSessionId();
              System.out.println("ingresaVocabulario Descripcion: "+descripcion);
              TblVocabulario vocabulario = new TblVocabulario();
              TblUnidad unidad = new TblUnidad();
              unidad.setUnidadId(unidad_id);
              vocabulario.setUnidadId(unidad);
              vocabulario.setVocabularioDescripcion(descripcion);
              vocabulario.setVocabularioInsusr(IdUserSession);
              vocabulario.setVocabularioInstim(fechaActual);
              vocabulario.setVocabularioSts(Estado);
              
              servicioAplicacion.ingresaVocabulario(vocabulario);
              
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vocabulario ha sido registrado con éxito.", " " );
               FacesContext.getCurrentInstance().addMessage(null, message);
              
              reestableceVocabulario();
              buscaVocabulario();
          }
    }

    public void buscaVocabulario() 
    {
        //Obtiene Detalle de Vocabularios registrados por Unidad.-
        System.out.println("buscaVocabulario unidad "+unidad_id);
        vocabularioList = servicioAplicacion.consultaVocabulario( unidad_id );
    }
    
    public void reestableceVocabulario()
    {
        unidad_id       =   Long.valueOf("0");
        descripcion     =   "";
        
    }
    
    /*
        METODO PARA ACTUALIZAR VOCABULARIO.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            
            VocabularioList vocabularioL = new VocabularioList(); 
            vocabularioL = (VocabularioList) event.getObject();
            System.out.println("Vocabulario Id a Actualizar: "+vocabularioL.getVocabularioId());
            
            TblUnidad unidad = new TblUnidad();
            unidad.setUnidadId(vocabularioL.getUnidadId());
            
            TblVocabulario vocabulario = new TblVocabulario(); 
            vocabulario.setVocabularioId(vocabularioL.getVocabularioId());
            vocabulario.setVocabularioDescripcion(vocabularioL.getVocabularioDescripcion());
            vocabulario.setVocabularioSts(vocabularioL.getVocabularioSts());
            vocabulario.setVocabularioInstim(vocabularioL.getVocabularioInstim());
            vocabulario.setVocabularioInsusr(vocabularioL.getVocabularioInsUsr());
            vocabulario.setUnidadId(unidad);
            
            servicioAplicacion.actualizaVocabulario(vocabulario);
            FacesContext context = FacesContext.getCurrentInstance();
            
            context.addMessage(null, new FacesMessage("Actualización realizada con éxito", ""));  
    
            
            //Obtiene Lista de Vocabulario.-
            vocabularioList = servicioAplicacion.consultaVocabulario( unidad_id );
            
        } catch (Exception e) {
            System.out.println("Ocurrio un error al actualizar Vocabulario: "+ e.getMessage());
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
        METODO PARA ELIMINAR PARALELO.-
    */
    public void eliminar(VocabularioList vocabulario)
    {
        System.out.println("Codigo de vocabulario a Eliminar: "+vocabulario.getVocabularioId());
        boolean eliminado = false;
        eliminado = servicioAplicacion.eliminaVocabulario(vocabulario.getVocabularioId());
        if(eliminado){
            
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Vocabulario eliminado con éxito.", "Vocabulario eliminado con éxito."));
            
            buscaVocabulario();
        }
    }       
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }

    public List<VocabularioList> getVocabularioList() {
        return vocabularioList;
    }

    public void setVocabularioList(List<VocabularioList> vocabularioList) {
        this.vocabularioList = vocabularioList;
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
    
    
}
