package pl.piomin.services.files.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static java.nio.file.Files.list;
import static java.nio.file.Files.writeString;

@RestController
@RequestMapping("/files")
public class FilesController {

    private static final Logger LOG = LoggerFactory.getLogger(FilesController.class);

    @Value("${MOUNT_PATH:/mount/data}")
    String root;

    @GetMapping("/all")
    public List<String> files() throws IOException {
        return list(Path.of(root)).map(Path::toString).toList();
    }

    @PostMapping("/{name}")
    public String createFile(@PathVariable("name") String name) throws IOException {
        return Files.createFile(Path.of(root + "/" + name)).toString();
    }

    @PostMapping("/{name}/line")
    public void addLine(@PathVariable("name") String name,
                        @RequestBody String line) {
        try {
            writeString(Path.of(root + "/" + name), line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.error("Error while writing to file", e);
        }
    }
}
