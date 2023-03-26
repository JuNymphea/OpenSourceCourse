import java.util.ArrayList;

public class Begin {
    ArrayList<Statement> body;

    public Begin(ArrayList<Statement> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Begin( body = " + this.body + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        for(int i = 0; i < body.size(); i++) {
            body.get(i).eval(ctx);
        }
        return null;
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        for(int i = 0; i < body.size(); i++) {
            body.get(i).gen(buff);
        }
    }
}


