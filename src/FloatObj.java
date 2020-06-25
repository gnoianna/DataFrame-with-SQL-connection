
public class FloatObj extends Value {
    public Float value;

    FloatObj(){
        super();
        value = (float) 0;
    }

    public FloatObj clone(){
        FloatObj copy = new FloatObj();
        copy.value= this.value;
        return copy;
    }

    FloatObj(float val){
        super();
        value = val;
    }

    public String toString() {
        return value.toString();
    }

    public Value add(Value v) {
        if(v instanceof FloatObj){
            value += ((FloatObj) v).value;
        }
        return this;
    }

    public Value sub(Value v) {
        if(v instanceof FloatObj){
            value -= ((FloatObj) v).value;
        }
        return this;
    }

    public Value mul(Value v) {
        if(v instanceof FloatObj){
            value *= ((FloatObj) v).value;
        }
        return this;
    }

    public Value div(Value v) {
        if(v instanceof FloatObj){
            value /= ((FloatObj) v).value;
        }
        return this;
    }

    public Value pow(Value v) {
        if(v instanceof IntegerObj){
            this.value = (float) Math.pow(this.value, ((FloatObj) v).value);
        }
        return this;
    }

    public boolean eq(Value v){
        if(v instanceof FloatObj){
            return this.value.equals(((FloatObj) v).value);
        }
        return false;
    }

    public boolean lte(Value v){
        if(v instanceof IntegerObj){
            return value.intValue() <= ((IntegerObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value <= ((FloatObj) v).value;
        }
        if(v instanceof DoubleObj){
            return value.doubleValue() <= ((DoubleObj) v).value;
        }
        return false;
    }

    public boolean gte(Value v){
        if(v instanceof IntegerObj){
            return value.intValue() >= ((IntegerObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value >= ((FloatObj) v).value;
        }
        if(v instanceof DoubleObj){
            return value.doubleValue() >= ((DoubleObj) v).value;
        }
        return false;
    }

    public boolean neq(Value v){
        return value != ((FloatObj) v).value;
    }

    public boolean equals(Value other){
        if(other instanceof FloatObj){
            return this.value.equals(((FloatObj) other).value);
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
        value = Float.parseFloat(s);
        return this;
    }

}
