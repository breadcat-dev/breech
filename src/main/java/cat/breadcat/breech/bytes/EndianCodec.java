package cat.breadcat.breech.bytes;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;

final class EndianCodec
{
    private EndianCodec() {}

    static void writeRaw(byte[] buffer, OutputStream out, long value, int byteSize, ByteOrder order) throws IOException
    {
        if(order == ByteOrder.LITTLE_ENDIAN)
        {
            for(int i = 0; i < byteSize; i++)
            {
                buffer[i] = (byte)(value >>> (i * Byte.SIZE));
            }
        }
        else
        {
            for(int i = 0; i < byteSize; i++)
            {
                buffer[byteSize - 1 - i] = (byte)(value >>> (i * Byte.SIZE));
            }
        }

        out.write(buffer, 0, byteSize);
    }

    static long readRaw(InputStream in, int byteSize, ByteOrder order) throws IOException
    {
        long value = 0;

        if (order == ByteOrder.LITTLE_ENDIAN)
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



    static byte readByte(InputStream in, ByteOrder order) throws IOException
    {
        return (byte)readRaw(in, Byte.BYTES, order);
    }

    static short readShort(InputStream in, ByteOrder order) throws IOException
    {
        return (short)readRaw(in, Short.BYTES, order);
    }

    static int readInt(InputStream in, ByteOrder order) throws IOException
    {
        return (int)readRaw(in, Integer.BYTES, order);
    }

    static long readLong(InputStream in, ByteOrder order) throws IOException
    {
        return readRaw(in, Long.BYTES, order);
    }


    static float readFloat(InputStream in, ByteOrder order) throws IOException
    {
        return Float.intBitsToFloat((int)readRaw(in, Float.BYTES, order));
    }

    static double readDouble(InputStream in, ByteOrder order) throws IOException
    {
        return Double.longBitsToDouble(readRaw(in, Double.BYTES, order));
    }


    static boolean readBoolean(InputStream in, ByteOrder order) throws IOException
    {
        return readRaw(in, Byte.BYTES, order) > 0;
    }


    static char readChar(InputStream in, ByteOrder order) throws IOException
    {
        return (char)readRaw(in, Character.BYTES, order);
    }



    static void writeByte(byte[] buffer, OutputStream out, byte value, ByteOrder order) throws IOException
    {
        writeRaw(buffer, out, value, Byte.BYTES ,order);
    }

    static void writeShort(byte[] buffer, OutputStream out, short value, ByteOrder order) throws IOException
    {
        writeRaw(buffer, out, value, Short.BYTES ,order);
    }

    static void writeInt(byte[] buffer, OutputStream out, int value, ByteOrder order) throws IOException
    {
        writeRaw(buffer, out, value, Integer.BYTES ,order);
    }

    static void writeLong(byte[] buffer, OutputStream out, long value, ByteOrder order) throws IOException
    {
        writeRaw(buffer, out, value, Long.BYTES ,order);
    }


    static void writeFloat(byte[] buffer, OutputStream out, float value, ByteOrder order) throws IOException
    {
        writeRaw(buffer, out, Float.floatToRawIntBits(value), Float.BYTES, order);
    }

    static void writeDouble(byte[] buffer, OutputStream out, double value, ByteOrder order) throws IOException
    {
        writeRaw(buffer, out, Double.doubleToLongBits(value), Double.BYTES, order);
    }


    static void writeBoolean(byte[] buffer, OutputStream out, boolean value, ByteOrder order) throws IOException
    {
        writeByte(buffer, out, (byte)(value ? 1 : 0), order);
    }


    static void writeChar(byte[] buffer, OutputStream out, char value, ByteOrder order) throws IOException
    {
        writeRaw(buffer, out, value, Character.BYTES, order);
    }
}