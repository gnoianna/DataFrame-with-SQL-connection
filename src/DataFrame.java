import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataFrame implements Cloneable {

    public ArrayList<Column> tab;
    private int size;

    DataFrame() {
        tab = new ArrayList<Column>();
        size = 0;
    }

    public DataFrame(String inputFile, String[] name) {
        try {
            // Open the file
            FileInputStream fstream = new FileInputStream(inputFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            tab = new ArrayList<Column>();
            size = 0;
            int iter = 0;
            String[] row1 = br.readLine().split(",");
            for (String s : row1) {
                if (s.equals("StringObj")) {
                    tab.add(new Column(name[iter], StringObj.class));
                }
                if (s.equals("IntegerObj")) {
                    tab.add(new Column(name[iter], IntegerObj.class));
                }
                if (s.equals("DoubleObj")) {
                    tab.add(new Column(name[iter], DoubleObj.class));
                }
                if (s.equals("FloatObj")) {
                    tab.add(new Column(name[iter], FloatObj.class));
                }
                iter++;
            }

            while ((strLine = br.readLine()) != null) {
                String[] row = strLine.split(",");
                int itr = 0;
                for (String s : row) {
                    Value v1 = tab.get(itr).type.newInstance();
                    v1.create(s);
                    tab.get(itr).data.add(v1);
                    itr++;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("something went wrong while reading the file!\n" + e.toString());
            System.exit(71830);

        }
    }

    DataFrame(String[] name, Class<? extends Value>[] type) {
        tab = new ArrayList<Column>();
        size = 0;
        int i = 0;
        for (String str : name) {
            tab.add(new Column(str, type[i]));
            i++;
        }
    }

    public int size() {
        if (!tab.isEmpty()) {
            for (Column col : tab) {
                if (col.data != null) {
                    if (size < col.data.size()) {
                        size = col.data.size();
                    }
                }
            }
        }
        return size;

    }

    public Column get(String colName) {
        for (Column col : tab) {
            if (col.name.equals(colName)) {
                return col;
            }
        }
        return null;
    }

    public DataFrame(String[] cols, boolean deepCopy) {
        DataFrame newOne = new DataFrame();
        for (Column col : tab) {
            for (String str : cols)
                if (deepCopy) {
                    if (col.name.equals(str)) {
                        Column newColumn = new Column(col.name, col.type);
                        newColumn.data = (ArrayList) col.data.clone();
                        newOne.tab.add(newColumn);
                    }
                } else {
                    newOne = this;
                }
        }

    }

    public DataFrame iloc(int indexOfRow) {
        DataFrame newOne = new DataFrame();
        this.adjustColumns();
        if (indexOfRow <= this.size()) {
            for (Column col : tab) {
                Column copy = new Column(col.name, col.type);
                copy.data.add(col.data.get(indexOfRow));
                newOne.tab.add(copy);
            }
        }
        return newOne;
    }

    private void adjustColumns() {
        int size = this.size;
        for (Column col : tab) {
            if (col.data.size() < size) {
                col.data.add(null);
            }
        }
    }

    public DataFrame iloc(int from, int to) {
        DataFrame newOne = new DataFrame();
        this.adjustColumns();
        for (Column col : tab) {
            Column copy = new Column(col.name, col.type);
            for (int i = from; i <= to; i++) {
                copy.data.add(col.data.get(i));
            }
            newOne.tab.add(copy);
        }
        return newOne;
    }

    public void print() {
        for (Column col : this.tab) {
            System.out.println("Name of column: " + col.name
                    + "\t\tType: " + col.type
                    + "\t\tDane: " + col.data.toString());
        }
        System.out.print("Number of rows: " + this.size() + "\n\n");

    }

    public GroupedDataFrame groupby(String colname1) {
        GroupedDataFrame grouped = new GroupedDataFrame();
        Column c1 = this.get(colname1);
        ArrayList<String> colNames = new ArrayList<>();
        for (Value row : c1.data) {
            if (!colNames.contains(row.toString())) {
                colNames.add(row.toString());
            }
        }
        int k = 0;
        for (String str : colNames) {
            DataFrame d = new DataFrame();
            for (int i = 0; i < this.tab.size(); i++) {
                d.tab.add(new Column(this.tab.get(i).name, this.tab.get(i).type));
            }
            for (Value row : c1.data) {
                if (row.toString().equals(str)) {
                    d.tab.get(0).data.add(row);
                    for (int j = 1; j < this.tab.size(); j++) {
                        d.tab.get(j).data.add(this.tab.get(j).data.get(k));
                    }
                    k++;
                }

            }
            grouped.GroupedDatas.add(d);

        }
        return grouped;
    }

    public DataFrame clone() {
        DataFrame newDF = new DataFrame();
        newDF.size = this.size;
        for (Column clnFromDF : tab) {
            newDF.tab.add(new Column(clnFromDF.name, clnFromDF.type));
            for (Value v : clnFromDF.data) {
                newDF.get(clnFromDF.name).data.add(v.clone());
            }
        }
        return newDF;
    }
}


