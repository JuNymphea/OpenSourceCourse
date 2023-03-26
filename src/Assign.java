import java.util.ArrayList;

public class Assign {
    String name;
    Expression expr;
    public Assign(String name, Expression expr) {
        this.name = name;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Assign( name = " + this.name + " , expr = " + this.expr + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        if(!ctx.vars.containsKey(this.name)) {
            throw new Exception("undifined variable " + this.name);
        }
        Integer val = this.expr.eval(ctx);
        if(val == null) {
            throw new Exception("invalid assignment expression");
        }
        ctx.vars.put(this.name, val);
        //扩展输入输出指令
//        System.out.println(val);

        return null;
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.expr.gen(buff);
        buff.add(new Ir("Store", this.name));
    }
}
