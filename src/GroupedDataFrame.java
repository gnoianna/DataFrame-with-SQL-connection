import java.util.LinkedList;

public class GroupedDataFrame implements GroupBy {

    LinkedList<DataFrame> GroupedDatas;

    GroupedDataFrame(){
        GroupedDatas = new LinkedList<>();
    }

    public void print(){
        for(DataFrame data : GroupedDatas){
            data.print();
        }
    }

    @Override
    public DataFrame max(){
        DataFrame MaxValues = new DataFrame();

        try {
            for (DataFrame data : GroupedDatas) {
                MaxValues.tab.add(new Column("MaxValues poszczególnych kolumn dla Daty " + (GroupedDatas.indexOf(data) + 1), Value.class));
                for (Column col : data.tab) {
                    if (!(col.type.equals("string"))) {
                        Value maxValue = col.data.get(0);
                        for (Value obj : col.data) {
                            if (maxValue.lte(obj)) {
                                maxValue = obj;
                            }
                        }
                        MaxValues.tab.get(GroupedDatas.indexOf(data)).data.add(maxValue);
                    }
                }
            }
        } catch (Exception e){
            System.out.print(e.toString());
        }
        return MaxValues;
    }

    @Override
    public DataFrame min(){
        DataFrame MinValues = new DataFrame();

        try {
            for (DataFrame data : GroupedDatas) {
                MinValues.tab.add(new Column("MinValues poszczegolnych kolumn dla Daty " + (GroupedDatas.indexOf(data) + 1), Value.class));
                for (Column col : data.tab) {
                    if (!(col.type.equals("string"))) {
                        Value minValue = col.data.get(0);
                        for (Value obj : col.data) {
                            if (minValue.gte(obj)) {
                                minValue = obj;
                            }
                        }
                        MinValues.tab.get(GroupedDatas.indexOf(data)).data.add(minValue);
                    }
                }
            }
        } catch (Exception e){
            System.out.print(e.toString());
        }
        return MinValues;

    }

    @Override
    public DataFrame mean() {

        DataFrame MeanValues = new DataFrame();

        try {
            for (DataFrame data : GroupedDatas) {
                MeanValues.tab.add(new Column("MeanValues poszczegóolnych kolumn dla Daty " + (GroupedDatas.indexOf(data) + 1), DoubleObj.class));
                for (Column col : data.tab) {
                    double meanValue = 0;
                    for (Value obj : col.data) {
                        if (obj instanceof IntegerObj) {
                            meanValue += ((IntegerObj) obj).value.doubleValue();
                        }
                        if (obj instanceof FloatObj) {
                            meanValue += ((FloatObj) obj).value.doubleValue();
                        }
                        if (obj instanceof DoubleObj) {
                            meanValue += ((DoubleObj) obj).value;
                        }

                    }
                    double meanValue2 = meanValue / col.data.size();
                    MeanValues.tab.get(GroupedDatas.indexOf(data)).data.add(new DoubleObj(meanValue2));

                }
            }
        } catch (Exception e){
            System.out.print(e.toString());
        }
        return MeanValues;

    }

    @Override
    public DataFrame sum(){
        DataFrame Sum = new DataFrame();

        try {
            for (DataFrame data : GroupedDatas) {
                Sum.tab.add(new Column("Sumy poszczegolnych kolumn dla Daty " + (GroupedDatas.indexOf(data) + 1), DoubleObj.class));
                for (Column col : data.tab) {
                    double sumValue = 0;
                    for (Value obj : col.data) {
                        if (obj instanceof IntegerObj) {
                            sumValue += ((IntegerObj) obj).value.doubleValue();
                        }
                        if (obj instanceof FloatObj) {
                            sumValue += ((FloatObj) obj).value.doubleValue();
                        }
                        if (obj instanceof DoubleObj) {
                            sumValue += ((DoubleObj) obj).value;
                        }

                    }
                    Sum.tab.get(GroupedDatas.indexOf(data)).data.add(new DoubleObj(sumValue));

                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return Sum;
    }

    @Override
    public DataFrame std(){
        DataFrame StdValues = new DataFrame();

        try {
            for (DataFrame data : GroupedDatas) {
                StdValues.tab.add(new Column("Std poszczegolnych kolumn dla Daty " + (GroupedDatas.indexOf(data) + 1), DoubleObj.class));
                int k = 0;

                for (Column col : data.tab) {
                    double std = 0;
                    double licznik = 0;

                    for (Value obj : col.data) {
                        if (obj instanceof IntegerObj) {
                            std += Math.pow(((IntegerObj) obj).value.doubleValue() - ((DoubleObj) this.mean().tab.get(GroupedDatas.indexOf(data)).data.get(k)).value, 2);
                        }
                        if (obj instanceof FloatObj) {
                            std += Math.pow(((FloatObj) obj).value.doubleValue() - ((DoubleObj) this.mean().tab.get(GroupedDatas.indexOf(data)).data.get(k)).value, 2);
                        }
                        if (obj instanceof DoubleObj) {
                            std += Math.pow(((DoubleObj) obj).value - ((DoubleObj) this.mean().tab.get(GroupedDatas.indexOf(data)).data.get(k)).value, 2);
                        }
                        licznik++;

                    }
                    StdValues.tab.get(GroupedDatas.indexOf(data)).data.add(new DoubleObj(std / licznik));
                    k++;
                }

            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return StdValues;

    }

    @Override
    public DataFrame var(){
        DataFrame VarValues = new DataFrame();
        int t = 0;

        try {
            for (Column col : this.std().tab) {
                VarValues.tab.add(new Column("Var poszczegolnych kolumn dla Daty " + (t + 1), DoubleObj.class));
                for (Value obj : col.data) {
                    VarValues.tab.get(t).data.add(new DoubleObj(Math.sqrt(((DoubleObj) obj).value)));
                }
                t++;
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return VarValues;

    }
}
