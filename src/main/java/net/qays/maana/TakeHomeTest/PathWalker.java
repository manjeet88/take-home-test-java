package net.qays.maana.TakeHomeTest;

import java.io.File;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class PathWalker {

    private final File path;
    private final Boolean followLinks;

    public void walk() {
        log.info("", path.toString());
    }
}
