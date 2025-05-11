public class CartaEquipo extends Carta {
    private String nombreEquipo;

    public CartaEquipo(String nombreEquipo) {
        super();
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaEquipo otraCarta &&
                this.nombreEquipo.equals(otraCarta.nombreEquipo);
    }
}
