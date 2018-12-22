/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import IO.Escritura;
import Model.Constantes;
import Model.Linea;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Morpheus
 */
public class LineasDAO
{
    private static LineasDAO dao;
    private Conexion conexion;

    private LineasDAO() throws SQLException
    {
        conexion = Conexion.getInstance();
    }
    
    public static LineasDAO getInstance() throws SQLException
    {
        if(dao == null)
            dao = new LineasDAO();
        
        return dao;
    }
    
    private Linea getDatosLinea(String numero) throws SQLException
    {
        Linea linea = null;
        
        //Preparamos la consulta
        String query = "SELECT n.digitos AS numero, n.id AS numero_id, IFNULL((SELECT id FROM activado WHERE numero_id = n.id), 0) AS activado_id, IFNULL((SELECT id FROM iccid_numero WHERE numero_id = n.id), 0) AS iccid_id " +
        "FROM numero n WHERE n.digitos = ?";
        PreparedStatement consulta = conexion.getConnection().prepareStatement(query);
        consulta.setString(1, numero);
        
        //Obtenemos el apuntador con los resultados
        ResultSet set = consulta.executeQuery();
        if(set.first())
            linea = new Linea(set.getString("numero"), set.getInt("numero_id"), set.getInt("activado_id"), set.getInt("iccid_id"));
        else
            linea = new Linea(numero);
        
        set.close();
        consulta.close();
        return linea;
    }
    
    private void eliminarLinea(Linea linea) throws SQLException
    {
        if(linea.getIccid_id() > 0)
        {
            String iccid = "DELETE FROM iccid_numero WHERE numero_id = ?";
            PreparedStatement borrado = conexion.getConnection().prepareStatement(iccid);
            borrado.setInt(1, linea.getNumero_id());
            
            //Realiza el borrado del ICCID
            borrado.executeUpdate();
            borrado.close();
        }
        
        String numero = "DELETE FROM numero WHERE id = ?";
        PreparedStatement borrado = conexion.getConnection().prepareStatement(numero);
        borrado.setInt(1, linea.getNumero_id());

        //Realiza el borrado del numero
        borrado.executeUpdate();
        borrado.close();
    }
    
    private List<Linea> getDatosLineas(List<String> numeros) throws SQLException
    {
        List<Linea> lineas = new ArrayList<>();
        for(int i = 0; i < numeros.size(); i++)
        {
            Linea linea = getDatosLinea(numeros.get(i));
            lineas.add(linea);
        }
        
        return lineas;
    }
    
    public void borradoDeLineas(File carpetaResultados, List<String> numeros) throws SQLException
    {
        if(carpetaResultados.exists())
            carpetaResultados.delete();
        
        //Crea la carpeta y los archivos
        carpetaResultados.mkdirs();
        File eliminados = new File(carpetaResultados.getAbsolutePath() + "\\" + Constantes.nombreArchivoEliminados);
        File activados = new File(carpetaResultados.getAbsolutePath() + "\\" + Constantes.nombreArchivoActivos);
        File inexistentes = new File(carpetaResultados.getAbsolutePath() + "\\" + Constantes.nombreArchivoInexistentes);
        
        //Crea las listas
        List<Linea> lineas = getDatosLineas(numeros);
        List<String> lineasEliminadas = new ArrayList<>();
        List<String> lineasActivadas = new ArrayList<>();
        List<String> lineasInexistentes = new ArrayList<>();
        
        //Elimina las lineas
        for(int i = 0; i < lineas.size(); i++)
        {
            Linea linea = lineas.get(i);
            if(linea.getNumero_id() == null)
                lineasInexistentes.add(linea.getNumero());
            else
            {
                if(linea.getActivo_id() > 0)
                    lineasActivadas.add(linea.getNumero());
                else
                {
                    lineasEliminadas.add(linea.getNumero());

                    //Elimina los numeros
                    eliminarLinea(linea);
                }
            }
        }
        
        Escritura escritura = Escritura.getInstance();
        escritura.crearArchivo(activados, lineasActivadas);
        escritura.crearArchivo(inexistentes, lineasInexistentes);
        escritura.crearArchivo(eliminados, lineasEliminadas);
        
        System.out.println("Lineas eliminadas");
    }
}
