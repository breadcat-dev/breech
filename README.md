# Radio - Lightweight ~~& Reliable~~ binary I/O library for Java
### Part of my TANK Series

## Status

EXPERIMENTAL - API might change in the future

## What it does
- Reads and writes primitive binary data (`byte`, `short`, `int`, `long`)
- Supports both little-endian and big-endian byte order
- Reads and writes length-prefixed UTF-8 strings
- Provides reliable EOF handling for incomplete data
- Lightweight wrapper around Java streams


## Examples

Write to File
```java
String name = "breadcatz";
int score = 3689;

try(BinaryOutput out = new BinaryOutput(BinaryEndianness.LittleEndian, Files.newOutputStream(Path.of("./data.bin"))))
{
    out.writeString(name);
    out.writeInt(score);
}
catch (IOException e)
{
    throw new RuntimeException(e);
}
```

(HEX view of data.bin)
```
09 00 00 00 62 72 65 61 64 63 61 74 7A 69 0E 00 00
```

Read from File
```java
try(BinaryInput in = new BinaryInput(BinaryEndianness.LittleEndian, Files.newInputStream(Path.of("data.bin"))))
{
    String name = in.readString();
    int score = in.readInt();

    System.out.println("name - " + name + "\nscore - " + score);
}
catch (IOException e)
{
    throw new RuntimeException(e);
}
```

(Console)
```
name - breadcatz
score - 3689
```

## Dependencies:
- Toolbox: `cat.breadcat:toolbox:[VERSION]` [Github](https://github.com/breadcat-dev/toolbox)