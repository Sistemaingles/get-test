
package com.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mespinoza
 */
@Entity
@Table(name = "tbl_presentaciontaller")
@NamedQueries({
@NamedQuery(name = "TblPresentaciontaller.findAll", query = "SELECT t FROM TblPresentaciontaller t")})

public class TblPresentaciontaller implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "taller_id")
    private Long tallerId;
    
    @JoinColumn(name = "destreza_Id", referencedColumnName = "destreza_Id")
    @ManyToOne(optional = false)
    private TblDestreza destrezaId;
     
    @JoinColumn(name = "unidad_Id", referencedColumnName = "unidad_Id")
    @ManyToOne(optional = false)
    private TblUnidad unidadId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "taller_nombre")
    private String tallerNombre;
    
    @Size(max = 500)
    @Column(name = "taller_descripcion")
    private String tallerDescripcion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "taller_insusr")
    private String tallerInsusr;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "taller_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tallerInstim;
    
    @Size(max = 20)
    @Column(name = "taller_updusr")
    private String tallerUpdusr;
    @Column(name = "taller_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tallerUpdtim;
    
    @Size(max = 20)
    @Column(name = "taller_dltusr")
    private String tallerDltusr;
    @Column(name = "taller_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tallerDlttim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "taller_sts")
    private String tallerSts;

    public TblPresentaciontaller() {
    }

    public TblPresentaciontaller(Long tallerId) {
        this.tallerId = tallerId;
    }

    public TblPresentaciontaller(Long tallerId, String tallerNombre, String tallerInsusr, Date tallerInstim, String tallerSts) {
        this.tallerId = tallerId;
        this.tallerNombre = tallerNombre;
        this.tallerInsusr = tallerInsusr;
        this.tallerInstim = tallerInstim;
        this.tallerSts = tallerSts;
    }

    public Long getTallerId() {
        return tallerId;
    }

    public void setTallerId(Long tallerId) {
        this.tallerId = tallerId;
    }

    public String getTallerNombre() {
        return tallerNombre;
    }

    public void setTallerNombre(String tallerNombre) {
        this.tallerNombre = tallerNombre;
    }

    public String getTallerDescripcion() {
        return tallerDescripcion;
    }

    public void setTallerDescripcion(String tallerDescripcion) {
        this.tallerDescripcion = tallerDescripcion;
    }

    public String getTallerInsusr() {
        return tallerInsusr;
    }

    public void setTallerInsusr(String tallerInsusr) {
        this.tallerInsusr = tallerInsusr;
    }

    public Date getTallerInstim() {
        return tallerInstim;
    }

    public void setTallerInstim(Date tallerInstim) {
        this.tallerInstim = tallerInstim;
    }

    public String getTallerUpdusr() {
        return tallerUpdusr;
    }

    public void setTallerUpdusr(String tallerUpdusr) {
        this.tallerUpdusr = tallerUpdusr;
    }

    public Date getTallerUpdtim() {
        return tallerUpdtim;
    }

    public void setTallerUpdtim(Date tallerUpdtim) {
        this.tallerUpdtim = tallerUpdtim;
    }

    public String getTallerDltusr() {
        return tallerDltusr;
    }

    public void setTallerDltusr(String tallerDltusr) {
        this.tallerDltusr = tallerDltusr;
    }

    public Date getTallerDlttim() {
        return tallerDlttim;
    }

    public void setTallerDlttim(Date tallerDlttim) {
        this.tallerDlttim = tallerDlttim;
    }

    public String getTallerSts() {
        return tallerSts;
    }

    public void setTallerSts(String tallerSts) {
        this.tallerSts = tallerSts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tallerId != null ? tallerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPresentaciontaller)) {
            return false;
        }
        TblPresentaciontaller other = (TblPresentaciontaller) object;
        if ((this.tallerId == null && other.tallerId != null) || (this.tallerId != null && !this.tallerId.equals(other.tallerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblPresentaciontaller[ tallerId=" + tallerId + " ]";
    }

    public TblUnidad getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(TblUnidad unidadId) {
        this.unidadId = unidadId;
    }

    public TblDestreza getDestrezaId() {
        return destrezaId;
    }

    public void setDestrezaId(TblDestreza destrezaId) {
        this.destrezaId = destrezaId;
    }
    
    
}
