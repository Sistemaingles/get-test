
package com.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mespinoza
 */
@Entity
@Table(name = "tbl_subtema")
@NamedQueries({
    @NamedQuery(name = "TblSubtema.findAll", query = "SELECT t FROM TblSubtema t")})
public class TblSubtema implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "subtema_id")
    private Long subtemaId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "subtema_desc")
    private String subtemaDesc;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "subtema_clase")
    private String subtemaClase;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "subtema_icono")
    private String subtemaIcono;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "subtema_sts")
    private String subtemaSts;
    @JoinColumn(name = "unidad_id", referencedColumnName = "unidad_Id")
    @ManyToOne(optional = false)
    private TblUnidad unidadId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subtemaId")
    private Collection<TblActividad> tblActividadCollection;

    public TblSubtema() {
    }

    public TblSubtema(Long subtemaId) {
        this.subtemaId = subtemaId;
    }

    public TblSubtema(Long subtemaId, String subtemaDesc, String subtemaSts) {
        this.subtemaId = subtemaId;
        this.subtemaDesc = subtemaDesc;
        this.subtemaSts = subtemaSts;
    }

    public Long getSubtemaId() {
        return subtemaId;
    }

    public void setSubtemaId(Long subtemaId) {
        this.subtemaId = subtemaId;
    }

    public String getSubtemaDesc() {
        return subtemaDesc;
    }

    public void setSubtemaDesc(String subtemaDesc) {
        this.subtemaDesc = subtemaDesc;
    }

    public String getSubtemaSts() {
        return subtemaSts;
    }

    public void setSubtemaSts(String subtemaSts) {
        this.subtemaSts = subtemaSts;
    }

    public TblUnidad getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(TblUnidad unidadId) {
        this.unidadId = unidadId;
    }

    public Collection<TblActividad> getTblActividadCollection() {
        return tblActividadCollection;
    }

    public void setTblActividadCollection(Collection<TblActividad> tblActividadCollection) {
        this.tblActividadCollection = tblActividadCollection;
    }

    public String getSubtemaClase() {
        return subtemaClase;
    }

    public void setSubtemaClase(String subtemaClase) {
        this.subtemaClase = subtemaClase;
    }

    public String getSubtemaIcono() {
        return subtemaIcono;
    }

    public void setSubtemaIcono(String subtemaIcono) {
        this.subtemaIcono = subtemaIcono;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subtemaId != null ? subtemaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblSubtema)) {
            return false;
        }
        TblSubtema other = (TblSubtema) object;
        if ((this.subtemaId == null && other.subtemaId != null) || (this.subtemaId != null && !this.subtemaId.equals(other.subtemaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.prueba.TblSubtema[ subtemaId=" + subtemaId + " ]";
    }
    
}
