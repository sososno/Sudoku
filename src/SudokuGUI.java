/**
 * klasa odpowiedzialna za tworzenie interfejsu użytkownika i obsługę zdarzeń
 */
import javax.swing.*;
import java.awt.*;

public class SudokuGUI {
    private JFrame frame;
    private JTextField[][] cells;
    private SudokuBoard sudokuBoard;
    private JPanel buttonPanel;

    public SudokuGUI() {
        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        sudokuBoard = new SudokuBoard();
        cells = new JTextField[SudokuBoard.BOARD_SIZE][SudokuBoard.BOARD_SIZE];
        frame.add(createBoard(), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        createAndShowGUI();
    }

    private JPanel createBoard() {
        JPanel board = new JPanel(new GridLayout(SudokuBoard.BOARD_SIZE, SudokuBoard.BOARD_SIZE));
        for (int row = 0; row < SudokuBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < SudokuBoard.BOARD_SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                board.add(cells[row][col]);
            }
        }
        return board;
    }

    private void createAndShowGUI() {
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> newGame());
        buttonPanel.add(newGameButton);

        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> check());
        buttonPanel.add(checkButton);

        JButton showSolutionButton = new JButton("Show Solution");
        showSolutionButton.addActionListener(e -> showSolution());
        buttonPanel.add(showSolutionButton);

        frame.setVisible(true);
    }
    private void newGame() {
        sudokuBoard.generateNewPuzzle();
        int[][] puzzle = sudokuBoard.getPuzzle();
        for (int row = 0; row < SudokuBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < SudokuBoard.BOARD_SIZE; col++) {
                if (puzzle[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(puzzle[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.LIGHT_GRAY);
                } else {
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);
                    cells[row][col].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void check() {
        boolean isCorrect = true;
        int[][] solution = sudokuBoard.getSolution();
        for (int row = 0; row < SudokuBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < SudokuBoard.BOARD_SIZE; col++) {
                String userInput = cells[row][col].getText();
                if (!userInput.equals(String.valueOf(solution[row][col]))) {
                    isCorrect = false;
                    break;
                }
            }
        }
        if (isCorrect) {
            JOptionPane.showMessageDialog(frame, "Congratulations! You solved the puzzle!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Some numbers are incorrect. Keep trying!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void showSolution() {
        int[][] solution = sudokuBoard.getSolution();
        for (int row = 0; row < SudokuBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < SudokuBoard.BOARD_SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].setText(String.valueOf(solution[row][col]));
                    cells[row][col].setEditable(false);
                }
            }
        }
    }
}
