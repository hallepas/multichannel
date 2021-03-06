package message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;
    private final byte[] content;
    private final String name;

    public Attachment(String path) throws IOException {
        this.name = new File(path).getName();
        content = Files.readAllBytes(Paths.get(path));
    }

    public byte[] getContent() {
        return content;
    }

    public String getFileName() {
        return name;
    }

    public void saveAttachment(String path) throws IOException {
        if (!(new File(path).isDirectory())) {
            throw new IOException("Path needs to be a directory");
        }
        FileOutputStream fos = new FileOutputStream(path + File.separator
                + name);
        fos.write(content);
        fos.close();
    }

}
