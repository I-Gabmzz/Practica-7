import javax.swing.*;

public abstract class Carta {
    protected boolean volteada;
    protected boolean encontrada;

    public Carta() {
        this.volteada = false;
        this.encontrada = false;
    }

    public boolean isVolteada() {
        return volteada;
    }

    public void setVolteada(boolean volteada) {
        this.volteada = volteada;
    }

    public boolean isEncontrada() {
        return encontrada;
    }

    public void setEncontrada(boolean encontrada) {
        this.encontrada = encontrada;
    }


    public abstract boolean esIgualA(Carta carta);
    public abstract ImageIcon getImagen();
}
