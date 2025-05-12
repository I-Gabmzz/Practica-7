import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class InterfazGrafica {
    private static boolean juegoIniciado = false;

    public static void main(String[] args) {
        menuInicial();
        solicitarJugadores();
        solicitarNombreDeJugador(1);
        solicitarModoDeJuego();
    }

    public static void menuInicial() {
        JFrame ventana = new JFrame("Flip & Match");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(550, 700);
        ventana.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelDeTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelBienvenida = new JLabel("Bienvenido a Flip & Match");
        labelBienvenida.setFont(new Font("Noto Sans", Font.BOLD, 36));
        labelBienvenida.setForeground(new Color(204, 33, 63));
        panelDeTitulo.add(labelBienvenida);

        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //ImageIcon imagenIcono = new ImageIcon("C:\\Users\\PC OSTRICH\\Practica-7\\pantallaInicial.gif");
        ImageIcon imagenIcono = new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Practica-7\\pantallaInicial.gif");
        JLabel labelImagen = new JLabel(imagenIcono);
        panelCentro.add(labelImagen);

        JPanel panelDeAcciones = new JPanel(new GridLayout(1, 3, 15, 15));
        JButton botonJugar = new JButton(" ▶ Jugar");
        JButton botonCreditos = new JButton(" \uD83D\uDC64 Créditos");
        JButton botonSalir = new JButton(" \uD83D\uDEAA Salir");

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 18);
        Color colorBoton = new Color(220, 220, 220);

        Stream.of(botonJugar, botonCreditos, botonSalir)
                .forEach(boton -> {
                    boton.setFont(fuenteBotones);
                    boton.setBackground(colorBoton);
                    boton.setFocusPainted(false);
                });

        panelDeAcciones.add(botonJugar);
        panelDeAcciones.add(botonCreditos);
        panelDeAcciones.add(botonSalir);

        panelPrincipal.add(panelDeTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
        panelPrincipal.add(panelDeAcciones, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);

        botonJugar.addActionListener(e -> {
            juegoIniciado = true;
            ventana.dispose();
        });

        botonCreditos.addActionListener(e -> {
            mostrarCreditos();
        });

        ventana.getRootPane().setDefaultButton(botonJugar);

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
        ventana.setVisible(true);
        while (!juegoIniciado) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void mostrarCreditos() {
        JPanel panelCreditos = new JPanel(new BorderLayout(10, 10));
        panelCreditos.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Créditos", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 24));
        titulo.setForeground(new Color(206, 32, 116));
        panelCreditos.add(titulo, BorderLayout.NORTH);

        JTextArea contenido = new JTextArea(
                "Desarrollado por:\n" +
                        "• Diego Erik Alfonso Montoya (1198520)\n" +
                        "• Angel Gabriel Manjarrez Moreno (1197503)\n\n" +
                        "Versión: 10/05/2025\n" +
                        "© Todos los derechos reservados"
        );
        contenido.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        contenido.setEditable(false);
        contenido.setBackground(new Color(240, 240, 240));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelCreditos.add(new JScrollPane(contenido), BorderLayout.CENTER);

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 14);
        Color colorBoton = new Color(220, 220, 220);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> ((Window) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose());
        cerrar.setFont(fuenteBotones);
        cerrar.setBackground(colorBoton);
        cerrar.setFocusPainted(false);

        JPanel panelBoton = new JPanel();
        panelBoton.add(cerrar);
        panelCreditos.add(panelBoton, BorderLayout.SOUTH);

        JDialog creditos = new JDialog();
        creditos.setTitle("Créditos");
        creditos.setModal(true);
        creditos.setResizable(true);
        creditos.setContentPane(panelCreditos);
        creditos.pack();
        creditos.setLocationRelativeTo(null);
        creditos.setVisible(true);
    }

    public static int solicitarJugadores() {
        AtomicInteger numeroDeJugadores = new AtomicInteger();

        JDialog jugadores = new JDialog();
        jugadores.setTitle("Número de Jugadores");
        jugadores.setSize(500, 150);
        jugadores.setLocationRelativeTo(null);
        jugadores.setModal(true);
        jugadores.setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Seleccione el número de jugadores \uD83D\uDC65", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 24));
        titulo.setForeground(new Color(205, 32, 186));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 15, 15));

        JButton boton2 = new JButton(" 2 ");
        JButton boton3 = new JButton(" 3 ");
        JButton boton4 = new JButton(" 4 ");

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 18);
        Color colorBoton = new Color(220, 220, 220);

        Stream.of(boton2, boton3, boton4).forEach(boton -> {
            boton.setFont(fuenteBotones);
            boton.setBackground(colorBoton);
            boton.setFocusPainted(false);
        });

        boton2.addActionListener(e -> {
            numeroDeJugadores.set(2);
            jugadores.dispose();
        });

        boton3.addActionListener(e -> {
            numeroDeJugadores.set(3);
            jugadores.dispose();
        });

        boton4.addActionListener(e -> {
            numeroDeJugadores.set(4);
            jugadores.dispose();
        });

        panelBotones.add(boton2);
        panelBotones.add(boton3);
        panelBotones.add(boton4);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        jugadores.add(panelPrincipal);
        jugadores.setVisible(true);

        return numeroDeJugadores.get();
    }

    public static String solicitarNombreDeJugador(int numDeJugador) {
        JDialog nombreJugador = new JDialog();
        nombreJugador.setTitle("Nombre del Jugador");
        nombreJugador.setSize(500, 200);
        nombreJugador.setLocationRelativeTo(null);
        nombreJugador.setModal(true);
        nombreJugador.setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Ingrese el nombre del jugador " + numDeJugador + "\uD83D\uDC64", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 18));
        titulo.setForeground(new Color(154, 32, 206));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JTextField campoNombre = new JTextField();
        campoNombre.setFont(new Font("Noto Sans", Font.PLAIN, 16));
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        panelPrincipal.add(campoNombre, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        JButton botonAceptar = new JButton("Aceptar");

        botonAceptar.setFont(new Font("Noto Sans", Font.BOLD, 18));
        botonAceptar.setBackground(new Color(220, 220, 220));
        botonAceptar.setFocusPainted(false);

        AtomicReference<String> nombre = new AtomicReference<>("");

        botonAceptar.addActionListener(e -> {
            if (!campoNombre.getText().trim().isEmpty()) {
                nombre.set(campoNombre.getText().trim());
                nombreJugador.dispose();
            } else {
                JOptionPane.showMessageDialog(nombreJugador, "Debe ingresar un nombre válido", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        campoNombre.addActionListener(e -> botonAceptar.doClick());

        panelBoton.add(botonAceptar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        nombreJugador.add(panelPrincipal);
        nombreJugador.setVisible(true);

        return nombre.get();
    }

    public static int solicitarModoDeJuego() {
        AtomicInteger modoSeleccionado = new AtomicInteger(0);

        JDialog modo = new JDialog();
        modo.setTitle("Modo de Juego");
        modo.setSize(1100, 700);
        modo.setLocationRelativeTo(null);
        modo.setModal(true);
        modo.setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("MODOS DE JUEGO", SwingConstants.CENTER);
        titulo.setFont(new Font("Noto Sans", Font.BOLD, 36));
        titulo.setForeground(new Color(32, 63, 205));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelModos = new JPanel(new GridLayout(1, 3, 15, 15));

        JPanel panelNaruto = new JPanel(new BorderLayout(5, 5));
        JPanel panelFutbolMatch = new JPanel(new BorderLayout(5, 5));
        JPanel panelGeoMatch = new JPanel(new BorderLayout(5, 5));

        //ImageIcon imagenNaruto = new ImageIcon("C:\\Users\\PC OSTRICH\\Practica-7\\cartasNarutoImagenes\\menuN.png");
        ImageIcon imagenNaruto = new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasNarutoImagenes\\menuN.png");
        Image imagenEscaladaN = imagenNaruto.getImage().getScaledInstance(350, 550, Image.SCALE_SMOOTH);
        imagenNaruto = new ImageIcon(imagenEscaladaN);

        //ImageIcon imagenFutbolMatch = new ImageIcon("C:\\Users\\PC OSTRICH\\Practica-7\\cartasFutbolImagenes\\menuFutbol.png");
        ImageIcon imagenFutbolMatch = new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasFutbolImagenes\\menuFutbol.png");
        Image imagenEscaladaF = imagenFutbolMatch.getImage().getScaledInstance(350, 550, Image.SCALE_SMOOTH);
        imagenFutbolMatch = new ImageIcon(imagenEscaladaF);

        //ImageIcon imagenGeoMatch = new ImageIcon("C:\\Users\\PC OSTRICH\\Practica-7\\cartasGeoMatchImagenes\\menuGeo.png");
        ImageIcon imagenGeoMatch = new ImageIcon("C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasGeoMatchImagenes\\menuGeo.png");
        Image imagenEscaladaG = imagenGeoMatch.getImage().getScaledInstance(350, 550, Image.SCALE_SMOOTH);
        imagenGeoMatch = new ImageIcon(imagenEscaladaG);

        JLabel labelImagenNaruto = new JLabel(imagenNaruto);
        JLabel labelImagenFutbol = new JLabel(imagenFutbolMatch);
        JLabel labelImagenModo3 = new JLabel(imagenGeoMatch);

        JButton botonNaruto = new JButton(" Naruto ");
        JButton botonFutbol = new JButton(" FutbolMatch ");
        JButton botonModo3 = new JButton(" GeoMatch ");

        Font fuenteBotones = new Font("Noto Sans", Font.BOLD, 18);
        Color colorBoton = new Color(220, 220, 220);

        Stream.of(botonNaruto, botonFutbol, botonModo3).forEach(boton -> {
            boton.setFont(fuenteBotones);
            boton.setBackground(colorBoton);
            boton.setFocusPainted(false);
        });

        UIManager.put("ToolTip.background", new Color(238, 238, 238));
        UIManager.put("ToolTip.foreground", Color.BLACK);
        UIManager.put("ToolTip.font", new Font("Noto Sans", Font.PLAIN, 14));
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(32, 63, 205), 3, true));


        botonNaruto.setToolTipText(
                "<html><b style='color:black;'>" + "\uD83C\uDF00" + " Modo Naruto</b><br>" +
                        "Relaciona a cada ninja con su aldea. ¡Demuestra tu conocimiento shinobi!</html>"
        );

        botonFutbol.setToolTipText(
                "<html><b style='color:black;'>" + "\u26BD" + " Modo Fútbol</b><br>" +
                        "Asocia correctamente a cada equipo con su liga. ¿Eres un verdadero fan?</html>"
        );

        botonModo3.setToolTipText(
                "<html><b style='color:black;'>" + "\uD83C\uDF0D" + " Modo GeoMatch</b><br>" +
                        "Vincula cada ciudad con su país. ¡Pon a prueba tu geografía!</html>"
        );

        botonNaruto.addActionListener(e -> {
            modoSeleccionado.set(1);
            modo.dispose();
        });

        botonFutbol.addActionListener(e -> {
            modoSeleccionado.set(2);
            modo.dispose();
        });

        botonModo3.addActionListener(e -> {
            modoSeleccionado.set(3);
            modo.dispose();
        });

        panelNaruto.add(labelImagenNaruto, BorderLayout.CENTER);
        panelNaruto.add(botonNaruto, BorderLayout.SOUTH);

        panelFutbolMatch.add(labelImagenFutbol, BorderLayout.CENTER);
        panelFutbolMatch.add(botonFutbol, BorderLayout.SOUTH);

        panelGeoMatch.add(labelImagenModo3, BorderLayout.CENTER);
        panelGeoMatch.add(botonModo3, BorderLayout.SOUTH);

        panelModos.add(panelNaruto);
        panelModos.add(panelFutbolMatch);
        panelModos.add(panelGeoMatch);

        panelPrincipal.add(panelModos, BorderLayout.CENTER);
        modo.add(panelPrincipal);
        modo.setVisible(true);

        return modoSeleccionado.get();
    }

    public static String getTituloDeModo(int modo) {
        return switch (modo) {
            case 1 -> " \uD83C\uDF00 Naruto ";
            case 2 -> " \u26BD FutbolMatch ";
            case 3 -> " \uD83C\uDF0D GeoMatch ";
            default -> "";
        };
    }

    public static String getBannerPrincipal(int modo) {
        return switch (modo) {
            case 1 -> "C:\\Users\\PC OSTRICH\\Practica-7\\bannerNaruto.png";
            // case 1 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\bannerNaruto.png";
            case 2 -> "C:\\Users\\PC OSTRICH\\Practica-7\\bannerFutbolMatch.png";
            //case 2 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\bannerFutbolMatch.png";
            case 3 -> "C:\\Users\\PC OSTRICH\\Practica-7\\bannerGeoMatch.png";
            //case 3 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\bannerGeoMatch.png";
            default -> "";
        };
    }

    public static String getBannerCentral(int modo) {
        return switch (modo) {
            case 1 -> "C:\\Users\\PC OSTRICH\\Practica-7\\NarutoCentral.gif";
            // case 1 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\NarutoCentral.gif"
            case 2 -> "C:\\Users\\PC OSTRICH\\Practica-7\\futbolMatchCentral.gif";
            // case 2 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\futbolMatchCentral.gif";
            case 3 -> "C:\\Users\\PC OSTRICH\\Practica-7\\GeoMatchCentral.gif";
            // case 3 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\GeoMatchCentral.gif";
            default -> "";
        };
    }

    public static String getCartasOcultas(int modo) {
        return switch (modo) {
            case 1 -> "C:\\Users\\PC OSTRICH\\Practica-7\\cartasNarutoImagenes\\fondoNaruto.png";
            // case 1 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasNarutoImagenes\\fondoNaruto.png";
            case 2 -> "C:\\Users\\PC OSTRICH\\Practica-7\\cartasFutbolImagenes\\fondoFut.png";
            // case 2 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasFutbolImagenes\\fondoFut.png";
            case 3 -> "C:\\Users\\PC OSTRICH\\Practica-7\\cartasGeoMatchImagenes\\PaisGeo.png";
            // case 3 -> "C:\\Users\\14321\\IdeaProjects\\Practica-7\\cartasGeoMatchImagenes\\PaisGeo.png";
            default -> "";
        };
    }

    public static void mostrarInstrucciones(int modo) {
        switch (modo) {
            case 1 -> JOptionPane.showMessageDialog(
                    null,
                    "• El juego consta de 20 cartas, en las cuales hay 10 ninjas y 10 aldeas.\n" +
                            "• Durante el turno voltearás 2 cartas con el objetivo de encontrar 2 cartas que sean pareja.\n" +
                            "• Dos cartas son parejas cuando encuentras a un ninja y a la aldea a la que este pertenece.\n" +
                            "• Si encuentras una pareja correcta, las cartas permanecen visibles, ganas 1 punto y vuelves a tener tu turno.\n" +
                            "• Si encuentras dos ninjas o dos aldeas en un mismo turno e inclusive un ninja y otra aldea de la cual no es originario,\n" +
                            " las cartas vuelven a ocultarse.\n" +
                            "• Este juego termina cuando todas las parejas han sido encontradas.\n","\uD83C\uDF00 Instrucciones Modo Naruto", JOptionPane.INFORMATION_MESSAGE
            );

            case 2 -> JOptionPane.showMessageDialog(
                    null,
                    "• El juego consta de 20 cartas, en las cuales hay 10 equipos de Fútbol y 10 ligas.\n" +
                            "• Durante el turno voltearás 2 cartas con el objetivo de encontrar 2 cartas que sean pareja.\n" +
                            "• Dos cartas son parejas cuando emparejas correctamente un equipo con la liga en la que compite.\n" +
                            "• Si encuentras una pareja correcta, las cartas permanecen visibles, ganas 1 punto y vuelves a tener tu turno.\n" +
                            "• Si encuentras dos equipos o dos ligas en un mismo turno e inclusive un equipo y otra liga de la cual no es \n" +
                            "partícipe, las cartas vuelven a ocultarse.\n" +
                            "• Este juego termina cuando todas las parejas han sido encontradas.\n","\u26BD Instrucciones Modo Fútbol",
                    JOptionPane.INFORMATION_MESSAGE
            );

            case 3 -> JOptionPane.showMessageDialog(
                    null,
                    "• El juego consta de 20 cartas, en las cuales hay 10 ciudades y 10 países.\n" +
                            "• Durante el turno voltearás 2 cartas con el objetivo de encontrar 2 cartas que sean pareja.\n" +
                            "• Dos cartas son parejas cuando unes una ciudad con el país al que pertenece.\n" +
                            "• Si encuentras una pareja correcta, las cartas permanecen visibles, ganas 1 punto y vuelves a tener tu turno.\n" +
                            "• Si encuentras dos ciudades o dos países en un mismo turno e inclusive una ciudad y otro país el cual no tiene \n" +
                            "esta ciudad, las cartas vuelven a ocultarse.\n" +
                            "• Este juego termina cuando todas las parejas han sido encontradas.\n","\uD83C\uDF0D Instrucciones Modo GeoMatch",
                    JOptionPane.INFORMATION_MESSAGE
            );
            default -> JOptionPane.showMessageDialog(null, "Modo no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

