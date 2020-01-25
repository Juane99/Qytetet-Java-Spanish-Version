/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author juanemartinez
 */
public class Qytetet {
    //CONSTANTES
    public final static int MAX_JUGADORES = 4;
    final static int NUM_SORPRESAS = 12;
    public final static int NUM_CASILLAS = 20;
    final static int PRECIO_LIBERTAD = 200;
    final static int SALDO_SALIDA = 1000;
    
    private static final Qytetet instance = new Qytetet();
    static Dado dado = Dado.getInstance();
    private EstadoJuego estado;
    
    private Sorpresa cartaActual;
    private ArrayList<Jugador> jugadores;
    private Jugador jugadorActual;
    private ArrayList<Sorpresa> mazo;
    private Tablero tablero;
    
    private int indiceJA;   //Para cuandocasilla necesitemos saber sorteo del primero
    private Random r = new Random();

    
    
    private Qytetet(){
        this.cartaActual = null;
        this.dado = getDado();
        this.jugadorActual = null;
        this.jugadores = getJugadores();
        this.indiceJA = 0;
        this.estado = null;
    }
    
    public static Qytetet getInstance(){
        return instance;
    }
    
    void actuarSiEnCasillaEdificable(){
        boolean deboPagar = this.jugadorActual.deboPagarAlquiler();
        
        if (deboPagar){
            this.jugadorActual.pagarAlquiler();
            
            if (this.jugadorActual.getSaldo() <= 0)
                this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        }
        
        Casilla casilla = obtenerCasillaJugadorActual();
        
        boolean tengoPropietario = ((Calle)casilla).tengoPropietario();
        
        if (this.estado != EstadoJuego.ALGUNJUGADORENBANCARROTA){
        
            if (tengoPropietario){
                this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
            }
            else{
                this.setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
            }
        }
    }

    void actuarSiEnCasillaNoEdificable(){
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        Casilla CasillaActual = this.jugadorActual.getCasillaActual();
        
        if (CasillaActual.getTipo() == TipoCasilla.IMPUESTO){
            this.jugadorActual.pagarImpuesto();
        }
        else{
            
            if (CasillaActual.getTipo() == TipoCasilla.JUEZ){
                this.encarcelarJugador();
            }
            else if(CasillaActual.getTipo() == TipoCasilla.SORPRESA){
                this.cartaActual = this.mazo.get(0);
                this.mazo.remove(0);
                this.setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
                
            }
        }
        
    }
    
