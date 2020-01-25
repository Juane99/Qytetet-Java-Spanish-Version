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
public class OtraCasilla extends Casilla {
    
    private TipoCasilla tipo;
    private TituloPropiedad titulo;
    
    OtraCasilla (int numeroCasilla, TipoCasilla tipo){
        super(numeroCasilla, 0);
        this.tipo = tipo;
        this.titulo = null;
    }
    
    @Override
    protected TipoCasilla getTipo() {
        return tipo;
    }

    @Override
    protected TituloPropiedad getTitulo() {
        return null;
    }

    @Override
    protected boolean soyEdificable(){
        return (false);
    }

    @Override
    public String toString() {
        
        return "[ Casilla " + super.getNumeroCasilla() + " ] [ tipo " + tipo + " ]\n";
    }   
}
