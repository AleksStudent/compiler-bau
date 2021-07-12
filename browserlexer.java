//Start of usercode
import java.lang.Integer;
//End of usercode


class browserlexer {
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

	browserlexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	browserlexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private browserlexer () {
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
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NOT_ACCEPT,
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
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"40:8,41:2,1,40:2,1,40:18,41,14,39,40:4,42,2,3,9,7,12,8,13,10,38:10,40,6,15," +
"11,16,40:2,37:18,34,37:7,40:6,31,33,32,28,23,30,35,18,19,37:2,26,37,25,29,2" +
"2,37,24,20,17,21,36,27,37:3,4,40,5,40:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,128,
"0,1,2,1:6,3,1:2,4,1:5,5,6,7,8,1,9:6,10,11,1,12,9:12,10,13,1,14,11,15,16,17," +
"18,12,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41," +
"42,43,44,45,46,47,48,49,50,51,52,53,54,9,55,56,57,58,59,60,61,62,63,64,65,6" +
"6,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90")[0];

	private int yy_nxt[][] = unpackFromString(91,43,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,91,46,93,91,110,95,112,71,91,9" +
"7,50,91,74,126,99,121,114,91:3,19,20,47,2,72,-1:44,2,-1:39,2,-1:39,21,-1:15" +
",22,-1:48,23,73,23:5,75,23:14,-1:17,45,-1:24,19,-1:6,49:41,-1:13,54,-1:24,2" +
"1,-1:21,23:22,-1:42,29,-1:6,49:37,30,49:3,-1:38,32,-1:21,23:8,48,23:4,24,23" +
":8,-1:21,26,23:21,-1:21,23:12,25,23:9,-1:21,23:10,27,23:11,-1:46,31,-1:17,2" +
"3:7,28,23:14,-1:21,23:3,33,23:18,-1:21,23:6,34,23:15,-1:21,23:6,35,23:15,-1" +
":21,23:9,36,23:12,-1:21,23:7,37,23:14,-1:21,23:6,38,23:15,-1:21,23:6,39,23:" +
"15,-1:21,23:3,40,23:18,-1:21,23:15,41,23:6,-1:21,23:15,42,23:6,-1:21,23:8,4" +
"3,23:13,-1:21,23:18,26,23:3,-1:21,23:6,42,23:15,-1:21,23:8,26,23:13,-1:21,4" +
"4,23:21,-1:21,23:11,42,23:10,-1:21,23:4,77,23,51,23:15,-1:6,52:41,-1:17,23:" +
"2,55,23:19,-1:21,23:9,96,23:2,53,23,98,23:7,-1:21,23:4,56,23:17,-1:21,23:3," +
"57,23:18,-1:21,23:9,58,23:12,-1:21,23:6,59,23:15,-1:21,23:9,60,23:12,-1:21," +
"23:14,48,23:7,-1:21,23:3,61,23:18,-1:21,23:3,62,23:18,-1:21,23:2,63,23:19,-" +
"1:21,23:2,64,23:19,-1:21,23:7,65,23:14,-1:21,23:8,66,23:13,-1:21,67,23:21,-" +
"1:21,23:14,68,23:7,-1:21,23:15,69,23:6,-1:21,23:6,70,23:15,-1:21,23:5,78,23" +
":16,-1:21,109,23:3,92,23:17,-1:21,23:2,79,23:19,-1:21,23:9,76,23:12,-1:21,2" +
"3:12,80,23:9,-1:21,23,94,23:20,-1:21,23:9,81,23:12,-1:21,23:9,100,23:12,-1:" +
"21,23:14,82,23:7,-1:21,83,23:21,-1:21,23:9,84,23:12,-1:21,23:4,85,23:17,-1:" +
"21,23:2,86,23:19,-1:21,23:14,87,23:7,-1:21,23:6,88,23:15,-1:21,23:14,89,23:" +
"7,-1:21,90,23:21,-1:21,23:14,101,23:7,-1:21,23:4,111,23:2,120,23:14,-1:21,2" +
"3:16,102,23:5,-1:21,23:6,113,23:15,-1:21,103,23:21,-1:21,115,23:21,-1:21,23" +
":7,104,23:14,-1:21,23:19,105,23:2,-1:21,23:9,106,23:12,-1:21,23:7,107,23:14" +
",-1:21,23:15,108,23:6,-1:21,23:2,116,23:9,127,23:9,-1:21,23:12,122,23:9,-1:" +
"21,23:12,117,23:9,-1:21,118,23:21,-1:21,23:6,119,23:15,-1:21,23:3,123,23:18" +
",-1:21,23:16,125,23:5,-1:21,124,23:21,-1:4");

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
						{ return new OPERATOR(yytext()); }
					case -9:
						break;
					case 9:
						{ return new OPERATOR(yytext()); }
					case -10:
						break;
					case 10:
						{ return new OPERATOR(yytext()); }
					case -11:
						break;
					case 11:
						{ return new OPERATOR(yytext()); }
					case -12:
						break;
					case 12:
						{ return new EQUALS(yytext()); }
					case -13:
						break;
					case 13:
						{ return new COMMA(yytext()); }
					case -14:
						break;
					case 14:
						{ return new POINT(yytext()); }
					case -15:
						break;
					case 15:
						{ return new UNARYOPERATOR(yytext()); }
					case -16:
						break;
					case 16:
						{ return new BINARYOPERATOR(yytext()); }
					case -17:
						break;
					case 17:
						{ return new BINARYOPERATOR(yytext()); }
					case -18:
						break;
					case 18:
						{ return new IDENTIFIER(yytext()); }
					case -19:
						break;
					case 19:
						{ return new INT(yytext()); }
					case -20:
						break;
					case 20:
						{ System.out.println("ERROR: "+yytext()); }
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
						{ return new IDENTIFIER(yytext()); }
					case -24:
						break;
					case 24:
						{ return new JIF(yytext()); }
					case -25:
						break;
					case 25:
						{ return new DO(yytext()); }
					case -26:
						break;
					case 26:
						{ return new JTYPE(yytext()); }
					case -27:
						break;
					case 27:
						{ return new JNEW(yytext()); }
					case -28:
						break;
					case 28:
						{ return new FOR(yytext()); }
					case -29:
						break;
					case 29:
						{ return new FLOAT(yytext()); }
					case -30:
						break;
					case 30:
						{ return new STRING(yytext()); }
					case -31:
						break;
					case 31:
						{ return new JCHAR(yytext()); }
					case -32:
						break;
					case 32:
						{ return new FLOAT(yytext()); }
					case -33:
						break;
					case 33:
						{ return new JTHIS(yytext()); }
					case -34:
						break;
					case 34:
						{ return new TRUE(yytext()); }
					case -35:
						break;
					case 35:
						{ return new ELSE(yytext()); }
					case -36:
						break;
					case 36:
						{ return new NULL(yytext()); }
					case -37:
						break;
					case 37:
						{ return new JSUPER(yytext()); }
					case -38:
						break;
					case 38:
						{ return new JWHILE(yytext()); }
					case -39:
						break;
					case 39:
						{ return new BOOLEAN(yytext()); }
					case -40:
						break;
					case 40:
						{ return new JCLASS(yytext()); }
					case -41:
						break;
					case 41:
						{ return new STATIC(yytext()); }
					case -42:
						break;
					case 42:
						{ return new ACCESSRIGHT(yytext()); }
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
						{ return new IDENTIFIER(yytext()); }
					case -46:
						break;
					case 47:
						{ System.out.println("ERROR: "+yytext()); }
					case -47:
						break;
					case 48:
						{ return new IDENTIFIER(yytext()); }
					case -48:
						break;
					case 50:
						{ return new IDENTIFIER(yytext()); }
					case -49:
						break;
					case 51:
						{ return new IDENTIFIER(yytext()); }
					case -50:
						break;
					case 53:
						{ return new IDENTIFIER(yytext()); }
					case -51:
						break;
					case 55:
						{ return new IDENTIFIER(yytext()); }
					case -52:
						break;
					case 56:
						{ return new IDENTIFIER(yytext()); }
					case -53:
						break;
					case 57:
						{ return new IDENTIFIER(yytext()); }
					case -54:
						break;
					case 58:
						{ return new IDENTIFIER(yytext()); }
					case -55:
						break;
					case 59:
						{ return new IDENTIFIER(yytext()); }
					case -56:
						break;
					case 60:
						{ return new IDENTIFIER(yytext()); }
					case -57:
						break;
					case 61:
						{ return new IDENTIFIER(yytext()); }
					case -58:
						break;
					case 62:
						{ return new IDENTIFIER(yytext()); }
					case -59:
						break;
					case 63:
						{ return new IDENTIFIER(yytext()); }
					case -60:
						break;
					case 64:
						{ return new IDENTIFIER(yytext()); }
					case -61:
						break;
					case 65:
						{ return new IDENTIFIER(yytext()); }
					case -62:
						break;
					case 66:
						{ return new IDENTIFIER(yytext()); }
					case -63:
						break;
					case 67:
						{ return new IDENTIFIER(yytext()); }
					case -64:
						break;
					case 68:
						{ return new IDENTIFIER(yytext()); }
					case -65:
						break;
					case 69:
						{ return new IDENTIFIER(yytext()); }
					case -66:
						break;
					case 70:
						{ return new IDENTIFIER(yytext()); }
					case -67:
						break;
					case 71:
						{ return new IDENTIFIER(yytext()); }
					case -68:
						break;
					case 72:
						{ System.out.println("ERROR: "+yytext()); }
					case -69:
						break;
					case 73:
						{ return new IDENTIFIER(yytext()); }
					case -70:
						break;
					case 74:
						{ return new IDENTIFIER(yytext()); }
					case -71:
						break;
					case 75:
						{ return new IDENTIFIER(yytext()); }
					case -72:
						break;
					case 76:
						{ return new IDENTIFIER(yytext()); }
					case -73:
						break;
					case 77:
						{ return new IDENTIFIER(yytext()); }
					case -74:
						break;
					case 78:
						{ return new IDENTIFIER(yytext()); }
					case -75:
						break;
					case 79:
						{ return new IDENTIFIER(yytext()); }
					case -76:
						break;
					case 80:
						{ return new IDENTIFIER(yytext()); }
					case -77:
						break;
					case 81:
						{ return new IDENTIFIER(yytext()); }
					case -78:
						break;
					case 82:
						{ return new IDENTIFIER(yytext()); }
					case -79:
						break;
					case 83:
						{ return new IDENTIFIER(yytext()); }
					case -80:
						break;
					case 84:
						{ return new IDENTIFIER(yytext()); }
					case -81:
						break;
					case 85:
						{ return new IDENTIFIER(yytext()); }
					case -82:
						break;
					case 86:
						{ return new IDENTIFIER(yytext()); }
					case -83:
						break;
					case 87:
						{ return new IDENTIFIER(yytext()); }
					case -84:
						break;
					case 88:
						{ return new IDENTIFIER(yytext()); }
					case -85:
						break;
					case 89:
						{ return new IDENTIFIER(yytext()); }
					case -86:
						break;
					case 90:
						{ return new IDENTIFIER(yytext()); }
					case -87:
						break;
					case 91:
						{ return new IDENTIFIER(yytext()); }
					case -88:
						break;
					case 92:
						{ return new IDENTIFIER(yytext()); }
					case -89:
						break;
					case 93:
						{ return new IDENTIFIER(yytext()); }
					case -90:
						break;
					case 94:
						{ return new IDENTIFIER(yytext()); }
					case -91:
						break;
					case 95:
						{ return new IDENTIFIER(yytext()); }
					case -92:
						break;
					case 96:
						{ return new IDENTIFIER(yytext()); }
					case -93:
						break;
					case 97:
						{ return new IDENTIFIER(yytext()); }
					case -94:
						break;
					case 98:
						{ return new IDENTIFIER(yytext()); }
					case -95:
						break;
					case 99:
						{ return new IDENTIFIER(yytext()); }
					case -96:
						break;
					case 100:
						{ return new IDENTIFIER(yytext()); }
					case -97:
						break;
					case 101:
						{ return new IDENTIFIER(yytext()); }
					case -98:
						break;
					case 102:
						{ return new IDENTIFIER(yytext()); }
					case -99:
						break;
					case 103:
						{ return new IDENTIFIER(yytext()); }
					case -100:
						break;
					case 104:
						{ return new IDENTIFIER(yytext()); }
					case -101:
						break;
					case 105:
						{ return new IDENTIFIER(yytext()); }
					case -102:
						break;
					case 106:
						{ return new IDENTIFIER(yytext()); }
					case -103:
						break;
					case 107:
						{ return new IDENTIFIER(yytext()); }
					case -104:
						break;
					case 108:
						{ return new IDENTIFIER(yytext()); }
					case -105:
						break;
					case 109:
						{ return new IDENTIFIER(yytext()); }
					case -106:
						break;
					case 110:
						{ return new IDENTIFIER(yytext()); }
					case -107:
						break;
					case 111:
						{ return new IDENTIFIER(yytext()); }
					case -108:
						break;
					case 112:
						{ return new IDENTIFIER(yytext()); }
					case -109:
						break;
					case 113:
						{ return new IDENTIFIER(yytext()); }
					case -110:
						break;
					case 114:
						{ return new IDENTIFIER(yytext()); }
					case -111:
						break;
					case 115:
						{ return new IDENTIFIER(yytext()); }
					case -112:
						break;
					case 116:
						{ return new IDENTIFIER(yytext()); }
					case -113:
						break;
					case 117:
						{ return new IDENTIFIER(yytext()); }
					case -114:
						break;
					case 118:
						{ return new IDENTIFIER(yytext()); }
					case -115:
						break;
					case 119:
						{ return new IDENTIFIER(yytext()); }
					case -116:
						break;
					case 120:
						{ return new IDENTIFIER(yytext()); }
					case -117:
						break;
					case 121:
						{ return new IDENTIFIER(yytext()); }
					case -118:
						break;
					case 122:
						{ return new IDENTIFIER(yytext()); }
					case -119:
						break;
					case 123:
						{ return new IDENTIFIER(yytext()); }
					case -120:
						break;
					case 124:
						{ return new IDENTIFIER(yytext()); }
					case -121:
						break;
					case 125:
						{ return new IDENTIFIER(yytext()); }
					case -122:
						break;
					case 126:
						{ return new IDENTIFIER(yytext()); }
					case -123:
						break;
					case 127:
						{ return new IDENTIFIER(yytext()); }
					case -124:
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
class browserscanner extends browserlexer implements browserparser.yyInput {
	public browserscanner (java.io.Reader reader) {
		super (reader);
	}
	//public int token() is not longer necessary

	public yyTokenclass advance() throws java.io.IOException {
		yyTokenclass ret = yylex();
		return ret;
	}

	//public Object value () is not longer necessary

}
