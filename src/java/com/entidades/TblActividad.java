
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
@Table(name = "tbl_actividad")
@NamedQueries({
    @NamedQuery(name = "TblActividad.findAll", query = "SELECT t FROM TblActividad t"),
    @NamedQuery(name = "TblActividad.findByActividadId", query = "SELECT t FROM TblActividad t WHERE t.actividadId = :actividadId"),
    @NamedQuery(name = "TblActividad.findByActividadInsusr", query = "SELECT t FROM TblActividad t WHERE t.actividadInsusr = :actividadInsusr"),
    @NamedQuery(name = "TblActividad.findByActividadInstim", query = "SELECT t FROM TblActividad t WHERE t.actividadInstim = :actividadInstim"),
    @NamedQuery(name = "TblActividad.findByActividadUpdusr", query = "SELECT t FROM TblActividad t WHERE t.actividadUpdusr = :actividadUpdusr"),
    @NamedQuery(name = "TblActividad.findByActividadUpdtim", query = "SELECT t FROM TblActividad t WHERE t.actividadUpdtim = :actividadUpdtim"),
    @NamedQuery(name = "TblActividad.findByActividadDltusr", query = "SELECT t FROM TblActividad t WHERE t.actividadDltusr = :actividadDltusr"),
    @NamedQuery(name = "TblActividad.findByActividadDlttim", query = "SELECT t FROM TblActividad t WHERE t.actividadDlttim = :actividadDlttim"),
    @NamedQuery(name = "TblActividad.findByActividadSts", query = "SELECT t FROM TblActividad t WHERE t.actividadSts = :actividadSts")})

public class TblActividad implements Serializable {
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "actividad_id")
    private Long actividadId;
    
    @JoinColumn(name = "unidad_id", referencedColumnName = "unidad_Id")
    @ManyToOne(optional = false)
    private TblUnidad unidadId;
    
    @JoinColumn(name = "subtema_id", referencedColumnName = "subtema_id")
    @ManyToOne(optional = false)
    private TblSubtema subtemaId;
    
    @JoinColumn(name = "destreza_Id", referencedColumnName = "destreza_Id")
    @ManyToOne(optional = false)
    private TblDestreza destrezaId;
    
    
    /*
    //@JoinColumn(name = "destreza_Id", referencedColumnName = "destreza_Id")
    @ManyToOne(optional = false)
    private TblDestreza destrezaId;
    
    //@JoinColumn(name = "unidad_id", referencedColumnName = "unidad_Id")
    @ManyToOne(optional = false)
    private TblDestreza unidadId;
    
    //@JoinColumn(name = "subtema_id", referencedColumnName = "subtema_id")
    @ManyToOne(optional = false)
    private TblDestreza unidadId;*/
    
    
    @Basic(optional = false)
    @NotNull
    //@Size(min = 1, max = 500)
    @Column(name = "actividad_descripcion",columnDefinition = "TEXT")
    private String actividadDescripcion;
    
    @Basic(optional = false)
    //@Size(min = 1, max = 500)
    @Column(name = "actividad_pregunta")
    private String actividadPregunta;
    
    @Basic(optional = false)
    //@NotNull
    @Column(name = "actividad_tipopreg")
    private String actividadTipopreg;
    
    @JoinColumn(name = "cursoparalelo_id", referencedColumnName = "cursoparalelo_id")
    @ManyToOne(optional = false)
    private TblCursoparalelo cursoparaleloId;
    
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 500)
    @Column(name = "actividad_archivo")
    private String actividadArchivo;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "actividad_insusr")
    private String actividadInsusr;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "actividad_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actividadInstim;
    
    @Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 20)
    @Column(name = "actividad_updusr")
    private String actividadUpdusr;
    
    @Basic(optional = false)
    //@NotNull
    @Column(name = "actividad_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actividadUpdtim;
    
    @Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 20)
    @Column(name = "actividad_dltusr")
    private String actividadDltusr;
    
    @Basic(optional = false)
    //@NotNull
    @Column(name = "actividad_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actividadDlttim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "actividad_sts")
    private String actividadSts;
  
    public TblActividad() {
    }

    public TblActividad(Long actividadId) {
        this.actividadId = actividadId;
    }


    public Long getActividadId() {
        return actividadId;
    }

    public void setActividadId(Long actividadId) {
        this.actividadId = actividadId;
    }

    public TblDestreza getDestrezaId() {
        return destrezaId;
    }

    public void setDestrezaId(TblDestreza destrezaId) {
        this.destrezaId = destrezaId;
    }

    public TblUnidad getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(TblUnidad unidadId) {
        this.unidadId = unidadId;
    }

    public TblSubtema getSubtemaId() {
        return subtemaId;
    }

    public void setSubtemaId(TblSubtema subtemaId) {
        this.subtemaId = subtemaId;
    }

    
    public String getActividadDescripcion() {
        return actividadDescripcion;
    }

    public void setActividadDescripcion(String actividadDescripcion) {
        this.actividadDescripcion = actividadDescripcion;
    }

    public String getActividadPregunta() {
        return actividadPregunta;
    }

    public void setActividadPregunta(String actividadPregunta) {
        this.actividadPregunta = actividadPregunta;
    }

    public String getActividadTipopreg() {
        return actividadTipopreg;
    }

    public void setActividadTipopreg(String actividadTipopreg) {
        this.actividadTipopreg = actividadTipopreg;
    }

    
    
    public TblCursoparalelo getCursoparaleloId() {
        return cursoparaleloId;
    }

    public void setCursoparaleloId(TblCursoparalelo cursoparaleloId) {
        this.cursoparaleloId = cursoparaleloId;
    }


    
    public String getActividadArchivo() {
        return actividadArchivo;
    }

    public void setActividadArchivo(String actividadArchivo) {
        this.actividadArchivo = actividadArchivo;
    }

    public String getActividadInsusr() {
        return actividadInsusr;
    }

    public void setActividadInsusr(String actividadInsusr) {
        this.actividadInsusr = actividadInsusr;
    }

    public Date getActividadInstim() {
        return actividadInstim;
    }

    public void setActividadInstim(Date actividadInstim) {
        this.actividadInstim = actividadInstim;
    }

    public String getActividadUpdusr() {
        return actividadUpdusr;
    }

    public void setActividadUpdusr(String actividadUpdusr) {
        this.actividadUpdusr = actividadUpdusr;
    }

    public Date getActividadUpdtim() {
        return actividadUpdtim;
    }

    public void setActividadUpdtim(Date actividadUpdtim) {
        this.actividadUpdtim = actividadUpdtim;
    }

    public String getActividadDltusr() {
        return actividadDltusr;
    }

    public void setActividadDltusr(String actividadDltusr) {
        this.actividadDltusr = actividadDltusr;
    }

    public Date getActividadDlttim() {
        return actividadDlttim;
    }

    public void setActividadDlttim(Date actividadDlttim) {
        this.actividadDlttim = actividadDlttim;
    }

    public String getActividadSts() {
        return actividadSts;
    }

    public void setActividadSts(String actividadSts) {
        this.actividadSts = actividadSts;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividadId != null ? actividadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblActividad)) {
            return false;
        }
        TblActividad other = (TblActividad) object;
        if ((this.actividadId == null && other.actividadId != null) || (this.actividadId != null && !this.actividadId.equals(other.actividadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblActividad[ actividadId=" + actividadId + " ]";
    }
    
}
