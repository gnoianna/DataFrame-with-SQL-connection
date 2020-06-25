import java.util.ArrayList;

public class Column{

    public Column(String name){
        this.name = new String(name);
    }

    public Column(String name, Class type){
        this.name= new String(name);
        this.type= type;
        this.data= new ArrayList<Value>();
        this.index = 0;
    }
    public Column(Column copy){}

    protected String name;
    protected Class<Value> type;
    public ArrayList<Value> data;
    protected int index;

}