import java.util.ArrayList;

public class Statement<T> {
    // Assign | Call | Begin | If | While | InputOutput
    T stmt;

    public Statement(T stmt) {
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "Statement( stmt = " + this.stmt + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        if(this.stmt instanceof Assign) {
            return ((Assign) this.stmt).eval(ctx);
        }
        else if(this.stmt instanceof Call) {
            return ((Call) this.stmt).eval(ctx);
        }
        else if(this.stmt instanceof Begin) {
            return ((Begin) this.stmt).eval(ctx);
        }
        else if(this.stmt instanceof If) {
            return ((If) this.stmt).eval(ctx);
        }
        else if(this.stmt instanceof While) {
            return ((While) this.stmt).eval(ctx);
        }
        else if(this.stmt instanceof InputOutput) {
            return ((InputOutput) this.stmt).eval(ctx);
        }
        else {
            return null;
        }
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        if(this.stmt instanceof Assign) {
            ((Assign) this.stmt).gen(buff);
        }
        else if(this.stmt instanceof Call) {
            ((Call) this.stmt).gen(buff);
        }
        else if(this.stmt instanceof Begin) {
            ((Begin) this.stmt).gen(buff);
        }
        else if(this.stmt instanceof If) {
            ((If) this.stmt).gen(buff);
        }
        else if(this.stmt instanceof While) {
            ((While) this.stmt).gen(buff);
        }
        else if(this.stmt instanceof InputOutput) {
            ((InputOutput) this.stmt).gen(buff);
        }
    }

}
