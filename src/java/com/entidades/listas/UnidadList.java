
package com.entidades.listas;

import java.io.Serializable;

/**
 *
 * @author mespinoza
 */
public class UnidadList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String periodo;
    private Long cursoparaleloid;
    private Long unidad_id;
    private String nombres;
    private String descripcion;
    private String clase;
    private String icono;
    private String curso_nombre;
    private String paralelo_nombre;
    private String estado;

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Long getCursoparaleloid() {
        return cursoparaleloid;
    }

    public void setCursoparaleloid(Long cursoparaleloid) {
        this.cursoparaleloid = cursoparaleloid;
    }

    public Long getUnidad_id() {
        return unidad_id;
    }

    public void setUnidad_id(Long unidad_id) {
        this.unidad_id = unidad_id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcin) {
        this.descripcion = descripcin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
  
    
}
