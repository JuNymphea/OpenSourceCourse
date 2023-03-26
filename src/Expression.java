import java.util.ArrayList;

public class Expression {
    String mod;
    Term lhs;
    ArrayList<Tuple> rhs;
    public Expression(String mod, Term lhs, ArrayList<Tuple> rhs) {
        this.mod = mod;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return "Expression( mod = " + this.mod + " , lhs = " + this.lhs + " , rhs = " + this.rhs  + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        int sign = 0;
        if(this.mod.equals("+") || this.mod.equals("")) {
            sign = 1;
        }
        else if(this.mod.equals("-")) {
            sign = -1;
        }
        else {
            throw new Exception("invalid expression sign");
        }

        Integer ret = this.lhs.eval(ctx) * sign;
        if(ret == null) {
            throw new Exception("invalid expression lhs");
        }

        for(int i = 0; i < this.rhs.size(); i++) {
            Tuple tmp = this.rhs.get(i);
            Term term = (Term)tmp.value;
            Integer val = term.eval(ctx);
            if(val == null) {
                throw new Exception("invalid expression rhs");
            }
            if(tmp.key.equals("+")) {
                ret += val;
            }
            else if(tmp.key.equals("-")) {
                ret -= val;
            }
            else {
                throw new Exception("invalid expression operator");
            }
        }

        return ret;

    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.lhs.gen(buff);
        if(this.mod.equals("-")) {
            buff.add(new Ir("Neg"));
        }
        else if(!this.mod.equals("") && !this.mod.equals("+")) {
            throw new Exception("invalid expression sign");
        }

        for(int i = 0; i < this.rhs.size(); i++) {
            Tuple tmp = this.rhs.get(i);
            Term term = (Term)tmp.value;
            term.gen(buff);

            if(tmp.key.equals("+")) {
                buff.add(new Ir("Add"));
            }
            else if(tmp.key.equals("-")) {
                buff.add(new Ir("Sub"));
            }
            else {
                throw new Exception("invalid expression operator");
            }
        }
    }


}
