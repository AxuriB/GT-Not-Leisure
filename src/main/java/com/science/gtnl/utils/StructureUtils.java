package com.science.gtnl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.block.Block;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.blocks.BlockCasingsAbstract;

public class StructureUtils {

    // 基础路径
    public static final String BASE_PATH = "/assets/";
    public static final ConcurrentHashMap<String, List<String[]>> MULTIBLOCK_CACHE = new ConcurrentHashMap<>();

    /**
     * 从文件读取多方块结构
     *
     * @param fileName 文件名
     * @return 多方块结构的二维字符串数组
     */
    public static String[][] readStructureFromFile(String fileName) {
        return MULTIBLOCK_CACHE.computeIfAbsent(fileName, name -> {
            List<String[]> structure = new ArrayList<>();
            String filePath = BASE_PATH + name.replace(':', '/') + ".mb";
            try (InputStream is = StructureUtils.class.getResourceAsStream(filePath)) {
                if (is == null) {
                    throw new IllegalArgumentException("无法读取文件: " + name + "，请检查文件是否存在。");
                }
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        structure.add(line.split(","));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return structure;
        })
            .toArray(new String[0][]);
    }

    /**
     * 转置二维数组
     *
     * @param original 原始二维数组
     * @return 转置后的二维数组
     */
    public static String[][] transposeStructure(String[][] original) {
        if (original == null || original.length == 0) {
            throw new IllegalArgumentException("矩阵为空，无法转置！");
        }

        int rows = original.length;
        int cols = original[0].length;
        String[][] transposed = new String[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = original[i][j];
            }
        }

        return transposed;
    }

    /**
     * 打印二维数组
     *
     * @param structure 二维字符串数组
     */
    public static void printStructure(String[][] structure) {
        for (String[] row : structure) {
            System.out.println(String.join(",", row));
        }
    }

    /**
     * Like structure definition, select a character from the structure definition string array as the target to place
     * blocks in the world, with the machine facing the XZ direction.
     *
     * @param aBaseMetaTileEntity the machine
     * @param OffSetX             HORIZONTAL_OFF_SET of the machine structure definition
     * @param OffSetY             VERTICAL_OFF_SET of the machine structure definition
     * @param OffSetZ             DEPTH_OFF_SET of the machine structure definition
     * @param StructureString     the machine structure definition string array
     * @param isStructureFlipped  if the machine is flipped, use getFlip().isHorizontallyFlipped() to get it
     * @param TargetString        target character
     * @param TargetBlock         target block
     * @param TargetMeta          target block meta
     */
    public static void setStringBlockXZ(IGregTechTileEntity aBaseMetaTileEntity, int OffSetX, int OffSetY, int OffSetZ,
        String[][] StructureString, boolean isStructureFlipped, String TargetString, Block TargetBlock,
        int TargetMeta) {
        int mDirectionX = aBaseMetaTileEntity.getFrontFacing().offsetX;
        int mDirectionZ = aBaseMetaTileEntity.getFrontFacing().offsetZ;
        int xDir = 0;
        int zDir = 0;
        if (mDirectionX == 1) {
            // EAST
            xDir = 1;
            zDir = 1;
        } else if (mDirectionX == -1) {
            // WEST
            xDir = -1;
            zDir = -1;
        }
        if (mDirectionZ == 1) {
            // SOUTH
            xDir = -1;
            zDir = 1;
        } else if (mDirectionZ == -1) {
            // NORTH
            xDir = 1;
            zDir = -1;
        }
        int LengthX = StructureString[0][0].length();
        int LengthY = StructureString.length;
        int LengthZ = StructureString[0].length;
        for (int x = 0; x < LengthX; x++) {
            for (int z = 0; z < LengthZ; z++) {
                for (int y = 0; y < LengthY; y++) {
                    String ListStr = String.valueOf(StructureString[y][z].charAt(x));
                    if (!Objects.equals(ListStr, TargetString)) continue;

                    int aX = (OffSetX - x) * xDir;
                    int aY = OffSetY - y;
                    int aZ = (OffSetZ - z) * zDir;
                    if (mDirectionX == 1 || mDirectionX == -1) {
                        int temp = aX;
                        aX = aZ;
                        aZ = temp;
                    }
                    if (isStructureFlipped) {
                        if (mDirectionX == 1 || mDirectionX == -1) {
                            aZ = -aZ;
                        } else {
                            aX = -aX;
                        }
                    }

                    aBaseMetaTileEntity.getWorld()
                        .setBlock(
                            aBaseMetaTileEntity.getXCoord() + aX,
                            aBaseMetaTileEntity.getYCoord() + aY,
                            aBaseMetaTileEntity.getZCoord() + aZ,
                            TargetBlock,
                            TargetMeta,
                            3);
                }
            }
        }
    }

    public static void setStringBlockXZ(IGregTechTileEntity aBaseMetaTileEntity, int OffSetX, int OffSetY, int OffSetZ,
        String[][] StructureString, boolean isStructureFlipped, String TargetString, Block TargetBlock) {
        setStringBlockXZ(
            aBaseMetaTileEntity,
            OffSetX,
            OffSetY,
            OffSetZ,
            StructureString,
            isStructureFlipped,
            TargetString,
            TargetBlock,
            0);
    }

    public static String[][] replaceLetters(String[][] array, String replacement) {
        String[][] outputArray = new String[array.length][];
        for (int i = 0; i < array.length; i++) {
            outputArray[i] = new String[array[i].length];
            for (int j = 0; j < array[i].length; j++) {
                outputArray[i][j] = array[i][j].replaceAll("[A-Z]", replacement);
            }
        }
        return outputArray;
    }

    public static int getTextureIndex(Block block, int meta) {
        return ((BlockCasingsAbstract) block).getTextureIndex(meta);
    }
}
