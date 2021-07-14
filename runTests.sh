set -e
cd Tests
java -cp ../ Main < TestMethodCalls.java
java -cp ../ Main < TestOperators.java
javac TesterOfTestClasses.java
java TesterOfTestClasses