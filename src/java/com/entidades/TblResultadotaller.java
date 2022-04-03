
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
@Table(name = "tbl_resultadotaller")
@NamedQueries({

@NamedQuery(name = "TblResultadotaller.findAll", query = "SELECT t FROM TblResultadotaller t")})
public class TblResultadotaller implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "resultado_Id")
    private Long resultadoId;
    
    @JoinColumn(name = "taller_Id", referencedColumnName = "taller_id")
    @ManyToOne(optional = false)
    private TblPresentaciontaller idTaller;
    
    @JoinColumn(name = "subtema_id", referencedColumnName = "subtema_id")
    @ManyToOne(optional = false)
    private TblSubtema subtemaId;
     
    @JoinColumn(name = "actividad_id", referencedColumnName = "actividad_id")
    @ManyToOne(optional = false)
    private TblActividad idActividad;
        
    @JoinColumn(name = "respuesta_id", referencedColumnName = "respuesta_id")
    @ManyToOne(optional = false)
    private TblRespuesta idRespuesta;
    
    @JoinColumn(name = "usuario_Cod", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsuarios idUsuario;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "resultado_respuesta")
    private Character resultadoRespuesta;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "resultado_instim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadoInstim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "resultado_insusr")
    private String resultadoInsusr;
    
    @Basic(optional = false)
    @Column(name = "resultado_updtim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadoUpdtim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "resultado_updusr")
    private String resultadoUpdusr;
    
    @Basic(optional = false)
    @Column(name = "resultado_dlttim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultadoDlttim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "resultado_dltusr")
    private String resultadoDltusr;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "resultado_sts")
    private String resultadoSts;

    public TblResultadotaller() {
    }

    public TblResultadotaller(Long resultadoId) {
        this.resultadoId = resultadoId;
    }

    public TblResultadotaller(Long resultadoId, Character resultadoRespuesta, Date resultadoInstim, String resultadoInsusr, Date resultadoUpdtim, String resultadoUpdusr, Date resultadoDlttim, String resultadoDltusr, String resultadoSts) {
        this.resultadoId = resultadoId;
        this.resultadoRespuesta = resultadoRespuesta;
        this.resultadoInstim = resultadoInstim;
        this.resultadoInsusr = resultadoInsusr;
        this.resultadoUpdtim = resultadoUpdtim;
        this.resultadoUpdusr = resultadoUpdusr;
        this.resultadoDlttim = resultadoDlttim;
        this.resultadoDltusr = resultadoDltusr;
        this.resultadoSts = resultadoSts;
    }

    public Long getResultadoId() {
        return resultadoId;
    }

    public void setResultadoId(Long resultadoId) {
        this.resultadoId = resultadoId;
    }

    public TblPresentaciontaller getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(TblPresentaciontaller idTaller) {
        this.idTaller = idTaller;
    }

    public TblActividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(TblActividad idActividad) {
        this.idActividad = idActividad;
    }

    public TblRespuesta getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(TblRespuesta idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public TblUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TblUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    public Character getResultadoRespuesta() {
        return resultadoRespuesta;
    }

    public void setResultadoRespuesta(Character resultadoRespuesta) {
        this.resultadoRespuesta = resultadoRespuesta;
    }

    public Date getResultadoInstim() {
        return resultadoInstim;
    }

    public void setResultadoInstim(Date resultadoInstim) {
        this.resultadoInstim = resultadoInstim;
    }

    public String getResultadoInsusr() {
        return resultadoInsusr;
    }

    public void setResultadoInsusr(String resultadoInsusr) {
        this.resultadoInsusr = resultadoInsusr;
    }

    public Date getResultadoUpdtim() {
        return resultadoUpdtim;
    }

    public void setResultadoUpdtim(Date resultadoUpdtim) {
        this.resultadoUpdtim = resultadoUpdtim;
    }

    public String getResultadoUpdusr() {
        return resultadoUpdusr;
    }

    public void setResultadoUpdusr(String resultadoUpdusr) {
        this.resultadoUpdusr = resultadoUpdusr;
    }

    public Date getResultadoDlttim() {
        return resultadoDlttim;
    }

    public void setResultadoDlttim(Date resultadoDlttim) {
        this.resultadoDlttim = resultadoDlttim;
    }

    public String getResultadoDltusr() {
        return resultadoDltusr;
    }

    public void setResultadoDltusr(String resultadoDltusr) {
        this.resultadoDltusr = resultadoDltusr;
    }

    public String getResultadoSts() {
        return resultadoSts;
    }

    public void setResultadoSts(String resultadoSts) {
        this.resultadoSts = resultadoSts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resultadoId != null ? resultadoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblResultadotaller)) {
            return false;
        }
        TblResultadotaller other = (TblResultadotaller) object;
        if ((this.resultadoId == null && other.resultadoId != null) || (this.resultadoId != null && !this.resultadoId.equals(other.resultadoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblResultadotaller[ resultadoId=" + resultadoId + " ]";
    }

    public TblSubtema getSubtemaId() {
        return subtemaId;
    }

    public void setSubtemaId(TblSubtema subtemaId) {
        this.subtemaId = subtemaId;
    }
    
    
}
