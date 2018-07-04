package com.surgical.decision3.common.bean.reasoner;

/**
 * Created by mess on 5/30/2017.
 */

public class Reasoner {
    public String toAxioms(String parsedPDDL){
        String test = getClass().getSimpleName() + "\n" + generateSolutionPath(parsedPDDL);
        return test;
    }

    private String generateSolutionPath(String parsedPDDL) {
        return "Reasoner.generateSolutionPath()";
    }
}
