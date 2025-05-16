// Se importan las librerias necesarias.
import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

// Se define la creacion de la clase CartaPais la cual se extiende de la clase Carta.
public class CartaPais extends Carta{
    // Se definen los atributos pertenecientes a la clase.
    private String nombrePais;
    private String nombreCiudad;
    private boolean esPais;
    private Clip clip;
    HashMap<String, String> sonidos = new HashMap<>();

    // Metodo constructor de la clase el cual crea una carta en base a un pais, una cuidad y un identificador acerca si este es pais o cuidad.
    public CartaPais(String nombrePais, String nombreCiudad, boolean esPais ) {
        super();
        this.nombrePais = nombrePais;
        this.nombreCiudad = nombreCiudad;
        this.esPais = esPais;
        cargarSonidos();
    }
    // Metodo derivado de la herencia de la clase Carta, este metodo tiene la finalidad de comparar una carta con otra.
    @Override
    public boolean esIgualA(Carta carta) {
        if (carta instanceof CartaPais otraCarta &&
                this.nombrePais.equals(otraCarta.nombrePais)) {
            reproducirAudioPais(this.nombrePais);
            return true;
        }
        return false;
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
    //Método que reproduce los sonidos una vez encontrado un par
    @Override
    public void distincion(Memorama memorama) {
        reproducirSonidoEspecial("Match");
    }
    //Método para cargar los sonidos en un HashMap
    public void cargarSonidos() {
        sonidos.put("Mexico", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Español.wav");
        sonidos.put("Alemania", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Aleman.wav");
        sonidos.put("Australia", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Ingles.wav");
        sonidos.put("Canada", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Ingles.wav");
        sonidos.put("Usa", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Ingles.wav");
        sonidos.put("ReinoUnido", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Ingles.wav");
        sonidos.put("Egipto", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Arabe.wav");
        sonidos.put("Francia", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Frances.wav");
        sonidos.put("Japon", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Japones.wav");
        sonidos.put("Italia", "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\Italiano.wav");
    }
    //Método para reproducir el audio según el país
    public void reproducirAudioPais(String pais) {
        String rutaAudio = sonidos.get(pais);
        if (rutaAudio != null) {
            try {
                detenerAudio();

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                        new File(rutaAudio));

                clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });

                clip.start();
            } catch (Exception e) {
                System.err.println("Error al reproducir audio para " + pais + ": " + e.getMessage());

            }
        } else {
            System.err.println("No hay audio configurado para: " + pais);
        }
    }
    //Método que reproduce el audio especial dependiendo el match
    public void reproducirSonidoEspecial(String sonido) {
        try {
            String ruta = "C:\\Users\\14321\\IdeaProjects\\Practica-7\\Audios\\" + sonido.toLowerCase() + ".wav";
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File(ruta));

            detenerAudio();
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido especial: " + e.getMessage());
        }
    }
    //Método para detener el audio
    public void detenerAudio() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.close();
        }
    }
}
