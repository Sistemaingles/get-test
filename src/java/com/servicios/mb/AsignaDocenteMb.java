
package com.servicios.mb;

import com.entidades.TblCurso;
import com.entidades.TblCursoparalelo;
import com.entidades.TblDocente;
import com.entidades.TblDocentecurso;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
@ManagedBean(name = "asignaDocenteMB")
@ViewScoped

/*
    CLASE JAVA MANAGED BEANS DE ASIGNA DOCENTE
*/

public class AsignaDocenteMb implements Serializable{
    
    private static final long   serialVersionUID = 1L;
    private List<TblPeriodo> periodoList;
    private Long periodo_cod;
    private Long curso_cod;
    private Long paralelo_cod;
    private Long docente_cod;
    private List<TblDocente> docenteList;
    private Date fechaActual;
        private String                url = "";
    private List<CursoAsignadoList> detperiodoList;
    private List<CursoAsignadoList> filteredPeriodo;
    private List<CursoAsignadoList> cursoAsignado;
    private List<CursoAsignadoList> paraleloAsignado;
    private List<TblCurso> cursoList;
    
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
        //Obtinene Lista de Periodo Lectivo (AÑO) .-
        periodoList        =   servicioAplicacion.obtenerPeriodo("A");
        Long periodoCod = Long.valueOf("0");
        for (int i = 0; i < periodoList.size(); i++) {
            periodoCod = periodoList.get(i).getPeriodoId();
        }
        
        //Obtiene Lista de Cursos Asignados.-
        cursoList          =   servicioAplicacion.consultaCursosAsignados(periodoCod);
        
        //Obtiene Lista de Docentes.-
        docenteList        =   servicioAplicacion.obtenerDocente();
        
