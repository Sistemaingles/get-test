
package com.servicios.mb;

import com.entidades.TblPeriodo;
import com.entidades.TblUsuarios;
import com.entidades.listas.CursoAsignadoList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;


/**
 *
 * @author mespinoza
 */
@ManagedBean(name = "periodoMB")
@RequestScoped

/*
    CLASE JAVA MANAGED BEANS DE PERIODO LECTIVO
*/

public class PeriodoMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    
    private String anio;
    private String descripcion;
    private List<TblPeriodo> periodoList;
    private Date fechaActual;
    private List<CursoAsignadoList> cursosAsignados;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    private int cantidadPeriodo;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    
    @PostConstruct
    public void init()
    {
        System.out.println("Inicio Periodo MB");
        //Obtinene Lista de Periodo.-
        periodoList          =   servicioAplicacion.obtenerPeriodo("");
        
    }

    
    /*
    METODO PARA INGRESAR UN PERIODO.-
    */
    public void ingresaPeriodo()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaPeriodo <periodoMB>*****************");
        String Estado   = "A";
        
        if( (anio    == null) || (anio.equals("")) ||
            (descripcion    == null) || (descripcion.equals(""))
          )
          {
            String mensaje = "";
            if( (anio    == null) || (anio.equals(""))){
                mensaje = "Es obligatorio llenar campo PERIODO. ";
            }
              
            if( (descripcion    == null) || (descripcion.equals("")) ){ 
              mensaje += "Es obligatorio llenar campo DESCRIPCIÓN.";
            }
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, " " );
            FacesContext.getCurrentInstance().addMessage(null, message);  

          }
          else
          {
             
             Date date = new Date();

             SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
             String currentYear = getYearFormat.format(date);
             System.out.println("Año actual: "+currentYear);
             int anio_actual = Integer.parseInt(currentYear);
             
             if ( Integer.parseInt(anio)  < anio_actual ){
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no valido, debe ser mayor o igual al año actual.", " " );  
                FacesContext.getCurrentInstance().addMessage(null, message);
             }
             else{
                 
                  
            fechaActual     =   new Date();
            IdUserSession   =   getUserSessionId();
            int aniosig     =   Integer.parseInt(anio) +1;
            String anio_lectivo   =   anio + '-' + aniosig;
            
            
            TblPeriodo periodo = new TblPeriodo();
            periodo.setPeriodoAnio(anio_lectivo);
            periodo.setPeriodoDescripcion(descripcion);
            periodo.setPeriodoSts(Estado);
            periodo.setPeriodoInstim(new Date());
            periodo.setPeriodoInsusr(IdUserSession);
            
            cantidadPeriodo = servicioAplicacion.cantidadPeriodo("");
            System.out.println("PeriodoMB Cantidad de Periodos ingresados: "+cantidadPeriodo);
            
            if ( cantidadPeriodo != 0){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe un periodo ingresado con estado Activo.", " " );  
                    FacesContext.getCurrentInstance().addMessage(null, message);
            }
            else{
                          
                servicioAplicacion.ingresarPeriodo(periodo); 
                
                //Obtinene Lista de Periodo.-
                periodoList          =   servicioAplicacion.obtenerPeriodo("");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Periodo ingresado con éxito", " " );  
                FacesContext.getCurrentInstance().addMessage(null, message);
                resstablePeriodo();
            } 
            
            } 
          }
    }
    
    public void resstablePeriodo()
    {
        anio         = "";
        descripcion  = "";
                    
    }
    
    public void actualizar(RowEditEvent event) {
        try {
            
            TblPeriodo periodo = new TblPeriodo(); 
            periodo = (TblPeriodo) event.getObject();
            System.out.println("Anio a Actualizar: "+periodo.getPeriodoAnio() + " Estado: "+periodo.getPeriodoSts());
            if (periodo.getPeriodoSts().equals("A")){
                cantidadPeriodo = servicioAplicacion.cantidadPeriodo("");
            }
            else
            {
                cantidadPeriodo = servicioAplicacion.cantidadPeriodo(periodo.getPeriodoAnio());
            }
            
            if ( cantidadPeriodo != 0  && periodo.getPeriodoSts().equals("A") ){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe un periodo ingresado con estado Activo.", " " );  
                    FacesContext.getCurrentInstance().addMessage(null, message);
            }
            else{
                 System.out.println("Periodo a Actualizar: "+periodo.getPeriodoDescripcion());
                servicioAplicacion.actualizaPeriodo(periodo);

                FacesContext context = FacesContext.getCurrentInstance();          
                context.addMessage(null, new FacesMessage("Actualización de periodo realizada con éxito", "Actualización con éxito"));  
            }
        
        //Obtiene Lista de Periodo.-
        periodoList  =   servicioAplicacion.obtenerPeriodo(""); 
 
           
        } catch (Exception e) {
        }
    }
    
    public void cancelar() {
        FacesContext context = FacesContext.getCurrentInstance();          
        context.addMessage(null, new FacesMessage("Ud. Canceló la actualización", "Cancelada Transacción de Actualizar")); 
    }
    
    public void eliminar(TblPeriodo periodo)
    {   
        //Obtiene Lista de CursosAsignados al Periodo.-
        cursosAsignados      = servicioAplicacion.consultaPeriodoCurso(periodo.getPeriodoId(), Long.valueOf("0"), Long.valueOf("0"), "A");
        
        System.out.println("Cantidad de cursos Asignados del Periodo a Eliminar: "+cursosAsignados.size());
        
        if ( cursosAsignados.isEmpty() ){ 
            System.out.println("Codigo de Periodo a Eliminar: "+periodo.getPeriodoId());
            
            servicioAplicacion.eliminaPeriodo(periodo);
            servicioAplicacion.reestableceSecuencia("tbl_periodo");
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Periodo eliminado con éxito", " " );  
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            //Obtinene Lista de Periodo.-
            periodoList          =   servicioAplicacion.obtenerPeriodo("");
        }
        else{ 
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo no se puede eliminar porque contiene información sensible.", " " );  
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
            
    }
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<TblPeriodo> getPeriodoList() {
        return periodoList;
    }

    public void setPeriodoList(List<TblPeriodo> periodoList) {
        this.periodoList = periodoList;
    }

    public String getIdUserSession() {
        return IdUserSession;
    }

    public void setIdUserSession(String IdUserSession) {
        this.IdUserSession = IdUserSession;
    }

    public List<CursoAsignadoList> getCursosAsignados() {
        return cursosAsignados;
    }

    public void setCursosAsignados(List<CursoAsignadoList> cursosAsignados) {
        this.cursosAsignados = cursosAsignados;
    }
    
    
    
    
}
