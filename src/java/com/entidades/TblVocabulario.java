
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
 * @author jespinoza
 */
@Entity
@Table(name = "tbl_vocabulario")
@NamedQueries({
    @NamedQuery(name = "TblVocabulario.findAll", query = "SELECT t FROM TblVocabulario t")})

public class TblVocabulario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "vocabulario_Id")
    private Long vocabularioId;
    
    @JoinColumn(name = "unidad_Id", referencedColumnName = "unidad_Id")
    @ManyToOne(optional = false)
    private TblUnidad unidadId;
        
    @Basic(optional = false)
    @NotNull
    @Column(name = "vocabulario_descripcion",columnDefinition = "TEXT")
    private String vocabularioDescripcion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "vocabulario_insusr")
    private String vocabularioInsusr;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "vocabulario_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vocabularioInstim;
    
    
    @Size(min = 1, max = 20)
    @Column(name = "vocabulario_updusr")
    private String vocabularioUpdusr;
    
    @Column(name = "vocabulario_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vocabularioUpdtim;
    
    @Size(min = 1, max = 20)
    @Column(name = "vocabulario_dltusr")
    private String vocabularioDltusr;
    
    
    @Column(name = "vocabulario_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vocabularioDlttim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "vocabulario_sts")
    private String vocabularioSts;

    public TblVocabulario() {
    }

    public TblVocabulario(Long vocabularioId) {
        this.vocabularioId = vocabularioId;
    }

    public TblVocabulario(Long vocabularioId, String vocabularioDescripcion, String vocabularioInsusr, Date vocabularioInstim, String vocabularioUpdusr, Date vocabularioUpdtim, String vocabularioDltusr, Date vocabularioDlttim, String vocabularioSts) {
        this.vocabularioId = vocabularioId;
        this.vocabularioDescripcion = vocabularioDescripcion;
        this.vocabularioInsusr = vocabularioInsusr;
        this.vocabularioInstim = vocabularioInstim;
        this.vocabularioUpdusr = vocabularioUpdusr;
        this.vocabularioUpdtim = vocabularioUpdtim;
        this.vocabularioDltusr = vocabularioDltusr;
        this.vocabularioDlttim = vocabularioDlttim;
        this.vocabularioSts = vocabularioSts;
    }

    public Long getVocabularioId() {
        return vocabularioId;
    }

    public void setVocabularioId(Long vocabularioId) {
        this.vocabularioId = vocabularioId;
    }

    public TblUnidad getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(TblUnidad unidadId) {
        this.unidadId = unidadId;
    }
    
    public String getVocabularioDescripcion() {
        return vocabularioDescripcion;
    }

    public void setVocabularioDescripcion(String vocabularioDescripcion) {
        this.vocabularioDescripcion = vocabularioDescripcion;
    }

    public String getVocabularioInsusr() {
        return vocabularioInsusr;
    }

    public void setVocabularioInsusr(String vocabularioInsusr) {
        this.vocabularioInsusr = vocabularioInsusr;
    }

    public Date getVocabularioInstim() {
        return vocabularioInstim;
    }

    public void setVocabularioInstim(Date vocabularioInstim) {
        this.vocabularioInstim = vocabularioInstim;
    }

    public String getVocabularioUpdusr() {
        return vocabularioUpdusr;
    }

    public void setVocabularioUpdusr(String vocabularioUpdusr) {
        this.vocabularioUpdusr = vocabularioUpdusr;
    }

    public Date getVocabularioUpdtim() {
        return vocabularioUpdtim;
    }

    public void setVocabularioUpdtim(Date vocabularioUpdtim) {
        this.vocabularioUpdtim = vocabularioUpdtim;
    }

    public String getVocabularioDltusr() {
        return vocabularioDltusr;
    }

    public void setVocabularioDltusr(String vocabularioDltusr) {
        this.vocabularioDltusr = vocabularioDltusr;
    }

    public Date getVocabularioDlttim() {
        return vocabularioDlttim;
    }

    public void setVocabularioDlttim(Date vocabularioDlttim) {
        this.vocabularioDlttim = vocabularioDlttim;
    }

    public String getVocabularioSts() {
        return vocabularioSts;
    }

    public void setVocabularioSts(String vocabularioSts) {
        this.vocabularioSts = vocabularioSts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vocabularioId != null ? vocabularioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblVocabulario)) {
            return false;
        }
        TblVocabulario other = (TblVocabulario) object;
        if ((this.vocabularioId == null && other.vocabularioId != null) || (this.vocabularioId != null && !this.vocabularioId.equals(other.vocabularioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblVocabulario[ vocabularioId=" + vocabularioId + " ]";
    }
    
}
