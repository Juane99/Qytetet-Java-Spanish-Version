/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Scanner;



/**
 *
 * @author juanemartinez
 */
public class PruebaQytetet {
    static Qytetet juego = Qytetet.getInstance();
    private static Scanner input = new Scanner(System.in);
    
    private static ArrayList<Sorpresa> MayorqueCero (){
        ArrayList<Sorpresa> aDevolver;
        aDevolver = new ArrayList<>();
        
        for (Sorpresa t: juego.getMazo()){
            
            if (t.getValor() > 0){
                aDevolver.add(t);
            }
        }
        
        return aDevolver;
    }
    
    private static ArrayList<Sorpresa> TipoIrACasilla(){
        ArrayList<Sorpresa> aDevolver;
        aDevolver = new ArrayList<>();
        
        for (Sorpresa t: juego.getMazo()){
            
            if (t.getTipo() == TipoSorpresa.IRACASILLA){
                aDevolver.add(t);
            }
        }
        
        return aDevolver;
    }
        
        
    private static ArrayList<Sorpresa> TipoEspecificado(TipoSorpresa especificado){
        ArrayList<Sorpresa> aDevolver;
        aDevolver = new ArrayList<>();
        
        for (Sorpresa t: juego.getMazo()){
            
            if (t.getTipo() == especificado){
                aDevolver.add(t);
            }
        }
        
        return aDevolver;
    }
    
    private static ArrayList<String> getNombreJugadores(){
        
        ArrayList<String> a_devolver = new ArrayList<>();
        
        System.out.println("Introduzca el numero de jugadores y sus nombres (2 a " + Qytetet.MAX_JUGADORES + " jugadores)");
        
        int numero_nombres = input.nextInt();
        
        while (numero_nombres > Qytetet.MAX_JUGADORES || numero_nombres < 2){
            System.out.println("Introduzca el numero de jugadores entre 2 y " + Qytetet.MAX_JUGADORES);
            numero_nombres = input.nextInt();
        }
        
        input.nextLine();
        
        for (int i=0; i<numero_nombres; i++){
            a_devolver.add(input.nextLine());
        }
        
        return a_devolver;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean comprada = false, salir_carcel;
        int calle1 = -1, calle2 = -1;
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Paco");
        nombres.add("Richi");
        nombres.add("Pepe");
       
        juego.inicializarJuego(nombres);
        System.out.println("El jugador actual es " + " " + juego.getJugadorActual());
        juego.mover(4);
        juego.comprarTituloPropiedad();
        juego.siguienteJugador();
        System.out.println("El jugador actual es " + " " + juego.getJugadorActual());
        juego.mover(4);
        juego.comprarTituloPropiedad();
        
        for (int i=0; i<juego.getJugadores().size(); i++)
           System.out.println(juego.getJugadores().get(i));
    
        /*
        int num_dado = juego.tirarDado();
        System.out.println("El dado ha salido " + " " + num_dado);
        
        System.out.println("A continuacion nos moveremos despues de tirar el dado");
        juego.mover(juego.getJugadorActual().getCasillaActual().getNumeroCasilla() + num_dado);
        System.out.println("El jugador esta en la casilla " + " " + juego.getJugadorActual().getCasillaActual());
        
        if (juego.getJugadorActual().getCasillaActual().getTipo() == TipoCasilla.CALLE){
            calle1 = juego.getJugadorActual().getCasillaActual().getNumeroCasilla();
            comprada = juego.comprarTituloPropiedad();
        }
        
        if (comprada){
            System.out.println ("La calle ha sido comprada");
        }
        else{
            System.out.println ("La calle no pudo ser comprada");
        }
        
        comprada = false;
        
        juego.siguienteJugador();
        
        System.out.println("El jugador actual es " + " " + juego.getJugadorActual());
        
        num_dado = juego.tirarDado();
        System.out.println("El dado ha salido " + " " + num_dado);
        
        System.out.println("A continuacion nos moveremos despues de tirar el dado");
        juego.mover(juego.getJugadorActual().getCasillaActual().getNumeroCasilla() + num_dado);
        System.out.println("El jugador esta en la casilla " + " " + juego.getJugadorActual().getCasillaActual());
        
        if (juego.getJugadorActual().getCasillaActual().getNumeroCasilla() == calle1)
            juego.getJugadorActual().pagarAlquiler();
        
     
        
        if (juego.getJugadorActual().getCasillaActual().getTipo() == TipoCasilla.CALLE){
            calle2 = juego.getJugadorActual().getCasillaActual().getNumeroCasilla();
            comprada = juego.comprarTituloPropiedad();
        }
        
        if (comprada){
            System.out.println ("La calle ha sido comprada");
        }
        else{
            System.out.println ("La calle no pudo ser comprada");
        }
        
        comprada = false;
        
        juego.siguienteJugador();
        
        System.out.println("El jugador actual es " + " " + juego.getJugadorActual());
        
        num_dado = juego.tirarDado();
        System.out.println("El dado ha salido " + " " + num_dado);
        
        System.out.println("A continuacion nos moveremos despues de tirar el dado");
        juego.mover(juego.getJugadorActual().getCasillaActual().getNumeroCasilla() + num_dado);
        System.out.println("El jugador esta en la casilla " + " " + juego.getJugadorActual().getCasillaActual());
        
        if (juego.getJugadorActual().getCasillaActual().getNumeroCasilla() == calle1 || juego.getJugadorActual().getCasillaActual().getNumeroCasilla() == calle2)
            juego.getJugadorActual().pagarAlquiler();
        
        if (juego.getJugadorActual().getCasillaActual().getTipo() == TipoCasilla.CALLE)
            comprada = juego.comprarTituloPropiedad();
        
        if (comprada){
            System.out.println ("La calle ha sido comprada");
        }
        else{
            System.out.println ("La calle no pudo ser comprada");
        }
        
        comprada = false;
        

        
        juego.obtenerRanking();
        
        for (int i=0; i<juego.getJugadores().size(); i++)
           System.out.println(juego.getJugadores().get(i));*/

    }
    
       
    
}
    

