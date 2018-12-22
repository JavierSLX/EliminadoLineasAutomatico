/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Morpheus
 */
public class Linea
{
    private String numero;
    private Integer numero_id;
    private Integer activo_id;
    private Integer iccid_id;

    public Linea(String numero, Integer numero_id, Integer activo_id, Integer iccid_id)
    {
        this.numero = numero;
        this.numero_id = numero_id;
        this.activo_id = activo_id;
        this.iccid_id = iccid_id;
    }

    public Linea(String numero)
    {
        this.numero = numero;
    }

    public String getNumero()
    {
        return numero;
    }

    public void setNumero(String numero)
    {
        this.numero = numero;
    }

    public Integer getNumero_id()
    {
        return numero_id;
    }

    public void setNumero_id(Integer numero_id)
    {
        this.numero_id = numero_id;
    }

    public Integer getActivo_id()
    {
        return activo_id;
    }

    public void setActivo_id(Integer activo_id)
    {
        this.activo_id = activo_id;
    }

    public Integer getIccid_id()
    {
        return iccid_id;
    }

    public void setIccid_id(Integer iccid_id)
    {
        this.iccid_id = iccid_id;
    }

    @Override
    public String toString()
    {
        return String.format("Numero: %s, ID: %d, Activo: %d, Iccid: %d", getNumero(), getNumero_id(), getActivo_id(), getIccid_id());
    }
}
