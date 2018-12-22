/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import Model.Linea;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Morpheus
 */
public class Lectura
{
    private static Lectura lectura;

    private Lectura()
    {
    }
    
    public static Lectura getInstance()
    {
        if(lectura == null)
            lectura = new Lectura();
        
        return lectura;
    }
    
    //Obtiene la lista de numeros de un archivo determinado
    public List<String> getLista(String path)
    {
        List<String> numeros = new ArrayList<>();
        BufferedReader br = null;
        
        InputStreamReader reader = null;
        try
        {
            //Abre el archivo
            reader = new InputStreamReader(new FileInputStream(path), "ISO-8859-1");
            br = new BufferedReader(reader);
            
            //Lee cada linea de texto y los acomoda en la lista
            String linea;
            while((linea = br.readLine()) != null && !linea.isEmpty())
                numeros.add(linea.replace(" ", ""));
            
            return numeros;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                reader.close();
                br.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
