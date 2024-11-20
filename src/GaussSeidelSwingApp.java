import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GaussSeidelSwingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GaussSeidelSwingApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Método de Gauss-Seidel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Fecha y hora en la parte superior
        JLabel dateTimeLabel = new JLabel();
        updateDateTime(dateTimeLabel);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(dateTimeLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel central dividido en tres secciones
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Sección 1: Entrada de datos
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new TitledBorder("Especificar datos de entrada"));
        JTextArea inputArea = new JTextArea(5, 30);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        centerPanel.add(inputPanel);

        // Sección 2: Botón de cálculo
        JPanel calculatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton calculateButton = new JButton("Calcular");
        JLabel statusLabel = new JLabel("Esperando entrada...");
        calculatePanel.add(calculateButton);
        calculatePanel.add(statusLabel);
        centerPanel.add(calculatePanel);

        // Sección 3: Resultados
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(new TitledBorder("Resultados"));
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        centerPanel.add(resultPanel);

        // Añadir el panel central al panel principal
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Añadir lógica al botón de cálculo
        calculateButton.addActionListener(e -> {
            String input = inputArea.getText().trim();
            if (input.isEmpty()) {
                statusLabel.setText("Por favor, introduce los datos de entrada.");
            } else {
                // Llamamos a la función de Gauss-Seidel
                String result = gaussSeidel();
                resultArea.setText(result);
                statusLabel.setText("Cálculo completado.");
            }
        });

        // Mostrar la ventana
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    // Método para actualizar la fecha y hora en tiempo real
    private static void updateDateTime(JLabel label) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> label.setText("Fecha y hora: " + sdf.format(new Date())));
            }
        }, 0, 1000);
    }

    // Método de Gauss-Seidel
    private static String gaussSeidel() {
        // Inicialización de variables
        double x = 0, y = 0, z = 0;
        double xPrev, yPrev, zPrev;
        int iterations = 10; // Número de iteraciones
        StringBuilder result = new StringBuilder("Iteraciones del Método de Gauss-Seidel:\n");

        // Método de Gauss-Seidel iterativo
        for (int i = 1; i <= iterations; i++) {
            // Guardar valores previos
            xPrev = x;
            yPrev = y;
            zPrev = z;

            // Actualizar valores de x, y, z
            x = (1 - y + z) / 3;
            y = (2 - 2 * x - z) / 4;
            z = (3 - x - 2 * y) / 5;

            // Mostrar el progreso de cada iteración
            result.append(String.format("Iteración %d: x = %.3f, y = %.3f, z = %.3f\n", i, x, y, z));

            // Si la diferencia entre iteraciones es pequeña, salimos
            if (Math.abs(x - xPrev) < 1e-3 && Math.abs(y - yPrev) < 1e-3 && Math.abs(z - zPrev) < 1e-3) {
                break;
            }
        }

        // Devolver el resultado
        result.append(String.format("\nSolución final: x = %.3f, y = %.3f, z = %.3f", x, y, z));
        return result.toString();
    }
}
