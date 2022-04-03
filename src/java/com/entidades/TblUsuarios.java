
package com.entidades;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.dominio.BeanDominio;

/**
 *
 * @author jespinoza
 */

@Entity
@Table(name="TBL_USUARIOS")
public class TblUsuarios extends BeanDominio implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//private List<UsuariosRoles> usuariosRoles;
	private String usuario;
	private String clave;
        private String clave_Usr; 
        private String nomApe; 
        
	@Column(name ="USUARIO")
	public String getUsuario() 
        {
		return usuario;
	}
	public void setUsuario(String usuario) 
        {
		this.usuario = usuario;
	}
	
	@Column(name="CLAVE")
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
        
        @Column(name="NOMAPE") 
        public String getNomApe() 
        {
            return nomApe;
        }

        public void setNomApe(String nomApe) 
        {
             this.nomApe = nomApe;
        }
        
  
        
        //@OneToMany(mappedBy="usuario", fetch=FetchType.EAGER)
        
        /*@OneToMany(targetEntity = UsuariosRoles.class)
	public List<UsuariosRoles> getUsuariosRoles() 
        {
		return usuariosRoles;
	}


	public void setUsuariosRoles(List<UsuariosRoles> usuariosRoles) 
        {
		this.usuariosRoles = usuariosRoles;
	}*/
        
        @Column(name="CLAVE_USR")
        public String getClave_Usr() 
        {
            return clave_Usr;
        }

        public void setClave_Usr(String clave_Usr) 
        {
            this.clave_Usr = clave_Usr;
        }
        
	
}
