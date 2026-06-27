package cat.breadcat.breech.bits;

import cat.breadcat.toolbox.units.PrimitiveUnits;
import cat.breadcat.toolbox.utils.BinaryUtil;

public final class Bitfield
{
    private final long bits;
    private final int size;

    public Bitfield(long bits, int size)
    {
        this.bits = bits;
        this.size = size;
    }


    public static Bitfield fromByte(byte value)
    {
        return new Bitfield(value, PrimitiveUnits.BYTE);
    }

    public static Bitfield fromShort(short value)
    {
        return new Bitfield(value, PrimitiveUnits.SHORT);
    }

    public static Bitfield fromInt(int value)
    {
        return new Bitfield(value, PrimitiveUnits.INT);
    }

    public static Bitfield fromLong(long value)
    {
        return new Bitfield(value, PrimitiveUnits.LONG);
    }


    public long getBits()
    {
        return bits;
    }

    public boolean[] getBitsArray()
    {
        boolean[] bitsArray = new boolean[size];

        for(int i = 0; i < size; i++)
        {
            bitsArray[i] = BinaryUtil.getBit(bits, i);
        }

        return bitsArray;
    }

    public boolean getBit(int bitNumber)
    {
        if(bitNumber < 0 || bitNumber > size)
            throw new IllegalArgumentException("Bit is out of Bitfield range");

        return BinaryUtil.getBit(bits, bitNumber);
    }

    public int getByteSize()
    {
        return size;
    }


    // byte stream shit idk
}
