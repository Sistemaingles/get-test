package com.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BeanDominio implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	private Long  id;
	private String estado;
	private String observacion;
	private Date fchIns;
        private String usrIns;
	private Date fchUpd;
        private String usrUpd;
        private Date fchDlt;
	private String usrDlt;
	
	@Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name="ID",nullable = false)
	public Long getId() 
        {
		return id;
	}
	public void setId(Long id) 
        {
		this.id = id;
	}
	
	@Column(name="ESTADO")
	public String getEstado() 
        {
		return estado;
	}
	public void setEstado(String estado) 
        {
		this.estado = estado;
	}
	
	@Column(name="OBSERVACION")
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
        
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name="FECHA_INSERCION")
        public Date getFchIns() 
        {
            return fchIns;
        }
 
        public void setFchIns(Date fchIns) 
        {
            this.fchIns = fchIns;
        }
        
        @Column(name="USUARIO_INSERCION")
        public String getUsrIns() 
        {
            return usrIns;
        }

        public void setUsrIns(String usrIns) 
        {
            this.usrIns = usrIns;
        }
        
        /*@Temporal(TemporalType.TIMESTAMP)
        @Column(name="FECHA_UPD")
        public Date getFchUpd() 
        {
            return fchUpd;
        }

        public void setFchUpd(Date fchUpd) 
        {
            this.fchUpd = fchUpd;
        }

        @Column(name="USR_UPD")
        public String getUsrUpd() 
        {
            return usrUpd;
        }

        public void setUsrUpd(String usrUpd) 
        {
            this.usrUpd = usrUpd;
        }
    
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name="FECHA_DLT")
        public Date getFchDlt() 
        {
            return fchDlt;
        }

        public void setFchDlt(Date fchDlt) 
        {
            this.fchDlt = fchDlt;
        }
        @Column(name="USR_DLT")
        public String getUsrDlt() 
        {
            return usrDlt;
        }

        public void setUsrDlt(String usrDlt) 
        {
            this.usrDlt = usrDlt;
        }*/
}
