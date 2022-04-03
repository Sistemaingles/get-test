package com.servicios.mb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

@Named("errorMB")
@SessionScoped
public class ErrorMB extends BeanFormulario implements Serializable
{

        private static final long serialVersionUID = 1L;
        private String errorNameAjax;
        private String errorMsgAjax;
	
        public ErrorMB() 
        {
			
     }
	
        public ErrorMB(String errorName, String errorMsg) 
        {
		this.errorNameAjax = errorName;
		this.errorMsgAjax = errorMsg;
	}
	
	public ErrorMB(String[] errores) 
        {
		this.errorNameAjax = errores[0];
		this.errorMsgAjax = errores[1];
	}
	
	
	/*public void llenarVariablesError(String[] errores) 
        {
		this.errorNameAjax = errores[0];
		this.errorMsgAjax = errores[1];
		RequestContext.getCurrentInstance().update("formError");
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("errorDialog.show();");
	}*/
	
	
	public String getErrorNameAjax() 
        {
		return errorNameAjax;
	}
	public void setErrorNameAjax(String errorNameAjax) 
        {
		this.errorNameAjax = errorNameAjax;
	}
	public String getErrorMsgAjax() 
        {
		return errorMsgAjax;
	}
	public void setErrorMsgAjax(String errorMsgAjax) 
        {
		this.errorMsgAjax = errorMsgAjax;
	}
}
