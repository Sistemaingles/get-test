
package com.entidades.listas;

import java.io.Serializable;

/**
 *
 * @author jespinoza
 */
public class PresentaTallerList implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Long taller_id;
    private String periodo;
    private Long unidad_id;
    private String unidad;
    private Long destreza_id;
    private String destreza;
    private String taller_nombre;
    private String taller_descripcion;
    private String curso_nombre;
    private String paralelo_nombre;
    private String estado;

    public Long getTaller_id() {
        return taller_id;
    }

    public void setTaller_id(Long taller_id) {
        this.taller_id = taller_id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Long getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(Long unidad_id) {
        this.unidad_id = unidad_id;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getTaller_nombre() {
        return taller_nombre;
    }

    public void setTaller_nombre(String taller_nombre) {
        this.taller_nombre = taller_nombre;
    }

    public String getTaller_descripcion() {
        return taller_descripcion;
    }

    public void setTaller_descripcion(String taller_descripcion) {
        this.taller_descripcion = taller_descripcion;
    }

    

    public String getCurso_nombre() {
        return curso_nombre;
    }

    public void setCurso_nombre(String curso_nombre) {
        this.curso_nombre = curso_nombre;
    }

    public String getParalelo_nombre() {
        return paralelo_nombre;
    }

    public void setParalelo_nombre(String paralelo_nombre) {
        this.paralelo_nombre = paralelo_nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getDestreza_id() {
        return destreza_id;
    }

    public void setDestreza_id(Long destreza_id) {
        this.destreza_id = destreza_id;
    }

    public String getDestreza() {
        return destreza;
    }

    public void setDestreza(String destreza) {
        this.destreza = destreza;
    }
    
    
}
