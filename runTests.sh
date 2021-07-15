set -e
cd Tests
cp ../compiled/Main.class ./Main.class
javac TesterOfTestClasses.java
java -cp ../javaFiles/asm.jar:../compiled/. Main < TestMethodCalls.java
java -cp ../javaFiles/asm.jar:../compiled/. Main < TestOperators.java
java -cp ../javaFiles/asm.jar:../compiled/. Main < TestWhile.java
java -cp ../javaFiles/asm.jar:../compiled/. Main < TestRecursion.java
java -cp ../javaFiles/asm.jar:../compiled/. Main < TestVariables.java
java TesterOfTestClasses
rm ./Main.class
rm *.class