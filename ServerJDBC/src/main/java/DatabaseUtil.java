import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Map;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_java";
    private static final String USER = "admin";
    private static final String PASS = "admin";
    private static DatabaseUtil ds;

    private DatabaseUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static DatabaseUtil getInstance() {
        if(ds == null) {
            ds = new DatabaseUtil();
        }
        return ds;
    }

    public CachedRowSet executeQuery(String query) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
            return crs;
        }
    }

    public CachedRowSet getQuestions() throws SQLException {
        return executeQuery("SELECT * FROM quiz_questions");
    }

    public CachedRowSet getResults() throws SQLException {
        return executeQuery("SELECT * FROM quiz_results");
    }
    synchronized int getNextId() throws SQLException {
        final String tableName = "quiz_results"; // Stała nazwa tabeli
        String query = "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM " + tableName;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             CachedRowSet crs = executeQuery(query)) {

            crs.next();
            int newId = crs.getInt("next_id");

            String insertSQL = "INSERT INTO quiz_results (id, wynik) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
                ps.setInt(1, newId);
                ps.setInt(2, 0); // Domyślna wartość 0 dla bezpieczeństwa
                ps.executeUpdate(); // KLUCZOWE: wykonanie inserta
            }

            return newId;
        }
    }
    public void saveQuizResults(Map<Integer, String> answersMap, int id, int wynik) throws SQLException {
        String insertAnswersSQL = "INSERT INTO quiz_answers (id, `1`,`2`,`3`,`4`,`5`,`6`,`7`,`8`,`9`,`10`,`11`,`12`,`13`,`14`,`15`,`16`,`17`,`18`,`19`,`20`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateResultsSQL = "UPDATE quiz_results SET wynik = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false);

            try {
                // 1. Zapisz odpowiedzi
                try (PreparedStatement psAnswers = conn.prepareStatement(insertAnswersSQL)) {
                    psAnswers.setInt(1, id);

                    for (int i = 1; i <= 20; i++) {
                        String answer = answersMap.getOrDefault(i, null);
                        psAnswers.setString(i + 1, answer);
                    }

                    psAnswers.executeUpdate();
                }

                // 2. Zaktualizuj wynik
                try (PreparedStatement psResults = conn.prepareStatement(updateResultsSQL)) {
                    psResults.setInt(1, wynik);
                    psResults.setInt(2, id);
                    int affectedRows = psResults.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Aktualizacja wyniku nie powiodła się, brak rekordu z id: " + id);
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException("Transakcja została wycofana: " + e.getMessage(), e);
            }
        }
    }


}
