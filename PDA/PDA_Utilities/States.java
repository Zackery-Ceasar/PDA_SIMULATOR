package PDA.PDA_Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/*State lines come in as:
        state x start
        state x accept
        state x accept  start
        state x start accept

        x can be any number between 0 and 1000
        
        */

class State {
    boolean start;
    boolean accept;
    int state_id;

    public State(boolean start, boolean accept, int state_id) {
        this.start = start;
        this.accept = accept;
        this.state_id = state_id;

    }

    boolean isStart() {
        return this.start;
    }

    boolean isAccept() {
        return this.accept;
    }

    int getId() {
        return this.state_id;
    }

}

public class States {
    Scanner scanner;
    HashMap<Integer, State> state_collection;
    State start;

    public States(File input) throws FileNotFoundException {
        scanner = new Scanner(input);
        state_collection = new HashMap<>();
    }

    public void process_file() {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");

            // System.out.println(parts[0]);
            if (parts[0].equals("state")) {
                State s;
                if (parts.length == 2) {
                    s = new State(false, false, Integer.parseInt(parts[1]));
                } else if (parts.length == 3) {
                    if (parts[2] == "accept") {
                        s = new State(false, true, Integer.parseInt(parts[1]));
                    } else {
                        s = new State(true, false, Integer.parseInt(parts[1]));
                        start = s;
                    }
                } else {
                    s = new State(true, false, Integer.parseInt(parts[1]));
                }
                state_collection.putIfAbsent(Integer.parseInt(parts[1]), s);
                System.out.println(s.getId());
            }

        }
    }

}
