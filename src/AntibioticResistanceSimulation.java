package antibioticresistancesimulation;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/** The AntibiotiResistanceSimulation class builds the GUI of the 
 * simulation and contains the main class of the project. 
 * The AntibioticResistanceSimulation class contains four variables:
 * (1) State colony: An State object to represent our bacteria colony.
 * (2) double[][] population: A 2-D array containing the model that represents
 * bacteria colony to be used by the graphics builder. 
 * (3) JLabel averageLabel: A label to print the average gene expression level 
 * of the colony.
 * (4) JFrame frame: Base frame of our GUI. 
 */

public class AntibioticResistanceSimulation extends JFrame {
    
double[][] population  = new double[25][25];
State colony = new State();
JLabel averageLabel = new JLabel();
JFrame frame;


    public static void main(String[] args) {
        new AntibioticResistanceSimulation();
    }

    public AntibioticResistanceSimulation() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                
                /* Initialize the POPULATION tabel model to contain 1 in all of 
                its cells. This paints all of the JTabel cells to apper white
                upon start of the application.
                */
                for(int i=0; i<25; i++){
                    for(int j=0; j<25; j++){
                        population[i][j] = 1;
                    }
                }
                
                // Basic set-up for the main frame of GUI. 
                frame = new JFrame("AntibioticResistanceSimulation");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setBackground(new java.awt.Color(0, 0, 0));
                frame.add(new TestPane(population));
                
                
                // Creating JButtons for Restart and dosing different antibiotic
                // solutions. 
                JButton restartButton = new javax.swing.JButton();
                restartButton.setText("RESTART");
                restartButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        colony.randomStart();
                        population=colony.exportModel();
                    }
                });
                JButton doseBlackButton = new javax.swing.JButton();
                doseBlackButton.setText("DOSE ANTIBIOTICS ①");
                doseBlackButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        colony.doseAntibio('b');
                    }
                });  
                JButton doseWhiteButton = new javax.swing.JButton();
                doseWhiteButton.setText("DOSE ANTIBIOTICS ②");
                doseWhiteButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        colony.doseAntibio('w');
                    }
                });   
                
                // Position all the buttons and the averageLabel onto the bottom
                // of the frame.
                JPanel bottomPanel = new JPanel();
                bottomPanel.add(restartButton);
                bottomPanel.add(doseBlackButton);
                bottomPanel.add(doseWhiteButton);
                bottomPanel.add(averageLabel);
                frame.add(bottomPanel, BorderLayout.PAGE_END);
                
                frame.pack();

                // Timer object used to update the state of the colony every 
                // second.
                Timer timer = new Timer(1000, new AntibioticResistanceSimulation.TimerListener());
                timer.start();
                
                // Provoke the start of the simulation.
                colony.randomStart();
                population = colony.exportModel();
                
                frame.setVisible(true);
            }
        });
    }
    
        /** The TimerListener class contains the actions to be performed
         * at each second of the simulation. The action listener first 
         * updates the state of the COLONY using updateState(), updates the
         * table model POPULATION by exporting COLONY as an appropriate form 
         * using exportModel(), updates the text displayed by averageLabel using
         * average(), then creates and adds a new TestPane based on the updated
         * POPULATION table model to update the graphics displayed on the screen.
         * 
         */
        private class TimerListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e){
                // The updateState() is called twice each second to speed up 
                // the demonstrated process.
                for(int i=0; i<2; i++){
                    colony.updateState();
                }
                population  = colony.exportModel();
                averageLabel.setText("Average: " + colony.average());
                frame.add(new TestPane(population));
                frame.pack();
            }
        }

        /** The following code of TestPane, PaintTableCellRenderer, 
         * AsciiTableModel classes were written by MadProgrammer. The code
         * was modified only to fit the dimensions of the colony, and to detect
         * the current state of the bacteria colony as the basis of the table 
         * model, instead of the original smilely graphics.
         * 
         * The function of the code is to paint the background of each cell of 
         * the JTabel based on the value contained by that cell. In our case, 
         * the higher the gene expression of a bacteria cell, the darker the 
         * JTabel cell appears on the screen.
         * 
         * @Author MadProgrammer
         * URL: https://stackoverflow.com/questions/30552644/how-do-i-color-individual-cells-of-a-jtable-based-on-the-value-in-the-cell
         */
    
        public class TestPane extends JPanel {
        public TestPane(double[][] state) {
            AntibioticResistanceSimulation.AsciiTableModel model = new AntibioticResistanceSimulation.AsciiTableModel();
            model.setData(state);

            JTable table = new JTable(model);
            table.setRowHeight(25);
            Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
            while (columns.hasMoreElements()) {
                TableColumn col = columns.nextElement();
                col.setWidth(25);
                col.setPreferredWidth(25);
                col.setMinWidth(25);
                col.setMaxWidth(25);
            }
            table.setRowHeight(25);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setDefaultRenderer(Object.class, new PaintTableCellRenderer());

            setLayout(new BorderLayout());
            add(new JScrollPane(table));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(630, 660);
        }
    }

        public class PaintTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
            if (value instanceof Double) {
                double distance = (double) value;
                int part = (int) (255 * distance);
                Color color = new Color(part, part, part);
                setBackground(color);
            } else {
                setBackground(Color.WHITE);
            }
            return this;
        }
    }
        public class AsciiTableModel extends AbstractTableModel {
        private double[][] data;
        public AsciiTableModel() {
            data = new double[25][25];
        }
        
        public void setData(double[][] value) {
            data = value;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return 25;
        }

        @Override
        public int getColumnCount() {
            return 25;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
    }
}
