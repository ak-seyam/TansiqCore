package io.asiam.tansiq.services;

public class EnvironmentVariablesSetup implements ExternalVariablesSetup{
    @Override
    public String getVar(String varName) {
        return System.getenv(varName);
    }
}
