import javax.swing.*;
import java.awt.*;

public class CartaPais extends Carta{
    private String nombrePais;
    private String nombreCiudad;
    private boolean esPais;

    public CartaPais(String nombrePais, String nombreCiudad, boolean esPais ) {
        super();
        this.nombrePais = nombrePais;
        this.nombreCiudad = nombreCiudad;
        this.esPais = esPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }
    public String getNombreCiudad() {
        return nombreCiudad;
    }
    public boolean esPais() {
        return esPais;
    }

    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaPais otraCarta &&
                this.nombrePais.equals(otraCarta.nombrePais);
    }

    @Override
    public ImageIcon getImagen() {
        String nombreImagen;
        if(esPais){
            nombreImagen = nombrePais + ".png";
        }else{
            nombreImagen = nombreCiudad + ".png";
        }
        //String ruta = "C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasGeoMatchImagenes\\" + nombreImagen;
        String ruta = "C:\\Users\\PC OSTRICH\\Practica-7\\cartasGeoMatchImagenes\\" + nombreImagen;
        ImageIcon icono = new ImageIcon(ruta);
        Image imagenEscalada = icono.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }
}
