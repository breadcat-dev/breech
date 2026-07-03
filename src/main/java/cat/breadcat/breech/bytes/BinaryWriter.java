package cat.breadcat.breech.bytes;

import cat.breadcat.breech.bits.Bitfield;

import java.io.*;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public final class BinaryWriter implements Closeable
{
    private final OutputStream out;
    private final ByteOrder order;

    private final byte[] buffer;
    private long position;

    public BinaryWriter(OutputStream out, ByteOrder endianness)
    {
        this.out = out;
        this.order = endianness;

        this.buffer = new byte[Long.BYTES];
        this.position = 0;
    }


    public void write(byte[] value, int offset, int size) throws IOException
    {
        position += size;
        out.write(value, offset, size);
    }

    public void write(byte[] value) throws IOException
    {
        position += value.length;
        out.write(value);
    }


    public void writeByte(byte value) throws IOException
    {
        position += Byte.BYTES;
        EndianCodec.writeByte(buffer, out, value, order);
    }

    public void writeShort(short value) throws IOException
    {
        position += Short.BYTES;
        EndianCodec.writeShort(buffer, out, value, order);
    }

    public void writeInt(int value) throws IOException
    {
        position += Integer.BYTES;
        EndianCodec.writeInt(buffer, out, value, order);
    }

    public void writeLong(long value) throws IOException
    {
        position += Long.BYTES;
        EndianCodec.writeLong(buffer, out, value, order);
    }


    public void writeFloat(float value) throws IOException
    {
        position += Float.BYTES;
        EndianCodec.writeFloat(buffer, out, value, order);
    }

    public void writeDouble(double value) throws IOException
    {
        position += Double.BYTES;
        EndianCodec.writeDouble(buffer, out, value, order);
    }


    public void writeBoolean(boolean value) throws IOException
    {
        position += Byte.BYTES;
        EndianCodec.writeBoolean(buffer, out, value, order);
    }


    public void writeChar(char value) throws IOException
    {
        position += Character.BYTES;
        EndianCodec.writeChar(buffer, out, value, order);
    }


    public void writeBitfield(Bitfield value) throws IOException
    {
        int byteSize = value.getByteSize();

        position += byteSize;
        EndianCodec.writeRaw(buffer, out, value.getBits(), byteSize, order);
    }

    public void writeString(String value) throws IOException
    {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        writeInt(bytes.length);
        write(bytes);
    }


    public long position()
    {
        return position;
    }

    public void flush() throws IOException
    {
        out.flush();
    }

    @Override
    public void close() throws IOException
    {
        out.close();
    }
}
