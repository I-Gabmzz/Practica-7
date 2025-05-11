public class CartaPais extends Carta{
    private String nombrePais;

    public CartaPais(String nombrePais) {
        super();
        this.nombrePais = nombrePais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaPais otraCarta &&
                this.nombrePais.equals(otraCarta.nombrePais);
    }
}
