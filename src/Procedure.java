import java.util.ArrayList;

public class Procedure {
    String name;
    Block body;

    public Procedure(String name, Block body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Procedure( name = " + this.name + " , body = " + this.body + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        return this.body.eval(ctx);
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.body.gen(buff);
    }



}
