class TestVariables {
    int a;
    boolean b;
    char c;
    String d;

    int checkThis(){
        a=3;
        b=true;
        c='d';
        d="test";
        char a;
        a = 'a';
        if((a!='a') || (this.a!=3)){
            return 1;
        }
        return 0;
    }

    int checkObject(){
        a=4;
        TestVariables testVariables= new TestVariables();
        testVariables.a=3;
        boolean a;
        a=false;

        if(a!=false||this.a!=4||testVariables.a!=3){
            return 1;
        }
        return 0;
    }
}
