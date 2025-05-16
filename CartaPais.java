// Se importan las librerias necesarias.
import javax.swing.*;
import java.awt.*;

// Se define la creacion de la clase CartaPais la cual se extiende de la clase Carta.
public class CartaPais extends Carta{
    // Se definen los atributos pertenecientes a la clase.
    private String nombrePais;
    private String nombreCiudad;
    private boolean esPais;

    // Metodo constructor de la clase el cual crea una carta en base a un pais, una cuidad y un identificador acerca si este es pais o cuidad.
    public CartaPais(String nombrePais, String nombreCiudad, boolean esPais ) {
        super();
        this.nombrePais = nombrePais;
        this.nombreCiudad = nombreCiudad;
        this.esPais = esPais;
    }

    // Metodo getter el cual brinda el pais que tiene asignado la carta.
    public String getNombrePais() {
        return nombrePais;
    }

    // Metodo getter el cual brinda la cuidad que tiene asignada la carta.
    public String getNombreCiudad() {
        return nombreCiudad;
    }

    // Metodo booleano el cual devuelve si la carta es un pais o es una cuidad.
    public boolean esPais() {
        return esPais;
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de comparar una carta con otra.
    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaPais otraCarta &&
                this.nombrePais.equals(otraCarta.nombrePais);
    }

    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de asignarle la imagen correspondiente a la carta. 
    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esPais){
            nombreImagen = nombrePais + ".png";
        }else{
            nombreImagen = nombreCiudad + ".png";
        }
        String ruta = "C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasGeoMatchImagenes\\" + nombreImagen;
        //String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasGeoMatchImagenes\\" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    @Override
    public void distincion(Memorama memorama){

    }
}
