
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ServerConfig {

    private static final String SERVER_HOST = "serverHost";
    private static final String SERVER_PORT = "serverPort";
    private static final String CONFIG_PATH = "src/main/resources/server_config.properties";
    private static final  Properties properties = new Properties();
    static {
       reloadData();
    }

    public static String getServerHost() {
        return properties.getProperty(SERVER_HOST);
    }

    public static int getServerPort() {
         return Integer.parseInt(properties.getProperty(SERVER_PORT));
    }
    public static void reloadData(){
        try(InputStream inputStream = Files.newInputStream( Path.of(CONFIG_PATH))) {
            properties.load(inputStream);
        }catch (IOException e) {
            throw new RuntimeException("Problem z ładowaniem konfiguracji servera");
        }
    }
}