    public void aplicarSorpresa(){
        
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        if (this.cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL){
            this.jugadorActual.setCartaLibertad(cartaActual);
        }
        else{
            this.mazo.add(this.cartaActual);
            
            if (this.cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR){
                this.jugadorActual.modificarSaldo(this.cartaActual.getValor());
                
                if (this.jugadorActual.getSaldo() < 0)
                    this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
            else if(this.cartaActual.getTipo() == TipoSorpresa.IRACASILLA){
                int valor = this.cartaActual.getValor();
                boolean casillaCarcel = this.tablero.esCasillaCarcel(valor);
                
                if (casillaCarcel == true){
                    this.encarcelarJugador();
                }
                else{
                    this.mover(valor);
                }
            }
            else if (this.cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL){
                int cantidad = this.cartaActual.getValor();
                int numeroTotal = this.jugadorActual.cuantasCasasHotelesTengo();
                this.jugadorActual.modificarSaldo(cantidad*numeroTotal);
                
                if (this.jugadorActual.getSaldo() < 0)
                    this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
            else if (this.cartaActual.getTipo() == TipoSorpresa.PORJUGADOR){
                
                for (int i=0; i< jugadores.size(); i++){
                 
                    Jugador jugador = this.jugadores.get(i);
                    
                    if (jugador != this.jugadorActual){
                        jugador.modificarSaldo(-this.cartaActual.getValor());
                        
                        if (jugador.getSaldo() < 0)
                            this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        
                        this.jugadorActual.modificarSaldo(this.cartaActual.getValor());
                        
                        if (this.jugadorActual.getSaldo() < 0)
                            this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                    }
                }
            }
            
            else if (this.cartaActual.getTipo() == TipoSorpresa.CONVERTIRME){
                
                Especulador espec = new Especulador(this.jugadorActual,this.cartaActual.getValor());
                
               jugadores.set (this.jugadores.indexOf(this.jugadorActual), espec);
               
               this.jugadorActual = espec;
            }
        }
    }
    
    public boolean cancelarHipoteca(int numeroCasilla){
    
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return (this.jugadorActual.cancelarHipoteca(titulo));
    }
    
    
    public boolean comprarTituloPropiedad(){
        
        boolean comprado = this.jugadorActual.comprarTituloPropiedad();
        
        if (comprado)
            this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return comprado;
    }
    
    public boolean edificarCasa(int numeroCasilla){
        
        boolean edificada = false;
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        
        edificada = this.jugadorActual.edificarCasa(titulo);
        
        if (edificada)
            this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return edificada;
    }
    
    public void mitad(){
        this.jugadorActual.modificarSaldo(-this.jugadorActual.getSaldo()/2);
    }
    
    public void saluda(){
        System.out.println("hola");
    }
    
    public boolean edificarHotel(int numeroCasilla){
        boolean edificada = false;
        
        //PRECONDICION: El numeroCasilla debe ser una de las propiedades
        //del jugadorActual
        
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        
        edificada = this.jugadorActual.edificarHotel(titulo);
        
        if (edificada)
            this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return edificada;
        
    }
    
    private void encarcelarJugador(){
        
        if (this.jugadorActual.deboIrACarcel()){
            Casilla casillaCarcel = this.tablero.getCarcel();
            this.jugadorActual.irACarcel(casillaCarcel);
            this.setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else{
            Sorpresa carta = this.jugadorActual.devolverCartaLibertad();
            this.mazo.add(carta);
            this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }
    
    public Sorpresa getCartaActual (){
        return this.cartaActual;
    }
    
    Dado getDado(){
        return this.dado;
    }
    
    public Jugador getJugadorActual(){
        return this.jugadorActual;
    }
    
    
    public ArrayList<Jugador> getJugadores(){
        return this.jugadores;
    }

    public int getValorDado(){
        return (this.getDado().getValor());
    }
    
    public boolean hipotecarPropiedad(int numeroCasilla){
        
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        this.jugadorActual.hipotecarPropiedad(titulo);
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return (true);
    }
    
    
    public void inicializarJuego(ArrayList<String> nombres){
        inicializarJugadores (nombres);
        inicializarTablero();
        inicializarCartasSorpresa();
        salidaJugadores();
        
    }
    private void inicializarJugadores(ArrayList<String> nombres){
        this.jugadores = new ArrayList<>();
        
        for (String t :nombres){
            jugadores.add(new Jugador(t));

        }
        
    } 

    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo){
        boolean libre;
        
        if (metodo == MetodoSalirCarcel.TIRANDODADO){
            int resultado = this.tirarDado();
            
            if (resultado >= 5)
                this.jugadorActual.setEncarcelado(false);
        }
        else if (metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
            this.jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
        }
        
        libre = this.jugadorActual.getEncarcelado();
        
        if (libre){
            this.setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else{
            this.setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }
        return libre;
    }
    
    public void jugar(){
        int resultado = this.tirarDado();
        this.mover(this.tablero.obtenerCasillaFinal(this.jugadorActual.getCasillaActual(), resultado).getNumeroCasilla());
    }
       
    void mover(int numCasillaDestino){
        
        Casilla casillaInicial = this.jugadorActual.getCasillaActual();
        Casilla casillaFinal = this.tablero.obtenerCasillaNumero(numCasillaDestino);
        this.jugadorActual.setCasillaActual(casillaFinal);
        
        if (numCasillaDestino < casillaInicial.getNumeroCasilla())
            this.jugadorActual.modificarSaldo(SALDO_SALIDA);
        
        if (casillaFinal.soyEdificable()){
            this.actuarSiEnCasillaEdificable();
        }
        else{
            this.actuarSiEnCasillaNoEdificable();
        }
    }
    
    public Casilla obtenerCasillaJugadorActual(){
        
        return (this.jugadorActual.getCasillaActual());
    }
    
    //public ArrayList<Casilla> obtenerCasillasTablero();

    public ArrayList<Integer> obtenerPropiedadesJugador(){
        ArrayList<Integer> a_devolver = new ArrayList<>();
        
        for (int i=0; i<this.jugadorActual.getPropiedades().size(); i++){
            if (this.jugadorActual.getCasillaActual().getTitulo() == this.jugadorActual.getPropiedades().get(i)){
                a_devolver.add(this.jugadorActual.getCasillaActual().getNumeroCasilla());
            }
        }
        return a_devolver;
    }
        
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca){
        ArrayList<Integer> a_devolver = new ArrayList<>();
        
        for (int i=0; i<this.jugadorActual.getPropiedades().size(); i++){
            if (this.jugadorActual.getPropiedades().get(i).getHipotecada() == estadoHipoteca){
                a_devolver.add(this.jugadorActual.getCasillaActual().getNumeroCasilla());
            }
        }
        return a_devolver;
    }
    
    public void obtenerRanking(){  

        Collections.sort(jugadores);
    } 
       
    public int obtenerSaldoJugadorActual(){
        return (this.jugadorActual.getSaldo());
    }
    private void salidaJugadores(){
        Casilla salida = new OtraCasilla(0, TipoCasilla.SALIDA);
        
        for (int i=0; i<jugadores.size(); i++){
            jugadores.get(i).setCasillaActual(salida);
        }
        
        this.indiceJA = r.nextInt(jugadores.size());
        this.jugadorActual = this.jugadores.get(indiceJA);
        this.estado = EstadoJuego.JA_PREPARADO;
    }
    private void setCartaActual(Sorpresa cartaActual){
        this.cartaActual = cartaActual;
    }
    public void siguienteJugador(){
        this.indiceJA = (indiceJA + 1) % jugadores.size();
        this.jugadorActual = this.jugadores.get(indiceJA);
        
        if (this.jugadorActual.getEncarcelado()){
            this.estado = EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD;
        }
        else{
            this.estado = EstadoJuego.JA_PREPARADO;
        }
    }
    int tirarDado(){
        
        return (this.getDado().tirar());
    }
    
    public boolean venderPropiedad(int numeroCasilla){
        
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        this.jugadorActual.venderPropiedad(casilla);
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return (true);
    }

    public void setEstadoJuego(EstadoJuego estado) {
        this.estado = estado;
    }
    
   
    ArrayList<Sorpresa> getMazo (){
        
        return mazo;
    }

    public Tablero getTablero() {
        return tablero;
    }
    
    public void inicializarTablero(){
        tablero = new Tablero();
    }
    
    public EstadoJuego getEstadoJuego(){
        return this.estado;
    }
    
    private void inicializarCartasSorpresa(){
        mazo = new ArrayList<>();
        
        mazo.add(new Sorpresa ("¡Enhorabuena, todos los jugadores te dan 500!", 500, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Te conviertes en especulador por 5000", 5000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("¡Enhorabuena, los jugadores te dan 1000!", 1000, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("¡Que pena, pierdes 1500!", -1500, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("Vas a la salida", 0, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Vas a la carcel", tablero.getCarcel().getNumeroCasilla() , TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("Vas a la casilla 13", 13, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("¡Enhorabuena, recibes 200 por cada hotel!", 200, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("¡Que pena, pierdes 400 por cada hotel!", -400, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("¡Das 200 a cada jugador!", -200, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("Sales de la carcel", 0, TipoSorpresa.SALIRCARCEL));
        mazo.add(new Sorpresa ("Te conviertes en especulador por 3000", 3000, TipoSorpresa.CONVERTIRME));
        
        Collections.shuffle(mazo);   
    }

    @Override
    public String toString() {
        return "Qytetet{" + "cartaActual=" + cartaActual + ", jugadores=" + jugadores + ", jugadorActual=" + jugadorActual + ", mazo=" + mazo + ", tablero=" + tablero + ", dado=" + dado + '}';
    }    
}
