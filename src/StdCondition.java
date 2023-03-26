import java.util.ArrayList;

public class StdCondition {
    Expression lhs;
    String op;
    Expression rhs;

    public StdCondition(Expression lhs, String op, Expression rhs) {
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }
    @Override
    public String toString() {
        return "StdCondition( lhs = " + this.lhs + " , op = " + this.op + " , rhs = " + this.rhs + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        Integer l = this.lhs.eval(ctx);
        Integer r = this.rhs.eval(ctx);
        if(l == null) {
            throw new Exception("invalid std condition lhs expression");
        }
        if(r == null) {
            throw new Exception("invalid std condition rhs expression");
        }

        if(this.op.equals("=")) {
           int ret = l.equals(r) ? 1 : 0;
           return ret;
        }
        if(this.op.equals("#")) {
            int ret = l.equals(r) ? 0 : 1;
            return ret;
        }
        if(this.op.equals("<")) {
            int ret = l < r ? 1 : 0;
            return ret;
        }
        if(this.op.equals("<=")) {
            int ret = l <= r ? 1 : 0;
            return ret;
        }
        if(this.op.equals(">")) {
            int ret = l > r ? 1 : 0;
            return ret;
        }
        if(this.op.equals(">=")) {
            int ret = l >= r ? 1 : 0;
            return ret;
        }
        throw new Exception("invalid std condition operator: " + this.op);
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.lhs.gen(buff);
        this.rhs.gen(buff);

        if(this.op.equals("=")) {
            buff.add(new Ir("Eq"));
        }
        else if(this.op.equals("#")) {
            buff.add(new Ir("Ne"));
        }
        else if(this.op.equals("<")) {
            buff.add(new Ir("Lt"));
        }
        else if(this.op.equals("<=")) {
            buff.add(new Ir("Lte"));
        }
        else if(this.op.equals(">")) {
            buff.add(new Ir("Gt"));
        }
        else if(this.op.equals(">=")) {
            buff.add(new Ir("Gte"));
        }
        else {
            throw new Exception("invalid std condition operator: " + this.op);
        }

    }


}
