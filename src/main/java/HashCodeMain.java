import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class HashCodeMain {

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        try (Stream<Path> paths = Files.walk(Paths.get("/home/jlacoste/CODE/HashCode/"))) {
            paths.filter(Files::isRegularFile).forEach(f -> {
                if (f.getFileName().endsWith(".in.txt")) {
                    try {
                        config.parseFromFile(f.getFileName().toAbsolutePath().toString());
                        Solveur solveur = new Solveur();
                        List<ProjectResult> res = solveur.solve(config.contributors, config.projects);
                        final long result = new Simulateur().calculPoints(res, config.contributors);
                        System.out.println("-> Points = " + result);

                        config.createOutputFile(res, f + ".out");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }   

}
