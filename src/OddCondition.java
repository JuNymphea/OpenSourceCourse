import java.util.ArrayList;

public class OddCondition {
    Expression expr;

    public OddCondition(Expression expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "OddCondition( expr = " + this.expr + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        Integer val =  this.expr.eval(ctx);
        if(val == null) {
            throw new Exception("invalid odd expression");
        }
        if(val % 2 == 0) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.expr.gen(buff);
        buff.add(new Ir("Odd"));
    }


}
