// created by jay 0.8 (c) 1998 Axel.Schreiner@informatik.uni-osnabrueck.de

					// line 1 "javaParser.jay"

import java.util.Vector;
class javaParser {
					// line 8 "-"
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
					// line 73 "javaParser.jay"
  { Program finalProgram = new Program(((Class)yyVals[0+yyTop])); finalProgram.typeCheck(); BytecodeGenerator.codeGen(finalProgram); yyVal=finalProgram; }
  break;
case 2:
					// line 74 "javaParser.jay"
  { Program finalProgram = new Program(((Class)yyVals[0+yyTop])); finalProgram.typeCheck(); BytecodeGenerator.codeGen(finalProgram); yyVal=finalProgram; }
  break;
case 3:
					// line 76 "javaParser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-4+yyTop])), ((Vector)yyVals[-2+yyTop]), ((Vector)yyVals[-1+yyTop])); System.out.println(new Class(new Type(((String)yyVals[-4+yyTop])), ((Vector)yyVals[-2+yyTop]), ((Vector)yyVals[-1+yyTop]))); }
  break;
case 4:
					// line 77 "javaParser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-3+yyTop])), new Vector(), ((Vector)yyVals[-1+yyTop])); }
  break;
case 5:
					// line 78 "javaParser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-3+yyTop])), ((Vector)yyVals[-1+yyTop]), new Vector()); }
  break;
case 6:
					// line 80 "javaParser.jay"
  { yyVal = new Class(new Type(((String)yyVals[-2+yyTop])), new Vector(), new Vector()); }
  break;
case 7:
					// line 82 "javaParser.jay"
  { Vector fs = new Vector(); fs.addElement(((Field)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 8:
					// line 83 "javaParser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Field)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 9:
					// line 85 "javaParser.jay"
  { yyVal = new Field(((String)yyVals[-1+yyTop]), ((Type)yyVals[-2+yyTop])); }
  break;
case 10:
					// line 87 "javaParser.jay"
  { yyVal = new Type(((String)yyVals[0+yyTop])); }
  break;
case 11:
					// line 89 "javaParser.jay"
  { Vector fs = new Vector(); fs.addElement(((Method)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 12:
					// line 90 "javaParser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Method)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 13:
					// line 92 "javaParser.jay"
  { yyVal = new Method(((String)yyVals[-4+yyTop]), ((Type)yyVals[-5+yyTop]), ((Vector)yyVals[-2+yyTop]), ((Block)yyVals[0+yyTop])); }
  break;
case 14:
					// line 93 "javaParser.jay"
  { yyVal = new Method(((String)yyVals[-3+yyTop]), ((Type)yyVals[-4+yyTop]), new Vector(), ((Block)yyVals[0+yyTop])); }
  break;
case 15:
					// line 95 "javaParser.jay"
  { Vector fs = new Vector(); fs.addElement(((Parameter)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 16:
					// line 96 "javaParser.jay"
  { Vector fs = ((Vector)yyVals[-2+yyTop]) ; fs.addElement(((Parameter)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 17:
					// line 98 "javaParser.jay"
  { yyVal = new Parameter(((String)yyVals[0+yyTop]), ((Type)yyVals[-1+yyTop])); }
  break;
case 18:
					// line 100 "javaParser.jay"
  { yyVal = new Block(new Vector()); }
  break;
case 19:
					// line 101 "javaParser.jay"
  { yyVal = new Block(((Vector)yyVals[-1+yyTop])); }
  break;
case 20:
					// line 103 "javaParser.jay"
  { Vector fs = new Vector(); fs.addElement(((Stmt)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 21:
					// line 104 "javaParser.jay"
  { Vector fs = ((Vector)yyVals[-1+yyTop]) ; fs.addElement(((Stmt)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 22:
					// line 106 "javaParser.jay"
  {}
  break;
case 23:
					// line 107 "javaParser.jay"
  {}
  break;
case 24:
					// line 108 "javaParser.jay"
  {}
  break;
case 25:
					// line 109 "javaParser.jay"
  {}
  break;
case 26:
					// line 110 "javaParser.jay"
  {}
  break;
case 27:
					// line 111 "javaParser.jay"
  {}
  break;
case 28:
					// line 112 "javaParser.jay"
  {}
  break;
case 29:
					// line 114 "javaParser.jay"
  { yyVal = new If(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop]), null); }
  break;
case 30:
					// line 116 "javaParser.jay"
  { yyVal = new If(((Expr)yyVals[-4+yyTop]), ((Stmt)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 31:
					// line 118 "javaParser.jay"
  { yyVal = new While(((Expr)yyVals[-2+yyTop]), ((Stmt)yyVals[0+yyTop])); }
  break;
case 32:
					// line 120 "javaParser.jay"
  { yyVal = new Return(((Expr)yyVals[0+yyTop])); }
  break;
case 33:
					// line 122 "javaParser.jay"
  { yyVal = new StmtExprExpr(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 34:
					// line 124 "javaParser.jay"
  { yyVal = new StmtExprStmt(((StmtExpr)yyVals[0+yyTop])); }
  break;
case 35:
					// line 126 "javaParser.jay"
  { yyVal = new LocalVarDecl(((Type)yyVals[-1+yyTop]), ((String)yyVals[0+yyTop])); }
  break;
case 36:
					// line 128 "javaParser.jay"
  {}
  break;
case 37:
					// line 129 "javaParser.jay"
  {}
  break;
case 38:
					// line 130 "javaParser.jay"
  {}
  break;
case 39:
					// line 131 "javaParser.jay"
  {}
  break;
case 40:
					// line 132 "javaParser.jay"
  {}
  break;
case 41:
					// line 133 "javaParser.jay"
  {}
  break;
case 42:
					// line 134 "javaParser.jay"
  {}
  break;
case 43:
					// line 135 "javaParser.jay"
  {}
  break;
case 44:
					// line 136 "javaParser.jay"
  {}
  break;
case 45:
					// line 137 "javaParser.jay"
  {}
  break;
case 46:
					// line 138 "javaParser.jay"
  {}
  break;
case 47:
					// line 139 "javaParser.jay"
  {}
  break;
case 48:
					// line 140 "javaParser.jay"
  {yyVal = ((Expr)yyVals[-1+yyTop]);}
  break;
case 49:
					// line 142 "javaParser.jay"
  {}
  break;
case 50:
					// line 143 "javaParser.jay"
  {}
  break;
case 51:
					// line 144 "javaParser.jay"
  {}
  break;
case 52:
					// line 145 "javaParser.jay"
  {}
  break;
case 53:
					// line 147 "javaParser.jay"
  { yyVal = new This(); }
  break;
case 54:
					// line 149 "javaParser.jay"
  { yyVal = new Super(); }
  break;
case 55:
					// line 151 "javaParser.jay"
  { yyVal = new LocalOrFieldVar(((String)yyVals[0+yyTop])); }
  break;
case 56:
					// line 153 "javaParser.jay"
  { yyVal = new Integer(((String)yyVals[0+yyTop])); }
  break;
case 57:
					// line 155 "javaParser.jay"
  { yyVal = new Binary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 58:
					// line 156 "javaParser.jay"
  { yyVal = new Binary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 59:
					// line 158 "javaParser.jay"
  { yyVal = new Unary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 60:
					// line 159 "javaParser.jay"
  { yyVal = new Unary(((String)yyVals[-1+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 61:
					// line 161 "javaParser.jay"
  { yyVal = new Bool(((String)yyVals[0+yyTop])); }
  break;
case 62:
					// line 163 "javaParser.jay"
  { yyVal = new InstVar(((Expr)yyVals[-2+yyTop]), ((String)yyVals[0+yyTop])); }
  break;
case 63:
					// line 165 "javaParser.jay"
  { yyVal = new JString(((String)yyVals[0+yyTop])); }
  break;
case 64:
					// line 167 "javaParser.jay"
  { yyVal = new Jnull(); }
  break;
case 65:
					// line 169 "javaParser.jay"
  { yyVal = new Char(((String)yyVals[0+yyTop])); }
  break;
case 66:
					// line 171 "javaParser.jay"
  { yyVal = new Assign(((String)yyVals[-2+yyTop]), ((Expr)yyVals[0+yyTop])); }
  break;
case 67:
					// line 173 "javaParser.jay"
  { yyVal = new New(((Type)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 68:
					// line 174 "javaParser.jay"
  { yyVal = new New(((Type)yyVals[-2+yyTop]), new Vector()); }
  break;
case 69:
					// line 176 "javaParser.jay"
  { yyVal = new MethodCall(((Expr)yyVals[-5+yyTop]), ((String)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 70:
					// line 177 "javaParser.jay"
  { yyVal = new MethodCall(new This(), ((String)yyVals[-3+yyTop]), ((Vector)yyVals[-1+yyTop])); }
  break;
case 71:
					// line 179 "javaParser.jay"
  { yyVal = new MethodCall(((Expr)yyVals[-4+yyTop]), ((String)yyVals[-2+yyTop]), new Vector()); }
  break;
case 72:
					// line 180 "javaParser.jay"
  { yyVal = new MethodCall(new This(), ((String)yyVals[-2+yyTop]), new Vector()); }
  break;
case 73:
					// line 182 "javaParser.jay"
  { Vector fs = new Vector(); fs.addElement(((Expr)yyVals[0+yyTop])); yyVal = fs; }
  break;
case 74:
					// line 183 "javaParser.jay"
  { Vector fs = ((Vector)yyVals[-2+yyTop]) ; fs.addElement(((Expr)yyVals[0+yyTop])); yyVal = fs; }
  break;
					// line 564 "-"
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
          0,    0,    0,    0,    0,   33,    0,    0,    0,    0,
         35,    0,    0,    0,   28,   26,   27,   19,   21,    0,
          0,    0,   72,    0,    0,   48,    0,    0,    0,    0,
          0,    0,    0,   70,   68,    0,    0,   31,    0,    0,
         67,   71,    0,    0,   69,   30,
    };
  } /* End of class YyDefRedClass */

  protected static final class YyDgotoClass {

    public static final short yyDgoto [] = {             2,
          3,    4,    9,   10,   26,   50,   51,   52,   53,   54,
         55,   56,   57,   58,   59,   60,   61,   62,   63,   64,
         65,   66,   67,   68,   69,   70,   71,   72,   86,   74,
         75,   76,   12,   77,   13,  105,   28,
    };
  } /* End of class YyDgotoClass */

  protected static final class YySindexClass {

    public static final short yySindex [] = {         -252,
       -229,    0,    0,    0, -232, -260,    0,    0,    0,    0,
       -217, -258, -256, -225,    0,    0, -253,    0,    0, -191,
          0, -259,    0, -201, -199,    0, -188, -222, -176,    0,
          0, -174, -199, -183, -181,    0,    0,    0,    0, -242,
          4,    4,    4,    0,    0,    0, -174,    0,    4, -169,
        -48,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0, -168,    0,    0,    0, -159,    0,
       -145,    0,    0,    0,    0,    0, -147,    0,    0,    4,
          4,    4,  -65, -242,  -48,    0,  -48, -152, -146,  -48,
          0, -126,    4,    4,    0,    0,    0,    0,    0, -123,
        -94,  -48,    0,  -48, -213,    0,  -42, -117,  -48,  -48,
        -89,  -89,    4,    0,    0, -193,  -19,    0, -107,  -48,
          0,    0, -150,  -89,    0,    0,
    };
  } /* End of class YySindexClass */

  protected static final class YyRindexClass {

    public static final short yyRindex [] = {            0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0, -243,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0,    0,  -68,    0,    0,    0,    0,    0,    0,    0,
          0,    0,    0, -215, -244,    0, -208,    0,    0, -101,
          0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
          0, -179,    0, -121,    0,    0,    0, -204, -158, -129,
          0,    0,    0,    0,    0,    0,    0,    0, -118, -100,
          0,    0,    0,    0,    0,    0,
    };
  } /* End of class YyRindexClass */

  protected static final class YyGindexClass {

    public static final short yyGindex [] = {            0,
          0,    0,    1,  158,  148,   -1,  -40,    0,    0,    0,
          0,    0,    0,    0,    0,    0,    0,    0,    0,  -73,
          0,    0,    0,    0,    0,   11,    0,    0,  -29,    0,
          0,    0,    0,    0,  171,  -92,    0,
    };
  } /* End of class YyGindexClass */

protected static class yyTable0 {
  protected static final short yyTable0 [] = {            73,
         85,   87,   88,   99,   11,    7,    7,    7,   90,    7,
         11,   20,    7,   19,  116,   20,    1,   19,   25,    8,
         27,   15,   10,   18,  123,   59,   23,   59,   82,   55,
         27,   55,   55,   59,   83,   30,    5,  118,  119,  100,
        101,  102,  104,   79,   21,   89,    6,   73,   14,   32,
        126,   22,  109,  110,   55,   33,   55,   55,  113,   55,
         55,   60,   55,   60,  114,   62,  104,   62,   62,   60,
         62,   62,  120,   62,   24,   22,  104,   31,  113,   29,
         34,   73,   73,   35,  121,   36,   37,   38,   39,   40,
         66,    7,   66,   80,   73,   81,   91,   41,   66,   42,
         43,   95,   29,   44,   45,   46,   47,   48,   49,   34,
         96,   57,   35,   57,   36,   37,   38,   39,   40,   57,
         92,  113,   93,   94,   97,  106,   41,  125,   42,   43,
        107,   29,   98,   45,   46,   47,   48,   49,   29,  108,
         58,   29,   58,   29,   29,   29,   29,   29,   58,   92,
         73,   93,   94,  124,  111,   29,   73,   29,   29,  117,
         29,   29,   29,   29,   29,   29,   29,   34,   32,   16,
         35,   74,   36,   37,   38,   39,   40,   74,   92,   78,
         93,   94,   17,  112,   41,    0,   42,   43,    0,   29,
          0,   45,   46,   47,   48,   49,   36,   37,   38,   39,
         84,   34,    0,    0,   33,    0,   33,   33,   41,    0,
         42,   43,  103,    0,    0,   45,   46,   47,   48,   36,
         37,   38,   39,   84,   92,    0,   93,   94,    0,    0,
          0,   41,    0,   42,   43,  115,    0,    0,   45,   46,
         47,   48,   36,   37,   38,   39,   84,    0,    0,    0,
          0,    0,    0,    0,   41,    0,   42,   43,  122,    0,
          0,   45,   46,   47,   48,   36,   37,   38,   39,   84,
          0,    0,    0,    0,    0,    0,    0,   41,    0,   42,
         43,    0,    0,    0,   45,   46,   47,   48,
  };
}
  protected static final class YyTableClass {

  static short[] yyTable = new short[289];
  protected static void yyTableInit () {
     int numyycheck;
     int yyTableerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyTableerun < 289) {
          yyTable[yyTableerun] = yyTable0.yyTable0[numyycheck];
          yyTableerun++;
        }
     }
}
  } /* End of class YyTableClass */

protected static class yyCheck0 {
  protected static final short yyCheck0 [] = {            29,
         41,   42,   43,   77,    6,  266,  266,  266,   49,  266,
         12,   13,  266,   13,  107,   17,  269,   17,  278,  280,
         22,  280,  266,  280,  117,  270,  280,  272,  271,  273,
         32,  275,  276,  278,  277,   25,  266,  111,  112,   80,
         81,   82,   83,   33,  270,   47,  279,   77,  266,  272,
        124,  277,   93,   94,  270,  278,  272,  273,  272,  275,
        276,  270,  278,  272,  278,  270,  107,  272,  273,  278,
        275,  276,  113,  278,  266,  277,  117,  266,  272,  279,
        257,  111,  112,  260,  278,  262,  263,  264,  265,  266,
        270,  266,  272,  277,  124,  277,  266,  274,  278,  276,
        277,  270,  279,  280,  281,  282,  283,  284,  285,  257,
        270,  270,  260,  272,  262,  263,  264,  265,  266,  278,
        273,  272,  275,  276,  270,  278,  274,  278,  276,  277,
        277,  279,  280,  281,  282,  283,  284,  285,  257,  266,
        270,  260,  272,  262,  263,  264,  265,  266,  278,  273,
        272,  275,  276,  261,  278,  274,  278,  276,  277,  277,
        279,  280,  281,  282,  283,  284,  285,  257,  270,   12,
        260,  272,  262,  263,  264,  265,  266,  278,  273,   32,
        275,  276,   12,  278,  274,   -1,  276,  277,   -1,  279,
         -1,  281,  282,  283,  284,  285,  262,  263,  264,  265,
        266,  270,   -1,   -1,  273,   -1,  275,  276,  274,   -1,
        276,  277,  278,   -1,   -1,  281,  282,  283,  284,  262,
        263,  264,  265,  266,  273,   -1,  275,  276,   -1,   -1,
         -1,  274,   -1,  276,  277,  278,   -1,   -1,  281,  282,
        283,  284,  262,  263,  264,  265,  266,   -1,   -1,   -1,
         -1,   -1,   -1,   -1,  274,   -1,  276,  277,  278,   -1,
         -1,  281,  282,  283,  284,  262,  263,  264,  265,  266,
         -1,   -1,   -1,   -1,   -1,   -1,   -1,  274,   -1,  276,
        277,   -1,   -1,   -1,  281,  282,  283,  284,
  };
}
  protected static final class YyCheckClass {

  static short[] yyCheck = new short[289];
  protected static void yyCheckInit () {
     int numyycheck;
     int yyCheckerun = 0;
     for (numyycheck = 0; numyycheck <= 1000; numyycheck++) {
        if (yyCheckerun < 289) {
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
//t    "type : IDENTIFIER",
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
    "INT","STRING","JCHAR","BOOLEAN","IDENTIFIER","STATIC","ABSTRACT",
    "JCLASS","SEMICOLON","EQUALS","COMMA","POINT","UNARYOPERATOR",
    "BINARYOPERATOR","PLUSMINUSOPERATOR","LBRACE","RBRACE","LBRACKET",
    "RBRACKET","JTHIS","JSUPER","JNEW","NULL","JRETURN",
    };
  } /* End of class YyNameClass */


					// line 186 "javaParser.jay"

      }
      					// line 931 "-"
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
  final class INT extends yyTokenclass {
   INT(Object o) {
      super(o);
      this.tokennr = 262;
   }
   INT() {
      super();
      this.tokennr = 262;
   }
}
  final class STRING extends yyTokenclass {
   STRING(Object o) {
      super(o);
      this.tokennr = 263;
   }
   STRING() {
      super();
      this.tokennr = 263;
   }
}
  final class JCHAR extends yyTokenclass {
   JCHAR(Object o) {
      super(o);
      this.tokennr = 264;
   }
   JCHAR() {
      super();
      this.tokennr = 264;
   }
}
  final class BOOLEAN extends yyTokenclass {
   BOOLEAN(Object o) {
      super(o);
      this.tokennr = 265;
   }
   BOOLEAN() {
      super();
      this.tokennr = 265;
   }
}
  final class IDENTIFIER extends yyTokenclass {
   IDENTIFIER(Object o) {
      super(o);
      this.tokennr = 266;
   }
   IDENTIFIER() {
      super();
      this.tokennr = 266;
   }
}
  final class STATIC extends yyTokenclass {
   STATIC(Object o) {
      super(o);
      this.tokennr = 267;
   }
   STATIC() {
      super();
      this.tokennr = 267;
   }
}
  final class ABSTRACT extends yyTokenclass {
   ABSTRACT(Object o) {
      super(o);
      this.tokennr = 268;
   }
   ABSTRACT() {
      super();
      this.tokennr = 268;
   }
}
  final class JCLASS extends yyTokenclass {
   JCLASS(Object o) {
      super(o);
      this.tokennr = 269;
   }
   JCLASS() {
      super();
      this.tokennr = 269;
   }
}
  final class SEMICOLON extends yyTokenclass {
   SEMICOLON(Object o) {
      super(o);
      this.tokennr = 270;
   }
   SEMICOLON() {
      super();
      this.tokennr = 270;
   }
}
  final class EQUALS extends yyTokenclass {
   EQUALS(Object o) {
      super(o);
      this.tokennr = 271;
   }
   EQUALS() {
      super();
      this.tokennr = 271;
   }
}
  final class COMMA extends yyTokenclass {
   COMMA(Object o) {
      super(o);
      this.tokennr = 272;
   }
   COMMA() {
      super();
      this.tokennr = 272;
   }
}
  final class POINT extends yyTokenclass {
   POINT(Object o) {
      super(o);
      this.tokennr = 273;
   }
   POINT() {
      super();
      this.tokennr = 273;
   }
}
  final class UNARYOPERATOR extends yyTokenclass {
   UNARYOPERATOR(Object o) {
      super(o);
      this.tokennr = 274;
   }
   UNARYOPERATOR() {
      super();
      this.tokennr = 274;
   }
}
  final class BINARYOPERATOR extends yyTokenclass {
   BINARYOPERATOR(Object o) {
      super(o);
      this.tokennr = 275;
   }
   BINARYOPERATOR() {
      super();
      this.tokennr = 275;
   }
}
  final class PLUSMINUSOPERATOR extends yyTokenclass {
   PLUSMINUSOPERATOR(Object o) {
      super(o);
      this.tokennr = 276;
   }
   PLUSMINUSOPERATOR() {
      super();
      this.tokennr = 276;
   }
}
  final class LBRACE extends yyTokenclass {
   LBRACE(Object o) {
      super(o);
      this.tokennr = 277;
   }
   LBRACE() {
      super();
      this.tokennr = 277;
   }
}
  final class RBRACE extends yyTokenclass {
   RBRACE(Object o) {
      super(o);
      this.tokennr = 278;
   }
   RBRACE() {
      super();
      this.tokennr = 278;
   }
}
  final class LBRACKET extends yyTokenclass {
   LBRACKET(Object o) {
      super(o);
      this.tokennr = 279;
   }
   LBRACKET() {
      super();
      this.tokennr = 279;
   }
}
  final class RBRACKET extends yyTokenclass {
   RBRACKET(Object o) {
      super(o);
      this.tokennr = 280;
   }
   RBRACKET() {
      super();
      this.tokennr = 280;
   }
}
  final class JTHIS extends yyTokenclass {
   JTHIS(Object o) {
      super(o);
      this.tokennr = 281;
   }
   JTHIS() {
      super();
      this.tokennr = 281;
   }
}
  final class JSUPER extends yyTokenclass {
   JSUPER(Object o) {
      super(o);
      this.tokennr = 282;
   }
   JSUPER() {
      super();
      this.tokennr = 282;
   }
}
  final class JNEW extends yyTokenclass {
   JNEW(Object o) {
      super(o);
      this.tokennr = 283;
   }
   JNEW() {
      super();
      this.tokennr = 283;
   }
}
  final class NULL extends yyTokenclass {
   NULL(Object o) {
      super(o);
      this.tokennr = 284;
   }
   NULL() {
      super();
      this.tokennr = 284;
   }
}
  final class JRETURN extends yyTokenclass {
   JRETURN(Object o) {
      super(o);
      this.tokennr = 285;
   }
   JRETURN() {
      super();
      this.tokennr = 285;
   }
}
final class yyError extends yyTokenclass {
   yyError () {
      super();
      this.tokennr = 256;
   }
}
