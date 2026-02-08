package PDA.PDA_Utilities;

import java.util.Stack;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
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

    Stack<Character> getNewStack(Transition tran) {
        // This needs access to what will be popped and that will be pushed, all inside
        // trans.
        Stack<Character> new_stack = new Stack<>();
        new_stack.addAll(this.stack);

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
        return this.input_current + 1;
    }

    char getTopStack() {
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        return 'X';
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

    @Override
    public String toString() {
        return "configuration: current state - " + state_current + " current input " + input_current + " current stack "
                + stack.toString();
    }

}

public class Configurations {

    Transitions trans_handler;
    States state_handler;
    Set<Config> configs;
    Set<Config> terminal_configs;
    String input_string;
    Queue<Config> conq;

    public Configurations(Transitions trans_handler, States state_handler) {
        this.trans_handler = trans_handler;
        this.state_handler = state_handler;
        configs = new HashSet<>();
        terminal_configs = new HashSet<>();
        conq = new LinkedList<>();
    }

    // Initalization should just find start state and create configuration for it,
    // and then log it in a hashset
    public void process_file(String input_string) {
        Config c = new Config(state_handler.start.getId(), 0);
        configs.add(c);
        conq.add(c);
        this.input_string = input_string;
    }

    // debugging

    public void debugConfigurations() {
        System.out.println("\nAll configs: \n");
        for (Config con : configs) {
            System.out.println(con);
        }
        System.out.println("\nAll terminal configs: \n");
        for (Config con : terminal_configs) {
            System.out.println(con);
        }

    }

    /*
     * 
     * When input_current == length of input_string we check if there are any
     * configs with accept states or reject states. I can make a list of both and
     * then just print whichever one is true. If there are any terminal configs that
     * are not accept states print out reject and then those states. If there are
     * any terminal configs which are accept states print out accept and then those
     * accept states.
     * 
     */

    public void generateResults() {
        if (terminal_configs.isEmpty()) {
            System.out.println("reject");
            return;
        }

        Set<Config> accept_states = new HashSet<>();
        Set<Config> reject_states = new HashSet<>();

        for (Config con : terminal_configs) {
            if (state_handler.isAccept(con.getStateCurrent())) {
                accept_states.add(con);
            } else {
                reject_states.add(con);
            }
        }

        String output = "";
        if (accept_states.isEmpty()) {
            // Print reject states
            System.out.print("reject ");
            for (Config con : reject_states) {
                if (!output.contains(String.valueOf(con.getStateCurrent()))) {
                    output = output + con.getStateCurrent() + " ";
                }
            }
            System.out.println(output);
        } else {
            // Print accept states
            System.out.print("accept ");
            for (Config con : accept_states) {
                if (!output.contains(String.valueOf(con.getStateCurrent()))) {
                    output = output + con.getStateCurrent() + " ";
                }
            }
            System.out.println(output);
        }

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
        // This needs to be a queue, and should iterate while the queue isn't empty
        while (!conq.isEmpty()) {
            Config con = conq.remove();
            // Iterates through each transition available at current state
            // More debugging
            // System.out.println(con.getStateCurrent());
            if (trans_handler.getTransCollection().containsKey(con.getStateCurrent())
                    && con.getInputCurrent() <= input_string.length()) {
                for (Transition tran : trans_handler.getTransCollection().get(con.getStateCurrent())) {
                    int curr = con.getInputCurrent();
                    // System.out.println(curr);
                    // Some of the configs will have the current input, it shouldn't be incremented
                    // everytime.
                    if ((curr < input_string.length()) && (input_string.charAt(curr) == tran.getRead())
                            && (con.getTopStack() == tran.getPop())) {
                        // Create new config and add it to set
                        // getAndAdd increments input and then passes the value to the new config
                        Config c = new Config(tran.getStateNext(), con.getAndAddInputCurrent(), con.getNewStack(tran));
                        configs.add(c);
                        conq.add(c);
                        if (c.getInputCurrent() == input_string.length()) {
                            terminal_configs.add(c);
                        }
                        // Each config has to keep track of what char it currently holds

                    }
                    if (('E' == tran.getRead()) && (con.getTopStack() == tran.getPop())) {
                        Config c = new Config(tran.getStateNext(), con.getInputCurrent(), con.getNewStack(tran));
                        configs.add(c);
                        conq.add(c);
                        if (c.getInputCurrent() == input_string.length()) {
                            terminal_configs.add(c);
                        }
                    }
                    if ((curr < input_string.length()) && (input_string.charAt(curr) == tran.getRead())
                            && ('E' == tran.getPop())) {
                        Config c = new Config(tran.getStateNext(), con.getAndAddInputCurrent(), con.getNewStack(tran));
                        configs.add(c);
                        conq.add(c);
                        if (c.getInputCurrent() == input_string.length()) {
                            terminal_configs.add(c);
                        }
                    }
                    if (('E' == tran.getRead()) && ('E' == tran.getPop())) {
                        Config c = new Config(tran.getStateNext(), con.getInputCurrent(), con.getNewStack(tran));
                        configs.add(c);
                        conq.add(c);
                        if (c.getInputCurrent() == input_string.length()) {
                            terminal_configs.add(c);
                        }
                    }
                }
            }

        }

    }

}
