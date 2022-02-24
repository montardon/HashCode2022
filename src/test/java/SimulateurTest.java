import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulateurTest {
    @Test
    void testBasic() {


        Project logging = new Project();
        logging.name = "Logging";
        logging.bestBefore = 5;
        logging.duration = 5;
        logging.score = 10;
        logging.skills = new ArrayList<>();

        final Skill cpp = new Skill();
        cpp.name = "CPP";
        cpp.level = 3;
        logging.skills.add(cpp);

        Contributor maria = new Contributor();
        maria.name = "Maria";
        maria.skills = List.of(cpp);

        final Simulateur simulateur = new Simulateur();
        simulateur.calculPoints();
    }
}