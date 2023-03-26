import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws Exception {
//        String s = "var i, s;\n" +
//                "begin\n" +
//                "    i := 0; s := 0;\n" +
//                "    while i < 5 do\n" +
//                "    begin\n" +
//                "        i := i + 1;\n" +
//                "        s := s + i * i;\n" +
//                "        !s\n" +
//                "    end\n" +
//                "end.";


//        String s = "var a, b, n, t;\n" +
//                "begin\n" +
//                "    ?n;\n" +
//                "    a := 0;\n" +
//                "    b := 1;\n" +
//                "    while n > 0 do\n" +
//                "    begin\n" +
//                "        !b;\n" +
//                "        n := n - 1;\n" +
//                "        t := a + b;\n" +
//                "        a := b;\n" +
//                "        b := t\n" +
//                "    end\n" +
//                "end.";
        String s = "var x, squ;\n" +
                "\n" +
                "procedure square;\n" +
                "begin\n" +
                "   squ:= x * x\n" +
                "end;\n" +
                "\n" +
                "begin\n" +
                "   x := 1;\n" +
                "   while x <= 10 do\n" +
                "   begin\n" +
                "      call square;\n" +
                "      ! squ;\n" +
                "      x := x + 1\n" +
                "   end\n" +
                "end.";


//        Lexer lx = new Lexer(s);
//        Token tk = lx.next();
//
//        while(!lx.eof()) {
//            System.out.println(tk);
//            tk = lx.next();
//        }

//        Parser ps = new Parser(new Lexer(s));
//        System.out.print(ps.program());

//        Parser ps = new Parser(new Lexer(s));
//        Program ast = ps.program();
//        EvalContext ctx = new EvalContext();
//        ast.eval(ctx);

//        Parser ps = new Parser(new Lexer(s));
//        Program ast = ps.program();
//        ArrayList<Ir> buff = new ArrayList<>();
//        ast.gen(buff);
//        for(int i = 0; i < buff.size(); i++) {
////            System.out.println(buff.get(i).args instanceof Integer);
//            System.out.println(i + ": " + buff.get(i));
//        }

        Parser ps = new Parser(new Lexer(s));
        Program ast = ps.program();
        ArrayList<Ir> buff = new ArrayList<>();
        ast.gen(buff);
        ir_eval(buff, new EvalContext());

//        Parser ps = new Parser(new Lexer(s));
//        Program ast = ps.program();
//        ast.eval(new EvalContext());

//        Parser ps = new Parser(new Lexer(s));
//        Program ast = ps.program();
//        ArrayList<Ir> buff = new ArrayList<>();
//        ast.gen(buff);
//        for(int i = 0; i < buff.size(); i++) {
////            System.out.println(buff.get(i).args instanceof Integer);
//            System.out.println(i + ": " + buff.get(i));
//        }







    }

    public static void ir_eval(ArrayList<Ir> buff, EvalContext ctx) throws Exception {
        int pc = 0;
        Stack<Integer> sp = new Stack<>();
        while(pc < buff.size()) {
            Ir ir = buff.get(pc);
            pc += 1;
            int v1, v2;
            if(ir.op.equals(Ir.irOpCode.get("Add"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 + v2);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Sub"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 - v2);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Mul"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 * v2);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Div"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                if(v2 == 0) {
                    throw new Exception("division by zero");
                }
                sp.push(v1 / v2);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Neg"))) {
                v1 = sp.pop();
                sp.push(-v1);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Eq"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 == v2 ? 1 : 0);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Ne"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 != v2 ? 1 : 0);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Lt"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 < v2 ? 1 : 0);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Lte"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 <= v2 ? 1 : 0);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Gt"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 > v2 ? 1 : 0);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Gte"))) {
                v2 = sp.pop();
                v1 = sp.pop();
                sp.push(v1 >= v2 ? 1 : 0);
            }
            else if(ir.op.equals(Ir.irOpCode.get("Odd"))) {
                v1 = sp.pop();
                sp.push(v1 % 2 == 1 ? 1 : 0);
            }
            else if(ir.op.equals(Ir.irOpCode.get("LoadVar"))) {
                if(!(ir.args instanceof String)) {
                    throw new Exception("invalid loadvar args");
                }

                if(ctx.vars.containsKey(ir.args)) {
                    String key = (String)ir.args;
                    Integer val = (Integer) ctx.vars.get(key);

                    if(val == null) {
                        throw new Exception("variable " + key + "referenced before initialization");
                    }
                    else {
                        sp.push(val);
                    }

                }
                else if(ctx.consts.containsKey(ir.args)) {
                    sp.push((Integer) ctx.consts.get(ir.args));
                }
                else {
                    throw new Exception("undefined variable: " + ir.args);
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("LoadLit"))) {
                if(!(ir.args instanceof Integer)) {
                    throw new Exception("invalid loadvar args");
                }
                else {
                    sp.push((Integer) ir.args);
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("Store"))) {
                if(!(ir.args instanceof String)) {
                    throw new Exception("invalid store args");
                }
                else if(!ctx.vars.containsKey(ir.args)) {
                    throw new Exception("undefined variable: " + ir.args);
                }
                else {
                    int val = sp.pop();
                    ctx.vars.put((String)ir.args, val);
//                    System.out.println(val);
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("Jump"))) {
                if(!(ir.args instanceof Integer)) {
                    throw new Exception("invalid jump args");
                }
                else if((Integer)ir.args >= 0 && (Integer)ir.args < buff.size()) {
                    pc = (Integer)ir.args;
                }
                else {
                    throw new Exception("branch out of bounds");
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("BrFalse"))) {
                if(sp.pop() == 0) {
                    if(!(ir.args instanceof Integer)) {
                        throw new Exception("invalid brfalse args");
                    }
                    else if((Integer)ir.args >= 0 && (Integer)ir.args < buff.size()) {
                        pc = (Integer)ir.args;
                    }
                    else {
                        throw new Exception("branch out of bounds");
                    }
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("DefVar"))) {
                if(!(ir.args instanceof String)) {
                    throw new Exception("invalid defvar args");
                }
                else if(ctx.vars.containsKey(ir.args) ||
                        ctx.consts.containsKey(ir.args) ||
                        ctx.procs.containsKey(ir.args)) {
                    throw new Exception("variable redeclared: " + ir.args);
                }
                else {
                    ctx.vars.put((String)ir.args, null);
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("DefLit"))) {
                if(!(ir.args instanceof String) || !(ir.value instanceof Integer)) {
                    throw new Exception("invalid deflit args");
                }
                else if(ctx.vars.containsKey(ir.args) ||
                        ctx.consts.containsKey(ir.args) ||
                        ctx.procs.containsKey(ir.args)) {
                    throw new Exception("variable redeclared: " + ir.args);
                }
                else {
                    ctx.consts.put((String)ir.args, (Integer)ir.value);
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("DefProc"))) {
                if(!(ir.args instanceof String) || !(ir.value instanceof ArrayList)) {
                    throw new Exception("invalid defproc args");
                }
                else if(ctx.vars.containsKey(ir.args) ||
                        ctx.consts.containsKey(ir.args) ||
                        ctx.procs.containsKey(ir.args)) {
                    throw new Exception("variable redeclared: " + ir.args);
                }
                else {
                    ctx.procs.put((String)ir.args, (ArrayList)ir.value);
                }
            }
            else if(ir.op.equals(Ir.irOpCode.get("Input"))){
                Scanner sc = new Scanner(System.in);
                sp.add(sc.nextInt());
            }
            else if(ir.op.equals(Ir.irOpCode.get("Output"))) {
                System.out.println(sp.pop());
            }
            else if(ir.op.equals(Ir.irOpCode.get("Halt"))) {
                break;
            }
            else {
                throw new Exception("invalid instruction");
            }





        }
    }
}