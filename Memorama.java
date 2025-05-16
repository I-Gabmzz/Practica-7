//Se importan las librerias necesarias para el funcionamiento del programa
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

//Se declara la clase 
public class Memorama {
    //Se definen los atributos de la clase 
    private int modoDeJuego;
    private List<String> jugadores;
    private List<Integer> puntuaciones;
    private int turnoActual;
    private Carta[][] cartas;
    private JButton[][] botonesCartas;
    private JLabel avisoTurno;
    private ImageIcon imagenCubierta;
    private Timer timer;
    private Carta primeraSeleccion;
    private int[] primeraSeleccionPos;
    private boolean bloqueado;
    private JTextArea areaPuntuaciones;
    private int ultimaFilaSeleccionada;
    private int ultimaColumnaSeleccionada;

    //Método para ejecutar el programa
    public static void main(String[] args) {
        Memorama m = new Memorama();
        m.iniciarMemorama();
    }

    //Método que muestra toda la interfaz del menÚ del juegador en donde se muestran cartas, turno y diversos componentes de la GUI
    public void menuDeJuego() {
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
        avisoTurno = new JLabel(getAvisoDeTurno(modoDeJuego), JLabel.CENTER);
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
        areaPuntuaciones = new JTextArea(getAvisoDePuntuacion());
        switch (modoDeJuego) {
            case 1 -> areaPuntuaciones.setForeground(new Color(243, 155, 14));
            case 2 -> areaPuntuaciones.setForeground(new Color(43, 151, 64));
            case 3 -> areaPuntuaciones.setForeground(new Color(112, 171, 149));
        }
        areaPuntuaciones.setFont(new Font("Noto Sans", Font.PLAIN, 24));
        areaPuntuaciones.setEditable(false);
        panelPuntuaciones.add(new JScrollPane(areaPuntuaciones), BorderLayout.CENTER);
        panelPuntuaciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 850));
        panelIzquierdo.add(panelPuntuaciones);


        JPanel panelDerecho = new JPanel(new GridLayout(4, 5, 5, 5));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 10));

        inicializarCartas();
        botonesCartas = new JButton[4][5];
        imagenCubierta = new ImageIcon(new ImageIcon(InterfazGrafica.getCartasOcultas(modoDeJuego))
                .getImage().getScaledInstance(115, 200, Image.SCALE_SMOOTH));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                botonesCartas[i][j] = new JButton();
                botonesCartas[i][j].setIcon(imagenCubierta);
                botonesCartas[i][j].setPreferredSize(new Dimension(115, 200));
                botonesCartas[i][j].setBorderPainted(false);
                botonesCartas[i][j].setContentAreaFilled(false);
                botonesCartas[i][j].setFocusPainted(false);

                final int fila = i;
                final int columna = j;
                botonesCartas[i][j].addActionListener(e -> {
                    if (!bloqueado && !cartas[fila][columna].isEncontrada() && !cartas[fila][columna].isVolteada()) {
                        seleccionarCarta(fila, columna);

                    }
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

    //Método para seleccionar las cartas, en este método se les asignan los botones a cada carta para que funcionen correctamente
    //además se maneja la lógica de si se encuentra un par se mantiene el turno y se le suma un punto, si no se encuentra un par se cambia al siguiente jugador,
    //se evalua si se terminó el juego o no.
    private void seleccionarCarta(int fila, int columna) {
        cartas[fila][columna].setVolteada(true);
        botonesCartas[fila][columna].setIcon(obtenerImagenCarta(cartas[fila][columna]));

        if (cartas[fila][columna] instanceof CartaClan && ((CartaClan) cartas[fila][columna]).esPoder()) {
            ((CartaClan) cartas[fila][columna]).distincion(this);
            botonesCartas[fila][columna].setEnabled(false);

            if (primeraSeleccion != null) {
                primeraSeleccion.setVolteada(false);
                botonesCartas[primeraSeleccionPos[0]][primeraSeleccionPos[1]].setIcon(imagenCubierta);
                primeraSeleccion = null;
            }

            return;
        }

        this.ultimaFilaSeleccionada = fila;
        this.ultimaColumnaSeleccionada = columna;

        Carta cartaSeleccionada = cartas[fila][columna];
        cartaSeleccionada.setVolteada(true);
        botonesCartas[fila][columna].setIcon(obtenerImagenCarta(cartaSeleccionada));

        if (cartaSeleccionada instanceof CartaEquipo && ((CartaEquipo)cartaSeleccionada).esEspecial()) {
            ((CartaEquipo)cartaSeleccionada).distincion(this);
            if (primeraSeleccion != null) {
                botonesCartas[primeraSeleccionPos[0]][primeraSeleccionPos[1]].setIcon(imagenCubierta);
                primeraSeleccion.setVolteada(false);
                primeraSeleccion = null;
            }
            return;
        }
        if (primeraSeleccion == null) {
            primeraSeleccion = cartaSeleccionada;
            primeraSeleccionPos = new int[]{fila, columna};
        }else {
            bloqueado = true;
            timer = new Timer(1000, e -> {
                if (primeraSeleccion.esIgualA(cartas[fila][columna])) {
                    primeraSeleccion.setEncontrada(true);
                    cartas[fila][columna].setEncontrada(true);
                    botonesCartas[primeraSeleccionPos[0]][primeraSeleccionPos[1]].setEnabled(false);
                    botonesCartas[fila][columna].setEnabled(false);
                    puntuaciones.set(turnoActual, puntuaciones.get(turnoActual) + 1);
                    areaPuntuaciones.setText(getAvisoDePuntuacion());

                    if (juegoTerminado()) {
                        determinarGanador();
                    }
                } else {
                    primeraSeleccion.setVolteada(false);
                    cartas[fila][columna].setVolteada(false);
                    botonesCartas[primeraSeleccionPos[0]][primeraSeleccionPos[1]].setIcon(imagenCubierta);
                    botonesCartas[fila][columna].setIcon(imagenCubierta);
                    siguienteTurno();
                }

                primeraSeleccion = null;
                bloqueado = false;
                timer.stop();
            });
            timer.start();
        }
    }

    //Método para obtener la dirección de la imagen de la carta correspondiente
    private ImageIcon obtenerImagenCarta(Carta carta) {
        return carta.getImagen();
    }

    //Método para pasar de turno entre los jugadores.
    public void siguienteTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
        avisoTurno.setText(getAvisoDeTurno(modoDeJuego));
    }

    //Método para mostrar los turnos
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

    //Método para mostrar la puntuación de los jugadores respectivamente.
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

    //Método para iniciar el memorama y jugar, en este método esta el flujo del juego.
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

    //Método para inicializar las cartas del memorama, además de que se revuelven.
    private void inicializarCartas() {
        cartas = new Carta[4][5];
        List<Carta> paresCartas = new ArrayList<>();
        switch (modoDeJuego) {
            case 1:
                String[] clanes = {"Konoha", "Sunagakure", "Takigakure", "Kusugakure", "Iwagakure",
                        "Amegakure", "Kirigakure", "Kumogakure", "Otogakure", "Yugakure"};
                String[] personajes = {"Naruto", "Gaara", "Kakuzu", "Karin", "Deidara",
                        "Pain", "Zabuza", "Killer Bee", "Orochimaru", "Hidan"};

                for (int i = 0; i < clanes.length; i++) {
                    paresCartas.add(new CartaClan(clanes[i], personajes[i], true));
                    paresCartas.add(new CartaClan(clanes[i], personajes[i], false));
                }
                paresCartas.add(new CartaClan("shuriken"));
                paresCartas.add(new CartaClan("papelBomba"));
                paresCartas.add(new CartaClan("bijuu"));
                paresCartas.add(new CartaClan("sanacion"));
                break;
            case 2:
                String[] equipos = {"Ajax", "RealMadrid", "Manchester", "America",
                        "BayernMunich", "Psg", "Juventus", "Galaxy"};
                String[] ligas = {"Eredivisie", "LaLiga", "Premier", "LigaMX", "Bundesliga", "Ligue1", "SerieA", "Mls"};
                for (int i = 0; i < ligas.length; i++) {
                    paresCartas.add(new CartaEquipo(equipos[i], ligas[i], true));
                    paresCartas.add(new CartaEquipo(equipos[i], ligas[i], false));
                }
                paresCartas.add(new CartaEquipo("TarjetaRoja"));
                paresCartas.add(new CartaEquipo("TarjetaRoja"));
                paresCartas.add(new CartaEquipo("Var"));
                paresCartas.add(new CartaEquipo("FueraDeJuego"));
                break;
            case 3:
                String[] paises = {"Mexico", "Alemania", "Australia", "Canada", "Egipto",
                        "Francia", "Italia", "Japon", "Usa", "ReinoUnido"};
                String[] ciudades = {"Cdmx", "Berlin", "Sidney", "Toronto", "ElCairo", "Paris", "Roma", "Tokio", "NuevaYork", "Londres"};
                for (int i = 0; i < paises.length; i++) {
                    paresCartas.add(new CartaPais(paises[i], ciudades[i], true));
                    paresCartas.add(new CartaPais(paises[i], ciudades[i], false));
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

    public void aplicarEfectoPoder(int puntos) {
        puntuaciones.set(turnoActual, puntuaciones.get(turnoActual) + puntos);
        areaPuntuaciones.setText(getAvisoDePuntuacion());
        siguienteTurno();
    }

    //Método para determinar si el juego ya terminó.
    private boolean juegoTerminado() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!cartas[i][j].isEncontrada()) {
                    return false;
                }
            }
        }
        return true;
    }

    //Método para determinar un ganador en el juego.
    private void determinarGanador() {
        int maxPuntos = Collections.max(puntuaciones);
        List<String> ganadores = new ArrayList<>();

        for (int i = 0; i < puntuaciones.size(); i++) {
            if (puntuaciones.get(i) == maxPuntos) {
                ganadores.add(jugadores.get(i));
            }
        }

        JPanel panelGanador = new JPanel(new BorderLayout(10, 10));
        panelGanador.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Color colorFuente = null;
        switch (modoDeJuego) {
            case 1 -> colorFuente = new Color(243, 155, 14);
            case 2 -> colorFuente = new Color(43, 151, 64);
            case 3 -> colorFuente = new Color(112, 171, 149);
        }

        JLabel titulo = new JLabel("FIN DEL JUEGO", JLabel.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 30));
        titulo.setForeground(colorFuente);
        panelGanador.add(titulo, BorderLayout.NORTH);

        ImageIcon imagenGanador = new ImageIcon(InterfazGrafica.getImagenGanador(modoDeJuego));
        JLabel labelImagen = new JLabel(imagenGanador, JLabel.CENTER);
        panelGanador.add(labelImagen, BorderLayout.CENTER);

        String mensaje;
        if (ganadores.size() == 1) {
            mensaje = "<html><center> \uD83C\uDFC6 <br>Felicidades " + ganadores.get(0) + "<br>Has ganado con " + maxPuntos + " puntos.</center></html>";
        } else {
            mensaje = "<html><center> \uD83C\uDFC6 <br>Empate entre:<br>";
            for (String ganador : ganadores) {
                mensaje += "- " + ganador + "<br>";
            }
            mensaje += "Todos con " + maxPuntos + " puntos.</center></html>";
        }

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setOpaque(false);

        JLabel labelMensaje = new JLabel(mensaje, JLabel.CENTER);
        labelMensaje.setFont(new Font("Noto Sans", Font.PLAIN, 20));
        labelMensaje.setForeground(colorFuente);
        panelInferior.add(labelMensaje, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));
        panelBotones.setOpaque(false);

        JButton botonReiniciar = new JButton(" Volver a jugar ");
        JButton botonSalir = new JButton(" Salir ");

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 18);
        Color colorBoton = new Color(220, 220, 220);

        botonReiniciar.setFont(fuenteBotones);
        botonReiniciar.setBackground(colorBoton);
        botonReiniciar.setFocusPainted(false);

        botonSalir.setFont(fuenteBotones);
        botonSalir.setBackground(colorBoton);
        botonSalir.setFocusPainted(false);

        panelBotones.add(botonReiniciar);
        panelBotones.add(botonSalir);

        panelInferior.add(panelBotones, BorderLayout.SOUTH);
        panelGanador.add(panelInferior, BorderLayout.SOUTH);

        JDialog dialogoGanador = new JDialog();
        dialogoGanador.setTitle("\uD83C\uDF89 Juego Terminado");
        dialogoGanador.setModal(true);
        dialogoGanador.setContentPane(panelGanador);
        dialogoGanador.pack();
        dialogoGanador.setLocationRelativeTo(null);
        dialogoGanador.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        botonReiniciar.addActionListener(e -> {
            dialogoGanador.dispose();
            Window ventanaActual = SwingUtilities.getWindowAncestor(avisoTurno);
            ventanaActual.dispose();
            reiniciarJuego();
        });

        botonSalir.addActionListener(e -> {
            dialogoGanador.dispose();
            Window ventanaActual = SwingUtilities.getWindowAncestor(avisoTurno);
            ventanaActual.dispose();
            mostrarAgradecimiento();
        });

        dialogoGanador.setVisible(true);
    }

    //Método para reiniciar el juego en caso de que el usuario quiera jugar otra vez y cambiar de modo.
    private void reiniciarJuego() {
        puntuaciones = new ArrayList<>();
        for (int i = 0; i < jugadores.size(); i++) {
            puntuaciones.add(0);
        }
        turnoActual = 0;

        modoDeJuego = InterfazGrafica.solicitarModoDeJuego();
        menuDeJuego();
    }

    //Método para mostrar un mensaje de despedida al momento de terminar el juego.
    private void mostrarAgradecimiento() {
        JFrame frameAgradecimiento = new JFrame();
        frameAgradecimiento.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelAgradecimiento = new JPanel(new BorderLayout());
        ImageIcon imagenAgradecimiento = new ImageIcon("C:\\Users\\PC OSTRICH\\Practica-7\\Agradecimiento.png");
        // ImageIcon imagenAgradecimiento = new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Practica-7\\Agradecimiento.png");
        JLabel labelImagen = new JLabel(imagenAgradecimiento, JLabel.CENTER);
        panelAgradecimiento.add(labelImagen, BorderLayout.CENTER);

        frameAgradecimiento.setContentPane(panelAgradecimiento);
        frameAgradecimiento.pack();
        frameAgradecimiento.setLocationRelativeTo(null);
        frameAgradecimiento.setVisible(true);

        Timer timer = new Timer(5000, e -> {
            frameAgradecimiento.dispose();
            System.exit(0);
        });
        timer.setRepeats(false);
        timer.start();

    }
    //Método para obtener los botones de la carta y así tener un mejor control de ellos.
    public JButton getBotonCarta(int fila, int columna) {
        return botonesCartas[fila][columna];
    }
    //Método para saber la posición de la fila que se seleccionó
    public int getUltimaFilaSeleccionada() {
        return ultimaFilaSeleccionada;
    }
    //Método para saber la posición de la columna que se seleccionó
    public int getUltimaColumnaSeleccionada() {
        return ultimaColumnaSeleccionada;
    }
    //Método para mostrar todas las cartas del tablero restantes, solo se muestran las que no han sido descubiertas
    public void mostrarTodasLasCartas() {
        bloqueado = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!cartas[i][j].isEncontrada() &&
                        !cartas[i][j].isVolteada() &&
                        botonesCartas[i][j].isEnabled()) {
                    cartas[i][j].setVolteada(true);
                    botonesCartas[i][j].setIcon(obtenerImagenCarta(cartas[i][j]));
                }
            }
        }
        Timer timer = new Timer(1000, e -> {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!cartas[i][j].isEncontrada() &&
                            cartas[i][j].isVolteada() &&
                            botonesCartas[i][j].isEnabled()) {
                        cartas[i][j].setVolteada(false);
                        botonesCartas[i][j].setIcon(imagenCubierta);
                    }
                }
            }
            bloqueado = false;
            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }
    //Método el cual revuelve las cartas y las coloca volteadas en el tablero, este método solo afecta a aquellas cartas que no han sido adivinadas.
    public void revolverCartasNoEmparejadas() {
        List<Carta> cartasNoEmparejadas = new ArrayList<>();
        List<int[]> posicionesNoEmparejadas = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!cartas[i][j].isEncontrada() && !(cartas[i][j] instanceof CartaEquipo && ((CartaEquipo) cartas[i][j]).esEspecial())) {
                    cartasNoEmparejadas.add(cartas[i][j]);
                    posicionesNoEmparejadas.add(new int[]{i, j});
                }
            }
        }
        Collections.shuffle(cartasNoEmparejadas);
        for (int k = 0; k < cartasNoEmparejadas.size(); k++) {
            int[] pos = posicionesNoEmparejadas.get(k);
            cartas[pos[0]][pos[1]] = cartasNoEmparejadas.get(k);
            botonesCartas[pos[0]][pos[1]].setIcon(imagenCubierta);
        }
    }
}