
package com.servicios.eao;

import com.entidades.TblEstudiante;
import com.entidades.TblUsuarios;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 *
 * @author mespinoza
 */
@Stateless
@LocalBean
public class EstudianteEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;

    /*Metodo ingresa Estudiante*/
    public void ingresarEstudiante(TblEstudiante estudiante)
    {
        try 
        {
            administradorEntidad.persist(estudiante);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaEstudiante en EstudianteEAO: "+e.getMessage());
        }
    }
    
    /*Metodo obtener Estudiante*/
    public List<TblEstudiante> obtenerEstudiante()
    {
        List<Object[]> lResultado    = new ArrayList<Object[]>();
        List<TblEstudiante> listEstudiante     = new ArrayList<TblEstudiante>();
        System.out.println("Inicio obtenerEstudiante en EstudianteEAO");
        
        String sql  = " select p.estudiante_id, p.usuario_cod, p.estudiante_nom , p.estudiante_ape, " +
                      " ( Case p.estudiante_genero" +
                      " When 'M' Then 'Masculino' " +
                      " When 'F' Then 'Femenino' " + 
                      " End ) genero ,"+
                      " p.estudiante_ident, p.estudiante_insusr, p.estudiante_instim, p.estudiante_sts " +
                      "  from tbl_estudiante p "        +
                      " order by p.estudiante_id    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Estudiantes: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblEstudiante estudiante = new TblEstudiante();

            estudiante.setEstudianteId(Long.valueOf(objs[0].toString()));
            TblUsuarios usuario = new TblUsuarios();
            usuario.setId(Long.valueOf(objs[1].toString()));
            estudiante.setUsuario_cod(usuario);
            
            estudiante.setEstudianteNom((String) objs[2]);
            estudiante.setEstudianteApe((String) objs[3]);
            estudiante.setEstudianteGenero((String) objs[4]);            
            estudiante.setEstudianteIdent((String) objs[5]);
            estudiante.setEstudianteInsusr((String) objs[6]);
            estudiante.setEstudianteInstim((Date) objs[7]);
            
            String estudiante_sts = (String) objs[8];
            
            if(estudiante_sts.equals("A")){
                estudiante.setEstudianteSts("ACTIVO");
            }
            else{
               estudiante.setEstudianteSts("INACTIVO"); 
            }
                
  
            listEstudiante.add(estudiante);   
        }
        
        return listEstudiante;
    }
   
    /*Metodo actualizar Estudiante*/
    public void actualizaEstudiante(TblEstudiante estudiante)
    {   
	   try 
            {
                System.out.println("Estudiante a actualizar :"+estudiante.getEstudianteId());
                String sql  = "update tbl_estudiante "
                        + " set estudiante_nom = :nombres ,"
                        + " estudiante_ape = :apellidos ,"
                        + " estudiante_genero = :genero ,"
                        + " estudiante_sts = :estado "
                        + " where estudiante_id = :estudianteId  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("estudianteId", estudiante.getEstudianteId());
                query.setParameter("nombres", estudiante.getEstudianteNom());
                query.setParameter("apellidos", estudiante.getEstudianteApe());
                query.setParameter("genero", estudiante.getEstudianteGenero());
                query.setParameter("estado", estudiante.getEstudianteSts());
                
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al actualizaEstudiante en EstudianteEAO: "+e.getMessage());
            }
    }
    
     /*Metodo elimina Estudiante*/
    public void eliminaEstudiante(TblEstudiante estudiante)
    {
            try 
            {
                System.out.println("Estudiante a eliminar :"+estudiante.getEstudianteId());
                String sql  = "delete from tbl_estudiante where estudiante_id = :estudianteId  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("estudianteId", estudiante.getEstudianteId());
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al eliminaEstudiante en EstudianteEAO: "+e.getMessage());
            }
    }
    
    
}
