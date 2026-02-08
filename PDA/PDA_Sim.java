package PDA;

import java.io.File;
import java.io.FileNotFoundException;

import PDA.PDA_Utilities.States;
import PDA.PDA_Utilities.Transitions;
import PDA.PDA_Utilities.Configurations;

public class PDA_Sim {
    File input;
    States state_handler;
    Transitions trans_handler;
    Configurations config_handler;

    public PDA_Sim(File input) throws FileNotFoundException {
        this.input = input;
        state_handler = new States(input);
        trans_handler = new Transitions(input);
        config_handler = new Configurations(trans_handler, state_handler);

    }

    // This takes input file and gives it to States and Transitions to store info
    public void load(String input_string) {
        state_handler.process_file();
        trans_handler.process_file();
        config_handler.process_file(input_string);
    }

    // This generates all of the possible configurations
    public void simulate() {
        config_handler.generateConfigurations();
    }

    // This finds if accept/reject and outputs results.
    public void output_results() {

    }

}
