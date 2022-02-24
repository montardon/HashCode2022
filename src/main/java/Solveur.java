import java.util.*;
import java.util.logging.Logger;

public class Solveur {
    private static Logger LOGGER = Logger.getLogger(Solveur.class.toString());

    public List<ProjectResult> solve(List<Contributor> contributors, List<Project> projects) {
        projects.sort(Comparator.comparingInt(p -> p.score - p.duration - p.skills.size()));

        Map<Contributor, Integer> dayOfAvailability = new HashMap<>();
        contributors.forEach(p -> dayOfAvailability.put(p, 0));

        List<ProjectResult> results = new ArrayList<>();

        for (Project p : projects) {
            final ProjectResult projectResult = new ProjectResult();

            projectResult.project = p;
            projectResult.contributors = new ArrayList<>();

            List<Contributor> currentContribs = new ArrayList<>(contributors);

            for (Skill skill : p.skills) {
                final boolean mentor = projectResult.contributors.stream().anyMatch(c -> c.skills.stream().anyMatch(s -> s.name.equals(skill.name) && s.level >= skill.level));
                Optional<Contributor> contrib = contributors.stream()
                        .filter(c -> c.skills.stream()
                                .anyMatch(s -> s.name.equals(skill.name) && (s.level >= skill.level || (mentor && s.level + 1 >= skill.level)) ))
                        .sorted(Comparator.comparingInt(c -> dayOfAvailability.get(c)))
                        .findAny();

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
                int start = projectResult.contributors.stream()
                        .map(c -> dayOfAvailability.getOrDefault(c, 0))
                        .max(Comparator.naturalOrder()).get();

                int end = start + projectResult.project.duration - 1;

                results.add(projectResult);
                projectResult.contributors.forEach(c -> dayOfAvailability.put(c, end));

                for (int i = 0; i < projectResult.contributors.size(); i++) {
                        int level = projectResult.project.skills.get(i).level;
                    final Contributor contributor = projectResult.contributors.get(i);
                    if (
                                contributor.skills.get(i).level == level
                                || contributor.skills.get(i).level == level - 1
                        ) {
                            contributors.get(contributors.indexOf(contributor)).skills.get(i).level ++;
                        }
                }

            } else {
                LOGGER.warning(p.name + " pas possible d'avoir ces skills");
            }

        }

        return results;
    }
}
