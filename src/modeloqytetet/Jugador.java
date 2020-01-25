/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
 /*
 * @author juanemartinez
 */
public class Jugador implements Comparable{ 
    private final int MAX_CASAS = 4;
    private final int MAX_HOTELES = 4;
    private boolean encarcelado;
    private String nombre;
    private int saldo;
    
    private ArrayList<TituloPropiedad> propiedades = new ArrayList<>();
    private Casilla casillaActual;
    private Sorpresa cartaLibertad;
    
    public Jugador(String nombre){
        this.encarcelado = false;
        this.saldo = 7500;
        this.nombre = nombre; 
    }
    
    protected Jugador ( Jugador otroJugador ){
        this.cartaLibertad = otroJugador.cartaLibertad;
        this.casillaActual = otroJugador.casillaActual;
        this.encarcelado = otroJugador.encarcelado;
        this.nombre = otroJugador.nombre;
        this.propiedades = otroJugador.propiedades;
        this.saldo = otroJugador.saldo;
        
    }
    
    boolean cancelarHipoteca (TituloPropiedad titulo){
        boolean cancelada;
        
        int costeCancelar = titulo.calcularCosteCancelar();
        
        if (this.tengoSaldo(costeCancelar)){
            titulo.cancelarHipoteca();
            cancelada = true; 
            this.modificarSaldo(-costeCancelar);
        }
        else
            cancelada = false;
        
        return cancelada;
    }
    
    boolean comprarTituloPropiedad (){
       
        boolean comprado = false;
        
        int costeCompra = this.casillaActual.getCoste();
        
        if (costeCompra < this.saldo){
            ((Calle)casillaActual).asignarPropietario(this);
            comprado = true;
            this.propiedades.add(((Calle)casillaActual).getTitulo());
            this.modificarSaldo(-costeCompra);
        }
        
        return comprado;
    }

    
    int cuantasCasasHotelesTengo (){
          int i, numTotal=0;
          
          for (i=0; i < propiedades.size(); i++){
              numTotal += propiedades.get(i).getNumCasas();
              numTotal += propiedades.get(i).getNumHoteles();
          }
          
          return numTotal;
      }
      
    boolean deboPagarAlquiler (){
        boolean deboPagar = false;
        
        TituloPropiedad titulo = this.casillaActual.getTitulo();
        boolean esDeMiPropiedad = this.esDeMiPropiedad(titulo);
        
        if (!esDeMiPropiedad){
            boolean tienePropietario = titulo.tengoPropietario();
            
            if (tienePropietario){
                boolean encarcelado = titulo.propietarioEncarcelado();
                
                if (!encarcelado){
                    boolean estaHipotecada = titulo.getHipotecada();
                    
                    if (!estaHipotecada)
                        deboPagar = true;
                }
            }
        }
        
        return (deboPagar);
    }
        
      
    Sorpresa devolverCartaLibertad (){
        Sorpresa a_devolver = null;
        
        if (this.tengoCartaLibertad()){
            a_devolver = this.getCartaLibertad();
            this.cartaLibertad = null;
        }
        
        return a_devolver;
      }
    
    boolean edificarCasa (TituloPropiedad titulo){
        
        boolean edificada = false;
        
        int numCasas = titulo.getNumCasas();
        
        if (this.puedoEdificarCasa(titulo)){
                titulo.edificarCasa();
                this.modificarSaldo(-titulo.getPrecioEdificar());
                edificada = true;
        }
        
        return edificada;
    }
    
    boolean edificarHotel (TituloPropiedad titulo){
        boolean edificada = false;
        
        int numHoteles = titulo.getNumHoteles();
        
        if (this.puedoEdificarHotel(titulo)){
                titulo.edificarHotel();
                this.modificarSaldo(-titulo.getPrecioEdificar());
                edificada = true;
        }
        
        return edificada;
        
    }
    
    private void eliminarDeMisPropiedades (TituloPropiedad titulo){
        
        this.propiedades.remove(titulo);
        titulo.setPropietario(null);
    }
    
    private boolean esDeMiPropiedad (TituloPropiedad titulo){
        boolean cierto = false;
        
        for (int i=0; i<propiedades.size(); i++){
            if (propiedades.get(i) == titulo){
                cierto = true;
            }
        }
        
        return cierto;
    }
//    boolean estoyEnCalleLibre();
    
    Sorpresa getCartaLibertad (){
        return this.cartaLibertad;
    }
    
    Casilla getCasillaActual (){
        return this.casillaActual;
    }
    
