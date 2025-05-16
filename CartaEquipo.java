// Se importan las librerias necesarias para la clase.
import javax.swing.*;
import java.awt.*;

// Se define la clase CartaEquipo la cual se extiende de la clase Carta, esta clase consta de equipos y ligas.
public class CartaEquipo extends Carta {
    // Se declaran los atributos de la clase.
    private String nombreEquipo;
    private String nombreLiga;
    private boolean esLiga;
    private boolean esEspecial;
    private String tipoEspecial;

    // Se establece el metodo constructor el cual requiere del nombre de un equipo, el nombre de una liga y un identificador el cual ayuda a conocer si la carta es liga o equipo.
    public CartaEquipo(String nombreEquipo, String nombreLiga, boolean esLiga) {
        super();
        this.nombreEquipo = nombreEquipo;
        this.nombreLiga = nombreLiga;
        this.esLiga = esLiga;
    }

    public CartaEquipo(String tipoEspecial){
        super();
        this.tipoEspecial = tipoEspecial;
        this.esEspecial = true;
    }

    public String getTipoEspecial(){
        return tipoEspecial;
    }

    public boolean esEspecial(){
        return esEspecial;
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de comparar una carta con otra.
    @Override
    public boolean esIgualA(Carta carta) {
        if (this.esEspecial && carta instanceof CartaEquipo) {
            return this.tipoEspecial.equals(((CartaEquipo)carta).getTipoEspecial());
        }
        return carta instanceof CartaEquipo otraCarta &&
                this.nombreLiga.equals(otraCarta.nombreLiga);
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de asignarle la imagen correspondiente a la carta.
    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esLiga){
            nombreImagen = nombreLiga + ".png";
        }else if(esEspecial){
            nombreImagen = tipoEspecial + ".png";
        }else{
            nombreImagen = nombreEquipo + ".png";
        }
        String ruta = "C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasFutbolImagenes\\" + nombreImagen;
        //String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasFutbolImagenes\\" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
    //Método el cual maneja el uso de las cartas especiales dentro del modo de juego de los equipos
    @Override
    public void distincion(Memorama memorama) {
        int columna = memorama.getUltimaColumnaSeleccionada();
        int fila = memorama.getUltimaFilaSeleccionada();
        this.setEncontrada(true);
        memorama.getBotonCarta(fila,columna).setEnabled(false);
        if (tipoEspecial.equals("TarjetaRoja")) {
            JOptionPane.showMessageDialog(null, "Tarjeta Roja, has perdido el turno");
            memorama.siguienteTurno();
            memorama.getBotonCarta(fila,columna).setEnabled(false);
        }else if(tipoEspecial.equals("Var")){
            JOptionPane.showMessageDialog(null, "Var, se muestran todas las cartas");
            memorama.mostrarTodasLasCartas();
            memorama.getBotonCarta(fila,columna).setEnabled(false);
        }else if(tipoEspecial.equals("FueraDeJuego")){
            JOptionPane.showMessageDialog(null, "Fuera de juego, se revuelven todas las cartas");
            memorama.revolverCartasNoEmparejadas();
            memorama.getBotonCarta(fila,columna).setEnabled(false);
        }
    }
}