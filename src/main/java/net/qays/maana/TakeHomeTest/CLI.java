package net.qays.maana.TakeHomeTest;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLI {

    private static final String OPT_LONG_HELP = "help";
    private static final String OPT_SHORT_HELP = "h";
    private static final String OPT_LONG_FOLLOWLINKS = "followlinks";
    private static final String OPT_SHORT_FOLLOWLINKS = "f";
    private static final String OPT_LONG_PATH = "path";
    private static final String OPT_SHORT_PATH = "p";
    private static final String OPT_LONG_LOGLEVEL = "loglevel";
    private static final String OPT_SHORT_LOGLEVEL = "l";

    static Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        Options options = new Options();

        options.addOption(
                Option.builder(OPT_SHORT_LOGLEVEL).longOpt(OPT_LONG_LOGLEVEL).desc("Level of verbosity in logs.")
                        .hasArg().argName("ALL | FINEST | FINER | FINE | CONFIG | INFO | WARNING | SEVERE | OFF")
                        .numberOfArgs(1).type(String.class).build());
        options.addOption(Option.builder(OPT_SHORT_HELP).longOpt(OPT_LONG_HELP).desc("print this message").build());
        options.addOption(Option.builder(OPT_SHORT_FOLLOWLINKS).longOpt(OPT_LONG_FOLLOWLINKS).desc("Follow sym links")
                .hasArg().argName("TRUE | FALSE").numberOfArgs(1).type(Boolean.class).build());
        options.addOption(Option.builder(OPT_SHORT_PATH).longOpt(OPT_LONG_PATH).desc("Top of the tree you want crawled")
                .hasArg().argName("~/path/to/dir/").numberOfArgs(1).required().type(File.class).build());

        // create the parser
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            if (line.hasOption('h')) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(CLI.class.getCanonicalName(), options);
                return;
            }

            File path = (File) line.getParsedOptionValue(OPT_LONG_PATH);
            String loglevel = line.getOptionValue(OPT_LONG_LOGLEVEL, "WARNING");
            String followlinks = line.getOptionValue(OPT_LONG_FOLLOWLINKS, "false");

            logger.setLevel(Level.parse(loglevel));

            processPath(path, Boolean.parseBoolean(followlinks));
        } catch (ParseException exp) {
            // oops, something went wrong
            logger.log(Level.SEVERE, "Unable to parse command line options.");
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(CLI.class.getCanonicalName(), options);
            return;
        }
    }

    private static void processPath(File parsedOptionValue, Boolean followLinks) {

    }

}
