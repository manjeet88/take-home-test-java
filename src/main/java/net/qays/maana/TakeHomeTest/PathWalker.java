package net.qays.maana.TakeHomeTest;

import java.io.File;
import java.nio.file.Files;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.java.truevfs.access.TFile;

@Builder
@Slf4j
public class PathWalker {

    private final File path;
    private final Boolean followLinks;

    public void walk() {
        log.debug("Walk down: {}, follow links: {}", path, followLinks);
        TFile entry = new TFile(path);
        if (entry.isDirectory()) {
            log.debug("entry is dir: {}", entry.toString());
        }
        for (TFile sub : entry.listFiles(x -> {
            return true;
        })) {
            log.debug("found: {}", sub.toString());
            log.debug("{}", sub.getName());
            log.debug("    is sym link: {}", Files.isSymbolicLink(sub.toPath()));
            if (sub.isDirectory()) { // also works for archives!
                log.debug("    is directory");

                PathWalker.builder().path(sub).followLinks(followLinks).build().walk();
            }
            if (sub.isFile()) {
                log.debug("    is file");
            }
        }
    }
}
