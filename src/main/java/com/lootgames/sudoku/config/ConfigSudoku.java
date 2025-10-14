package com.lootgames.sudoku.config;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;

import com.github.bsideup.jabel.Desugar;

import lombok.Getter;
import ru.timeconqueror.lootgames.common.config.LGConfigs;
import ru.timeconqueror.timecore.api.common.config.Config;

public class ConfigSudoku extends Config {

    public final StageConfig level1;
    public final StageConfig level2;
    public final StageConfig level3;
    public final StageConfig level4;

    public ConfigSudoku() {
        super("sudoku");
        level1 = new StageConfig(getKey(), "stage_1", "Easy", 35);
        level2 = new StageConfig(getKey(), "stage_2", "Normal", 45);
        level3 = new StageConfig(getKey(), "stage_3", "Hard", 55);
        level4 = new StageConfig(getKey(), "stage_4", "Expert", 64);
    }

    @Override
    public void init() {
        level1.init(config);
        level2.init(config);
        level3.init(config);
        level4.init(config);
        config.setCategoryComment(getKey(), "Regulates 'Sudoku' minigame blank cell counts.");
    }

    @Override
    public String getRelativePath() {
        return LGConfigs.resolve("games/" + getKey());
    }

    public ConfigSudokuSnapshot snapshot() {
        return new ConfigSudokuSnapshot(level1.snapshot(), level2.snapshot(), level3.snapshot(), level4.snapshot());
    }

    public static class StageConfig extends ru.timeconqueror.timecore.api.common.config.ConfigSection {

        public int blanksCount;
        public final int defaultBlanks;

        public StageConfig(String parent, String name, String comment, int defaultBlanks) {
            super(parent, name, comment);
            this.defaultBlanks = defaultBlanks;
        }

        public void init(Configuration config) {
            blanksCount = config.getInt("blanks", getCategoryName(), defaultBlanks, 0, 81, getComment());
            config.setCategoryComment(getCategoryName(), getComment());
        }

        public LevelSnapshot snapshot() {
            return new LevelSnapshot(blanksCount);
        }
    }

    @Getter
    public static class ConfigSudokuSnapshot {

        public final LevelSnapshot stage1;
        public final LevelSnapshot stage2;
        public final LevelSnapshot stage3;
        public final LevelSnapshot stage4;

        public ConfigSudokuSnapshot(LevelSnapshot s1, LevelSnapshot s2, LevelSnapshot s3, LevelSnapshot s4) {
            this.stage1 = s1;
            this.stage2 = s2;
            this.stage3 = s3;
            this.stage4 = s4;
        }

        public static NBTTagCompound serialize(ConfigSudokuSnapshot snap) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("stage_1", LevelSnapshot.serialize(snap.stage1));
            tag.setTag("stage_2", LevelSnapshot.serialize(snap.stage2));
            tag.setTag("stage_3", LevelSnapshot.serialize(snap.stage3));
            tag.setTag("stage_4", LevelSnapshot.serialize(snap.stage4));
            return tag;
        }

        public static ConfigSudokuSnapshot deserialize(NBTTagCompound tag) {
            return new ConfigSudokuSnapshot(
                LevelSnapshot.deserialize(tag.getCompoundTag("stage_1")),
                LevelSnapshot.deserialize(tag.getCompoundTag("stage_2")),
                LevelSnapshot.deserialize(tag.getCompoundTag("stage_3")),
                LevelSnapshot.deserialize(tag.getCompoundTag("stage_4")));
        }

        public static ConfigSudokuSnapshot stub() {
            return new ConfigSudokuSnapshot(
                LevelSnapshot.stub(),
                LevelSnapshot.stub(),
                LevelSnapshot.stub(),
                LevelSnapshot.stub());
        }

        public LevelSnapshot getStageByIndex(int idx) {
            return switch (idx) {
                case 1 -> stage1;
                case 2 -> stage2;
                case 3 -> stage3;
                case 4 -> stage4;
                default -> throw new IllegalArgumentException("Unknown stage index: " + idx);
            };
        }
    }

    @Desugar
    public record LevelSnapshot(int blanksCount) {

        public static NBTTagCompound serialize(LevelSnapshot s) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("blanks", s.blanksCount);
            return tag;
        }

        public static LevelSnapshot deserialize(NBTTagCompound tag) {
            return new LevelSnapshot(tag.getInteger("blanks"));
        }

        public static LevelSnapshot stub() {
            return new LevelSnapshot(0);
        }
    }
}
