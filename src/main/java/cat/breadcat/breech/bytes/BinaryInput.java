package cat.breadcat.breech.bytes;

import cat.breadcat.breech.BinaryEndianness;
import cat.breadcat.breech.bits.Bitfield;
import cat.breadcat.breech.bits.BitfieldSize;
import cat.breadcat.toolbox.units.PrimitiveUnits;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class BinaryInput implements AutoCloseable
{
    private final InputStream in;
    private final BinaryEndianness endianness;

    public BinaryInput(BinaryEndianness endianness, InputStream in)
    {
        this.in = in;
        this.endianness = endianness;
    }


    private long readValue(int byteSize) throws IOException
    {
        long value = 0;

        if (endianness == BinaryEndianness.LittleEndian)
        {
            for (int i = 0; i < byteSize; i++)
            {
                int readByte = in.read();

                if (readByte == -1)
                    throw new EOFException();

                value |= ((long)readByte) << (i * 8);
            }
        }
        else
        {

            for (int i = 0; i < byteSize; i++)
            {
                int readByte = in.read();

                if (readByte == -1)
                    throw new EOFException();

                value = (value << 8) | readByte;
            }
        }

        return value;
    }

    public byte[] readBytes(int length) throws IOException
    {
        byte[] bytes = new byte[length];
        int total = 0;

        while(total < length)
        {
            int read = in.read(bytes, total, length - total);

            if(read == -1)
                throw new EOFException();

            total += read;
        }

        return bytes;
    }

    public int read(byte[] buffer) throws IOException
    {
        return in.read(buffer);
    }



    public byte readByte() throws IOException
    {
        return (byte)readValue(PrimitiveUnits.BYTE);
    }

    public short readShort() throws IOException
    {
        return (short)readValue(PrimitiveUnits.SHORT);
    }

    public int readInt() throws IOException
    {
        return (int)readValue(PrimitiveUnits.INT);
    }

    public long readLong() throws IOException
    {
        return readValue(PrimitiveUnits.LONG);
    }


    public float readFloat() throws IOException
    {
        return Float.intBitsToFloat((int)readValue(PrimitiveUnits.FLOAT));
    }

    public double readDouble() throws IOException
    {
        return Double.longBitsToDouble(readValue(PrimitiveUnits.DOUBLE));
    }


    public boolean readBoolean() throws IOException
    {
        return readValue(PrimitiveUnits.BYTE) > 0;
    }

    public Bitfield readBitfield(BitfieldSize size) throws IOException
    {
        int byteSize = size.getBytes();

        return new Bitfield(readValue(byteSize), byteSize);
    }


    public char readChar() throws IOException
    {
        return (char)readValue(PrimitiveUnits.CHAR);
    }

    public String readString() throws IOException
    {
        int length = readInt();
        byte[] bytes = readBytes(length);

        return new String(bytes, StandardCharsets.UTF_8);
    }


    @Override
    public void close() throws IOException
    {
        in.close();
    }
}
