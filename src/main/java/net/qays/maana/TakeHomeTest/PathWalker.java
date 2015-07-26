package net.qays.maana.TakeHomeTest;

import java.io.File;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class PathWalker {

    private static final String REGEX_ZIP_TGZ_TARGZIP = "(zip|ZIP)";
    private final File path;
    private final Boolean followLinks;

    public void walk() {
        log.debug("Walk down: {}, follow links: {}", path, followLinks);
        try {
            // Locate the file
            FileSystemManager fsManager = VFS.getManager();
            FileObject fileObject = fsManager.resolveFile(path.getPath());

            // List the children of the file
            log.trace("Children of {}", fileObject.getName().getURI());
            for (FileObject child : fileObject.getChildren()) {
                log.trace("Child: {}", child.getName().getBaseName());
                if (shouldRecurse(child)) {
                    PathWalker subPath = PathWalker.builder().path(new File(child.toString())).followLinks(followLinks)
                            .build();
                    subPath.walk();
                }
            }
        } catch (FileSystemException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean shouldRecurse(FileObject file) throws FileSystemException {
        if (file.getType() == FileType.FOLDER)
            return true;
        if (file.getType() == FileType.FILE) {
            if (file.getName().getExtension().matches(REGEX_ZIP_TGZ_TARGZIP)) {
                FileSystemManager fsManager = VFS.getManager();
                FileObject fileObject = fsManager.resolveFile(file);

                log.debug("");
                return true;
//            BufferedReader br = new BufferedReader(new InputStreamReader(file.getContent().getInputStream()));
//            try {
//                String strLine;
//                while ((strLine = br.readLine()) != null) {
//                    // Print the content on the console
//                    System.out.println(strLine);
//                }
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            }
        }
        return false;
    }
}
