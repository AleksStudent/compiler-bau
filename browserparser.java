import java.util.Vector;
// created by jay 0.8 (c) 1998 Axel.Schreiner@informatik.uni-osnabrueck.de

					// line 2 "browserparser.jay"
class browserparser {
					// line 6 "-"
 yyError yytemperror = new yyError();
 public final int yyErrorCode = yytemperror.tokennr; 

  /** thrown for irrecoverable syntax errors and stack overflow.
    */
  public static class yyException extends java.lang.Exception {
    public yyException (String message) {
      super(message);
    }
  }

  /** must be implemented by a scanner object to supply input to the parser.
    */
  public interface yyInput {
    /** move on to next token.
        @return false if positioned beyond tokens.
        @throws IOException on input error.
      */
  /** modified by diho, 10.04.2004 */
    yyTokenclass advance () throws java.io.IOException;
    /** classifies current token.
        Should not be called if advance() returned false.
        @return current %token or single character.
      */
	/** modified by diho, 10.04.2004 token () is not longer necessary,
		advance () supplies token or null
    int token (); */

	
    /** associated with current token.
        Should not be called if advance() returned false.
        @return value for token().
      */
    /** modified by diho, 10.04.2004 value () is not longer necessary,
		Token value can be retrieved from Token itself.
    Object value ();*/
  }

  /** simplified error message.
      @see <a href="#yyerror(java.lang.String, java.lang.String[])">yyerror</a>
    */
  public void yyerror (String message) {
    yyerror(message, null);
  }

  /** (syntax) error message.
      Can be overwritten to control message format.
      @param message text to be displayed.
      @param expected vector of acceptable tokens, if available.
    */
  public void yyerror (String message, String[] expected) {
    if (expected != null && expected.length > 0) {
      System.err.print(message+", expecting");
      for (int n = 0; n < expected.length; ++ n)
        System.err.print(" "+expected[n]);
      System.err.println();
    } else
      System.err.println(message);
  }

  /** debugging support, requires the package jay.yydebug.
      Set to null to suppress debugging messages.
    */
  protected jay.yydebug.yyDebug yydebug;

  protected static final int yyFinal = 2;

  /** index-checked interface to yyName[].
      @param token single character or %token value.
      @return token name or [illegal] or [unknown].
    */
  public static final String yyname (int token) {
    if (token < 0 || token > YyNameClass.yyName.length) return "[illegal]";
    String name;
    if ((name = YyNameClass.yyName[token]) != null) return name;
    return "[unknown]";
  }

  /** computes list of expected tokens on error by tracing the tables.
      @param state for which to compute the list.
      @return list of token names.
    */
  protected String[] yyExpecting (int state) {
    int token, n, len = 0;
    boolean[] ok = new boolean[YyNameClass.yyName.length];

    if ((n = YySindexClass.yySindex[state]) != 0)
      for (token = n < 0 ? -n : 0;
           token < YyNameClass.yyName.length && n+token < YyTableClass.yyTable.length; ++ token)
        if (YyCheckClass.yyCheck[n+token] == token && !ok[token] && YyNameClass.yyName[token] != null) {
          ++ len;
          ok[token] = true;
        }
    if ((n = YyRindexClass.yyRindex[state]) != 0)
      for (token = n < 0 ? -n : 0;
           token < YyNameClass.yyName.length && n+token < YyTableClass.yyTable.length; ++ token)
        if (YyCheckClass.yyCheck[n+token] == token && !ok[token] && YyNameClass.yyName[token] != null) {
          ++ len;
          ok[token] = true;
        }

    String result[] = new String[len];
    for (n = token = 0; n < len;  ++ token)
      if (ok[token]) result[n++] = YyNameClass.yyName[token];
    return result;
  }

  /** the generated parser, with debugging messages.
      Maintains a state and a value stack, currently with fixed maximum size.
      @param yyLex scanner.
      @param yydebug debug message writer implementing yyDebug, or null.
      @return result of the last reduction, if any.
      @throws yyException on irrecoverable parse error.
    */
  public Object yyparse (yyInput yyLex, Object yydebug)
				throws java.io.IOException, yyException {
    this.yydebug = (jay.yydebug.yyDebug)yydebug;
    return yyparse(yyLex);
  }

  /** initial size and increment of the state/value stack [default 256].
      This is not final so that it can be overwritten outside of invocations
      of yyparse().
    */
  protected int yyMax;

  /** executed at the beginning of a reduce action.
      Used as $$ = yyDefault($1), prior to the user-specified action, if any.
      Can be overwritten to provide deep copy, etc.
      @param first value for $1, or null.
      @return first.
    */
  protected Object yyDefault (Object first) {
    return first;
  }

