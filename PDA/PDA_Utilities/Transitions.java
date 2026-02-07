package PDA.PDA_Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/* Transition lines come in as:
        transition p x y q z

        p - Current state
        x - What we are reading
        y - What we are popping
        q - Next state
        z - What we are pushing
        
        */
class Transition {
        int state_current;
        int state_next;
        String read;
        String pop;
        String push;

        public Transition(int state_current, int state_next, String read, String pop, String push) {
                this.state_current = state_current;
                this.state_next = state_next;
                this.read = read;
                this.pop = pop;
                this.push = push;
        }

        int getStateCurrent() {
                return this.state_current;
        }

        int getStateNext() {
                return this.state_next;
        }

        String getRead() {
                return this.read;
        }

        String getPop() {
                return this.pop;
        }

        String getPush() {
                return this.push;
        }

}

public class Transitions {
        Scanner scanner;
        HashMap<Integer, Transition> trans_collection;

        public Transitions(File input) throws FileNotFoundException {
                scanner = new Scanner(input);
                trans_collection = new HashMap<>();
        }

        public void process_file() {
                while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split("\\s+");

                        // System.out.println(parts[0]);
                        if (parts[0].equals("transition")) {
                                Transition t;
                                t = new Transition(Integer.parseInt(parts[1]), Integer.parseInt(parts[4]), parts[2],
                                                parts[3], parts[5]);
                                trans_collection.putIfAbsent(Integer.parseInt(parts[1]), t);
                                System.out.println(t.getStateCurrent());
                        }

                }
        }
}
