import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TesterOfTestClasses{

    static Random rng= new Random();
    static int numbersTested=100000;

    //Classes to Test
    static TestOperators testOperators=new TestOperators();
    static TestMethodCalls testMethodCalls= new TestMethodCalls();
    static TestWhile testWhile = new TestWhile();
    static TestRecursion testRecursion = new TestRecursion();
    static TestVariables testVariables = new TestVariables();
    public static void main(String[] args) {
        testTestOperators();    
        testTestMethodCalls();
        testTestWhile();
        testTestRecursion();
        testTestVariables();
    }

    public static void testTestOperators(){
        System.out.println("Testing Operators");
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(testOperators.addNumbers(num1,num2)!=num1+num2){
                throw new RuntimeException("addNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(testOperators.subtractNumbers(num1,num2)!=num1-num2){
                throw new RuntimeException("subtractNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(num2==0)num2=1;
            if(testOperators.divideNumbers(num1,num2)!=num1/num2){
                throw new RuntimeException("divideNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(testOperators.multiplyNumbers(num1,num2)!=num1*num2){
                throw new RuntimeException("multiplyNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(num2==0)num2=1;
            if(testOperators.moduloNumbers(num1,num2)!=num1%num2){
                throw new RuntimeException("moduloNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(testOperators.lessNumbers(num1,num2)!=num1<num2){
                throw new RuntimeException("lessNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(testOperators.lessOrEqualNumbers(num1,num2)!=num1<=num2){
                throw new RuntimeException("lessOrEqualNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(testOperators.greaterNumbers(num1,num2)!=num1>num2){
                throw new RuntimeException("greaterNumbers of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            int num2=rng.nextInt();
            if(testOperators.greaterOrEqualNumbers(num1,num2)!=num1>=num2){
                throw new RuntimeException("greaterOrEqualNumbers of TestOperators failed");
            }
        }
        String alphabet="abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.addChars(char1,char2)!=char1+char2){
                throw new RuntimeException("addChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.subtractChars(char1,char2)!=char1-char2){
                throw new RuntimeException("subtractChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.divideChars(char1,char2)!=char1/char2){
                throw new RuntimeException("divideChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.multiplyChars(char1,char2)!=char1*char2){
                throw new RuntimeException("multiplyChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.moduloChars(char1,char2)!=char1%char2){
                throw new RuntimeException("moduloChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.lessChars(char1,char2)!=char1<char2){
                throw new RuntimeException("lessChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.lessOrEqualChars(char1,char2)!=char1<=char2){
                throw new RuntimeException("lessOrEqualChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.greaterChars(char1,char2)!=char1>char2){
                throw new RuntimeException("greaterChars of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            char char1=alphabet.charAt(rng.nextInt(alphabet.length()));
            char char2=alphabet.charAt(rng.nextInt(alphabet.length()));
            if(testOperators.greaterOrEqualChars(char1,char2)!=char1>=char2){
                throw new RuntimeException("greaterOrEqualChars of TestOperators failed");
            }
        }
        List<Boolean> possibleValues= Arrays.asList(true,false);
        for (Boolean possibleValue : possibleValues) {
            for (Boolean possibleValue2 : possibleValues) {
                if(possibleValue&&possibleValue2!=testOperators.andBoolean(possibleValue,possibleValue2)){
                    throw new RuntimeException("And Operator of TestOperators failed");
                }
                if((possibleValue||possibleValue2)!=testOperators.orBoolean(possibleValue,possibleValue2)){
                    throw new RuntimeException("Or Operator of TestOperators failed");
                }
                if(!possibleValue != testOperators.notBoolean(possibleValue)){
                    throw new RuntimeException("Not Operator of TestOperators failed");
                }
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            if(testOperators.unaryPlus(num1)!=+num1){
                throw new RuntimeException("unaryPlus of TestOperators failed");
            }
        }
        for (int i = 0; i < numbersTested; i++) {
            int num1=rng.nextInt();
            if(testOperators.unaryMinus(num1)!=-num1){
                throw new RuntimeException("unaryPlus of TestOperators failed");
            }
        }
        System.out.println("Tests of TestOperators successful");
    }

    public static void testTestMethodCalls(){
        System.out.println("Testing MethodCalls");
        testMethodCalls.Method1();
        testMethodCalls.Method2(2);
        testMethodCalls.Method3();
        if(testMethodCalls.Method4()!=1){
            throw new RuntimeException("Method4 failed");
        }
        if(testMethodCalls.Method5(2)!=1+2){
            throw new RuntimeException("Method5 failed");
        }
        if(testMethodCalls.Method6(6,3)!=6-3){
            throw new RuntimeException("Method6 failed");
        }
        if(testMethodCalls.Method7(2)!=2){
            throw new RuntimeException("Method7 failed");
        }
        if(testMethodCalls.Method8()!=true){
            throw new RuntimeException("Method8 failed");
        }
        if(testMethodCalls.Method9()!=false){
            throw new RuntimeException("Method9 failed");
        }
        if(testMethodCalls.Method10()!='a'){
            throw new RuntimeException("Method10 failed");
        }
        if(!testMethodCalls.Method11().equals("test")){
            throw new RuntimeException("Method11 failed");
        }
        System.out.println("Tests of TestMethodCalls successful");
    }

    public static int fakultät( final int n )
    { return n == 0 ? 1 : n * fakultät( n - 1 ); }

    public static void testTestWhile(){
        System.out.println("Testing While");
        for (int i = 0; i < 10; i++) {
            if(testWhile.fakultaet(i)!=fakultät(i)){
                throw new RuntimeException(String.format("testWhile has failed, got %s expected %s",testWhile.fakultaet(i),fakultät(i)));
            }
        }
        if(testWhile.whileInsideWhile()!=1){
            throw new RuntimeException("testWhile while inside while failed");
        }
        System.out.println("While Test successful");
    }

    public static void testTestRecursion(){
        System.out.println("Testing recursion");
        for (int i = 0; i < 10; i++) {
            if(testRecursion.fakultaet(i)!=fakultät(i)){
                throw new RuntimeException(String.format("testRecursion has failed, got %s expected %s",testRecursion.fakultaet(i),fakultät(i)));
            }
        }
        System.out.println("Recursion Test successful");
    }

    public static void testTestVariables(){
        System.out.println("Testing variables");
        if(testVariables.checkThis()!=0){
            throw new RuntimeException("testVariables checkThis has failed");
        }
        if(testVariables.checkObject()!=0){
            throw new RuntimeException("testVariables checkThis has failed");
        }
        System.out.println("Variable Test successful");
    }
}