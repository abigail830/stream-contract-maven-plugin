package com.github.abigail830.contract.maven.plugin;

public class Contract {

    String name;
    String content;
    String provider;
    String consumer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }


    @Override
    public String toString() {
        return "Contract{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", provider='" + provider + '\'' +
                ", consumer='" + consumer + '\'' +
                '}';
    }
}
