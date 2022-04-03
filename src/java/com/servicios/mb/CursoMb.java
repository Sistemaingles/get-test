
package com.servicios.mb;

import com.entidades.TblCurso;
import com.entidades.TblUsuarios;
import com.entidades.listas.CursoAsignadoList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
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
 * @author jespinoza
 */
@ManagedBean(name = "cursoMB")
@RequestScoped

/*
    CLASE JAVA MANAGED BEANS DE PERIODO LECTIVO
*/

public class CursoMb implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    
    private String anio;
    private String descripcion;
    private List<TblCurso> cursoList;
    private Date fechaActual;
    private List<CursoAsignadoList> cursosAsignados;
    private List<TblCurso> cursosList;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    
    @PostConstruct
    public void init()
    {
        System.out.println("Inicio Curso MB");
        //Obtinene Lista de Cursos.-
        cursoList          =   servicioAplicacion.obtenerCurso("");
        
    }

    
    /*
    METODO PARA INGRESAR UN PERIODO.-
    */
    public void ingresaCurso()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaPeriodo <periodoMB>*****************");
        String Estado   = "A";
        
        if ( anio    == null || anio.equals("") )
          {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Es obligatorio llenar campo CURSO.", " " );
            FacesContext.getCurrentInstance().addMessage(null, message);  
 
          }
          else
          {
            
            buscaCursos() ;
            int cantidad = 0;
            cantidad = cursosList.size();
            System.out.println("cantidad de Cursps Registrados: "+cantidad);
            if (cantidad == 0){
                
                fechaActual     =   new Date();
                IdUserSession   =   getUserSessionId();

                TblCurso curso = new TblCurso();
                curso.setCursoNumero(anio);
                curso.setCursoDescripcion(descripcion);
                curso.setCursoSts(Estado);
                curso.setCursoInstim(new Date());
                curso.setCursoInsusr(IdUserSession);

                servicioAplicacion.ingresarCurso(curso);
                  
                //Obtiene Lista de Curso.-
                cursoList          =   servicioAplicacion.obtenerCurso("");
            
                resstableCurso();
            }
            else{
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Curso ya se encuentra registrado", "" ) );
            }
           
           
          }
    }
    
    // METODO Busca Cursos Registrados .-
    public void buscaCursos() 
    {
        //Obtiene Detalle de Paralelos Registrados.-
        System.out.println("buscaCursos paralelo_num: "+anio);
        
        cursosList          =   servicioAplicacion.obtieneCursos(anio);
        
    }
    public void resstableCurso()
    {
        anio         = "";
        descripcion  = "";
                    
    }
    
    public void actualizar(RowEditEvent event) {
        try {
            TblCurso curso = new TblCurso(); 
            curso = (TblCurso) event.getObject();
            
            boolean existeCurso = false;
            for (int i = 0; i < cursoList.size(); i++) {
                TblCurso get = cursoList.get(i);
                if( ! cursoList.get(i).getCursoId().equals(curso.getCursoId()) &&
                    cursoList.get(i).getCursoNumero().equals(curso.getCursoNumero())) {
                    existeCurso = true;
                    break;
                }
            }
            
            if (existeCurso) {       
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso ya se encuentra registrado.", " " );  
               FacesContext.getCurrentInstance().addMessage(null, message);
            }
            else{
                servicioAplicacion.actualizaCurso(curso);
            
                FacesContext context = FacesContext.getCurrentInstance(); 
                context.addMessage(null, new FacesMessage("Actualización realizada con éxito", "Actualización con éxito"));  

            }
            
            //Obtiene Lista de Curso.-
            cursoList  =   servicioAplicacion.obtenerCurso("");
               
        } catch (Exception e) {
        }
    }
    
    public void cancelar() {
        FacesContext context = FacesContext.getCurrentInstance();          
        context.addMessage(null, new FacesMessage("Ud. Canceló la actualización", "Cancelada Transacción de Actualizar")); 
    }
    
    public void eliminar(TblCurso curso)
    {
       //Obtiene Lista de CursosAsignados al Curso.-
       cursosAsignados      = servicioAplicacion.consultaPeriodoCurso(Long.valueOf("0"), curso.getCursoId(), Long.valueOf("0"), "A");
       
       System.out.println("Cantidad de cursos Asignados del Curso a Eliminar: "+cursosAsignados.size());
       
       if ( cursosAsignados.isEmpty() ){ 
           
            System.out.println("Codigo de Curso a Eliminar: "+curso.getCursoId());
            servicioAplicacion.eliminaCurso(curso);
            servicioAplicacion.reestableceSecuencia("tbl_curso");
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Curso eliminado con éxito", "Curso eliminado con éxito"));
      
            //Obtinene Lista de Curso.-
            cursoList          =   servicioAplicacion.obtenerCurso("");
       }
       else{ 
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Curso no se puede eliminar porque contiene información sensible.", " " );  
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

    public List<TblCurso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<TblCurso> cursoList) {
        this.cursoList = cursoList;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    

    public String getIdUserSession() {
        return IdUserSession;
    }

    public void setIdUserSession(String IdUserSession) {
        this.IdUserSession = IdUserSession;
    }
    
    
    
    
}
