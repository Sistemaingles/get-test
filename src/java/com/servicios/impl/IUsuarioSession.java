package com.servicios.impl;

import java.io.Serializable;

public interface IUsuarioSession<T extends Serializable>
{

    public void setUsuarioSession(T usuario);
	
    public T getUsuarioSession();
	
}
