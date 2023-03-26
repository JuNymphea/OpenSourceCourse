import java.util.Map;
import java.util.HashMap;

public class Token<T> {

    public final static Map TokenKind = new HashMap();
    static {
        TokenKind.put(0, "op");
        TokenKind.put(1, "Num");
        TokenKind.put(2, "Name");
        TokenKind.put(3, "Keyword");
        TokenKind.put(4, "Eof");
    }

    int ty;

    T val;

    public Token(int ty, T val){
        this.ty = ty;
        this.val = val;
    }
    @Override
    public String toString() {
        return "type: " + this.TokenKind.get(this.ty) + " | val: " + this.val;
    }


    public Token op() {
        return this;
    }

    public Token num() {
        return this;
    }

    public Token name() {
        return this;
    }

    public Token keyword() {
        return this;
    }

    public Token eof() {
        return this;
    }


}
