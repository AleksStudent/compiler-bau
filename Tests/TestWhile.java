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

    int whileInsideWhile(){
        int i;
        i=0;
        while(true){
            while(i<2147483647){
                i=i+1;
            }
            return 1;
        }
    }
}
