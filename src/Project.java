import java.util.ArrayList;
import java.util.List;

public class Project {
    public String name;
    public int duration;
    public int score;
    public int bestBefore;
    public List<Role> roles = new ArrayList<>();

    public static class Role {
        public String name;
        public int minLevel;
    }
}
