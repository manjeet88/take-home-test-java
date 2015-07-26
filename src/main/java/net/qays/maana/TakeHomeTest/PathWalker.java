package net.qays.maana.TakeHomeTest;

import java.io.File;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class PathWalker {

    private final File path;
    private final Boolean followLinks;

    public void walk() {
        log.debug("Walk down: {}, follow links: {}", path, followLinks);
        try {
            // Locate the Jar file
            FileSystemManager fsManager = VFS.getManager();
            FileObject jarFile = fsManager.resolveFile(new File(System.getProperty("user.home")), path.getPath());

            // List the children of the Jar file
            FileObject[] children = jarFile.getChildren();
            System.out.println("Children of " + jarFile.getName().getURI());
            for (int i = 0; i < children.length; i++) {
                System.out.println(children[i].getName().getBaseName());
            }
        } catch (FileSystemException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
