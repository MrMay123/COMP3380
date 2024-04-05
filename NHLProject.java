import java.sql.Connection;

import java.util.ArrayList;
import java.util.Scanner;

public class NHLProject {
    static Connection connection;
    static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        // startup sequence
        MyDatabase db = new MyDatabase();
        signInConsole(db);

        System.out.println("Exiting...");
    }

    public static void signInConsole(MyDatabase db){
        UserManager manager = new UserManager();
        manager.readFile();
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome! Type s to sign up or l to login, or type q to quit. ");
        System.out.print("user > ");
        String line;

        while (console.hasNextLine()){
            line = console.nextLine();
            if (line.equals("q")){
                break;
            }
            else if (line.equals("s")){
                System.out.println("Please type your user name and password below, such as: Alice 1234, or type q to quit. If you are an administrator, please add your administrator code");
                System.out.print("sign up > ");
                manager.signUp(console);
            } else if (line.equals("l")) {
                System.out.println("Please type your user name and password below, such as: Alice 1234");
                System.out.print("login > ");
                int userType = manager.login(console);
                if (userType == 1){
                    runConsole(db, console);
                    break;
                }
                else if (userType == 2){
                    runAdminConsole(db, console);
                }
            }
            else {
                System.out.println("Invalid input! Please reinput!");
            }
            System.out.print("user > ");
        }

        console.close();
    }

    public static void runConsole(MyDatabase db, Scanner console) {
        System.out.println("Welcome! Type c for all users commands. ");
        System.out.print("db > ");
        String line = console.nextLine();
        String[] parts;
        String arg = "";

        while (line != null && !line.equals("q")) {
            parts = line.split("\\s+");
            if (line.indexOf(" ") > 0)
                arg = line.substring(line.indexOf(" ")).trim();

            if (parts[0].equals("c"))
                printSelectHelp();
            else if (parts[0].equals("allt")) {
                db.allTeam();
            }

            else if (parts[0].equals("allp")) {
                db.allPlayer();
            }

            else if (parts[0].equals("alls")) {
                db.allSeason();
            }

            else if (parts[0].equals("mt")) {
                db.playerForMultipleTeams();
            }

            else if (parts[0].equals("mts")) {
                if (parts.length >= 2)
                    db.playerWithoutTrade(arg);
                else
                    System.out.println("Require an argument for this command");
            }

            else if (parts[0].equals("g")) {
                if (parts.length >= 3)
                    db.Games(parts[1], parts[2]);
                else
                    System.out.println("Require two arguments for this command");
            }

            else if (parts[0].equals("pros20")) {
                db.prospectsMore20();
            }

            else if (parts[0].equals("avgt")) {
                db.attendance10000();
            }

            else if (parts[0].equals("top5p")) {
                if (parts.length >= 3)
                    db.top5Players(parts[1], parts[2]);
                else
                    System.out.println("Require two arguments for this command");
            }

            else if (parts[0].equals("over30")) {
                if (parts.length >= 2)
                    db.playersOver30(arg);
                else
                    System.out.println("Require an argument for this command");
            }

            else if (parts[0].equals("tro")) {
                db.trophy();
            }

            else if (parts[0].equals("prosl")) {
                db.prospectLeague();
            }

            else if (parts[0].equals("goa")) {
                db.goalieStanley();
            }

            else if (parts[0].equals("phw")) {
                if (parts.length >= 2)
                    db.pointsHeightWeight(arg);
                else
                    System.out.println("Require an argument for this command");
            }

            else if (parts[0].equals("p35")) {
                if (parts.length >= 2)
                    db.playerOver35(arg);
                else
                    System.out.println("Require an argument for this command");
            }

            else if (parts[0].equals("sh2")) {
                if (parts.length >= 2)
                    db.playerWithHighShot(arg);
                else
                    System.out.println("Require an argument for this command");
            }


            else
                System.out.println("Read the help with c, or find help somewhere else.");

            System.out.print("db > ");
            line = console.nextLine();
        }
    }

    public static void runAdminConsole(MyDatabase db, Scanner console){
        System.out.println("Welcome! Type c for all admin commands. ");
        System.out.print("admin > ");
        String line;
        String[] parts;

        while (console.hasNextLine()) {
            line = console.nextLine();
            parts = line.split("\\s+");

            if (line.equals("q")) {
                break;
            }

            if (parts[0].equals("c")) {
                printAdminHelp();
            } else if (parts[0].equals("user")) {
                runConsole(db, console);
            } else if (parts[0].equals("del")) {
                runDelConsole(db, console);
            } else if (parts[0].equals("ins")) {
                runInsertConsole(db, console);
            } else {
                System.out.println("Read the help with c, or find help somewhere else.");
            }

            System.out.print("admin > ");
        }

//        while (line != null && !line.equals("q")){
//            parts = line.split("\\s+");
//
//            if (parts[0].equals("c"))
//                printAdminHelp();
//
//            else if (parts[0].equals("user")){
//                runConsole(db);
//            }
//            else if (parts[0].equals("del")){
//                runDelConsole(db);
//            }
//            else if (parts[0].equals("insert")){
//                runInsertConsole(db);
//            }
//            else
//                System.out.println("Read the help with c, or find help somewhere else.");
//
//            System.out.print("admin > ");
//            line = console.nextLine();
//        }
//        console.close();
    }

    private static void printSelectHelp() {
        System.out.println("NHL database");
        System.out.println("Commands:");
        System.out.println("allt - Get id and name of all teams");
        System.out.println("allp - Get id and name of all players");
        System.out.println("alls - Get season year of all seasons");
        System.out.println("mt - Get players who play for multiple teams");
        System.out.println("mts <season> - Get players that have played for multiple teams except players that were traded in a single season A");
        System.out.println("g <teamA> <teamB> - View team A’s head-to-head games against team B");
        System.out.println("pros20 - Filter Prospects that are drafted not in the first round and played 20 or more games");
        System.out.println("avgt - Select teams that have played in games where average attendance is < 10000");
        System.out.println("top5p <team> <season>  - Select top 5 point producers in a team in a certain season");
        System.out.println("over30 <season> - Select teams ordered by average number of players over 30 years old for a specific season and select them");
        System.out.println("tro - Select players that have been nominated for a trophy but have not won a trophy");
        System.out.println("prosl - For all seasons, count the number of prospects drafted from a specific league for each team");
        System.out.println("goa - Select the goalies that won the stanley cup");
        System.out.println("phw <season> - Select the average overall points and height of all teams in season A, and order by desc");
        System.out.println("p35 <season> - Find players > 35 that have competed in a season and been nominated for a trophy");
        System.out.println("hs2 <season> - Find players that have a high shot making ability or were nominated twice in the season");

        System.out.println("");

        System.out.println("q - Exit the program");

        System.out.println("---- end help ----- ");
    }

    private static void printAdminHelp(){
        System.out.println("Welcome to Admin console!");
        System.out.println("Commands: ");
        System.out.println("user - Go to the normal user menu");
        System.out.println("del - Go to the deletion menu");
        System.out.println("insert - Go to the insertion menu");
    }

    private static void printDeleteHelp(){
        System.out.println("Welcome to deletion console!");
        System.out.println("Commands: ");
        System.out.println();
        System.out.println("q - return to previous directory");
    }

    private static void printInsertHelp(){
        System.out.println("Welcome to insertion console!");
        System.out.println("Commands: ");
        System.out.println();
        System.out.println("q - return to previous directory");
    }

    private static void runDelConsole(MyDatabase db, Scanner console){
        System.out.println("Welcome! Type c for all delete commands. ");
        System.out.print("del > ");
        String line;
        String[] parts;

        while (console.hasNextLine()){
            line = console.nextLine();
            parts = line.split("\\s+");

            if (parts[0].equals("q")){
                break;
            }
            else if (parts[0].equals("c")){
                printDeleteHelp();
            }
            else if (parts[0].equals("........")){

            }
            else {
                System.out.println("Read the help with c, or find help somewhere else");
            }
            System.out.print("del > ");
        }
    }

    private static void runInsertConsole(MyDatabase db, Scanner console){
        System.out.println("Welcome! Type c for all insert commands. ");
        System.out.print("ins > ");
        String line;
        String[] parts;

        while (console.hasNextLine()){
            line = console.nextLine();
            parts = line.split("\\s+");

            if (parts[0].equals("q")){
                break;
            }
            else if (parts[0].equals("c")){
                printInsertHelp();
            }
            else if (parts[0].equals("........")){

            }
            else {
                System.out.println("Read the help with c, or find help somewhere else");
            }
            System.out.print("ins > ");
        }
    }
}

