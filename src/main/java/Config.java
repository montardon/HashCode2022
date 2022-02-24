
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Config {
    public List<Contributor> contributors = new ArrayList<>();
    public List<Project> projects = new ArrayList<>();

    public void createOutputFile(List<ProjectResult> res, String outFile) throws IOException {
        FileWriter fileWriter = new FileWriter(outFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(res.size());
        for (ProjectResult pr : res) {
            printWriter.println(pr.project.name);
            for (int idx = 0 ; idx < pr.contributors.size() ; ++idx) {
                printWriter.print(pr.contributors.get(idx).name);
                if (idx < pr.contributors.size() - 1) printWriter.print(" ");
            }
            printWriter.println();
        }
        printWriter.close();
    }

    public void parseFromFile(String file) throws FileNotFoundException {
        int nbProject = 0;
        int nbContrib = 0;
        contributors.clear();
        projects.clear();
        try (Scanner scan = new Scanner(new File(file))) {
            while (scan.hasNextLine()) {
                int[] nbs = Arrays.stream(scan.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                nbContrib = nbs[0];
                nbProject = nbs[1];
                // Contributors
                for (int idx = 0; idx < nbContrib; ++idx) {
                    Contributor ctrb = new Contributor();
                    String[] line = scan.nextLine().split(" ");
                    String ctrName = line[0];
                    ctrb.name = ctrName;
                    int nbSkills = Integer.parseInt(line[1]);
                    for (int s = 0; s < nbSkills; ++s) {
                        String[] lineSk = scan.nextLine().split(" ");
                        Skill skill = new Skill();
                        skill.name = lineSk[0];
                        skill.level = Integer.parseInt(lineSk[1]);
                        ctrb.skills.add(skill);
                    }
                    contributors.add(ctrb);
                }

                // projects
                for (int idx = 0; idx < nbProject; ++idx) {
                    Project proj = new Project();
                    String[] lineProj = scan.nextLine().split(" ");
                    proj.name = lineProj[0];
                    proj.duration = Integer.parseInt(lineProj[1]);
                    proj.score = Integer.parseInt(lineProj[2]);
                    proj.bestBefore = Integer.parseInt(lineProj[3]);
                    int nbRoles = Integer.parseInt(lineProj[4]);
                    for (int r = 0; r < nbRoles; ++r) {
                        String[] line = scan.nextLine().split(" ");
                        Skill role = new Skill();
                        role.name = line[0];
                        role.level = Integer.parseInt(line[1]);
                        proj.skills.add(role);
                    }
                    projects.add(proj);
                }
            }
        }
    }
}
