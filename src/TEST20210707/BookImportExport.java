package TEST20210707;

import TEST20210707.Utilities.Stampa;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookImportExport {

    private static final Stampa stampa = new Stampa();
    static Library _library = Library.getBooksLibrary();

    public static void ExportDati() {

        try {
            File file = new File("C:\\ires\\ToDoList\\ToDoList.txt");
            FileWriter fw = new FileWriter(file.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(fw);
            for(Book book: _library.getBookList() ){
                String s = book.exportString();
                bw.write(s);
            }
            bw.flush();
            bw.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImportaDati() {

        stampa.display("Su quale file vuoi fare l'import?");
        String filePath = stampa.Scan();
        Path inputFile = Paths.get( filePath );
        List<List<String>> bookData = new ArrayList<>();

        try( BufferedReader reader = Files.newBufferedReader(inputFile, Charset.defaultCharset()) ){
            String lineaDaFile = "";
            List<String> todoString = new ArrayList<>();
            while ((lineaDaFile = reader.readLine()) != null) {
                if( lineaDaFile.length() == 0 ){
                    bookData.add( todoString );
                    todoString = new ArrayList<>();
                }
                else
                    todoString.add( lineaDaFile );
            }
        }
        catch (NoSuchFileException e){
            stampa.display("Errore, il file non esiste.");
            return;
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        stampa.display("Vuoi sovrascrivere tutti i Libri con quelli importati? (s/n)");
        boolean sovrascrivi = stampa.Scan().toLowerCase().equals("s");
        if( sovrascrivi )
        {Library.clear();}
        System.out.println(String.valueOf(bookData.size()));
        bookData.stream().map( Book::creaBookDaStringa ).forEach( book -> Library.aggiugi(book));
        stampa.spazio();

        stampa.display( "I Libri sono stati importati.\n" );

        Collection<Book> list = _library.getBookList();
        bookData.stream().map( Book::creaBookDaStringa ).forEach( book -> stampa.display(book.prettyPrint()));
    }
}
