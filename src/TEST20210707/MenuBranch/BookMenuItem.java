package TEST20210707.MenuBranch;

public class BookMenuItem implements Runnable {

    private final String _ID;
    private final String _title;

    // Costruttore da richiamare nei costruttori delle classi figlie
    protected BookMenuItem(String menuID, String title) {
        _ID = menuID;
        _title = title;
    }

    // Comportamento del menu item, da specializzare per ciascun tipo
    public String getID() {
        return _ID;
    }

    public String getTitle() {
        return _title;
    }

    @Override
    public String toString() {
        return getID() + ". " + getTitle();
    }

    @Override
    public void run() {

    }
}
