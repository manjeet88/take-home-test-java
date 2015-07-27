package net.qays.maana.TakeHomeTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileReader;

@Builder
@Slf4j
public class PathWalker {

    private final File path;
    private final Boolean followLinks;

    private static final Map<Integer, Integer> counts = new HashMap<>();

    public void walk() {
        log.debug("Walk down: {}, follow links: {}", path, followLinks);
        TFile entry = new TFile(path);
        if (entry.isDirectory()) {
            log.debug("entry is dir: {}", entry.toString());
        }
        for (TFile sub : entry.listFiles(file -> {
            if (!followLinks) {
                boolean isSymbolicLink = Files.isSymbolicLink(new File(file.getAbsolutePath()).toPath());
                log.debug("{}: is sym link: {}", file.getName(), isSymbolicLink);
                return !isSymbolicLink;
            }
            return true;
        })) {
            log.debug("found: {}", sub.toString());
            log.debug("{}", sub.getName());
            if (Files.isSymbolicLink(new File(sub.getAbsolutePath()).toPath())) {
                log.debug("    is sym link");
                log.debug("    is sym link: {}", Files.isSymbolicLink(new File(sub.getAbsolutePath()).toPath()));
            }
            if (sub.isDirectory()) { // also works for archives!
                log.debug("    is directory");
                PathWalker.builder().path(sub).followLinks(followLinks).build().walk();
            }
            if (sub.isFile()) {
                log.debug("    is file");
                if (sub.getName().endsWith(".txt")) {
                    readFile(sub);
                }
            }
        }
    }

    private void readFile(TFile file) {
        try {
            Reader reader = new TFileReader(file);
            // TODO: could handle additional charsets here
            try (BufferedReader br = new BufferedReader(reader)) {
                String line;
                while ((line = br.readLine()) != null) {
                    Lists.newArrayList(line.split(" ")).forEach(word -> {
                        log.trace("{}: {}", word.length(), word);
                        counts.computeIfPresent(word.length(), (key, value) -> {
                            return value + 1;
                        });
                        counts.putIfAbsent(word.length(), 1);
                    });
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Map<Integer, Integer> getCounts() {
        return counts;
    }
}
