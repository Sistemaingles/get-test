
package com.servicios.mb;

import com.entidades.TblRol;
import com.entidades.TblDocente;
import com.entidades.TblUsuarios;
import com.entidades.TblUsuariosRoles;
import com.seguridad.StringEncript;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import com.servicios.util.ValidaCedula;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@ManagedBean(name = "docenteMB")
@RequestScoped

/*
    CLASE JAVA MANAGED BEANS DE DOCENTE
*/
public class DocenteMB {
    
    private Long docentecurso_Id;
    private String identidad = "";
    private String nombres = "";
    private String apellidos= "" ;
    private String genero;
    private String correo;
    private List<TblDocente> docenteList;
    private Date fechaActual;
    private String Usuario;
   
    private String NomApe;
    private String clave; 
    private TblRol rol;
    private Long rol_cod; 
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    private boolean existeDocente = false;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    
    @PostConstruct
    public void init()
    {
        System.out.println("Inicio Docente MB cedula: "+identidad+
                            " nombres: "+nombres+
                            " apellidos: "+ apellidos);
        //Obtiene Lista de Docentes.-
        docenteList          =   servicioAplicacion.obtenerDocente();
    }

    /*
        METODO PARA INGRESAR UN DOCENTE.-
    */
    public void ingresaDocente()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaDocente <DocenteMB>*****************");
        String Estado = "A";
        ValidaCedula validaCedula = new ValidaCedula();
        boolean cedulaValida = validaCedula.verificarCedula(identidad);
        
        
        if( (identidad   == null) || (identidad.equals(""))   || 
            (nombres     == null) || (nombres.equals(""))     || 
            (apellidos   == null) || (apellidos.equals(""))   || 
            (genero      == null) || (genero.equals(""))      ||
            (correo      == null) || (correo.equals(""))  )
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
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo Genero. ", "" ) ); 
            }
            
            if ( (correo   == null) || (correo.equals(""))){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Es obligatorio llenar campo Correo. ", "" ) ); 
            }
               
          }
          else
          {
            String mensaje = "";
            // Patrón para validar el email
            Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");      
            
            Matcher mather = pattern.matcher(correo);

            if (mather.find() == false) {
                mensaje = "El correo ingresado no es válido. \n ";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensaje, "" ) ); 
            }
           

            if ( cedulaValida) {

                  existeDocente = buscaDocentes();

                  if (existeDocente) {
                      context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"La cédula del Docente ya se encuentra registrado.", "" ) ); 
                  }
                  else{
                      if ( mensaje.isEmpty()){
                            //Crea Usuario.-
                           TblUsuarios user = creaUsuario();

                           Long docente_id = guardaDocente( user, identidad, nombres, apellidos, genero,
                                                       correo, Estado);


                           context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Registro de Docente realizado con éxito.", "Registro de Docente realizado con éxito." ) );
                           context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se creo Usuario con las siguiente Credenciales Usuario: "+Usuario + " Contraseña: "+clave, "Se creo Usuario con las siguiente Credenciales Usuario: "+Usuario + " Contraseña: "+clave ) );

                           docenteList          =   servicioAplicacion.obtenerDocente();

                           resstableDocente();
                      }
                  }          
              }
            else{
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Cédula no es válida. ", "" ) ); 
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
            System.out.println("inicialUser  en creaUsuario:"+inicialUser);
            
            int pos     = apellidos.indexOf(" ",1);
            if(pos == -1){
                pos = apellidos.length();
            }

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
            TblUsuarios usuario = new TblUsuarios();
            usuario.setNomApe(NomApe);
            usuario.setUsuario(Usuario);
            usuario.setClave(StringEncript.encrypt(key, iv, clave));
            usuario.setClave_Usr(clave); 
            usuario.setEstado("A");
            usuario.setFchIns(new Date());
            usuario.setObservacion(" ");
            usuario.setUsrIns("ADMIN");
            
            Long idUsr  = Long.valueOf("0");
            idUsr   =   servicioAplicacion.añadirUsuarios(usuario);
            System.out.println("Id de Usuario en creaUsuario DocenteMB :" +idUsr);
            usuario.setId(idUsr);
            
            rol_cod  =   Long.valueOf("2"); //DOCENTE
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
            System.out.println("Error al Guardar Usuario en DocenteMB : "+ex.getMessage());
            return null;
        }    
    }
    
    /*
        METODO PARA GUARDAR DOCENTE SI NO SE ENCUENTRA REGISTRADO.-
    */
    public Long guardaDocente( TblUsuarios usuarios, String identificacion1, String nombres1, String apellidos1, String genero1,
                               String correo1, String estado1)
    {
        TblDocente docente  =   new TblDocente();
        fechaActual         =   new Date();
        IdUserSession       =   getUserSessionId();
        
        System.out.println("guardaDocente Usuario: "+usuarios.getId()+
                            " nombres: "+nombres1+
                            " apellidos: "+apellidos1+
                            " genero: "+genero1+
                            " identificacion: "+identificacion1+
                            " correo: "+correo1+
                            " estado: "+estado1+
                            " fechaActual: "+fechaActual+
                            " IdUserSession: "+IdUserSession
                );
        docente.setDocenteNombres(nombres1);
        docente.setDocenteApellidos(apellidos1);
        docente.setDocenteGenero(genero1);
        docente.setDocenteIdent(identificacion1);
        docente.setDocenteTelefono("09");
        docente.setDocenteCorreo(correo1);
        docente.setDocenteInsusr(IdUserSession);
        docente.setDocenteInstim(fechaActual);
        docente.setDocenteUpdusr(" ");
        docente.setDocenteUpdtim(fechaActual);
        docente.setDocenteDltusr(" ");
        docente.setDocenteDlttim(fechaActual);
        docente.setDocenteSts(estado1);
        docente.setUsuario_cod(usuarios);
        
        return servicioAplicacion.ingresaDocente(docente);

    }
    
    // METODO Busca Docentes Registrados .-
    public boolean buscaDocentes() 
    {
        //Obtiene Lista de Docentes.-
        docenteList   =   servicioAplicacion.obtenerDocente();
        
        existeDocente =  false;
        
        for (int i = 0; i < docenteList.size(); i++) {
            TblDocente get = docenteList.get(i);
            if ( docenteList.get(i).getDocenteIdent().equals(identidad) /*|| 
                 ( docenteList.get(i).getDocenteNombres().equals(nombres) && 
                   docenteList.get(i).getDocenteApellidos().equals(apellidos) )*/) {
                existeDocente = true;
                break;
            } 
        }
        return existeDocente;  
    }
    
    public void resstableDocente()
    {
        identidad   = "";
        nombres     = "";
        apellidos   = "" ;
        genero      = "" ;
        correo      = "" ;
                    
    }
    
 
    /*
        METODO PARA ACTUALIZAR DOCENTE.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            TblDocente docente = new TblDocente(); 
            docente = (TblDocente) event.getObject();

            System.out.println("Docente Id a Actualizar: "+docente.getDocenteId());
            
            for (int i = 0; i < docenteList.size(); i++) {
                TblDocente get = docenteList.get(i);
                if ( ! docenteList.get(i).getDocenteId().equals(docente.getDocenteId()) &&
                      (
                         docenteList.get(i).getDocenteNombres().equals(docente.getDocenteNombres()) && 
                         docenteList.get(i).getDocenteApellidos().equals(docente.getDocenteApellidos())
                      )
                    ) {
                    existeDocente = true;
                    break;
                } 
            }
            
            if (existeDocente) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Docente ya se encuentra registrado.", "" ) ); 
            }
            else{
                servicioAplicacion.actualizaDocente(docente);
                FacesContext context = FacesContext.getCurrentInstance();          
                context.addMessage(null, new FacesMessage("Actualización realizada con éxito", "Actualización con éxito"));  

            }
            
            //Obtiene Lista de Docente.-
            docenteList          =   servicioAplicacion.obtenerDocente();  

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
    public void eliminar(TblDocente docente)
    {
      System.out.println("Codigo de docente a Eliminar: "+docente.getDocenteId());
      servicioAplicacion.eliminaDocente(docente);
      FacesContext context = FacesContext.getCurrentInstance();          
      context.addMessage(null, new FacesMessage("Docente eliminado con éxito", "Docente eliminado con éxito"));
      
      //Obtinene Lista de Docente.-
      docenteList          =   servicioAplicacion.obtenerDocente();  
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public IUsuarioSession<TblUsuarios> getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(IUsuarioSession<TblUsuarios> usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public List<TblDocente> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<TblDocente> docenteList) {
        this.docenteList = docenteList;
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
