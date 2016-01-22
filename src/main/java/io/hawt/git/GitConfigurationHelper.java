package io.hawt.git;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 */
public class GitConfigurationHelper extends GitFacade{

    public GitConfigurationHelper() {
    }

    public GitConfigurationHelper(String remoteRepository) throws Exception {
        this(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString(), remoteRepository);
    }

    public GitConfigurationHelper(String baseDirectory, String directory, String remoteRepository) throws Exception {
        File configDir = new File(baseDirectory, directory);
        configDir.mkdirs();
        setConfigDirectory(configDir);
        setRemoteRepository(remoteRepository);
        init();
    }

    public String getFileContent(String branchName, String filePath)
            throws IOException, GitAPIException {

        return getFileContent(branchName, null, filePath);
    }

    public String getFileContent(String branchName, String tag, String filePath)
            throws IOException, GitAPIException {

        FileContents contents = read(branchName, filePath);
        if(tag!=null && !tag.isEmpty()){
            return getContent(tag, filePath);
        } else {
            return contents.getText();
        }
    }

}
