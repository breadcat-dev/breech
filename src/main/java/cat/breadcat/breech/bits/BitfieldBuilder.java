package cat.breadcat.breech.bits;

import cat.breadcat.toolbox.units.PrimitiveUnits;
import cat.breadcat.toolbox.utils.BinaryUtil;

public final class BitfieldBuilder
{
    private final int size;

    private long bits;
    public int index;

    public BitfieldBuilder(int size)
    {
        if(size > PrimitiveUnits.LONG)
            throw new IllegalArgumentException("Maximum Bitfield size is LONG (8 Bytes)");

        this.size = size;

        this.bits = 0L;
        this.index = 0;
    }


    public BitfieldBuilder bit(boolean value)
    {
        if(value)
            bits = BinaryUtil.setBit(bits, index);
        index++;

        return this;
    }

    public Bitfield build()
    {
        if(index > size)
            throw new IllegalStateException("More Bits set than maximum Bitfield size");

        return new Bitfield(bits, size);
    }
}
