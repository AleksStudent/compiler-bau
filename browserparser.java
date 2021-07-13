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
  { yyVal = new Program(((Class)yyVals[0+yyTop])); System.out.println(new Program(((Class)yyVals[0+yyTop]))); }
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
  { yyVal = new Class(new Vector(), new Vector()); }
  break;
case 5:
					// line 82 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Field)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 6:
					// line 83 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Field)yyVals[0+yyTop])); yyVal = fs; }
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
  { Vector fs = new Vector(); fs.addElement(((Method)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 10:
					// line 90 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Method)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 11:
					// line 92 "browserparser.jay"
  { yyVal = new Method(((String)yyVals[-4+yyTop]), ((Type)yyVals[-5+yyTop]), ((Vector)yyVals[-2+yyTop]), ((Block)yyVals[0+yyTop])); }
  break;
case 12:
					// line 93 "browserparser.jay"
  { yyVal = new Method(((String)yyVals[-3+yyTop]), ((Type)yyVals[-4+yyTop]), new Vector(), ((Block)yyVals[0+yyTop])); }
  break;
case 13:
					// line 95 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Parameter)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 14:
					// line 96 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-2+yyTop]) ; fs.addElement(yyVals[-1+yyTop]); yyVal = fs; }
  break;
case 15:
					// line 98 "browserparser.jay"
  { yyVal = new Parameter(((String)yyVals[0+yyTop]), ((Type)yyVals[-1+yyTop])); }
  break;
case 16:
					// line 100 "browserparser.jay"
  { yyVal = new Block(new Vector()); }
  break;
case 17:
					// line 101 "browserparser.jay"
  { yyVal = new Block(((Vector)yyVals[-1+yyTop])); }
  break;
case 18:
					// line 103 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Stmt)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 19:
					// line 104 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Stmt)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 20:
					// line 106 "browserparser.jay"
  {}
  break;
case 21:
					// line 107 "browserparser.jay"
  {}
  break;
case 22:
					// line 108 "browserparser.jay"
  {}
  break;
case 23:
					// line 109 "browserparser.jay"
  {}
  break;
case 24:
					// line 110 "browserparser.jay"
  {}
  break;
case 25:
					// line 111 "browserparser.jay"
  {}
  break;
case 26:
					// line 112 "browserparser.jay"
  {}
  break;
case 27:
					// line 114 "browserparser.jay"
  { yyVal = new If(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop]), new Block(new Vector())); }
  break;
case 28:
					// line 116 "browserparser.jay"
  { yyVal = new If(((Expr)yyVals[-4+yyTop]), ((Stmt)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 29:
					// line 118 "browserparser.jay"
  { yyVal = new While(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 30:
					// line 120 "browserparser.jay"
  { yyVal = new Return(((Expr)yyVals[-1+yyTop])); }
  break;
case 31:
					// line 122 "browserparser.jay"
  { yyVal = new StmtExprExpr(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 32:
					// line 124 "browserparser.jay"
  { yyVal = new StmtExprStmt(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 33:
					// line 126 "browserparser.jay"
  { yyVal = new LocalVarDecl(((Type)yyVals[-2+yyTop]), ((String)yyVals[-1+yyTop])); }
  break;
case 34:
					// line 128 "browserparser.jay"
  {}
  break;
case 35:
					// line 129 "browserparser.jay"
  {}
  break;
case 36:
					// line 130 "browserparser.jay"
  {}
  break;
case 37:
					// line 131 "browserparser.jay"
  {}
  break;
case 38:
					// line 132 "browserparser.jay"
  {}
  break;
case 39:
					// line 133 "browserparser.jay"
  {}
  break;
case 40:
					// line 134 "browserparser.jay"
  {}
  break;
case 41:
					// line 135 "browserparser.jay"
  {}
  break;
case 42:
					// line 136 "browserparser.jay"
  {}
  break;
case 43:
					// line 137 "browserparser.jay"
  {}
  break;
case 44:
					// line 138 "browserparser.jay"
  {}
  break;
case 45:
					// line 139 "browserparser.jay"
  {}
  break;
case 46:
					// line 141 "browserparser.jay"
  {}
  break;
case 47:
					// line 142 "browserparser.jay"
  {}
  break;
case 48:
					// line 143 "browserparser.jay"
  {}
  break;
case 49:
					// line 144 "browserparser.jay"
  {}
  break;
case 50:
					// line 146 "browserparser.jay"
  { yyVal = new This(); }
  break;
case 51:
					// line 148 "browserparser.jay"
  { yyVal = new Super(); }
  break;
case 52:
					// line 150 "browserparser.jay"
  { yyVal = new LocalOrFieldVar(((String)yyVals[0+yyTop])); }
  break;
case 53:
					// line 152 "browserparser.jay"
  { yyVal = new Integer(((String)yyVals[0+yyTop])); }
  break;
case 54:
					// line 154 "browserparser.jay"
  { yyVal = new Binary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 55:
					// line 156 "browserparser.jay"
  { yyVal = new Unary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 56:
					// line 158 "browserparser.jay"
  { yyVal = new Bool(((String)yyVals[0+yyTop])); }
  break;
case 57:
					// line 160 "browserparser.jay"
  { yyVal = new InstVar(((Expr)yyVals[-2+yyTop]), ((String)yyVals[0+yyTop])); }
  break;
case 58:
					// line 162 "browserparser.jay"
  { yyVal = new JString(((String)yyVals[0+yyTop])); }
  break;
case 59:
					// line 164 "browserparser.jay"
  { yyVal = new Jnull(); }
  break;
case 60:
					// line 166 "browserparser.jay"
  { yyVal = new Char(((String)yyVals[0+yyTop])); }
  break;
case 61:
					// line 168 "browserparser.jay"
  { yyVal = new Assign(((String)yyVals[-3+yyTop]), ((Expr)yyVals[-1+yyTop])); }
  break;
case 62:
					// line 170 "browserparser.jay"
  { yyVal = new New(((Type)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 63:
					// line 171 "browserparser.jay"
  { yyVal = new New(((Type)yyVals[-2+yyTop]), new Vector()); }
  break;
case 64:
					// line 173 "browserparser.jay"
  { yyVal = new MethodCall(((Expr)yyVals[-5+yyTop]), ((String)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 65:
					// line 174 "browserparser.jay"
  { yyVal = new MethodCall(new This(), ((String)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 66:
					// line 176 "browserparser.jay"
  { yyVal = new MethodCall(((Expr)yyVals[-4+yyTop]), ((String)yyVals[-2+yyTop]), new Vector()); }
  break;
case 67:
					// line 177 "browserparser.jay"
  { yyVal = new MethodCall(new This(), ((String)yyVals[-2+yyTop]), new Vector()); }
  break;
case 68:
					// line 179 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
case 69:
					// line 180 "browserparser.jay"
  { yyVal = new Vector(); }
  break;
					// line 542 "-"
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
          3,    3,   37,   37,    5,   26,   26,   34,   34,   20,
         20,   20,   20,   20,   20,   20,   23,   24,   22,   25,
         19,   27,   21,    7,    7,    7,    7,    7,    7,    7,
          7,    7,    7,    7,    7,   29,   29,   29,   29,    9,
          8,   16,   10,   17,   18,   11,   12,   13,   14,   15,
         32,   28,   28,   30,   30,   31,   31,   36,   36,
    };
  } /* End of class YyLhsClass */

  protected static final class YyLenClass {

    public static final short yyLen [] = {           2,
          1,    1,    6,    4,    1,    2,    3,    1,    1,    2,
          6,    5,    1,    3,    2,    2,    3,    1,    2,    1,
          1,    1,    1,    1,    1,    1,    5,    7,    5,    3,
          1,    1,    3,    1,    1,    1,    1,    1,    1,    1,
          1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
          1,    1,    1,    3,    2,    1,    3,    1,    1,    1,
          4,    5,    4,    6,    4,    5,    3,    1,    3,
    };
  } /* End class YyLenClass */

  protected static final class YyDefRedClass {

    public static final short yyDefRed [] = {            0,
          0,    0,    1,    2,    0,    0,    8,    4,    5,    0,
          0,    0,    9,    6,    0,    0,    7,    0,    3,   10,
          0,    0,    0,    0,   13,    0,    0,    0,   12,   15,
          0,    0,    0,    0,   53,   58,   60,   56,    0,    0,
         16,   50,   51,    0,   59,    0,    0,    0,   35,   34,
         37,   40,   41,   42,   43,   44,   36,   38,   39,   45,
         18,   26,   22,   20,   21,   24,   23,   25,   47,    0,
         48,   49,   46,    0,   14,   11,    0,    0,    0,    0,
          0,   31,    0,    0,    0,    0,    0,   17,   19,    0,
          0,    0,   67,    0,    0,    0,   30,   33,    0,    0,
          0,    0,   61,    0,   65,   63,    0,    0,   29,    0,
          0,   62,   66,    0,    0,   64,   28,
    };
  } /* End of class YyDefRedClass */

  protected static final class YyDgotoClass {

    public static final short yyDgoto [] = {             2,
          3,    4,   13,    9,   25,   47,   48,   49,   50,   51,
         52,   53,   54,   55,   56,   57,   58,   59,   60,   61,
         62,   63,   64,   65,   66,   67,   68,   69,   82,   71,
         72,   73,   11,   74,   16,   95,   27,
    };
  } /* End of class YyDgotoClass */

  protected static final class YySindexClass {

    public static final short yySindex [] = {         -251,
       -236,    0,    0,    0, -229, -259,    0,    0,    0, -232,
       -209, -188,    0,    0, -204, -258,    0, -267,    0,    0,
       -201, -254, -197, -175,    0, -163, -264, -174,    0,    0,
       -209, -175, -164, -152,    0,    0,    0,    0, -252,   -6,
          0,    0,    0, -209,    0,   -6, -133, -146,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0, -143,    0,    0,   -6,   -6,   -6,  -57,
       -146,    0, -142, -245, -137, -122,   -6,    0,    0, -160,
       -129, -210,    0, -146, -231,  -34,    0,    0, -121, -146,
        -81,  -81,    0,   -6,    0,    0, -230,  -11,    0, -104,
       -146,    0,    0, -191,  -81,    0,    0,
    };
  } /* End of class YySindexClass */

  protected static final class YyRindexClass {

    public static final short yyRindex [] = {            0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0, -178,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0, -206,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
       -260,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0, -179,    0,    0,    0,    0, -147, -238,
          0,    0,    0,    0,    0,    0,    0,    0,    0, -112,
       -176,    0,    0,    0,    0,    0,    0,
    };
  } /* End of class YyRindexClass */

  protected static final class YyGindexClass {

    public static final short yyGindex [] = {            0,
          0,    0,  143,  149,  130,    3,  -39,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,  -72,
          0,    0,    0,    0,    0,  -14,    0,    0,  -28,    0,
          0,    0,    0,    0,    0,  -91,    0,
    };
  } /* End of class YyGindexClass */

protected static class yyTable0 {
  protected static final short yyTable0 [] = {            70,
         81,   89,    7,    7,  107,   17,   84,    7,   10,   29,
         31,   22,   55,   15,   55,   32,  114,   76,   21,   55,
          1,   79,    8,   19,   26,   24,   80,   97,  109,  110,
         86,    5,   87,   26,   54,   12,   54,   90,   91,   92,
         94,   54,  117,  104,  104,   70,   83,  100,  105,  112,
         32,    6,    7,   32,   32,   32,   94,   32,   32,   32,
         32,   32,  103,   18,  111,   86,   23,   87,   94,   31,
         32,   31,   70,   70,   32,   32,   32,   32,   32,   32,
         32,   22,   33,  104,   17,   34,   70,    7,  116,   35,
         36,   37,   38,   39,   52,   68,   52,   52,   69,   52,
         68,   52,   40,   69,   30,   28,   28,   41,   42,   43,
         44,   45,   46,   33,   77,   86,   34,   87,    7,  101,
         35,   36,   37,   38,   39,   57,   78,   57,   57,   86,
         57,   87,   57,   40,   85,   98,   96,   28,   88,   42,
         43,   44,   45,   46,   27,   99,   86,   27,   87,   27,
        102,   27,   27,   27,   27,   27,  115,  108,   20,   14,
         75,    0,    0,    0,   27,    0,    0,    0,   27,   27,
         27,   27,   27,   27,   27,   33,    0,    0,   34,    0,
          7,    0,   35,   36,   37,   38,   39,    0,    0,    0,
          0,    0,    0,    0,    0,   40,    0,    0,    0,   28,
          0,   42,   43,   44,   45,   46,   35,   36,   37,   38,
         39,    0,    0,    0,    0,    0,    0,    0,    0,   40,
          0,    0,   93,    0,    0,   42,   43,   44,   45,   35,
         36,   37,   38,   39,    0,    0,    0,    0,    0,    0,
          0,    0,   40,    0,    0,  106,    0,    0,   42,   43,
         44,   45,   35,   36,   37,   38,   39,   35,   36,   37,
         38,   39,    0,    0,    0,   40,    0,    0,  113,    0,
         40,   42,   43,   44,   45,    0,   42,   43,   44,   45,
  };
}
  protected static final class YyTableClass {

  static short[] yyTable = new short[281];
  protected static void yyTableInit () {
     int numyycheck;
     int yyTableerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyTableerun < 281) {
          yyTable[yyTableerun] = yyTable0.yyTable0[numyycheck];
          yyTableerun++;
        }
     }
}
  } /* End of class YyTableClass */

protected static class yyCheck0 {
  protected static final short yyCheck0 [] = {            28,
         40,   74,  262,  262,   96,  273,   46,  262,    6,   24,
        275,  279,  273,   11,  275,  280,  108,   32,   16,  280,
        272,  274,  282,  282,   22,  280,  279,  273,  101,  102,
        276,  268,  278,   31,  273,  268,  275,   77,   78,   79,
         80,  280,  115,  275,  275,   74,   44,   87,  280,  280,
        257,  281,  262,  260,  261,  262,   96,  264,  265,  266,
        267,  268,  273,  268,  104,  276,  268,  278,  108,  276,
        277,  278,  101,  102,  281,  282,  283,  284,  285,  286,
        287,  279,  257,  275,  273,  260,  115,  262,  280,  264,
        265,  266,  267,  268,  273,  275,  275,  276,  275,  278,
        280,  280,  277,  280,  268,  281,  281,  282,  283,  284,
        285,  286,  287,  257,  279,  276,  260,  278,  262,  280,
        264,  265,  266,  267,  268,  273,  279,  275,  276,  276,
        278,  278,  280,  277,  268,  273,  279,  281,  282,  283,
        284,  285,  286,  287,  257,  268,  276,  260,  278,  262,
        280,  264,  265,  266,  267,  268,  261,  279,   16,   11,
         31,   -1,   -1,   -1,  277,   -1,   -1,   -1,  281,  282,
        283,  284,  285,  286,  287,  257,   -1,   -1,  260,   -1,
        262,   -1,  264,  265,  266,  267,  268,   -1,   -1,   -1,
         -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,  281,
         -1,  283,  284,  285,  286,  287,  264,  265,  266,  267,
        268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
         -1,   -1,  280,   -1,   -1,  283,  284,  285,  286,  264,
        265,  266,  267,  268,   -1,   -1,   -1,   -1,   -1,   -1,
         -1,   -1,  277,   -1,   -1,  280,   -1,   -1,  283,  284,
        285,  286,  264,  265,  266,  267,  268,  264,  265,  266,
        267,  268,   -1,   -1,   -1,  277,   -1,   -1,  280,   -1,
        277,  283,  284,  285,  286,   -1,  283,  284,  285,  286,
  };
}
  protected static final class YyCheckClass {

  static short[] yyCheck = new short[281];
  protected static void yyCheckInit () {
     int numyycheck;
     int yyCheckerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyCheckerun < 281) {
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
    "method : type IDENTIFIER LBRACE RBRACE block",
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
    "instVar : expression POINT IDENTIFIER",
    "string : STRING",
    "jnull : NULL",
    "char : JCHAR",
    "assign : IDENTIFIER EQUALS expression SEMICOLON",
    "new : JNEW type LBRACE expressions RBRACE",
    "new : JNEW type LBRACE RBRACE",
    "methodCall : expression POINT IDENTIFIER LBRACE expressions RBRACE",
    "methodCall : IDENTIFIER LBRACE expressions RBRACE",
    "emptyMethodCall : expression POINT IDENTIFIER LBRACE RBRACE",
    "emptyMethodCall : IDENTIFIER LBRACE RBRACE",
    "expressions : expression",
    "expressions : expressions COMMA expression",
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
    "JTYPE","FLOAT","INT","STRING","JCHAR","BOOLEAN","IDENTIFIER",
    "ACCESSRIGHT","STATIC","ABSTRACT","JCLASS","SEMICOLON","EQUALS",
    "COMMA","POINT","UNARYOPERATOR","OPERATOR","LBRACE","RBRACE",
    "LBRACKET","RBRACKET","JTHIS","JSUPER","JNEW","NULL","JRETURN","TRUE",
    };
  } /* End of class YyNameClass */


					// line 184 "browserparser.jay"
}
      					// line 896 "-"
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
  final class JCHAR extends yyTokenclass {
   JCHAR(Object o) {
      super(o);
      this.tokennr = 266;
   }
   JCHAR() {
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
