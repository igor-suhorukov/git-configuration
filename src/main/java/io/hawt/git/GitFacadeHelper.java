package io.hawt.git;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 */
public class GitFacadeHelper {

    private static String getFileContent(GitFacade git, String branchName, String filePath) throws IOException, GitAPIException {
        FileContents contents = git.read(branchName, filePath);
        return contents.getText();
    }

    public static GitFacade createGit(String baseDirectory, String directory) {
        File configDir = new File(baseDirectory, directory);
        configDir.mkdirs();
        GitFacade gitFacade = new GitFacade();
        gitFacade.setConfigDirectory(configDir);
        return gitFacade;
    }

    public void printHistory(GitFacade git, String branch, String path) throws IOException, GitAPIException {
        List<CommitInfo> log = git.history(branch, null, path, 0);
        System.out.println("Showing history for path " + path);
        for (CommitInfo info : log) {
            System.out.println("  " + info);

            if (path != null && path.indexOf(".") > 0) {
                String content = git.getContent(info.getName(), path);
                System.out.println("    = " + content);
            }
        }
        System.out.println();
    }
}
