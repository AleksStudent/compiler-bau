set -e
javac TesterOfTestClasses.java
java -cp ./asm.jar:./ Main < TestMethodCalls.java
java -cp ./asm.jar:./. Main < TestOperators.java
java TesterOfTestClasses
