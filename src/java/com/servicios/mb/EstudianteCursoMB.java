
package com.servicios.mb;


import com.entidades.TblRol;
import com.entidades.TblCursoparalelo;
import com.entidades.TblEstudiante;
import com.entidades.TblEstudiantecurso;
import com.entidades.TblUsuarios;
import com.entidades.TblUsuariosRoles;
import com.entidades.listas.EstudianteList;
import com.entidades.listas.CursoAsignadoList;
import com.seguridad.StringEncript;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import com.servicios.util.ValidaCedula;
import java.io.Serializable;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author mespinoza
 */
@ManagedBean(name = "estudianteCursoMB")
@ViewScoped
public class EstudianteCursoMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private Date fechaNac;
    private String genero;
    private String correo;
    private CursoAsignadoList selectedCurso;
    private Long cursoparalelo_cod;
    private Date fechaActual;
    private String Usuario;
    private String NomApe;
    private String clave; 
    private TblRol rol;
    private Long rol_cod; 
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    private List<EstudianteList> estudianteList;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @PostConstruct
    public void init()
    { 
         
        cursoparalelo_cod  = Long.valueOf("0");
        estudianteList = servicioAplicacion.consultaEstudianteCurso("", "", "" );
         
    }
    public void showCursoParalelo(ActionEvent ae) {
        System.out.print("estudianteCursoMB Ejecuta showCursoParalelo");
      RequestContext.getCurrentInstance().openDialog("pag_asignacursoe", getDialogOptions(), null);
  }
    
    public void cursoParaleloSelecionado(SelectEvent event) 
    {
	CursoAsignadoList periodo = (CursoAsignadoList) event.getObject();
        System.out.println("CursoParalelo Seleccionado:"+periodo.getCursoparalelo_cod());
        
        /*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "CursoParalelo Seleccionado: "+periodo.getCursoparalelo_cod(), "Id:" + periodo.getCursoparalelo_cod());
        FacesContext.getCurrentInstance().addMessage(null, message);*/
        
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
    METODO PARA INGRESAR CURSO CON SU PARALELO ASIGNADO.-
    */
    public void ingresaCursoParalelo()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaPeriodoLectivo <periodoMB>*****************");
        String Estado = "A";
        int valorcero =0;
        System.out.println("selectedCurso CursoParalelo_cod: "+cursoparalelo_cod);
        ValidaCedula validaCedula = new ValidaCedula();
        boolean cedulaValida = validaCedula.verificarCedula(identificacion);
        
        if( (identificacion   == null) || (identificacion.equals(""))   || 
            (nombres    == null) || (nombres.equals(""))   || 
            (apellidos  == null) || (apellidos.equals(""))   || 
            (fechaNac   == null)    || 
            (genero     == null) || (genero.equals(""))   ||
            (correo     == null) || (correo.equals(""))  ||
            (cursoparalelo_cod == null) || ( cursoparalelo_cod == 0 )  
          )
          {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor, llene información obligatoria (*) del formulario para continuar.", " " );
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("No ingreso informacion obligatoria del Formualario: ");
          }
          else
          {
                if (cedulaValida){
                    
                    buscaCursosParalelosAsignados() ;
                    int cantidad_estudianteCurso = 0;
                    int cantidad = 0;
                    Long estudiante_id = Long.valueOf("0");

                    cantidad_estudianteCurso  = estudianteList.size();

                    System.out.println("cantidad de Estudiantes Asignados : "+cantidad);

                    for (int i = 0; i < estudianteList.size(); i++) 
                    {
                      Long curso = estudianteList.get(i).getCursoparaleloid();
                      estudiante_id = estudianteList.get(i).getEstudiante_id();
                      System.out.println("Curso asignado al estudiante : "+curso);
                      if (curso == cursoparalelo_cod)
                      {
                          cantidad = cantidad + 1;

                      }   
                    }

                    if( cantidad_estudianteCurso != 0 && cantidad != 0)
                    {
                        //Si existe Estudiante con el curso Seleccionado registrado.-
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Docente ya se encuentra registrado y con el curso-paralelo asignado.", "Docente ya se encuentra registrado y con el curso-paralelo asignado." ) );
                    } 
                    else 
                    {
                        if(cantidad_estudianteCurso == 0){
                            //Si no existe Docente Registrado, lo registra.-
                            System.out.println("Invoca Metodo Guardar Estudiante: ");

                            //Crea Usuario.-
                            TblUsuarios user = creaUsuario();

                            estudiante_id = guardaEstudiante( user, identificacion, nombres, apellidos, fechaNac, genero,
                                                           correo, Estado);

                            //Asigna Curso al Estudiante Registrado.-
                            asignarCursoEstudiante( estudiante_id, cursoparalelo_cod);

                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro de Estudiante y curso asignado con éxito.", "Registro de Docente y curso asignado con éxito." ) );
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se creo Usuario con las siguiente Credenciales Usuario: "+Usuario + " Contraseña: "+clave, "Se creo Usuario con las siguiente Credenciales Usuario: "+Usuario + " Contraseña: "+clave ) );

                            estudianteList = servicioAplicacion.consultaEstudianteCurso("", "", "" );
                        }
                        else {
                            //Asigna Curso al Estudiante Registrado.-
                            System.out.println("ingresaCursoParalelo estudiante_id: "+estudiante_id);
                            asignarCursoEstudiante(estudiante_id, cursoparalelo_cod);
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Curso asignado al estudiante con éxito.", "Curso asignado al estudiante con éxito." ) );
                        }
                    }
                }
                else{
                   context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Identificación no valida.", "Identificación no valida." ) ); 
                }
                    

          }
    }
    

    // METODO Busca Cursos Paralelos Asignados al Estudiante.-
    public void buscaCursosParalelosAsignados() 
    {
        //Obtiene Detalle de Cursos-Paralelos asignados al estudiante.-
        System.out.println("buscaCursosParalelosAsignados identificacion: "+identificacion+
                                       "nombres: "+nombres+
                                        "apellidos: "+apellidos
                                        );
        estudianteList = servicioAplicacion.consultaEstudianteCurso( identificacion, nombres, apellidos );
    }
    
    // METODO Busca Cursos Paralelos Asignados al Estudiante .-
    public void verEstudiantes() 
    {
        buscaCursosParalelosAsignados();
        System.out.println("verEstudiantes identificacion: "+identificacion+
                            " Cant Reg: "+estudianteList.size());
        for (int i = 0; i < estudianteList.size(); i++) {
            System.out.println("verEstudiantes identificacion: "+identificacion);
            System.out.println("verEstudiantes getIdentificacion: "+estudianteList.get(i).getIdentificacion());
            if(estudianteList.get(i).getIdentificacion().equals(identificacion) )
            {
                System.out.println("verEstudiantes Nombre: "+estudianteList.get(i).getNombres());
                System.out.println("verEstudiantes FechaNac: "+estudianteList.get(i).getFechaNac());
                setNombres(estudianteList.get(i).getNombres()); 
                setApellidos(estudianteList.get(i).getApellidos());
                setFechaNac(estudianteList.get(i).getFechaNac());
                setGenero(estudianteList.get(i).getGenero());
                setCorreo(estudianteList.get(i).getCorreo());
            }
        }
        
    }
    
    /*
        METODO PARA GUARDAR ESTUDIANTE SI NO SE ENCUENTRA REGISTRADO.-
    */
    public Long guardaEstudiante( TblUsuarios usuario, String identificacion1, String nombres1, String apellidos1, Date fechaNac1, String genero1,
                                    String correo1, String estado1)
    {
        TblEstudiante estudiante  =   new TblEstudiante();
        fechaActual         =   new Date();
        IdUserSession       =   getUserSessionId();
        
        System.out.println("guardaEstudiante nombres: "+nombres1+
                            " apellidos: "+apellidos1+
                            " fechaNac: "+fechaNac1+
                            " genero: "+genero1+
                            " identificacion: "+identificacion1+
                            " correo: "+correo1+
                            " estado: "+estado1+
                            " fechaActual: "+fechaActual+
                            " IdUserSession: "+IdUserSession
                );
        estudiante.setEstudianteIdent(identificacion1);
        estudiante.setEstudianteNom(nombres1);
        estudiante.setEstudianteApe(apellidos1);
        estudiante.setEstudianteFechaNac(fechaNac1);
        estudiante.setEstudianteGenero(genero1);
        estudiante.setEstudiantePapa(" ");
        estudiante.setEstudianteMama(" ");
        estudiante.setEstudianteCelular("09");
        estudiante.setEstudianteInsusr(IdUserSession);
        estudiante.setEstudianteInstim(fechaActual);
        estudiante.setEstudianteUpdusr(" ");
        estudiante.setEstudianteUpdtim(fechaActual);
        estudiante.setEstudianteDltusr(" ");
        estudiante.setEstudianteDlttim(fechaActual);
        estudiante.setEstudianteSts(estado1);
        estudiante.setUsuario_cod(usuario);
        return servicioAplicacion.ingresaEstudiante(estudiante);

    }
    
    /*
        METODO ASIGNA CURSO PARALELO AL ESTUDIANTE REGISTRADO.-
    */
    public void asignarCursoEstudiante( Long estudiante_id1, Long cursoparalelo_cod1)
    {
        TblEstudiantecurso estudiantecurso = new TblEstudiantecurso();
        fechaActual         =   new Date();
        IdUserSession       =   getUserSessionId();
        
        System.out.println("asignarCursoEstudiante estudiante_id: "+estudiante_id1+
                            " cursoparalelo_cod:"+cursoparalelo_cod1);
        
        TblEstudiante estudiante = new TblEstudiante();
        estudiante.setEstudianteId(estudiante_id1);
        
        TblCursoparalelo cursoParalelo = new TblCursoparalelo();
        cursoParalelo.setCursoparaleloId(cursoparalelo_cod1);
        
        estudiantecurso.setEstudianteId(estudiante);
        estudiantecurso.setCursoparaleloId(cursoParalelo);
        estudiantecurso.setEstudiantecursoInsusr(IdUserSession);
        estudiantecurso.setEstudiantecursoInstim(fechaActual);
        estudiantecurso.setEstudiantecursoSts("A");
        estudiantecurso.setEstudiantecursoUpdusr(" ");
        estudiantecurso.setEstudiantecursoUpdtim(fechaActual);
        estudiantecurso.setEstudiantecursoDltusr(" ");
        estudiantecurso.setEstudiantecursoDlttim(fechaActual);
        
        servicioAplicacion.ingresaEstudianteCurso(estudiantecurso);
        
    }    
    
    public void resstableceEstudiante()
    {
        identificacion  = "";
        nombres         = "";
        apellidos       = "";
        fechaNac        = null;  
        genero          = null;
        correo          = "";
    }
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }
    
    public TblUsuarios creaUsuario()
    {
        nombres     = nombres.trim();
        apellidos   = apellidos.trim();
        String inicialUser = nombres.substring(0, 1);
        System.out.println("inicialUser  en creaUsuario:"+inicialUser);
        int pos     = apellidos.indexOf(" ",1);
        System.out.println("POsicion  en creaUsuario:"+pos);
        Usuario     = inicialUser + apellidos.substring(0, pos) ;
        Usuario     = Usuario.toUpperCase();
        clave       = Usuario;
        System.out.println("Usuario en creaUsuario:"+Usuario);
        NomApe      = nombres +" "+apellidos;
        NomApe      = NomApe.toUpperCase();
        Long idUsr  = Long.valueOf("0");
        
        try 
        {
            /*Inserta Usuario .-*/
            TblUsuarios usuario = new TblUsuarios();
            usuario.setNomApe(NomApe);
            usuario.setUsuario(Usuario);
            usuario.setClave(StringEncript.encrypt(key, iv, clave));
            usuario.setClave_Usr(clave); 
            usuario.setEstado("A");
            usuario.setFchIns(new Date());
            usuario.setObservacion(" ");
            usuario.setUsrIns("ADMIN");
            
            idUsr   =   servicioAplicacion.añadirUsuarios(usuario);
            System.out.println("Id de Usuario en creaUsuario estudianteCursoMB :" +idUsr);
            usuario.setId(idUsr);
            
            rol_cod  =   Long.valueOf("3");
            System.out.println("Rol Seleccionado: "+rol_cod);
            rol = servicioAplicacion.obtieneRolxId(rol_cod);
            
            System.out.println("Rol Seleccionado2: "+rol.getRol());

            TblUsuariosRoles usuariosRoles = new TblUsuariosRoles();
            usuariosRoles.setEstado("A");
            usuariosRoles.setObservacion("---");
            usuariosRoles.setDescripcion("Por sistema");
            usuariosRoles.setRol(rol);
            usuariosRoles.setUsuario(usuario);
            usuariosRoles.setFchIns(new Date());
            usuariosRoles.setUsrIns("ADMIN");
            
            servicioAplicacion.añadirUsuariosRoles(usuariosRoles);
            
            return usuario;
            
        } catch (Exception ex) 
        {
            System.out.println("Error al Guardar Usuario en CursoParaleloMB : "+ex.getMessage());
            return null;
        }
        

        
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<EstudianteList> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<EstudianteList> estudianteList) {
        this.estudianteList = estudianteList;
    }

    
}
