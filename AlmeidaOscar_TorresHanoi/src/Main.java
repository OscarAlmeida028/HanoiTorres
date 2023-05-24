import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
    public static void main(String[] args) {
        Interfaz interfaz = new Interfaz();
        interfaz.setContentPane(interfaz.gerPrincipal());
        interfaz.setVisible(true);
        interfaz.setBounds(100,100, 600,600);
        interfaz.setTitle("Torres de Hanoi");
        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}