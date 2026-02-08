package PDA.PDA_Utilities;

import java.util.Stack;
import java.util.HashSet;
import java.util.Set;

/*
Should have:
- Current state
- The current input symbol
- Whats on top of the stack (So it holds a stack)

*/

class Config {
    int state_current;
    char input_current;
    Stack<Character> stack;

    Config(int state_current, char input_current) {
        this.state_current = state_current;
        this.input_current = input_current;
        this.stack = new Stack<>();
    }

    Config(int state_current, char input_current, Stack<Character> stack) {
        this.state_current = state_current;
        this.input_current = input_current;
        this.stack = stack;
    }

    int getStateCurrent() {
        return this.state_current;
    }

    char getInputCurrent() {
        return this.input_current;
    }

    char getTopStack() {
        return stack.peek();
    }

}

public class Configurations {

    Transitions trans_handler;
    States state_handler;
    Set<Config> configs;
    String input_string;

    public Configurations(Transitions trans_handler, States state_handler) {
        this.trans_handler = trans_handler;
        this.state_handler = state_handler;
        configs = new HashSet<>();

    }

    // Initalization should just find start state and create configuration for it,
    // and then log it in a hashset
    public void process_file(String input_string) {
        configs.add(new Config(state_handler.start.getId(), input_string.charAt(0)));
    }

    /*
     * 1.
     * Current input symbol in config = read symbol of transition
     * AND
     * Whats on top of the stack in config = pop symbol of transition
     * 
     * 2.
     * read symbol of transition = Epsilon (Empty state)
     * AND
     * Whats on top of the stack in config = pop symbol of transition
     * 
     * 3.
     * Current input symbol in config = read symbol of transition
     * AND
     * pop symbol of transition = Epsilon (Empty state)
     * 
     * 4.
     * read symbol of transition = Epsilon (Empty state)
     * AND
     * pop symbol of transition = Epsilon (Empty state)
     */

    public void generateConfigurations() {
        // At first there will only be one config
        for (Config con : configs) {
            for (Transition tran : trans_handler.getTransCollection().get(con.getStateCurrent())) {
                if ((con.getInputCurrent() == tran.getRead()) && (con.getTopStack() == tran.getPop())) {
                    // Create new config and add it to set

                }
                if (('E' == tran.getRead()) && (con.getTopStack() == tran.getPop())) {

                }
                if ((con.getInputCurrent() == tran.getRead()) && ('E' == tran.getPop())) {

                }
                if (('E' == tran.getRead()) && ('E' == tran.getPop())) {

                }
            }

        }

    }

}
