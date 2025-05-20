import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int MAX_CLIENTS = 250;
    private final ExecutorService clientPool;

    public Server() {
        this.clientPool = Executors.newFixedThreadPool(MAX_CLIENTS);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(ServerConfig.getServerPort())) {
            System.out.println("🔌 Serwer nasłuchuje na porcie: " + ServerConfig.getServerPort());

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("🟢 Połączenie od: " + clientSocket.getInetAddress());

                    // Obsłuż klienta
                    clientPool.execute(new ClientHandler(clientSocket, DatabaseUtil.getInstance().getNextId()));

                } catch (IOException e) {
                    System.err.println("❌ Błąd przy akceptacji połączenia: " + e.getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Nie udało się uruchomić serwera: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        System.out.println("🛑 Zamykam serwer...");
        clientPool.shutdown();
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
