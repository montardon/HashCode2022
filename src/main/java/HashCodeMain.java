import java.io.IOException;
import java.util.List;

public class HashCodeMain {
    
    public static void main(String[] args) throws IOException {
        Config config = new Config();
        String file = "/home/jlacoste/CODE/HashCode/a_an_example.in.txt";
        config.parseFromFile(file);

        //
        Solveur solveur = new Solveur();
        List<ProjectResult>  res = solveur.solve(config.contributors, config.projects);

        config.createOutputFile(res, file + ".out");
    }   

}
