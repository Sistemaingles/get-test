package com.servicios.impl;

public interface IGuardaUsuarioSession 
{
    public static final String PAGINA_LOGIN="/login.jsf";
    public static final String PAGINA_PRINCIPAL="home.jsf";
    public static final String SEMILLA="SEMILLA";
	
    public boolean usuarioEnSession ();	
	
    public boolean validarSemilla(String semilla);
}
