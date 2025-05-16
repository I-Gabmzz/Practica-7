// Se importan las librerias necesarias.
import javax.swing.*;
import java.awt.*;

// Se define la creacion de la clase CartaClan la cual se extiende de la clase Carta.
public class CartaClan extends Carta {
    // Se definen los atributos pertenecientes a la clase.
    private String clan;
    private String personaje;
    private boolean esClan;

    // Metodo constructor de la clase el cual crea una carta en base a una clan, un personaje y un identificador acerca si este es personaje o clan.
    public CartaClan(String clan, String personaje, boolean esClan) {
        super();
        this.clan = clan;
        this.personaje = personaje;
        this.esClan = esClan;
    }

    // Metodo getter el cual brinda el clan que tiene asignado la carta.
    public String getClan() {
        return clan;
    }

    // Metodo getter el cual brinda el personaje que tiene asignado la carta.
    public String getPersonaje() {
        return personaje;
    }

    // Metodo booleano el cual devuelve si la carta es una clan o es un personaje.
    public boolean esClan() {
        return esClan;
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de comparar una carta con otra.
    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaClan otraCarta &&
                this.clan.equals(otraCarta.clan);
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de asignarle la imagen correspondiente a la carta. 
    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esClan){
            nombreImagen = clan + ".png";
        }else{
            nombreImagen = personaje + ".png";
        }
        String ruta = "C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasNarutoImagenes\\" + nombreImagen;
        //String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasNarutoImagenes\\" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    @Override
    public void distincion(Memorama memorama){

    }
}
