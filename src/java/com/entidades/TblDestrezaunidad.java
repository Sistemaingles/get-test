
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
 * @author jespinoza
 */
@Entity
@Table(name = "tbl_destrezaunidad")
@NamedQueries({
    @NamedQuery(name = "TblDestrezaunidad.findAll", query = "SELECT t FROM TblDestrezaunidad t")})
public class TblDestrezaunidad implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "destrezaunidad_Id")
    private Long destrezaunidadId;
    
    @JoinColumn(name = "destreza_Id", referencedColumnName = "destreza_Id")
    @ManyToOne(optional = false)
    private TblDestreza destrezaId;
    
    @JoinColumn(name = "unidad_Id", referencedColumnName = "unidad_Id")
    @ManyToOne(optional = false)
    private TblUnidad unidad_Id;
        
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "destrezaunidad_Sts")
    private String destrezaunidadSts;

    public TblDestrezaunidad() {
    }

    public TblDestrezaunidad(Long destrezaunidadId) {
        this.destrezaunidadId = destrezaunidadId;
    }

    public TblDestrezaunidad(Long destrezaunidadId, String destrezaunidadSts) {
        this.destrezaunidadId = destrezaunidadId;
        this.destrezaunidadSts = destrezaunidadSts;
    }

    public TblDestreza getDestrezaId() {
        return destrezaId;
    }

    public void setDestrezaId(TblDestreza destrezaId) {
        this.destrezaId = destrezaId;
    }

    public TblUnidad getUnidad_Id() {
        return unidad_Id;
    }

    public void setUnidad_Id(TblUnidad unidad_Id) {
        this.unidad_Id = unidad_Id;
    }
    
    public Long getDestrezaunidadId() {
        return destrezaunidadId;
    }

    public void setDestrezaunidadId(Long destrezaunidadId) {
        this.destrezaunidadId = destrezaunidadId;
    }

    public String getDestrezaunidadSts() {
        return destrezaunidadSts;
    }

    public void setDestrezaunidadSts(String destrezaunidadSts) {
        this.destrezaunidadSts = destrezaunidadSts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (destrezaunidadId != null ? destrezaunidadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDestrezaunidad)) {
            return false;
        }
        TblDestrezaunidad other = (TblDestrezaunidad) object;
        if ((this.destrezaunidadId == null && other.destrezaunidadId != null) || (this.destrezaunidadId != null && !this.destrezaunidadId.equals(other.destrezaunidadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblDestrezaunidad[ destrezaunidadId=" + destrezaunidadId + " ]";
    }
    
}
