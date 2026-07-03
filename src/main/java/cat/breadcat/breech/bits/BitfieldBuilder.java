package cat.breadcat.breech.bits;

import cat.breadcat.toolbox.util.BinaryUtil;

public final class BitfieldBuilder
{
    private final int byteSize;
    private final int bitCount;

    private long bits;
    private int index;

    public BitfieldBuilder(int byteSize)
    {
        if (byteSize < Bitfield.MIN_SIZE || byteSize > Bitfield.MAX_SIZE)
            throw new IllegalArgumentException("Byte size must be between " + Bitfield.MIN_SIZE + " and " + Bitfield.MAX_SIZE);

        this.byteSize = byteSize;
        this.bitCount = byteSize * Byte.SIZE;
    }


    public BitfieldBuilder bit(boolean value)
    {
        if(index >= bitCount)
            throw new IllegalStateException("Bitfield is full");

        if(value)
            bits = BinaryUtil.setBit(bits, index);

        index++;
        return this;
    }


    public Bitfield build()
    {
        return new Bitfield(bits, byteSize);
    }
}
