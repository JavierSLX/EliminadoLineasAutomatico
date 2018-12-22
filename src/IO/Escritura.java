/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Morpheus
 */
public class Escritura
{
    private static Escritura escritura;

    private Escritura()
    {
    }
    
    public static Escritura getInstance()
    {
        if(escritura == null)
            escritura = new Escritura();
        
        return escritura;
    }
    
    public boolean borradoCarpeta(File carpeta)
    {
        if(carpeta.exists())
            return carpeta.delete();
        
        return false;
    }
    
    public void crearArchivo(File file, List<String> numeros)
    {
        try
        {
            FileWriter writer = new FileWriter(file, true);
            
            //Coloca el contenido en el archivo
            for(int i = 0; i < numeros.size(); i++)
                writer.write(numeros.get(i) + "\r\n");
            
            writer.close();
            System.out.println("Archivo " + file.getAbsolutePath() + " creado correctamente");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
