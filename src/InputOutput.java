import java.util.ArrayList;
import java.util.Scanner;

public class InputOutput {
    String name;
    boolean is_input;

    public InputOutput(String name, boolean b) {
        this.name = name;
        this.is_input = b;
    }
    @Override
    public String toString() {
        return "InputOutput( name = " + this.name + " , is_input = " + this.is_input + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        if(!this.is_input) {
            System.out.println(new Factor(this.name).eval(ctx));
        }
        else if(ctx.vars.containsKey(this.name)){
            Scanner sc = new Scanner(System.in);
            ctx.vars.put(this.name, sc.nextInt());
        }
        else {
            throw new Exception("undefined variable: " + this.name);
        }
        return null;
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        if(this.is_input) {
            buff.add(new Ir("Input"));
            buff.add(new Ir("Store", this.name));
        }
        else {
            buff.add(new Ir("LoadVar", this.name));
            buff.add(new Ir("Output"));
        }
    }
}
