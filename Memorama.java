import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Memorama {
    private int modoDeJuego;
    private List<String> jugadores;
    private List<Integer> puntuaciones;
    private int turnoActual;
    private JLabel avisoTurno;
    private JTextArea areaPuntuaciones;
    private Carta[][] cartas;
    private Carta primeraCartaSeleccionada;
    private boolean cartaBloqueada;
    private JButton[][] botonesCartas;
    private int[] posicionCartaSeleccionada;
    private ImageIcon imagenCubierta;
    private Timer timer;

    public static void main(String[] args) {
        Memorama m = new Memorama();
        m.iniciarMemorama();
    }

    public void menuDeJuego() {
        JButton[][] botonesCartas;

        JFrame ventana = new JFrame(InterfazGrafica.getTituloDeModo(modoDeJuego));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1000, 1050);
        ventana.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JPanel panelTitulo = new JPanel();
        ImageIcon gifTitular = new ImageIcon(InterfazGrafica.getBannerPrincipal(modoDeJuego));
        JLabel panelTitular = new JLabel(gifTitular);
        panelTitulo.add(panelTitular);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 5));

        JPanel panelTurno = new JPanel(new BorderLayout());

        JLabel avisoTurno = new JLabel(getAvisoDeTurno(modoDeJuego), JLabel.CENTER);
        avisoTurno.setFont(new Font("Noto Sans", Font.BOLD, 24));
        panelTurno.add(avisoTurno, BorderLayout.CENTER);
        panelTurno.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panelIzquierdo.add(panelTurno);

        panelIzquierdo.add(Box.createVerticalStrut(15));
        JPanel panelGif = new JPanel(new BorderLayout());
        ImageIcon gifCentral = new ImageIcon(InterfazGrafica.getBannerCentral(modoDeJuego));
        JLabel panelGifCentral = new JLabel(gifCentral, JLabel.CENTER);
        panelGif.add(panelGifCentral, BorderLayout.CENTER);
        panelGif.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50000));
        panelIzquierdo.add(panelGif);

        panelIzquierdo.add(Box.createVerticalStrut(15));

        JPanel panelPuntuaciones = new JPanel(new BorderLayout());
        JTextArea areaPuntuaciones = new JTextArea(getAvisoDePuntuacion());
        switch (modoDeJuego) {
            case 1 -> areaPuntuaciones.setForeground(new Color(243, 155, 14));
            case 2 -> areaPuntuaciones.setForeground(new Color(43, 151, 64));
            case 3 -> areaPuntuaciones.setForeground(new Color(112, 171, 149));
        }
        areaPuntuaciones.setFont(new Font("Noto Sans", Font.PLAIN, 24));
        areaPuntuaciones.setEditable(false);
        panelPuntuaciones.add(new JScrollPane(areaPuntuaciones), BorderLayout.CENTER);
        panelPuntuaciones.setMaximumSize(new Dimension(Integer.MAX_VALUE,850));
        panelIzquierdo.add(panelPuntuaciones);

        JPanel panelDerecho = new JPanel(new GridLayout(4, 5, 5, 5));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 10));

        botonesCartas = new JButton[4][5];
        ImageIcon imagenCartaOculta = new ImageIcon(InterfazGrafica.getCartasOcultas(modoDeJuego));
        Image imagenCartaOcultaEscalada = imagenCartaOculta.getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH);
        ImageIcon imagenCubierta = new ImageIcon(imagenCartaOcultaEscalada);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                botonesCartas[i][j] = new JButton();
                botonesCartas[i][j].setIcon(imagenCubierta);
                botonesCartas[i][j].setPreferredSize(new Dimension(115, 200));
                botonesCartas[i][j].setBorderPainted(false);
                botonesCartas[i][j].setContentAreaFilled(false);
                botonesCartas[i][j].setFocusPainted(false);

                botonesCartas[i][j].addActionListener(e -> {

                });
                panelDerecho.add(botonesCartas[i][j]);
            }
        }

        panelCentral.add(panelIzquierdo, BorderLayout.WEST);
        panelCentral.add(panelDerecho, BorderLayout.CENTER);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 20, 20));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JButton botonInstrucciones = new JButton("Instrucciones");
        JButton botonSalir = new JButton("Salir");

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 18);
        Color colorBoton = new Color(220, 220, 220);

        Stream.of(botonInstrucciones, botonSalir)
                .forEach(boton -> {
                    boton.setFont(fuenteBotones);
                    boton.setBackground(colorBoton);
                    boton.setFocusPainted(false);
                });

        botonInstrucciones.addActionListener(e -> {
            InterfazGrafica.mostrarInstrucciones(modoDeJuego);
        });

        botonSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                    ventana,
                    "¿Estás seguro que quieres salir del juego?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        panelInferior.add(botonInstrucciones);
        panelInferior.add(botonSalir);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }
  
    public String getAvisoDeTurno(int modo) {
        String jugador = jugadores.get(turnoActual);
        return switch (modo) {
            case 1 -> String.format(
                    "<html><center><font color='#f39b0e'>\uD83D\uDC64<br>Turno del Jugador<br><br><font color='black'>%s</font></font></center></html>",
                    jugador
            );
            case 2 -> String.format(
                    "<html><center><font color='#2b9740'>\uD83D\uDC64<br>Turno del Jugador<br><br><font color='black'>%s</font></font></center></html>",
                    jugador
            );
            case 3 -> String.format(
                    "<html><center><font color='#70ab95'>\uD83D\uDC64<br>Turno del Jugador<br><br><font color='black'>%s</font></font></center></html>",
                    jugador
            );
            default -> "";
        };
    }

  public String getAvisoDePuntuacion() {
        StringBuilder sb = new StringBuilder();
        sb.append("         \uD83C\uDFC6   Puntuación   \uD83C\uDFC6      \n\n");
        for (int i = 0; i < jugadores.size(); i++) {
            int puntos = puntuaciones.get(i);
            sb.append(jugadores.get(i))
                    .append(": ")
                    .append(puntos);

            if (i < jugadores.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
  
    public void iniciarMemorama() {
        InterfazGrafica.menuInicial();
        jugadores = new ArrayList<>();
        puntuaciones = new ArrayList<>();

        int numeroDeJugadores = InterfazGrafica.solicitarJugadores();
        for (int i = 1; i < numeroDeJugadores + 1; i++) {
            String nombreDeJugador = InterfazGrafica.solicitarNombreDeJugador(i);
            jugadores.add(nombreDeJugador);
            puntuaciones.add(0);
        }
        modoDeJuego = InterfazGrafica.solicitarModoDeJuego();
        menuDeJuego();
    }

    private void siguienteTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
        avisoTurno.setText(getAvisoDeTurno(modoDeJuego));
        areaPuntuaciones.setText(getAvisoDePuntuacion());
    }

    private ImageIcon obtenerImagenCarta(Carta carta) {
        return carta.getImagen();
    }

    private void inicializarCartas() {
        cartas = new Carta[4][5];
        List<Carta> paresCartas = new ArrayList<>();
        switch(modoDeJuego) {
            case 1:
                String[] clanes = {"Konoha", "Sunagakure", "Takigakure", "Kusugakure", "Iwagakure",
                        "Amegakure", "Kirigakure", "Kumogakure", "Otogakure", "Yugakure"};
                String[] personajes = {"Naruto","Gaara", "Kakuzu", "Karin", "Deidara",
                        "Pain", "Zabuza","Killer Bee", "Orochimaru", "Hidan"};

                for (int i = 0; i < clanes.length; i++) {
                    paresCartas.add(new CartaClan(clanes[i], personajes[i], true));
                    paresCartas.add(new CartaClan(clanes[i], personajes[i], false));
                }
                break;
            case 2:
                String[] equipos = {"Ajax", "RealMadrid", "Manchester", "America",
                        "BayernMunich", "Psg", "Juventus", "Galaxy", "Flamengo","BocaJuniors"};
                String[] ligas ={"Eredivisie","LaLiga","Premier","LigaMX","Bundesliga","Ligue1","SerieA","Mls","Brasileirao","LigaArgentina"};
                for (int i = 0; i < ligas.length; i++) {
                    paresCartas.add(new CartaEquipo(equipos[i],ligas[i], true));
                    paresCartas.add(new CartaEquipo(equipos[i],ligas[i], false));
                }
                break;
            case 3:
                String[] paises = {"Mexico", "Alemania", "Australia", "Canada", "Egipto",
                        "Francia", "Italia", "Japon", "Usa","ReinoUnido"};
                String[] ciudades ={"Cdmx","Berlin","Sidney","Toronto","ElCairo","Paris","Roma","Tokio","NuevaYork","Londres"};
                for (int i = 0; i < paises.length; i++) {
                    paresCartas.add(new CartaPais(paises[i],ciudades[i], true));
                    paresCartas.add(new CartaPais(paises[i],ciudades[i], false));
                }
                break;
        }
        Collections.shuffle(paresCartas);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                cartas[i][j] = paresCartas.get(i * 5 + j);
            }
        }
    }

    private void seleccionarCarta(int fila, int columna) {
        cartas[fila][columna].setVolteada(true);
        botonesCartas[fila][columna].setIcon(obtenerImagenCarta(cartas[fila][columna]));
        if (primeraCartaSeleccionada == null) {
            primeraCartaSeleccionada = cartas[fila][columna];
            posicionCartaSeleccionada = new int[]{fila, columna};
        } else {
            cartaBloqueada = true;
            timer = new Timer(1000, e -> {
                if (primeraCartaSeleccionada.esIgualA(cartas[fila][columna])) {
                    primeraCartaSeleccionada.setEncontrada(true);
                    cartas[fila][columna].setEncontrada(true);
                    botonesCartas[posicionCartaSeleccionada[0]][posicionCartaSeleccionada[1]].setEnabled(false);
                    botonesCartas[fila][columna].setEnabled(false);
                    puntuaciones.set(turnoActual, puntuaciones.get(turnoActual) + 1);
                    avisoTurno.setText(getAvisoDeTurno(modoDeJuego));

                } else {
                    primeraCartaSeleccionada.setVolteada(false);
                    cartas[fila][columna].setVolteada(false);
                    botonesCartas[posicionCartaSeleccionada[0]][posicionCartaSeleccionada[1]].setIcon(imagenCubierta);
                    botonesCartas[fila][columna].setIcon(imagenCubierta);
                    siguienteTurno();
                }
                primeraCartaSeleccionada = null;
                cartaBloqueada = false;
                timer.stop();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
  
}