        //Obtiene Lista de Cursos Asignados al Docente.-
        detperiodoList     = servicioAplicacion.consultaPeriodoCursoDocente(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "A");
        
    }
    
     /*FUNCION DETECTA VALOR DE COMBOBOX DE CURSO*/
    public void onCursoChange() 
    {
        try 
        {
            if(curso_cod != 0)
            {
                System.out.println("onCursoChange curso_cod: "+curso_cod);
                paraleloAsignado = servicioAplicacion.consultaPeriodoCurso(periodo_cod, curso_cod ,Long.valueOf("0"), "A");
        
            }
        }
        catch (Exception ex) 
        {
            System.out.println("Error en Metodo onCursoChange AsignaDocenteoMB: "+ex.getMessage());
        }      
    }
    
    /*
        METODO PARA ACTUALIZAR CURSO ASGINADO AL DOCENTE.-
    */
    public void actualizar(RowEditEvent event) {
        try {
            CursoAsignadoList cursoAsignado = new CursoAsignadoList(); 
            cursoAsignado = (CursoAsignadoList) event.getObject();

            System.out.println("Curso Asignado Id a Actualizar: "+cursoAsignado.getCursoasignado_cod());
            servicioAplicacion.actualizaCursoAsignadoDocente(cursoAsignado);
            
            //Obtiene Lista de Cursos Asignados al Docente.-
            detperiodoList      = servicioAplicacion.consultaPeriodoCursoDocente(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "A");
            
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
    METODO PARA INGRESAR CURSO ASIGNADO AL DOCENTE.-
    */
    public void ingresaCursoAsignado()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("**************ingresaPeriodoLectivo <periodoMB>*****************");
        String Estado = "A";
        
        if( (periodo_cod   == null) || (periodo_cod  == 0)   || 
            (curso_cod     == null) || (curso_cod    == 0)   || 
            (paralelo_cod  == null) || (paralelo_cod == 0)   ||
            (docente_cod  == null)  || (docente_cod == 0)  
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
                    System.out.println("Invoca Metodo ingresaCursoAsignado: ");
                    System.out.println("periodo_cod: "+periodo_cod.toString()+
                                        " curso_cod: "+curso_cod.toString()+
                                        " paralelo_cod: "+paralelo_cod.toString()
                                        );
                    guardaCursoDocente( periodo_cod , curso_cod, paralelo_cod, Estado);
                    
                    //Obtiene Lista de Cursos Asignados al Docente.-
                    detperiodoList      = servicioAplicacion.consultaPeriodoCursoDocente(Long.valueOf("0"), Long.valueOf("0"), Long.valueOf("0"), "A");
                    
                    reestableceCursoAsignado();
                }
                else
                {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Docente ya se encuentra asignado al curso.", "" ) );
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
        
        detperiodoList      = servicioAplicacion.consultaPeriodoCursoDocente(periodo_cod, curso_cod, paralelo_cod, "A");
        
    }
    
    // METODO Busca Periodos Lectivos - Cursos Registrados al Docente .-
    public void buscaPeriodoLectivos() 
    {
        //Obtiene Detalle de Cursos Asignados al Docente.-
        periodo_cod  =   Long.valueOf("0");
        paralelo_cod =   Long.valueOf("0");
        
        System.out.println("buscaPeriodoLectivos periodo_cod: "+periodo_cod.toString()+
                                        "curso_cod: "+curso_cod.toString()+
                                        "paralelo_cod: "+paralelo_cod.toString()
                                        );
        
        detperiodoList      = servicioAplicacion.consultaPeriodoCursoDocente(periodo_cod, curso_cod, paralelo_cod, "A");
        
    }
      public void generarReporte() throws JRException, IOException
    {   
        //Obtiene Lista de Estudiantes por Docentes.-
     periodo_cod  =   Long.valueOf("0");
        paralelo_cod =   Long.valueOf("0");
        
        System.out.println("buscaPeriodoLectivos periodo_cod: "+periodo_cod.toString()+
                                        "curso_cod: "+curso_cod.toString()+
                                        "paralelo_cod: "+paralelo_cod.toString()
                                        );
        
        detperiodoList      = servicioAplicacion.consultaPeriodoCursoDocente(periodo_cod, curso_cod, paralelo_cod, "A");
         url = generaPDF("rpt_DOCENTEXCURSO.jasper",detperiodoList);
            
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
    
    public void reestableceCursoAsignado()
    {
        periodo_cod  = null;
        curso_cod    = null;
        paralelo_cod = null;
        docente_cod  = null;                    
    }
    
    /*
        METODO PARA GUARDAR CURSO ASIGNADO AL DOCENTE.-
    */
    public void guardaCursoDocente( Long periodo_cod1 , Long curso_cod1 , Long paralelo_cod1 , String estado)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        TblDocentecurso docenteCurso = new TblDocentecurso();
        fechaActual     =   new Date();
        IdUserSession   =   getUserSessionId();

        /*Obtiene Curso Asignado segun id de periodo, id de curso y id de paralelo.-*/
        cursoAsignado = servicioAplicacion.consultaPeriodoCurso( periodo_cod1, curso_cod1, paralelo_cod1, "A");
        
        Long curso_paraleloId = Long.valueOf("0");
        
        for (int i = 0; i < cursoAsignado.size(); i++) {
            curso_paraleloId = cursoAsignado.get(i).getCursoparalelo_cod();
            
        }
        System.out.println("guardaCursoDocente docente_cod: "+docente_cod+
                    " curso_paraleloId: "+curso_paraleloId
                    );
        
        if ( curso_paraleloId  == null || curso_paraleloId == 0 ){
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Periodo, curso y paralelo seleccionado no han sido asignado.", "" ) );
        }
        else{
            TblCursoparalelo cursoParalelo = new TblCursoparalelo();
            cursoParalelo.setCursoparaleloId(curso_paraleloId);

            TblDocente docente = new TblDocente();
            docente.setDocenteId(docente_cod);
            docenteCurso.setCursoparaleloId(cursoParalelo);
            docenteCurso.setDocenteId(docente);

            docenteCurso.setDocentecursoInsusr(IdUserSession);
            docenteCurso.setDocentecursoInstim(fechaActual);
            docenteCurso.setDocentecursoSts(estado);

            servicioAplicacion.ingresaDocenteCurso(docenteCurso);
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Docente asignado al curso con éxito.", "" ) );
                    
            reestableceCursoAsignado();
        }
        
    }
    
    
    public String getUserSessionId() 
    {
        System.out.println("Usuario de Sesion: " + usuarioSession.getUsuarioSession().getUsuario());
        return usuarioSession.getUsuarioSession().getUsuario();
    }
    
    
    
    
    public List<TblPeriodo> getPeriodoList() {
        return periodoList;
    }

    public void setPeriodoList(List<TblPeriodo> periodoList) {
        this.periodoList = periodoList;
    }

    public Long getPeriodo_cod() {
        return periodo_cod;
    }
   public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setPeriodo_cod(Long periodo_cod) {
        this.periodo_cod = periodo_cod;
    }
    
    public Long getCurso_cod() {
        return curso_cod;
    }

    public void setCurso_cod(Long curso_cod) {
        this.curso_cod = curso_cod;
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

    public List<TblDocente> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<TblDocente> docenteList) {
        this.docenteList = docenteList;
    }

    public Long getDocente_cod() {
        return docente_cod;
    }

    public void setDocente_cod(Long docente_cod) {
        this.docente_cod = docente_cod;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<CursoAsignadoList> getCursoAsignado() {
        return cursoAsignado;
    }

    public void setCursoAsignado(List<CursoAsignadoList> cursoAsignado) {
        this.cursoAsignado = cursoAsignado;
    }

    public List<CursoAsignadoList> getParaleloAsignado() {
        return paraleloAsignado;
    }

    public void setParaleloAsignado(List<CursoAsignadoList> paraleloAsignado) {
        this.paraleloAsignado = paraleloAsignado;
    }

    public List<TblCurso> getCursoList() {
        return cursoList;
    }

    public void setCursoList(List<TblCurso> cursoList) {
        this.cursoList = cursoList;
    }
    
    
    
}
