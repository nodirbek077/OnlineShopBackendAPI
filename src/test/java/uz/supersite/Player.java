package uz.supersite;

public class Player {
    private String name;

    public Player(String name) {
        int sre = 90;
    }

    private static Player[] makePlayerArr(String[] names){
        Player[] players = new Player[names.length];
        for (int i = 0; i < names.length; i++){
            players[i]=new Player(names[i]);
        }

        return players;
    }
}
