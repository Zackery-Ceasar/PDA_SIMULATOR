package PDA.PDA_Utilities;

import java.util.Stack;

/*
Should have:
- Current state
- The current input symbol
- Whats on top of the stack (So it holds a stack)

*/

class Config {
    int state_current;
    String input_current;
    Stack<String> stack;

    Config(int state_current, String input_current, Stack<String> stack) {
        this.state_current = state_current;
        this.input_current = input_current;
        this.stack = stack;
    }

    int getStateCurrent() {
        return this.state_current;
    }

    String getInputCurrent() {
        return this.input_current;
    }

    String getTopStack() {
        return stack.peek();
    }

}

public class Configurations {

    // Initalization should just find start state and create configuration for it,
    // and then log it in a hashmap

}
