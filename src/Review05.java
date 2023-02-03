import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // ドライバのクラスをjava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DBと接続
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "sasa0719"
                    );
            // DBとやりとりする窓口（Statementオブジェクト）の作成
            String selectsql = "SELECT * FROM person WHERE Id = ?";
            pstmt = con.prepareStatement(selectsql);

            // 検索するIdを入力
            System.out.println("Idを入力してください。 > ");
            int id = keyInNum();

            // 入力されたidをPreparedStatementにセット
            pstmt.setInt(1, id);

            // SELECT文の実行と結果の格納／代入
            rs = pstmt.executeQuery();

            // 結果を表示
            while ( rs.next() ) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name + "\n" + age);
            }


        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じる時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                }
            }
        }

    }
    /*
     * キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列
     */
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader (new InputStreamReader (System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }
    /*
     * キーボードから入力された値をintで返す 引数：なし 戻り値：int
     */
    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch (NumberFormatException e) {
        }
        return result;
    }

}
