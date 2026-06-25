package cat.breadcat.breech;

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


    public int readByte() throws IOException
    {
        return (int)readValue(PrimitiveUnits.BYTE);
    }

    public int readShort() throws IOException
    {
        return (int)readValue(PrimitiveUnits.SHORT);
    }

    public int readInt() throws IOException
    {
        return (int)readValue(PrimitiveUnits.INT);
    }

    public long readLong() throws IOException
    {
        return readValue(PrimitiveUnits.LONG);
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
