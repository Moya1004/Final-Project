/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import FileHandlers.JavaFileHandler;
import calc.ws.Calculate;
import compilers.MyJavaCompiler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam;
import model.JavaClassModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author Moya
 */
public class DeleteTestClass {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, JSONException
    {
        /*
        JUnitCore junit = new JUnitCore();
        URL[] urls = {(new File("C:\\Users\\Moya\\Documents\\NetBeansProjects\\TestApplication\\test\\")).toURL()};
        URL[] urls2 = { (new File("C:\\Users\\Moya\\Documents\\NetBeansProjects\\TestApplication\\src\\").toURL())};
        URLClassLoader loader = new URLClassLoader(urls);
        URLClassLoader loader2 = new URLClassLoader(urls2);
        Class clazz2 = loader2.loadClass("javaapplication1.EmpBusinessLogic");
        Class clazz = loader.loadClass("TestEmployeeDetails");
        Result result = junit.run(clazz,clazz2);
        System.out.println(result.getFailureCount() + " " + result.getRunCount());
        
        List<Failure> failures = result.getFailures();
        
        for (Failure failure : failures)
        {
            System.out.println(failure.getTestHeader() + "     "+ failure.getMessage());
        }

        String[] command = {"jar","cvf","zzzz.jar","*"};
        ProcessBuilder builder = new ProcessBuilder(command);
        builder = builder.directory(new File("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\build\\"));
        Process runtime = builder.start();
                 */
       /* Path path = Paths.get("C:\\Users\\Moya\\Documents\\NetBeansProjects\\Newfolder\\JUnit-master.zip");
        byte[] data = Files.readAllBytes(path);
        Calculate calculate = new Calculate();
        calculate.CalculateClass("JUnit-master.zip", data);*/
        SocketServer ss = new SocketServer("localhost",9090);
    }
    
    /*public static String CalculateClass(String fileName) throws ClassNotFoundException, IOException, JSONException {
        String result = "";
        try{
          //try{
            //FileOutputStream fos = new FileOutputStream("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\"+ fileName);
            //fos.write(fileArray);
            //fos.close();
        //}
        //catch(Exception ex)
        //{
            //return "Error Occured While Uploading file to the system";
        //}
        //File file = new File("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\"+fileName);
        
        /*if(file.exists())
        {
            int resultOfUnzip = UnzipFile.unzip("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\"+fileName,
                            "C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\Uncompressed");
            
            if (resultOfUnzip != 0)
            {
                return "The File Couldn't be Unzipped";
            }
        }
        if (file.exists())
            file.delete();
        
        String filePath = "C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\Uncompressed"+fileName.replace(".zip", "")+"\\";
        
        JavaFileHandler fileHandler ;
        ArrayList<String> javaFiles  ;
        ArrayList<String> jarFiles;
        String classPath ;
        
        try{
            fileHandler = new JavaFileHandler(filePath);
        }
        catch(Exception ex)
        {
            return "1";
        }
        
        try{
            javaFiles = fileHandler.getJavaFiles();
        }
        catch(Exception ex)
        {
            return "2";
        }
        
        try{
            jarFiles = fileHandler.getJarFiles();
        }
        catch(Exception ex)
        {
            return "3";
        }
        try{
            classPath = fileHandler.getClassPath();
        }
        catch(Exception ex)
        {
            return "4";
        }
        
        for(String item : jarFiles)
        {
            classPath += ";" +item;
        }
        
        try{
            MyJavaCompiler compiler = new MyJavaCompiler(javaFiles,classPath);
            result = compiler.compileFiles();
        }
        catch(Exception ex)
        {
            return "5";
        }
        
        if (!result.equals("0"))
            return result + " Couldn't Compile";
            
        
        
        
        
        ArrayList<JavaClassModel> classFiles = fileHandler.getClassFiles();
        ArrayList<JavaClassModel> testClassFiles = new ArrayList();
        
        for (JavaClassModel item : classFiles)
            if (item.getClassName().toLowerCase().startsWith("test"))
                testClassFiles.add(item);
        for (JavaClassModel item : testClassFiles)
            classFiles.remove(item);
        
        int numOfTestsWritten ;
        String stringToReturn = "";
        List<ResponseDataType> forms = new ArrayList();
        for (JavaClassModel item : classFiles)
        {
            MyReflectionClass myRef = new MyReflectionClass(new JavaClassModel(item.getClassName(),item.getClassPackage(),item.getRootDirectory()));
            Class clazz = myRef.getClassObject();
            
            
            
            //MyReflectionClass myRefTest = new MyReflectionClass(new JavaClassModel(item.getClassName(),item.getClassPackage(),item.getRootDirectory()));
           // Class clazz = myRef.getClassObject();
            
            
            Field[] fields = clazz.getDeclaredFields();

            for (Field field: fields)
            {
                stringToReturn += field.getName() + "--" + field.getType().getSimpleName();
            }
            Method[] methods = clazz.getDeclaredMethods();
            stringToReturn += "Üye Fonksiyonlar: " + methods.length + "\n";

            for (Method method: methods)
            {
                stringToReturn += method.getName();
                stringToReturn += method.getReturnType().getSimpleName() ;
                stringToReturn += method.getParameterCount();
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter: parameters)
                    stringToReturn += parameter.getName() +"--" +parameter.getType().getSimpleName();
                stringToReturn += "\n***************************************************\n";
            }
            JavaClassModel testClass = Helper.Contains(testClassFiles, "test"+item.getClassName());
            if (testClass != null)
            {
                MyReflectionClass myRefTest = new MyReflectionClass(testClass);
                Class testClazz = myRefTest.getClassObject();
                numOfTestsWritten = testClazz.getDeclaredMethods().length;
            }
            else
                numOfTestsWritten = 0;
            forms.add(new ResponseDataType(clazz.getSimpleName(),methods.length,numOfTestsWritten,methods.length,"Details","Not Secure"));
        }
        JSONArray jsonObject = JsonOperations.getJsonFromMyFormObject(forms);
        //fileHandler.deleteDirectory(filePath);
        return jsonObject.toString();  
        }
        catch(Exception ex)
        {
            return result;
        }
        
    }
    */
    
    
    
}
