package PDA.PDA_Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

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
        char read;
        char pop;
        char push;

        Transition(int state_current, int state_next, char read, char pop, char push) {
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

        char getRead() {
                return this.read;
        }

        char getPop() {
                return this.pop;
        }

        char getPush() {
                return this.push;
        }

        @Override
        public String toString() {
                return "transition " + state_current + " " + read + " " + pop + " " + state_next + " " + push;
        }

        @Override
        public boolean equals(Object o) {
                if (!(o instanceof Transition)) {
                        return false;
                }
                Transition t = (Transition) o;
                return this.state_current == t.state_current &&
                                this.pop == t.pop &&
                                this.read == t.read &&
                                this.state_next == t.state_next &&
                                this.push == t.push;
        }

        @Override
        public int hashCode() {
                return this.state_current;
        }

}

public class Transitions {
        Scanner scanner;
        Set<Transition> same_state_trans;
        HashMap<Integer, Set<Transition>> trans_collection;

        public Transitions(File input) throws FileNotFoundException {
                scanner = new Scanner(input);
                trans_collection = new HashMap<>();
        }

        public HashMap<Integer, Set<Transition>> getTransCollection() {
                return this.trans_collection;
        }

        public void debugTransitions() {
                // Debugging
                for (Set<Transition> set_tr : trans_collection.values()) {
                        for (Transition tr : set_tr) {
                                System.out.println(tr);
                        }
                }
        }

        public void process_file() {
                while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split("\\s+");

                        // System.out.println(parts[0]);
                        if (parts[0].equals("transition")) {
                                Transition t;
                                t = new Transition(Integer.parseInt(parts[1]), Integer.parseInt(parts[4]),
                                                parts[2].charAt(0),
                                                parts[3].charAt(0), parts[5].charAt(0));
                                if (trans_collection.containsKey(Integer.parseInt(parts[1]))) {
                                        trans_collection.get(Integer.parseInt(parts[1])).add(t);
                                } else {
                                        same_state_trans = new HashSet<>();
                                        same_state_trans.add(t);
                                        trans_collection.put(Integer.parseInt(parts[1]), same_state_trans);
                                }

                                // System.out.println(t.getStateCurrent());
                        }

                }
        }
}
