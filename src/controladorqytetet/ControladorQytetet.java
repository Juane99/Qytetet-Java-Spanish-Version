/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorqytetet;

import java.util.ArrayList;
import modeloqytetet.EstadoJuego;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.Qytetet;


public class ControladorQytetet {
    private static final ControladorQytetet instance = new ControladorQytetet();
    private ArrayList<String> nombreJugadores;
    static Qytetet modelo = Qytetet.getInstance();
    
    private ControladorQytetet(){}
    
    public static ControladorQytetet getInstance(){
        return instance;
    }
    
    public void setNombreJugadores(ArrayList<String> nombreJugadores){
        this.nombreJugadores = nombreJugadores;
    }
    
    public ArrayList<Integer> obtenerOperacionesJuegoValidas(){
        ArrayList<Integer> operacionesValidas = new ArrayList<>();
        
        if (modelo.getJugadores() == null)
            operacionesValidas.add(OpcionMenu.INICIARJUEGO.ordinal());
        
        else{
            if(modelo.getEstadoJuego() == EstadoJuego.ALGUNJUGADORENBANCARROTA){
                operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());     
            }
            else if(modelo.getEstadoJuego() == EstadoJuego.JA_ENCARCELADO){
                operacionesValidas.add(OpcionMenu.PASARTURNO.ordinal());
            }
            else if(modelo.getEstadoJuego() == EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD){
                operacionesValidas.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
                operacionesValidas.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
            }
            else if(modelo.getEstadoJuego() == EstadoJuego.JA_CONSORPRESA){
                operacionesValidas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                operacionesValidas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                operacionesValidas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                operacionesValidas.add(OpcionMenu.EDIFICARCASA.ordinal());
                operacionesValidas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                operacionesValidas.add(OpcionMenu.APLICARSORPRESA.ordinal());
            }
            else if(modelo.getEstadoJuego() == EstadoJuego.JA_PUEDEGESTIONAR){
                operacionesValidas.add(OpcionMenu.PASARTURNO.ordinal());
                operacionesValidas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                operacionesValidas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                operacionesValidas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                operacionesValidas.add(OpcionMenu.EDIFICARCASA.ordinal());
                operacionesValidas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
            }
            else if(modelo.getEstadoJuego() == EstadoJuego.JA_PUEDECOMPRAROGESTIONAR){
                operacionesValidas.add(OpcionMenu.PASARTURNO.ordinal());
                operacionesValidas.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                operacionesValidas.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                operacionesValidas.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                operacionesValidas.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                operacionesValidas.add(OpcionMenu.EDIFICARCASA.ordinal());
                operacionesValidas.add(OpcionMenu.EDIFICARHOTEL.ordinal());
            }
            else if(modelo.getEstadoJuego() == EstadoJuego.JA_PREPARADO){
                operacionesValidas.add(OpcionMenu.JUGAR.ordinal());
            }

            operacionesValidas.add(OpcionMenu.TERMINARJUEGO.ordinal());
            operacionesValidas.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operacionesValidas.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operacionesValidas.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operacionesValidas.add(OpcionMenu.DIHOLA.ordinal());
            operacionesValidas.add(OpcionMenu.REDUCEMITAD.ordinal());

        }
        
