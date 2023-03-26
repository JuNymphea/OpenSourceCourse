import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ir<T> {

    public final static Map<String, Integer> irOpCode = new HashMap<String, Integer>();
    static {
        irOpCode.put("Add", 0);
        irOpCode.put("Sub", 1);
        irOpCode.put("Mul", 2);
        irOpCode.put("Div", 3);
        irOpCode.put("Neg", 4);
        irOpCode.put("Eq", 5);
        irOpCode.put("Ne", 6);
        irOpCode.put("Lt", 7);
        irOpCode.put("Lte", 8);
        irOpCode.put("Gt", 9);
        irOpCode.put("Gte", 10);
        irOpCode.put("Odd", 11);
        irOpCode.put("LoadVar", 12);
        irOpCode.put("LoadLit", 13);
        irOpCode.put("Store", 14);
        irOpCode.put("Jump", 15);
        irOpCode.put("BrFalse", 16);
        irOpCode.put("DefVar", 17);
        irOpCode.put("DefLit", 18);
        irOpCode.put("DefProc", 19);
        irOpCode.put("Input", 100);
        irOpCode.put("Output", 101);
        irOpCode.put("Halt", 255);
    }
    Integer op;
//  str int Procedure
    T args;
    T value;

    public Ir(String s) {
        this.op = irOpCode.get(s);
        this.args = null;
        this.value = null;
    }
    public Ir(String s, String name) {
        this.op = irOpCode.get(s);
        this.args = (T)name;
        this.value = null;
    }
    public Ir(String s, Integer name) {
        this.op = irOpCode.get(s);
        this.args = (T)name;
        this.value = null;
    }

    public Ir(String s, String name, Integer value) {
        this.op = irOpCode.get(s);
        this.args = (T) name;
        this.value = (T)value;
    }
    public Ir(String s, String name, ArrayList value) {
        this.op = irOpCode.get(s);
        this.args = (T) name;
        this.value = (T)value;
    }

    @Override
    public String toString() {
            List<String> keyList = new ArrayList<>();
            for(String key: irOpCode.keySet()){
                if(irOpCode.get(key).equals(this.op)){
                    keyList.add(key);
                }
            }
            String opName = keyList.get(0);


        return "{ " + opName + " : " + this.op + " ; " + this.args + " ; " + this.value + " }";
    }

}
