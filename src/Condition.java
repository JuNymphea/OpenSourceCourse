import java.util.ArrayList;

public class Condition<T> {
    T cond;

    public Condition(T cond) {
        this.cond = cond;
    }

    @Override
    public String toString() {
        return "Condition( cond = " + this.cond + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        if(this.cond instanceof OddCondition odd) {
            return odd.eval(ctx);
        }
        else if(this.cond instanceof StdCondition std) {
            return std.eval(ctx);
        }
        else {
            return null;
        }
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        if(this.cond instanceof OddCondition odd) {
            odd.gen(buff);
        }
        else if(this.cond instanceof StdCondition std) {
            std.gen(buff);
        }
    }
}
