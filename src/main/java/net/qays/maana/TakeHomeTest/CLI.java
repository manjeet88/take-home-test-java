package net.qays.maana.TakeHomeTest;

import static java.lang.String.format;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CLI {

    private static final String REGEX_TILDE_PATH = "^~";
    private static final String OPT_LONG_HELP = "help";
    private static final String OPT_SHORT_HELP = "h";
    private static final String OPT_LONG_FOLLOWLINKS = "followlinks";
    private static final String OPT_SHORT_FOLLOWLINKS = "f";
    private static final String OPT_LONG_PATH = "path";
    private static final String OPT_SHORT_PATH = "p";

    public static void main(String[] args) {
        Options options = new Options();

        options.addOption(Option.builder(OPT_SHORT_HELP).longOpt(OPT_LONG_HELP).desc("print this message").build());
        options.addOption(Option.builder(OPT_SHORT_FOLLOWLINKS).longOpt(OPT_LONG_FOLLOWLINKS).desc("Follow sym links")
                .hasArg(false).build());
        options.addOption(Option.builder(OPT_SHORT_PATH).longOpt(OPT_LONG_PATH).desc("Top of the tree you want crawled")
                .hasArg().argName("~/path/to/dir/").numberOfArgs(1).required().build());

        // create the parser
        CommandLineParser parser = new DefaultParser();
        CommandLine line;
        File path;
        try {
            // parse the command line arguments
            line = parser.parse(options, args);
            path = new File(
                    line.getOptionValue(OPT_LONG_PATH).replaceFirst(REGEX_TILDE_PATH, System.getProperty("user.home")));

            if (!path.exists() || !path.canRead()) {
                log.error("Unable to read file: {}", path.getAbsolutePath());
                throw new ParseException(format("Unable to read file: %s", path.getAbsolutePath()));
            }
        } catch (ParseException exp) {
            // oops, something went wrong
            log.error("Unable to parse command line options.");
            log.error("Parsing failed.  Reason: {}", exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            System.out.println(exp.getMessage());
            formatter.printHelp(CLI.class.getCanonicalName(), options);
            return;
        }
        if (line.hasOption('h')) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(CLI.class.getCanonicalName(), options);
            return;
        }
        Boolean followlinks = line.hasOption(OPT_LONG_FOLLOWLINKS);

        log.info("path: {}, follow links: {}", path, followlinks);
        PathWalker pathWalker = PathWalker.builder().path(path).followLinks(followlinks).build();
        pathWalker.walk();
    }
}
