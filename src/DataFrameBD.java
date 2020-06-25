
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;


public class DataFrameBD extends DataFrame {
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    public DataFrameBD() {
        super();
    }

    public DataFrameBD(String inputFile, String[] name) {
        super(inputFile, name);
    }

    DataFrameBD(String[] name, Class<? extends Value>[] type) {
        super(name, type);
    }

    public void connect() {
        for (int i = 0; i < 3; i++) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("", "", ""); // <-- TODO
                break;
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void freeUpResources() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
            }
            stmt = null;
        }
    }

    public void DataFrameToSql(String inputFile, String tableName) {
        try {
            connect();
            stmt = conn.createStatement();

            FileInputStream fstream = new FileInputStream(inputFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            String[] colName = new String[]{};
            String[] colType = new String[]{};
            if ((strLine = br.readLine()) != null) {
                colName = strLine.split(",");
            }
            if ((strLine = br.readLine()) != null) {
                colType = strLine.split(",");
            }
            stmt.executeUpdate("CREATE TABLE " + tableName + " (" + colName[0] + " " + colType[0] + ")");

            for (int i = 1; i < colName.length; i++) {
                stmt.executeUpdate(
                        "ALTER TABLE " + tableName + " ADD" + colName[i] + " " + colType[i] + " ;");
            }
            while ((strLine = br.readLine()) != null) {
                stmt.executeUpdate("INSERT INTO " + tableName + " VALUES( " + strLine + " );");
            }

            br.close();

        } catch (Exception e) {
            System.out.println("something went wrong while reading the file!\n" + e.toString());
            System.exit(71830);

        }

    }

    public DataFrame iloc(int from, int to, String tableName) {
        return this.sqlToDataFrame("SELECT * FROM " + tableName + " LIMIT " + (from) + ", " + (to - from));
    }

    public DataFrame sqlToDataFrame(String selectQuery) {
        DataFrame newOne = new DataFrame();
        try {
            connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(selectQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            int CountOfColumn = rsmd.getColumnCount();

            for (int i = 1; i <= CountOfColumn; i++) {

                switch (rsmd.getColumnType(i)) {
                    case 4: //Integer
                        newOne.tab.add(new Column(rsmd.getColumnName(i), IntegerObj.class));
                        break;
                    case 8: //Double
                        newOne.tab.add(new Column(rsmd.getColumnName(i), DoubleObj.class));
                        break;
                    case 12: //String
                        newOne.tab.add(new Column(rsmd.getColumnName(i), StringObj.class));
                        break;
                    default:
                        throw new RuntimeException("Something went wrong while parsing SQL to DF");
                }
            }
            while (rs.next()) {
                for (int j = 1; j <= CountOfColumn; j++) {
                    Value val = newOne.tab.get(j - 1).type.newInstance();
                    val.create(rs.getString(j));
                    newOne.tab.get(j - 1).data.add(val);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            freeUpResources();
        }
        return newOne;
    }

    public GroupedDataFrame groupby(String colname1, String tableName) {
        GroupedDataFrame gd = new GroupedDataFrame();
        try {
            connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName + " " + " GROUP BY " + colname1);

            ArrayList<Integer> arr = new ArrayList<>();
            while(rs.next()) {
                arr.add(Integer.parseInt(rs.getString(1)));
            }

            rs = stmt.executeQuery("SELECT * FROM " + tableName + " " + " ORDER BY " + colname1);
            ResultSetMetaData rsmd = rs.getMetaData();
            int CountOfColumn = rsmd.getColumnCount();

            for(int howMany : arr) {
                DataFrame newOne = new DataFrame();
                for (int i = 1; i <= CountOfColumn; i++) {

                    switch (rsmd.getColumnType(i)) {
                        case 4: //Integer
                            newOne.tab.add(new Column(rsmd.getColumnName(i), IntegerObj.class));
                            break;
                        case 8: //Double
                            newOne.tab.add(new Column(rsmd.getColumnName(i), DoubleObj.class));
                            break;
                        case 12: //String
                            newOne.tab.add(new Column(rsmd.getColumnName(i), StringObj.class));
                            break;
                        default:
                            throw new RuntimeException("Something went wrong while parsing SQL to DF");
                    }
                }
                int i = 0;
                while (i < howMany) {
                    rs.next();
                    for (int j = 1; j <= CountOfColumn; j++) {
                        Value val = newOne.tab.get(j - 1).type.newInstance();
                        val.create(rs.getString(j));
                        newOne.tab.get(j - 1).data.add(val);
                    }
                    i++;
                }
                gd.GroupedDatas.add(newOne);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            freeUpResources();
        }
        return gd;
    }

}