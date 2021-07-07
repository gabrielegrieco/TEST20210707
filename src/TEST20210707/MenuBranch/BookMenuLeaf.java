package TEST20210707.MenuBranch;

public class BookMenuLeaf extends BookMenuItem {

    private final Runnable _action;

    public BookMenuLeaf(String ID, String title, Runnable action) {
        super(ID, title);
        _action = action;
    }
    @Override
    public void run() {
        _action.run();
    }
}
