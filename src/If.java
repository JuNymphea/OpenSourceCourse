import java.util.ArrayList;

public class If {
    Condition cond;
    Statement stmt;

    public If(Condition cond, Statement stmt) {
        this.cond = cond;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "If( cond = " + this.cond + " , stmt = " + this.stmt + " )";
    }
    public Integer eval(EvalContext ctx) throws Exception {
        Integer val = this.cond.eval(ctx);
        if(val == null) {
            throw new Exception("invalid if condition expression");
        }
        if(val != 0) {
            this.stmt.eval(ctx);
        }
        return null;
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.cond.gen(buff);
        int i = buff.size();
        buff.add(new Ir("BrFalse"));
        this.stmt.gen(buff);
        buff.set(i, new Ir("BrFalse", buff.size()));
    }




}
