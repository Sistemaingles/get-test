
package com.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mespinoza
 */
@Entity
@Table(name = "tbl_unidad")
@NamedQueries({
    @NamedQuery(name = "TblUnidad.findAll", query = "SELECT t FROM TblUnidad t"),
    @NamedQuery(name = "TblUnidad.findByUnidadId", query = "SELECT t FROM TblUnidad t WHERE t.unidadId = :unidadId"),
    @NamedQuery(name = "TblUnidad.findByUnidadDescripcion", query = "SELECT t FROM TblUnidad t WHERE t.unidadDescripcion = :unidadDescripcion"),
    @NamedQuery(name = "TblUnidad.findByUnidadSts", query = "SELECT t FROM TblUnidad t WHERE t.unidadSts = :unidadSts")})

public class TblUnidad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "unidad_Id")
    private Long unidadId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "unidad_nombre")
    private String unidadNombre;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "unidad_descripcion")
    private String unidadDescripcion;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "unidad_clase")
    private String unidadClase;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "unidad_icono")
    private String unidadIcono;
    
    @JoinColumn(name = "cursoparalelo_id", referencedColumnName = "cursoparalelo_id")
    @ManyToOne(optional = false)
    private TblCursoparalelo cursoparaleloId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "unidad_sts")
    private String unidadSts;
    
    public TblUnidad() {
    }

    public TblUnidad(Long unidadId) {
        this.unidadId = unidadId;
    }

    public TblUnidad(Long unidadId, String unidadDescripcion, String unidadSts) {
        this.unidadId = unidadId;
        this.unidadDescripcion = unidadDescripcion;
        this.unidadSts = unidadSts;
    }

    public Long getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(Long unidadId) {
        this.unidadId = unidadId;
    }

    public String getUnidadNombre() {
        return unidadNombre;
    }

    public void setUnidadNombre(String unidadNombre) {
        this.unidadNombre = unidadNombre;
    }
    
    
    public String getUnidadDescripcion() {
        return unidadDescripcion;
    }

    public void setUnidadDescripcion(String unidadDescripcion) {
        this.unidadDescripcion = unidadDescripcion;
    }

    public String getUnidadSts() {
        return unidadSts;
    }

    public void setUnidadSts(String unidadSts) {
        this.unidadSts = unidadSts;
    }

    public String getUnidadClase() {
        return unidadClase;
    }

    public void setUnidadClase(String unidadClase) {
        this.unidadClase = unidadClase;
    }

    public String getUnidadIcono() {
        return unidadIcono;
    }

    public void setUnidadIcono(String unidadIcono) {
        this.unidadIcono = unidadIcono;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unidadId != null ? unidadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblUnidad)) {
            return false;
        }
        TblUnidad other = (TblUnidad) object;
        if ((this.unidadId == null && other.unidadId != null) || (this.unidadId != null && !this.unidadId.equals(other.unidadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblUnidad[ unidadId=" + unidadId + " ]";
    }

    public TblCursoparalelo getCursoparaleloId() {
        return cursoparaleloId;
    }

    public void setCursoparaleloId(TblCursoparalelo cursoparaleloId) {
        this.cursoparaleloId = cursoparaleloId;
    }
    
  
}
