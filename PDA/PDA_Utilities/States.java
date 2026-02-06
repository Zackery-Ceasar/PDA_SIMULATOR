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

}

public class States {
    Scanner scanner;
    HashMap<Integer, State> state_collection;

    public States(File input) throws FileNotFoundException {
        scanner = new Scanner(input);
        state_collection = new HashMap<>();
    }

    public void process_file() {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            System.out.println(parts);
        }
    }

}
