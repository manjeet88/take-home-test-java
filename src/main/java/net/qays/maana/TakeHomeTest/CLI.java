package net.qays.maana.TakeHomeTest;

import java.io.File;
import java.io.PrintWriter;
import java.util.Optional;

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
                .hasArg().argName("~/path/to/dir/").numberOfArgs(1).required().type(File.class).build());
        options.addOption("C", false, "list entries by columns");

        // create the parser
        CommandLineParser parser = new DefaultParser();
        Optional<CommandLine> line;
        Optional<File> path;
        try {
            // parse the command line arguments
            line = Optional.ofNullable(parser.parse(options, args));
            path = Optional.ofNullable((File) line.map(x -> {
                try {
                    return x.getParsedOptionValue(OPT_LONG_PATH);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return Optional.empty();
            }).orElse(Optional.empty()));
        } catch (ParseException exp) {
            // oops, something went wrong
            log.error("Unable to parse command line options.");
            log.error("Parsing failed.  Reason: {}", exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(CLI.class.getCanonicalName(), options);
            formatter.printWrapped(new PrintWriter(System.out), 80, exp.getMessage());
            return;
        }
        line.ifPresent(m -> {
            if (m.hasOption('h')) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(CLI.class.getCanonicalName(), options);
                return;
            }
            Boolean followlinks = m.hasOption(OPT_LONG_FOLLOWLINKS);
            log.debug("path: {}\nfollow links: {}", path, followlinks);
            path.ifPresent(p -> {
                PathWalker pathWalker = PathWalker.builder().path(p).followLinks(followlinks).build();
                pathWalker.walk();
            });
        });
    }
}
