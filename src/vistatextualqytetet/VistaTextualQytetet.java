/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistatextualqytetet;

import java.util.ArrayList;
import controladorqytetet.*;
import modeloqytetet.Qytetet;
import java.util.Scanner;

/**
 *
 * @author juanemartinez
 */
public class VistaTextualQytetet {
    
    static ControladorQytetet controlador = ControladorQytetet.getInstance();
    private static Scanner input = new Scanner(System.in);
    
    public ArrayList<String> obtenerNombreJugadores(){
        ArrayList<String> a_devolver = new ArrayList<>();
        ArrayList<String> numero_jugadores = new ArrayList<>();
        numero_jugadores.add("2");
        numero_jugadores.add("3");
        numero_jugadores.add("4");
        
        System.out.println("Introduzca el numero de jugadores y sus nombres (2 a " + Qytetet.MAX_JUGADORES + " jugadores)");
        
        int numero_nombres = Integer.valueOf(this.leerValorCorrecto(numero_jugadores));
          
        System.out.println("Introduzca el nombre de los jugadores");
        
        for (int i=0; i<numero_nombres; i++){
            a_devolver.add(input.nextLine());
        }
        
        return a_devolver;
    }
    
    public int elegirCasilla(int opcionMenu){
        
        ArrayList<Integer> casValidas = new ArrayList<>();
        ArrayList<String> casString = new ArrayList<>();
        String valorCorrecto;
        
        casValidas = controlador.obtenerCasillasValidas(opcionMenu);
        
        if (casValidas.size() == 0)
            return (-1);
        else{
            System.out.println(casValidas);
            
            for (int i=0; i<casValidas.size(); i++)
                 casString.add(String.valueOf(casValidas.get(i)));
            
            valorCorrecto = leerValorCorrecto(casString);
            
            return (Integer.valueOf(valorCorrecto));
        }
    }
    
    public String leerValorCorrecto(ArrayList<String> valoresCorrectos){
        String cadena;
        
        do{
            cadena = input.nextLine();  
            
        }while (!valoresCorrectos.contains(cadena));
        
        return cadena;
    }
    
    
    public int elegirOperacion(){
        ArrayList<Integer> operacionesValidas = new ArrayList<>();
        ArrayList<String> operacionesString = new ArrayList<>();
        String a_devolver;
        
        operacionesValidas = controlador.obtenerOperacionesJuegoValidas();
        
        for (int i=0; i<operacionesValidas.size(); i++)
            operacionesString.add(String.valueOf(operacionesValidas.get(i)));
        
        System.out.println("Las operaciones que puede elegir son: ");
        
        for (int i=0; i<operacionesString.size(); i++)
            System.out.println(operacionesString.get(i) + " " + OpcionMenu.values()[operacionesValidas.get(i)] );
        
        a_devolver = leerValorCorrecto(operacionesString);
        
        return (Integer.valueOf(a_devolver));
        
    }
    
    public static void main(String[] args) {
        VistaTextualQytetet ui = new VistaTextualQytetet();
        controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        
        int operacionElegida, casillaElegida = 0;
        boolean necesitaElegirCasilla;
        
        do {
            operacionElegida = ui.elegirOperacion();
            necesitaElegirCasilla = controlador.necesitaElegirCasilla(operacionElegida);
            if (necesitaElegirCasilla)
                casillaElegida = ui.elegirCasilla(operacionElegida);
            
            if (!necesitaElegirCasilla || casillaElegida >= 0)
                System.out.println(controlador.realizarOperacion(operacionElegida,
                casillaElegida));
            
        } while (true);
    }
    
}
