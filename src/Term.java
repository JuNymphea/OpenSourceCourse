import java.util.ArrayList;

public class Term {
    Factor lhs;

    ArrayList<Tuple> rhs;

    public Term(Factor lhs, ArrayList<Tuple> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return "Term( lhs = " + this.lhs + " , rhs = " + this.rhs + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        Integer ret = this.lhs.eval(ctx);
        if(ret == null) {
            throw new Exception("invalid term lhs");
        }

        for(int i = 0; i < this.rhs.size(); i++) {
            Tuple tmp = this.rhs.get(i);
            Factor factor = (Factor)tmp.value;
            Integer val = factor.eval(ctx);
            if(val == null) {
                throw new Exception("invalid expression rhs");
            }
            if(tmp.key.equals("*")) {
                ret *= val;
            }
            else if(tmp.key.equals("/")) {
                if(val == 0) {
                    throw new Exception("division by zero");
                }
                else {
                    ret /= val;
                }
            }
            else {
                throw new Exception("invalid expression operator");
            }
        }
        return ret;
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.lhs.gen(buff);

        for(int i = 0; i < this.rhs.size(); i++) {
            Tuple tmp = this.rhs.get(i);
            Factor factor = (Factor)tmp.value;
            factor.gen(buff);

            if(tmp.key.equals("*")) {
                buff.add(new Ir("Mul"));
            }
            else if(tmp.key.equals("/")) {
                buff.add(new Ir("Div"));
            }
            else {
                throw new Exception("invalid expression operator");
            }
        }
    }
}