  /** the generated parser.
      Maintains a state and a value stack, currently with fixed maximum size.
      @param yyLex scanner.
      @return result of the last reduction, if any.
      @throws yyException on irrecoverable parse error.
    */
  public Object yyparse (yyInput yyLex)
				throws java.io.IOException, yyException {
    YyCheckClass.yyCheckInit();                         // initial yyCheck eingefuegt PL 05-03-08
    YyTableClass.yyTableInit();                         // initial yyCheck eingefuegt PL 05-03-08
    if (yyMax <= 0) yyMax = 256;			// initial size
    int yyState = 0, yyStates[] = new int[yyMax];	// state stack
    Object yyVal = null, yyVals[] = new Object[yyMax];	// value stack
    /*modified by diho, 14.04.2004
      orig: int yyToken = -1 was replaced by "empty Token"*/
    yyTokenclass yyToken = new yyTokenclass();		// current input
    int yyErrorFlag = 0;				// #tks to shift

    yyLoop: for (int yyTop = 0;; ++ yyTop) {
      if (yyTop >= yyStates.length) {			// dynamically increase
        int[] i = new int[yyStates.length+yyMax];
        System.arraycopy(yyStates, 0, i, 0, yyStates.length);
        yyStates = i;
        Object[] o = new Object[yyVals.length+yyMax];
        System.arraycopy(yyVals, 0, o, 0, yyVals.length);
        yyVals = o;
      }
      yyStates[yyTop] = yyState;
      yyVals[yyTop] = yyVal;
      if (yydebug != null) yydebug.push(yyState, yyVal);

      yyDiscarded: for (;;) {	// discarding a token does not change stack
        int yyN;
        if ((yyN = YyDefRedClass.yyDefRed[yyState]) == 0) {	// else [default] reduce (yyN)
          /* modified by diho, 14.04.2004
             if "empty token", get next token */
          if (yyToken.tokennr <0) {
          /* modified by diho, 10.04.2004 
          call yyLex.advance in any case, check if yyToken is EOF */
            yyToken = yyLex.advance(); /*? yyLex.token() : 0;*/
           /* modified by diho, 27.04.2004
             if "null", create EOF token */
            if (yyToken == null) {yyToken = new EOF();}

            if (yydebug != null)
          /* modified by diho, 14.04.2004
             orig.: yydebug.lex(yyState, yyToken, yyname(yyToken), yyLex.value()); */
              yydebug.lex(yyState, yyToken.tokennr, yyname(yyToken.tokennr), yyToken.value);
          }
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr*/
          if ((yyN = YySindexClass.yySindex[yyState]) != 0 && (yyN += yyToken.tokennr) >= 0
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr*/
              && yyN < YyTableClass.yyTable.length && YyCheckClass.yyCheck[yyN] == yyToken.tokennr) {
            if (yydebug != null)
              yydebug.shift(yyState, YyTableClass.yyTable[yyN], yyErrorFlag-1);
            yyState = YyTableClass.yyTable[yyN];		// shift to yyN
          /* modified by diho, 14.04.2004 
             replaced yyLex.value() by yyToken.value*/
            yyVal = yyToken.value;
          /* modified by diho, 14.04.2004 
             orig:  yyToken = -1 */
            yyToken = new yyTokenclass();
            if (yyErrorFlag > 0) -- yyErrorFlag;
            continue yyLoop;
          }
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr*/
          if ((yyN = YyRindexClass.yyRindex[yyState]) != 0 && (yyN += yyToken.tokennr) >= 0
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr*/
              && yyN < YyTableClass.yyTable.length && YyCheckClass.yyCheck[yyN] == yyToken.tokennr)
            yyN = YyTableClass.yyTable[yyN];			// reduce (yyN)
          else
            switch (yyErrorFlag) {
  
            case 0:
              yyerror("syntax error", yyExpecting(yyState));
              if (yydebug != null) yydebug.error("syntax error");
  
            case 1: case 2:
              yyErrorFlag = 3;
              do {
                if ((yyN = YySindexClass.yySindex[yyStates[yyTop]]) != 0
                    && (yyN += yyErrorCode) >= 0 && yyN < YyTableClass.yyTable.length
                    && YyCheckClass.yyCheck[yyN] == yyErrorCode) {
                  if (yydebug != null)
                    yydebug.shift(yyStates[yyTop], YyTableClass.yyTable[yyN], 3);
                  yyState = YyTableClass.yyTable[yyN];
          /* modified by diho, 14.04.2004 
             replaced yyLex.value by yyToken.value*/
                  yyVal = yyToken.value;
                  continue yyLoop;
                }
                if (yydebug != null) yydebug.pop(yyStates[yyTop]);
              } while (-- yyTop >= 0);
              if (yydebug != null) yydebug.reject();
              throw new yyException("irrecoverable syntax error");
  
            case 3:
          /* modified by diho, 14.04.2004 
             orig.: if (yyToken == 0)*/
              if (yyToken == null) {
                if (yydebug != null) yydebug.reject();
                throw new yyException("irrecoverable syntax error at end-of-file");
              }
              if (yydebug != null)
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr, yyLex.value by yyToken.value*/
                yydebug.discard(yyState, yyToken.tokennr, yyname(yyToken.tokennr),
  							yyToken.value);
          /* modified by diho, 14.04.2004 
             orig: yyToken = -1*/
              yyToken = new yyTokenclass();
              continue yyDiscarded;		// leave stack alone
            }
        }
        int yyV = yyTop + 1-YyLenClass.yyLen[yyN];
        if (yydebug != null)
          yydebug.reduce(yyState, yyStates[yyV-1], yyN, YyRuleClass.yyRule[yyN], YyLenClass.yyLen[yyN]);
        yyVal = yyDefault(yyV > yyTop ? null : yyVals[yyV]);
        switch (yyN) {
case 1:
					// line 75 "browserparser.jay"
  { yyVal = new Program(((Class)yyVals[0+yyTop])); }
  break;
case 2:
					// line 76 "browserparser.jay"
  { yyVal = new Program(((Class)yyVals[0+yyTop])); }
  break;
case 3:
					// line 78 "browserparser.jay"
  { yyVal = new Class(((Vector)yyVals[-2+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 4:
					// line 80 "browserparser.jay"
  { yyVal = new Class( new Vector(), new Vector() ); }
  break;
case 5:
					// line 82 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 6:
					// line 83 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 7:
					// line 85 "browserparser.jay"
  { yyVal = new Field(((String)yyVals[-1+yyTop]), ((Type)yyVals[-2+yyTop])); }
  break;
case 8:
					// line 87 "browserparser.jay"
  { yyVal = new Type(); }
  break;
case 9:
					// line 89 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 10:
					// line 90 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 11:
					// line 92 "browserparser.jay"
  { yyVal = new Method(((String)yyVals[-4+yyTop]), ((Type)yyVals[-5+yyTop]), ((Vector)yyVals[-2+yyTop]), ((Block)yyVals[0+yyTop])); }
  break;
case 12:
					// line 94 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 13:
					// line 95 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 14:
					// line 97 "browserparser.jay"
  { yyVal = new Parameter(((String)yyVals[0+yyTop]), ((Type)yyVals[-1+yyTop])); }
  break;
case 15:
					// line 99 "browserparser.jay"
  { yyVal = new Block( new Vector() ); }
  break;
case 16:
					// line 100 "browserparser.jay"
  { yyVal = new Block(((Vector)yyVals[-1+yyTop])); }
  break;
case 17:
					// line 102 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 18:
					// line 103 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 19:
					// line 105 "browserparser.jay"
  { yyVal = new Stmt(); }
  break;
case 20:
					// line 106 "browserparser.jay"
  { yyVal = new Stmt(); }
  break;
case 21:
					// line 107 "browserparser.jay"
  { yyVal = new Stmt(); }
  break;
case 22:
					// line 108 "browserparser.jay"
  { yyVal = new Stmt(); }
  break;
case 23:
					// line 109 "browserparser.jay"
  { yyVal = new Stmt(); }
  break;
case 24:
					// line 110 "browserparser.jay"
  { yyVal = new Stmt(); }
  break;
case 25:
					// line 111 "browserparser.jay"
  { yyVal = new Stmt(); }
  break;
case 26:
					// line 113 "browserparser.jay"
  { yyVal = new If(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop]), new Stmt()); }
  break;
case 27:
					// line 115 "browserparser.jay"
  { yyVal = new If(((Expr)yyVals[-4+yyTop]), ((Stmt)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 28:
					// line 117 "browserparser.jay"
  { yyVal = new While(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 29:
					// line 119 "browserparser.jay"
  { yyVal = new Return(((Expr)yyVals[-1+yyTop])); }
  break;
case 30:
					// line 121 "browserparser.jay"
  { yyVal = new StmtExprExpr(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 31:
					// line 123 "browserparser.jay"
  { yyVal = new StmtExprStmt(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 32:
					// line 125 "browserparser.jay"
  { yyVal = new LocalVarDecl(((Type)yyVals[-2+yyTop]), ((String)yyVals[-1+yyTop])); }
  break;
case 33:
					// line 127 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 34:
					// line 128 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 35:
					// line 129 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 36:
					// line 130 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 37:
					// line 131 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 38:
					// line 132 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 39:
					// line 133 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 40:
					// line 134 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 41:
					// line 135 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 42:
					// line 136 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 43:
					// line 137 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 44:
					// line 138 "browserparser.jay"
  { yyVal = new Expr(); }
  break;
case 45:
					// line 140 "browserparser.jay"
  { yyVal = new StmtExpr(); }
  break;
case 46:
					// line 141 "browserparser.jay"
  { yyVal = new StmtExpr(); }
  break;
case 47:
					// line 142 "browserparser.jay"
  { yyVal = new StmtExpr(); }
  break;
case 48:
					// line 143 "browserparser.jay"
  { yyVal = new StmtExpr(); }
  break;
case 49:
					// line 145 "browserparser.jay"
  { yyVal = new This(); }
  break;
case 50:
					// line 147 "browserparser.jay"
  { yyVal = new Super(); }
  break;
case 51:
					// line 149 "browserparser.jay"
  { yyVal = new LocalOrFieldVar(((String)yyVals[0+yyTop])); }
  break;
case 52:
					// line 151 "browserparser.jay"
  { yyVal = new Integer(((String)yyVals[0+yyTop])); }
  break;
case 53:
					// line 153 "browserparser.jay"
  { yyVal = new Binary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 54:
					// line 155 "browserparser.jay"
  { yyVal = new Unary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 55:
					// line 157 "browserparser.jay"
  { yyVal = new Bool(((String)yyVals[0+yyTop])); }
  break;
case 56:
					// line 159 "browserparser.jay"
  { yyVal = new InstVar(((LocalOrFieldVar)yyVals[-2+yyTop]), ((String)yyVals[0+yyTop])); }
  break;
case 57:
					// line 161 "browserparser.jay"
  { yyVal = new JString(((String)yyVals[0+yyTop])); }
  break;
case 58:
					// line 163 "browserparser.jay"
  { yyVal = new Jnull(); }
  break;
case 59:
					// line 165 "browserparser.jay"
  { yyVal = new Char(((String)yyVals[0+yyTop])); }
  break;
case 60:
					// line 167 "browserparser.jay"
  { yyVal = new Assign(((String)yyVals[-2+yyTop]), ((Expr)yyVals[-3+yyTop]), ((Expr)yyVals[-1+yyTop])); }
  break;
case 61:
					// line 169 "browserparser.jay"
  { yyVal = new New(((Type)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 62:
					// line 171 "browserparser.jay"
  { yyVal = new MethodCall(new Expr(), ((String)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 63:
					// line 173 "browserparser.jay"
  { yyVal = new MethodCall(new Expr(), ((String)yyVals[-2+yyTop]), new Vector()); }
  break;
case 64:
					// line 175 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 65:
					// line 176 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
					// line 526 "-"
        }
        yyTop -= YyLenClass.yyLen[yyN];
        yyState = yyStates[yyTop];
        int yyM = YyLhsClass.yyLhs[yyN];
        if (yyState == 0 && yyM == 0) {
          if (yydebug != null) yydebug.shift(0, yyFinal);
          yyState = yyFinal;
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr*/
          if (yyToken.tokennr < 0) {
          /** modified by diho, 10.04.2004
          */
            yyToken = yyLex.advance(); /* ? yyLex.token() : 0;*/
          /* modified by diho, 14.04.2004
             if "empty token", get next token */
            if (yyToken == null) {yyToken = new EOF();}
            if (yydebug != null)
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr, yyLex.value() by yyToken.value*/
               yydebug.lex(yyState, yyToken.tokennr,yyname(yyToken.tokennr), yyToken.value);
          }
          /* modified by diho, 14.04.2004 
             orig.: if (yyToken == 0) */
          if (yyToken.tokennr == 0) {
            if (yydebug != null) yydebug.accept(yyVal);
            return yyVal;
          }
          continue yyLoop;
        }
        if ((yyN = YyGindexClass.yyGindex[yyM]) != 0 && (yyN += yyState) >= 0
            && yyN < YyTableClass.yyTable.length && YyCheckClass.yyCheck[yyN] == yyState)
          yyState = YyTableClass.yyTable[yyN];
        else
          yyState = YyDgotoClass.yyDgoto[yyM];
        if (yydebug != null) yydebug.shift(yyStates[yyTop], yyState);
	 continue yyLoop;
      }
    }
  }

  protected static final class YyLhsClass {

    public static final short yyLhs [] = {              -1,
          0,    0,    1,    2,   33,   33,    4,    6,   35,   35,
          3,   37,   37,    5,   26,   26,   34,   34,   20,   20,
         20,   20,   20,   20,   20,   23,   24,   22,   25,   19,
         27,   21,    7,    7,    7,    7,    7,    7,    7,    7,
          7,    7,    7,    7,   28,   28,   28,   28,    9,    8,
         16,   10,   17,   18,   11,   12,   13,   14,   15,   32,
         29,   30,   31,   36,   36,
    };
  } /* End of class YyLhsClass */

  protected static final class YyLenClass {

    public static final short yyLen [] = {           2,
          1,    1,    6,    4,    1,    2,    3,    1,    1,    2,
          6,    1,    3,    2,    2,    3,    1,    2,    1,    1,
          1,    1,    1,    1,    1,    5,    7,    5,    3,    1,
          1,    3,    1,    1,    1,    1,    1,    1,    1,    1,
          1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
          1,    1,    3,    2,    1,    3,    1,    1,    1,    4,
          5,    4,    3,    1,    2,
    };
  } /* End class YyLenClass */

  protected static final class YyDefRedClass {

    public static final short yyDefRed [] = {            0,
          0,    0,    1,    2,    0,    0,    8,    4,    5,    0,
          0,    0,    9,    6,    0,    0,    7,    0,    3,   10,
          0,    0,    0,   12,    0,    0,   14,    0,    0,   13,
          0,   11,    0,    0,   52,   57,   59,   55,    0,    0,
         15,   49,   50,    0,   58,    0,    0,    0,   34,   33,
         36,   39,   40,   41,   42,   43,    0,   37,   38,   44,
         17,   25,   21,   19,   20,   23,   22,   24,    0,   46,
         47,   48,   45,    0,    0,    0,    0,    0,   30,    0,
          0,    0,    0,    0,    0,   16,   18,    0,    0,   63,
          0,    0,    0,   29,   32,    0,    0,   56,    0,    0,
         62,    0,    0,   60,   28,    0,   61,    0,   27,
    };
  } /* End of class YyDefRedClass */

  protected static final class YyDgotoClass {

    public static final short yyDgoto [] = {             2,
          3,    4,   13,    9,   24,   47,   48,   49,   50,   51,
         52,   53,   54,   55,   56,   57,   58,   59,   60,   61,
         62,   63,   64,   65,   66,   67,   68,   79,   70,   71,
         72,   73,   11,   74,   16,   92,   26,
    };
  } /* End of class YyDgotoClass */

  protected static final class YySindexClass {

    public static final short yySindex [] = {         -256,
       -247,    0,    0,    0, -272, -260,    0,    0,    0, -242,
       -228, -238,    0,    0, -226, -258,    0, -259,    0,    0,
       -220, -228, -239,    0, -219, -257,    0, -228, -231,    0,
       -155,    0, -224, -223,    0,    0,    0,    0, -222,  151,
          0,    0,    0, -228,    0,  151, -216, -245,    0,    0,
          0,    0,    0,    0,    0,    0, -218,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0, -124,  151,  151,   54, -245,    0, -217,
       -246, -214,  151,  151, -208,    0,    0, -268, -263,    0,
       -245,   77,  151,    0,    0, -227, -245,    0,  -62,  -62,
          0, -245,  100,    0,    0, -200,    0,  -62,    0,
    };
  } /* End of class YySindexClass */

  protected static final class YyRindexClass {

    public static final short yyRindex [] = {            0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,  -38,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,  -15,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0, -186,    0,
          0,    0,    0,    0,    0,    0,    0,    8,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
        123,    0,    0,    0,    0,    0,   31,    0,    0,    0,
          0,  146,    0,    0,    0,  -93,    0,    0,    0,
    };
  } /* End of class YyRindexClass */

  protected static final class YyGindexClass {

    public static final short yyGindex [] = {            0,
          0,    0,   47,   55,   37,   -3,  -39,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,  -69,
          0,    0,    0,    0,    0,   38,    0,  -31,    0,    0,
          0,    0,    0,    0,    0,  -23,    0,
    };
  } /* End of class YyGindexClass */

protected static class yyTable0 {
  protected static final short yyTable0 [] = {            69,
         78,    7,   10,    7,   87,   83,   81,   15,    6,   84,
         83,   99,   21,   17,   84,    1,  100,   28,   25,   22,
          5,    8,   29,   19,   25,   12,   94,   83,   83,  105,
        106,   84,   84,    7,   17,   88,   89,   91,  109,   22,
         80,   18,   69,   96,   97,  104,   83,   23,   27,   31,
         84,   82,  102,   91,   75,   76,   77,   85,   95,   98,
        108,   93,   20,  102,   30,   14,   32,   69,   69,  103,
         31,    0,    0,   31,   31,   31,   69,   31,   31,   31,
         31,   31,    0,    0,    0,    0,    0,   30,    0,    0,
         31,   30,    0,    0,   31,   31,   31,   31,   31,   31,
         31,   33,    0,    0,   34,    0,    7,    0,   35,   36,
         37,   38,   39,    0,    0,    0,    0,    0,    0,    0,
          0,   40,    0,    0,    0,   31,   41,   42,   43,   44,
         45,   46,   33,    0,    0,   34,    0,    7,    0,   35,
         36,   37,   38,   39,    0,    0,    0,    0,    0,    0,
          0,    0,   40,    0,    0,    0,   31,   86,   42,   43,
         44,   45,   46,   26,    0,    0,   26,    0,   26,    0,
         26,   26,   26,   26,   26,    0,    0,    0,    0,    0,
          0,    0,    0,   26,    0,    0,    0,   26,   26,   26,
         26,   26,   26,   26,   33,    0,    0,   34,    0,    7,
          0,   35,   36,   37,   38,   39,    0,    0,    0,    0,
          0,    0,    0,    0,   40,    0,    0,    0,   31,    0,
         42,   43,   44,   45,   46,   51,   51,   51,   51,   51,
          0,    0,    0,    0,   51,   51,    0,   51,   51,   51,
          0,   51,    0,    0,   51,   51,   51,   51,   35,   35,
         35,   35,   35,    0,    0,    0,    0,   35,   35,    0,
          0,   35,   35,    0,   35,    0,    0,   35,   35,   35,
         35,   54,   54,   54,   54,   54,    0,    0,    0,    0,
         54,    0,    0,    0,   54,    0,    0,   54,    0,    0,
         54,   54,   54,   54,   53,   53,   53,   53,   53,    0,
          0,    0,    0,   53,    0,    0,    0,   53,    0,    0,
         53,    0,    0,   53,   53,   53,   53,   35,   36,   37,
         38,   39,    0,    0,    0,    0,    0,    0,    0,    0,
         40,    0,    0,   90,    0,    0,   42,   43,   44,   45,
         35,   36,   37,   38,   39,    0,    0,    0,    0,    0,
          0,    0,    0,   40,    0,    0,  101,    0,    0,   42,
         43,   44,   45,   35,   36,   37,   38,   39,    0,    0,
          0,    0,    0,    0,    0,    0,   40,    0,    0,  107,
          0,    0,   42,   43,   44,   45,   64,   64,   64,   64,
         64,    0,    0,    0,    0,    0,    0,    0,    0,   64,
          0,    0,   64,    0,    0,   64,   64,   64,   64,   65,
         65,   65,   65,   65,   35,   36,   37,   38,   39,    0,
          0,    0,   65,    0,    0,   65,    0,   40,   65,   65,
         65,   65,    0,   42,   43,   44,   45,
  };
}
  protected static final class YyTableClass {

  static short[] yyTable = new short[438];
  protected static void yyTableInit () {
     int numyycheck;
     int yyTableerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyTableerun < 438) {
          yyTable[yyTableerun] = yyTable0.yyTable0[numyycheck];
          yyTableerun++;
        }
     }
}
  } /* End of class YyTableClass */

protected static class yyCheck0 {
  protected static final short yyCheck0 [] = {            31,
         40,  262,    6,  262,   74,  274,   46,   11,  281,  278,
        274,  280,   16,  273,  278,  272,  280,  275,   22,  279,
        268,  282,  280,  282,   28,  268,  273,  274,  274,   99,
        100,  278,  278,  262,  273,   75,   76,   77,  108,  279,
         44,  268,   74,   83,   84,  273,  274,  268,  268,  281,
        278,  268,   92,   93,  279,  279,  279,  276,  273,  268,
        261,  279,   16,  103,   28,   11,   29,   99,  100,   93,
        257,   -1,   -1,  260,  261,  262,  108,  264,  265,  266,
        267,  268,   -1,   -1,   -1,   -1,   -1,  274,   -1,   -1,
        277,  278,   -1,   -1,  281,  282,  283,  284,  285,  286,
        287,  257,   -1,   -1,  260,   -1,  262,   -1,  264,  265,
        266,  267,  268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
         -1,  277,   -1,   -1,   -1,  281,  282,  283,  284,  285,
        286,  287,  257,   -1,   -1,  260,   -1,  262,   -1,  264,
        265,  266,  267,  268,   -1,   -1,   -1,   -1,   -1,   -1,
         -1,   -1,  277,   -1,   -1,   -1,  281,  282,  283,  284,
        285,  286,  287,  257,   -1,   -1,  260,   -1,  262,   -1,
        264,  265,  266,  267,  268,   -1,   -1,   -1,   -1,   -1,
         -1,   -1,   -1,  277,   -1,   -1,   -1,  281,  282,  283,
        284,  285,  286,  287,  257,   -1,   -1,  260,   -1,  262,
         -1,  264,  265,  266,  267,  268,   -1,   -1,   -1,   -1,
         -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,  281,   -1,
        283,  284,  285,  286,  287,  264,  265,  266,  267,  268,
         -1,   -1,   -1,   -1,  273,  274,   -1,  276,  277,  278,
         -1,  280,   -1,   -1,  283,  284,  285,  286,  264,  265,
        266,  267,  268,   -1,   -1,   -1,   -1,  273,  274,   -1,
         -1,  277,  278,   -1,  280,   -1,   -1,  283,  284,  285,
        286,  264,  265,  266,  267,  268,   -1,   -1,   -1,   -1,
        273,   -1,   -1,   -1,  277,   -1,   -1,  280,   -1,   -1,
        283,  284,  285,  286,  264,  265,  266,  267,  268,   -1,
         -1,   -1,   -1,  273,   -1,   -1,   -1,  277,   -1,   -1,
        280,   -1,   -1,  283,  284,  285,  286,  264,  265,  266,
        267,  268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
        277,   -1,   -1,  280,   -1,   -1,  283,  284,  285,  286,
        264,  265,  266,  267,  268,   -1,   -1,   -1,   -1,   -1,
         -1,   -1,   -1,  277,   -1,   -1,  280,   -1,   -1,  283,
        284,  285,  286,  264,  265,  266,  267,  268,   -1,   -1,
         -1,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,  280,
         -1,   -1,  283,  284,  285,  286,  264,  265,  266,  267,
        268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
         -1,   -1,  280,   -1,   -1,  283,  284,  285,  286,  264,
        265,  266,  267,  268,  264,  265,  266,  267,  268,   -1,
         -1,   -1,  277,   -1,   -1,  280,   -1,  277,  283,  284,
        285,  286,   -1,  283,  284,  285,  286,
  };
}
  protected static final class YyCheckClass {

  static short[] yyCheck = new short[438];
  protected static void yyCheckInit () {
     int numyycheck;
     int yyCheckerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyCheckerun < 438) {
          yyCheck[yyCheckerun] = yyCheck0.yyCheck0[numyycheck];
          yyCheckerun++;
        }
     }

    };
  } /* End of class YyCheckClass */


  protected static final class YyRuleClass {

    public static final String yyRule [] = {
    "$accept : programm",
    "programm : class",
    "programm : emptyClass",
    "class : JCLASS IDENTIFIER LBRACKET fields methods RBRACKET",
    "emptyClass : JCLASS IDENTIFIER LBRACKET RBRACKET",
    "fields : field",
    "fields : fields field",
    "field : type IDENTIFIER SEMICOLON",
    "type : JTYPE",
    "methods : method",
    "methods : methods method",
    "method : type IDENTIFIER LBRACE parameters RBRACE block",
    "parameters : parameter",
    "parameters : parameters COMMA parameter",
    "parameter : type IDENTIFIER",
    "block : LBRACKET RBRACKET",
    "block : LBRACKET statements RBRACKET",
    "statements : statement",
    "statements : statements statement",
    "statement : ifthenstatement",
    "statement : ifthenelsestatement",
    "statement : whilestatement",
    "statement : block",
    "statement : returnstatement",
    "statement : stmtExprStmt",
    "statement : localVarDecl",
    "ifthenstatement : JIF LBRACE expression RBRACE statement",
    "ifthenelsestatement : JIF LBRACE expression RBRACE statement ELSE statement",
    "whilestatement : JWHILE LBRACE expression RBRACE statement",
    "returnstatement : JRETURN expression SEMICOLON",
    "stmtExprExpr : stmtExpr",
    "stmtExprStmt : stmtExpr",
    "localVarDecl : type IDENTIFIER SEMICOLON",
    "expression : this",
    "expression : super",
    "expression : localOrFieldVar",
    "expression : integer",
    "expression : binary",
    "expression : unary",
    "expression : bool",
    "expression : instVar",
    "expression : string",
    "expression : jnull",
    "expression : char",
    "expression : stmtExprExpr",
    "stmtExpr : assign",
    "stmtExpr : new",
    "stmtExpr : methodCall",
    "stmtExpr : emptyMethodCall",
    "this : JTHIS",
    "super : JSUPER",
    "localOrFieldVar : IDENTIFIER",
    "integer : INT",
    "binary : expression OPERATOR expression",
    "unary : UNARYOPERATOR expression",
    "bool : BOOLEAN",
    "instVar : localOrFieldVar POINT IDENTIFIER",
    "string : STRING",
    "jnull : NULL",
    "char : CHAR",
    "assign : expression EQUALS expression SEMICOLON",
    "new : JNEW type LBRACE expressions RBRACE",
    "methodCall : IDENTIFIER LBRACE expressions RBRACE",
    "emptyMethodCall : IDENTIFIER LBRACE RBRACE",
    "expressions : expression",
    "expressions : expressions expression",
    };
  } /* End of class YyRuleClass */

  protected static final class YyNameClass {

    public static final String yyName [] = {    
    "end-of-file",null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,null,null,null,null,null,null,null,
    null,null,null,null,null,null,null,"JWHILE","DO","FOR","JIF","ELSE",
    "JTYPE","FLOAT","INT","STRING","CHAR","BOOLEAN","IDENTIFIER",
    "ACCESSRIGHT","STATIC","ABSTRACT","JCLASS","SEMICOLON","EQUALS",
    "COMMA","POINT","UNARYOPERATOR","OPERATOR","LBRACE","RBRACE",
    "LBRACKET","RBRACKET","JTHIS","JSUPER","JNEW","NULL","JRETURN","TRUE",
    };
  } /* End of class YyNameClass */


					// line 180 "browserparser.jay"
}
      					// line 905 "-"
class yyTokenclass {	
   public int tokennr;	
   public Object value;

   yyTokenclass () {
      this.tokennr=-1;
   }
   yyTokenclass (Object o) {
      this.value = o;
   }
}
class EOF extends yyTokenclass {	

   EOF () {
      super();
      this.tokennr=0;
   }
}
// %token constants

  final class JWHILE extends yyTokenclass {
   JWHILE(Object o) {
      super(o);
      this.tokennr = 257;
   }
   JWHILE() {
      super();
      this.tokennr = 257;
   }
}
  final class DO extends yyTokenclass {
   DO(Object o) {
      super(o);
      this.tokennr = 258;
   }
   DO() {
      super();
      this.tokennr = 258;
   }
}
  final class FOR extends yyTokenclass {
   FOR(Object o) {
      super(o);
      this.tokennr = 259;
   }
   FOR() {
      super();
      this.tokennr = 259;
   }
}
  final class JIF extends yyTokenclass {
   JIF(Object o) {
      super(o);
      this.tokennr = 260;
   }
   JIF() {
      super();
      this.tokennr = 260;
   }
}
  final class ELSE extends yyTokenclass {
   ELSE(Object o) {
      super(o);
      this.tokennr = 261;
   }
   ELSE() {
      super();
      this.tokennr = 261;
   }
}
  final class JTYPE extends yyTokenclass {
   JTYPE(Object o) {
      super(o);
      this.tokennr = 262;
   }
   JTYPE() {
      super();
      this.tokennr = 262;
   }
}
  final class FLOAT extends yyTokenclass {
   FLOAT(Object o) {
      super(o);
      this.tokennr = 263;
   }
   FLOAT() {
      super();
      this.tokennr = 263;
   }
}
  final class INT extends yyTokenclass {
   INT(Object o) {
      super(o);
      this.tokennr = 264;
   }
   INT() {
      super();
      this.tokennr = 264;
   }
}
  final class STRING extends yyTokenclass {
   STRING(Object o) {
      super(o);
      this.tokennr = 265;
   }
   STRING() {
      super();
      this.tokennr = 265;
   }
}
  final class CHAR extends yyTokenclass {
   CHAR(Object o) {
      super(o);
      this.tokennr = 266;
   }
   CHAR() {
      super();
      this.tokennr = 266;
   }
}
  final class BOOLEAN extends yyTokenclass {
   BOOLEAN(Object o) {
      super(o);
      this.tokennr = 267;
   }
   BOOLEAN() {
      super();
      this.tokennr = 267;
   }
}
  final class IDENTIFIER extends yyTokenclass {
   IDENTIFIER(Object o) {
      super(o);
      this.tokennr = 268;
   }
   IDENTIFIER() {
      super();
      this.tokennr = 268;
   }
}
  final class ACCESSRIGHT extends yyTokenclass {
   ACCESSRIGHT(Object o) {
      super(o);
      this.tokennr = 269;
   }
   ACCESSRIGHT() {
      super();
      this.tokennr = 269;
   }
}
  final class STATIC extends yyTokenclass {
   STATIC(Object o) {
      super(o);
      this.tokennr = 270;
   }
   STATIC() {
      super();
      this.tokennr = 270;
   }
}
  final class ABSTRACT extends yyTokenclass {
   ABSTRACT(Object o) {
      super(o);
      this.tokennr = 271;
   }
   ABSTRACT() {
      super();
      this.tokennr = 271;
   }
}
  final class JCLASS extends yyTokenclass {
   JCLASS(Object o) {
      super(o);
      this.tokennr = 272;
   }
   JCLASS() {
      super();
      this.tokennr = 272;
   }
}
  final class SEMICOLON extends yyTokenclass {
   SEMICOLON(Object o) {
      super(o);
      this.tokennr = 273;
   }
   SEMICOLON() {
      super();
      this.tokennr = 273;
   }
}
  final class EQUALS extends yyTokenclass {
   EQUALS(Object o) {
      super(o);
      this.tokennr = 274;
   }
   EQUALS() {
      super();
      this.tokennr = 274;
   }
}
  final class COMMA extends yyTokenclass {
   COMMA(Object o) {
      super(o);
      this.tokennr = 275;
   }
   COMMA() {
      super();
      this.tokennr = 275;
   }
}
  final class POINT extends yyTokenclass {
   POINT(Object o) {
      super(o);
      this.tokennr = 276;
   }
   POINT() {
      super();
      this.tokennr = 276;
   }
}
  final class UNARYOPERATOR extends yyTokenclass {
   UNARYOPERATOR(Object o) {
      super(o);
      this.tokennr = 277;
   }
   UNARYOPERATOR() {
      super();
      this.tokennr = 277;
   }
}
  final class OPERATOR extends yyTokenclass {
   OPERATOR(Object o) {
      super(o);
      this.tokennr = 278;
   }
   OPERATOR() {
      super();
      this.tokennr = 278;
   }
}
  final class LBRACE extends yyTokenclass {
   LBRACE(Object o) {
      super(o);
      this.tokennr = 279;
   }
   LBRACE() {
      super();
      this.tokennr = 279;
   }
}
  final class RBRACE extends yyTokenclass {
   RBRACE(Object o) {
      super(o);
      this.tokennr = 280;
   }
   RBRACE() {
      super();
      this.tokennr = 280;
   }
}
  final class LBRACKET extends yyTokenclass {
   LBRACKET(Object o) {
      super(o);
      this.tokennr = 281;
   }
   LBRACKET() {
      super();
      this.tokennr = 281;
   }
}
  final class RBRACKET extends yyTokenclass {
   RBRACKET(Object o) {
      super(o);
      this.tokennr = 282;
   }
   RBRACKET() {
      super();
      this.tokennr = 282;
   }
}
  final class JTHIS extends yyTokenclass {
   JTHIS(Object o) {
      super(o);
      this.tokennr = 283;
   }
   JTHIS() {
      super();
      this.tokennr = 283;
   }
}
  final class JSUPER extends yyTokenclass {
   JSUPER(Object o) {
      super(o);
      this.tokennr = 284;
   }
   JSUPER() {
      super();
      this.tokennr = 284;
   }
}
  final class JNEW extends yyTokenclass {
   JNEW(Object o) {
      super(o);
      this.tokennr = 285;
   }
   JNEW() {
      super();
      this.tokennr = 285;
   }
}
  final class NULL extends yyTokenclass {
   NULL(Object o) {
      super(o);
      this.tokennr = 286;
   }
   NULL() {
      super();
      this.tokennr = 286;
   }
}
  final class JRETURN extends yyTokenclass {
   JRETURN(Object o) {
      super(o);
      this.tokennr = 287;
   }
   JRETURN() {
      super();
      this.tokennr = 287;
   }
}
  final class TRUE extends yyTokenclass {
   TRUE(Object o) {
      super(o);
      this.tokennr = 288;
   }
   TRUE() {
      super();
      this.tokennr = 288;
   }
}
final class yyError extends yyTokenclass {
   yyError () {
      super();
      this.tokennr = 256;
   }
}
