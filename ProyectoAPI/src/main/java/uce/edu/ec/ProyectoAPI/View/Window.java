package uce.edu.ec.ProyectoAPI.View;

import uce.edu.ec.ProyectoAPI.Controller.Container;
import uce.edu.ec.ProyectoAPI.Model.MarsFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Window extends JFrame {
    private final Container container;

    private final JComboBox<Integer> comboSol;
    private final JComboBox<String> comboCamara;
    private final JTextArea areaInfo;
    private final JPanel panelMiniaturas;
    private final JScrollPane scrollPane;

    public Window() {
        this.container = new Container();

        setTitle("Consumiendo API");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        comboSol = new JComboBox<>(new Integer[]{1000, 1020, 1040, 1060, 1080, 1100, 1120, 1140, 2000, 3000});
        comboCamara = new JComboBox<>(new String[]{"FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM", "PANCAM", "MINITES"});
        areaInfo = new JTextArea(5, 40);
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("SansSerif", Font.PLAIN, 16));

        panelMiniaturas = new JPanel();
        panelMiniaturas.setLayout(new BoxLayout(panelMiniaturas, BoxLayout.Y_AXIS)); // Usar BoxLayout para el panel verticalmente

        scrollPane = new JScrollPane(panelMiniaturas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        agregarComponentes();
        setVisible(true);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelSeleccion = new JPanel();
        panelSeleccion.add(new JLabel("Sol:"));
        panelSeleccion.add(comboSol);
        panelSeleccion.add(new JLabel("Camara:"));
        panelSeleccion.add(comboCamara);

        JPanel panelInformacion = new JPanel(new BorderLayout());
        panelInformacion.add(new JScrollPane(areaInfo), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        agregarBoton(panelBotones, "Curiosity");
        agregarBoton(panelBotones, "Opportunity");
        agregarBoton(panelBotones, "Spirit");

        panelPrincipal.add(panelSeleccion, BorderLayout.NORTH);
        panelPrincipal.add(panelInformacion, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void agregarBoton(JPanel panel, String nombreRover) {
        JButton boton = new JButton(nombreRover);
        boton.addActionListener(e -> {
            int sol = comboSol.getItemAt(comboSol.getSelectedIndex());
            String camara = comboCamara.getItemAt(comboCamara.getSelectedIndex());

            areaInfo.setText("Buscando fotos para el rover " + nombreRover + ", cámara " + camara + ", sol " + sol + "...\n");
            limpiarPanelMiniaturas();
            new Thread(() -> {
                try {
                    Map<String, List<MarsFilter>> fotos = container.fetchAndPrintPhotosByCameraAndSol(nombreRover.toLowerCase(), camara, sol, areaInfo);
                    SwingUtilities.invokeLater(() -> {
                        mostrarFotos(fotos, nombreRover, camara, sol);
                        int totalResultados = fotos.values().stream().mapToInt(List::size).sum();
                        areaInfo.append("Encontramos: " + totalResultados + " resultado(s) para esta búsqueda\n");
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    areaInfo.append("Error al buscar fotos: " + ex.getMessage() + "\n");
                }
            }).start();
        });
        panel.add(boton);
    }

    private void mostrarFotos(Map<String, List<MarsFilter>> fotosPorCamara, String nombreRover, String camara, int sol) {
        fotosPorCamara.values().forEach(photos -> photos.forEach(photo -> {
            JPanel panelFoto = crearPanelFoto(photo);
            panelMiniaturas.add(panelFoto);
        }));

        // Asegura que el JScrollPane se ajuste automáticamente al contenido vertical
        SwingUtilities.invokeLater(() -> {
            panelMiniaturas.revalidate();
            panelMiniaturas.repaint();
            scrollPane.revalidate(); // Revalidar el scrollPane para ajustar la barra de desplazamiento
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        });
    }

    private JPanel crearPanelFoto(MarsFilter foto) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel etiquetaInfo = new JLabel("<html>ID: " + foto.getPhotoId() + ", Fecha: " + foto.getEarthDate() +
                ", <a href='" + foto.getImageUrl() + "'>URL: " + foto.getImageUrl() + "</a></html>");
        etiquetaInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        etiquetaInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPaginaWeb(foto.getImageUrl());
            }
        });
        panel.add(etiquetaInfo, BorderLayout.NORTH);

        new Thread(() -> {
            ImageIcon iconoImagen = cargarIconoImagen(foto.getImageUrl());
            if (iconoImagen != null) {
                JLabel etiquetaImagen = new JLabel(new ImageIcon(iconoImagen.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                etiquetaImagen.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mostrarImagenCompleta(iconoImagen);
                    }
                });
                panel.add(etiquetaImagen, BorderLayout.CENTER);
                panel.revalidate();
                panel.repaint();
            }
        }).start();

        return panel;
    }

    private ImageIcon cargarIconoImagen(String urlImagen) {
        try {
            URL url = new URL(urlImagen);

            // Leer los primeros bytes para determinar el tipo de contenido
            URLConnection connection = url.openConnection();
            connection.connect();

            // Verificar el tipo de contenido
            String contentType = connection.getContentType();
            if (contentType != null && contentType.startsWith("image")) {
                // Si es una imagen, cargarla como ImageIcon
                return new ImageIcon(url);
            } else {
                // Si no es una imagen directa, verificar si es una página web
                // Leer contenido de la URL para verificar si contiene una imagen
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    boolean contieneImagen = false;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                        if (line.contains("<img")) {
                            contieneImagen = true;
                            break;
                        }
                    }
                    if (contieneImagen) {
                        SwingUtilities.invokeLater(() -> areaInfo.append("La URL: " + urlImagen + " es una página web que puede no ser una imagen directa.\n"));
                    } else {
                        SwingUtilities.invokeLater(() -> areaInfo.append("La URL no corresponde a una imagen directa o no contiene imágenes.\n"));
                    }
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void abrirPaginaWeb(String url) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void mostrarImagenCompleta(ImageIcon iconoImagen) {
        JFrame marcoImagenCompleta = new JFrame();
        marcoImagenCompleta.setTitle("Vista completa");
        marcoImagenCompleta.setSize(800, 600);

        Dimension tamanoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (tamanoPantalla.width - marcoImagenCompleta.getWidth()) / 2;
        int y = (tamanoPantalla.height - marcoImagenCompleta.getHeight()) / 2;
        marcoImagenCompleta.setLocation(x, y);

        JLabel etiquetaImagen = new JLabel(iconoImagen);
        JScrollPane panelDesplazamiento = new JScrollPane(etiquetaImagen);
        marcoImagenCompleta.add(panelDesplazamiento);

        marcoImagenCompleta.setVisible(true);
    }

    private void limpiarPanelMiniaturas() {
        panelMiniaturas.removeAll();
        panelMiniaturas.revalidate();
        panelMiniaturas.repaint();
    }

}
