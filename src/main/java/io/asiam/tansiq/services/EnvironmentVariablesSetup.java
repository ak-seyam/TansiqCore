package io.asiam.tansiq.services;

import org.springframework.stereotype.Service;

@Service
public class EnvironmentVariablesSetup implements ExternalVariablesSetup{
    @Override
    public String getVar(String varName) {
        return System.getenv(varName);
    }
}
