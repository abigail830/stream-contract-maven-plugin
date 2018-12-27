package com.github.abigail830.contract.maven.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

@Mojo(name = "cleanContract", requiresProject = false, defaultPhase = LifecyclePhase.CLEAN)
public class CleanContractMojo extends AbstractMojo {


    /**
     * Directory where the generated spring contracts should be placed.
     */
    @Parameter(defaultValue = Constant.DEFAULT_CONTRACT_DIR)
    private File targetContractDirectory;

    /**
     * Flag using to skip the plugin execute function
     */
    @Parameter(property = "stream.contract.plugin.skip", defaultValue = "false")
    private boolean skip;


    public void execute() throws MojoExecutionException, MojoFailureException {

        if (this.skip) {
            getLog().info(String.format(
                    "Skipping Spring Cloud Contract Verifier execution: spring.cloud.contract.verifier.skip=%s",
                    this.skip));
            return;
        }

        try {
            FileUtils.deleteDirectory(targetContractDirectory);
        } catch (IOException e) {
            getLog().warn("Fail to clean up contract directory.");
            throw new MojoFailureException(e.getMessage(), e.getCause());
        }
    }
}
