import java.sql.*;

class MyDatabase {
    private Connection connection;

    public MyDatabase() {
        try {
            String url = "jdbc:sqlite:library.db";
            // create a connection to the database
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    //1.for allt
    public void allTeam(){
        try {
            String sql = "SELECT teamName FROM team";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Print all teams' name");
            boolean hasResult = false;

            while (resultSet.next()){
                hasResult = true;
                System.out.println(resultSet.getString("teamName"));
            }

            if (!hasResult){
                System.out.println("No result found");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    //2.for allp
    public void allPlayer(){
        try {
            String sql = "SELECT playerName FROM player";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Print all players' name");
            boolean hasResult = false;

            while (resultSet.next()){
                hasResult = true;
                System.out.println(resultSet.getString("playerName"));
            }

            if (!hasResult){
                System.out.println("No result found");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // for alls
    public void allSeason(){
        try {
            String sql = "SELECT seasonYear FROM season";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Print all season years");
            boolean hasResult = false;

            while (resultSet.next()){
                hasResult = true;
                System.out.println(resultSet.getString("seasonYear"));
            }

            if (!hasResult){
                System.out.println("No result found");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    //3.for mt
    public void playerForMultipleTeams(){
        try {
            String sql = "SELECT DISTINCT playerID, playerName FROM " +
                    "playedFor p1, playedFor p2 " +
                    "WHERE p1.playerID = p2.playerID and p1.teamName != p2.teamName";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Print players that have played for multiple teams");
            boolean hasResult = false;

            while (resultSet.next()){
                hasResult = true;
                System.out.println(resultSet.getInt("playerID") + " " + resultSet.getString("playerName"));
            }

            if (!hasResult){
                System.out.println("No result found");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    //4.for mts
    public void playerWithoutTrade(String season){
    }

    //5.for g
    public void Games(String teamA, String teamB){
        try {
            String sql = "SELECT game* FROM " +
                    "team JOIN compete on team.teamName = compete.teamName " +
                    "JOIN game on compete.gameID = game.gameID " +
                    "WHERE (game.home = ? AND game.visitor = ?) OR (game.home = ? AND game.visitor = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teamA);
            statement.setString(2, teamB);
            statement.setString(3, teamB);
            statement.setString(4, teamA);
            ResultSet resultSet = statement.executeQuery();
            int resultColumns = 8;

            System.out.println("Print " + teamA + "’s head-to-head games against " + teamB);
            boolean hasResult = false;

            System.out.println("GameID, Date, Visitor, Visitor goals, Home, Home goals, Attendance, Length of game");

            while (resultSet.next()){
                hasResult = true;
                for (int i = 1;i <= 8;i++){
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
            }

            if (!hasResult){
                System.out.println("No result found");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    //6.for pros 20
    public void prospectsMore20(){

    }

    //7.for avgt
    public void attendance10000(){

    }

    //8.for top5p
    public void top5Players(String team, String season){

    }

    //9.for over30
    public void playersOver30(String season){

    }

    //10.for tro
    public void trophy(){

    }

    //11.for prosl
    public void prospectLeague(){

    }

    //12.for goa
    public void goalieStanley(){

    }

    //13.for phw
    public void pointsHeightWeight(String season){

    }

    //14.for p35
    public void playerOver35(String season){

    }

    //15.for sh2
    public void playerWithHighShot(String season){

    }

    public void insert(){

    }

    public void delete(){

    }
}