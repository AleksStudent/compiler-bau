import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TesterOfTestClasses{

    static Random rng= new Random();
    static int numbersTested=100000;

    //Classes to Test
    static TestOperators testOperators=new TestOperators();
    static TestMethodCalls TestMethodCalls= new TestMethodCalls();

    public static void main(String[] args) {
        testTestOperators();    
        testTestMethodCalls();
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
        List<Boolean> possibleValues= Arrays.asList(true,false);
        for (Boolean possibleValue : possibleValues) {
            for (Boolean possibleValue2 : possibleValues) {
                if(possibleValue&&possibleValue2!=testOperators.andBoolean(possibleValue,possibleValue2)){
                    throw new RuntimeException("And Operator of TestOperators failed");
                }
                if((possibleValue||possibleValue2)!=testOperators.orBoolean(possibleValue,possibleValue2)){
                    throw new RuntimeException("Or Operator of TestOperators failed");
                }
            }
        }
        System.out.println("Tests of TestOperators successful");
    }

    public static void testTestMethodCalls(){
        System.out.println("Testing MethodCalls");
        TestMethodCalls.Method1();
        TestMethodCalls.Method2(2);
        TestMethodCalls.Method3();
        if(TestMethodCalls.Method4()!=1){
            throw new RuntimeException("Method4 failed");
        }
        if(TestMethodCalls.Method5(2)!=1+2){
            throw new RuntimeException("Method5 failed");
        }
        if(TestMethodCalls.Method6(6,3)!=6-3){
            throw new RuntimeException("Method6 failed");
        }
        if(TestMethodCalls.Method7(2)!=2){
            throw new RuntimeException("Method7 failed");
        }
        if(TestMethodCalls.Method8()!=true){
            throw new RuntimeException("Method8 failed");
        }
        if(TestMethodCalls.Method9()!=false){
            throw new RuntimeException("Method9 failed");
        }
        if(TestMethodCalls.Method10()!='a'){
            throw new RuntimeException("Method10 failed");
        }
        if(!TestMethodCalls.Method11().equals("test")){
            throw new RuntimeException("Metho1 failed");
        }
        System.out.println("Tests of TestMethodCalls successful");
    }
}