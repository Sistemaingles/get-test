
package com.servicios.eao;

import com.entidades.TblDocente;
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
public class DocenteEAO {
    @PersistenceContext(unitName = "project",type = PersistenceContextType.TRANSACTION)
    
    protected EntityManager administradorEntidad;

    /*Metodo ingresa Docente*/
    public void ingresarDocente(TblDocente docente)
    {
        try 
        {
            administradorEntidad.persist(docente);
        } catch (Exception e) 
        {
            System.out.println("Error al ingresaDocente en DocenteEAO: "+e.getMessage());
        }
    }
    
    /*Metodo obtener Paralelos*/
    public List<TblDocente> obtenerDocente()
    {
        List<Object[]> lResultado    = new ArrayList<Object[]>();
        List<TblDocente> listDocente     = new ArrayList<TblDocente>();
        System.out.println("Inicio obtenerDocente en DocenteEAO");
        
        String sql  = " select p.docente_id, p.usuario_cod, p.docente_nombres , p.docente_apellidos, " +
                      " ( Case p.docente_genero" +
                      " When 'M' Then 'Masculino' " +
                      " When 'F' Then 'Femenino' " + 
                      " End ) genero ,"+
                      " p.docente_ident, p.docente_correo, p.docente_insusr, p.docente_instim, p.docente_sts " +
                      "  from tbl_docente p "        +
                      " order by p.docente_id    "  ;
    
        Query query = administradorEntidad.createNativeQuery(sql);
        lResultado = query.getResultList();
        
        System.out.println("Cantidad de Docentes: "+lResultado.size());
        
        for (Object[] objs : lResultado) 
        {
            TblDocente docente = new TblDocente();

            docente.setDocenteId(Long.valueOf(objs[0].toString()));
            TblUsuarios usuario = new TblUsuarios();
            usuario.setId(Long.valueOf(objs[1].toString()));
            docente.setUsuario_cod(usuario);
            
            docente.setDocenteNombres((String) objs[2]);
            docente.setDocenteApellidos((String) objs[3]);
            docente.setDocenteGenero((String) objs[4]);            
            docente.setDocenteIdent((String) objs[5]);
            docente.setDocenteCorreo((String) objs[6]);
            docente.setDocenteInsusr((String) objs[7]);
            docente.setDocenteInstim((Date) objs[8]);
            
            String docente_sts = (String) objs[9];
            
            if(docente_sts.equals("A")){
                docente.setDocenteSts("ACTIVO");
            }
            else{
               docente.setDocenteSts("INACTIVO"); 
            }
                
  
            listDocente.add(docente);   
        }
        
        return listDocente;
    }
   
    /*Metodo actualizar Docente*/
    public void actualizaDocente(TblDocente docente)
    {   
	   try 
            {
                System.out.println("Docente a actualizar :"+docente.getDocenteId());
                String sql  = "update tbl_docente "
                        + " set docente_nombres = :docente_nombres ,"
                        + " docente_apellidos = :docente_apellidos ,"
                        + " docente_genero = :docente_genero ,"
                        + " docente_correo = :docente_correo ,"
                        + " docente_sts = :docente_sts "
                        + " where docente_id = :docenteId  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("docenteId", docente.getDocenteId());
                query.setParameter("docente_nombres", docente.getDocenteNombres());
                query.setParameter("docente_apellidos", docente.getDocenteApellidos());
                query.setParameter("docente_genero", docente.getDocenteGenero());
                query.setParameter("docente_correo", docente.getDocenteCorreo());
                query.setParameter("docente_sts", docente.getDocenteSts());
                
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al actualizaDocente en DocenteEAO: "+e.getMessage());
            }
    }
    
     /*Metodo elimina Docente*/
    public void eliminaDocente(TblDocente docente)
    {
            try 
            {
                System.out.println("Docente a eliminar :"+docente.getDocenteId());
                String sql  = "delete from tbl_docente where docente_id = :docenteId  " ;
                
                Query query = administradorEntidad.createNativeQuery(sql);
                query.setParameter("docenteId", docente.getDocenteId());
                query.executeUpdate();

            } catch (Exception e) 
            {
                System.out.println("Error al eliminaDocente en DocenteEAO: "+e.getMessage());
            }
    }
    
    
}
