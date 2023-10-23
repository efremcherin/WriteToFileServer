public class Main {
    public static void main(String[] args) {
        WriteToFileServer server = new WriteToFileServer(12000, "BYE");
        server.run();
    }
}