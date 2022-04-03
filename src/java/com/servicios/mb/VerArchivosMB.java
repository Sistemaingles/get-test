
package com.servicios.mb;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import com.servicios.impl.IservicioAplicacion;

/**
 *
 * @author jespinoza
 */
@ManagedBean(name = "verArchivosMB")
@RequestScoped

public class VerArchivosMB implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private StreamedContent reporte;
    private String  url = "";

    @EJB
    private IservicioAplicacion  servicioAplicacion;

   
    public StreamedContent getReporte() 
    {
        if (FacesContext.getCurrentInstance().getRenderResponse()) 
        {
            return new DefaultStreamedContent();
        } 
        else 
        {
            url     =   servicioAplicacion.obtenerParametro("RUTA_REPORT");
            url     =   url + "Rpt.pdf";
 
            System.out.println("Url de PDF generado enviado desde getReporte():");
            
            return getPathPDF(url);
        
        }
        

    }
    
    
    public StreamedContent getPathPDF(String url) 
    {
        System.out.println("Ruta de Archivo PDF: : "+url);

        File file         = new File(url);
        
        boolean existeArchivo = file.exists();

        InputStream input;
        
        try 
        {
            if (existeArchivo)
            {
                input = new FileInputStream(file);
                reporte    = new DefaultStreamedContent(input, "application/pdf",file.getName());
                System.out.println("Ingreso a setear Reporte : "+file.getName());
                return reporte;
            }    
            else
            {
                System.out.println("No existe Archivo PDF Generado");
                return null;
            }
    
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(reporteActividadesMB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al Obtener PDF : "+ex.getMessage());
            return null;
        }

    } 
        
    public void setReporte(StreamedContent reporte) 
    {
        this.reporte = reporte;
    }
    


    public String getUrl() 
    {
        return url;
    }

    public void setUrl(String url) 
    {
        this.url = url;
    }
    

    
}
