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
        Config config = new Config();
        config.parseFromFile("/home/jlacoste/CODE/HashCode/a_an_example.in.txt");
    }   

}
