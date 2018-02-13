public interface TextEditor {

    void login(String username);

    void logout(String username);

    void prepend(String username, String string);

    void insert(String username, int index, String string);

    void substring(String username, int startIndex, int length);

    void delete(String username, int startIndex, int length);


    void clear(String username);

    int length(String username);

    String print(String username);


    void undo(String username);

    Iterable<String> users(String prefix);
}
