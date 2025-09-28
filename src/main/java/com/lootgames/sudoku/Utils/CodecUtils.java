package com.lootgames.sudoku.Utils;

import java.lang.reflect.Array;
import java.util.function.Predicate;

import net.minecraft.nbt.NBTTagCompound;

import ru.timeconqueror.lootgames.utils.future.ICodec;

public class CodecUtils {

    /**
     * 用于整型元素的编码/解码，将值存储在 NBTTagCompound 字段 "v" 中
     */
    public static final ICodec<Integer, NBTTagCompound> INT_CODEC = new ICodec<>() {

        @Override
        public NBTTagCompound encode(Integer obj) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("v", obj);
            return tag;
        }

        @Override
        public Integer decode(NBTTagCompound nbtTag) {
            return nbtTag.getInteger("v");
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> T[][] read2DimArr(NBTTagCompound tableTag, Class<T> elementClass,
        ICodec<T, NBTTagCompound> elementCodec) {
        int size = tableTag.getInteger("size");

        T[][] table = null;
        for (int i = 0; i < size; i++) {
            NBTTagCompound columnTag = tableTag.getCompoundTag(Integer.toString(i));
            int columnSize = columnTag.getInteger("size");

            T[] column = (T[]) Array.newInstance(elementClass, columnSize);
            for (int j = 0; j < columnSize; j++) {
                if (columnTag.hasKey(Integer.toString(j))) {
                    NBTTagCompound elementTag = columnTag.getCompoundTag(Integer.toString(j));
                    column[j] = elementCodec.decode(elementTag);
                }
            }

            if (i == 0) {
                Class<?> columnClass = column.getClass();
                table = (T[][]) Array.newInstance(columnClass, size);
            }

            table[i] = column;
        }
        return table;
    }

    public static <T> NBTTagCompound write2DimArr(T[][] objArr, ICodec<T, NBTTagCompound> elementCodec) {
        return write2DimArr(objArr, elementCodec, e -> true);
    }

    public static <T> NBTTagCompound write2DimArr(T[][] objArr, ICodec<T, NBTTagCompound> elementCodec,
        Predicate<T> writeElementIf) {
        NBTTagCompound tableTag = new NBTTagCompound();
        for (int i = 0; i < objArr.length; i++) {
            NBTTagCompound column = new NBTTagCompound();
            for (int j = 0; j < objArr[i].length; j++) {
                T element = objArr[i][j];
                if (writeElementIf.test(element)) {
                    NBTTagCompound elementTag = elementCodec.encode(element);
                    column.setTag(Integer.toString(j), elementTag);
                }
            }
            column.setInteger("size", objArr[i].length);
            tableTag.setTag(Integer.toString(i), column);
        }
        tableTag.setInteger("size", objArr.length);
        return tableTag;
    }
}
