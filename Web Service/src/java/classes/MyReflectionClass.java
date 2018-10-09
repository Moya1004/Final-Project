/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import model.JavaClassModel;

/**
 *
 * @author Moya
 */
public class MyReflectionClass {

    /**
     * @param args the command line arguments
     */
    
    private JavaClassModel javaClassModel;
    private Class clazz;
    
    
    public MyReflectionClass(JavaClassModel model)
    {
        this.javaClassModel = model;
        setClassObject();
    }
    
    public void setClassObject()
    {
        try{
            File file = new File(this.javaClassModel.getRootDirectory()+"\\");
            URL[] urls = {file.toURL()};
            URLClassLoader loader = new URLClassLoader(urls);
            String packagee = javaClassModel.getClassPackage().replace(".class","");
            this.clazz = loader.loadClass(packagee);
        }
        catch(Exception ex)
        {
            
        }
    }
    
    
    public Class getClassObject()
    {
        return clazz;
    }
    
    public Annotation[] getAnnotations()
    {
        return null;
    }
    
    
}
