// Se importan las librerias necesarias para la clase.
import javax.swing.*;
import java.awt.*;

// Se define la clase CartaEquipo la cual se extiende de la clase Carta, esta clase consta de equipos y ligas.
public class CartaEquipo extends Carta {
    // Se declaran los atributos de la clase.
    private String nombreEquipo;
    private String nombreLiga;
    private boolean esLiga;

    // Se establece el metodo constructor el cual requiere del nombre de un equipo, el nombre de una liga y un identificador el cual ayuda a conocer si la carta es liga o equipo.
    public CartaEquipo(String nombreEquipo, String nombreLiga, boolean esLiga) {
        super();
        this.nombreEquipo = nombreEquipo;
        this.nombreLiga = nombreLiga;
        this.esLiga = esLiga;
    }

    // Se crea este metodo getter el cual sirve para obtener el nombre del equipo.
    public String getNombreEquipo() {
        return nombreEquipo;
    }

    // Se crea este metodo getter el cual sirve para obtener el nombre de la liga.
    public String getNombreLiga() {
        return nombreLiga;
    }

    // Metodo booleano el cual ayuda a identificar si la carta es una liga o es un equipo,
    public boolean esLiga() {
        return esLiga;
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de comparar una carta con otra.
    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaEquipo otraCarta &&
                this.nombreEquipo.equals(otraCarta.nombreEquipo);
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de asignarle la imagen correspondiente a la carta. 
    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esLiga){
            nombreImagen = nombreLiga + ".png";
        }else{
            nombreImagen = nombreEquipo + ".png";
        }
        // String ruta = "C:\\Users\\14321\\Downloads\\Practica-7\\cartasFutbolImagenes\\" + nombreImagen;
        String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasFutbolImagenes\\" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}
