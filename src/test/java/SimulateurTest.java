import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulateurTest {
    @Test
    void testBasic() {

        Project logging = new Project();
        logging.name = "Logging";
        logging.bestBefore = 5;
        logging.duration = 5;
        logging.score = 10;
    }
}