
package com.servicios.mb;

import com.entidades.TblActividad;
import com.entidades.TblPresentaciontaller;
import com.entidades.TblRespuesta;
import com.entidades.TblResultado;
import com.entidades.TblResultadotaller;
import com.entidades.TblSubtema;
import com.entidades.TblTempactiv;
import com.entidades.TblUsuarios;
import com.entidades.listas.RespuestasList;
import com.entidades.listas.TallerList;
import com.seguridad.StringEncript;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author mespinoza
 */
@ManagedBean(name = "presentaTallerMB")
//@RequestScoped
@SessionScoped
public class presentatallerMB implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    


    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    private List<TallerList>    tallerList;
    private List<TblRespuesta>  respuestList;
    private Date                fechaActual;
    private TblResultado        resultado;
    private Long                Subtema_Id;
    private List<TblTempactiv>  temp_Activ;
    private Long                unidad_Id;
    private String              tipo = "S"; //SUBTEMA
    private Long                destrezaId;
    private Long                usuario_Cod;
    private final String key     =   "92AE31A79FEEB2A3"; //llave
    private final String iv      =   "0123456789ABCDEF"; // vector de inicialización
    
    @Inject
    private TblRespuesta respuesta;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    private BigInteger pct_Aprobado;
    
    @PostConstruct
    public void init()
    { 
        System.out.println("presentaTallerMb Metodo Init");
        try {
            
            temp_Activ = servicioAplicacion.consultaTmpActividad(usuarioSession.getUsuarioSession().getId(), "");
            for (int i = 0; i < temp_Activ.size(); i++) {
                if(temp_Activ.get(i).getTempActivTipo().equals("D"))
                {
                    destrezaId = temp_Activ.get(i).getTempActivIdRef();
                    System.out.println("presentaTallerMb DestrezaId: "+destrezaId);
                }
                if(temp_Activ.get(i).getTempActivTipo().equals("U"))
                {
                    unidad_Id = temp_Activ.get(i).getTempActivIdRef();
                    System.out.println("presentaTallerMb unidad_Id: "+unidad_Id);
                }
                if(temp_Activ.get(i).getTempActivTipo().equals("S"))
                {
                    Subtema_Id = temp_Activ.get(i).getTempActivIdRef();
                    System.out.println("presentaTallerMb Subtema_Id: "+Subtema_Id);
                }

            }
            
            usuario_Cod = usuarioSession.getUsuarioSession().getId();
            
            if (Subtema_Id == null){
                System.out.println("SubtemaMb Subtema_Id ==null"); 
                FacesContext facesContext = FacesContext. getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                Map params = externalContext.getRequestParameterMap();
                String IdC1 = (String) params.get("Id1" );
                String IdC2 = (String) params.get("Id2" );
                 try {
                        IdC1 = StringEncript.decrypt(key, iv, IdC1);
                        IdC2 = StringEncript.decrypt(key, iv, IdC2);
                 } catch (Exception ex) {
                    Logger.getLogger(SubtemaMb.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Unidad: " + IdC1);
                System.out.println("SubTemaId: " + IdC2);

                Subtema_Id   = Long.valueOf(IdC2);
                    
                TblTempactiv tmp_Activ = new TblTempactiv();
                tmp_Activ.setTempActivUsrCod(usuario_Cod);
                tmp_Activ.setTempActivIdRef(Subtema_Id);
                tmp_Activ.setTempActivTipo("S");
                tmp_Activ.setTempActivSts("A");
                servicioAplicacion.ingresaTempActiv(tmp_Activ);
            }
            
            tallerList          =   servicioAplicacion.consultaTaller(usuario_Cod, destrezaId, Subtema_Id );
            fechaActual         =   new Date();
            IdUserSession       =   getUserSessionId();
            pct_Aprobado        =   servicioAplicacion.obtenerParametroNum("PCT_APROBADO");

            //Obtiene Unidad.-
            //obtieneUnidad();
        } catch (Exception e) {
            System.out.println("Error en metodo init SubtemaMb: "+e.getMessage());
        }
        
       
    }

   
    public presentatallerMB(){
        System.out.println("presentaTallerMb Constructor");
    }
    
    public String Regresar(Long destrezaId){
        System.out.println("Regresar en presentaTallerMB destreza_Id: " +destrezaId+
                           " unidadId: "+unidad_Id);
        String IdC1 = "";
        String IdC2 = "";
        try {
            IdC1 = StringEncript.encrypt(key, iv, destrezaId.toString()); 
            IdC2 = StringEncript.encrypt(key, iv, unidad_Id.toString());
        } catch (Exception ex) {
            Logger.getLogger(DestrezaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), tipo);
        return  "/estudiante/pag_subtemas.jsf?faces-redirect=true&Id1="+IdC1+"&Id2="+IdC2; 
    }

    /*Metodo obtiene informacion de Actividad Temporal.-*/
    public void obtieneActivTmp(Long usuario_Cod){
        
        temp_Activ = servicioAplicacion.consultaTmpActividad(usuario_Cod, "");
        System.out.println("Size temp_Activ: "+temp_Activ.size());
        if ( temp_Activ.size() != 0 ){
            
            for (int i = 0; i < temp_Activ.size(); i++) {
                System.out.println("temp_act_tipo: "+temp_Activ.get(i).getTempActivTipo());
                if ( temp_Activ.get(i).getTempActivTipo().equals(tipo) ){
                   Subtema_Id = temp_Activ.get(i).getTempActivIdRef();
                    System.out.println("presentaTallerMB Subtema_Id1 : "+Subtema_Id); 
                }
                else{
                    if ( temp_Activ.get(i).getTempActivTipo().equals("D") ){
                        destrezaId =  temp_Activ.get(i).getTempActivIdRef();
                        System.out.println("presentaTallerMB destrezaId : "+destrezaId); 
                    }
                }  
                
            }
            
        }  
        System.out.println("presentaTallerMB Subtema_Id2 : "+Subtema_Id); 
        if ( Subtema_Id == null || Subtema_Id == Long.valueOf("0")) {
            FacesContext facesContext = FacesContext. getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            Map params = externalContext.getRequestParameterMap();
            try {
                Integer SubtemaId = new Integer((String) params.get("SubtemaId" ));
                Subtema_Id = Long.valueOf(SubtemaId);
            } catch (Exception e) {
                System.out.println("Error al obtener SubtemaId: "+ e.getMessage());
            }
            System.out.println("presentaTallerMB Subtema_Id2: "+Subtema_Id);
            
            TblTempactiv tmp_Activ = new TblTempactiv();
            tmp_Activ.setTempActivUsrCod(usuario_Cod);
            tmp_Activ.setTempActivIdRef(Subtema_Id);
            tmp_Activ.setTempActivTipo(tipo);
            tmp_Activ.setTempActivSts("A");
            servicioAplicacion.ingresaTempActiv(tmp_Activ);
            
        }
        
        System.out.println("presentaTallerMB Subtema_Id3: "+Subtema_Id);
    }
    
    /*Metodo obtiene Unidad.-*/
    public void obtieneUnidad(){
        if(tallerList.isEmpty()){
            //Si no existe Taller Registrado elimina Id Temporal SubTema.-
            servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), tipo);
            
            //Si no existe Taller Registrado elimina Id Temporal dESTREZA.-
            //servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), "D");
        }
        else{
           for (int i = 0; i < tallerList.size(); i++) {
            unidad_Id = tallerList.get(i).getUnidad_id();
            
        }  
        }
            
       
    }

    /*Metodo Registra Taller.-*/
    public void ingresaTaller(){
        
        System.out.println("**************Ingresa ingresaTaller*****************");
        boolean valido = validaIngresoTaller();
        int contador = 0;
        if (valido) {
            
            TblUsuarios usuario = new TblUsuarios();
            usuario.setId(usuarioSession.getUsuarioSession().getId());

            System.out.println("ingresaTaller UsuarioId: "+usuarioSession.getUsuarioSession().getId());
            Long unidad_id = Long.valueOf("0");

            for (TallerList taller : this.tallerList) {

                TblPresentaciontaller presentaciontaller = new TblPresentaciontaller();
                presentaciontaller.setTallerId(taller.getTaller_id());

                System.out.println("ingresaTaller TallerId: "+taller.getTaller_id()+
                            " Actividad_id: "+taller.getActividad_id()+
                            " SubtemaId: "+Subtema_Id
                        );

                TblActividad activid = new TblActividad();
                activid.setActividadId(taller.getActividad_id());

                unidad_id   = taller.getUnidad_id();
                String tipo_Pregunta = taller.getTipo_pregunta();

                //Inactiva detalle de resultados del taller.-
                if(contador == 0){
                    servicioAplicacion.inactivaResultadoTaller(taller.getTaller_id(), Subtema_Id);
                }
                
                Long IdRespSelec = taller.getIdRespuestSelec();
                System.out.println("Id de Respuesta Seleccionada: "+IdRespSelec);
                
                contador += 1;
                
                for(RespuestasList resp : taller.getRespuestas()){

                    TblRespuesta respuest = new TblRespuesta();
                    respuest.setRespuestaId(resp.getId());

                    System.out.println("ingresaTaller tipo_Pregunta:" +tipo_Pregunta+
                            " RespuestaId: "+resp.getId()+
                            " RespuestaDesc: "+resp.getDescripcion()+
                            " Respuesta: "+resp.getStatus());

                    TblResultadotaller resultado = new TblResultadotaller();
                    resultado.setIdTaller(presentaciontaller);
                    resultado.setIdActividad(activid);
                    resultado.setIdRespuesta(respuest);
                    resultado.setIdUsuario(usuario);

                    if( tipo_Pregunta.equals("L") || tipo_Pregunta.equals("U") || tipo_Pregunta.equals("M") )
                    {   
                        if (resp.getId().equals(IdRespSelec))
                        {
                            resultado.setResultadoRespuesta('S');
                        }
                        else
                        {
                            resultado.setResultadoRespuesta('N');
                        } 
                    }
                    else
                    {
                       if( tipo_Pregunta.equals("R") ) 
                       {
                           System.out.println("Seleccionada: "+resp.getStatus().toUpperCase()+
                                    " Descripcion: "+resp.getDescripcion().toUpperCase());
                           if ( resp.getStatus().toUpperCase().trim().equals(resp.getDescripcion().toUpperCase().trim()) )
                           {
                               resultado.setResultadoRespuesta('S');
                                System.out.println("Seleccionada: "+resp.getStatus().toUpperCase()+
                                    " Descripcion: "+resp.getDescripcion().toUpperCase()+
                                    " SI ");
                           } 
                           else
                           {
                               resultado.setResultadoRespuesta('N');
                               System.out.println("Seleccionada: "+resp.getStatus().toUpperCase()+
                                    " Descripcion: "+resp.getDescripcion().toUpperCase()+
                                    " NO ");
                           } 
                       }
                    }    
                    resultado.setResultadoInstim(fechaActual);
                    resultado.setResultadoInsusr(IdUserSession);
                    resultado.setResultadoUpdusr(" ");
                    resultado.setResultadoDltusr(" ");
                    resultado.setResultadoSts("A");
                    
                    TblSubtema subtema = new TblSubtema();
                    subtema.setSubtemaId(Subtema_Id);
                    resultado.setSubtemaId(subtema);
                    
                    //Registra resultado al Detalle.-
                    servicioAplicacion.ingresaResultadoTaller(resultado);

                }

            }

            System.out.println("Unidad Id: "+unidad_id);

            resultado = servicioAplicacion.obtieneResultadosporUnidad( usuarioSession.getUsuarioSession().getId(), unidad_id , Subtema_Id );  
            servicioAplicacion.ingresaResultado(resultado);

            servicioAplicacion.eliminaTempActiv(usuarioSession.getUsuarioSession().getId(), tipo);
            
            tallerList          =   servicioAplicacion.consultaTaller(usuarioSession.getUsuarioSession().getId(), destrezaId,  Subtema_Id );
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Taller se ha registrado con éxito.", " " );
            FacesContext.getCurrentInstance().addMessage(null, message);
            
        }
        else
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor, seleccione las respuestas de cada actividad.", " " );
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("Por favor, seleccione las respuestas de cada actividad.");
            
        }
            
    }
    
    /*Metodo Valida ingreso Taller.-*/
    public boolean validaIngresoTaller()
    {
        boolean valido = false;
        
        for (TallerList taller : this.tallerList) {
            
            String tipo_Pregunta = taller.getTipo_pregunta();
            System.out.println("presentaTallerMB tipo_Pregunta: "+tipo_Pregunta );
            
            Long respSelec = Long.valueOf("0");
            respSelec = taller.getIdRespuestSelec();
            System.out.println("presentaTallerMB RespuestaSeleccionada:"+ respSelec);
            
            if( respSelec == Long.valueOf("0") ){
                valido = false;
            }    
            else{
                valido = true;
            }
            /*for(RespuestasList resp : taller.getRespuestas()){
                
                System.out.println("presentaTallerMB Resp: "+resp.getStatus() );
                
                if( tipo_Pregunta.equals("U") || tipo_Pregunta.equals("M") )
                {   
                    if (resp.getStatus().equals("true") )
                    {
                        valido = true;
                    } 
                }
                else
                {
                    System.out.println("presentaTallerMB tipo_Pregunta: "+tipo_Pregunta+
                               " Resp: "+resp.getStatus() );
                    if( tipo_Pregunta.equals("R") ) 
                   {
                      
                       if ( ! resp.getStatus().equals("") )
                       {
                           valido = true;
                       } 
                   }
                } 
            }*/
        }
        return valido;
    } 
    
    /**Metodo obtiene Usuario de Session*/
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }

    
    public List<TallerList> getTallerList() {
        return tallerList;
    }

    public void setTallerList(List<TallerList> tallerList) {
        this.tallerList = tallerList;
    }

    public List<TblRespuesta> getRespuestList() {
        return respuestList;
    }

    public void setRespuestList(List<TblRespuesta> respuestList) {
        this.respuestList = respuestList;
    }

    public TblRespuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(TblRespuesta respuesta) {
        this.respuesta = respuesta;
    }

    public TblResultado getResultado() {
        return resultado;
    }

    public void setResultado(TblResultado resultado) {
        this.resultado = resultado;
    }

    public BigInteger getPct_Aprobado() {
        return pct_Aprobado;
    }

    public void setPct_Aprobado(BigInteger pct_Aprobado) {
        this.pct_Aprobado = pct_Aprobado;
    }

    public Long getSubtema_Id() {
        return Subtema_Id;
    }

    public void setSubtema_Id(Long Subtema_Id) {
        this.Subtema_Id = Subtema_Id;
    }

    public Long getUnidad_Id() {
        return unidad_Id;
    }

    public void setUnidad_Id(Long unidad_Id) {
        this.unidad_Id = unidad_Id;
    }

    public List<TblTempactiv> getTemp_Activ() {
        return temp_Activ;
    }

    public void setTemp_Activ(List<TblTempactiv> temp_Activ) {
        this.temp_Activ = temp_Activ;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getDestrezaId() {
        return destrezaId;
    }

    public void setDestrezaId(Long destrezaId) {
        this.destrezaId = destrezaId;
    }

    public Long getUsuario_Cod() {
        return usuario_Cod;
    }

    public void setUsuario_Cod(Long usuario_Cod) {
        this.usuario_Cod = usuario_Cod;
    }

   
    
}
