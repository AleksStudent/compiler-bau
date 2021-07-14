class TestMethodCalls{
    void Method1(){

    }

    void Method2(int var1){
        
    }

    void Method3(){
        Method1();
        Method2(2);
        Method8();
    }

    int Method4(){
        return 1;
    }

    int Method5(int num1){
        return Method4()+num1;
    }
    int Method6(int num1,int num2){
        return num1-num2;
    }
    int Method7(int num1){
        return Method6(Method5(num1),Method4());
    }

    boolean Method8(){
        return true;
    }

    boolean Method9(){
        return false;
    }

    char Method10(){
        return 'a';
    }

    String Method11(){
        return "test";
    }
}