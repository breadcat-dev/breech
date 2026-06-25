package cat.breadcat.breech;

import cat.breadcat.toolbox.units.DataUnits;
import cat.breadcat.toolbox.units.PrimitiveUnits;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class BinaryOutput implements AutoCloseable
{
    private final OutputStream out;
    private final byte[] buffer;
    private final BinaryEndianness endianness;

    public BinaryOutput(BinaryEndianness endianness, OutputStream out)
    {
        this.out = out;
        this.buffer = new byte[PrimitiveUnits.LONG];
        this.endianness = endianness;
    }


    private void writeValue(int byteSize, long value) throws IOException
    {
        if(endianness == BinaryEndianness.LittleEndian)
        {
            for(int i = 0; i < byteSize; i++)
            {
                buffer[i] = (byte)(value >>> (i * DataUnits.BYTE_BITS));
            }
        }
        else
        {
            for(int i = 0; i < byteSize; i++)
            {
                buffer[byteSize - 1 - i] = (byte)(value >>> (i * DataUnits.BYTE_BITS));
            }
        }

        out.write(buffer, 0, byteSize);
    }

    public void writeBytes(byte[] data) throws IOException
    {
        out.write(data);
    }


    public void writeByte(int data) throws IOException
    {
        writeValue(PrimitiveUnits.BYTE, data);
    }

    public void writeShort(int data) throws IOException
    {
        writeValue(PrimitiveUnits.SHORT, data);
    }

    public void writeInt(int data) throws IOException
    {
        writeValue(PrimitiveUnits.INT, data);
    }

    public void writeLong(long data) throws IOException
    {
        writeValue(PrimitiveUnits.LONG, data);
    }

    public void writeString(String data) throws IOException
    {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

        writeInt(bytes.length);
        writeBytes(bytes);
    }


    @Override
    public void close() throws IOException
    {
        out.close();
    }

    public void flush() throws IOException
    {
        out.flush();
    }
}


// add position
// add varints