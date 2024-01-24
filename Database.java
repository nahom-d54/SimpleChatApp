import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private Connection db;
    public Database(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String uri = "jdbc:mysql://localhost:3306/chatdb";
            this.db = DriverManager.getConnection(uri, "root","jesus123");

        }catch (Exception e){
            e.printStackTrace();
            closeConnection();
        }

    }

    public Connection getConnection(){
        return db;
    }
    public void closeConnection(){
        try {
            if (db != null) {
                db.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
