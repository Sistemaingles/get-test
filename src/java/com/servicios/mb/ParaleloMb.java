
package com.servicios.mb;

import com.entidades.TblParalelo;
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
 * @author mespinoza
 */
@ManagedBean(name = "paraleloMB")
@RequestScoped

/*
    CLASE JAVA MANAGED BEANS DE CURSOS
*/

public class ParaleloMb implements Serializable{
    
    private static final long   serialVersionUID = 1L;

    private List<TblParalelo> paraleloList;
    private String paralelo_num;
    private String paralelo_descripcion;
    private Date fechaActual;
    private List<CursoAsignadoList> cursosAsignados;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    
    @PostConstruct
    public void init()
    {
        System.out.println("Inicio Paralelo MB");

        //Obtiene Lista de Paralelos.-
        paraleloList          =   servicioAplicacion.obtenerParalelo("");
        
    }


    /*
    METODO PARA INGRESAR UN PARALELO.-
    */
    public void ingresaParalelo()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaParalelo <paraleloMB>*****************");
        String Estado = "A";
        
        if( (paralelo_descripcion   == null) || (paralelo_descripcion.equals(""))   || 
            (paralelo_num     == null) || (paralelo_num.equals(""))  
          )
          {
            String mensaje = "";
            if( (paralelo_num    == null) || (paralelo_num.equals(""))){
               mensaje = "Es obligatorio llenar campo PARALELO.";
 
            }
              
            if( (paralelo_descripcion    == null) || (paralelo_descripcion.equals("")) ){
               mensaje += "Es obligatorio llenar campo DESCRIPCIÓN.";     
            }
              
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, " " );
            FacesContext.getCurrentInstance().addMessage(null, message);     
              
          }
          else
          {
                buscaParalelos() ;
                int cantidad = 0;
                cantidad = paraleloList.size();
                
                System.out.println("cantidad de Paralelos Registrados: "+cantidad);
                if (cantidad == 0)
                {
                    System.out.println("Invoca Metodo Guardar Paralelos: ");
                    System.out.println("paralelo_cod: "+paralelo_num
                                        );
                    guardaParalelo( paralelo_num, paralelo_descripcion, Estado);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Paralelo registrado con éxito", "Paralelo registrado con éxito" ) );
                    paraleloList          =   servicioAplicacion.obtenerParalelo("");
                    resstableParalelo();
                }
                else
                {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Paralelo ya se encuentra registrado", "" ) );
                }  
          }
    }
    
    // METODO Busca Paralelos Registrados .-
    public void buscaParalelos() 
    {
        //Obtiene Detalle de Paralelos Registrados.-
        System.out.println("buscaParalelos paralelo_num: "+paralelo_num.toString());
        
        paraleloList          =   servicioAplicacion.obtieneParalelos(paralelo_num);
        
    }
    
    public void resstableParalelo()
    {
        paralelo_num         = "";
        paralelo_descripcion = "";
                    
    }
    
    /*
        METODO PARA GUARDAR PARALELO.-
    */
    public void guardaParalelo( String paralelo_cod1 , String descripcion , String estado)
    {
        TblParalelo paralelo  = new TblParalelo();
        fechaActual     =   new Date();
        IdUserSession   =   getUserSessionId();

        paralelo.setParaleloNum(paralelo_cod1);
        paralelo.setParaleloNombre(descripcion);
        paralelo.setParaleloInsusr(IdUserSession);
        paralelo.setParaleloInstim(fechaActual);
        paralelo.setParaleltoSts(estado);
        
        servicioAplicacion.ingresaParalelo(paralelo);
    }
    
    /*
        METODO PARA ACTUALIZAR PARALELO.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            TblParalelo paralelo = new TblParalelo(); 
            paralelo = (TblParalelo) event.getObject();

            System.out.println("Paralelo Id a Actualizar: "+paralelo.getParaleloId());
            
            boolean existeParalelo = false;
            for (int i = 0; i < paraleloList.size(); i++) {
                TblParalelo get = paraleloList.get(i);
                if( ! paraleloList.get(i).getParaleloId().equals(paralelo.getParaleloId()) &&
                    paraleloList.get(i).getParaleloNum().equals(paralelo.getParaleloNum())) {
                    existeParalelo = true;
                    break;
                }
            }
            
            if (existeParalelo) {       
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Paralelo ya se encuentra registrado.", " " );  
               FacesContext.getCurrentInstance().addMessage(null, message);
            }
            else{
                servicioAplicacion.actualizaParalelo(paralelo);
                FacesContext context = FacesContext.getCurrentInstance();          
                context.addMessage(null, new FacesMessage("Actualización realizada con éxito", ""));  
            }
            
            //Obtinene Lista de Paralelo.-
            paraleloList          =   servicioAplicacion.obtenerParalelo("");  
        } catch (Exception e) {
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
    public void eliminar(TblParalelo paralelo)
    {
        //Obtiene Lista de CursosAsignados al Paralelo.-
       cursosAsignados      = servicioAplicacion.consultaPeriodoCurso(Long.valueOf("0"), Long.valueOf("0"), paralelo.getParaleloId(), "A");
       
       System.out.println("Cantidad de cursos Asignados del Paralelo a Eliminar: "+cursosAsignados.size());
       
       if ( cursosAsignados.isEmpty() ){
           
            System.out.println("Codigo de paralelo a Eliminar: "+paralelo.getParaleloId());
            servicioAplicacion.eliminaParalelo(paralelo);
            servicioAplicacion.reestableceSecuencia("tbl_paralelo");
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Paralelo eliminado con éxito", "Paralelo eliminado con éxito"));
      
            //Obtiene Lista de Paralelo.-
            paraleloList          =   servicioAplicacion.obtenerParalelo("");  
        }
       else{ 
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Paralelo no se puede eliminar porque contiene información sensible.", " " );  
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }

    public List<TblParalelo> getParaleloList() {
        return paraleloList;
    }

    public void setParaleloList(List<TblParalelo> paraleloList) {
        this.paraleloList = paraleloList;
    }

    public String getParalelo_num() {
        return paralelo_num;
    }

    public void setParalelo_num(String paralelo_num) {
        this.paralelo_num = paralelo_num;
    }

    public String getParalelo_descripcion() {
        return paralelo_descripcion;
    }

    public void setParalelo_descripcion(String paralelo_descripcion) {
        this.paralelo_descripcion = paralelo_descripcion;
    }

    public List<CursoAsignadoList> getCursosAsignados() {
        return cursosAsignados;
    }

    public void setCursosAsignados(List<CursoAsignadoList> cursosAsignados) {
        this.cursosAsignados = cursosAsignados;
    }
    
    
   

}
