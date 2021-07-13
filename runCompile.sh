javac -cp ./asm.jar:. BytecodeGenerator.java
java -cp JLex2.jar JLex2.Main browserlexer
javac browserlexer.java
./jaooy -v browserparser.jay < skeleton.jaooy > browserparser.java
javac browserparser.java
