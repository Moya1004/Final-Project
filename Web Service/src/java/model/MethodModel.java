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
public class MethodModel {
    private String methodName ;
    private String returnType ;
    private String[] parameters;
    private String[] annotationTypes;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public MethodModel(String methodName, String returnType, String[] parameters, String[] annoationType) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameters = parameters;
        this.annotationTypes = annoationType;
    }

    public String[] getAnnoationTypes() {
        return annotationTypes;
    }

    public void setAnnoationTypes(String[] annoationTypes) {
        this.annotationTypes = annoationTypes;
    }
    
}
