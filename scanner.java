//Start ofUsercode
//End of usercode


public class scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

    public enum JavaToken {
    WHILE,
    DO,
    FOR,
    IF,
    ELSE,
    TYPE,
    FLOAT,
	INT,
	STRING,
    BOOLEAN,
    IDENTIFIER,
    ACCESSRIGHT,
    STATIC,
    ABSTRACT,
    CLASS,
    SEMICOLON,
    EQUALS,
    OPERATOR
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public scanner (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public scanner (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private scanner () {
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
		/* 32 */ YY_NOT_ACCEPT,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NOT_ACCEPT,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NOT_ACCEPT,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
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
		/* 95 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"35:8,36:2,1,35:2,1,35:18,36,35:6,34,2,3,9,7,35,8,33,10,32:10,35,6,35,11,35:" +
"3,31:26,35:6,23,25,24,17,16,19,27,13,14,31:2,15,31,26,18,29,31,21,20,22,28," +
"30,12,31:3,4,35,5,35:65410,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,96,
"0,1,2,1:6,3,1:3,4,5,1,6,7:5,8,9,10,7:7,8,11,12,13,9,14,15,10,16,17,18,19,20" +
",21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45" +
",46,47,48,49,50,51,52,53,7,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69," +
"70")[0];

	private int yy_nxt[][] = unpackFromString(71,37,
"1,2,3,4,5,6,7,8,9,10,11,12,13,78,33,78,65,37,78,51,80,78,67,94,82,89,78:3,8" +
"4,78:2,14,15,34,15,2,-1:38,2,-1:34,2,-1:32,16,-1:16,17,66,17:19,-1:36,14,32" +
",-1:35,16,39,-1:15,17:21,-1:36,22,-1:6,36:32,23,36:2,-1:32,24,-1:16,17:7,18" +
",17:6,35,17:6,-1:6,36:35,-1:12,17:10,20,17:10,-1:16,17:6,19,17:14,-1:16,17:" +
"9,21,17:11,-1:16,17:4,25,17:16,-1:16,17:4,26,17:16,-1:16,17:4,27,17:16,-1:1" +
"6,17:8,28,17:12,-1:16,17:15,20,17:5,-1:16,17:12,29,17:8,-1:16,17:12,30,17:8" +
",-1:16,17:14,20,17:6,-1:16,17:4,30,17:16,-1:16,17:10,31,17:10,-1:16,17:5,30" +
",17:15,-1:16,17:3,68,17:2,38,17:4,69,17:9,-1:16,17:8,40,17:12,-1:16,17:16,4" +
"1,17:4,-1:16,17:3,42,17:17,-1:16,17:11,35,17:9,-1:16,17:8,41,17:12,-1:16,17" +
":8,43,17:12,-1:16,17:14,44,17:6,-1:16,17:2,45,17:18,-1:16,17:2,46,17:18,-1:" +
"16,17:11,47,17:9,-1:16,17:10,48,17:10,-1:16,17:12,49,17:8,-1:16,17:4,50,17:" +
"16,-1:16,17:3,52,17:17,-1:16,17:2,54,17:18,-1:16,17:9,53,17:11,-1:16,17:6,5" +
"5,17:14,-1:16,17:3,56,17:17,-1:16,17:11,57,17:9,-1:16,17:2,58,17:18,-1:16,1" +
"7:10,59,17:10,-1:16,17:3,60,17:17,-1:16,17:4,61,17:16,-1:16,17:11,62,17:9,-" +
"1:16,17:11,63,17:9,-1:16,17:10,64,17:10,-1:16,17:9,71,17,72,17:9,-1:16,17:1" +
"0,79,17:10,-1:16,17:13,73,17:7,-1:16,17:3,70,17:17,-1:16,17:3,74,17:17,-1:1" +
"6,17:9,90,17:6,81,17:4,-1:16,17:18,75,17:2,-1:16,17:9,76,17:11,-1:16,17:12," +
"77,17:8,-1:16,17:6,83,17:14,-1:16,17:6,88,17:14,-1:16,17:2,85,17:3,95,17:14" +
",-1:16,17:10,86,17:10,-1:16,17:4,87,17:16,-1:16,17:8,91,17:12,-1:16,17:13,9" +
"3,17:7,-1:16,17:10,92,17:10,-1:4");

	public java.lang.Integer yylex ()
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

System.out.println("EOF reached");
return -1;
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
						{ System.out.println(yytext()+" :whitespace"); }
					case -3:
						break;
					case 3:
						{ System.out.println(yytext()+" :bracket open"); }
					case -4:
						break;
					case 4:
						{ System.out.println(yytext()+" :bracket close"); }
					case -5:
						break;
					case 5:
						{ System.out.println(yytext()+" :curly bracket open"); }
					case -6:
						break;
					case 6:
						{ System.out.println(yytext()+" :curly bracket close"); }
					case -7:
						break;
					case 7:
						{ System.out.println(yytext()+" :semicolon"); }
					case -8:
						break;
					case 8:
						{ System.out.println(yytext()+" :operator"); }
					case -9:
						break;
					case 9:
						{ System.out.println(yytext()+":operator"); }
					case -10:
						break;
					case 10:
						{ System.out.println(yytext()+" :operator"); }
					case -11:
						break;
					case 11:
						{ System.out.println(yytext()+" :operator"); }
					case -12:
						break;
					case 12:
						{ System.out.println(yytext()+" :equals"); }
					case -13:
						break;
					case 13:
						{ System.out.println(yytext()+" :identifier"); }
					case -14:
						break;
					case 14:
						{ System.out.println(yytext()+" :integer"); }
					case -15:
						break;
					case 15:
						{ System.out.println("ERROR: "+yytext()); }
					case -16:
						break;
					case 16:
						{ System.out.println(yytext()+" :integer"); }
					case -17:
						break;
					case 17:
						{ System.out.println(yytext()+" :identifier"); }
					case -18:
						break;
					case 18:
						{ System.out.println(yytext()+" :if"); }
					case -19:
						break;
					case 19:
						{ System.out.println(yytext()+" :do"); }
					case -20:
						break;
					case 20:
						{ System.out.println(yytext()+" :type"); }
					case -21:
						break;
					case 21:
						{ System.out.println(yytext()+" :for"); }
					case -22:
						break;
					case 22:
						{ System.out.println(yytext()+" :float"); }
					case -23:
						break;
					case 23:
						{ System.out.println(yytext()+" :string"); }
					case -24:
						break;
					case 24:
						{ System.out.println(yytext()+" :float"); }
					case -25:
						break;
					case 25:
						{ System.out.println(yytext()+" :else"); }
					case -26:
						break;
					case 26:
						{ System.out.println(yytext()+" :boolean"); }
					case -27:
						break;
					case 27:
						{ System.out.println(yytext()+" :while"); }
					case -28:
						break;
					case 28:
						{ System.out.println(yytext()+" :class"); }
					case -29:
						break;
					case 29:
						{ System.out.println(yytext()+" :static"); }
					case -30:
						break;
					case 30:
						{ System.out.println(yytext()+" :accessright"); }
					case -31:
						break;
					case 31:
						{ System.out.println(yytext()+" :abstract"); }
					case -32:
						break;
					case 33:
						{ System.out.println(yytext()+" :identifier"); }
					case -33:
						break;
					case 34:
						{ System.out.println("ERROR: "+yytext()); }
					case -34:
						break;
					case 35:
						{ System.out.println(yytext()+" :identifier"); }
					case -35:
						break;
					case 37:
						{ System.out.println(yytext()+" :identifier"); }
					case -36:
						break;
					case 38:
						{ System.out.println(yytext()+" :identifier"); }
					case -37:
						break;
					case 40:
						{ System.out.println(yytext()+" :identifier"); }
					case -38:
						break;
					case 41:
						{ System.out.println(yytext()+" :identifier"); }
					case -39:
						break;
					case 42:
						{ System.out.println(yytext()+" :identifier"); }
					case -40:
						break;
					case 43:
						{ System.out.println(yytext()+" :identifier"); }
					case -41:
						break;
					case 44:
						{ System.out.println(yytext()+" :identifier"); }
					case -42:
						break;
					case 45:
						{ System.out.println(yytext()+" :identifier"); }
					case -43:
						break;
					case 46:
						{ System.out.println(yytext()+" :identifier"); }
					case -44:
						break;
					case 47:
						{ System.out.println(yytext()+" :identifier"); }
					case -45:
						break;
					case 48:
						{ System.out.println(yytext()+" :identifier"); }
					case -46:
						break;
					case 49:
						{ System.out.println(yytext()+" :identifier"); }
					case -47:
						break;
					case 50:
						{ System.out.println(yytext()+" :identifier"); }
					case -48:
						break;
					case 51:
						{ System.out.println(yytext()+" :identifier"); }
					case -49:
						break;
					case 52:
						{ System.out.println(yytext()+" :identifier"); }
					case -50:
						break;
					case 53:
						{ System.out.println(yytext()+" :identifier"); }
					case -51:
						break;
					case 54:
						{ System.out.println(yytext()+" :identifier"); }
					case -52:
						break;
					case 55:
						{ System.out.println(yytext()+" :identifier"); }
					case -53:
						break;
					case 56:
						{ System.out.println(yytext()+" :identifier"); }
					case -54:
						break;
					case 57:
						{ System.out.println(yytext()+" :identifier"); }
					case -55:
						break;
					case 58:
						{ System.out.println(yytext()+" :identifier"); }
					case -56:
						break;
					case 59:
						{ System.out.println(yytext()+" :identifier"); }
					case -57:
						break;
					case 60:
						{ System.out.println(yytext()+" :identifier"); }
					case -58:
						break;
					case 61:
						{ System.out.println(yytext()+" :identifier"); }
					case -59:
						break;
					case 62:
						{ System.out.println(yytext()+" :identifier"); }
					case -60:
						break;
					case 63:
						{ System.out.println(yytext()+" :identifier"); }
					case -61:
						break;
					case 64:
						{ System.out.println(yytext()+" :identifier"); }
					case -62:
						break;
					case 65:
						{ System.out.println(yytext()+" :identifier"); }
					case -63:
						break;
					case 66:
						{ System.out.println(yytext()+" :identifier"); }
					case -64:
						break;
					case 67:
						{ System.out.println(yytext()+" :identifier"); }
					case -65:
						break;
					case 68:
						{ System.out.println(yytext()+" :identifier"); }
					case -66:
						break;
					case 69:
						{ System.out.println(yytext()+" :identifier"); }
					case -67:
						break;
					case 70:
						{ System.out.println(yytext()+" :identifier"); }
					case -68:
						break;
					case 71:
						{ System.out.println(yytext()+" :identifier"); }
					case -69:
						break;
					case 72:
						{ System.out.println(yytext()+" :identifier"); }
					case -70:
						break;
					case 73:
						{ System.out.println(yytext()+" :identifier"); }
					case -71:
						break;
					case 74:
						{ System.out.println(yytext()+" :identifier"); }
					case -72:
						break;
					case 75:
						{ System.out.println(yytext()+" :identifier"); }
					case -73:
						break;
					case 76:
						{ System.out.println(yytext()+" :identifier"); }
					case -74:
						break;
					case 77:
						{ System.out.println(yytext()+" :identifier"); }
					case -75:
						break;
					case 78:
						{ System.out.println(yytext()+" :identifier"); }
					case -76:
						break;
					case 79:
						{ System.out.println(yytext()+" :identifier"); }
					case -77:
						break;
					case 80:
						{ System.out.println(yytext()+" :identifier"); }
					case -78:
						break;
					case 81:
						{ System.out.println(yytext()+" :identifier"); }
					case -79:
						break;
					case 82:
						{ System.out.println(yytext()+" :identifier"); }
					case -80:
						break;
					case 83:
						{ System.out.println(yytext()+" :identifier"); }
					case -81:
						break;
					case 84:
						{ System.out.println(yytext()+" :identifier"); }
					case -82:
						break;
					case 85:
						{ System.out.println(yytext()+" :identifier"); }
					case -83:
						break;
					case 86:
						{ System.out.println(yytext()+" :identifier"); }
					case -84:
						break;
					case 87:
						{ System.out.println(yytext()+" :identifier"); }
					case -85:
						break;
					case 88:
						{ System.out.println(yytext()+" :identifier"); }
					case -86:
						break;
					case 89:
						{ System.out.println(yytext()+" :identifier"); }
					case -87:
						break;
					case 90:
						{ System.out.println(yytext()+" :identifier"); }
					case -88:
						break;
					case 91:
						{ System.out.println(yytext()+" :identifier"); }
					case -89:
						break;
					case 92:
						{ System.out.println(yytext()+" :identifier"); }
					case -90:
						break;
					case 93:
						{ System.out.println(yytext()+" :identifier"); }
					case -91:
						break;
					case 94:
						{ System.out.println(yytext()+" :identifier"); }
					case -92:
						break;
					case 95:
						{ System.out.println(yytext()+" :identifier"); }
					case -93:
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
