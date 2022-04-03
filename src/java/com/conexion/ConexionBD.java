
package com.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author AMORAN
 */

public class ConexionBD 
{


  /*METODO DE CONEXION A LA BASE DE DATOS*/
  public Connection getConnection()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
    
      return DriverManager.getConnection("jdbc:mysql://localhost:3306/system_web", "neo", "neo123");
      
    }
    catch (Exception e) 
    {
        System.out.println("ERROR getConnection"+e.getMessage());
    }
    return null;
  }
  
}
