import java.util.ArrayList;

public class Call {
    String name;

    public Call(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Call( name = " + this.name + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        if(ctx.procs.containsKey(this.name)) {
            Procedure tmp = (Procedure) ctx.procs.get(this.name);
            tmp.eval(ctx);
            return null;
        }
        else {
            throw new Exception("undefined procedure " + this.name);
        }
    }

    public void gen(ArrayList<Ir> buff) {
        for(int i = 0; i < buff.size(); i++) {
            Ir tmp = buff.get(i);
            if(tmp.args != null && tmp.args.equals(this.name)) {
                ArrayList<Ir> proc = (ArrayList<Ir>)tmp.value;
                for(int j = 0; j < proc.size(); j++) {
                    buff.add(proc.get(j));
                }
            }
        }
    }
}
