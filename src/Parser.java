import java.util.ArrayList;

public class Parser {
    Lexer lx;

    public Parser(Lexer lx) {
        this.lx = lx;
    }

    public boolean check(int ty, String val) throws Exception {
        int tmp = this.lx.i;
        Token tk = this.lx.next();
//        System.out.println(tk);

        if(tk.ty == ty && tk.val.equals(val)) {
            return true;
        }

        this.lx.i = tmp;
        return false;
    }
    public boolean check(int ty, Integer val) throws Exception {
        int tmp = this.lx.i;
        Token tk = this.lx.next();

        if(tk.ty == ty && tk.val == val) {
            return true;
        }

        this.lx.i = tmp;
        return false;
    }

    public void expect(int ty, String val) throws Exception {
        Token tk = this.lx.next();

        if(tk.ty != ty) {
            throw new Exception(ty + " expected, got " + tk.ty);
        }

        if(!tk.val.equals(val)) {
            throw new Exception(val + " expected, got " + tk.val);
        }
    }
    public void expect(int ty, Integer val) throws Exception {
        Token tk = this.lx.next();

        if(tk.ty != ty) {
            throw new Exception(ty + " expected, got " + tk.ty);
        }

        if(tk.val != val) {
            throw new Exception(val + " expected, got " + tk.val);
        }
    }
    public Program program() throws Exception {
        Block block = this.block();
        this.expect(0, ".");
        return new Program(block);
    }

    public Block block() throws Exception {
        ArrayList<Const> consts = new ArrayList<>();
        ArrayList<String> vars = new ArrayList<>();
        Procedure proc = null;

        if(this.check(3, "const")) {
            consts.addAll(this.consts());
        }

        if(this.check(3, "var")) {
            vars.addAll(this.vars());
//            System.out.println("hahaha");
        }

        if(this.check(3, "procedure")) {
            proc = this.procedure();
        }

        Statement stmt = this.statement();

        return new Block(consts, vars, proc, stmt);
    }
    public ArrayList<Const> consts() throws Exception {
        ArrayList<Const> ret = new ArrayList<>();
        while(true) {
            Token name = this.lx.next();
            int ty = name.ty;

            if(ty != 2) {
                throw new Exception("name expected");
            }

            this.expect(0, "=");
            Token num = this.lx.next();
            ty = num.ty;

            if(ty != 1) {
                throw new Exception("num expected");
            }
            else {
//                System.out.println(name);
//                System.out.println(num);
                ret.add(new Const((String)name.val, Integer.parseInt((String)num.val)));
            }

            if(this.check(0, ";")) {
                return ret;
            }
            else {
                this.expect(0, ",");
            }
        }
    }

    public ArrayList<String> vars() throws Exception {
        ArrayList<String> ret = new ArrayList<>();

        while(true) {
            Token name = this.lx.next();
            int ty = name.ty;

            if(ty != 2) {
                throw new Exception("name expected");
            }
            else {
                ret.add((String)name.val);
            }

            if(this.check(0, ";")) {
                return ret;
            }
            else {
                this.expect(0, ",");
            }
        }
    }

    public Procedure procedure() throws Exception {
        Token name = this.lx.next();
        int ty = name.ty;

        if(name.ty != 2) {
            throw new Exception("name expected");
        }
        else {
            this.expect(0, ";");
        }

        Block block = this.block();

        this.expect(0, ";");

        return new Procedure((String)name.val, block);

    }

    public Statement statement() throws Exception {
            if(this.check(3, "call")) {
                Token ident = this.lx.next();
                if(ident.ty != 2) {
                    throw new Exception("name expected");
                }
                else {
                    return new Statement(new Call((String)ident.val));
                }
            }

            else if(this.check(3, "begin")) {
                ArrayList<Statement> body = new ArrayList<>();
                while(true) {
                    if(this.check(3, "end")) {
//                        System.out.println("oeoeoe");
                        break;
                    }

                    Statement stmt = this.statement();
                    body.add(stmt);

                    this.check(0, ";");

                }
                return new Statement(new Begin(body));
            }

            else if(this.check(3, "if")) {
                Condition cond = this.condition();

                this.expect(3, "then");

                return new Statement(new If(cond, this.statement()));
            }

            else if(this.check(3, "while")) {
//                System.out.println("mememe");
                Condition cond = this.condition();

                this.expect(3, "do");

                return new Statement(new While(cond, this.statement()));
            }
            else if(this.check(0, "?")) {
                Token name = this.lx.next();
                int ty = name.ty;
                if(ty != 2) {
                    throw new Exception("name expected");
                }
                else {
                    return new Statement(new InputOutput((String)name.val, true));
                }
            }
            else if(this.check(0, "!")) {
                Token name = this.lx.next();
                int ty = name.ty;
                if(ty != 2) {
                    throw new Exception("name expected");
                }
                else {
                    return new Statement(new InputOutput((String)name.val, false));
                }
            }
            else {
                Token tk = this.lx.next();
//                System.out.println(tk);
                if(tk.ty != 2) {
//                    System.out.println(tk);
                    throw new Exception("name expected");
                }
                this.expect(0, ":=");
//                System.out.println("gegege");
                return new Statement(new Assign((String)tk.val, this.expression()));

            }
        }

    public Condition condition() throws Exception {
        if(this.check(3, "odd")) {
            OddCondition cond = this.oddCondition();
            return new Condition(cond);
        }
        else {
            StdCondition cond = this.stdCondition();
            return new Condition(cond);
        }
    }

    public OddCondition oddCondition() throws Exception {
        Expression expr = this.expression();
        return new OddCondition(expr);
    }

    public StdCondition stdCondition() throws Exception {
        Expression lhs = this.expression();

        Token tk = this.lx.next();
        if(tk.ty != 0) {
            throw new Exception("operator expected");
        }
        ArrayList<String> ops = new ArrayList<>();
        ops.add("=");
        ops.add("#");
        ops.add("<");
        ops.add("<=");
        ops.add(">");
        ops.add(">=");
        if(!ops.contains(tk.val)) {
            throw new Exception("condition operator expected");
        }

        Expression rhs = this.expression();

        return new StdCondition(lhs, (String)tk.val, rhs);
    }


    public Expression expression() throws Exception {
        String mod = "";
        if(this.check(0, "+")) {
            mod = "+";
        }
        else if(this.check(0, "-")) {
            mod = "-";
        }

        Term lhs = this.term();

        ArrayList<Tuple> rhs = new ArrayList<>();
        while(true) {
            if(this.check(0, "+")) {
                rhs.add(new Tuple("+", this.term()));
            }
            else if(this.check(0, "-")) {
                rhs.add(new Tuple("-", this.term()));
            }
            else {
                return new Expression(mod, lhs, rhs);
            }
        }
    }

    public Term term() throws Exception {
        Factor lhs = this.factor();

        ArrayList<Tuple> rhs = new ArrayList<>();
        while(true) {
            if(this.check(0, "*")) {
                rhs.add(new Tuple("*", this.factor()));
            }
            else if(this.check(0, "/")) {
                rhs.add(new Tuple("/", this.factor()));
            }
            else {
                return new Term(lhs, rhs);
            }
        }
    }

    public Factor factor() throws Exception {
        Token tk = this.lx.next();
        if(tk.ty == 1 || tk.ty == 2) {
            return new Factor(tk.val);
        }
        if(tk.ty != 0 || !tk.val.equals("(")) {
            throw new Exception("\"(\" expected");
        }

        Expression expr = this.expression();
        this.expect(0, ")");
        return new Factor(expr);
    }



}
