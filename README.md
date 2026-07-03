# Breech

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)
![Java](https://img.shields.io/badge/Java-21-blue)
![Status](https://img.shields.io/badge/status-experimental-orange)
![Release](https://img.shields.io/github/v/release/breadcat-dev/breech)

> A lightweight binary I/O library for Java projects

Part of the TANK Series.

---

## Features

- Read and write all Java primitive types
- Big-endian and little-endian support
- Variable-size bitfields (1–8 bytes)
- UTF-8 string serialization
- Sequential binary readers and writers
- Small, straightforward API

---

## Design Goals

- Simple
- Lightweight
- Reusable

---

## Installation

Currently, Breech is not on Maven Central.
To use it, clone the repository and publish it to your local Maven Repository.


```sh
git clone https://github.com/breadcat-dev/breech.git
cd breech
```

### Linux / MacOS
```sh
./gradlew publishToMavenLocal
```
### Windows
```sh
./gradlew.bat publishToMavenLocal
```

Once installed, add the dependency:

### Groovy
```gradle
implementation "cat.breadcat:breech:<version>"
```

### Kotlin DSL
```gradle
implementation("cat.breadcat:breech:<version>")
```

---

## Quick Examples

### Writing
```java
try(BinaryWriter writer = new BinaryWriter
(
    new FileOutputStream("save.dat"),
    ByteOrder.LITTLE_ENDIAN
))
{
    writer.writeString("BreadCat");
    writer.writeInt(64);
    writer.writeFloat(3.14f);
}
```

### Reading
```java
try(BinaryReader reader = new BinaryReader
(
    new FileInputStream("save.dat"),
    ByteOrder.LITTLE_ENDIAN
))
{
    String name = reader.readString();
    int coins = reader.readInt();
    int exp = reader.readFloat();
}
```


---

## Dependencies:
- Toolbox - [Github](https://github.com/breadcat-dev/toolbox)