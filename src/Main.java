import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<File> list = Arrays.asList(new File("C:/Games/Game/savegames/save0.dat"),
                new File("C:/Games/Game/savegames/save1.dat"),
                new File("C:/Games/Game/savegames/save2.dat"));
        GameProgress[] gp = {new GameProgress(100, 1, 25, 1.5),
                new GameProgress(77, 2, 20, 5.0),
                new GameProgress(50, 3, 17, 4.4)};
        saveGame(gp);
        zipFiles(gp);
       deleteFiles(list);
    }


    public static void saveGame(GameProgress[] gp) {
        for (int i = 0; i < gp.length; i++) {
            try (FileOutputStream f = new FileOutputStream("C:/Games/Game/savegames/save" + i + ".dat");
                 ObjectOutputStream oos = new ObjectOutputStream(f)) {
                oos.writeObject(gp[i]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void zipFiles(GameProgress[] gp) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("C:/Games/Game/savegames/zip_output.zip"))) {
            for (int i = 0; i < gp.length; i++) {
                try (FileInputStream fis = new FileInputStream("C:/Games/Game/savegames/save" + i + ".dat")) {
                    ZipEntry entry = new ZipEntry("save" + i + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFiles(List<File> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                Files.delete(Path.of("C:/Games/Game/savegames/save" + i + ".dat"));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}





