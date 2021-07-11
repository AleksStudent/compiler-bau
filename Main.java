import jay.yydebug.*;

public class Main {
    public static void main (String [] args) {
        browserscanner scanner = new browserscanner(new java.io.InputStreamReader (System.in));
        browserparser parser = new browserparser() ;
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
