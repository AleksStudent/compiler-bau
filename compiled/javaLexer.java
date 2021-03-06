//Start of usercode
import java.lang.Integer;
//End of usercode


class javaLexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	javaLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	javaLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private javaLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"40:8,41:2,1,40:2,1,40:18,41,15,39,40:2,11,18,42,2,3,9,7,13,8,14,10,38:10,40" +
",6,16,12,17,40:2,37:26,40:6,34,36,35,31,26,33,37,21,22,37:2,29,37,28,32,25," +
"37,27,23,20,24,37,30,37:3,4,19,5,40:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,97,
"0,1,2,1:10,3,1:2,4,5,6,7,8,9,1:6,10:5,11,1,10:10,11,12,13,14,15,16,17,18,1," +
"19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,10," +
"43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60")[0];

	private int yy_nxt[][] = unpackFromString(61,43,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,46,20,78,47,80,78:2,82,91,6" +
"5,78,84,51,78,67,96,86,78:2,21,50,53,2,55,-1:44,2,-1:39,2,-1:13,22,-1:42,23" +
",-1:42,24,-1:42,25,-1:48,26,-1:44,28,66,28:5,68,28:11,-1:42,21,-1:24,28:19," +
"-1:6,45:37,33,45:3,-1:19,27,-1:43,28:13,29,28:5,-1:24,28:10,31,28:8,-1:46,3" +
"4,-1:2,45:41,-1:20,28:12,30,28:6,-1:24,28:7,32,28:11,-1:24,28:3,35,28:15,-1" +
":6,49:41,-1:20,28:6,36,28:12,-1:24,28:6,37,28:12,-1:24,28:9,38,28:9,-1:24,2" +
"8:7,39,28:11,-1:24,28:6,40,28:12,-1:24,28:3,41,28:15,-1:24,28:15,42,28:3,-1" +
":24,28:8,43,28:10,-1:24,44,28:18,-1:24,28:4,70,28,48,28:12,-1:24,28:2,54,28" +
":16,-1:24,28:12,52,28,83,28:4,-1:24,28:4,56,28:14,-1:24,28:3,57,28:15,-1:24" +
",28:9,58,28:9,-1:24,28:6,59,28:12,-1:24,28:9,60,28:9,-1:24,28:3,56,28:15,-1" +
":24,28:3,61,28:15,-1:24,28:2,62,28:16,-1:24,28:7,63,28:11,-1:24,28:15,64,28" +
":3,-1:24,28:5,71,28:13,-1:24,90,28:3,79,28:14,-1:24,28:2,72,28:16,-1:24,28:" +
"9,69,28:9,-1:24,28:9,73,28:9,-1:24,28,81,28:17,-1:24,28:14,74,28:4,-1:24,28" +
":9,85,28:9,-1:24,75,28:18,-1:24,28:4,76,28:14,-1:24,28:14,77,28:4,-1:24,28:" +
"14,87,28:4,-1:24,28:6,92,28:12,-1:24,88,28:18,-1:24,28:7,89,28:11,-1:24,93," +
"28:18,-1:24,28:3,94,28:15,-1:24,28:16,95,28:2,-1:4");

	public yyTokenclass yylex ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

  return new EOF();
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{  }
					case -3:
						break;
					case 3:
						{ return new LBRACE(yytext()); }
					case -4:
						break;
					case 4:
						{ return new RBRACE(yytext()); }
					case -5:
						break;
					case 5:
						{ return new LBRACKET(yytext()); }
					case -6:
						break;
					case 6:
						{ return new RBRACKET(yytext()); }
					case -7:
						break;
					case 7:
						{ return new SEMICOLON(yytext()); }
					case -8:
						break;
					case 8:
						{ return new PLUSMINUSOPERATOR(yytext()); }
					case -9:
						break;
					case 9:
						{ return new PLUSMINUSOPERATOR(yytext()); }
					case -10:
						break;
					case 10:
						{ return new BINARYOPERATOR(yytext()); }
					case -11:
						break;
					case 11:
						{ return new BINARYOPERATOR(yytext()); }
					case -12:
						break;
					case 12:
						{ return new BINARYOPERATOR(yytext()); }
					case -13:
						break;
					case 13:
						{ return new EQUALS(yytext()); }
					case -14:
						break;
					case 14:
						{ return new COMMA(yytext()); }
					case -15:
						break;
					case 15:
						{ return new POINT(yytext()); }
					case -16:
						break;
					case 16:
						{ return new UNARYOPERATOR(yytext()); }
					case -17:
						break;
					case 17:
						{ return new BINARYOPERATOR(yytext()); }
					case -18:
						break;
					case 18:
						{ return new BINARYOPERATOR(yytext()); }
					case -19:
						break;
					case 19:
						{ throw new RuntimeException(yytext()); }
					case -20:
						break;
					case 20:
						{ return new IDENTIFIER(yytext()); }
					case -21:
						break;
					case 21:
						{ return new INT(yytext()); }
					case -22:
						break;
					case 22:
						{ return new BINARYOPERATOR(yytext()); }
					case -23:
						break;
					case 23:
						{ return new BINARYOPERATOR(yytext()); }
					case -24:
						break;
					case 24:
						{ return new BINARYOPERATOR(yytext()); }
					case -25:
						break;
					case 25:
						{ return new BINARYOPERATOR(yytext()); }
					case -26:
						break;
					case 26:
						{ return new BINARYOPERATOR(yytext()); }
					case -27:
						break;
					case 27:
						{ return new BINARYOPERATOR(yytext()); }
					case -28:
						break;
					case 28:
						{ return new IDENTIFIER(yytext()); }
					case -29:
						break;
					case 29:
						{ return new JIF(yytext()); }
					case -30:
						break;
					case 30:
						{ return new DO(yytext()); }
					case -31:
						break;
					case 31:
						{ return new JNEW(yytext()); }
					case -32:
						break;
					case 32:
						{ return new FOR(yytext()); }
					case -33:
						break;
					case 33:
						{ String temp=yytext();return new STRING(temp.substring( 1, temp.length() - 1 )); }
					case -34:
						break;
					case 34:
						{ return new JCHAR(yytext()); }
					case -35:
						break;
					case 35:
						{ return new JTHIS(yytext()); }
					case -36:
						break;
					case 36:
						{ return new BOOLEAN(yytext()); }
					case -37:
						break;
					case 37:
						{ return new ELSE(yytext()); }
					case -38:
						break;
					case 38:
						{ return new NULL(yytext()); }
					case -39:
						break;
					case 39:
						{ return new JSUPER(yytext()); }
					case -40:
						break;
					case 40:
						{ return new JWHILE(yytext()); }
					case -41:
						break;
					case 41:
						{ return new JCLASS(yytext()); }
					case -42:
						break;
					case 42:
						{ return new STATIC(yytext()); }
					case -43:
						break;
					case 43:
						{ return new JRETURN(yytext()); }
					case -44:
						break;
					case 44:
						{ return new ABSTRACT(yytext()); }
					case -45:
						break;
					case 46:
						{ throw new RuntimeException(yytext()); }
					case -46:
						break;
					case 47:
						{ return new IDENTIFIER(yytext()); }
					case -47:
						break;
					case 48:
						{ return new IDENTIFIER(yytext()); }
					case -48:
						break;
					case 50:
						{ throw new RuntimeException(yytext()); }
					case -49:
						break;
					case 51:
						{ return new IDENTIFIER(yytext()); }
					case -50:
						break;
					case 52:
						{ return new IDENTIFIER(yytext()); }
					case -51:
						break;
					case 53:
						{ throw new RuntimeException(yytext()); }
					case -52:
						break;
					case 54:
						{ return new IDENTIFIER(yytext()); }
					case -53:
						break;
					case 55:
						{ throw new RuntimeException(yytext()); }
					case -54:
						break;
					case 56:
						{ return new IDENTIFIER(yytext()); }
					case -55:
						break;
					case 57:
						{ return new IDENTIFIER(yytext()); }
					case -56:
						break;
					case 58:
						{ return new IDENTIFIER(yytext()); }
					case -57:
						break;
					case 59:
						{ return new IDENTIFIER(yytext()); }
					case -58:
						break;
					case 60:
						{ return new IDENTIFIER(yytext()); }
					case -59:
						break;
					case 61:
						{ return new IDENTIFIER(yytext()); }
					case -60:
						break;
					case 62:
						{ return new IDENTIFIER(yytext()); }
					case -61:
						break;
					case 63:
						{ return new IDENTIFIER(yytext()); }
					case -62:
						break;
					case 64:
						{ return new IDENTIFIER(yytext()); }
					case -63:
						break;
					case 65:
						{ return new IDENTIFIER(yytext()); }
					case -64:
						break;
					case 66:
						{ return new IDENTIFIER(yytext()); }
					case -65:
						break;
					case 67:
						{ return new IDENTIFIER(yytext()); }
					case -66:
						break;
					case 68:
						{ return new IDENTIFIER(yytext()); }
					case -67:
						break;
					case 69:
						{ return new IDENTIFIER(yytext()); }
					case -68:
						break;
					case 70:
						{ return new IDENTIFIER(yytext()); }
					case -69:
						break;
					case 71:
						{ return new IDENTIFIER(yytext()); }
					case -70:
						break;
					case 72:
						{ return new IDENTIFIER(yytext()); }
					case -71:
						break;
					case 73:
						{ return new IDENTIFIER(yytext()); }
					case -72:
						break;
					case 74:
						{ return new IDENTIFIER(yytext()); }
					case -73:
						break;
					case 75:
						{ return new IDENTIFIER(yytext()); }
					case -74:
						break;
					case 76:
						{ return new IDENTIFIER(yytext()); }
					case -75:
						break;
					case 77:
						{ return new IDENTIFIER(yytext()); }
					case -76:
						break;
					case 78:
						{ return new IDENTIFIER(yytext()); }
					case -77:
						break;
					case 79:
						{ return new IDENTIFIER(yytext()); }
					case -78:
						break;
					case 80:
						{ return new IDENTIFIER(yytext()); }
					case -79:
						break;
					case 81:
						{ return new IDENTIFIER(yytext()); }
					case -80:
						break;
					case 82:
						{ return new IDENTIFIER(yytext()); }
					case -81:
						break;
					case 83:
						{ return new IDENTIFIER(yytext()); }
					case -82:
						break;
					case 84:
						{ return new IDENTIFIER(yytext()); }
					case -83:
						break;
					case 85:
						{ return new IDENTIFIER(yytext()); }
					case -84:
						break;
					case 86:
						{ return new IDENTIFIER(yytext()); }
					case -85:
						break;
					case 87:
						{ return new IDENTIFIER(yytext()); }
					case -86:
						break;
					case 88:
						{ return new IDENTIFIER(yytext()); }
					case -87:
						break;
					case 89:
						{ return new IDENTIFIER(yytext()); }
					case -88:
						break;
					case 90:
						{ return new IDENTIFIER(yytext()); }
					case -89:
						break;
					case 91:
						{ return new IDENTIFIER(yytext()); }
					case -90:
						break;
					case 92:
						{ return new IDENTIFIER(yytext()); }
					case -91:
						break;
					case 93:
						{ return new IDENTIFIER(yytext()); }
					case -92:
						break;
					case 94:
						{ return new IDENTIFIER(yytext()); }
					case -93:
						break;
					case 95:
						{ return new IDENTIFIER(yytext()); }
					case -94:
						break;
					case 96:
						{ return new IDENTIFIER(yytext()); }
					case -95:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
class javaScanner extends javaLexer implements javaParser.yyInput {
	public javaScanner (java.io.Reader reader) {
		super (reader);
	}
	//public int token() is not longer necessary

	public yyTokenclass advance() throws java.io.IOException {
		yyTokenclass ret = yylex();
		return ret;
	}

	//public Object value () is not longer necessary

}
