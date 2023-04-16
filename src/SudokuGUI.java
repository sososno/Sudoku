/**
 * klasa odpowiedzialna za tworzenie interfejsu użytkownika i obsługę zdarzeń
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame implements ActionListener {

    private SudokuBoard board;
    private JTextField[][] fields;

    public SudokuGUI() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        board = new SudokuBoard();
        fields = new JTextField[9][9];

        createMenu();
        createBoard();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem checkItem = new JMenuItem("Check");

        newGameItem.addActionListener(this);
        checkItem.addActionListener(this);

        gameMenu.add(newGameItem);
        gameMenu.add(checkItem);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
    }

    private void createBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(9, 9));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                fields[row][col] = new JTextField();
                fields[row][col].setHorizontalAlignment(JTextField.CENTER);
                boardPanel.add(fields[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("New Game".equals(command)) {
            board.generateNewBoard();
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = board.getCellValue(row, col);
                    fields[row][col].setText(value == 0 ? "" : String.valueOf(value));
                }
            }
        } else if ("Check".equals(command)) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    String text = fields[row][col].getText();
                    int value = text.isEmpty() ? 0 : Integer.parseInt(text);
                    board.setCellValue(row, col, value);
                }
            }

            if (board.checkSolution()) {
                JOptionPane.showMessageDialog(this, "Gratulacje! Sudoku zostało poprawnie rozwiązane!");
            } else {
                JOptionPane.showMessageDialog(this, "Niestety, rozwiązanie Sudoku jest niepoprawne.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
