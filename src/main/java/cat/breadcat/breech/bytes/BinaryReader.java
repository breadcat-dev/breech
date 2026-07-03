package cat.breadcat.breech.bytes;

import cat.breadcat.breech.bits.Bitfield;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public final class BinaryReader implements Closeable
{
    private final InputStream in;
    private final ByteOrder order;

    private long position;

    public BinaryReader(InputStream in, ByteOrder endianness)
    {
        this.in = in;
        this.order = endianness;

        this.position = 0;
    }


    public void readFully(byte[] buffer) throws IOException
    {
        int length = buffer.length;

        int total = 0;
        while(total < length)
        {
            int read = in.read(buffer, total, length - total);

            if(read == -1)
                throw new EOFException();

            total += read;
        }

        position += total;
    }

    public byte[] readFully(int length) throws IOException
    {
        byte[] buffer = new byte[length];

        int total = 0;
        while(total < length)
        {
            int read = in.read(buffer, total, length - total);

            if(read == -1)
                throw new EOFException();

            total += read;
        }

        position += total;
        return buffer;
    }

    public int read(byte[] buffer) throws IOException
    {
        int read = in.read(buffer);

        if (read > 0)
            position += read;

        return read;
    }


    public byte readByte() throws IOException
    {
        position += Byte.BYTES;
        return EndianCodec.readByte(in, order);
    }

    public short readShort() throws IOException
    {
        position += Short.BYTES;
        return EndianCodec.readShort(in, order);
    }

    public int readInt() throws IOException
    {
        position += Integer.BYTES;
        return EndianCodec.readInt(in, order);
    }

    public long readLong() throws IOException
    {
        position += Long.BYTES;
        return EndianCodec.readLong(in, order);
    }


    public float readFloat() throws IOException
    {
        position += Float.BYTES;
        return EndianCodec.readFloat(in, order);
    }

    public double readDouble() throws IOException
    {
        position += Double.BYTES;
        return EndianCodec.readDouble(in, order);
    }


    public boolean readBoolean() throws IOException
    {
        position += Byte.BYTES;
        return EndianCodec.readBoolean(in, order);
    }


    public char readChar() throws IOException
    {
        position += Character.BYTES;
        return EndianCodec.readChar(in, order);
    }


    public Bitfield readBitfield(int byteSize) throws IOException
    {
        if (byteSize < Bitfield.MIN_SIZE || byteSize > Bitfield.MAX_SIZE)
            throw new IllegalArgumentException("Byte size must be between " + Bitfield.MIN_SIZE + " and " + Bitfield.MAX_SIZE);

        position += byteSize;
        return new Bitfield(EndianCodec.readRaw(in, byteSize, order), byteSize);
    }

    public String readString() throws IOException
    {
        int length = readInt();
        byte[] bytes = readFully(length);

        return new String(bytes, StandardCharsets.UTF_8);
    }


    public void skip(long bytes) throws IOException
    {
        while (bytes > 0)
        {
            long skipped = in.skip(bytes);

            if(skipped == 0)
            {
                if (in.read() == -1)
                    throw new EOFException();

                skipped = 1;
            }

            bytes -= skipped;
            position += skipped;
        }
    }

    public long position()
    {
        return position;
    }

    @Override
    public void close() throws IOException
    {
        in.close();
    }
}
