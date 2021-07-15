cd compiled
javac  -cp ../javaFiles/asm.jar:../javaFiles/. @../sources.txt
cd ..
chmod +x jaooy_mac
./jaooy_mac -v browserparser.jay < skeleton.jaooy > browserparser.java
mv browserparser.java ./compiled/browserparser.java
cd compiled
javac -cp ../javaFiles/asm.jar:../javaFiles/. browserparser.java
cp -b ../browserlexer .
java -cp ../JLex2.jar JLex2.Main browserlexer
rm browserlexer
javac -cp ../javaFiles/asm.jar:. browserlexer.java
javac -cp ../javaFiles/asm.jar:. ../JavaFiles/Main.java