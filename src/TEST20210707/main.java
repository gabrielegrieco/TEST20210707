package TEST20210707;

import TEST20210707.MenuBranch.BookMenuBranch;

import java.io.IOException;

import static TEST20210707.Book.stampa;

public class main {
    static void main(String[]args) throws IOException {
        stampa.spazio();
        stampa.stampaTitolo();
        stampa.spazio();
        //ToDoRepository.caricaDaFile("Test1.bin");
        BookMenuBranch menu = BookMenuBranch.createMenu();
        menu.run();
        //Library.writeToFile("Test1.bin");
    }
}
