// created by jay 0.8 (c) 1998 Axel.Schreiner@informatik.uni-osnabrueck.de

					// line 2 "browserparser.jay"
import java.util.Vector;
class browserparser {
					// line 7 "-"
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
//t  protected jay.yydebug.yyDebug yydebug;

  protected static final int yyFinal = 2;

  /** index-checked interface to yyName[].
      @param token single character or %token value.
      @return token name or [illegal] or [unknown].
    */
//t  public static final String yyname (int token) {
//t    if (token < 0 || token > YyNameClass.yyName.length) return "[illegal]";
//t    String name;
//t    if ((name = YyNameClass.yyName[token]) != null) return name;
//t    return "[unknown]";
//t  }

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
//t    this.yydebug = (jay.yydebug.yyDebug)yydebug;
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
//t      if (yydebug != null) yydebug.push(yyState, yyVal);

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

//t            if (yydebug != null)
//t          /* modified by diho, 14.04.2004
//t             orig.: yydebug.lex(yyState, yyToken, yyname(yyToken), yyLex.value()); */
//t              yydebug.lex(yyState, yyToken.tokennr, yyname(yyToken.tokennr), yyToken.value);
          }
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr*/
          if ((yyN = YySindexClass.yySindex[yyState]) != 0 && (yyN += yyToken.tokennr) >= 0
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr*/
              && yyN < YyTableClass.yyTable.length && YyCheckClass.yyCheck[yyN] == yyToken.tokennr) {
//t            if (yydebug != null)
//t              yydebug.shift(yyState, YyTableClass.yyTable[yyN], yyErrorFlag-1);
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
//t              if (yydebug != null) yydebug.error("syntax error");
  
            case 1: case 2:
              yyErrorFlag = 3;
              do {
                if ((yyN = YySindexClass.yySindex[yyStates[yyTop]]) != 0
                    && (yyN += yyErrorCode) >= 0 && yyN < YyTableClass.yyTable.length
                    && YyCheckClass.yyCheck[yyN] == yyErrorCode) {
//t                  if (yydebug != null)
//t                    yydebug.shift(yyStates[yyTop], YyTableClass.yyTable[yyN], 3);
                  yyState = YyTableClass.yyTable[yyN];
          /* modified by diho, 14.04.2004 
             replaced yyLex.value by yyToken.value*/
                  yyVal = yyToken.value;
                  continue yyLoop;
                }
//t                if (yydebug != null) yydebug.pop(yyStates[yyTop]);
              } while (-- yyTop >= 0);
//t              if (yydebug != null) yydebug.reject();
              throw new yyException("irrecoverable syntax error");
  
            case 3:
          /* modified by diho, 14.04.2004 
             orig.: if (yyToken == 0)*/
              if (yyToken == null) {
//t                if (yydebug != null) yydebug.reject();
                throw new yyException("irrecoverable syntax error at end-of-file");
              }
//t              if (yydebug != null)
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr, yyLex.value by yyToken.value*/
//t                yydebug.discard(yyState, yyToken.tokennr, yyname(yyToken.tokennr),
//t  							yyToken.value);
          /* modified by diho, 14.04.2004 
             orig: yyToken = -1*/
              yyToken = new yyTokenclass();
              continue yyDiscarded;		// leave stack alone
            }
        }
        int yyV = yyTop + 1-YyLenClass.yyLen[yyN];
