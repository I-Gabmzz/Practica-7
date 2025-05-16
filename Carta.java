// Se importa los paquetes necesarios.
import javax.swing.*;

// Se define la clase.
public abstract class Carta {
    // Se colocan los atributos pertenecientes a la clase Carta.
    protected boolean volteada;
    protected boolean encontrada;

    // Se genera el constructor de la clase carta.
    public Carta() {
        this.volteada = false;
        this.encontrada = false;
    }

    // Este metodo tiene la finalidad de dar a conocer si una carta esta volteada o no.
    public boolean isVolteada() {
        return volteada;
    }

    // Este metodo logra que a una carta se le asigne el estado de volteada.
    public void setVolteada(boolean volteada) {
        this.volteada = volteada;
    }

    // Este metodo tiene la finalidad de devolver si una cartada ya fue o no encontrada.
    public boolean isEncontrada() {
        return encontrada;
    }

    // Este metodo tiene como finalidad marcar que una carta ya fue encontrada.
    public void setEncontrada(boolean encontrada) {
        this.encontrada = encontrada;
    }

    // Se define este metodo abstracto el cual compara si una carta es igual a otra.
    public abstract boolean esIgualA(Carta carta);
    
    // Se define este metodo abstracto el cual logra que una carta obtenga su respectiva imagen correspondiente. 
    public abstract ImageIcon getImagen();

    // Metodo el cual se usa para distinguir las cartas entre si y marcar herencia.
    public abstract void distincion(Memorama memorama);
}
