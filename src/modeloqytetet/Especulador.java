/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author miguemumo99
 */
public class Especulador extends Jugador {
    private final int MAX_CASAS = 8;
    private final int MAX_HOTELES = 8;

    private int fianza;

    Especulador ( Jugador jugador, int fianza){
        super(jugador);
        this.fianza = fianza;
    }
    
    @Override
    protected void pagarImpuesto (){
        int impuesto = this.getCasillaActual().getCoste()/2;
        this.modificarSaldo(-impuesto);
    }
    
    private boolean pagarFianza(){
        boolean tiene_saldo = false;
        if (super.getSaldo() > this.fianza){
            tiene_saldo = true;
            super.modificarSaldo(-fianza);
        }
        
        return tiene_saldo;
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean puede_edificar = true;
        if (titulo.getNumCasas() < MAX_CASAS){
            if (this.getSaldo() > titulo.getPrecioCompra())
                puede_edificar = true;
        }
        
        return puede_edificar;
    }
    
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean puede_edificar = true;
        if (titulo.getNumHoteles() < MAX_HOTELES){
            if (this.getSaldo() > titulo.getPrecioCompra())
                puede_edificar = true;
        }
        
        return puede_edificar;
    }  
    
    @Override
    protected Especulador convertirme (int fianza){
        return this;
    }
    
    @Override
    protected boolean deboIrACarcel(){
        return (super.deboIrACarcel() && !this.pagarFianza());  
    }
    
    @Override
    public String toString(){
        return super.toString() + "Soy especulador";
    }
    
}
