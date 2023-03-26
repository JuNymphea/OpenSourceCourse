import java.util.ArrayList;

public class While {

    Condition cond;
    Statement stmt;

    public While(Condition cond, Statement stmt) {
        this.cond = cond;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "While( cond = " + this.cond + " , stmt = " + this.stmt + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        while(true) {
            Integer val = this.cond.eval(ctx);
            if(val == null) {
                throw new Exception("invalid while condition expression");
            }
            if(val == 0) {
                break;
            }
            else {
                this.stmt.eval(ctx);
            }
        }
        return null;
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        int i = buff.size();
        this.cond.gen(buff);
        int j = buff.size();
        buff.add(new Ir("BrFalse"));
        this.stmt.gen(buff);
        buff.add(new Ir("Jump", i));
        buff.set(j, new Ir("BrFalse", buff.size()));
    }


}
