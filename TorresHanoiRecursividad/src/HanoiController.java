import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.Stack;

public class HanoiController {
    private int contNumMovimientos = 0;
    private DefaultTableModel ModeloTorreA, ModeloTorreB, ModeloTorreC;
    private int objetivo = 0;
    private double MinNumMovements;
    boolean Stop = false;
    private Stack <String> PilaTorreA;
    private Stack <String> PilaTorreB;
    private Stack <String> PilaTorreC;

    public HanoiController(JTable TorreA, JTable TorreB, JTable TorreC) {

        ModeloTorreA = (DefaultTableModel) TorreA.getModel();
        ModeloTorreA.setRowCount(10);
        ModeloTorreA.setColumnCount(1);
        ModeloTorreB = (DefaultTableModel) TorreB.getModel();
        ModeloTorreB.setRowCount(10);
        ModeloTorreB.setColumnCount(1);
        ModeloTorreC = (DefaultTableModel) TorreC.getModel();
        ModeloTorreC.setRowCount(10);
        ModeloTorreC.setColumnCount(1);

        DefaultTableCellRenderer rendererA = new DefaultTableCellRenderer();
        rendererA.setHorizontalAlignment(SwingConstants.CENTER);

        TorreA.getColumnModel().getColumn(0).setCellRenderer(rendererA);
        TorreB.getColumnModel().getColumn(0).setCellRenderer(rendererA);
        TorreC.getColumnModel().getColumn(0).setCellRenderer(rendererA);

    }

    public int getContNumMovimientos() {
        return contNumMovimientos;
    }

    public int getObjetivo() {
        return objetivo;
    }


    public void setObjetivo(int objetivo) {
        this.objetivo = objetivo;
    }

    public int LimpiarNumMovimientos() {

        return this.contNumMovimientos = 0;

    }

    public double LimpiarMinNumMovements() {

        return this.MinNumMovements = 0;
    }

    public int SumarCantMovimientos() {

        return this.contNumMovimientos++;

    }

    public void inicializarStackTorres() {

        PilaTorreA = new Stack<String>();
        PilaTorreB = new Stack<String>();
        PilaTorreC = new Stack<String>();

    }

    public int calcularMinNumMovements() {
        MinNumMovements = Math.pow(2, objetivo) - 1;
        return (int) MinNumMovements;
    }

    public Stack<String> armarTorre(Stack<String> PilaTorre) {

        PilaTorre.clear();
        for (int i = 1; i <= objetivo; i++) {

            String disco = "";

            for (int x = i ; x > 0; x--){
                disco += "*";
            }

            PilaTorre.push(disco);

        }

        return PilaTorre;
    }

    public DefaultTableModel dibujarTorre(Stack<String> PilaTorre, DefaultTableModel ModeloTorre) {
        //Revisar constructor de esta clase
        ModeloTorre.setColumnCount(1);
        ModeloTorre.setRowCount(0);
        ModeloTorre.setRowCount(10);
        PilaTorre.sort(((s1, s2) -> s1.compareTo(s2) )); //Ordeno de nuevo para que imprima del mayor al menor

        int RowDisco = (10 - PilaTorre.size());

        if (PilaTorre.size() > 0 ) {
            for (String p:PilaTorre) {
                String [] Dibujo = {p.toString()};

                ModeloTorre.insertRow(RowDisco,Dibujo);
                RowDisco++;
            }
        }
        //ModeloTorre.setRowCount(10);
        return ModeloTorre;
    }

