package TEST20210707.MenuBranch;
import TEST20210707.BookImportExport;
import TEST20210707.BookManager;
import TEST20210707.Utilities.BookList;

import java.util.*;

;import static TEST20210707.Book.stampa;

    // OPZIONE: contiene altri elementi
    public class BookMenuBranch extends BookMenuItem {

        protected BookMenuBranch(String menuID, String title) {
            super(menuID, title);
        }
        private List<BookMenuItem> _options = new ArrayList<>();
        private boolean _exit;
        private final String _defaultExitMessage = "Indietro";

        // Public constructor
        public BookMenuBranch(String ID, String title, List<BookMenuItem> options) {
            super(ID, title);
            _options.addAll(options);
            String exitID = String.valueOf(_options.size() + 1);
            initExit(exitID, _defaultExitMessage);
        }

        public BookMenuBranch(String ID, String title, List<BookMenuItem> options, String exitID, String exitMessage) {
            super(ID, title);
            _options.addAll(options);
            initExit(exitID, exitMessage);
        }

        private void initExit(String exitID, String exitMessage) {
            BookMenuLeaf exitLeaf = new BookMenuLeaf(exitID, exitMessage, () -> _exit = true);
            _options.add(exitLeaf);
        }

        @Override
        public void run() {
            Scanner in = new Scanner(System.in);
            _exit = false;
            do {
                printContent();
                String choice = in.nextLine();
                Optional<BookMenuItem> selected = _options.stream().filter(o -> o.getID().equals(choice)).findFirst();
                if (selected.isPresent())
                    selected.get().run();
                else
                    stampa.display("L'opzione che hai selezionato non è valida.");
            } while (!_exit);
        }

        private void printContent() {
            stampa.display(getTitle());
            _options.stream().map(BookMenuItem::toString).forEach(stampa::display);
        }

        public static BookMenuBranch createMenu() {
            BookMenuLeaf byAlphabetical = new BookMenuLeaf("1", "Per ordine alfabetico", BookList::visualizzaPerOrdineAlfabetico);
            BookMenuLeaf byData = new BookMenuLeaf("2", "Per data", BookList::visualizzaPerData);
            BookMenuLeaf byTitle = new BookMenuLeaf("3", "Per titolo", BookList::visualizzaPer);
            BookMenuBranch visualizzaMenu = new BookMenuBranch("1", "Visualizza", Arrays.asList( byAlphabetical, byData, byTitle ));

            BookMenuLeaf addBook = new BookMenuLeaf("1", "Aggiungi", BookManager::createNewBook);
            BookMenuLeaf removeBook = new BookMenuLeaf("2", "Rimuovi", BookManager::removeBook);
            BookMenuLeaf editBook = new BookMenuLeaf("3", "Modifica", BookManager::updateBook);
            BookMenuBranch editMenu = new BookMenuBranch("2", "Aggiungi, Rimuovi, Modifica", Arrays.asList(addBook, removeBook, editBook));

            BookMenuLeaf exportBook = new BookMenuLeaf("1", "Export su file", BookImportExport::ExportDati);
            BookMenuLeaf importBook = new BookMenuLeaf("2", "Import da file", BookImportExport::ImportaDati);
            BookMenuBranch importExportMenu = new BookMenuBranch("3", "Import/Export", Arrays.asList(exportBook, importBook));

            BookMenuBranch mainMenu = new BookMenuBranch("MainMenu", "Menu Principale", Arrays.asList(visualizzaMenu, editMenu, importExportMenu), "4", "Esci");
            return mainMenu;
        }
    }
