
public class IntegerObj extends Value {
    public Integer value;

    IntegerObj(){
        super();
        value = 0;
    }

    public IntegerObj clone(){
        IntegerObj copy = new IntegerObj();
        copy.value=this.value;
        return copy;
    }

    IntegerObj(int val){
        super();
        value = val;
    }

    public String toString() {
        return value.toString();
    }

    public Value add(Value v) {
        if(v instanceof IntegerObj){
            value += ((IntegerObj) v).value;
        }
        return this;
    }

    public Value sub(Value v) {
        if(v instanceof IntegerObj){
            value -= ((IntegerObj) v).value;
        }
        return this;
    }

    public Value mul(Value v) {
        if(v instanceof IntegerObj){
            value *= ((IntegerObj) v).value;
        }
        return this;
    }

    public Value div(Value v) {
        if(v instanceof IntegerObj){
            value /= ((IntegerObj) v).value;
        }
        return this;
    }

    public Value pow(Value v) {
        if(v instanceof IntegerObj){
            this.value = (int) Math.pow(this.value, ((IntegerObj) v).value);
        }
        return this;
    }

    public boolean eq(Value v){
        return value == ((IntegerObj) v).value;
    }

    public boolean lte(Value v){
        if(v instanceof DoubleObj){
            return value.doubleValue() <= ((DoubleObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value.floatValue() <= ((DoubleObj) v).value;
        }
        if(v instanceof IntegerObj){
            return value <= ((IntegerObj) v).value;
        }
        return false;
    }

    public boolean gte(Value v){
        if(v instanceof DoubleObj){
            return value.doubleValue() >= ((DoubleObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value.floatValue() >= ((DoubleObj) v).value;
        }
        if(v instanceof IntegerObj){
            return value >= ((IntegerObj) v).value;
        }
        return false;
    }

    public boolean neq(Value v){
        if(v instanceof DoubleObj){
            return value.doubleValue() != ((DoubleObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value.floatValue() != ((DoubleObj) v).value;
        }
        if(v instanceof IntegerObj){
            return value != ((IntegerObj) v).value;
        }
        return false;
    }

    public boolean equals(Value other){
        if(other instanceof IntegerObj){
            return this.value.equals(((IntegerObj) other).value);
        }
        return false;
    }

    public int hashCode(){
        return value.hashCode();
    }

    public void print(){
        System.out.print(value);
    }

    public Value create(String s){
        value = Integer.parseInt(s);
        return this;
    }

}
