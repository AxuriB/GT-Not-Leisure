package com.lootgames.sudoku.sudoku;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import com.lootgames.sudoku.Utils.CodecUtils;
import com.lootgames.sudoku.Utils.RandHelper;

import lombok.Getter;
import lombok.Setter;
import ru.timeconqueror.lootgames.api.util.Pos2i;

public class SudokuBoard {

    @Getter
    @Setter
    private long lastClickTime = -1;
    public static long currentTime = 0;
    public static final int SIZE = 9;
    @Getter
    @Setter
    private Integer[][] solution = new Integer[SIZE][SIZE]; // 完整解
    private Integer[][] puzzle = new Integer[SIZE][SIZE]; // 挖空后的谜题
    private Integer[][] player = new Integer[SIZE][SIZE]; // 玩家填写

    /** 根据挖空数量生成棋盘 */
    public void generate(int blanks) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                solution[i][j] = 0;
            }
        }

        fillSolution(0, 0);
        copySolutionToPuzzle();
        removeCells(blanks);
        resetPlayer();
    }

    private boolean fillSolution(int row, int col) {
        if (row == SIZE) return true;
        int nextRow = col == SIZE - 1 ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;
        List<Integer> nums = RandHelper.shuffledList(1, 9);
        for (int n : nums) {
            if (canPlace(solution, row, col, n)) {
                solution[row][col] = n;
                if (fillSolution(nextRow, nextCol)) return true;
                solution[row][col] = 0;
            }
        }
        return false;
    }

    private boolean canPlace(Integer[][] g, int r, int c, int n) {
        for (int i = 0; i < SIZE; i++) if (g[r][i] == n || g[i][c] == n) return false;
        int br = (r / 3) * 3, bc = (c / 3) * 3;
        for (int rr = br; rr < br + 3; rr++) for (int cc = bc; cc < bc + 3; cc++) if (g[rr][cc] == n) return false;
        return true;
    }

    private void copySolutionToPuzzle() {
        for (int i = 0; i < SIZE; i++) System.arraycopy(solution[i], 0, puzzle[i], 0, SIZE);
    }

    private void removeCells(int blanks) {
        List<Integer> pos = RandHelper.shuffledList(0, SIZE * SIZE - 1);
        int removed = 0;

        for (int idx : pos) {
            int r = idx / SIZE;
            int c = idx % SIZE;

            int backup = puzzle[r][c];
            puzzle[r][c] = 0;

            if (countSolutions(new Integer[SIZE][SIZE], 0, 0) != 1) {
                // 恢复，不能挖这个格子
                puzzle[r][c] = backup;
            } else {
                removed++;
                if (removed >= blanks) break;
            }
        }
    }

    private int countSolutions(Integer[][] board, int row, int col) {
        // 初始复制 puzzle 给 board
        if (row == 0 && col == 0) {
            for (int i = 0; i < SIZE; i++) {
                System.arraycopy(puzzle[i], 0, board[i], 0, SIZE);
            }
        }

        if (row == SIZE) return 1;

        int nextRow = col == SIZE - 1 ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;

        if (board[row][col] != 0) {
            return countSolutions(board, nextRow, nextCol);
        }

        int totalSolutions = 0;
        for (int n = 1; n <= 9; n++) {
            if (canPlace(board, row, col, n)) {
                board[row][col] = n;
                totalSolutions += countSolutions(board, nextRow, nextCol);
                if (totalSolutions > 1) return totalSolutions; // 超过一个解，提前退出
                board[row][col] = 0;
            }
        }
        return totalSolutions;
    }

    private void resetPlayer() {
        for (int i = 0; i < SIZE; i++) System.arraycopy(puzzle[i], 0, player[i], 0, SIZE);
    }

    public boolean isGenerated() {
        return solution[0][0] != 0;
    }

    public int getPlayerValue(int x, int y) {
        Integer v = player[x][y];
        return v != null ? v : 0;
    }

    public int getPlayerValue(Pos2i pos) {
        return getPlayerValue(pos.getX(), pos.getY());
    }

    public int getSolutionValue(int x, int y) {
        Integer v = solution[x][y];
        return v != null ? v : 0;
    }

    public int getSolutionValue(Pos2i pos) {
        return getSolutionValue(pos.getX(), pos.getY());
    }

    public int getPuzzleValue(int x, int y) {
        Integer v = puzzle[x][y];
        return v != null ? v : 0;
    }

    public int getPuzzleValue(Pos2i pos) {
        return getPuzzleValue(pos.getX(), pos.getY());
    }

    public void cycleValueMinus(Pos2i pos) {
        int r = pos.getX(), c = pos.getY();
        if (puzzle[r][c] != 0) return;
        player[r][c] = (player[r][c] + 9) % 10;
    }

    public void cycleValueAdd(Pos2i pos) {
        int r = pos.getX(), c = pos.getY();
        if (puzzle[r][c] != 0) return;
        player[r][c] = (player[r][c] + 1) % 10;
    }

    public boolean checkWin() {
        for (int i = 0; i < SIZE; i++) for (int j = 0; j < SIZE; j++) {
            if (player[i][j] == 0 || player[i][j].equals(solution[i][j])) return false;
        }
        return true;
    }

    public NBTTagCompound writeNBT() {
        NBTTagCompound t = new NBTTagCompound();
        t.setTag("puzzle", CodecUtils.write2DimArr(puzzle, CodecUtils.INT_CODEC));
        t.setTag("player", CodecUtils.write2DimArr(player, CodecUtils.INT_CODEC));
        t.setTag("solution", CodecUtils.write2DimArr(solution, CodecUtils.INT_CODEC));
        return t;
    }

    public void readNBT(NBTTagCompound t) {
        puzzle = CodecUtils.read2DimArr(t.getCompoundTag("puzzle"), Integer.class, CodecUtils.INT_CODEC);
        player = CodecUtils.read2DimArr(t.getCompoundTag("player"), Integer.class, CodecUtils.INT_CODEC);
        solution = CodecUtils.read2DimArr(t.getCompoundTag("solution"), Integer.class, CodecUtils.INT_CODEC);
    }

    public void cSetPlayerValue(Pos2i pos, int value) {
        int r = pos.getX(), c = pos.getY();

        if (puzzle[r][c] == 0) {
            player[r][c] = value;
        }
    }

    public int countTotalBlanks() {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (puzzle[i][j] != null && puzzle[i][j] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countFilledCells() {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (puzzle[i][j] != null && puzzle[i][j] == 0 && player[i][j] != null && player[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
