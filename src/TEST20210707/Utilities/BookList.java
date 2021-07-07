package TEST20210707.Utilities;

import TEST20210707.Book;
import TEST20210707.Library;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class BookList {
    static Stampa stampa = new Stampa();

    public static void visualizzaLista( String msg, Function<Book, Comparable> field ) {
        stampa.display(msg);
        List<Book> sortedList = Library.getBookList();
        Comparator<Book> comparator = Comparator.comparing( field );
        sortedList.sort( comparator );
        prettyPrint(sortedList);
    }

    private static void prettyPrint(List<Book> sortedList) {
        sortedList.stream().map( Book::prettyPrint ).forEach(stampa::display);
    }

    public static void visualizzaPerOrdineAlfabetico() {
        visualizzaLista("ordino la lista per ordine alfabetico\n", Book::getTitle);
    }
    public static void visualizzaPerData(){
        visualizzaLista("ordino la lista per data\n", Book::getPubblicationDate);
    }
    public static void visualizzaPer(){
        visualizzaLista("ordino lista per IBSN\n", Book::getIBSN);
    }
}
