import java.util.ArrayList;
import java.util.regex.Pattern;

public class Factor<T> {
//    str | int | Expression
    T value;

    public Factor(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Factor( value = " + this.value + " )";
    }

    public Integer eval(EvalContext ctx) throws Exception {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

        if(pattern.matcher((String)this.value).matches()) {
            return Integer.valueOf((String)this.value);
        }
        else if(this.value instanceof String) {
            if(ctx.vars.containsKey(this.value)) {
                Integer ret = (Integer)ctx.vars.get(this.value);
                if(ret == null) {
                    throw new Exception("variable " + this.value + " referenced before initialize");
                }
                else {
                    return ret;
                }
            }

            if(ctx.consts.containsKey(this.value)) {
                return (Integer)ctx.consts.get(this.value);
            }

            else {
                throw new Exception("undifined symbol : " + this.value);
            }
        }
        else if(this.value instanceof Expression) {
            Expression tmp = (Expression) this.value;
            Integer val = tmp.eval(ctx);
            if(val == null) {
                throw new Exception("invalid nested expression");
            }
            return val;
        }
        else {
            throw new Exception("invalid factor value");
        }
    }

    public void gen(ArrayList<Ir> buff) throws Exception {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

        if(pattern.matcher((String)this.value).matches()) {
            buff.add(new Ir("LoadLit", Integer.valueOf((String)this.value)));
        }
        else if(this.value instanceof String) {
            buff.add(new Ir("LoadVar", (String) this.value));
        }
        else if(this.value instanceof Expression) {
            Expression tmp = (Expression) this.value;
            tmp.gen(buff);
        }

    }

}
