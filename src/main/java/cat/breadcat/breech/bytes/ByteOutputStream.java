package cat.breadcat.breech.bytes;

import cat.breadcat.breech.bits.Bitfield;
import cat.breadcat.toolbox.units.DataUnits;
import cat.breadcat.toolbox.units.PrimitiveUnits;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class ByteOutputStream implements Closeable
{
    private final OutputStream out;
    private final byte[] buffer;
    private final BinaryEndianness endianness;

    public ByteOutputStream(BinaryEndianness endianness, OutputStream out)
    {
        this.out = out;
        this.buffer = new byte[PrimitiveUnits.LONG];
        this.endianness = endianness;
    }


    private void writeValue(long value, int byteSize) throws IOException
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


    public void writeBytes(byte[] value) throws IOException
    {
        out.write(value);
    }

    public void writeBytes(byte[] value, int offset, int size) throws IOException
    {
        out.write(value, offset, size);
    }



    public void writeByte(int value) throws IOException
    {
        writeValue(value, PrimitiveUnits.BYTE);
    }

    public void writeShort(int value) throws IOException
    {
        writeValue(value, PrimitiveUnits.SHORT);
    }

    public void writeInt(int value) throws IOException
    {
        writeValue(value, PrimitiveUnits.INT);
    }

    public void writeLong(long value) throws IOException
    {
        writeValue(value, PrimitiveUnits.LONG);
    }


    public void writeFloat(float value) throws IOException
    {
        writeValue(Float.floatToRawIntBits(value), PrimitiveUnits.FLOAT);
    }

    public void writeDouble(double value) throws IOException
    {
        writeValue(Double.doubleToRawLongBits(value), PrimitiveUnits.DOUBLE);
    }


    public void writeBoolean(boolean value) throws IOException
    {
        writeValue(value ? 1 : 0, PrimitiveUnits.BYTE);
    }

    public void writeBitfield(Bitfield value) throws IOException
    {
        writeValue(value.getBits(), value.getByteSize());
    }


    public void writeChar(char value) throws IOException
    {
        writeValue(value, PrimitiveUnits.CHAR);
    }

    public void writeString(String value) throws IOException
    {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

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