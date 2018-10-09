/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Moya
 */
public class TestResultModel {
    
    private double successRate;
    private String failureMessage;

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public TestResultModel(double successRate, String failureMessage) {
        this.successRate = successRate;
        this.failureMessage = failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
    
}
