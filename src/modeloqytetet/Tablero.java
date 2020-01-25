/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
/**
 *
 * @author juane
 */
public class Tablero {
    
    private ArrayList<Casilla> casillas = new ArrayList<>();
    private Casilla carcel;
    
    Tablero(){
        inicializar();
    }
    
    Tablero (ArrayList<Casilla> casillas, Casilla carcel){
        this.casillas = casillas;
        this.carcel = carcel;
    }
    
    @Override
    public String toString() {
        return "\n-----------TABLERO DE JUEGO-----------\n" + casillas + "\n";
    }

    public Casilla getCarcel() {
        return carcel;
    }

    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }
    
    
    boolean esCasillaCarcel (int numeroCasilla){
        return (carcel.getNumeroCasilla() == numeroCasilla);
    }
    
    Casilla obtenerCasillaNumero(int numeroCasilla){
        return casillas.get(numeroCasilla);
    }
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento){
        int posicionFinal = casilla.getNumeroCasilla() + desplazamiento;
        posicionFinal %= casillas.size();
        
        return casillas.get(posicionFinal);
    }
    
    
    private void inicializar(){
        TituloPropiedad titulo1 = new TituloPropiedad ("Avinguda de las Toronjas", 811, 50, 250, 150, 250);
        TituloPropiedad titulo2 = new TituloPropiedad ("Avenida de la Constitucion", 600, 50, 250, 150, 250);
        TituloPropiedad titulo3 = new TituloPropiedad ("Paseo de los tristes", 1000, 50, 250, 150, 250);
        TituloPropiedad titulo4 = new TituloPropiedad ("Calle Maria Goyri", 900, 50, 250, 150, 250);
        TituloPropiedad titulo5 = new TituloPropiedad ("Calle Padre Claret", 900, 50, 250, 150, 250);
        TituloPropiedad titulo6 = new TituloPropiedad ("Carretera Antigua de Malaga", 550, 50, 250, 150, 250);
        TituloPropiedad titulo7 = new TituloPropiedad ("Calle Sagrada Familia", 650, 50, 250, 150, 250);
        TituloPropiedad titulo8 = new TituloPropiedad ("Calle Pedro Antonio de Alarcon", 1000, 50, 250, 150, 250);
        TituloPropiedad titulo9 = new TituloPropiedad ("Calle Periodista Eugenio Selles", 500, 50, 250, 150, 250);
        TituloPropiedad titulo10 = new TituloPropiedad ("Calle Esturion", 777, 50, 250, 150, 250);
        TituloPropiedad titulo11 = new TituloPropiedad ("Calle Marmolillos", 600, 50, 250, 150, 250);
        TituloPropiedad titulo12 = new TituloPropiedad ("Calle Virgen de la Paloma", 500, 50, 250, 150, 250);
        
        
        casillas.add(new OtraCasilla (0, TipoCasilla.SALIDA));
        casillas.add(new Calle (1, titulo1));
        casillas.add(new Calle (2, titulo2));
        casillas.add(new OtraCasilla (3, TipoCasilla.SORPRESA));
        casillas.add(new Calle (4, titulo3));
        casillas.add(new OtraCasilla (5, TipoCasilla.JUEZ));
        casillas.add(new Calle (6, titulo4));
        casillas.add(new Calle (7, titulo12));
        casillas.add(new Calle (8, titulo5));
        casillas.add(new Calle (9, titulo11));
        casillas.add(new Calle (10, titulo6));
        casillas.add(new OtraCasilla (11, TipoCasilla.SORPRESA));
        casillas.add(new Calle (12, titulo7));
        casillas.add(new OtraCasilla (13, TipoCasilla.CARCEL));
        casillas.add(new Calle (14,titulo8));
        casillas.add(new OtraCasilla (15, TipoCasilla.SORPRESA));
        casillas.add(new Calle (16, titulo9));
        casillas.add(new OtraCasilla (17, TipoCasilla.IMPUESTO));
        casillas.add(new Calle (18, titulo10));
        casillas.add(new OtraCasilla (19, TipoCasilla.PARKING));
        
        carcel = casillas.get(13);
    }
}
