
package com.servicios.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author mespinoza
 */
public class UtilJsf implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static String guardaBlobEnFicheroTemporal(String path, byte[] bytes, String nombreArchivo) {
        
        String ubicacionImagen = null;
        /*ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path2 = servletContext.getRealPath("") + File.separatorChar + "resources" +
                File.separatorChar + "actividades"  + File.separatorChar + nombreArchivo;*/
        String path2 = path  + nombreArchivo;  
        System.out.println("Path guardaBlobEnFicheroTemporal: "+path2);
        File f = null;
        InputStream in = null;
        
        try {
            f  = new File(path2);
            in = new ByteArrayInputStream(bytes);
            FileOutputStream out = new FileOutputStream(f.getAbsolutePath());
            System.out.println("Path f.getAbsolutePath(): "+f.getAbsolutePath());
            int c=0;
            
            while ((c= in.read()) >= 0) {                
                out.write(c);
            }
            
            out.flush();
            out.close();
            ubicacionImagen = nombreArchivo;
        } catch (Exception e) {
            System.err.println("No se pudo cargar el archivo");
        }
        return ubicacionImagen;
    }
    
 
    
}
