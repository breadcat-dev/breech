package cat.breadcat.breech.bits;

import cat.breadcat.toolbox.util.BinaryUtil;

public final class Bitfield
{
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = Long.BYTES;

    private final long bits;
    private final int byteSize;
    private final int bitCount;

    public Bitfield(long bits, int byteSize)
    {
        if (byteSize < MIN_SIZE || byteSize > MAX_SIZE)
            throw new IllegalArgumentException("Byte size must be between " + MIN_SIZE + " and " + MAX_SIZE);

        this.byteSize = byteSize;
        this.bitCount = byteSize * Byte.SIZE;

        long mask = (byteSize == MAX_SIZE) ? -1L : (1L << bitCount) - 1;
        this.bits = bits & mask;
    }


    public boolean getBit(int index)
    {
        if (index < 0 || index >= bitCount)
            throw new IndexOutOfBoundsException();

        return BinaryUtil.getBit(bits, index);
    }

    public boolean[] toBooleanArray()
    {
        boolean[] array = new boolean[bitCount];

        for (int i = 0; i < bitCount; i++)
            array[i] = getBit(i);

        return array;
    }


    public long getBits()
    {
        return bits;
    }

    public int getByteSize()
    {
        return byteSize;
    }

    public int getBitCount()
    {
        return bitCount;
    }
}
