import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Interfaz extends JFrame{
    private JPanel Principal;
    private JTable tableTorreA;
    private JButton btnAtoB;
    private JButton btnAtoC;
    private JComboBox comboBoxNumDiscos;
    private JButton btnIniciar;
    private JButton btnResolver;
    private JButton btnReiniciar;
    private JTextField textNumMinMov;
    private JTextField textNumMov;
    private JTable tableTowerB;
    private JTable tableTowerC;
    private JButton btnBtoA;
    private JButton btnBtoC;
    private JButton btnCtoA;
    private JButton btnCtoB;
    private DefaultTableModel ModelA;
    private DefaultTableModel ModelB;
    private DefaultTableModel ModelC;

    private  TowerManager towerManager;

    public Interfaz() {
        FillNumberDiscs();
        towerManager = new TowerManager();

        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InitTowers();
            }
        });
        btnReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetTowers();
            }
        });
        btnResolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resolver();
            }
        });
        btnResolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resolver();
            }
        });
        btnAtoB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveFrom1To2();
            }
        });
        btnAtoC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveFrom1to3();
            }
        });
        btnBtoA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveFrom2to1();
            }
        });
        btnBtoC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveFrom2to3();
            }
        });
        btnCtoA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveFrom3to1();
            }
        });
        btnCtoB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MoveFrom3to2();
            }
        });

    }

    private void Resolver() {
        towerManager.Resolver(tableTorreA, tableTowerB, tableTowerC);

    }

    private void MoveFrom3to2() {
        towerManager.moveFrom3To2();
        FillTowers(3, tableTowerC);
        FillTowers(2, tableTowerB);
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
    }

    private void MoveFrom3to1() {
        towerManager.moveFrom3To1();
        FillTowers(3, tableTowerC);
        FillTowers(1, tableTorreA);
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
    }

    private void MoveFrom2to3() {
        towerManager.moveFrom2To3();
        FillTowers(2, tableTowerB);
        FillTowers(3, tableTowerC);
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
    }

    private void MoveFrom2to1() {
        towerManager.moveFrom2To1();
        FillTowers(1, tableTorreA);
        FillTowers(2, tableTowerB);
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
    }

    private void MoveFrom1to3() {
        towerManager.moveFrom1To3();
        FillTowers(1, tableTorreA);
        FillTowers(3, tableTowerC);
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
    }

    private void MoveFrom1To2() {
        towerManager.moveFrom1To2();
        FillTowers(1, tableTorreA);
        FillTowers(2, tableTowerB);
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
    }

    private void ResetTowers() {
        Limpiar();
        InitTowers();
    }

    private void InitTowers() {
        towerManager.Init(Integer.parseInt(comboBoxNumDiscos.getSelectedItem().toString()));
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
        textNumMinMov.setText(String.valueOf(towerManager.getNumMinMov()));
        FillTowers(1, tableTorreA);
        FillTowers(2, tableTowerB);
        FillTowers(3, tableTowerC);
    }

    private void FillNumberDiscs() {
        for (int i=3; i <= 10; i++){
            comboBoxNumDiscos.addItem(i);
        }
    }

    public void FillTowers(int towerNumber, JTable tableTower) {

        tableTower.setModel(towerManager.PresentarTorre(towerNumber));

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        tableTower.getColumnModel().getColumn(0).setCellRenderer(render);
    }

    private void Limpiar(){
        towerManager.Limpiar();
        textNumMinMov.setText(String.valueOf(towerManager.getNumMinMov()));
        textNumMov.setText(String.valueOf(towerManager.getContNumMov()));
    }

    public JPanel gerPrincipal(){
        return Principal;
    }

}
