/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author juane
 */
abstract class Casilla {
    
    private int numeroCasilla = 0;
    private int coste;
    
    Casilla (int numeroCasilla, int coste){
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
    }
    
    int getCoste() {
        return coste;
    }
    
    int getNumeroCasilla() {
        return numeroCasilla;
    }


    protected abstract TipoCasilla getTipo();

    protected abstract TituloPropiedad getTitulo();
    
    protected abstract boolean soyEdificable();
    
    public void setCoste(int coste){
        this.coste = coste;
    }
   
}
