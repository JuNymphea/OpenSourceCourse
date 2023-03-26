import java.util.ArrayList;

public class Program {
    Block block;

    public Program(Block block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "Program( block = " + this.block + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        return this.block.eval(ctx);
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        this.block.gen(buff);
        buff.add(new Ir("Halt"));
    }

}
