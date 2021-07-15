cd compiled
javac  -d . -cp ../javaFiles/asm.jar:../javaFiles/. @../sources.txt
cd ..
chmod +x jaooy_linux
./jaooy_linux -v browserparser.jay < skeleton.jaooy > browserparser.java
mv browserparser.java ./compiled/browserparser.java
cd compiled
javac -d . -cp ../javaFiles/asm.jar:../javaFiles/. browserparser.java
cp -b ../browserlexer .
java -cp ../JLex2.jar JLex2.Main browserlexer
rm browserlexer
javac -d . -cp ../javaFiles/asm.jar:. browserlexer.java
javac -d . -cp ../javaFiles/asm.jar:. ../JavaFiles/Main.java