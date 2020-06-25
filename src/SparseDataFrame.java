public class SparseDataFrame extends DataFrame {
    Value hide;

    public SparseDataFrame(String[] name, Class<? extends Value>[] type, Value hide){
        super(name, type);
        this.hide=hide;
    }

    public void add(String colName, Value value){
        if(!value.equals(hide)){
            COOValue obj = new COOValue(this.get(colName).index, value);
            this.get(colName).data.add(obj);
        }
        this.get(colName).index++;
    }

    public void add(int index, Value value){
        if(!value.equals(hide)){
            COOValue obj = new COOValue(this.tab.get(index).index, value);
            this.tab.get(index).data.add(obj);
        }
        this.tab.get(index).index++;
    }

    public void print(){
        for( Column cln : this.tab){
            System.out.print("\nName of column: " + cln.name +
                    "\t\tType of column: " + cln.type +
                    "\t\tDane: ");
            for(Object coo: cln.data){
                COOValue value= (COOValue) coo;
                System.out.print("("+((COOValue) coo).index + "," + ((COOValue) coo).value + ")");
            }
        }
        System.out.println("\nNumber of rows: " + this.size());
        System.out.println("\n");
    }
    public DataFrame toDense(){
        DataFrame newOne = new DataFrame();
        for(Column col : this.tab){
            Column copy = new Column(col.name, col.type);
            if(col.data.size() == 0){
                while(copy.data.size() < col.index){
                    copy.data.add(hide);
                }
            }else{
                for(Object coo : col.data){
                    COOValue value = (COOValue) coo;
                    while(copy.data.size() < value.index){
                        copy.data.add(hide);
                    }
                    if(copy.data.size() == value.index){
                        copy.data.add(value.value);
                    }
                }
            }
            while(copy.data.size() < col.index){
                copy.data.add(hide);
            }

            newOne.tab.add(copy);
        }
        return newOne;
    }

}
