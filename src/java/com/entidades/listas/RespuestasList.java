
package com.entidades.listas;

import java.io.Serializable;

/**
 *
 * @author jespinoza
 */


public class RespuestasList implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long   id;
    private Long   id_actividad;
    private String descripcion;
    private Character esCorrecta;
    private String status;
    private String tipoPregunta;
    private int orden;
    private boolean checked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_actividad() {
        return id_actividad;
    }

    public void setId_actividad(Long id_actividad) {
        this.id_actividad = id_actividad;
    }

    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(Character esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    
   
}
