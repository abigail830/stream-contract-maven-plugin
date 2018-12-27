package com.github.abigail830.contract.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

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

    }
}
