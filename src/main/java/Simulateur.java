import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Simulateur {
    private static Logger LOGGER = Logger.getLogger(Simulateur.class.toString());

    public long calculPoints(List<ProjectResult> projects, List<Contributor> contributors) {
        Map<Contributor, Integer> dayOfAvailability = new HashMap<>();
        contributors.forEach(p -> dayOfAvailability.put(p, 0));

        long score = 0;

        for (ProjectResult prj : projects) {
            int start = prj.contributors.stream()
                    .map(c -> dayOfAvailability.getOrDefault(c, 0))
                    .max(Comparator.naturalOrder()).get();

            int end = start + prj.project.duration - 1;

            int prjScore = Math.max(0, prj.project.score - (end + 1 - prj.project.bestBefore));

            LOGGER.info("Project " + prj.project.name + " = (" + start + "/" + end + ") " + prjScore);

            prj.contributors.forEach(p -> dayOfAvailability.put(p, end+1));

            score += prjScore;
            LOGGER.info("Total score = " + score);
        }

        return score;
    }
}
