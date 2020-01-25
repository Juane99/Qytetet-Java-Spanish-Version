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
public class Dado {
    private static final Dado instance = new Dado();
    private int valor;
    
    private Dado(){
        this.valor = 0;
    }
    
    public static Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        int numero = (int) (Math.random() * 6) + 1;
        this.valor = numero;
        
        return numero;
    }
    public int getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return "Dado{" + "valor=" + valor + '}';
    }
    
    
}
