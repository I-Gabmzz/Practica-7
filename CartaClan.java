import javax.swing.*;
import java.awt.*;

public class CartaClan extends Carta {
    private String clan;
    private String personaje;
    private boolean esClan;

    public CartaClan(String clan, String personaje, boolean esClan) {
        super();
        this.clan = clan;
        this.personaje = personaje;
        this.esClan = esClan;
    }

    public String getClan() {
        return clan;
    }

    public String getPersonaje() {
        return personaje;
    }

    public boolean esClan() {
        return esClan;
    }

    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaClan otraCarta &&
                this.clan.equals(otraCarta.clan);
    }

    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esClan){
            nombreImagen = clan + ".png";
        }else{
            nombreImagen = personaje + ".png";
        }
        //String ruta = "C:\\Users\\14321\\Downloads\\Practica-7\\cartasNarutoImagenes\\" + nombreImagen;
        String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasNarutoImagenes\\" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}