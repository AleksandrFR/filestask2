import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(List<String> paths, List<GameProgress> players) {

        for (int i = 0; paths.size() > i; i++) {
            try (FileOutputStream fos = new FileOutputStream(paths.get(i));
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(players.get(i).toString());

            } catch (Exception ex) {
                System.out.println(ex.getMessage(
                ));
            }
        }
    }

    public static void zipFiles(String path, List<String> files) {
        try (
                ZipOutputStream zout = new ZipOutputStream(new
                        FileOutputStream(path))) {


            for (int i = 0; files.size() > i; i++) {

                try
                        (FileInputStream fis = new FileInputStream(files.get(i))) {
                    ZipEntry entry = new ZipEntry("save_player" + (i + 1) + ".txt");

                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);

                    zout.closeEntry();
                                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<GameProgress> players = Arrays.asList(
                new GameProgress(54, 1234, 15, 33.2),
                new GameProgress(22, 1123, 25, 54.2),
                new GameProgress(67, 4, 78, 11147.7)
        );

        List<String> paths = Arrays.asList(
                "C:\\Users\\sasha\\IdeaProjects\\Games\\savegames\\player1.dat",
                "C:\\Users\\sasha\\IdeaProjects\\Games\\savegames\\player2.dat",
                "C:\\Users\\sasha\\IdeaProjects\\Games\\savegames\\player3.dat"
        );

        String zipFile = "C:\\Users\\sasha\\IdeaProjects\\Games\\savegames\\zip_output.zip";

        saveGame(paths, players);
        zipFiles(zipFile, paths);

        for (String path : paths) {
            try {
                Files.delete(Path.of(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