//t        if (yydebug != null)
//t          yydebug.reduce(yyState, yyStates[yyV-1], yyN, YyRuleClass.yyRule[yyN], YyLenClass.yyLen[yyN]);
        yyVal = yyDefault(yyV > yyTop ? null : yyVals[yyV]);
        switch (yyN) {
case 1:
					// line 74 "browserparser.jay"
  { Program finalProgram = new Program(((Class)yyVals[0+yyTop])); finalProgram.typeCheck(); BytecodeGenerator.codeGen(finalProgram); yyVal=finalProgram; }
  break;
case 2:
					// line 75 "browserparser.jay"
  { Program finalProgram = new Program(((Class)yyVals[0+yyTop])); finalProgram.typeCheck(); BytecodeGenerator.codeGen(finalProgram); yyVal=finalProgram; }
  break;
case 3:
					// line 77 "browserparser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-4+yyTop])), ((Vector)yyVals[-2+yyTop]), ((Vector)yyVals[-1+yyTop])); System.out.println(new Class(new Type(((String)yyVals[-4+yyTop])), ((Vector)yyVals[-2+yyTop]), ((Vector)yyVals[-1+yyTop]))); }
  break;
case 4:
					// line 78 "browserparser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-3+yyTop])), new Vector(), ((Vector)yyVals[-1+yyTop])); }
  break;
case 5:
					// line 79 "browserparser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-3+yyTop])), ((Vector)yyVals[-1+yyTop]), new Vector()); }
  break;
case 6:
					// line 81 "browserparser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-2+yyTop])), new Vector(), new Vector()); }
  break;
case 7:
					// line 83 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Field)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 8:
					// line 84 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Field)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 9:
					// line 86 "browserparser.jay"
  { yyVal = new Field(((String)yyVals[-1+yyTop]), ((Type)yyVals[-2+yyTop])); }
  break;
case 10:
					// line 88 "browserparser.jay"
  { yyVal = new Type(((String)yyVals[0+yyTop])); }
  break;
case 11:
					// line 90 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Method)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 12:
					// line 91 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Method)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 13:
					// line 93 "browserparser.jay"
  { yyVal = new Method(((String)yyVals[-4+yyTop]), ((Type)yyVals[-5+yyTop]), ((Vector)yyVals[-2+yyTop]), ((Block)yyVals[0+yyTop])); }
  break;
case 14:
					// line 94 "browserparser.jay"
  { yyVal = new Method(((String)yyVals[-3+yyTop]), ((Type)yyVals[-4+yyTop]), new Vector(), ((Block)yyVals[0+yyTop])); }
  break;
case 15:
					// line 96 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Parameter)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 16:
					// line 97 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-2+yyTop]) ; fs.addElement(((Parameter)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 17:
					// line 99 "browserparser.jay"
  { yyVal = new Parameter(((String)yyVals[0+yyTop]), ((Type)yyVals[-1+yyTop])); }
  break;
case 18:
					// line 101 "browserparser.jay"
  { yyVal = new Block(new Vector()); }
  break;
case 19:
					// line 102 "browserparser.jay"
  { yyVal = new Block(((Vector)yyVals[-1+yyTop])); }
  break;
case 20:
					// line 104 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Stmt)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 21:
					// line 105 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Stmt)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 22:
					// line 107 "browserparser.jay"
  {}
  break;
case 23:
					// line 108 "browserparser.jay"
  {}
  break;
case 24:
					// line 109 "browserparser.jay"
  {}
  break;
case 25:
					// line 110 "browserparser.jay"
  {}
  break;
case 26:
					// line 111 "browserparser.jay"
  {}
  break;
case 27:
					// line 112 "browserparser.jay"
  {}
  break;
case 28:
					// line 113 "browserparser.jay"
  {}
  break;
case 29:
					// line 115 "browserparser.jay"
  { yyVal = new If(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop]), null); }
  break;
