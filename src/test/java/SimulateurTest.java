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

        Assertions.assertEquals(10, result);
    }

    @Test
    void testFirst() {

        Project webserver = new Project();
        webserver.name = "webserver";
        webserver.bestBefore = 7;
        webserver.duration = 7;
        webserver.score = 10;
        webserver.skills = new ArrayList<>();

        {
            final Skill html = new Skill();
            html.name = "HTML";
            html.level = 3;
            webserver.skills.add(html);

            final Skill cpp = new Skill();
            cpp.name = "CPP";
            cpp.level = 2;
            webserver.skills.add(cpp);
        }

        Project logging = new Project();
        logging.name = "Logging";
        logging.bestBefore = 5;
        logging.duration = 5;
        logging.score = 10;
        logging.skills = new ArrayList<>();

        final Skill cpp = new Skill();
        cpp.name = "CPP";
        cpp.level = 2;
        logging.skills.add(cpp);

        Contributor anna = new Contributor();
        anna.name = "Anna";
        anna.skills = List.of(cpp);

        Contributor bob = new Contributor();
        bob.name = "Bob";
        Skill html = new Skill();
        html.level = 5;
        html.name = "HTML";
        bob.skills = List.of(html);


        ProjectResult wS = new ProjectResult();
        wS.contributors = List.of(bob, anna);
        wS.project = webserver;

        ProjectResult pR = new ProjectResult();
        pR.contributors = List.of(anna);
        pR.project = logging;

        final Simulateur simulateur = new Simulateur();

        long result = simulateur.calculPoints(List.of(wS,pR), List.of(anna, bob));

        Assertions.assertEquals(13, result);
    }

}