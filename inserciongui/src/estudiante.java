import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class estudiante {
    private JTextField introduceElNombreDelTextField;
    private JTextField introduceLaCedulaDelTextField;
    private JTextField introduceLaNotaDelTextField;
    private JTextField introduceLaNotaDelTextField1;
    private JButton insertarButton;
    private JPanel PanelMain;
    private JTextField notaB1TextField;
    private JTextField notaB2TextField;
    private JTextField cedulaTextField;

    public estudiante() {
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = introduceElNombreDelTextField.getText();
                String cedula = introduceLaCedulaDelTextField.getText();
                try {
                    double b1 = Double.parseDouble(introduceLaNotaDelTextField.getText().trim());
                    double b2 = Double.parseDouble(introduceLaNotaDelTextField1.getText().trim());

                    // Validar que las notas están en el rango de 0 a 10
                    if (b1 < 0 || b1 > 10 || b2 < 0 || b2 > 10) {
                        JOptionPane.showMessageDialog(null, "Las notas deben estar en el rango de 0 a 10.");
                        return;
                    }

                    insertarEstudiante(new EstudianteData(cedula, nombre, b1, b2));

                    introduceElNombreDelTextField.setText("");
                    introduceLaCedulaDelTextField.setText("");
                    introduceLaNotaDelTextField.setText("");
                    introduceLaNotaDelTextField1.setText("");
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public JPanel getPanelMain() {
        return PanelMain;
    }

    private static void insertarEstudiante(EstudianteData estudiante) {
        String url = "jdbc:mysql://localhost:3306/estudiantes2024a";
        String user = "root";
        String password = "172843";
        String sql = "INSERT INTO estudiantes (Cedula, NOMBRE, b1, b2) VALUES (?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, estudiante.getCedula());
                preparedStatement.setString(2, estudiante.getNombre());
                preparedStatement.setDouble(3, estudiante.getB1());
                preparedStatement.setDouble(4, estudiante.getB2());

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Inserción exitosa en la base de datos.");

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al insertar el estudiante: " + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Controlador JDBC no encontrado.");
        }
    }

    private static class EstudianteData {
        private String cedula;
        private String nombre;
        private double b1;
        private double b2;

        public EstudianteData(String cedula, String nombre, double b1, double b2) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.b1 = b1;
            this.b2 = b2;
        }

        public String getCedula() {
            return cedula;
        }

        public String getNombre() {
            return nombre;
        }

        public double getB1() {
            return b1;
        }

        public double getB2() {
            return b2;
        }
    }
}
