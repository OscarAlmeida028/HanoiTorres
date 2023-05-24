import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        HanoiUI Interface = new HanoiUI();
        Interface.setBounds(500,200,600,600);
        Interface.setContentPane(Interface.getJPanelPrincipal());
        Interface.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Interface.setVisible(true);
    }
}