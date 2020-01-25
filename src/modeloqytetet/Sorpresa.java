/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author juanemartinez
 */
public class Sorpresa {
    
    private String texto;
    private TipoSorpresa tipo;
    private int valor;
    
    Sorpresa (String texto, int valor, TipoSorpresa tipo){
        this.texto = texto;
        this.valor = valor;
        this.tipo = tipo;
    }
    
    String getTexto(){
        return texto;
    }
    
    TipoSorpresa getTipo(){
        return tipo;
    }
    
    int getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return "Sorpresa{" + "texto=" + texto + ", tipo=" + tipo + ", valor=" + valor + '}' + "\n";
    }
    

    
}
