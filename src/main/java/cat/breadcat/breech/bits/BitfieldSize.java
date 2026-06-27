package cat.breadcat.breech.bits;

import cat.breadcat.toolbox.units.DataUnits;

public enum BitfieldSize
{
    Bitfield8(DataUnits.BYTE_BITS),
    Bitfield16(2 * DataUnits.BYTE_BITS),
    Bitfield32(4 * DataUnits.BYTE_BITS),
    Bitfield64(8 * DataUnits.BYTE_BITS);


    private final int bytes;

    BitfieldSize(int bytes)
    {
        this.bytes = bytes;
    }


    public int getBytes()
    {
        return bytes;
    }
}
