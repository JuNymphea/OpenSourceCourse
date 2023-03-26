public class Const {
    String name;
    int value;

    public Const(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Const( name = " + this.name + " , value = " + this.value + " )";
    }
}