case 30:
					// line 117 "browserparser.jay"
  { yyVal = new If(((Expr)yyVals[-4+yyTop]), ((Stmt)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 31:
					// line 119 "browserparser.jay"
  { yyVal = new While(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 32:
					// line 121 "browserparser.jay"
  { yyVal = new Return(((Expr)yyVals[0+yyTop])); }
  break;
case 33:
					// line 123 "browserparser.jay"
  { yyVal = new StmtExprExpr(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 34:
					// line 125 "browserparser.jay"
  { yyVal = new StmtExprStmt(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 35:
					// line 127 "browserparser.jay"
  { yyVal = new LocalVarDecl(((Type)yyVals[-1+yyTop]), ((String)yyVals[0+yyTop])); }
  break;
case 36:
					// line 129 "browserparser.jay"
  {}
  break;
case 37:
					// line 130 "browserparser.jay"
  {}
  break;
case 38:
					// line 131 "browserparser.jay"
  {}
  break;
case 39:
					// line 132 "browserparser.jay"
  {}
  break;
case 40:
					// line 133 "browserparser.jay"
  {}
  break;
case 41:
					// line 134 "browserparser.jay"
  {}
  break;
case 42:
					// line 135 "browserparser.jay"
  {}
  break;
case 43:
					// line 136 "browserparser.jay"
  {}
  break;
case 44:
					// line 137 "browserparser.jay"
  {}
  break;
case 45:
					// line 138 "browserparser.jay"
  {}
  break;
case 46:
					// line 139 "browserparser.jay"
  {}
  break;
case 47:
					// line 140 "browserparser.jay"
  {}
  break;
case 48:
					// line 141 "browserparser.jay"
  {yyVal = ((Expr)yyVals[-1+yyTop]);}
  break;
case 49:
					// line 143 "browserparser.jay"
  {}
  break;
case 50:
					// line 144 "browserparser.jay"
  {}
  break;
case 51:
					// line 145 "browserparser.jay"
  {}
  break;
case 52:
					// line 146 "browserparser.jay"
  {}
  break;
case 53:
					// line 148 "browserparser.jay"
  { yyVal = new This(); }
  break;
case 54:
					// line 150 "browserparser.jay"
  { yyVal = new Super(); }
  break;
case 55:
					// line 152 "browserparser.jay"
  { yyVal = new LocalOrFieldVar(((String)yyVals[0+yyTop])); }
  break;
case 56:
					// line 154 "browserparser.jay"
  { yyVal = new Integer(((String)yyVals[0+yyTop])); }
  break;
case 57:
					// line 156 "browserparser.jay"
  { yyVal = new Binary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 58:
					// line 157 "browserparser.jay"
  { yyVal = new Binary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 59:
					// line 159 "browserparser.jay"
  { yyVal = new Unary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 60:
					// line 160 "browserparser.jay"
  { yyVal = new Unary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 61:
					// line 162 "browserparser.jay"
  { yyVal = new Bool(((String)yyVals[0+yyTop])); }
  break;
case 62:
					// line 164 "browserparser.jay"
  { yyVal = new InstVar(((Expr)yyVals[-2+yyTop]), ((String)yyVals[0+yyTop])); }
  break;
case 63:
					// line 166 "browserparser.jay"
  { yyVal = new JString(((String)yyVals[0+yyTop])); }
  break;
case 64:
					// line 168 "browserparser.jay"
  { yyVal = new Jnull(); }
  break;
case 65:
					// line 170 "browserparser.jay"
  { yyVal = new Char(((String)yyVals[0+yyTop])); }
  break;
case 66:
					// line 172 "browserparser.jay"
  { yyVal = new Assign(((String)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 67:
					// line 174 "browserparser.jay"
  { yyVal = new New(((Type)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 68:
					// line 175 "browserparser.jay"
  { yyVal = new New(((Type)yyVals[-2+yyTop]), new Vector()); }
  break;
case 69:
					// line 177 "browserparser.jay"
  { yyVal = new MethodCall(((Expr)yyVals[-5+yyTop]), ((String)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 70:
					// line 178 "browserparser.jay"
  { yyVal = new MethodCall(new This(), ((String)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 71:
					// line 180 "browserparser.jay"
  { yyVal = new MethodCall(((Expr)yyVals[-4+yyTop]), ((String)yyVals[-2+yyTop]), new Vector()); }
  break;
case 72:
					// line 181 "browserparser.jay"
  { yyVal = new MethodCall(new This(), ((String)yyVals[-2+yyTop]), new Vector()); }
  break;
case 73:
					// line 183 "browserparser.jay"
  { Vector fs = new Vector(); fs.addElement(((Expr)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 74:
					// line 184 "browserparser.jay"
  { Vector fs = ((Vector)yyVals[-2+yyTop]) ; fs.addElement(((Expr)yyVals[0+yyTop])); yyVal = fs; }
  break;
					// line 563 "-"
        }
        yyTop -= YyLenClass.yyLen[yyN];
        yyState = yyStates[yyTop];
        int yyM = YyLhsClass.yyLhs[yyN];
        if (yyState == 0 && yyM == 0) {
//t          if (yydebug != null) yydebug.shift(0, yyFinal);
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
//t            if (yydebug != null)
          /* modified by diho, 14.04.2004 
             replaced yyToken by yyToken.tokennr, yyLex.value() by yyToken.value*/
//t               yydebug.lex(yyState, yyToken.tokennr,yyname(yyToken.tokennr), yyToken.value);
          }
          /* modified by diho, 14.04.2004 
             orig.: if (yyToken == 0) */
          if (yyToken.tokennr == 0) {
//t            if (yydebug != null) yydebug.accept(yyVal);
            return yyVal;
          }
          continue yyLoop;
        }
        if ((yyN = YyGindexClass.yyGindex[yyM]) != 0 && (yyN += yyState) >= 0
            && yyN < YyTableClass.yyTable.length && YyCheckClass.yyCheck[yyN] == yyState)
          yyState = YyTableClass.yyTable[yyN];
        else
          yyState = YyDgotoClass.yyDgoto[yyM];
//t        if (yydebug != null) yydebug.shift(yyStates[yyTop], yyState);
	 continue yyLoop;
      }
    }
  }

  protected static final class YyLhsClass {

    public static final short yyLhs [] = {              -1,
          0,    0,    1,    1,    1,    2,   33,   33,    4,    6,
         35,   35,    3,    3,   37,   37,    5,   26,   26,   34,
         34,   20,   20,   20,   20,   20,   20,   20,   23,   24,
         22,   25,   19,   27,   21,    7,    7,    7,    7,    7,
          7,    7,    7,    7,    7,    7,    7,    7,   29,   29,
         29,   29,    9,    8,   16,   10,   17,   17,   18,   18,
         11,   12,   13,   14,   15,   32,   28,   28,   30,   30,
         31,   31,   36,   36,
    };
  } /* End of class YyLhsClass */

  protected static final class YyLenClass {

    public static final short yyLen [] = {           2,
          1,    1,    6,    5,    5,    4,    1,    2,    3,    1,
          1,    2,    6,    5,    1,    3,    2,    2,    3,    1,
          2,    1,    1,    1,    1,    2,    2,    2,    5,    7,
          5,    2,    1,    1,    2,    1,    1,    1,    1,    1,
          1,    1,    1,    1,    1,    1,    1,    3,    1,    1,
          1,    1,    1,    1,    1,    1,    3,    3,    2,    2,
          1,    3,    1,    1,    1,    3,    5,    4,    6,    4,
          5,    3,    1,    3,
    };
  } /* End class YyLenClass */

  protected static final class YyDefRedClass {

    public static final short yyDefRed [] = {            0,
          0,    0,    1,    2,    0,    0,   10,    6,   11,    7,
          0,    0,    0,    0,    5,    8,    0,    4,   12,    0,
          9,    0,    3,    0,    0,   15,    0,    0,    0,   14,
         17,    0,    0,    0,    0,   56,   63,   65,   61,    0,
          0,    0,    0,   18,   53,   54,    0,   64,    0,    0,
          0,   37,   36,   39,   42,   43,   44,   45,   46,   38,
         40,   41,   47,   20,    0,   24,   22,   23,    0,   25,
          0,   50,    0,   51,   52,   49,    0,   16,   13,    0,
          0,    0,    0,    0,   33,    0,    0,    0,    0,   35,
          0,    0,    0,   28,   26,   27,   19,   21,    0,    0,
          0,   72,    0,    0,   48,    0,    0,    0,    0,    0,
          0,    0,   70,   68,    0,    0,   31,    0,    0,   67,
         71,    0,    0,   69,   30,
    };
  } /* End of class YyDefRedClass */

  protected static final class YyDgotoClass {

    public static final short yyDgoto [] = {             2,
          3,    4,    9,   10,   26,   50,   51,   52,   53,   54,
         55,   56,   57,   58,   59,   60,   61,   62,   63,   64,
         65,   66,   67,   68,   69,   70,   71,   72,   85,   74,
         75,   76,   12,   77,   13,  104,   28,
    };
  } /* End of class YyDgotoClass */

  protected static final class YySindexClass {

    public static final short yySindex [] = {         -253,
       -248,    0,    0,    0, -262, -256,    0,    0,    0,    0,
       -233, -255, -254, -239,    0,    0, -252,    0,    0, -223,
          0, -249,    0, -204, -203,    0, -175, -228, -177,    0,
          0, -167, -203, -182, -181,    0,    0,    0,    0, -214,
          7,    7,    7,    0,    0,    0, -167,    0,    7, -165,
       -241,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0, -160,    0,    0,    0, -159,    0,
       -157,    0,    0,    0,    0,    0, -147,    0,    0,    7,
          7,    7,  -63, -241,    0, -241, -152, -146, -241,    0,
       -141,    7,    7,    0,    0,    0,    0,    0,  -45,  -22,
       -241,    0, -241, -195,    0,  -39, -137, -241, -241,  -87,
        -87,    7,    0,    0,  -90,  -16,    0, -119, -241,    0,
          0,  -68,  -87,    0,    0,
    };
  } /* End of class YySindexClass */

  protected static final class YyRindexClass {

    public static final short yyRindex [] = {            0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0, -217,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0, -120,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0, -251,    0, -224,    0,    0, -127,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
       -210,    0,  -66,    0,    0,    0, -206, -180, -150,    0,
          0,    0,    0,    0,    0,    0,    0, -117,  -56,    0,
          0,    0,    0,    0,    0,
    };
  } /* End of class YyRindexClass */

  protected static final class YyGindexClass {

    public static final short yyGindex [] = {            0,
          0,    0,   62,  140,  121,   -1,  -40,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,  -73,
          0,    0,    0,    0,    0,  -10,    0,    0,  -29,    0,
          0,    0,    0,    0,  143,  -92,    0,
    };
  } /* End of class YyGindexClass */

protected static class yyTable0 {
  protected static final short yyTable0 [] = {            73,
         84,   86,   87,   98,   11,    7,    7,    7,   89,    7,
         11,   20,    7,  115,   30,   20,    1,    6,    5,   59,
         27,   59,   79,  122,    8,   15,   18,   59,   23,   25,
         27,   21,   91,   14,   92,   93,  117,  118,   22,   99,
        100,  101,  103,   24,   32,   88,   60,   73,   60,  125,
         33,  108,  109,   55,   60,   55,   55,   82,   55,   55,
         66,   55,   66,   83,   62,  103,   62,   62,   66,   62,
         62,  119,   62,   22,   19,  103,   29,  112,   19,   34,
         73,   73,   35,  113,    7,   36,   37,   38,   39,   40,
         57,   31,   57,   73,    7,   80,   81,   41,   57,   42,
         43,   90,   29,   44,   45,   46,   47,   48,   49,   34,
         94,   95,   35,   96,    7,   36,   37,   38,   39,   40,
         58,   91,   58,   92,   93,  107,  105,   41,   58,   42,
         43,  106,   29,   97,   45,   46,   47,   48,   49,   29,
        116,  123,   29,   32,   29,   29,   29,   29,   29,   29,
         34,   16,   78,   33,   17,   33,   33,   29,    0,   29,
         29,    0,   29,   29,   29,   29,   29,   29,   29,   34,
          0,    0,   35,    0,    7,   36,   37,   38,   39,   40,
          0,    0,  112,    0,    0,    0,    0,   41,  120,   42,
         43,    0,   29,    0,   45,   46,   47,   48,   49,   36,
         37,   38,   39,   40,  112,    0,   73,    0,    0,    0,
        124,   41,   73,   42,   43,  102,   74,    0,   45,   46,
         47,   48,   74,   36,   37,   38,   39,   40,   91,    0,
         92,   93,    0,  110,    0,   41,    0,   42,   43,  114,
          0,    0,   45,   46,   47,   48,   36,   37,   38,   39,
         40,   91,    0,   92,   93,    0,  111,    0,   41,    0,
         42,   43,  121,    0,    0,   45,   46,   47,   48,   36,
         37,   38,   39,   40,    0,    0,    0,    0,    0,    0,
          0,   41,    0,   42,   43,    0,    0,    0,   45,   46,
         47,   48,
  };
}
  protected static final class YyTableClass {

  static short[] yyTable = new short[293];
  protected static void yyTableInit () {
     int numyycheck;
     int yyTableerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyTableerun < 293) {
          yyTable[yyTableerun] = yyTable0.yyTable0[numyycheck];
          yyTableerun++;
        }
     }
}
  } /* End of class YyTableClass */

protected static class yyCheck0 {
  protected static final short yyCheck0 [] = {            29,
         41,   42,   43,   77,    6,  262,  262,  262,   49,  262,
         12,   13,  262,  106,   25,   17,  270,  280,  267,  271,
         22,  273,   33,  116,  281,  281,  281,  279,  281,  279,
         32,  271,  274,  267,  276,  277,  110,  111,  278,   80,
         81,   82,   83,  267,  273,   47,  271,   77,  273,  123,
        279,   92,   93,  271,  279,  273,  274,  272,  276,  277,
        271,  279,  273,  278,  271,  106,  273,  274,  279,  276,
        277,  112,  279,  278,   13,  116,  280,  273,   17,  257,
        110,  111,  260,  279,  262,  263,  264,  265,  266,  267,
        271,  267,  273,  123,  262,  278,  278,  275,  279,  277,
        278,  267,  280,  281,  282,  283,  284,  285,  286,  257,
        271,  271,  260,  271,  262,  263,  264,  265,  266,  267,
        271,  274,  273,  276,  277,  267,  279,  275,  279,  277,
        278,  278,  280,  281,  282,  283,  284,  285,  286,  257,
        278,  261,  260,  271,  262,  263,  264,  265,  266,  267,
        271,   12,   32,  274,   12,  276,  277,  275,   -1,  277,
        278,   -1,  280,  281,  282,  283,  284,  285,  286,  257,
         -1,   -1,  260,   -1,  262,  263,  264,  265,  266,  267,
         -1,   -1,  273,   -1,   -1,   -1,   -1,  275,  279,  277,
        278,   -1,  280,   -1,  282,  283,  284,  285,  286,  263,
        264,  265,  266,  267,  273,   -1,  273,   -1,   -1,   -1,
        279,  275,  279,  277,  278,  279,  273,   -1,  282,  283,
        284,  285,  279,  263,  264,  265,  266,  267,  274,   -1,
        276,  277,   -1,  279,   -1,  275,   -1,  277,  278,  279,
         -1,   -1,  282,  283,  284,  285,  263,  264,  265,  266,
        267,  274,   -1,  276,  277,   -1,  279,   -1,  275,   -1,
        277,  278,  279,   -1,   -1,  282,  283,  284,  285,  263,
        264,  265,  266,  267,   -1,   -1,   -1,   -1,   -1,   -1,
         -1,  275,   -1,  277,  278,   -1,   -1,   -1,  282,  283,
        284,  285,
  };
}
  protected static final class YyCheckClass {

  static short[] yyCheck = new short[293];
  protected static void yyCheckInit () {
     int numyycheck;
     int yyCheckerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyCheckerun < 293) {
          yyCheck[yyCheckerun] = yyCheck0.yyCheck0[numyycheck];
          yyCheckerun++;
        }
     }

    };
  } /* End of class YyCheckClass */


//t  protected static final class YyRuleClass {

//t    public static final String yyRule [] = {
//t    "$accept : programm",
//t    "programm : class",
//t    "programm : emptyClass",
//t    "class : JCLASS IDENTIFIER LBRACKET fields methods RBRACKET",
//t    "class : JCLASS IDENTIFIER LBRACKET methods RBRACKET",
//t    "class : JCLASS IDENTIFIER LBRACKET fields RBRACKET",
//t    "emptyClass : JCLASS IDENTIFIER LBRACKET RBRACKET",
//t    "fields : field",
//t    "fields : fields field",
//t    "field : type IDENTIFIER SEMICOLON",
//t    "type : JTYPE",
//t    "methods : method",
//t    "methods : methods method",
//t    "method : type IDENTIFIER LBRACE parameters RBRACE block",
//t    "method : type IDENTIFIER LBRACE RBRACE block",
//t    "parameters : parameter",
//t    "parameters : parameters COMMA parameter",
//t    "parameter : type IDENTIFIER",
//t    "block : LBRACKET RBRACKET",
//t    "block : LBRACKET statements RBRACKET",
//t    "statements : statement",
//t    "statements : statements statement",
//t    "statement : ifthenstatement",
//t    "statement : ifthenelsestatement",
//t    "statement : whilestatement",
//t    "statement : block",
//t    "statement : returnstatement SEMICOLON",
//t    "statement : stmtExprStmt SEMICOLON",
//t    "statement : localVarDecl SEMICOLON",
//t    "ifthenstatement : JIF LBRACE expression RBRACE statement",
//t    "ifthenelsestatement : JIF LBRACE expression RBRACE statement ELSE statement",
//t    "whilestatement : JWHILE LBRACE expression RBRACE statement",
//t    "returnstatement : JRETURN expression",
//t    "stmtExprExpr : stmtExpr",
//t    "stmtExprStmt : stmtExpr",
//t    "localVarDecl : type IDENTIFIER",
//t    "expression : this",
//t    "expression : super",
//t    "expression : localOrFieldVar",
//t    "expression : integer",
//t    "expression : binary",
//t    "expression : unary",
//t    "expression : bool",
//t    "expression : instVar",
//t    "expression : string",
//t    "expression : jnull",
//t    "expression : char",
//t    "expression : stmtExprExpr",
//t    "expression : LBRACE expression RBRACE",
//t    "stmtExpr : assign",
//t    "stmtExpr : new",
//t    "stmtExpr : methodCall",
//t    "stmtExpr : emptyMethodCall",
//t    "this : JTHIS",
//t    "super : JSUPER",
//t    "localOrFieldVar : IDENTIFIER",
//t    "integer : INT",
//t    "binary : expression BINARYOPERATOR expression",
//t    "binary : expression PLUSMINUSOPERATOR expression",
//t    "unary : UNARYOPERATOR expression",
//t    "unary : PLUSMINUSOPERATOR expression",
//t    "bool : BOOLEAN",
//t    "instVar : expression POINT IDENTIFIER",
//t    "string : STRING",
//t    "jnull : NULL",
//t    "char : JCHAR",
//t    "assign : IDENTIFIER EQUALS expression",
//t    "new : JNEW type LBRACE expressions RBRACE",
//t    "new : JNEW type LBRACE RBRACE",
//t    "methodCall : expression POINT IDENTIFIER LBRACE expressions RBRACE",
//t    "methodCall : IDENTIFIER LBRACE expressions RBRACE",
//t    "emptyMethodCall : expression POINT IDENTIFIER LBRACE RBRACE",
//t    "emptyMethodCall : IDENTIFIER LBRACE RBRACE",
//t    "expressions : expression",
//t    "expressions : expressions COMMA expression",
//t    };
//t  } /* End of class YyRuleClass */

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
    "JTYPE","INT","STRING","JCHAR","BOOLEAN","IDENTIFIER","STATIC",
    "ABSTRACT","JCLASS","SEMICOLON","EQUALS","COMMA","POINT",
    "UNARYOPERATOR","BINARYOPERATOR","PLUSMINUSOPERATOR","LBRACE",
    "RBRACE","LBRACKET","RBRACKET","JTHIS","JSUPER","JNEW","NULL",
    "JRETURN",
    };
  } /* End of class YyNameClass */


					// line 188 "browserparser.jay"
}
      					// line 932 "-"
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
  final class INT extends yyTokenclass {
   INT(Object o) {
      super(o);
      this.tokennr = 263;
   }
   INT() {
      super();
      this.tokennr = 263;
   }
}
  final class STRING extends yyTokenclass {
   STRING(Object o) {
      super(o);
      this.tokennr = 264;
   }
   STRING() {
      super();
      this.tokennr = 264;
   }
}
  final class JCHAR extends yyTokenclass {
   JCHAR(Object o) {
      super(o);
      this.tokennr = 265;
   }
   JCHAR() {
      super();
      this.tokennr = 265;
   }
}
  final class BOOLEAN extends yyTokenclass {
   BOOLEAN(Object o) {
      super(o);
      this.tokennr = 266;
   }
   BOOLEAN() {
      super();
      this.tokennr = 266;
   }
}
  final class IDENTIFIER extends yyTokenclass {
   IDENTIFIER(Object o) {
      super(o);
      this.tokennr = 267;
   }
   IDENTIFIER() {
      super();
      this.tokennr = 267;
   }
}
  final class STATIC extends yyTokenclass {
   STATIC(Object o) {
      super(o);
      this.tokennr = 268;
   }
   STATIC() {
      super();
      this.tokennr = 268;
   }
}
  final class ABSTRACT extends yyTokenclass {
   ABSTRACT(Object o) {
      super(o);
      this.tokennr = 269;
   }
   ABSTRACT() {
      super();
      this.tokennr = 269;
   }
}
  final class JCLASS extends yyTokenclass {
   JCLASS(Object o) {
      super(o);
      this.tokennr = 270;
   }
   JCLASS() {
      super();
      this.tokennr = 270;
   }
}
  final class SEMICOLON extends yyTokenclass {
   SEMICOLON(Object o) {
      super(o);
      this.tokennr = 271;
   }
   SEMICOLON() {
      super();
      this.tokennr = 271;
   }
}
  final class EQUALS extends yyTokenclass {
   EQUALS(Object o) {
      super(o);
      this.tokennr = 272;
   }
   EQUALS() {
      super();
      this.tokennr = 272;
   }
}
  final class COMMA extends yyTokenclass {
   COMMA(Object o) {
      super(o);
      this.tokennr = 273;
   }
   COMMA() {
      super();
      this.tokennr = 273;
   }
}
  final class POINT extends yyTokenclass {
   POINT(Object o) {
      super(o);
      this.tokennr = 274;
   }
   POINT() {
      super();
      this.tokennr = 274;
   }
}
  final class UNARYOPERATOR extends yyTokenclass {
   UNARYOPERATOR(Object o) {
      super(o);
      this.tokennr = 275;
   }
   UNARYOPERATOR() {
      super();
      this.tokennr = 275;
   }
}
  final class BINARYOPERATOR extends yyTokenclass {
   BINARYOPERATOR(Object o) {
      super(o);
      this.tokennr = 276;
   }
   BINARYOPERATOR() {
      super();
      this.tokennr = 276;
   }
}
  final class PLUSMINUSOPERATOR extends yyTokenclass {
   PLUSMINUSOPERATOR(Object o) {
      super(o);
      this.tokennr = 277;
   }
   PLUSMINUSOPERATOR() {
      super();
      this.tokennr = 277;
   }
}
  final class LBRACE extends yyTokenclass {
   LBRACE(Object o) {
      super(o);
      this.tokennr = 278;
   }
   LBRACE() {
      super();
      this.tokennr = 278;
   }
}
  final class RBRACE extends yyTokenclass {
   RBRACE(Object o) {
      super(o);
      this.tokennr = 279;
   }
   RBRACE() {
      super();
      this.tokennr = 279;
   }
}
  final class LBRACKET extends yyTokenclass {
   LBRACKET(Object o) {
      super(o);
      this.tokennr = 280;
   }
   LBRACKET() {
      super();
      this.tokennr = 280;
   }
}
  final class RBRACKET extends yyTokenclass {
   RBRACKET(Object o) {
      super(o);
      this.tokennr = 281;
   }
   RBRACKET() {
      super();
      this.tokennr = 281;
   }
}
  final class JTHIS extends yyTokenclass {
   JTHIS(Object o) {
      super(o);
      this.tokennr = 282;
   }
   JTHIS() {
      super();
      this.tokennr = 282;
   }
}
  final class JSUPER extends yyTokenclass {
   JSUPER(Object o) {
      super(o);
      this.tokennr = 283;
   }
   JSUPER() {
      super();
      this.tokennr = 283;
   }
}
  final class JNEW extends yyTokenclass {
   JNEW(Object o) {
      super(o);
      this.tokennr = 284;
   }
   JNEW() {
      super();
      this.tokennr = 284;
   }
}
  final class NULL extends yyTokenclass {
   NULL(Object o) {
      super(o);
      this.tokennr = 285;
   }
   NULL() {
      super();
      this.tokennr = 285;
   }
}
  final class JRETURN extends yyTokenclass {
   JRETURN(Object o) {
      super(o);
      this.tokennr = 286;
   }
   JRETURN() {
      super();
      this.tokennr = 286;
   }
}
final class yyError extends yyTokenclass {
   yyError () {
      super();
      this.tokennr = 256;
   }
}
