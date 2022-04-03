
package com.servicios;
import com.entidades.TblUsuarios;
import com.servicios.impl.ITestUsuarioSession;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import com.servicios.impl.IUsuarioSession;


/**
 *
 * @author User - AMORAN
 */

@ITestUsuarioSession
@SessionScoped
public class UsuarioSession implements IUsuarioSession<TblUsuarios>, Serializable
{
    
    private static final long serialVersionUID = 1L;
    private TblUsuarios usuario;

    public TblUsuarios getUsuario() 
    {
        return usuario;
    }

    public void setUsuario(TblUsuarios usuario) 
    {
        this.usuario = usuario;
    }

    
    public void setUsuarioSession(TblUsuarios usuario) 
    {
        this.usuario = usuario;
    }
	
    public TblUsuarios getUsuarioSession() 
    {
		
        return this.usuario;
    }


}
