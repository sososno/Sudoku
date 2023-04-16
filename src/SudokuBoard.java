import java.util.*;

public class SudokuBoard {
    public static final int BOARD_SIZE = 9;
    private int[][] puzzle;
    private int[][] solution;

    public SudokuBoard() {
        puzzle = new int[BOARD_SIZE][BOARD_SIZE];
        solution = new int[BOARD_SIZE][BOARD_SIZE];
        generateNewPuzzle();
    }

    public void generateNewPuzzle() {
        fillValues();
        removeKDigits();
    }

    private boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (row == BOARD_SIZE - 1 && col == BOARD_SIZE) {
            return true;
        }

        if (col == BOARD_SIZE) {
            row++;
            col = 0;
        }

        if (puzzle[row][col] != 0) {
            return solve(row, col + 1);
        }

        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (isSafe(row, col, num)) {
                puzzle[row][col] = num;

                if (solve(row, col + 1)) {
                    return true;
                }
            }

            puzzle[row][col] = 0;
        }

        return false;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public int[][] getSolution() {
        solve();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(puzzle[i], 0, solution[i], 0, BOARD_SIZE);
        }
        return solution;
    }

    private void fillValues() {
        fillValues(0, 0);
    }

    private boolean fillValues(int row, int col) {
        if (row == BOARD_SIZE - 1 && col == BOARD_SIZE) {
            return true;
        }

        if (col == BOARD_SIZE) {
            row++;
            col = 0;
        }

        if (puzzle[row][col] != 0) {
            return fillValues(row, col + 1);
        }

        List< Integer > numbers = new ArrayList<>();
        for (int i = 1; i <= BOARD_SIZE; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int num : numbers) {
            if (isSafe(row, col, num)) {
                puzzle[row][col] = num;

                if (fillValues(row, col + 1)) {
                    return true;
                }
            }

            puzzle[row][col] = 0;
        }

        return false;
    }

    private boolean isSafe(int row, int col, int num) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (puzzle[row][i] == num || puzzle[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzzle[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private void removeKDigits() {
        int k = 40;
        while (k != 0) {
            int i = (int) (Math.random() * BOARD_SIZE);
            int j = (int) (Math.random() * BOARD_SIZE);

            if (puzzle[i][j] != 0) {
                puzzle[i][j] = 0;
                k--;
            }
        }
    }
}