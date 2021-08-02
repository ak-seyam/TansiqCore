package io.asiam.tansiq.services;

import org.springframework.stereotype.Service;

@Service
public interface ExternalVariablesSetup {
    String getVar(String varName);
}
