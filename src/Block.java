import java.util.ArrayList;

public class Block {
    ArrayList<Const> consts;
    ArrayList<String> vars;
    Procedure procs;
    Statement stmt;

    public Block(ArrayList<Const> consts, ArrayList<String> vars, Procedure procs, Statement stmt) {
        this.consts = consts;
        this.vars = vars;
        this.procs = procs;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "Block( consts = " + this.consts + " , vars = " + this.vars + " , procs = " + this.procs + " , stmt = " + this.stmt + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        for(int i = 0; i < this.consts.size(); i++) {
            if(ctx.consts.containsKey(this.consts.get(i).name)) {
                throw new Exception("constant redefinition " + this.consts.get(i).name);
            }
            else {
                ctx.consts.put(this.consts.get(i).name, this.consts.get(i).value);
            }
        }

        for(int i = 0; i < this.vars.size(); i++) {
            if(ctx.vars.containsKey(this.vars.get(i))) {
                throw new Exception("variable redefinition " + this.vars.get(i));
            }
            else if(ctx.consts.containsKey(this.vars.get(i))) {
                throw new Exception("variable redefinition " + this.vars.get(i));
            }
            else {
                ctx.vars.put(this.vars.get(i), null);
            }
        }

        if(procs != null) {
            ctx.procs.put(this.procs.name, this.procs);
        }


        this.stmt.eval(ctx);
        return null;
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        for(int i = 0; i < this.consts.size(); i++) {
            buff.add(new Ir("DefLit", this.consts.get(i).name, this.consts.get(i).value));
        }

        for(int i = 0; i < this.vars.size(); i++) {
            buff.add(new Ir("DefVar", this.vars.get(i)));
        }

       if(procs != null) {
           ArrayList<Ir> proc = new ArrayList<>();
           procs.gen(proc);
           buff.add(new Ir("DefProc", this.procs.name, proc));
       }


        this.stmt.gen(buff);
    }

}
