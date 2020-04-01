import languageRecognition.Text;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TextLoader {

    public static List<Text> getTextList(Path dirPath) {
        List<Text> texts = new ArrayList<>();
        // =====

        try (Stream<Path> pathStream = Files.walk(dirPath)) {
            List<Path> dirList = pathStream.filter(Files::isDirectory).collect(Collectors.toList());
            dirList.remove(dirPath);

            // iterating over each language directory
            for (Path path : dirList) {
                String name = path.toString().substring(dirPath.toString().length() + 1);

                try (Stream<Path> textPathStream = Files.walk(path)) {
                    List<Path> textPathList = textPathStream
                            .filter(Files::isRegularFile)
                            .collect(Collectors.toList());

                    // iterating over each text file
                    for (Path textPath : textPathList) {
                        String content = null;

                        try (FileChannel fcIn = FileChannel.open(textPath)) {
                            ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) fcIn.size());
                            fcIn.read(byteBuffer);
                            byteBuffer.flip();
                            content = StandardCharsets.UTF_8.decode(byteBuffer).toString();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        // adding each text to the list
                        texts.add(new Text(name, content));
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // =====
        return texts;
    }
}
