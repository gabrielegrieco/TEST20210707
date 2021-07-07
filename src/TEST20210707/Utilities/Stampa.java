package TEST20210707.Utilities;

import java.util.Scanner;

public class Stampa {

    public void spazio(){
        System.out.println("");
    }

    public void display(String printLine){ System.out.println(printLine);}

    public void stampaTitolo(){
        System.out.println("--------------");
        System.out.println("---Library---");
        System.out.println("--------------");
    }

    public String Scan() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
