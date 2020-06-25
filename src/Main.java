import javax.xml.crypto.Data;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /** Caly projekt ma na celu odwzorowanie tabeli 'DataFrame' skladajacej sie z roznego rodzaju obiektow: StringObj, IntegerObj, DoubleObj, FloatObj.
         * Program ma tworzyc DataFrame, skladajaca sie z kolumn poszczegolnych typow. Istnieje mozliwosc stworzenia GroupedDataFrame - grupujemy wtedy po jednej
         * z kolumn tworzac nowe DataFrame'y. Co wiecej dla kazdej z kolumn (jesli nie jest typu StringObj) mozemy obliczyc max, min, std, var, sum, mean.
         * Istnieje tez szansa polaczenia sie z baza danych, mozna wtedy za pomoca gotowego DataFrame'a stworzyc baze 'DataFrameToSql, albo sciagajac dane z bazy
         * utworzyc DataFrame - funkcja sqlToDataFrame. Mamy tez szanse na stworzenie SparseDataFrame, wyznaczamy element ktory chcemy ukryc.
         */

        /** Testowanie groupby **/
        IntegerObj i1= new IntegerObj(5);
        IntegerObj i2= new IntegerObj(2);
        IntegerObj i3= new IntegerObj(3);
        IntegerObj i4= new IntegerObj(6);
        IntegerObj i5= new IntegerObj(8);
        IntegerObj i6= new IntegerObj(1);

        DataFrame d1= new DataFrame(new String[]{"col1", "col2"}, new Class[]{IntegerObj.class, IntegerObj.class});
        d1.tab.get(0).data.add(i1);
        d1.tab.get(1).data.add(i2);
        d1.tab.get(0).data.add(i3);
        d1.tab.get(1).data.add(i4);
        d1.tab.get(0).data.add(i5);
        d1.tab.get(1).data.add(i6);
        System.out.println("Przykladowy DataFrame: \n");
        d1.print();

        DataFrame d2 = new DataFrame("plik.txt", new String[]{"col1", "col2", "col3"});
        System.out.println("DataFrame przed grupowaniem: \n");
        d2.print();
        GroupedDataFrame g1 = d2.groupby("col1");
        System.out.println("DataFrame po grupowaniu: \n");
        g1.print();
        DataFrame dd = g1.std();
        System.out.println("Std dla poszczegolnych DataFrame'ow: \n");
        dd.print();

        //DataFrameBD df= new DataFrameBD();
        //DataFrame df2= df.sqlToDataFrame("select author, year from books");
        //df2.print();
        //df.DataFrameToSql("bazy.txt", "test");

        /** Test SparseDataFrame **/
        IntegerObj ii1= new IntegerObj();
        ii1.create("2");
        IntegerObj ii2= new IntegerObj(3);
        IntegerObj ii3= new IntegerObj(4);
        IntegerObj ii4= new IntegerObj(5);
        IntegerObj ii5= new IntegerObj(6);


        SparseDataFrame s1 = new SparseDataFrame(new String[]{"col1", "col2"}, new Class[]{IntegerObj.class, IntegerObj.class}, ii1);
        s1.add("col1", ii1);
        s1.add("col1", ii2);
        s1.add("col1", ii3);
        s1.add("col1", ii4);

        s1.add("col2", ii2);
        s1.add("col2", ii3);
        s1.add("col2", ii4);
        s1.add("col2", ii5);

        System.out.println("SparseDataFrame z ukrytym elementem: ");
        s1.print(); //(index wiersza, wartosc)

        DataFrame dd1 = s1.toDense();
        System.out.println("DataFrame odzyskany ze SparseDataFrame: \n");
        dd1.print();
    }

}