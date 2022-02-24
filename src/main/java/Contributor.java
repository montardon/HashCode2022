import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contributor {
    public String name;
    List<Skill> skills = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contributor that = (Contributor) o;

        if (!name.equals(that.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
