import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class Lexer {

    final List<String> KEYWORD_SET = new ArrayList<String>() {{
        add("const");
        add("var");
        add("procedure");
        add("call");
        add("begin");
        add("end");
        add("if");
        add("then");
        add("while");
        add("do");
        add("odd");
    }};

    final List<Character> IDENT_FIRST = new ArrayList<Character>() {{
        for(char c = 'a'; c <= 'z'; c++) {
            add(c);
        }
        for(char c = 'A'; c <= 'Z'; c++) {
            add(c);
        }
        add('_');
    }};

    final List<Character> IDENT_REMAIN = new ArrayList<Character>() {{
        for(char c = 'a'; c <= 'z'; c++) {
            add(c);
        }
        for(char c = 'A'; c <= 'Z'; c++) {
            add(c);
        }
        for(char c = '0'; c <= '9'; c++) {
            add(c);
        }
        add('_');
    }};

    final List<Character> OPERATOR = new ArrayList<Character>() {{
       String s = "=#+-*/,.;()?!";
       for(int i = 0; i < s.length(); i++){
           add(s.charAt(i));
       }
    }};

    final List<Character> COMPARE = new ArrayList<Character>() {{
        String s = "<>";
        for(int i = 0; i < s.length(); i++){
            add(s.charAt(i));
        }
    }};



//    ArrayList<Token> res;
    int i;
    String s;


    public Lexer(String src) {
        this.i = 0;
        this.s = src;
//        ArrayList<Token> res = new ArrayList<>();
//        this.res = res;
    }

    public char locate() {
        return this.s.charAt(this.i);
    }

    public boolean eof() {
        return this.i >= s.length();
    }

    public void skip_blank() {
        while(!this.eof() && Character.isSpace(this.locate())){
            this.i++;
        }
    }

    public Token next() throws Exception {
        String val = "";
        this.skip_blank();

        if(this.eof()) {
            return new Token(4, val).eof();
        }

        else if(Character.isDigit(this.locate())) {
            while(Character.isDigit(this.locate())) {
                val += this.locate();
                this.i++;
            }
            return new Token(1, val).num();
        }

        else if(IDENT_FIRST.contains(this.locate())) {
            while(IDENT_REMAIN.contains(this.locate())) {
                val += this.locate();
                this.i++;
            }
            if(KEYWORD_SET.contains(val)) {
                return new Token(3, val).keyword();
            }
            else {
                return new Token(2, val).name();
            }
        }

        else if(OPERATOR.contains(this.locate())) {
            val += this.locate();
            this.i++;
            return new Token(0, val).op();
        }

        else if(COMPARE.contains(this.locate())) {
            val += this.locate();
            this.i++;
            if(!this.eof() && this.locate() == '=') {
                val += "=";
                this.i++;
                return new Token(0, val).op();
            }
            else {
                return new Token(0, val).op();
            }
        }

        else if(this.locate() == ':') {
            this.i++;
            if(this.locate() == '=') {
                this.i++;
                return new Token(0, ":=").op();
            }
            else {
//                System.out.println("\"=\" expected");
                throw new Exception("\"=\" expected");
            }
        }

        else {
//            System.out.println("Invalid character");
            throw new Exception("Invalid character");

        }

    }

}
