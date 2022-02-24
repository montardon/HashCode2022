import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HashCodeMain {
    
    public static void main(String[] args) throws FileNotFoundException {
        Map<String, Contributor> contributors = new HashMap<>();
        int nbContrib = 0;
        int nbProject = 0;
        try (Scanner scan = new Scanner(new File("/home/jlacoste/CODE/HashCode/a_an_example.in"))) {
            while (scan.hasNextLine()) {
                int[] nbs = Arrays.stream(scan.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                nbContrib = nbs[0];
                nbProject = nbs[1]; 
                // Contributors
                for (int idx = 0 ; idx < nbContrib ; ++idx) {
                    Contributor ctrb = new Contributor();
                    String[] line = scan.nextLine().split(" ");
                    String ctrName = line[0];

                }
            }
        }
    }    

}
