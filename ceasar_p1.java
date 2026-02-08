import PDA.PDA_Sim;

import java.io.File;
import java.io.FileNotFoundException;

public class ceasar_p1 {
    public static void main(String[] args) throws FileNotFoundException {

        // Can only take in one file
        if (args.length != 2) {
            System.err.println("Usage: java ~.java <pda.txt> <input_string>");
            return;
        }

        File input_file = new File(args[0]);

        // Make sure it exists
        if (!input_file.exists()) {
            System.out.println("Error: The specified file does not exist: " + args[0]);
            System.exit(1);
        }

        if (!input_file.isFile()) {
            System.out.println("Error: The specified path is not a file: " + args[0]);
            System.exit(1);
        }

        // Create pda_sim and pass in file

        PDA_Sim pda_sim_handler = new PDA_Sim(input_file);

        pda_sim_handler.load(args[1]);

    }

}
