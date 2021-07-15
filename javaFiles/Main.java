
public class Main {
    public static void main (String [] args) {
        javaScanner scanner = new javaScanner(new java.io.InputStreamReader (System.in));
        javaParser parser = new javaParser() ;
        try {
	    //Ohne Animation
            parser.yyparse(scanner);

	    //Textuelle Dokumentation des Algorithmus
	    //parser.yyparse(scanner, new yyDebugAdapter());

	    //Grafische Animatiomn des Algorithmus
            //parser.yyparse(scanner, new yyAnim("Browser", yyAnim.IN)); 
	}
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
