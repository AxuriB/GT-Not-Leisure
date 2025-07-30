package com.lootgames.sudoku.sudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lootgames.sudoku.Utils.DrawHelper;
import com.lootgames.sudoku.block.SudokuTile;

import ru.timeconqueror.lootgames.api.block.tile.BoardGameMasterTile;
import ru.timeconqueror.lootgames.api.util.Pos2i;

public class SudokuRenderer extends TileEntitySpecialRenderer {

    private static final ResourceLocation BOARD = new ResourceLocation("lootgames", "textures/game/ms_board.png");

    @Override
    public void renderTileEntityAt(TileEntity teIn, double x, double y, double z, float partialTicks) {
        SudokuTile te = (SudokuTile) teIn;
        GameSudoku game = te.getGame();
        SudokuBoard board = game.getBoard();
        int size = game.getCurrentBoardSize();

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        BoardGameMasterTile.prepareMatrix(te);

        bindTexture(BOARD);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(1f, 1f, 1f, 1f);

        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                ru.timeconqueror.timecore.api.util.client.DrawHelper
                    .drawTexturedRectByParts(cx, cz, 1, 1, -0.005f, 0, 0, 1, 1, 4);
            }
        }

        GL11.glLineWidth(150.0F);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4f(1f, 1f, 1f, 1f);

        for (int i = 0; i <= size; i++) {
            if (i % 3 == 0) {
                GL11.glVertex3f(i, 0, -0.02f);
                GL11.glVertex3f(i, size, -0.02f);
            }
        }

        for (int i = 0; i <= size; i++) {
            if (i % 3 == 0) {
                GL11.glVertex3f(0, i, -0.02f);
                GL11.glVertex3f(size, i, -0.02f);
            }
        }

        GL11.glEnd();

        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Map<Integer, Integer> numberCounts = new HashMap<>();
        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                int puzzleVal = board.getPuzzleValue(cx, cz);
                int playerVal = board.getPlayerValue(new Pos2i(cx, cz));
                if (puzzleVal != 0) {
                    numberCounts.put(puzzleVal, numberCounts.getOrDefault(puzzleVal, 0) + 1);
                } else if (playerVal != 0) {
                    numberCounts.put(playerVal, numberCounts.getOrDefault(playerVal, 0) + 1);
                }
            }
        }

        Set<Pos2i> duplicatePositions = new HashSet<>();
        Set<Pos2i> correctCompletedPositions = new HashSet<>();

        // Rows
        for (int row = 0; row < size; row++) {
            Map<Integer, Set<Pos2i>> map = new HashMap<>();
            boolean complete = true;
            for (int col = 0; col < size; col++) {
                int val = board.getPlayerValue(new Pos2i(row, col));
                if (val == 0 || val != board.getSolution()[row][col]) complete = false;
                map.computeIfAbsent(val, k -> new HashSet<>())
                    .add(new Pos2i(row, col));
            }
            for (Map.Entry<Integer, Set<Pos2i>> e : map.entrySet()) {
                if (e.getKey() != 0 && e.getValue()
                    .size() > 1) duplicatePositions.addAll(e.getValue());
            }
            if (complete && map.size() == 9) {
                for (Set<Pos2i> set : map.values()) {
                    correctCompletedPositions.addAll(set);
                }
            }
        }

        // Columns
        for (int col = 0; col < size; col++) {
            Map<Integer, Set<Pos2i>> map = new HashMap<>();
            boolean complete = true;
            for (int row = 0; row < size; row++) {
                int val = board.getPlayerValue(new Pos2i(row, col));
                if (val == 0 || val != board.getSolution()[row][col]) complete = false;
                map.computeIfAbsent(val, k -> new HashSet<>())
                    .add(new Pos2i(row, col));
            }
            for (Map.Entry<Integer, Set<Pos2i>> e : map.entrySet()) {
                if (e.getKey() != 0 && e.getValue()
                    .size() > 1) duplicatePositions.addAll(e.getValue());
            }
            if (complete && map.size() == 9) {
                for (Set<Pos2i> set : map.values()) {
                    correctCompletedPositions.addAll(set);
                }
            }
        }

        // Boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                Map<Integer, Set<Pos2i>> map = new HashMap<>();
                boolean complete = true;
                for (int dy = 0; dy < 3; dy++) {
                    for (int dx = 0; dx < 3; dx++) {
                        int r = boxRow * 3 + dy;
                        int c = boxCol * 3 + dx;
                        int val = board.getPlayerValue(new Pos2i(r, c));
                        if (val == 0 || val != board.getSolution()[r][c]) complete = false;
                        map.computeIfAbsent(val, k -> new HashSet<>())
                            .add(new Pos2i(r, c));
                    }
                }
                for (Map.Entry<Integer, Set<Pos2i>> e : map.entrySet()) {
                    if (e.getKey() != 0 && e.getValue()
                        .size() > 1) duplicatePositions.addAll(e.getValue());
                }
                if (complete && map.size() == 9) {
                    for (Set<Pos2i> set : map.values()) {
                        correctCompletedPositions.addAll(set);
                    }
                }
            }
        }

        // 绘制数字部分（颜色区分）
        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                Pos2i pos = new Pos2i(cx, cz);
                int puzzleVal = board.getPuzzleValue(cx, cz);
                int playerVal = board.getPlayerValue(pos);

                int actualVal = puzzleVal != 0 ? puzzleVal : playerVal;
                if (actualVal != 0) {
                    String text = Integer.toString(actualVal);
                    int count = numberCounts.getOrDefault(actualVal, 0);
                    int color;

                    if (count > 9) {
                        color = 0xFFAAAA; // light red
                    } else if (duplicatePositions.contains(pos)) {
                        color = 0xFFFF00; // yellow
                    } else if (correctCompletedPositions.contains(pos)) {
                        color = 0x00FFFF; // cyan
                    } else if (count == 9) {
                        color = 0x00FF00; // green
                    } else {
                        color = puzzleVal != 0 ? 0x808080 : 0xFFFFFF;
                        // gray or white
                    }

                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glScalef(0.1f, 0.1f, 0.1f);
                    GL11.glTranslatef((cx + 0.33f) * 10f, (cz) * 10f, -0.2f);
                    DrawHelper.drawString(text, 0, 0, 0, color, true);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glPopMatrix();
                }
            }
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

        SudokuOverlayHandler.addSupportedMaster(te.getBlockPos(), game);
    }
}
