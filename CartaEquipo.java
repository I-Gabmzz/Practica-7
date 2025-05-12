import javax.swing.*;
import java.awt.*;

public class CartaEquipo extends Carta {
    private String nombreEquipo;
    private String nombreLiga;
    private boolean esLiga;

    public CartaEquipo(String nombreEquipo, String nombreLiga, boolean esLiga) {
        super();
        this.nombreEquipo = nombreEquipo;
        this.nombreLiga = nombreLiga;
        this.esLiga = esLiga;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getNombreLiga() {
        return nombreLiga;
    }

    public boolean esLiga() {
        return esLiga;
    }

    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaEquipo otraCarta &&
                this.nombreEquipo.equals(otraCarta.nombreEquipo);
    }

    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esLiga){
            nombreImagen = nombreLiga + ".png";
        }else{
            nombreImagen = nombreEquipo + ".png";
        }
        String ruta = "C:\\Users\\14321\\Downloads\\Practica-7\\cartasFutbolImagenes\\" + nombreImagen;
        // String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasFutbolImagenes" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}
