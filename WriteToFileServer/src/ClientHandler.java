import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final WriteToFileServer server;
    public ClientHandler(Socket socket, WriteToFileServer server) {
        this.socket = socket;
        this.server = server;
    }
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String welcomeMessage = "Welcome to the WriteToFileServer. Please send your request in the following format: fileName.txt E poi il testo.";
            bw.write(welcomeMessage + System.lineSeparator());
            bw.flush();

            while (true) {
                String line = br.readLine();
                if (line.equals(server.getQuitCommand())) {
                    bw.write("Terminating connection."+ System.lineSeparator());
                    bw.flush();
                    socket.close();
                    break;
                }
                bw.write(WriteToFileServer.process(line) + System.lineSeparator());
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}