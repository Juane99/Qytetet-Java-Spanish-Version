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
public class Calle extends Casilla {
  
    private TituloPropiedad titulo;
    private TipoCasilla tipo;
    
    Calle (int numeroCasilla, TituloPropiedad titulo){
        super(numeroCasilla, titulo.getPrecioCompra());
        this.tipo = TipoCasilla.CALLE;
        this.titulo = titulo;
    }
    
    public void asignarPropietario(Jugador jugador){
        this.titulo.setPropietario(jugador);

    }
    
    
    @Override
    protected TipoCasilla getTipo() {
        return tipo;
    }

    @Override
    protected TituloPropiedad getTitulo() {
        return titulo;
    }
   
    public int pagarAlquiler(){
        
        return (this.titulo.pagarAlquiler());
    }
    
    private void setTitulo(TituloPropiedad titulo) {
        this.titulo = titulo;
    }
    
    @Override
    protected boolean soyEdificable(){
        return (true);
    }

    public boolean tengoPropietario(){
        return (this.titulo.tengoPropietario());
    }

    @Override
    public String toString() {
        return "[  Calle " + super.getNumeroCasilla() + "  ] [ Coste " + super.getCoste() + " ] [ tipo " + tipo + " ] [ titulo " + titulo + " ]\n";

    }   
}

