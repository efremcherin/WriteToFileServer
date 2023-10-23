import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WriteToFileServer {

    private final int port;
    private final String quitCommand;

    public WriteToFileServer(int port, String quitCommand) {
        this.port = port;
        this.quitCommand = quitCommand;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String process(String input) throws IOException {
        if (input.length() == 0) {
            return "Plese follow the request format.";
        }

        String[] words = input.split("\\s+");// split the string into words using a regular expression

        if (words.length < 2 || !words[0].endsWith(".txt")) {
            return "Please follow the request format.";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(words[0], true))) {
            writer.write(input.replaceFirst("^[^\\s]+\\s*", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(words[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line); // Append the line and a newline character
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String getQuitCommand() {
        return quitCommand;
    }

}