        return operacionesValidas;
    }
    
    public boolean necesitaElegirCasilla(int opcionMenu){
    
        boolean necesita = false;
        OpcionMenu opcionmenu = OpcionMenu.values()[opcionMenu];
        
        if (opcionmenu == OpcionMenu.HIPOTECARPROPIEDAD || opcionmenu == OpcionMenu.CANCELARHIPOTECA
                || opcionmenu == OpcionMenu.EDIFICARCASA || opcionmenu == OpcionMenu.EDIFICARHOTEL
                || opcionmenu == OpcionMenu.VENDERPROPIEDAD)
            
            necesita = true;
      
        return necesita;
    }
    
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu){
        OpcionMenu opcionmenu = OpcionMenu.values()[opcionMenu];
        ArrayList<Integer> numerosCasilla = new ArrayList<>();
        
        if (opcionmenu == OpcionMenu.CANCELARHIPOTECA)
            numerosCasilla = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
        
        else if (opcionmenu == OpcionMenu.HIPOTECARPROPIEDAD)
            numerosCasilla = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
        
        else if (opcionmenu == OpcionMenu.EDIFICARCASA || opcionmenu == OpcionMenu.EDIFICARHOTEL || opcionmenu == OpcionMenu.VENDERPROPIEDAD)
            numerosCasilla = modelo.obtenerPropiedadesJugador();
        
        return numerosCasilla;
    }
    
    public String realizarOperacion(int opcionElegida, int casillaElegida){
    
        OpcionMenu opcionmenu = OpcionMenu.values()[opcionElegida];
        String a_devolver = " ";
               
        if (opcionmenu == OpcionMenu.INICIARJUEGO){
            modelo.inicializarJuego(this.nombreJugadores);
            a_devolver = "-------------Qytetet Java-------------\n\n" + "-------------Participantes-------------\n" + modelo.getJugadores() + "\n\n"
                    + "-------------Comienzo de turno-------------\n" + modelo.getJugadorActual();
        }
        else if (opcionmenu == OpcionMenu.JUGAR){
            modelo.jugar();
            a_devolver = "El valor del dado es: " + modelo.getValorDado() + "\ny la casilla actual es: "
                + modelo.obtenerCasillaJugadorActual();   
        }   
        else if (opcionmenu == OpcionMenu.APLICARSORPRESA){
            a_devolver = "La sorpresa a aplicar es: " + modelo.getCartaActual();
            modelo.aplicarSorpresa();
        }
        else if (opcionmenu == OpcionMenu.CANCELARHIPOTECA){
            if (modelo.cancelarHipoteca(casillaElegida))
                a_devolver = "Has cancelado la hipoteca de la casilla: " + casillaElegida;
            else
                a_devolver = "No se ha podido cancelar la hipoteca";
        }
        else if (opcionmenu == OpcionMenu.COMPRARTITULOPROPIEDAD){
            if (modelo.comprarTituloPropiedad())
                a_devolver = "Has comprado la calle";
            else
                a_devolver = "No has podido comprar la calle";
        }
        else if (opcionmenu == OpcionMenu.EDIFICARCASA){
            if (modelo.edificarCasa(casillaElegida))
                a_devolver = "Has edificado una casa en la casilla: " + casillaElegida;
            else
                a_devolver = "No has podido edificar una casa en la casilla: " + casillaElegida;
        }
        else if (opcionmenu == OpcionMenu.EDIFICARHOTEL){
            if (modelo.edificarHotel(casillaElegida))
                a_devolver = "Has edificado un hotel en la casilla: " + casillaElegida;
            else
                a_devolver = "No has podido edificar un hotel en la casilla: " + casillaElegida;
        }
        else if (opcionmenu == OpcionMenu.HIPOTECARPROPIEDAD){
            if (modelo.hipotecarPropiedad(casillaElegida))
                a_devolver = "Has hipotecado la casilla: " + casillaElegida;
            else
                a_devolver = "No has podido hipotecar la casilla: " + casillaElegida;
        }
        else if (opcionmenu == OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD){
            if (modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD))
                a_devolver = "No has podido salir de la carcel y sigues encarcelado";
            else
                a_devolver = "¡ENHORABUENA!, Has salido de la carcel pagando libertad";
        }
        else if (opcionmenu == OpcionMenu.INTENTARSALIRCARCELTIRANDODADO){
            if (modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO))
                a_devolver = "No has podido salir de la carcel y sigues encarcelado";
            else
                a_devolver = "¡ENHORABUENA!, Has salido de la carcel tirando el dado";
        }
        else if (opcionmenu == OpcionMenu.MOSTRARJUGADORACTUAL){
            a_devolver = modelo.getJugadorActual().toString();
        }
        else if (opcionmenu == OpcionMenu.MOSTRARJUGADORES){
            a_devolver = modelo.getJugadores().toString();
        }
        else if (opcionmenu == OpcionMenu.MOSTRARTABLERO){
            a_devolver = modelo.getTablero().toString();
        }
        else if (opcionmenu == OpcionMenu.TERMINARJUEGO){
            modelo.obtenerRanking();
            System.out.println("El ranking de jugadores es el siguiente: \n" 
            + modelo.getJugadores().toString());
            System.exit(0);
        }
        else if (opcionmenu == OpcionMenu.PASARTURNO){
            modelo.siguienteJugador();
            a_devolver = "Has pasado el turno a el jugador: \n" + modelo.getJugadorActual().toString();
        }
        else if (opcionmenu == OpcionMenu.VENDERPROPIEDAD){
            if (modelo.venderPropiedad(casillaElegida))
                a_devolver = "Has vendido la propiedad de la casilla: " + casillaElegida;
            else
                a_devolver = "No has podido vender la propiedad de la casilla: " + casillaElegida;
        }
        else if (opcionmenu == OpcionMenu.DIHOLA){
            modelo.saluda();
        }
        else if (opcionmenu == OpcionMenu.REDUCEMITAD){
            modelo.mitad();
            a_devolver = "Su saldo ha sido reducido a la mitad";
        }
        
        return a_devolver;
  
    }
    
    
}

