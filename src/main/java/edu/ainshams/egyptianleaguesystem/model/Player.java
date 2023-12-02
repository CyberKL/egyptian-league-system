package edu.ainshams.egyptianleaguesystem.model;
import java.time.LocalDate;
public abstract class Player extends FootballCharacter{

    protected int playerId;
    protected int number;
    protected Team team;
    protected int height;
    protected int weight;
    protected String preferredFoot;
    protected String position;
    private static int numOfPlayers = 0;

    protected Player(String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot, String position, int yellowCards, int redCards){
        super(name, dateOfBirth, nationality, yellowCards, redCards);
        this.playerId = playerId;
        this.number = number;
        this.team = team;
        this.height = height;
        this.weight = weight;
        this.preferredFoot = preferredFoot;
        this.position = position;
        numOfPlayers ++;
    }
    protected String displayPlayerInfo(){

        return "Name: "+name+"/nNationality"+nationality+"/nAge: "+age+"/nPlayerId:"+playerId+"/nNumber:"+number+"/nTeam:"+team+"/nHeight:"+height+"/nWeight:"+weight+"/nPreferredFoot:"+preferredFoot+"/nPosition:"+position+"/nYellowCards:"+yellowCards+"/nRedCards:"+redCards;
    }

    public static int displayNumOfPlayers(){
        return numOfPlayers;
    }

    public Team getTeam() {
        return team;
    }
    public String getName() {
        return this.name;
    }
    //public String searchPlayer(String playerName, Team team) {

    //return"Player name is wrong";
    //}


    //public void deletePlayer(){
    // return;}

    protected void setNumber(int number) {
        this.number = number;
    }
    protected void setTeam(Team team) {
        this.team = team;
    }
    protected void setHeight(int height) {
        this.height = height;
    }

    protected void setWeight(int weight) {
        this.weight = weight;
    }
    protected void setPreferredFoot(String preferredFoot) {
        this.preferredFoot = preferredFoot;
    }
    protected void setPosition(String position) {
        this.position = position;
    }
    protected void setNationality(String nationality) {
        this.nationality = nationality;
    }

    protected void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void updatePlayerInfo( int number, Team team, int height, int weight, String preferredFoot, String position,String nationality, LocalDate dateOfBirth) {
        setNumber(number);
        setTeam(team);
        setHeight(height);
        setWeight(weight);
        setPreferredFoot(preferredFoot);
        setPosition(position);
        setNationality(nationality);
        setDateOfBirth(dateOfBirth);
        calculateAge();
    }

    protected void enterPlayerInfo(String name, LocalDate dateOfBirth, String nationality, int playerId, int number, Team team, int height, int weight, String preferredFoot, String position, int yellowCards, int redCards) {
    }
}
