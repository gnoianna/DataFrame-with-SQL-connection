
public class DoubleObj extends Value {
    public Double value;

    DoubleObj(){
        super();
        value = 0.0;
    }

    public DoubleObj clone(){
        DoubleObj copy = new DoubleObj();
        copy.value= this.value;
        return copy;
    }

    DoubleObj(double val){
        super();
        value = val;
    }

    public String toString() {
        return value.toString();
    }

    public Value add(Value v) {
        if(v instanceof DoubleObj){
            value += ((DoubleObj) v).value;
        }
        return this;
    }

    public Value sub(Value v) {
        if(v instanceof DoubleObj){
            value -= ((DoubleObj) v).value;
        }
        return this;
    }

    public Value mul(Value v) {
        if(v instanceof DoubleObj){
            value *= ((DoubleObj) v).value;
        }
        return this;
    }

    public Value div(Value v) {
        if(v instanceof DoubleObj){
            value /= ((DoubleObj) v).value;
        }
        return this;
    }

    public Value pow(Value v) {
        if(v instanceof DoubleObj){
            this.value = Math.pow(this.value, ((DoubleObj) v).value);
        }
        return this;
    }

    public boolean eq(Value v){
        return value == ((DoubleObj) v).value;
    }

    public boolean lte(Value v){
        if(v instanceof IntegerObj){
            return value.intValue() <= ((IntegerObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value.floatValue() <= ((FloatObj) v).value;
        }
        if(v instanceof DoubleObj){
            return value <= ((DoubleObj) v).value;
        }
        return false;
    }

    public boolean gte(Value v){
        if(v instanceof IntegerObj){
            return value.intValue() >= ((IntegerObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value.floatValue() >= ((FloatObj) v).value;
        }
        if(v instanceof DoubleObj){
            return value >= ((DoubleObj) v).value;
        }
        return false;
    }

    public boolean neq(Value v){
        if(v instanceof IntegerObj){
            return value.intValue() != ((IntegerObj) v).value;
        }
        if(v instanceof FloatObj) {
            return value.floatValue() != ((FloatObj) v).value;
        }
        if(v instanceof DoubleObj){
            return value != ((DoubleObj) v).value;
        }
        return false;
    }

    public boolean equals(Value v){
        if(v instanceof DoubleObj){
            return this.value.equals(((DoubleObj) v).value);
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
        value = Double.parseDouble(s);
        return this;
    }

}
