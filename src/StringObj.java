import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class StringObj extends Value {
    String value;

    StringObj(String val) {
        this.value = val;
    }

    StringObj() {
        value = "Elo";
    }

    public StringObj clone() {
        StringObj copy = new StringObj();
        copy.value = this.value;
        return copy;
    }

    public String toString() {
        return value;
    }

    public Value add(Value v) {
        value = new String(this.value + v.toString());
        return this;
    }

    public Value sub(Value v) {
        throw new NotImplementedException();
    }

    public Value mul(Value v) {
        throw new NotImplementedException();
    }

    public Value div(Value v) {
        throw new NotImplementedException();
    }

    public Value pow(Value v) {
        throw new NotImplementedException();
    }

    public boolean eq(Value v) {
        boolean ret = false;
        if (v instanceof StringObj) {
            ret = value.equals(((StringObj) v).value);
        } else {
            throw new IllegalArgumentException();
        }
        return ret;
    }

    public boolean lte(Value v) {
        boolean ret = false;
        if (v instanceof StringObj) {
            ret = (value.length() <= ((StringObj) v).value.length());
        } else {
            throw new IllegalArgumentException();
        }
        return ret;
    }

    public boolean gte(Value v) {
        boolean ret = false;
        if (v instanceof StringObj) {
            ret = (value.length() >= ((StringObj) v).value.length());
        } else {
            throw new IllegalArgumentException();
        }
        return ret;
    }

    public boolean neq(Value v) {
        return !this.eq(v);
    }

    public boolean equals(Value other) {
        return value.equals(other.toString());
    }

    public int hashCode() {
        return value.hashCode();
    }

    public Value create(String s) {
        value = new String(s);
        return this;
    }
}