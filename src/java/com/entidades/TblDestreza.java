
package com.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tbl_destreza")
@NamedQueries({
    @NamedQuery(name = "TblDestreza.findAll", query = "SELECT t FROM TblDestreza t"),
    @NamedQuery(name = "TblDestreza.findByDestrezaId", query = "SELECT t FROM TblDestreza t WHERE t.destrezaId = :destrezaId"),
    @NamedQuery(name = "TblDestreza.findByDestrezaNombre", query = "SELECT t FROM TblDestreza t WHERE t.destrezaNombre = :destrezaNombre"),
    @NamedQuery(name = "TblDestreza.findByDestrezaSts", query = "SELECT t FROM TblDestreza t WHERE t.destrezaSts = :destrezaSts")})

public class TblDestreza implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "destreza_Id")
    private Long destrezaId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "destreza_nombre")
    private String destrezaNombre;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "destreza_clase")
    private String destrezaClase;
    
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "destreza_icono")
    private String destrezaIcono;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "destreza_sts")
    private String destrezaSts;


    public TblDestreza() {
    }

    public TblDestreza(Long destrezaId) {
        this.destrezaId = destrezaId;
    }

    public TblDestreza(Long destrezaId, String destrezaNombre, String destrezaSts) {
        this.destrezaId = destrezaId;
        this.destrezaNombre = destrezaNombre;
        this.destrezaSts = destrezaSts;
    }

    public Long getDestrezaId() {
        return destrezaId;
    }

    public void setDestrezaId(Long destrezaId) {
        this.destrezaId = destrezaId;
    }

    public String getDestrezaNombre() {
        return destrezaNombre;
    }

    public void setDestrezaNombre(String destrezaNombre) {
        this.destrezaNombre = destrezaNombre;
    }

    public String getDestrezaClase() {
        return destrezaClase;
    }

    public void setDestrezaClase(String destrezaClase) {
        this.destrezaClase = destrezaClase;
    }

    public String getDestrezaIcono() {
        return destrezaIcono;
    }

    public void setDestrezaIcono(String destrezaIcono) {
        this.destrezaIcono = destrezaIcono;
    }

    public String getDestrezaSts() {
        return destrezaSts;
    }

    public void setDestrezaSts(String destrezaSts) {
        this.destrezaSts = destrezaSts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (destrezaId != null ? destrezaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblDestreza)) {
            return false;
        }
        TblDestreza other = (TblDestreza) object;
        if ((this.destrezaId == null && other.destrezaId != null) || (this.destrezaId != null && !this.destrezaId.equals(other.destrezaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblDestreza[ destrezaId=" + destrezaId + " ]";
    }

    
}
