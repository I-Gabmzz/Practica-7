import javax.swing.*;
import java.awt.*;

public class CartaClan extends Carta {
    private String clan;
    private String personaje;
    private boolean esClan;
    private boolean esPoder;
    private String tipoPoder;

    public CartaClan(String clan, String personaje, boolean esClan) {
        super();
        this.clan = clan;
        this.personaje = personaje;
        this.esClan = esClan;
        this.esPoder = false;
    }

    public CartaClan(String tipoPoder) {
        super();
        this.esPoder = true;
        this.tipoPoder = tipoPoder;
    }

    public boolean esPoder() {
        return esPoder;
    }

    @Override
    public boolean esIgualA(Carta carta) {
        if (this.esPoder || (carta instanceof CartaClan && ((CartaClan) carta).esPoder)) {
            return false;
        }

        return carta instanceof CartaClan otraCarta &&
                this.clan.equals(otraCarta.clan);
    }

    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esPoder){
            nombreImagen = tipoPoder + ".png";
        } else if (esClan) {
            nombreImagen = clan + ".png";
        } else {
            nombreImagen = personaje + ".png";
        }
        //String ruta = "C:\\Users\\14321\\Downloads\\Practica-7\\cartasNarutoImagenes\\" + nombreImagen;
        String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasNarutoImagenes\\" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    @Override
    public void distincion(Memorama memorama) {
        int puntos = 0;
        String mensaje = "";

        switch(tipoPoder) {
            case "shuriken":
                puntos = -1;
                mensaje = "Shuriken ---> Pierdes un 1 punto";
                break;
            case "papelBomba":
                puntos = -2;
                mensaje = "Papel Bomba ---> Pierdes 2 puntos";
                break;
            case "bijuu":
                puntos = 2;
                mensaje = "Biju ---> Ganas 2 puntos";
                break;
            case "sanacion":
                puntos = 1;
                mensaje = "Sanación ---> Ganas 1 punto";
                break;
        }
        JOptionPane.showMessageDialog(null, mensaje, "¡Poder especial!", JOptionPane.INFORMATION_MESSAGE);
        memorama.aplicarEfectoPoder(puntos);
        this.setEncontrada(true);
    }
}