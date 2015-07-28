package net.qays.maana.TakeHomeTest;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PathWalkerTest {
    private Boolean followLinks;
    private File file;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test1() throws Exception {
        file = folder.newFile("foo.txt");

        List<String> lines = new ArrayList<>();
        lines.add("print('foobar');");
        Files.write(file.toPath(), lines);

        PathWalker walker = PathWalker.builder().path(file).followLinks(followLinks).build();
    }
}