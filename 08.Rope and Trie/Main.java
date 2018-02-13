import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        TextEditorImpl editor = new TextEditorImpl();
        String command;
        while (!"end".equals(command = reader.readLine())) {
            String[] tokens = command.split(" ");
            switch (tokens[0]) {
                case "login":
                    editor.login(tokens[1]);
                    break;
                case "logout":
                    editor.logout(tokens[1]);
                    break;
                case "users":
                    if (tokens.length > 1) {
                        for (String user : editor.users(tokens[1])) {
                            System.out.println(user);
                        }
                    }else{
                        for (String user : editor.users("")) {
                            System.out.println(user);
                        }
                    }
                    break;
                default:
                    String username = tokens[0];
                    switch (tokens[1]) {
                        case "prepend":
                            StringBuilder prependSb = new StringBuilder();
                            for (int i = 2; i < tokens.length; i++) {
                                prependSb.append(tokens[i]);
                                if (i != tokens.length - 1){
                                    prependSb.append(" ");
                                }
                            }
                            editor.prepend(username, prependSb.toString());
                            break;
                        case "insert":
                            StringBuilder insertSb = new StringBuilder();
                            for (int i = 3; i < tokens.length; i++) {
                                insertSb.append(tokens[i]);
                                if (i != tokens.length - 1){
                                    insertSb.append(" ");
                                }
                            }
                            editor.insert(username, Integer.parseInt(tokens[2]), insertSb.toString());
                            break;
                        case "print":
                            System.out.println(editor.print(username));
                            break;
                        case "clear":
                            editor.clear(username);
                            break;
                        case "length":
                            System.out.println(editor.length(username));
                            break;
                        case "substring":
                            editor.substring(username, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                            break;
                        case "undo":
                            editor.undo(username);
                            break;
                        case "delete":
                            editor.delete(username, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                            break;
                    }
            }
        }
    }
}
