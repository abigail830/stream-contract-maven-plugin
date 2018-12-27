package com.github.abigail830.contract.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

@Mojo(name = "streamConvert", requiresProject = false, defaultPhase = LifecyclePhase.PROCESS_TEST_RESOURCES)
public class StreamConvertMojo extends AbstractMojo {


    /**
     * Directory where the generated spring contracts should be placed.
     */
    @Parameter(defaultValue = Constant.DEFAULT_CONTRACT_DIR)
    private File targetContractDirectory;

    /**
     * The REST URL point to remote server for collect content of contracts
     */
    @Parameter
    private String remoteUrl;

    /**
     * To filter list of contracts belongs to all specified providers
     */
    @Parameter
    private String[] providers;

    /**
     * To filter list of contracts belongs to all specified consumers
     */
    @Parameter
    private String[] consumers;

    /**
     * To filter list of contracts belongs to all specified urls
     */
    @Parameter
    private String[] urls;

    /**
     * Flag using to skip the plugin execute function
     */
    @Parameter(property = "stream.contract.plugin.skip", defaultValue = "false")
    private boolean skip;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;


    public void execute() throws MojoExecutionException, MojoFailureException {

        if (this.skip) {
            getLog().info(String.format(
                    "Skipping Spring Cloud Contract Verifier execution: spring.cloud.contract.verifier.skip=%s",
                    this.skip));
            return;
        }

        String groupId = this.project.getGroupId();
        StreamContractDownloader streamContractDownloader = new StreamContractDownloader(targetContractDirectory,groupId,
                remoteUrl, providers, consumers, urls);
        try {
            streamContractDownloader.download();
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage(), e.getCause());
        }


    }
}
