package net.qays.maana.TakeHomeTest;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLI {
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
			}
		} catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
		}
	}
}
