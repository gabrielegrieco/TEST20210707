package TEST20210707;

import TEST20210707.Utilities.Stampa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BookManager {

    static Stampa stampa = new Stampa();
    static Library library = Library.getBooksLibrary();

    public static void createNewBook() {
        Book book = new Book();
        askForABook(book, false);
        library.aggiugi(book);
    }

    public static void removeBook(){
        stampa.display("inserisci l' ID del Libro da eliminare.");
        Long ID = Long.parseLong(stampa.Scan());
        if(!Library.has(ID)){
            stampa.display("il Libro non esiste");
        }
        else{
            Book book = Library.getBook(ID);
            stampa.display(book.getTitle());
            stampa.display("vuoi procedere nell' eliminazione di questo Libro? s/n");
            String risp = stampa.Scan();
            if(risp.toLowerCase().equals("s"))
                return;
            library.delete(ID);
            stampa.display("Il Libro è stato eliminato");
        }
    }

    public void modify(Book t, boolean aggiornato){
        String input;


        if(aggiornato){
            stampa.display("Inserisci i dati, lascia il campo vuoto se vuoi mantenere i dati originali");
        }else{
            stampa.display("inserisci i dati:");

            stampa.display("titolo");
            input = stampa.Scan();
            if(!(aggiornato && input.isEmpty()) )
                t.setTitle(input);
            if(!(aggiornato && input.isEmpty()) )
                t.setSinoxis(input);

            Book.genre genre = requestGenre(t, aggiornato);

            t.setGenre(genre);

            Book.personalJudgment judgment = requestJudgment(t, aggiornato);

            t.setPersonalJudgment(judgment);

            stampa.display("Data (" + Book.dateFormat + "): " );
            LocalDateTime dataPubblicazione = requestDate(Book.getDateFomatter(), aggiornato);
            if(dataPubblicazione != null){
                t.setPubblicationDate(dataPubblicazione);
            }
        }
    }

    public static void updateBook(){
        Book book = new Book();
        askForABook( book, false );
        stampa.display( book.prettyPrint() );
        stampa.display("Vuoi creare questo libro? (s/n)");
        String input = stampa.Scan();
        if( input.toLowerCase().equals("s") ) {
            Library.aggiugi(book);
            stampa.display("Il ToDo è stato aggiunto.\n");
        }
    }

    private static void askForABook( Book book, boolean isEdit ){
        String input;
        if( isEdit )
            stampa.display("Inserisci i dati, lascia il campo vuoto se vuoi mantenere gli originali:");
        else
            stampa.display("Inserisci i dati:");

        stampa.display("Titolo: ");
        input = stampa.Scan();
        if( !(isEdit && input.isEmpty()) )
            book.setTitle( input );

        stampa.display("Descrizione:  ");
        input = stampa.Scan();
        if( !(isEdit && input.isEmpty()) )
            book.setSinoxis( input );

        Book.genre genre = requestGenre( book, isEdit );
        book.setGenre( genre );

        // da gestire il giudizio per ...
        stampa.display("hai letto tutto il libro?");
        stampa.display("inserisci : 1: true se lo hai letto, 2: false se non lo hai ancora finito");
        String s = stampa.Scan();
        boolean b = true;
        if(s == "2"){
            b = false;
        }
        Book.readingAdvantage readingAdvantage = requestReadingAdvantage(book, isEdit);
        book.setReadingAdvantage(readingAdvantage);
        Book.personalJudgment judgment = requestJudgment( book, isEdit, b );
        book.setPersonalJudgment( judgment );
        if(book.getReadingAdvantage() == Book.readingAdvantage.NOT_READ)
        {
            stampa.display("non puoi dare un giudizio perchè non hai letto il libro");
            book.setPersonalJudgment( Book.personalJudgment.NONE);
        }

        // da gestire l'avanzamento lettura


        stampa.display( "Data (" + Book.dateFormat + "): " );

        LocalDateTime date = requestDate(  Book.getDateFomatter(), isEdit );
        if( date != null )
            book.setPubblicationDate( date );
    }

    private static Book.readingAdvantage requestReadingAdvantage( Book book, boolean isEdit ){
        Book.readingAdvantage readingAdvantage = null;
        boolean valid;
        do {
            valid = true;
            stampa.display( "avanzamento lettura (1: TWENTYFIVE, 2: FIFTY, 3: SEVENTYFIVE, 4: ONEHOUNDRED, 5: NOT_READ): " );
            String input = stampa.Scan();
            switch (input) {
                case "1": readingAdvantage = Book.readingAdvantage.TWENTYFIVE; break;
                case "2": readingAdvantage = Book.readingAdvantage.FIFTY; break;
                case "3": readingAdvantage = Book.readingAdvantage.SEVENTYFIVE; break;
                case "4": readingAdvantage = Book.readingAdvantage.ONEHOUNDRED; break;
                case "5": readingAdvantage = Book.readingAdvantage.NOT_READ; break;
                case "":
                    if( isEdit ){
                        readingAdvantage = book.getReadingAdvantage();
                        break;
                    }
                default:
                    stampa.display("Errore, scelta non valida, riprova.");
                    valid = false;
            }
        }while( !valid );

        return readingAdvantage;
    }

    public static LocalDateTime requestDate(DateTimeFormatter dtf, boolean allowBlank ){
        LocalDateTime result = null;
        boolean valid = false;
        do {
            String str = stampa.Scan();
            try {
                result = LocalDateTime.parse(str, dtf);
            }
            catch ( DateTimeParseException e ){
                if( allowBlank && str.isEmpty() )
                    valid = true;
                else {
                    stampa.display("Errore, la stringa inserita non è una data, riprova.");
                    continue;
                }
            }
            valid = true;
        }while(!valid);
        return result;
    }

    private static Book.personalJudgment requestJudgment( Book book, boolean isEdit ){
        Book.personalJudgment personalJudgment = null;
        boolean valid;
            do {
            valid = true;
            stampa.display( "Priorità (1: BAD, 2: MMMH, 3: GOOD, 4: EXCELLENT): " );
            String input = stampa.Scan();
            switch (input) {
                case "1": personalJudgment = Book.personalJudgment.BAD; break;
                case "2": personalJudgment = Book.personalJudgment.MMMH; break;
                case "3": personalJudgment = Book.personalJudgment.GOOD; break;
                case "4": personalJudgment = Book.personalJudgment.EXCELLENT; break;
                case "":
                    if( isEdit ){
                        personalJudgment = book.getPersonalJudgment();
                        break;
                    }
                default:
                    stampa.display("Errore, la priorità non è valida, riprova.");
                    valid = false;
            }
        }while( !valid );

        return personalJudgment;
    }

    private static Book.personalJudgment requestJudgment( Book book, boolean isEdit, boolean isRead ){
        Book.personalJudgment personalJudgment = null;
        boolean valid;
        if(isRead ){
            do {
                valid = true;
                stampa.display( "Priorità (1: BAD, 2: MMMH, 3: GOOD, 4: EXCELLENT): " );
                String input = stampa.Scan();
                switch (input) {
                    case "1": personalJudgment = Book.personalJudgment.BAD; break;
                    case "2": personalJudgment = Book.personalJudgment.MMMH; break;
                    case "3": personalJudgment = Book.personalJudgment.GOOD; break;
                    case "4": personalJudgment = Book.personalJudgment.EXCELLENT; break;
                    case "":
                        if( isEdit ){
                            personalJudgment = book.getPersonalJudgment();
                            break;
                        }
                    default:
                        stampa.display("Errore, la priorità non è valida, riprova.");
                        valid = false;
                }
            }while( !valid );
        }
        else{
            stampa.display("prima di dare un giudizio leggi il libro");
        }

        return personalJudgment;
    }

    private static Book.genre requestGenre( Book t, boolean isEdit ){
        Book.genre genre = null;
        boolean valid;
        do {
            valid = true;
            stampa.display( "Stato (1: FANTASY, 2: HORROR, 3: NOVEL, 4: LITERATURE): " );
            String input = stampa.Scan();
            switch (input) {
                case "1": genre = Book.genre.FANTASY; break;
                case "2": genre = Book.genre.HORROR; break;
                case "3": genre = Book.genre.NOVEL; break;
                case "4": genre = Book.genre.LITERATURE; break;
                case "":
                    if( isEdit ){
                        genre = t.getGenre();
                        break;
                    }
                default:
                    stampa.display("Errore, il genere non è presente, riprova.");
                    valid = false;
            }
        }while( !valid );

        return genre;
    }

}
