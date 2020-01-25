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
public class TituloPropiedad {
        
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private boolean hipotecada;
    private String nombre;
    private int numCasas = 0;
    private int numHoteles = 0;
    private int precioCompra;
    private int precioEdificar;
    private Jugador propietario;
    
    

    TituloPropiedad(String nombre, int precioCompra, int alquilerBase, float factorRevalorizacion, int hipotecaBase, int precioEdificar) {
        this.nombre = nombre;
        this.hipotecada = false;
        this.precioCompra = precioCompra;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        this.numHoteles = 0;
        this.numCasas = 0;
        this.propietario = null;
    }

    TituloPropiedad(String nombre, int precioCompra, int alquilerBase, float factorRevalorizacion, int hipotecaBase, int precioEdificar, boolean hipotecada, int numHoteles, int numCasas) {
        this.nombre = nombre;
        this.hipotecada = hipotecada;
        this.precioCompra = precioCompra;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        this.numHoteles = numHoteles;
        this.numCasas = numCasas;
        this.propietario = null;
    }
    
    int calcularCosteCancelar(){
        int coste;
        
        coste = this.calcularCosteHipotecar() + ((this.calcularCosteHipotecar() * 10) / 100);
        return coste;
    }
    
    int calcularCosteHipotecar(){
        
        int costeHipoteca = (int) (this.hipotecaBase + this.numCasas*0.5*this.hipotecaBase + this.numHoteles*this.hipotecaBase);
        
        return costeHipoteca;
    }
    
    int calcularImporteAlquiler(){
        
        int costeAlquiler;
        
        costeAlquiler = this.alquilerBase + (int)(this.numCasas*0.5 + this.numHoteles*2);
        
        return costeAlquiler;
        
    }
    
    int calcularPrecioVenta(){
        
        int precioVenta = (int) (this.precioCompra + (this.numCasas + this.numHoteles) * this.precioEdificar * this.factorRevalorizacion);
        
        return precioVenta;
    }
    
    void cancelarHipoteca(){
    
        this.setHipotecada(false);
    }
    
//    void cobrarAlquiler(int coste);
    void edificarCasa(){
        this.numCasas += 1;
    }
    
    void edificarHotel(){
        
        this.numHoteles += 1;
    }
    
    int getAlquilerBase() {
        return alquilerBase;
    }
    
    float getFactorRevalorizacion() {
        return factorRevalorizacion;
    }
    
    int getHipotecaBase() {
        return hipotecaBase;
    }
    
    boolean getHipotecada() {
        return hipotecada;
    }

    String getNombre() {
        return nombre;
    }
    
    int getNumCasas() {
        return numCasas;
    }
    
    int getNumHoteles() {
        return numHoteles;
    }

    int getPrecioCompra() {
        return precioCompra;
    }

    int getPrecioEdificar() {
        return precioEdificar;
    }
    
    Jugador getPropietario(){
        return this.propietario;
    }
    
    int hipotecar(){
        
        this.setHipotecada(true);
        int costeHipoteca = this.calcularCosteHipotecar();
        
        return costeHipoteca;
    }
    
    int pagarAlquiler(){
        
        int costeAlquiler = this.calcularImporteAlquiler();
        this.propietario.modificarSaldo(costeAlquiler);
        
        return costeAlquiler;
    }
    
    boolean propietarioEncarcelado(){
        boolean esta_encarcelado = false;
        if (this.propietario != null)
            esta_encarcelado = this.propietario.getEncarcelado();
        
        return esta_encarcelado;
        
    }
    
    void setPropietario (Jugador propietario){
        this.propietario = propietario;
    }
    
    boolean tengoPropietario(){
        return (this.propietario != null);
    }

    public void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    @Override
    public String toString() {
        return "{ " + nombre + " [ hipotecada " + hipotecada + " ] [ precioCompra " + precioCompra + " ] [ alquilerBase " + alquilerBase + " ] [ factorRevalorizacion " + factorRevalorizacion + " ] [ hipotecaBase " + hipotecaBase + " ] [ precioEdificar " + precioEdificar + " ] [ numHoteles " + numHoteles + " ] [ numCasas " + numCasas + " ]";
    }

}

