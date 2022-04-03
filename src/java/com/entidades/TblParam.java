
package com.entidades;

import com.dominio.BeanDominio;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author User - PC
 */
@Entity
@Table(name="TBL_PARAM")
public class TblParam extends BeanDominio implements Serializable {

    private Long paramNum;
    private String paramChr;
    private Date paramFch;
    private String valor;
    
    @Column(name ="PARAM_NUM" )
    public Long getParamNum() {
        return paramNum;
    }

    public void setParamNum(Long paramNum) {
        this.paramNum = paramNum;
    }
    @Column(name ="PARAM_CHR" )
    public String getParamChr() {
        return paramChr;
    }

    public void setParamChr(String paramChr) {
        this.paramChr = paramChr;
    }
    @Temporal(TemporalType.DATE)
    @Column(name ="PARAM_FCH" )
    public Date getParamFch() {
        return paramFch;
    }

    public void setParamFch(Date paramFch) {
        this.paramFch = paramFch;
    }
    	@Lob
	@Column(name="VALOR")
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
    
}
