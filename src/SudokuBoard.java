/**
 * klasa odpowiedzialna za budowanie i rozwiązanie planszy Sudoku
 */

import java.util.Random;

public class SudokuBoard {

    private int[][] board;
    private static final int BOARD_SIZE = 9;

    public SudokuBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
    }

    public void generateNewBoard() {
        fillValues();
        removeKDigits();
    }

    private void fillValues() {
        fillRemaining(0, 0);
    }

    private boolean fillRemaining(int i, int j) {
        if (i == BOARD_SIZE - 1 && j == BOARD_SIZE) {
            return true;
        }

        if (j == BOARD_SIZE) {
            i++;
            j = 0;
        }

        if (board[i][j] != 0) {
            return fillRemaining(i, j + 1);
        }

        for (int num = 1; num <= BOARD_SIZE; num++) {
            if (isSafe(i, j, num)) {
                board[i][j] = num;
                if (fillRemaining(i, j + 1)) {
                    return true;
                }
            }
        }
        board[i][j] = 0;
        return false;
    }

    private void removeKDigits() {
        int digitsToRemovePerBox = 5;
        for (int boxRow = 0; boxRow < BOARD_SIZE; boxRow += 3) {
            for (int boxCol = 0; boxCol < BOARD_SIZE; boxCol += 3) {
                int digitsRemoved = 0;
                while (digitsRemoved < digitsToRemovePerBox) {
                    int i = boxRow + new Random().nextInt(3);
                    int j = boxCol + new Random().nextInt(3);
                    if (board[i][j] != 0) {
                        digitsRemoved++;
                        board[i][j] = 0;
                    }
                }
            }
        }
    }


    private boolean isSafe(int row, int col, int num) {
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(row - row % 3, col - col % 3, num);
    }

    private boolean isInRow(int row, int num) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int num) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int row, int col, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[row + i][col + j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkSolution() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = board[row][col];
                if (value == 0 || !isSafe(row, col, value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getCellValue(int row, int col) {
        return board[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }
}