    public Stack<String> MoverDiscos(Stack<String> PilaTorreInicio,Stack<String> PilaTorreDestino){

        if (PilaTorreInicio.size() > 0) {
            PilaTorreInicio.sort(((s1, s2) -> s2.compareTo(s1) )); //Ordeno la pila de inicio para tener el primer disco arriba del stack
            PilaTorreDestino.sort((s1, s2) -> s2.compareTo(s1)); //Ordeno la pila de destino para tener el primer disco arriba del stack
            String discoCima = PilaTorreInicio.peek();
            System.out.println(discoCima);
            if (PilaTorreDestino.size() > 0) {
                System.out.println("Elemento de arriba de la torre destino: "+PilaTorreDestino.peek());
                System.out.println("Compara el discoCima: "+discoCima+" con el elemento de arriba de la torre: "+PilaTorreDestino.peek());
                if(discoCima.compareTo(PilaTorreDestino.peek()) > 0) { //Si es mayor a 0 significa que es menor, de menor longitud el elemento de la torre destino
                    JOptionPane.showMessageDialog(null, "No puede realizar este movimiento.");
                    return PilaTorreDestino; //REVISAR, ANTES MANDABA NULL
                }

            }
            System.out.println("Evaluo las funciones");
            PilaTorreInicio.pop();
            //PilaTorreDestino.clear();
            PilaTorreDestino.push(discoCima);
            SumarCantMovimientos();
        }
        return PilaTorreDestino;
    }

    public Stack<String> MoverDiscosHaciaTorreC(Stack<String> PilaTorreInicio,Stack<String> PilaTorreDestino){

        MoverDiscos(PilaTorreInicio,PilaTorreDestino); //Invoco el metodo de arriba

        if (PilaTorreDestino.size() == objetivo && contNumMovimientos == MinNumMovements) {

            JOptionPane.showMessageDialog(null,"Felicidades, has completado el juego con el mínimo número de movimientos!\n" +
                    "Intenta con otro nivel de dificultad","Felicitaciones",JOptionPane.WARNING_MESSAGE);

        } else if (PilaTorreDestino.size() == objetivo && contNumMovimientos != MinNumMovements) {

            JOptionPane.showMessageDialog(null,"Felicidades, has completado el juego!\n" +
                    "Intenta superar el objetivo mínimo de movimientos","Felicitaciones",JOptionPane.INFORMATION_MESSAGE);
        }
        return PilaTorreDestino;
    }

    public void MoverPlataforma(Stack<String> PilaTorreOrigen,Stack<String> PilaTorreDestino) {
        if( Stop == false ) {

            String Plataforma = PilaTorreOrigen.peek();

            PilaTorreOrigen.pop();

            PilaTorreDestino.push(Plataforma);
        }
    }

    public void ResolverPorRecursividad(int n,Stack<String> PilaTorreOrigen,Stack<String> PilaTorreAuxiliar,Stack<String> PilaTorreDestino) {

        if (PilaTorreOrigen.isEmpty()) {
            // No hay más discos en la torre origen, detener la recursión
            return;
        }

        PilaTorreOrigen.sort(((s1, s2) -> s2.compareTo(s1) )); //Ordeno la pila de inicio para tener el primer disco arriba del stack
        PilaTorreDestino.sort((s1, s2) -> s2.compareTo(s1)); //Ordeno la pila de destino para tener el primer disco arriba del stack
        PilaTorreAuxiliar.sort((s1, s2) -> s2.compareTo(s1)); //Ordeno la pila de destino para tener el primer disco arriba del stack

        if (n > 0) {

            // Mover n-1 discos de la torre origen a la torre auxiliar
            ResolverPorRecursividad(n-1,PilaTorreOrigen, PilaTorreDestino, PilaTorreAuxiliar);

            // Mover el disco n de la torre origen a la torre destino
            String disco = PilaTorreOrigen.pop();
            PilaTorreDestino.push(disco);

            // Mover los n-1 discos restantes de la torre auxiliar a la torre destino
            ResolverPorRecursividad(n-1,PilaTorreAuxiliar, PilaTorreOrigen, PilaTorreDestino);
            SumarCantMovimientos();

            JOptionPane panel = new JOptionPane("Paso N°"+getContNumMovimientos()+"\n\nContinuar?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
            JDialog dialog = panel.createDialog("Numero de pasos");
            dialog.setLocation(600,400);
            dialog.setVisible(true);

            int option = (int) panel.getValue();

            if(option == JOptionPane.NO_OPTION) {
                Stop = true;
            }
        }

    }



}
