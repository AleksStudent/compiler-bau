class Main {
    public static void main(String args[])
                throws java.io.IOException {
    scanner s =
        new scanner (
            new java.io.InputStreamReader(System.in));
    while (s.yylex() != -1) {}
    }
}