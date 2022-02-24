import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Solveur {
    private static Logger LOGGER = Logger.getLogger(Solveur.class.toString());

    public List<ProjectResult> solve(List<Contributor> contributors, List<Project> projects) {
        projects.sort(Comparator.comparingInt(p -> p.score));

        List<ProjectResult> results = new ArrayList<>();

        for (Project p : projects) {
            final ProjectResult projectResult = new ProjectResult();

            projectResult.project = p;
            projectResult.contributors = new ArrayList<>();

            List<Contributor> currentContribs = new ArrayList<>(contributors);

            for (Skill skill : p.skills) {
                Optional<Contributor> contrib = contributors.stream().filter(c -> c.skills.stream().anyMatch(s -> s.level >= skill.level)).findAny();

                if (contrib.isEmpty()) {
                    LOGGER.warning("project " + p.name + " aucun contrib pour " + skill.name + " !!!!!!!!!!!!!!!!!!!!");
                    break;
                } else {
                    LOGGER.info("project " + p.name + " "  + contrib.get().name + " pour " + skill.name);
                    projectResult.contributors.add(contrib.get());
                    contributors.remove(contrib.get());
                }
            }

            if (projectResult.contributors.size() == p.skills.size()) {
                results.add(projectResult);
            } else {
                LOGGER.warning(p.name + " pas possible d'avoir ces skills");
            }

        }

        return results;
    }
}
