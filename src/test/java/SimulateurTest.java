import org.junit.jupiter.api.Assertions;
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

        ProjectResult pR = new ProjectResult();
        pR.contributors = List.of(maria);
        pR.project = logging;

        final Simulateur simulateur = new Simulateur();

        long result = simulateur.calculPoints(List.of(pR), List.of(maria));

        Assertions.assertEquals(5, result);
    }
}