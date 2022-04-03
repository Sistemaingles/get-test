
package com.servicios.mb;

import com.entidades.TblRol;
import com.entidades.TblEstudiante;
import com.entidades.TblUsuarios;
import com.entidades.TblUsuariosRoles;
import com.seguridad.StringEncript;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import com.servicios.util.ValidaCedula;
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
@ManagedBean(name = "estudianteMB")
@RequestScoped

/*
    CLASE JAVA MANAGED BEANS DE DOCENTE
*/
public class EstudianteMB {
    
    private Long docentecurso_Id;
    private String identidad = "";
    private String nombres = "";
    private String apellidos= "" ;
    private String genero;
    private List<TblEstudiante> estudianteList;
    private Date fechaActual;
    private String Usuario;
   
    private String NomApe;
    private String clave; 
    private TblRol rol;
    private Long rol_cod; 
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    private boolean existeEstudiante;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    
    @PostConstruct
    public void init()
    {
        System.out.println("Inicio Estudiante MB cedula: "+identidad+
                            " nombres: "+nombres+
                            " apellidos: "+ apellidos);
        
        //Obtiene Lista de Estudiante.-
        estudianteList          =   servicioAplicacion.obtenerEstudiante();
    }

    /*
        METODO PARA INGRESAR UN ESTUDIANTE.-
    */
    public void ingresaEstudiante()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaEstudiante <EstudianteMB>*****************");
        String Estado = "A";
        ValidaCedula validaCedula = new ValidaCedula();
        boolean cedulaValida = validaCedula.verificarCedula(identidad);
        
        
        if( (identidad   == null) || (identidad.equals(""))   || 
            (nombres     == null) || (nombres.equals(""))     || 
            (apellidos   == null) || (apellidos.equals(""))   || 
            (genero      == null) || (genero.equals(""))      )
          {
            
            if ( (identidad   == null) || (identidad.equals(""))){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo Cédula. ", "" ) ); 
            }
            
            if ( (nombres   == null) || (nombres.equals(""))){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo Nombres. ", "" ) ); 
            }
            
            if ( (apellidos   == null) || (apellidos.equals(""))){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo Apellidos. ", "" ) ); 
            }
            
            if ( (genero   == null) || (genero.equals(""))){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo Genero.", "" ) ); 
            }
              
       
          }
          else
          {
      
            if (cedulaValida){

                existeEstudiante = buscaEstudiantes();
                
                if (existeEstudiante) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"La cédula del Estudiante ya se encuentra registrada.", "" ) ); 
                }
                else{
                    //Crea Usuario.-
                    TblUsuarios user = creaUsuario();

                    guardaEstudiante( user, identidad, nombres, apellidos, genero,Estado);

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro de Estudiante realizado con éxito.", "Registro de Estudiante realizado con éxito." ) );
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se creo Usuario con las siguiente Credenciales Usuario: "+Usuario + " Contraseña: "+clave, "Se creo Usuario con las siguiente Credenciales Usuario: "+Usuario + " Contraseña: "+clave ) );

                    estudianteList          =   servicioAplicacion.obtenerEstudiante();

                    resstableEstudiante();  
                } 
            }
            else{
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cédula no es valida.", "" ) ); 
            }
          }
    }
    
    public TblUsuarios creaUsuario()
    {
        nombres     = nombres.trim();
        apellidos   = apellidos.trim();
        
        boolean existeUser = true;
        int contador = 0;
        
        while (existeUser) {
            contador += 1;
            String inicialUser = nombres.substring(0, contador);
            
            System.out.println("apellidos  en creaUsuario:"+apellidos);
            System.out.println("inicialUser  en creaUsuario:"+inicialUser);
            int pos     = apellidos.indexOf(" ",1);
            if(pos == -1){
                pos = apellidos.length();
            }
            System.out.println("POsicion  en creaUsuario: "+pos);
            Usuario     = inicialUser + apellidos.substring(0, pos) ;
            Usuario     = Usuario.toUpperCase();
            clave       = Usuario;
            System.out.println("Usuario en creaUsuario:"+Usuario);
            
            try {
                 TblUsuarios lusuario = servicioAplicacion.ValidaUsuario(Usuario);
                if (lusuario.equals(null)){
                    existeUser = false;
                    System.out.println("No existe Usuario");
                }
            } catch (Exception e) {
                System.out.println("No existe Usuario en TryCatch");
                existeUser = false;
            }
        }
       
        NomApe      = nombres +" "+apellidos;
        NomApe      = NomApe.toUpperCase();
        
        try 
        {
            /*Inserta Usuario .-*/
            Long idUsr  = Long.valueOf("0");
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
            System.out.println("Id de Usuario en creaUsuario DocenteMB :" +idUsr);
            usuario.setId(idUsr);
            
            rol_cod  =   Long.valueOf("3"); //ESTUDIANTE
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
            System.out.println("Error al Guardar Usuario en EstudianteMB : "+ex.getMessage());
            return null;
        }    
    }
    
    /*
        METODO PARA GUARDAR ESTUDIANTE SI NO SE ENCUENTRA REGISTRADO.-
    */
    public void guardaEstudiante( TblUsuarios usuarios, String identificacion1, String nombres1, String apellidos1, String genero1, String estado1)
    {
        TblEstudiante estudiante  =   new TblEstudiante();
        fechaActual         =   new Date();
        IdUserSession       =   getUserSessionId();
        
        System.out.println("guardaEstudiante Usuario: "+usuarios.getId()+
                            " nombres: "+nombres1+
                            " apellidos: "+apellidos1+
                            " genero: "+genero1+
                            " identificacion: "+identificacion1+
                            " estado: "+estado1+
                            " fechaActual: "+fechaActual+
                            " IdUserSession: "+IdUserSession
                );
        estudiante.setEstudianteNom(nombres1);
        estudiante.setEstudianteApe(apellidos1);
        estudiante.setEstudianteGenero(genero1);
        estudiante.setEstudianteIdent(identificacion1);
        estudiante.setEstudianteCelular("09");
        estudiante.setEstudianteInsusr(IdUserSession);
        estudiante.setEstudianteInstim(fechaActual);
        estudiante.setEstudianteUpdusr(" ");
        estudiante.setEstudianteUpdtim(fechaActual);
        estudiante.setEstudianteDltusr(" ");
        estudiante.setEstudianteDlttim(fechaActual);
        estudiante.setEstudianteSts(estado1);
        estudiante.setUsuario_cod(usuarios);
        
        servicioAplicacion.ingresarEstudiante(estudiante);

    }
    
    // METODO Busca Estudiantes Registrados .-
    public boolean buscaEstudiantes() 
    {
        System.out.println("buscaEstudiantes identidad: "+identidad.toString());
        
        //Obtiene Lista de Estudiantes.-
        estudianteList   =   servicioAplicacion.obtenerEstudiante();
        
        existeEstudiante =  false;
        
        for (int i = 0; i < estudianteList.size(); i++) {
            TblEstudiante get = estudianteList.get(i);
            if ( estudianteList.get(i).getEstudianteIdent().equals(identidad) /*|| 
                 ( estudianteList.get(i).getEstudianteNom().equals(nombres) && 
                   estudianteList.get(i).getEstudianteApe().equals(apellidos) )*/) {
                existeEstudiante = true;
                break;
            } 
        }
        return existeEstudiante; 
        
    }
    
    public void resstableEstudiante()
    {
        identidad   = "";
        nombres     = "";
        apellidos   = "" ;
        genero      = "" ;
                    
    }
    
 
    /*
        METODO PARA ACTUALIZAR ESTUDIANTE.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            TblEstudiante estudiante = new TblEstudiante(); 
            estudiante = (TblEstudiante) event.getObject();

            System.out.println("Estudiante Id a Actualizar: "+estudiante.getEstudianteId());
            
            for (int i = 0; i < estudianteList.size(); i++) {
                TblEstudiante get = estudianteList.get(i);
                if ( ! estudianteList.get(i).getEstudianteId().equals(estudiante.getEstudianteId()) &&
                      (
                         estudianteList.get(i).getEstudianteNom().equals(estudiante.getEstudianteNom()) && 
                         estudianteList.get(i).getEstudianteApe().equals(estudiante.getEstudianteApe())
                      )
                    ) {
                    existeEstudiante = true;
                    break;
                } 
            }
            
            if (existeEstudiante) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Estudiante ya se encuentra registrado.", "" ) ); 
            }
            else{
                servicioAplicacion.actualizaEstudiante(estudiante);
                FacesContext context = FacesContext.getCurrentInstance();          
                context.addMessage(null, new FacesMessage("Actualización realizada con éxito", ""));  
            }
            
            //Obtinene Lista de Estudiante.-
            estudianteList          =   servicioAplicacion.obtenerEstudiante();  
           
        } catch (Exception e) {
            System.out.println("Error al Actualizar en DocenteMb; "+e.getMessage());
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
    public void eliminar(TblEstudiante estudiante)
    {
      System.out.println("Codigo de estudiante a Eliminar: "+estudiante.getEstudianteId());
      servicioAplicacion.eliminaEstudiante(estudiante);
      FacesContext context = FacesContext.getCurrentInstance();          
      context.addMessage(null, new FacesMessage("Estudiante eliminado con éxito", "Estudiante eliminado con éxito"));
      
      //Obtinene Lista de Estudiantes.-
      estudianteList          =   servicioAplicacion.obtenerEstudiante();  
    }
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }

    public Long getDocentecurso_Id() {
        return docentecurso_Id;
    }

    public void setDocentecurso_Id(Long docentecurso_Id) {
        this.docentecurso_Id = docentecurso_Id;
    }

    
    public String getIdentidad() {
        return identidad;
    }

    public void setIdentidad(String identidad) {
        this.identidad = identidad;
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



    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    public IUsuarioSession<TblUsuarios> getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(IUsuarioSession<TblUsuarios> usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public List<TblEstudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<TblEstudiante> estudianteList) {
        this.estudianteList = estudianteList;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public TblRol getRol() {
        return rol;
    }

    public void setRol(TblRol rol) {
        this.rol = rol;
    }

    public Long getRol_cod() {
        return rol_cod;
    }

    public void setRol_cod(Long rol_cod) {
        this.rol_cod = rol_cod;
    }

    
    
    
    
    
}
