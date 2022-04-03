
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
@Table(name = "tbl_resultado")
@NamedQueries({
    @NamedQuery(name = "TblResultado.findAll", query = "SELECT t FROM TblResultado t")})

public class TblResultado implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "resultado_id")
    private Long resultadoId;
    
    @JoinColumn(name = "destreza_Id", referencedColumnName = "destreza_Id")
    @ManyToOne(optional = false)
    private TblDestreza destrezaId;
     
    @JoinColumn(name = "unidad_id", referencedColumnName = "unidad_Id")
    @ManyToOne(optional = false)
    private TblUnidad unidadId;
    
    @JoinColumn(name = "subtema_id", referencedColumnName = "subtema_id")
    @ManyToOne(optional = false)
    private TblSubtema subtemaId;
    
    @JoinColumn(name = "usuario_cod", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TblUsuarios usuarioCod;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "resultado_totRespuesta")
    private float resultadototRespuesta;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "resultado_totAciertos")
    private float resultadototAciertos;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "resultado_porcentaje")
    private float resultadoPorcentaje;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "resultado_intento")
    private int resultadoIntento;
     
    @Basic(optional = false)
    @NotNull
    //@Size(min = 1, max = 20)
    @Column(name = "resultado_estado")
    private String resultadoEstado;
    
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
    private Date resultado_updtim;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "resultado_updusr")
    private String resultado_updusr;
    
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

    public TblResultado() {
    }

    public TblResultado(Long resultadoId) {
        this.resultadoId = resultadoId;
    }


    public Long getResultadoId() {
        return resultadoId;
    }

    public void setResultadoId(Long resultadoId) {
        this.resultadoId = resultadoId;
    }

    public TblUnidad getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(TblUnidad unidadId) {
        this.unidadId = unidadId;
    }

    public TblUsuarios getUsuarioCod() {
        return usuarioCod;
    }

    public void setUsuarioCod(TblUsuarios usuarioCod) {
        this.usuarioCod = usuarioCod;
    }

    public float getResultadototRespuesta() {
        return resultadototRespuesta;
    }

    public void setResultadototRespuesta(float resultadototRespuesta) {
        this.resultadototRespuesta = resultadototRespuesta;
    }

    public float getResultadototAciertos() {
        return resultadototAciertos;
    }

    public void setResultadototAciertos(float resultadototAciertos) {
        this.resultadototAciertos = resultadototAciertos;
    }

    public float getResultadoPorcentaje() {
        return resultadoPorcentaje;
    }

    public void setResultadoPorcentaje(float resultadoPorcentaje) {
        this.resultadoPorcentaje = resultadoPorcentaje;
    }

    public int getResultadoIntento() {
        return resultadoIntento;
    }

    public void setResultadoIntento(int resultadoIntento) {
        this.resultadoIntento = resultadoIntento;
    }

    public String getResultadoEstado() {
        return resultadoEstado;
    }

    public void setResultadoEstado(String resultadoEstado) {
        this.resultadoEstado = resultadoEstado;
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
        if (!(object instanceof TblResultado)) {
            return false;
        }
        TblResultado other = (TblResultado) object;
        if ((this.resultadoId == null && other.resultadoId != null) || (this.resultadoId != null && !this.resultadoId.equals(other.resultadoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entidades.TblResultado[ resultadoId=" + resultadoId + " ]";
    }

    public Date getResultado_updtim() {
        return resultado_updtim;
    }

    public void setResultado_updtim(Date resultado_updtim) {
        this.resultado_updtim = resultado_updtim;
    }

    public String getResultado_updusr() {
        return resultado_updusr;
    }

    public void setResultado_updusr(String resultado_updusr) {
        this.resultado_updusr = resultado_updusr;
    }

    public TblSubtema getSubtemaId() {
        return subtemaId;
    }

    public void setSubtemaId(TblSubtema subtemaId) {
        this.subtemaId = subtemaId;
    }

    public TblDestreza getDestrezaId() {
        return destrezaId;
    }

    public void setDestrezaId(TblDestreza destrezaId) {
        this.destrezaId = destrezaId;
    }
    
    
    
}
