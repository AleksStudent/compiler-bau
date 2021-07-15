set -e
cd Tests
javac TesterOfTestClasses.java
java -cp ../asm.jar:../. Main < TestMethodCalls.java
java -cp ../asm.jar:../. Main < TestOperators.java
java -cp ../asm.jar:../. Main < TestWhile.java
java -cp ../asm.jar:../. Main < TestRecursion.java
java -cp ../asm.jar:../. Main < TestVariables.java
java TesterOfTestClasses