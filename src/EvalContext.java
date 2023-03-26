import java.util.HashMap;

public class EvalContext<T> {
    HashMap<String, Integer> vars;
    HashMap<String, T> procs;
    HashMap<String, Integer> consts;

    public EvalContext() {
        this.vars = new HashMap<>();
        this.procs = new HashMap<>();
        this.consts = new HashMap<>();
    }

}
