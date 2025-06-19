# w-tapFuscator

A powerful Java bytecode obfuscator that provides comprehensive protection for your Java applications.

## Features

- **String Encryption**: Encrypts string literals using multiple encryption algorithms
- **InvokeDynamic Transformation**: Converts method calls to dynamic invocations
- **Control Flow Obfuscation**: Shuffles and obfuscates control flow
- **Trash Code Injection**: Injects meaningless code to confuse decompilers
- **Constant Obfuscation**: Obfuscates constant values
- **ASCII Art Comments**: Adds ASCII art to confuse analysis tools
- **Decompiler Crashers**: Adds code that crashes popular decompilers
- **Method Renaming**: Renames methods and fields
- **Access Modifier Transformation**: Modifies access modifiers
- **Try-Catch Wrapping**: Wraps code in try-catch blocks

## Usage

### Command Line

```bash
java -jar w-tapFuscator.jar --input input.jar --output obfuscated.jar
```

### Maven

```bash
mvn clean compile
java -cp target/classes dev.librry.Obfuscator --input input.jar --output obfuscated.jar
```

## Transformers

The obfuscator includes the following transformers:

1. **InvokeDynamic**: Converts method calls to dynamic invocations
2. **StringEncrypt**: Encrypts string literals using Base64 and XOR encryption
3. **molitvaotantidebag**: Anti-debugging and anti-analysis techniques
4. **CrasherJDGUI**: Adds code that crashes JD-GUI decompiler
5. **CrasherRecaf**: Adds code that crashes Recaf decompiler
6. **ShuffleTransformer**: Shuffles method instructions
7. **TrashCodeTransformer**: Injects meaningless code
8. **ConstantTransformer**: Obfuscates constant values
9. **ASCIIArtTransformer**: Adds ASCII art comments
10. **ObfuscatorNewTech**: Advanced string encryption using ConstantDynamic 

## Building

```bash
mvn clean compile
mvn package
```

## Requirements

- Java 8 or higher
- Maven 3.6 or higher

## Credits

- 1WantToFreak
- Denger
- m1rch
- 3elehyy
- notkek

## License

This project is provided as-is for educational and research purposes.



