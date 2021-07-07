package TEST20210707;

import TEST20210707.Utilities.Stampa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Book {

    protected enum genre{FANTASY, HORROR, NOVEL, LITERATURE};
    protected enum personalJudgment{BAD, MMMH, GOOD, EXCELLENT,NONE};
    protected enum readingAdvantage{TWENTYFIVE, FIFTY, SEVENTYFIVE, ONEHOUNDRED, NOT_READ};

    public final static Stampa stampa = new Stampa();
    public final static String dateFormat = "dd-MM-yyyy HH:mm";

    private String title;
    private String author;
    private String sinoxis;
    private String IBSN;
    private LocalDateTime pubblicationDate;
    private genre genre;
    private personalJudgment personalJudgment;
    private readingAdvantage readingAdvantage;

    public Book(){}

    public String exportString(){
        return String.format("pubblication date:%s\ntitle:%s\nsinoxis:%s\ngenre:%s\npersonal judgment:%s\nreading advantage:%s\nauthor:%S\n",
                formatDate(pubblicationDate), title, sinoxis, genre.name(), personalJudgment.name(), readingAdvantage.name(),author);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        author = author;
    }

    public String getSinoxis() {
        return sinoxis;
    }

    public void setSinoxis(String sinoxis) {
        sinoxis = sinoxis;
    }

    public String getIBSN() {
        return IBSN;
    }

    public void setIBSN(String IBSN) {
        this.IBSN = IBSN;
    }

    public LocalDateTime getPubblicationDate() {
        return pubblicationDate;
    }

    public void setPubblicationDate(LocalDateTime pubblicationDate) {
        this.pubblicationDate = pubblicationDate;
    }

    public void setPubblicationDate(String s){
        LocalDateTime ldt = getDateDaStringa( s );
        this.pubblicationDate = ldt;
    }

    public Book.genre getGenre() {
        return genre;
    }

    public void setGenre(Book.genre genre) {
        this.genre = genre;
    }

    public Book.personalJudgment getPersonalJudgment() {
        return personalJudgment;
    }

    public void setPersonalJudgment(Book.personalJudgment personalJudgment) {
        this.personalJudgment = personalJudgment;
    }

    public Book.readingAdvantage getReadingAdvantage() {
        return readingAdvantage;
    }

    public void setReadingAdvantage(Book.readingAdvantage readingAdvantage) {
        this.readingAdvantage = readingAdvantage;
    }

    public static String formatDate(LocalDateTime date){
        return date.format( getDateFomatter() ).toString();
    }

    public static DateTimeFormatter getDateFomatter(){
        return DateTimeFormatter.ofPattern(dateFormat);
    }

    public String prettyPrint() {
        return String.format(
                "title: %s, sinoxis: %s, author: %s, IBSN: %s, genre: %s, pubblication date: %s\n",
                title, sinoxis, author, IBSN, genre.name(), formatDate(pubblicationDate)
        );
    }

    private static LocalDateTime getDateDaStringa(String s){
        LocalDateTime ldt = null;
        try {
            ldt = LocalDateTime.parse(s, getDateFomatter());
        }
        catch ( DateTimeParseException e ){
            stampa.display("Errore durante l'importazione del file. La data inserita non è valida.");
        }
        return ldt;
    }

    public static Book creaBookDaStringa(List<String> stringsList){

        Book b = new Book();
        for(String s : stringsList){
            int separator = s.indexOf(":");
            String key = s.substring(0,separator);
            String value = s.substring(separator + 1);
            switch (key){
                case "IBSN": b.setIBSN(value);
                    break;
                case "author": b.setAuthor(value);
                    break;
                case "pubblication date": b.setPubblicationDate(value);
                    break;
                case "title": b.setTitle(value);
                    break;
                case "sinoxis": b.setSinoxis(value);
                    break;
                case "genre":
                    Book.genre genre = b.genre.valueOf(value.toUpperCase());
                    b.setGenre(genre);
                    break;
                case "personal judgment":
                    Book.personalJudgment judgment = b.personalJudgment.valueOf(value.toUpperCase());
                    b.setPersonalJudgment(judgment);
                    break;
                case "reading advantage":
                    Book.readingAdvantage readingAdvantage = b.readingAdvantage.valueOf(value.toUpperCase());
                    b.setReadingAdvantage(readingAdvantage);
                default:
                    stampa.display("Errore, chiave non valida");
            }
        }
        return b;
        // in questo metodo considero la lista che mi arriva dal txt come fosse una hash anche se non lo è realmente,
        // infatti io scrivo come riferimento un titolo: .... ecc ecc e mi conviene usare la stessa impostazione mentale di
        // chiave e valore come la hashmap per "separare" i dati che mi serve pescare per costruire il toDo.
    }

    public static Book cloneForUpdate(Book daCopiare) {
        Book book = daCopiare;
        return book;
    }

}
