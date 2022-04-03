
package com.servicios.mb;

import com.entidades.TblCurso;
import com.entidades.TblCursoparalelo;
import com.entidades.TblParalelo;
import com.entidades.TblPeriodo;
import com.entidades.TblUsuarios;
import com.entidades.listas.CursoAsignadoList;
import com.servicios.impl.ITestUsuarioSession;
import com.servicios.impl.IUsuarioSession;
import com.servicios.impl.IservicioAplicacion;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.event.RowEditEvent;


/**
 *
 * @author jespinoza
 */
@ManagedBean(name = "asignaCursoMB")
@RequestScoped

/*
    CLASE JAVA MANAGED BEANS DE ASIGNA CURSO
*/

public class AsignaCursoMb implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private List<TblPeriodo> periodoList;
    private Long periodo_cod;
    
    private List<TblCurso> cursoList;
    private Long curso_cod;
    
    private List<TblParalelo> paraleloList;
    private Long paralelo_cod;
    
    private Date fechaActual;
            private String                url = "";
    private List<CursoAsignadoList> detperiodoList;
    
    private List<CursoAsignadoList> filteredPeriodo;
    
    @Inject
    @ITestUsuarioSession
    private IUsuarioSession<TblUsuarios> usuarioSession;
    private String IdUserSession;
    
    @EJB
    private IservicioAplicacion  servicioAplicacion;
    
    
    @PostConstruct
    public void init()
    {
        System.out.println("Inicio Periodo MB");
        
        //Obtiene Lista de CursosAsignados.-
        filteredPeriodo      = servicioAplicacion.consultaPeriodoCurso(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "A");
        
        //Obtiene Lista de Periodo Lectivo (AÑO) .-
        periodoList          =   servicioAplicacion.obtenerPeriodo("A");
        
        //Obtiene Lista de Cursos.-
        cursoList          =   servicioAplicacion.obtieneCursos("");
        
        //Obtinene Lista de Paralelos.-
        paraleloList       =   servicioAplicacion.obtieneParalelos("");
        
        //Obtiene Lista de CursosAsignados.-
        detperiodoList      = servicioAplicacion.consultaPeriodoCurso(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "");
        
        
    }

    // METODO Busca Periodos Lectivos - Cursos Registrados .-
    public void buscaPeriodoLectivos() 
    {
        //Obtiene Detalle de Periodos Lectivos - Cursos Registrados.-
        periodo_cod  =   Long.valueOf("0");
        paralelo_cod =   Long.valueOf("0");
        System.out.println("buscaPeriodoLectivos periodo_cod: "+periodo_cod.toString()+
                                        "curso_cod: "+curso_cod.toString()+
                                        "paralelo_cod: "+paralelo_cod.toString()
                                        );
        
        detperiodoList      = servicioAplicacion.consultaPeriodoCurso(periodo_cod, curso_cod, paralelo_cod, "");
        
    }
    
    
         public void generarReporte() throws JRException, IOException
    {   
        //Obtiene Lista de Estudiantes por Docentes.-
     //Obtiene Detalle de Periodos Lectivos - Cursos Registrados.-
        periodo_cod  =   Long.valueOf("0");
        paralelo_cod =   Long.valueOf("0");
        System.out.println("buscaPeriodoLectivos periodo_cod: "+periodo_cod.toString()+
                                        "curso_cod: "+curso_cod.toString()+
                                        "paralelo_cod: "+paralelo_cod.toString()
                                        );
        
        detperiodoList      = servicioAplicacion.consultaPeriodoCurso(periodo_cod, curso_cod, paralelo_cod, "");
           url = generaPDF("rpt_CursoXPeriodo_.jasper",detperiodoList);
            
    }
     
      
        public String generaPDF(String jasper, List<CursoAsignadoList> detperiodoList) throws JRException 
    {
        String  path = "";
      //  ConexionBD con      =   new ConexionBD();
  
        path                =   servicioAplicacion.obtenerParametro("RUTA_TEMP");   
        path                =   path+ jasper;
        System.out.println("RUTA JASPER: "+path);
        
        File file = new File(path);
        
        if (file.exists())
        {
            System.out.println("Encontro Jasper Report Generado");  
            String ruta_tmpo     =   servicioAplicacion.obtenerParametro("RUTA_REPORT");
            String ruta_tmp      =   ruta_tmpo + "Rpt.pdf";
            
            String ruta          =   servicioAplicacion.obtenerParametro("RUTA_RESOURCES");
            String ruta_sello    =   ruta + "logo.PNG" ;  

            File pdf            =   new File(ruta_tmp);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JasperPrint jasperPrint;

            System.out.println("Ruta_sello: "+ruta_sello);
            System.out.println("Usuario: "+getUserSessionId());
        /*    System.out.println("PeriodoId: "+id1);
            System.out.println("CursoId: "+id2);
            System.out.println("DocenteId: "+id3);
            System.out.println("ParaleloId: "+id4);*/
            
         //   Map parametros = new HashMap();
          /*  parametros.put("Periodo","2021");
            parametros.put("ruta_sello",ruta_sello);
            parametros.put("Usuario", getUserSessionId());
            parametros.put("PeriodoId", id1);
            parametros.put("CursoId", id2);
            parametros.put("DocenteId", id3);
            parametros.put("ParaleloId", id4);*/
           
        int year=(Calendar.getInstance().get(Calendar.YEAR));  
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(detperiodoList);
            
             Map<String, Object> parameters = new HashMap<String, Object>();
             parameters.put("Periodo",year);
            parameters.put("ruta_sello",ruta_sello);
            parameters.put("Usuario", getUserSessionId());
        parameters.put("CollectionParam", itemsJRBean);
        
            jasperPrint = JasperFillManager.fillReport(reporte, parameters, new JREmptyDataSource());
            
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File(ruta_tmp));
            exporter.exportReport();
            
            url = pdf.getAbsolutePath();
            
            return pdf.getAbsolutePath();
        }
        else
        {
            System.out.println("reporteEstudianteDocenteMB ocurrió un error al generarPDF: No encontro archivo" );    
            return null;
        }
        
    }
    /*
        METODO PARA ACTUALIZAR CURSO ASIGNADO.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            CursoAsignadoList cursoAsignado = new CursoAsignadoList(); 
            cursoAsignado = (CursoAsignadoList) event.getObject();

            System.out.println("Curso Asignado Id a Actualizar: "+cursoAsignado.getCursoparalelo_cod());
            servicioAplicacion.actualizaCursoAsignado(cursoAsignado);
            
            //Obtiene Lista de Cursos Asignados.-
            detperiodoList      = servicioAplicacion.consultaPeriodoCurso(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "");
            
            FacesContext context = FacesContext.getCurrentInstance();          
            context.addMessage(null, new FacesMessage("Actualización realizada con éxito", "Actualización con éxito"));  
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
    METODO PARA INGRESAR UN PERIODO LECTIVO.-
    */
    public void ingresaCursoAsignado()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaPeriodoLectivo <periodoMB>*****************");
        String Estado = "A";
        
        if( (periodo_cod   == null) || (periodo_cod  == 0)   || 
            (curso_cod     == null) || (curso_cod    == 0)   || 
            (paralelo_cod  == null) || (paralelo_cod == 0)   
          )
          {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor, llene información obligatoria (*) del formulario para continuar.", " " );
                FacesContext.getCurrentInstance().addMessage(null, message);
                System.out.println("No ingreso informacion obligatoria del Formualario: ");
          }
          else
          {
                buscaCursoAsignado() ;
                int cantidad = 0;
                cantidad = detperiodoList.size();
                
                System.out.println("cantidad de Periodos Registrados: "+cantidad);
                if (cantidad == 0)
                {
                    System.out.println("Invoca Metodo Guardar Periodo: ");
                    System.out.println("periodo_cod: "+periodo_cod.toString()+
                                        "curso_cod: "+curso_cod.toString()+
                                        "paralelo_cod: "+paralelo_cod.toString()
                                        );
                    guardaPeriodo( periodo_cod , curso_cod, paralelo_cod, Estado);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Curso asignado con éxito.", "Curso asignado con éxito." ) );
                    
                    //Obtiene Lista de CursosAsignados.-
                    detperiodoList      = servicioAplicacion.consultaPeriodoCurso(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "");
                }
                else
                {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Curso ya se encuentra asignado.", "Curso ya se encuentra asignado." ) );
                }  
          }
    }
    
    // METODO Busca CursosAsignados .-
    public void buscaCursoAsignado() 
    {
        //Obtiene Detalle de Periodos Lectivos - Cursos Registrados.-
        System.out.println("buscaCursoAsignado periodo_cod: "+periodo_cod.toString()+
                                        "curso_cod: "+curso_cod.toString()+
                                        "paralelo_cod: "+paralelo_cod.toString()
                                        );
        
        detperiodoList      = servicioAplicacion.consultaPeriodoCurso(periodo_cod, curso_cod, paralelo_cod, "");
        
    }
    
    public void reestableceCursoAsignado()
    {
           periodo_cod  = null;
           curso_cod    = null;
           paralelo_cod = null;
                    
    }
    
    /*
        METODO PARA GUARDAR PERIODO LECTIVO.-
    */
    public void guardaPeriodo( Long periodo_cod1 , Long curso_cod1 , Long paralelo_cod1 , String estado)
    {
        TblCursoparalelo cursoParalelo = new TblCursoparalelo();
        fechaActual     =   new Date();
        IdUserSession   =   getUserSessionId();
        TblPeriodo periodos = new TblPeriodo();
        periodos.setPeriodoId(periodo_cod1);
        

        TblCurso cursos = new TblCurso();
        cursos.setCursoId(curso_cod1);
        
        TblParalelo paralelos = new TblParalelo();
        paralelos.setParaleloId(paralelo_cod1);
        
        cursoParalelo.setPeriodoId(periodos);
        cursoParalelo.setCursoId(cursos);
        cursoParalelo.setParaleloId(paralelos);
        cursoParalelo.setCursoparaleloInsusr(IdUserSession);
        cursoParalelo.setCursoparaleloInstim(fechaActual);
        cursoParalelo.setCursoparaleloSts(estado);
        
        servicioAplicacion.ingresaPeriodo(cursoParalelo);
    }
    
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }
    
    
    
    
    public List<TblPeriodo> getPeriodoList() {
        return periodoList;
    }
   public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setPeriodoList(List<TblPeriodo> periodoList) {
        this.periodoList = periodoList;
    }

    public Long getPeriodo_cod() {
        return periodo_cod;
    }

    public void setPeriodo_cod(Long periodo_cod) {
        this.periodo_cod = periodo_cod;
    }


 
    public List<TblCurso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<TblCurso> cursoList) {
        this.cursoList = cursoList;
    }

    public Long getCurso_cod() {
        return curso_cod;
    }

    public void setCurso_cod(Long curso_cod) {
        this.curso_cod = curso_cod;
    }

    public List<TblParalelo> getParaleloList() {
        return paraleloList;
    }

    public void setParaleloList(List<TblParalelo> paraleloList) {
        this.paraleloList = paraleloList;
    }

    public Long getParalelo_cod() {
        return paralelo_cod;
    }

    public void setParalelo_cod(Long paralelo_cod) {
        this.paralelo_cod = paralelo_cod;
    }

    public List<CursoAsignadoList> getDetperiodoList() {
        return detperiodoList;
    }

    public void setDetperiodoList(List<CursoAsignadoList> detperiodoList) {
        this.detperiodoList = detperiodoList;
    }

    public List<CursoAsignadoList> getFilteredPeriodo() {
        return filteredPeriodo;
    }

    public void setFilteredPeriodo(List<CursoAsignadoList> filteredPeriodo) {
        this.filteredPeriodo = filteredPeriodo;
    }
    
    
    
}
