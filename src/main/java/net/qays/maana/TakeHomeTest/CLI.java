package net.qays.maana.TakeHomeTest;

import java.io.File;
import java.util.Optional;
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
	static Logger logger = Logger.getAnonymousLogger(CLI.class.getCanonicalName());

	public static void main(String[] args) {
		Options options = new Options();

		options.addOption(Option.builder("l").longOpt("loglevel").desc("Level of verbosity in logs.").hasArg()
				.argName("level").numberOfArgs(1).type(Integer.class).build());
		options.addOption(Option.builder("h").longOpt("help").desc("print this message").build());
		options.addOption(Option.builder("f").longOpt("followlinks").desc("Follow sym links").hasArg()
				.argName("followlinks").numberOfArgs(1).type(Boolean.class).build());
		options.addOption(Option.builder("p").longOpt("path").desc("Top of the tree you want crawled").hasArg()
				.argName("path").numberOfArgs(1).required().type(File.class).build());

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

			File path = (File) line.getParsedOptionValue("path");
			Optional<Integer> loglevel = Optional.of((Integer) line.getParsedOptionValue("loglevel"));
			Optional<Boolean> followlinks = Optional.of((Boolean) line.getParsedOptionValue("followlinks"));

			processPath(path, loglevel, followlinks);
		} catch (ParseException exp) {
			// oops, something went wrong
			logger.log(Level.SEVERE, "test");
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(CLI.class.getCanonicalName(), options);
			return;
		}
	}

	private static void processPath(File parsedOptionValue, Optional<Integer> loglevel, Optional<Boolean> followlinks) {

	}

}