    boolean getEncarcelado (){
        return this.encarcelado;
    }
    
    String getNombre (){
        return this.nombre;
    }
    
    ArrayList<TituloPropiedad> getPropiedades (){
        return this.propiedades;
    }
    
    public int getSaldo (){
        return this.saldo;
    }
    
    boolean hipotecarPropiedad (TituloPropiedad titulo){
        int costeHipoteca = titulo.hipotecar();
        this.modificarSaldo(costeHipoteca);
        
        return (true);
    }
    
    void irACarcel (Casilla casilla){
        boolean encarcelado = true;
        
        this.setCasillaActual(casilla);
        this.setEncarcelado(encarcelado);
    }
    
    int modificarSaldo (int cantidad){
        this.saldo += cantidad;
        
        return (this.saldo);
    }
    int obtenerCapital (){
        int i, precioPropiedades = 0;
        
        for (i=0; i<this.propiedades.size(); i++){
            precioPropiedades += propiedades.get(i).getPrecioCompra() +
                    ((propiedades.get(i).getNumCasas() + propiedades.get(i).getNumHoteles())
                      * propiedades.get(i).getPrecioEdificar());
            
            if (propiedades.get(i).getHipotecada())
                precioPropiedades -= propiedades.get(i).getHipotecaBase();
            
        }
        
        return (precioPropiedades + this.getSaldo());
    }
    ArrayList<TituloPropiedad> obtenerPropiedades (boolean hipotecada){
        ArrayList<TituloPropiedad> a_devolver = new ArrayList<>();
        
        for (int i=0; i<propiedades.size(); i++){
            if (propiedades.get(i).getHipotecada() == hipotecada)
                a_devolver.add(propiedades.get(i));
         
        }
        
        return a_devolver;
    }

    void pagarAlquiler (){
        
        int costeAlquiler = ((Calle)this.casillaActual).pagarAlquiler();
        this.modificarSaldo(-costeAlquiler);
    }
    
    protected void pagarImpuesto (){
        int saldo_final = this.getSaldo();
        saldo_final -= this.getCasillaActual().getCoste();
        
        this.saldo = saldo_final;
    }
    
    void pagarLibertad (int cantidad){
        
        boolean tengoSaldo = this.tengoSaldo(cantidad);
        
        if (tengoSaldo){
            this.setEncarcelado(false);
            this.modificarSaldo(-cantidad);
        }
    }
    
    void setCartaLibertad (Sorpresa carta){
        this.cartaLibertad = carta;
    }
    
    void setCasillaActual (Casilla casilla){
        this.casillaActual = casilla;
    }
    
    void setEncarcelado (boolean encarcelado){
        this.encarcelado = encarcelado;
    }
    
    boolean tengoCartaLibertad (){
        return (cartaLibertad != null);
    }
    protected boolean tengoSaldo (int cantidad){
        return (this.getSaldo() > cantidad);
    }
    
    boolean venderPropiedad (Casilla casilla){
        
        TituloPropiedad titulo = casilla.getTitulo();
        this.eliminarDeMisPropiedades(titulo);
        int precioVenta = titulo.calcularPrecioVenta();
        this.modificarSaldo(precioVenta);
        
        return (true);
    }
    
    protected Especulador convertirme (int fianza){
        Jugador jug = new Jugador (this);
        
        Especulador espec = new Especulador (jug, fianza);
        
        return espec;
    }
    
    protected boolean deboIrACarcel(){
        return (!this.tengoCartaLibertad());
    }
    
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean puede_edificar = false;
        if (titulo.getNumCasas() < MAX_CASAS){
            if (this.tengoSaldo(titulo.getPrecioCompra()))
                puede_edificar = true;
        }
        
        return puede_edificar;
    }
    
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        boolean puede_edificar = false;
        if (titulo.getNumHoteles() < MAX_HOTELES){
            if (this.tengoSaldo(titulo.getPrecioCompra()))
                puede_edificar = true;
        }
        
        return puede_edificar;
    }  
    
    @Override
    public int compareTo(Object otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return (otroCapital - obtenerCapital());
    }
    
    @Override
    public String toString() {
        return "[" + nombre  + "] [ saldo " + saldo + " ] [ encarcelado " + encarcelado + " ] [ capital " + this.obtenerCapital() + "]\ncasillaActual " + casillaActual + "[ cartaLibertad " + cartaLibertad + " ]\n" + "Propiedades: \n" + this.getPropiedades() + "\n";
    }

        
}
