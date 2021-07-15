class TestWhile {

    int fakultaet(int n){
        int i;
        int result;
        i=1;
        result=1;
        while(i<n){
            i=i+1;
            result=result*i;
        }
        return result;
    }
}
