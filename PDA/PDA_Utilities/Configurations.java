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
    int input_current;
    Stack<Character> stack;

    Config(int state_current, int input_current) {
        this.state_current = state_current;
        this.input_current = input_current;
        this.stack = new Stack<>();
    }

    Config(int state_current, int input_current, Stack<Character> stack) {
        this.state_current = state_current;
        this.input_current = input_current;
        this.stack = stack;
    }

    Stack<Character> setNewStack(Transition tran) {
        // This needs access to what will be popped and rhat will be pushed, all inside
        // trans.
        Stack<Character> new_stack = this.stack;
        if (tran.getPop() != 'E') {
            new_stack.pop();
        }
        if (tran.getPush() != 'E') {
            new_stack.push(tran.getPush());
        }

        return new_stack;

    }

    int getStateCurrent() {
        return this.state_current;
    }

    int getInputCurrent() {
        return this.input_current;
    }

    int getAndAddInputCurrent() {
        return this.input_current++;
    }

    char getTopStack() {
        return stack.peek();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Config)) {
            return false;
        }
        Config c = (Config) o;
        return this.state_current == c.state_current &&
                this.input_current == c.input_current &&
                this.stack == c.stack;
    }

    @Override
    public int hashCode() {
        return this.state_current;
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
            // Iterates through each transition available at current state
            for (Transition tran : trans_handler.getTransCollection().get(con.getStateCurrent())) {
                int curr = con.getInputCurrent();
                if ((input_string.charAt(curr) == tran.getRead()) && (con.getTopStack() == tran.getPop())) {
                    // Create new config and add it to set
                    // getAndAdd increments input and then passes the value to the new config
                    Config c = new Config(tran.getStateNext(), input_string.charAt(con.getAndAddInputCurrent()));
                    c.setNewStack(tran);
                    configs.add(c);
                    // Each config has to keep track of what char it currently holds

                }
                if (('E' == tran.getRead()) && (con.getTopStack() == tran.getPop())) {
                    Config c = new Config(tran.getStateNext(), input_string.charAt(con.getAndAddInputCurrent()));
                    c.setNewStack(tran);
                    configs.add(c);
                }
                if ((input_string.charAt(curr) == tran.getRead()) && ('E' == tran.getPop())) {
                    Config c = new Config(tran.getStateNext(), input_string.charAt(con.getAndAddInputCurrent()));
                    c.setNewStack(tran);
                    configs.add(c);
                }
                if (('E' == tran.getRead()) && ('E' == tran.getPop())) {
                    Config c = new Config(tran.getStateNext(), input_string.charAt(con.getAndAddInputCurrent()));
                    c.setNewStack(tran);
                    configs.add(c);
                }
            }

        }

    }

}
