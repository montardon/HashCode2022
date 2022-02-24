import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Solveur {
    private static Logger LOGGER = Logger.getLogger(Solveur.class.toString());

    public List<ProjectResult> solve(List<Contributor> contributors, List<Project> projects) {
        Set<String> skills = projects.stream().flatMap(p -> p.skills.stream().map(s -> s.name))
                        .collect(Collectors.toSet());

        for (Contributor contributor : contributors) {
            final Set<String> skillsContrib = contributor.skills.stream().map(s -> s.name).collect(Collectors.toSet());
            Set<String> toAdd = new HashSet<>(skills);
            toAdd.removeAll(skillsContrib);

            toAdd.forEach(s -> {
                Skill skill = new Skill();
                skill.name = s;
                skill.level = 0;
                contributor.skills.add(skill);
            });
        }

        projects.sort(Comparator.comparingDouble(p -> p.score - p.duration - p.skills.size() - p.skills.stream().mapToInt(s->s.level).summaryStatistics().getAverage()));

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
                    final Skill skillProject = projectResult.project.skills.get(i);
                    int level = skillProject.level;
                    final Contributor contributor = projectResult.contributors.get(i);

                    int idx = contributor.skills.indexOf(skillProject);
                    if (idx < 0) {
                        final Skill s = new Skill();
                        s.level = 1;
                        s.name = skillProject.name;
                        contributor.skills.add(s);
                    } else {
                        final Skill skillContrib = contributor.skills.get(idx);
                        if (
                                skillContrib.level == level
                                        || skillContrib.level == level - 1
                        ) {
                            skillContrib.level++;
                        }
                    }
                }

            } else {
                LOGGER.warning(p.name + " pas possible d'avoir ces skills");
            }

        }

        return results;
    }
}
