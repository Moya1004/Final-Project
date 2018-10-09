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
public class JavaClassModel {
    private String className;
    private String classPackage;
    private String rootDirectory;

    
    public JavaClassModel(String className , String classPackage,String rootDirectory)
    {
        this.className = className.replace(".class", "") ;
        this.classPackage = classPackage;
        this.rootDirectory = rootDirectory;
    }


    public String getClassName() {
        return className;
    }

    public void setFilePath(String className) {
        this.className = className;
    }

    public String getClassPackage(){
        return classPackage;
    }

    public void setClassPackage(String FilePackage) {
        this.classPackage = FilePackage;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
}
