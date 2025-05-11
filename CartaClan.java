public class CartaClan extends Carta {
    private String clan;

    public CartaClan(String clan) {
        super();
        this.clan = clan;
    }

    public String getClan() {
        return clan;
    }

    @Override
    public boolean esIgualA(Carta carta) {
        return carta instanceof CartaClan otraCarta &&
                this.clan.equals(otraCarta.clan);
    }
}
