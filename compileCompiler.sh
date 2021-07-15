javac -cp asm.jar:. @sources.txt
./jaooy -v browserparser.jay < skeleton.jaooy > browserparser.java
javac -cp asm.jar:. browserparser.java
java -cp JLex2.jar JLex2.Main browserlexer
javac -cp asm.jar:. browserlexer.java
javac Main.java
