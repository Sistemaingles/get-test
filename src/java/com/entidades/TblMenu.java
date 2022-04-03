
package com.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.dominio.BeanDominio;

/**
 *
 * @author AMORAN
 */
@Entity
@Table(name="TBL_MENU")
public class TblMenu  extends BeanDominio implements Serializable
{
        private static final long serialVersionUID = 1L;
        
        private String  descripcion;
	private String  imagen;
        private TblMenu  subMenuId;
        private String url;
        
        @Column(name ="DESCRIPCION" )
        public String getDescripcion() 
        {
            return descripcion;
        }

        public void setDescripcion(String descripcion) 
        {
            this.descripcion = descripcion;
        }

        @Column(name ="IMAGEN" )
        public String getImagen() 
        {
            return imagen;
        }

        public void setImagen(String imagen) 
        {
            this.imagen = imagen;
        }

        
        
        @ManyToOne
        @JoinColumn(name = "SUBMENUID")
        
        public TblMenu getSubMenuId() 
        {
            return subMenuId;
        }

        public void setSubMenuId(TblMenu subMenuId) 
        {
            this.subMenuId = subMenuId;
        }
        
        @Column(name = "URL")
        public String getUrl() 
        {
            return url;
        }

        public void setUrl(String url) 
        {
            this.url = url;
        }
        
        /*@Column(name="ESTADO")
	public String getEstado() 
        {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}*/

}
