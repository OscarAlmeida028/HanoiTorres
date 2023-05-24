import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.Stack;

public class
TowerManager {

    private Stack<String> torre1 = new Stack();
    private Stack<String> torre2 = new Stack();
    private Stack<String> torre3 = new Stack();

    private DefaultTableModel modelTorre1 = new DefaultTableModel();
    private DefaultTableModel modelTorre2 = new DefaultTableModel();
    private DefaultTableModel modelTorre3 = new DefaultTableModel();
    int objetivo = 0;
    long numMinMov = 0;
    long contNumMov = 0;
    boolean Stop = false;

    public TowerManager(){

        modelTorre1.setRowCount(10);
        modelTorre2.setRowCount(10);
        modelTorre3.setRowCount(10);

    }

    public void Limpiar(){
        numMinMov = 0;
        contNumMov = 0;
    }

    public void UpdateContNumMov(){
        contNumMov++;
    }

    public long getNumMinMov() {
        return numMinMov;
    }

    public long getContNumMov() {
        return contNumMov;
    }

    public void Init(int objetivo) {
        torre1.clear();
        torre2.clear();
        torre3.clear();

        numMinMov = (long)Math.pow(2, objetivo) - 1;

        contNumMov = 0;

        this.objetivo = objetivo;

        for (int i=objetivo; i >= 1; i--){
            String Disco = "";
            for (int j=i; j > 0; j--){
                Disco += "#";
            }

            torre1.push(Disco);
        }

    }

    public DefaultTableModel PresentarTorre(int torreNum){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Torre");
        Stack<String> torreTemp = new Stack<>();
        switch (torreNum){
            case 1:
                torreTemp.addAll(torre1);
                break;
            case 2:
                torreTemp.addAll(torre2);
                break;
            case 3:
                torreTemp.addAll(torre3);
                break;
        }

        while(!torreTemp.isEmpty()){
            model.addRow(new Object[]{torreTemp.pop()});
        }

        return model;
    }



    public void moveFrom1To2() {
        if(torre1.isEmpty()) return;

        String discoTop = torre1.peek();

        if(!torre2.isEmpty()){
            if(discoTop.compareTo(torre2.peek()) > 0) return;
        }

        torre2.push(torre1.pop());

        UpdateContNumMov();
    }
    public void moveFrom1To3() {
        if(torre1.isEmpty()) return;

        String discoTop = torre1.peek();

        if(!torre3.isEmpty()){
            if(discoTop.compareTo(torre3.peek()) > 0) return;
        }

        torre3.push(torre1.pop());

        UpdateContNumMov();

        HasWon();
    }

    private void HasWon() {
        if(torre1.isEmpty() && torre2.isEmpty()){
            JOptionPane.showMessageDialog(null, "Lo lograste!!!");
        }
    }

    public void moveFrom2To1() {
        if(torre2.isEmpty()) return;

        String discoTop = torre2.peek();

        if(!torre1.isEmpty()){
            if(discoTop.compareTo(torre1.peek()) > 0) return;
        }

        torre1.push(torre2.pop());

        UpdateContNumMov();
    }

    public void moveFrom2To3() {
        if(torre2.isEmpty()) return;

        String discoTop = torre2.peek();

        if(!torre3.isEmpty()){
            if(discoTop.compareTo(torre3.peek()) > 0) return;
        }

        torre3.push(torre2.pop());

        UpdateContNumMov();

        HasWon();
    }

    public void moveFrom3To1() {
        if(torre3.isEmpty()) return;

        String discoTop = torre3.peek();

        if(!torre1.isEmpty()){
            if(discoTop.compareTo(torre1.peek()) > 0) return;
        }

        torre1.push(torre3.pop());

        UpdateContNumMov();
    }

    public void moveFrom3To2() {
        if(torre3.isEmpty()) return;

        String discoTop = torre3.peek();

        if(!torre2.isEmpty()){
            if(discoTop.compareTo(torre2.peek()) > 0) return;
        }

        torre2.push(torre3.pop());

        UpdateContNumMov();
    }

    boolean stop = false;
    JTable jtable1;
    JTable jtable2;
    JTable jtable3;

    public void Resolver(JTable jtable1, JTable jtable2, JTable jtable3){
        stop = false;
        this.jtable1 = jtable1;
        this.jtable2 = jtable2;
        this.jtable3 = jtable3;
        HanoiRecursivo(objetivo, torre1, torre2, torre3);

    }

    private void OrigenDestino(Stack<String> torreOrigen, Stack<String> torreDestino){
        if(!stop){
            torreDestino.push(torreOrigen.pop());

            FillTowers(1, jtable1);
            FillTowers(2, jtable2);
            FillTowers(3, jtable3);

            UpdateContNumMov();

            JOptionPane pane = new JOptionPane("Paso #" + contNumMov + "\n\n Continuar?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
            JDialog dialog = pane.createDialog("Numero de Pasos");
            dialog.setLocation(600,600);
            dialog.setVisible(true);

            int opt = (int) pane.getValue();

            if(opt == JOptionPane.NO_OPTION){
                stop = true;
            }
        }
    }

    private void HanoiRecursivo(int objetivo, Stack<String> torreOrigen, Stack<String> torreAuxiliar,Stack<String> torreDestino){
        if(objetivo == 1){
            OrigenDestino(torreOrigen, torreDestino);
        }
        else{
            HanoiRecursivo(objetivo-1, torreOrigen, torreDestino, torreAuxiliar);

            OrigenDestino(torreOrigen, torreDestino);

            HanoiRecursivo(objetivo-1, torreAuxiliar, torreOrigen, torreDestino);
        }
    }

    public void FillTowers(int towerNumber, JTable tableTower) {

        tableTower.setModel(PresentarTorre(towerNumber));

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        tableTower.getColumnModel().getColumn(0).setCellRenderer(render);
    }

    public void ResolveAnyUpdate(JTable jtable1, JTable jtable2, JTable jtable3){
        stop = false;
        this.jtable1 = jtable1;
        this.jtable2 = jtable2;
        this.jtable3 = jtable3;
        HanoiRecursivoAny(objetivo, torre1, torre2, torre3);

        FillTowers(1, jtable1);
        FillTowers(2, jtable2);
        FillTowers(3, jtable3);
    }

    public void HanoiRecursivoAny(int objetivo, Stack<String> rodA, Stack<String> rodB, Stack<String> rodC) {
        if (objetivo == 0) return;

        Stack<String>[] rods = new Stack[]{rodA, rodB, rodC};

        // Find the rod with the disk n.
        Stack<String> sourceRod = null, targetRod = rodC, auxRod = null;
        for (Stack<String> rod : rods) {
            if (!rod.isEmpty() && rod.peek().length() == objetivo) {
                sourceRod = rod;
                auxRod = rod == rodA ? (targetRod == rodB ? rodC : rodB) : (targetRod == rodC ? rodA : rodC);
                break;
            }
        }

        if (sourceRod == targetRod) {
            // If the n-th disk is already at the target, solve for n-1
            HanoiRecursivoAny(objetivo - 1, rodA, rodB, rodC);
        } else {
            // Move all disks smaller than n from source to aux
            moveDisks(sourceRod, auxRod, objetivo - 1);

            // Move n-th disk to target
            targetRod.push(sourceRod.pop());

            // Move disks back from aux to source
            moveDisks(auxRod, sourceRod, objetivo - 1);

            // Solve for n-1
            HanoiRecursivoAny(objetivo - 1, rodA, rodB, rodC);
        }
    }

    public void moveDisks(Stack<String> source, Stack<String> target, int objetivo) {
        if (objetivo <= 0 || source.isEmpty()) return;

        if (source.peek().length() == objetivo) {
            target.push(source.pop());
            OrigenDestinoAny(source, target);
        } else {
            moveDisks(source, target, objetivo - 1);
        }
    }

    private void OrigenDestinoAny(Stack<String> torreOrigen, Stack<String> torreDestino){
        if(!stop){
            torreDestino.push(torreOrigen.pop());

            FillTowers(1, jtable1);
            FillTowers(2, jtable2);
            FillTowers(3, jtable3);

            UpdateContNumMov();

            JOptionPane pane = new JOptionPane("Paso #" + contNumMov + "\n\n Continuar?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
            JDialog dialog = pane.createDialog("Numero de Pasos");
            dialog.setLocation(600,600);
            dialog.setVisible(true);

            int opt = (int) pane.getValue();

            if(opt == JOptionPane.NO_OPTION){
                stop = true;
            }
        }
    }
}
