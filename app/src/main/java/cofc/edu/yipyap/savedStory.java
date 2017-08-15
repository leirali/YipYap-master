package cofc.edu.yipyap;

import java.util.ArrayList;

public class savedStory {

    String gameName;
    String topic;
    ArrayList<String> players;
    int numRounds;
    String story;
    String timestamp;


    savedStory(String inname, String intop, ArrayList<String> inpla, int inrnds, String instory, String intime)
    {
        gameName = inname;
        topic = intop;
        players = inpla;
        numRounds = inrnds;
        story = instory;
        timestamp = intime;
    }

    public String getTopic(){return topic;}
    public ArrayList<String> getPlayers(){return players;}
    public int getNumRounds(){return numRounds;}
    public String getStory(){return story;}
    public String getTimestamp(){return timestamp;}
    public String getName(){return gameName;}

}
