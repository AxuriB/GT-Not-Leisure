package com.lootgames.sudoku.packet;

import java.util.Objects;

import net.minecraft.nbt.NBTTagCompound;

import com.lootgames.sudoku.sudoku.GameSudoku;
import com.lootgames.sudoku.sudoku.SudokuBoard;

import ru.timeconqueror.lootgames.api.minigame.LootGame;
import ru.timeconqueror.lootgames.api.packet.NBTGamePacket;

/**
 * 将整个数独棋盘（puzzle + player + solution）同步到客户端。
 */
public class SPSSyncBoard extends NBTGamePacket {

    /** 仅供反射用 */
    @Deprecated
    public SPSSyncBoard() {}

    public SPSSyncBoard(GameSudoku game, SudokuBoard board) {
        super(() -> {
            NBTTagCompound nbt = new NBTTagCompound();
            // 复用 SudokuBoard.writeNBT()，会写入 puzzle/player/solution
            nbt.setTag("board", board.writeNBT());
            return nbt;
        });
    }

    @Override
    public <S extends LootGame.Stage, T extends LootGame<S, T>> void runOnClient(LootGame<S, T> genericGame) {
        GameSudoku game = (GameSudoku) genericGame;
        NBTTagCompound boardTag = Objects.requireNonNull(getCompound())
            .getCompoundTag("board");
        game.getBoard()
            .readNBT(boardTag);
    }
}
