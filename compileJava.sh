cp ./compiled/Main.class ./Main.class
java -cp javaFiles/asm.jar:compiled/. Main < "$1"
rm ./Main.class
