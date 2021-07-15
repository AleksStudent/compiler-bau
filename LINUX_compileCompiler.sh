cd compiled
javac  -d . -cp ../javaFiles/asm.jar:../javaFiles/. @../sources.txt
cd ..
chmod +x jaooy_linux
./jaooy_linux -v javaParser.jay < skeleton.jaooy > javaParser.java
mv javaParser.java ./compiled/javaParser.java
cd compiled
javac -d . -cp ../javaFiles/asm.jar:../javaFiles/. javaParser.java
cp -b ../javaLexer .
java -cp ../JLex2.jar JLex2.Main javaLexer
rm javaLexer
javac -d . -cp ../javaFiles/asm.jar:. javaLexer.java
javac -d . -cp ../javaFiles/asm.jar:. ../JavaFiles/Main.java