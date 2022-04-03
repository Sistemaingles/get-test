
package com.entidades.listas;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mespinoza
 */
public class EstudianteList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long estudiantecursoid;
    private Long cursoparaleloid;
    private Long estudiante_id;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private Date fechaNac;
    private String genero;
    private String correo;
    private String curso_nombre;
    private String paralelo_nombre;
    private String estado;

    public Long getEstudiantecursoid() {
        return estudiantecursoid;
    }

    public void setEstudiantecursoid(Long estudiantecursoid) {
        this.estudiantecursoid = estudiantecursoid;
    }

   

    

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public Long getCursoparaleloid() {
        return cursoparaleloid;
    }

    public void setCursoparaleloid(Long cursoparaleloid) {
        this.cursoparaleloid = cursoparaleloid;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    
    public String getGenero() {
        return genero;
    }

    public Long getEstudiante_id() {
        return estudiante_id;
    }

    public void setEstudiante_id(Long estudiante_id) {
        this.estudiante_id = estudiante_id;
    }

    
    
    
    
    
    
}
