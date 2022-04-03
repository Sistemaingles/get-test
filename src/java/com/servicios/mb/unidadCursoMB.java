
package com.servicios.mb;



import com.entidades.TblCursoparalelo;
import com.entidades.TblUnidad;
import com.entidades.TblUsuarios;
import com.entidades.listas.CursoAsignadoList;
import com.entidades.listas.UnidadList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author mespinoza
 */
@ManagedBean(name = "unidadCursoMB")
@ViewScoped
public class unidadCursoMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;

    private CursoAsignadoList selectedCurso;
    private String nombre;
    private String descripcion;
    private Long cursoparalelo_cod;
    private Date fechaActual;
    private String Usuario;
    private String NomApe;
    private BigInteger total_unidades;
    private BigInteger cantidad_unidaddes;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    private List<UnidadList> unidadList;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    { 
        cursoparalelo_cod  =   Long.valueOf("0");
        //    unidadList         = servicioAplicacion.consultaUnidadCurso(usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "", "" );
        unidadList         =   servicioAplicacion.consultaUnidad(usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "");
        total_unidades     =   servicioAplicacion.obtenerParametroNum("TOTAL_UNIDAD");
    }
    

    public void showCursoParalelo(ActionEvent ae) {
        System.out.print("unidadCursoMB Ejecuta showCursoParalelo");
      RequestContext.getCurrentInstance().openDialog("pag_asignacursoU", getDialogOptions(), null);
    }
    
    public void cursoParaleloSelecionado(SelectEvent event) 
    {
	CursoAsignadoList periodo = (CursoAsignadoList) event.getObject();
        System.out.println("CursoParalelo Seleccionado:"+periodo.getCursoparalelo_cod());
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CursoParalelo Seleccionado: "+periodo.getCursoparalelo_cod(), "Id:" + periodo.getCursoparalelo_cod());
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        
       selectedCurso              =   new CursoAsignadoList();         
       selectedCurso.setCursoparalelo_cod(periodo.getCursoparalelo_cod());
       selectedCurso.setCurso_cod(periodo.getCurso_cod());
       selectedCurso.setCurso_descripcion(periodo.getCurso_descripcion());
       selectedCurso.setParalelo_cod(periodo.getParalelo_cod());
       selectedCurso.setParalelo_nombre(periodo.getParalelo_nombre());
       setCursoparalelo_cod(periodo.getCursoparalelo_cod());
        
       System.out.println("selectedCurso CursoParalelo_cod: "+cursoparalelo_cod);
       
    }
    
    public Map<String, Object> getDialogOptions() {
      Map<String, Object> options = new HashMap<>();
      options.put("resizable", false);
      options.put("draggable", true);
      options.put("modal", true);
      //options.put("height", 600);
      //options.put("contentHeight", "100%");
      options.put("contentHeight", 350);
      options.put("contentWidth", 1000);
      
      return options;
  }


     /*
    METODO PARA INGRESAR UNIDAD CON CURSO ASIGNADO.-
    */
    public void ingresaUnidadCurso()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaUnidadCurso <unidadCursoMB>*****************");
        String Estado = "A";
        int valorcero =0;
        System.out.println("ingresaUnidadCurso CursoParalelo_cod: "+cursoparalelo_cod);
        cantidad_unidaddes  = BigInteger.ZERO;
        
        if( (nombre   == null) || (nombre.equals(""))   || 
            (descripcion   == null) || (descripcion.equals(""))   || 
            (cursoparalelo_cod == null) || ( cursoparalelo_cod == 0 )  
          )
          {
                
            if( (nombre   == null) || (nombre.equals("")) ){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo UNIDAD.", "" ) );
            }
            
            if( (descripcion   == null) || (descripcion.equals("")) ){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo DESCRIPCIÓN.", "" ) );
            }
            
            if( (cursoparalelo_cod   == null) || (cursoparalelo_cod == 0) ){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo CURSO Y PARALELO.", "" ) );
            }

          }
          else
          {
                buscaUnidadCursosAsignados();

                int cantidad = 0;
                Long unidad_id = Long.valueOf("0");
                
      
                System.out.println("cantidad de Unidad Cursos Asignados : "+cantidad);
                
                for (int i = 0; i < unidadList.size(); i++) 
                {
                  Long curso = unidadList.get(i).getCursoparaleloid();
                  unidad_id = unidadList.get(i).getUnidad_id();
                  String nombre_unidad = unidadList.get(i).getNombres();
                  System.out.println("Curso asignado a la Unidad : "+curso+
                            " Nombre unidad: "+nombre_unidad+
                            " Cursoparalelo Cod: "+cursoparalelo_cod);
                  
                  if ( curso.equals(cursoparalelo_cod)  )
                  {
                      if ( nombre_unidad.equals(nombre) ){
                          cantidad = cantidad + 1;
                      }
                      
                      cantidad_unidaddes = cantidad_unidaddes.add(BigInteger.ONE);
                  }
                      
                }
                
                if( cantidad != 0)
                {
                    //Si existe Unidad con el curso Seleccionado registrado.-
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Unidad ya se encuentra registrada y con el curso-paralelo asignado.", "Unidad ya se encuentra registrada y con el curso-paralelo asignado." ) );
                } 
                else 
                {
                    cantidad_unidaddes = cantidad_unidaddes.add(BigInteger.ONE);
                    int cantidad_unidadCurso = cantidad_unidaddes.compareTo(total_unidades);
                    System.out.println("Total de Unidades: "+total_unidades+
                                        " Cantidad_unidaddes: "+cantidad_unidaddes
                                        );
                    if( cantidad_unidadCurso == 1){
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se puede registrar mas de "+total_unidades.toString() + " Unidades.", "" ) );
                    }
                    else {
                        //Asigna Curso a la Unidad Registrada.-
                        System.out.println("ingresaUnidadCurso unidad: "+unidad_id);
                        
                        unidad_id = guardaUnidad( nombre, descripcion, cursoparalelo_cod, Estado);
                        
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Curso asignado a la unidad con éxito.", "Curso asignado a la Unidad con éxito." ) );
                    }
                    
                    buscaUnidadCursosAsignados() ;
                }
          }
    }
    
    
    // METODO Busca Unidad-Cursos Paralelos Asignados .-
    public void buscaUnidadCursosAsignados() 
    {
        //Obtiene Detalle de Cursos-Paralelos asignados al docente.-
        System.out.println("buscaUnidadCursosAsignados nombres "+nombre+
                                       " descripcion: "+descripcion
                                        );
        //unidadList = servicioAplicacion.consultaUnidadCurso( usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), nombre, descripcion );
        unidadList             = servicioAplicacion.consultaUnidad(usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "");
    }
    
    /*
        METODO PARA GUARDAR UNIDAD Y CURSO SI NO SE ENCUENTRA REGISTRADO.-
    */
    public Long guardaUnidad( String nombres1, String descripcion1, Long cursoparalelo_cod1, String estado1)
    {
        TblUnidad unidad  =   new TblUnidad();
        fechaActual         =   new Date();
        IdUserSession       =   getUserSessionId();
        
        System.out.println("guardaUnidad nombres: "+nombres1+
                            " descripcion: "+descripcion1+
                            " estado: "+estado1+
                            " fechaActual: "+fechaActual+
                            " IdUserSession: "+IdUserSession+
                            " cursoparalelo_cod: "+cursoparalelo_cod1
                );
        
        TblCursoparalelo cursoParalelo = new TblCursoparalelo();
        cursoParalelo.setCursoparaleloId(cursoparalelo_cod1);
        
        unidad.setUnidadNombre(nombre);
        unidad.setUnidadDescripcion(descripcion);
        unidad.setUnidadClase("small-box bg-yellow");
        unidad.setUnidadIcono("ion ion-clipboard");
        	
        //unidad.setCursoparaleloId(cursoParalelo);
        /*unidad.setDocenteInstim(fechaActual);
        unidad.setDocenteUpdusr(" ");
        unidad.setDocenteUpdtim(fechaActual);
        unidad.setDocenteDltusr(" ");
        unidad.setDocenteDlttim(fechaActual);*/
        unidad.setCursoparaleloId(cursoParalelo);
        unidad.setUnidadSts(estado1);
        
        return servicioAplicacion.ingresaUnidad(unidad);

    }
    
    /*
        METODO PARA ACTUALIZAR UNIDAD.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            
            UnidadList unidadL = new UnidadList(); 
            unidadL = (UnidadList) event.getObject();
            System.out.println("Unidad Id a Actualizar: "+unidadL.getUnidad_id());
            
            TblCursoparalelo cursoParalelo = new TblCursoparalelo();
            cursoParalelo.setCursoparaleloId(unidadL.getCursoparaleloid());
        
            TblUnidad unidad = new TblUnidad();
            unidad.setUnidadId(unidadL.getUnidad_id());
            unidad.setUnidadNombre(unidadL.getNombres());
            unidad.setUnidadDescripcion(unidadL.getDescripcion());
            unidad.setUnidadClase(unidadL.getClase());
            unidad.setUnidadIcono(unidadL.getIcono());
            unidad.setUnidadSts(unidadL.getEstado());
            unidad.setCursoparaleloId(cursoParalelo);
            
            servicioAplicacion.actualizaUnidad(unidad);
            
            FacesContext context = FacesContext.getCurrentInstance();
            
            context.addMessage(null, new FacesMessage("Actualización realizada con éxito", ""));  
    
            //Obtiene Lista de Unidad.-
            unidadList  = servicioAplicacion.consultaUnidad(usuarioSession.getUsuarioSession().getId(), Long.valueOf("0"), "");
            
        } catch (Exception e) {
            System.out.println("Ocurrio un error al actualizar Unidad: "+ e.getMessage());
        }
    }
    
    /*
        METODO PARA CANCELAR ACTUALIZACION.-
    */
    public void cancelar() {
        FacesContext context = FacesContext.getCurrentInstance();          
        context.addMessage(null, new FacesMessage("Ud. Canceló la actualización", "")); 
    }
    
    public void resstableceUnidad()
    {

        nombre              = "";
        descripcion         = "";
        //cursoparalelo_cod   = null; 
        //selectedCurso       = null;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<UnidadList> getUnidadList() {
        return unidadList;
    }

    public void setUnidadList(List<UnidadList> unidadList) {
        this.unidadList = unidadList;
    }

    public BigInteger getTotal_unidades() {
        return total_unidades;
    }

    public void setTotal_unidades(BigInteger total_unidades) {
        this.total_unidades = total_unidades;
    }

    public BigInteger getCantidad_unidaddes() {
        return cantidad_unidaddes;
    }

    public void setCantidad_unidaddes(BigInteger cantidad_unidaddes) {
        this.cantidad_unidaddes = cantidad_unidaddes;
    }
   
    
    
}
