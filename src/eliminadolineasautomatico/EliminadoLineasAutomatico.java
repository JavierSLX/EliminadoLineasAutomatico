/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eliminadolineasautomatico;

import Controller.LineasDAO;
import IO.Lectura;
import Model.Constantes;
import Model.Linea;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Morpheus
 */
public class EliminadoLineasAutomatico
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        File desktop = new File(System.getProperty("user.home") + "\\Desktop");
        String ruta = rutaArchivo(desktop);
        
        if(ruta != null)
        {
            List<String> numeros = Lectura.getInstance().getLista(ruta);
            
            //Borra las lineas
            File carpeta = new File(desktop.getAbsoluteFile() + "\\" + Constantes.nombreCarpetaResultado);
            try
            {
                LineasDAO.getInstance().borradoDeLineas(carpeta, numeros);
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    //Saca la ruta del archivo del txt
    private static String rutaArchivo(File path)
    {
        //Abre un dialogo de eleccion de archivo para txt
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Texto Plano", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(path);
        
        //Muestra el dialogo
        int result = fileChooser.showOpenDialog(null);
        
        //Si eligio un archivo
        String archivo = null;
        if(result == JFileChooser.APPROVE_OPTION)
            archivo = fileChooser.getSelectedFile().getAbsolutePath();
        
        return archivo;
    }
}
