import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class COOValue extends Value {

    private COOValue() {
    }

    COOValue(int index, Value value){
        this.index = index;
        this.value = value;
    }

    int index;
    Value value;

    public String toString() {
        return new String("(" + index + "," + value.toString() + ") ");
    }

    public COOValue clone() {
        COOValue copy = new COOValue();
        copy.value = this.value.clone();
        copy.index = this.index;
        return copy;
    }


    public Value add(Value v) {
        value.add(v);
        return this;
    }

    public Value sub(Value v) {
        value.sub(v);
        return this;
    }

    public Value mul(Value v) {
        value.mul(v);
        return this;
    }

    public Value div(Value v) {
        value.div(v);
        return this;
    }

    public Value pow(Value v) {
        value.pow(v);
        return this;
    }

    public boolean eq(Value v) {
        return value.eq(v);
    }

    public boolean lte(Value v) {
        return value.lte(v);
    }

    public boolean gte(Value v) {
        return value.gte(v);
    }

    public boolean neq(Value v) {
        return value.neq(v);
    }

    public boolean equals(Value other) {
        return value.equals(other);
    }

    public int hashCode() {
        return this.hashCode();
    }

    public Value create(String s) {
        throw new NotImplementedException();
    }
}

