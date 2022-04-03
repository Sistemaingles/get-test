
package com.entidades.listas;

import java.io.Serializable;
import java.util.Date;



/**
 *
 * @author mespinoza
 */
public class VocabularioList implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Long vocabularioId;
    private Long unidadId;
    private String unidad_nombre;
    private String vocabularioDescripcion;
    private String vocabularioSts;
    private Date vocabularioInstim;
    private String vocabularioInsUsr;
    
    public Long getVocabularioId() {
        return vocabularioId;
    }

    public void setVocabularioId(Long vocabularioId) {
        this.vocabularioId = vocabularioId;
    }

    public Long getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(Long unidadId) {
        this.unidadId = unidadId;
    }

    public String getUnidad_nombre() {
        return unidad_nombre;
    }

    public void setUnidad_nombre(String unidad_nombre) {
        this.unidad_nombre = unidad_nombre;
    }

    public String getVocabularioDescripcion() {
        return vocabularioDescripcion;
    }

    public void setVocabularioDescripcion(String vocabularioDescripcion) {
        this.vocabularioDescripcion = vocabularioDescripcion;
    }

    public String getVocabularioSts() {
        return vocabularioSts;
    }

    public void setVocabularioSts(String vocabularioSts) {
        this.vocabularioSts = vocabularioSts;
    }

    public Date getVocabularioInstim() {
        return vocabularioInstim;
    }

    public void setVocabularioInstim(Date vocabularioInstim) {
        this.vocabularioInstim = vocabularioInstim;
    }

    public String getVocabularioInsUsr() {
        return vocabularioInsUsr;
    }

    public void setVocabularioInsUsr(String vocabularioInsUsr) {
        this.vocabularioInsUsr = vocabularioInsUsr;
    }
    
}
