package com.github.abigail830.contract.maven.plugin;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamContractDownloader {

    private static final Logger log = LoggerFactory.getLogger(StreamContractDownloader.class);

    private File targetContractDirectory;
    private String remoteUrl;
    private String[] providers;
    private String[] consumers;
    private String[] urls;
    private String groupId;

    public StreamContractDownloader(File targetContractDirectory, String groupId,
                                    String remoteUrl, String[] providers, String[] consumers, String[] urls) {
        this.targetContractDirectory = targetContractDirectory;
        this.remoteUrl = remoteUrl;
        this.providers = providers;
        this.consumers = consumers;
        this.urls = urls;
        this.groupId = groupId;
    }

    public void download() throws MojoFailureException {
        if(!shouldDownloadContracts()){
            log.warn("Either targetContractDirectory or remoteUrl are missing " +
                    "while they should be mandatory for trigger streamConvert goal.");
            return;
        }

        log.info("Going to start download contract");

        writeJsonToContractFiles(prepareTargetRootDirectory(
                this.targetContractDirectory),
                downloadContractToFile()
        );

    }

    private String downloadContractToFile() {
        log.info("- Remote URL: {}", this.remoteUrl);

        Contract contract = new Contract();
        contract.setName("should_get_actuator_health.yml");
        contract.setProvider("ContractProvider");
        contract.setConsumer("ContractConsumer1");
        contract.setContent("request:\n" +
                "  method: GET\n" +
                "  url: /info/health\n" +
                "  headers:\n" +
                "    Content-Type: application/json\n" +
                "\n" +
                "response:\n" +
                "  status: 200\n" +
                "  headers:\n" +
                "    Content-Type: application/json;charset=UTF-8\n" +
                "  body: |\n" +
                "    { \"status\": \"up\" }");

        Gson gson = new Gson();
        return gson.toJson(Arrays.asList(contract));

    }

    private void writeJsonToContractFiles(File targetdir, String downloadContractsJson) throws MojoFailureException {
        Gson gson = new Gson();
        ArrayList<Contract> contractArrayList = gson.fromJson(downloadContractsJson,
                new TypeToken<List<Contract>>(){}.getType());

        try{
            for(Contract contract : contractArrayList) {

                File contractFile = createContractFile(
                        this.targetContractDirectory + "/" +
                                contract.getProvider() + "/" +
                                contract.getConsumer() + "/" +
                                contract.getName());
                FileUtils.writeStringToFile(contractFile, contract.getContent(), "UTF-8", false);

            }

        } catch (IOException e) {
            log.warn("Fail to write contract to target Directory.");
            throw new MojoFailureException(e.getMessage(), e.getCause());
        }


    }

    private File createContractFile(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        return file;
    }

    private File prepareTargetRootDirectory(File targetContractDirectory) {
        if(!targetContractDirectory.exists()){
            log.debug("{} not exist, going to create directory.", targetContractDirectory);
            targetContractDirectory.mkdir();
        }
        log.info("- Target Contract Dir: {}", targetContractDirectory);

        return targetContractDirectory;
    }

    private boolean shouldDownloadContracts(){
        return this.targetContractDirectory != null && this.remoteUrl != null;
    }


}
