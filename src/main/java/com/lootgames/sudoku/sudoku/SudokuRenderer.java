package com.lootgames.sudoku.sudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lootgames.sudoku.block.SudokuTile;

import ru.timeconqueror.lootgames.api.block.tile.BoardGameMasterTile;
import ru.timeconqueror.lootgames.api.util.Pos2i;
import ru.timeconqueror.timecore.api.util.client.DrawHelper;

public class SudokuRenderer extends TileEntitySpecialRenderer {

    public static ResourceLocation BOARD = new ResourceLocation("lootgames", "textures/game/ms_board.png");

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
                DrawHelper.drawTexturedRectByParts(cx, cz, 1, 1, -0.005f, 0, 0, 1, 1, 4);
            }
        }

        drawGridLines(size, 0.02f);

        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Map<Integer, Integer> numberCounts = new HashMap<>();

        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                int puzzleVal = board.getPuzzleValue(cx, cz);
                int playerVal = board.getPlayerValue(new Pos2i(cx, cz));
                int val = puzzleVal != 0 ? puzzleVal : playerVal;
                if (val != 0) numberCounts.put(val, numberCounts.getOrDefault(val, 0) + 1);
            }
        }

        Set<Pos2i> duplicatePositions = new HashSet<>();
        Set<Pos2i> correctCompletedPositions = new HashSet<>();

        for (int row = 0; row < size; row++) {
            Set<Pos2i> section = new HashSet<>();
            Map<Integer, Integer> counts = new HashMap<>();
            boolean valid = true;

            for (int col = 0; col < size; col++) {
                Pos2i pos = new Pos2i(row, col);
                int val = board.getPuzzleValue(pos);
                if (val == 0) val = board.getPlayerValue(pos);
                section.add(pos);

                if (val < 1 || val > 9) valid = false;
                if (val != 0) {
                    counts.put(val, counts.getOrDefault(val, 0) + 1);
                }
            }

            for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
                if (e.getValue() > 1) {
                    valid = false;
                    for (int col = 0; col < size; col++) {
                        Pos2i pos = new Pos2i(row, col);
                        int val = board.getPuzzleValue(pos);
                        if (val == 0) val = board.getPlayerValue(pos);
                        if (val == e.getKey()) duplicatePositions.add(pos);
                    }
                }
            }

            if (valid && counts.size() == 9) correctCompletedPositions.addAll(section);
        }

        for (int col = 0; col < size; col++) {
            Set<Pos2i> section = new HashSet<>();
            Map<Integer, Integer> counts = new HashMap<>();
            boolean valid = true;

            for (int row = 0; row < size; row++) {
                Pos2i pos = new Pos2i(row, col);
                int val = board.getPuzzleValue(pos);
                if (val == 0) val = board.getPlayerValue(pos);
                section.add(pos);

                if (val < 1 || val > 9) valid = false;
                if (val != 0) {
                    counts.put(val, counts.getOrDefault(val, 0) + 1);
                }
            }

            for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
                if (e.getValue() > 1) {
                    valid = false;
                    for (int row = 0; row < size; row++) {
                        Pos2i pos = new Pos2i(row, col);
                        int val = board.getPuzzleValue(pos);
                        if (val == 0) val = board.getPlayerValue(pos);
                        if (val == e.getKey()) duplicatePositions.add(pos);
                    }
                }
            }

            if (valid && counts.size() == 9) correctCompletedPositions.addAll(section);
        }

        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                Set<Pos2i> section = new HashSet<>();
                Map<Integer, Integer> counts = new HashMap<>();
                boolean valid = true;

                for (int dy = 0; dy < 3; dy++) {
                    for (int dx = 0; dx < 3; dx++) {
                        int row = boxRow * 3 + dy;
                        int col = boxCol * 3 + dx;
                        Pos2i pos = new Pos2i(row, col);
                        int val = board.getPuzzleValue(pos);
                        if (val == 0) val = board.getPlayerValue(pos);
                        section.add(pos);

                        if (val < 1 || val > 9) valid = false;
                        if (val != 0) {
                            counts.put(val, counts.getOrDefault(val, 0) + 1);
                        }
                    }
                }

                for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
                    if (e.getValue() > 1) {
                        valid = false;
                        for (Pos2i pos : section) {
                            int val = board.getPuzzleValue(pos);
                            if (val == 0) val = board.getPlayerValue(pos);
                            if (val == e.getKey()) duplicatePositions.add(pos);
                        }
                    }
                }

                if (valid && counts.size() == 9) correctCompletedPositions.addAll(section);
            }
        }

        for (int cx = 0; cx < size; cx++) {
            for (int cz = 0; cz < size; cz++) {
                Pos2i pos = new Pos2i(cx, cz);
                int puzzleVal = board.getPuzzleValue(cx, cz);
                int playerVal = board.getPlayerValue(pos);
                int actualVal = puzzleVal != 0 ? puzzleVal : playerVal;

                if (actualVal != 0) {
                    int count = numberCounts.getOrDefault(actualVal, 0);
                    int color;

                    if (count > 9) {
                        color = 0xFFAAAA;
                    } else if (duplicatePositions.contains(pos)) {
                        color = 0xFFFF00;
                    } else if (correctCompletedPositions.contains(pos)) {
                        color = 0x00FFFF;
                    } else if (count == 9) {
                        color = 0x00FF00;
                    } else {
                        color = puzzleVal != 0 ? 0x808080 : 0xFFFFFF;
                    }

                    GL11.glPushMatrix();
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glTranslatef(cx + 0.25f, cz + 0.175f, -0.01f);
                    GL11.glScalef(0.08f, 0.08f, 0.08f);
                    drawString(Integer.toString(actualVal), 0, 0, 0, color, true);
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

    public static void drawGridLines(int size, float thickness) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1f, 1f, 1f, 1f);

        float z = -0.01f;
        float half = thickness / 2.0f;

        GL11.glBegin(GL11.GL_QUADS);

        for (int i = 0; i <= size; i++) {
            if (i % 3 == 0) {
                GL11.glVertex3f(i - half, 0, z);
                GL11.glVertex3f(i + half, 0, z);
                GL11.glVertex3f(i + half, size, z);
                GL11.glVertex3f(i - half, size, z);
            }
        }

        for (int i = 0; i <= size; i++) {
            if (i % 3 == 0) {
                GL11.glVertex3f(0, i - half, z);
                GL11.glVertex3f(size, i - half, z);
                GL11.glVertex3f(size, i + half, z);
                GL11.glVertex3f(0, i + half, z);
            }
        }

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    public static void drawString(String text, float x, float y, float z, int color, boolean shadow) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 0F, z);
        if (shadow) {
            fr.drawStringWithShadow(text, (int) x, (int) y, color);
        } else {
            fr.drawString(text, (int) x, (int) y, color);
        }
        GL11.glPopMatrix();
    }

}
