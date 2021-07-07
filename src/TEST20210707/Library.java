package TEST20210707;

import TEST20210707.Utilities.Stampa;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private static final Stampa stampa = new Stampa();
    private static Library _library = null;
    Map<Long, Book> _data = new HashMap<>();
    private long IDKey;
    private long IDSeed = 0;

    private Library(){};

    public static void aggiugi(Book b) {
        long idMap = Library.getBooksLibrary().IDSeed;
        _library._data.put(idMap, b);
    }

    public void delete(Long ID) {
        _library._data.remove(ID);
    };

    public static Library getBooksLibrary() {
        if(_library == null){
            _library = new Library();
            Library.nextID();
            _library.IDKey = nextID();
        }
        Library.nextID();
        _library.IDKey = nextID();
        return _library;
    }

    public static long nextID(){
        return ++_library.IDSeed;
    }

    public static Library caricaDaFile(String fileName) throws ClassNotFoundException, IOException {
        leggiDaFile(fileName);
        if (_library == null) {
            _library = new Library();
        }
        return _library;
    }

    public static void leggiDaFile(String fileName) throws IOException, ClassNotFoundException{
        try{
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            Library _repository = (Library) in.readObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(file);
        Library _repository = (Library) in.readObject();
    }

    public static List<Book> getBookList() {
        return new ArrayList<Book>(_library._data.values());
    }

    public static void writeToFile(String fileName) throws IOException {
        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream objOut = new ObjectOutputStream(file);
        objOut.writeObject(_library);
        objOut.close();
        file.close();
    }

    public static void modifica(long idKey) {
        Book bookToModify = _library._data.get(idKey);
        Book clone = Book.cloneForUpdate(bookToModify);
        _library._data.put(idKey, clone);
    }

    public static void clear (){_library._data.clear();}

    public static boolean has(long IDKey) {
        return _library._data.containsKey(IDKey);
    }

    public static Book getBook(long IDKey){
        return _library._data.get( IDKey );
    }

}
