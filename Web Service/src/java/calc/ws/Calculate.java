/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calc.ws;

import FileHandlers.JavaFileHandler;
import classes.DB;
import classes.Helper;
import classes.JsonOperations;
import classes.MyReflectionClass;
import classes.ResponseDataType;
import classes.SocketServer;
import classes.UnzipFile;
import compilers.MyJavaCompiler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import model.JavaClassModel;
import model.MethodModel;
import model.TestResultModel;
import model.VariableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author Moya
 */
@WebService(serviceName = "Calculate")
public class Calculate {

    String result = "";
    JUnitCore junit = new JUnitCore();
    Result testResults ;
    List<ResponseDataType> forms = new ArrayList();
    JSONArray jsonObject = null;
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addition")
    public Double addition(@WebParam(name = "x") Double x, @WebParam(name = "y") Double y) {
        //TODO write your implementation code here:
        return x + y;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ReadFile")
    public String ReadFile(@WebParam(name = "fileName") File fileName) {
        //TODO write your implementation code here:
        return fileName.getName();
    }

    /**
     * Web service operation
     * @param fileName
     * @param fileArray
     * @return 
     */
    
    public String CalculateClass(@WebParam(name = "fileName") String fileName, @WebParam(name = "fileArray") byte[] fileArray) throws ClassNotFoundException, IOException, JSONException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date2 = new Date();
	String currentDate = dateFormat.format(date2);
        long start_time = System.currentTimeMillis();
        long end_time ;
        int isSuccesfull = 0;
        String response = "Error";
        DB db = new DB();
        SocketServer ss = new SocketServer("localhost",9090);
        db.insertLog();
        String justfileName = fileName.substring(0,fileName.indexOf('.')); 
        String justExtension = fileName.substring(fileName.indexOf('.'),fileName.length());
        db.insertFile(justfileName,justExtension , fileArray.length);
        forms = new ArrayList();
        try{
          try{
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\"+ fileName);
            fos.write(fileArray);
            fos.close();
        }
        catch(Exception ex)
        {
            end_time = System.currentTimeMillis();
            isSuccesfull = 0;
            response = "Error Occured While Uploading file to the system";
            db.insertResponse(currentDate, response, isSuccesfull, (end_time - start_time)/1000);
            return response;
        }
        File file = new File("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\"+fileName);
        
        
        if(fileName.endsWith(".jar"))
        {
            String pathToJar  = "C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\"+ fileName;
            if(workWithJar(pathToJar) != 0)
            {
                end_time = System.currentTimeMillis();
                isSuccesfull = 0;
                response = "Jar Is Corrupted";
                db.insertResponse(currentDate, response, isSuccesfull, (end_time - start_time)/1000);
                return response;
            }
        }
        
        else if (fileName.endsWith(".zip"))
        {
            if(file.exists())
        {
            int resultOfUnzip = UnzipFile.unzip("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\"+fileName,
                            "C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\Uncompressed");
            
            if (resultOfUnzip != 0)
            {
                end_time = System.currentTimeMillis();
                isSuccesfull = 0;
                response = "The File Could not be Unzipped";
                db.insertResponse(currentDate, response, isSuccesfull, (end_time - start_time)/1000);
                return response;
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
            fileHandler.deleteClassFiles();
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
        System.out.println("Result" + result);

        
        if (!result.equals("0"))
        {
            end_time = System.currentTimeMillis();
            isSuccesfull = 0;
            response = result + " Could not Compile";
            db.insertResponse(currentDate, response, isSuccesfull, (end_time - start_time)/1000);
            return response;
        }
            
        Date date = new Date();
        String randomFileName = Helper.generateString() + date.getTime();
        String[] command = {"jar","cvf", randomFileName+".jar","*"};
        ProcessBuilder builder = new ProcessBuilder(command);
        builder = builder.directory(new File("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\build\\"));
        Process runtime = builder.start();
        runtime.waitFor();
        String pathToJar = "C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\build\\" + randomFileName +".jar";
        workWithJar(pathToJar);
        
        /*
        ArrayList<JavaClassModel> classFiles = fileHandler.getClassFiles();
        ArrayList<JavaClassModel> testClassFiles = new ArrayList();
        
        for (JavaClassModel item : classFiles)
            if (item.getClassName().toLowerCase().startsWith("test"))
                testClassFiles.add(item);
        for (JavaClassModel item : testClassFiles)
            classFiles.remove(item);
        
        int numOfTestsWritten ;
        String stringToReturn = "";
        for (JavaClassModel item : classFiles)
        {
            String successRate = "";
            String details = "";
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
                try{
                    testResults = junit.run(testClazz);
                    List<Failure> failures = testResults.getFailures();
                    for (Failure failure : failures)
                    {
                        details += failure.getTestHeader() + " " + failure.getMessage();
                        successRate = "" + ((testResults.getRunCount() - (double)testResults.getFailureCount())/testResults.getRunCount()) * 100;
                    }
                }
                catch (Exception ex)
                {
                    details = ex.getMessage() + " Fails";
                    successRate = ex.getMessage() + " Fails";
                }
                
            }
            else
                numOfTestsWritten = 0;
            forms.add(new ResponseDataType(clazz.getSimpleName(),methods.length,numOfTestsWritten,methods.length - numOfTestsWritten,details,successRate));
        }*/
        jsonObject = JsonOperations.getJsonFromMyFormObject(forms);
        fileHandler.deleteDirectory(filePath);
        }
        end_time = System.currentTimeMillis();
        isSuccesfull = 1;
        response = jsonObject.toString();
        db.insertResponse(currentDate, response, isSuccesfull, (end_time - start_time)/1000);
        return response;  
        }
        catch(Exception ex)
        {
            return ex.getMessage();
        }
    }
    
    
    
    public int workWithJar(String path)
    {
        try{  
            ArrayList<Class> normalClassFiles = new ArrayList();
            ArrayList<Class> testClassFiles = new ArrayList();
            JarFile jarFile = new JarFile(path);
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls = { new URL("jar:file:" + path+"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls,getClass().getClassLoader());
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
        // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            System.out.println(c.getSimpleName());
            if (c.getSimpleName().toLowerCase().startsWith("test"))
                testClassFiles.add(c);
            else 
                normalClassFiles.add(c);
            }
            
            System.out.println(testClassFiles.size());
            
            String[] details = null;
            double successRate;
            for (Class c: normalClassFiles)
            {
                details = null;
                successRate = -1;
                int numOfTestsWritten = 0;
                Class testClass = Helper.contains(testClassFiles, "test"+c.getSimpleName());
                if (testClass != null)
                {
                    System.out.println(testClass.getSimpleName());
                    numOfTestsWritten = testClass.getDeclaredMethods().length;
                    try{
                         testResults = junit.run(testClass);
                        List<Failure> failures = testResults.getFailures();
                        details = new String[failures.size()];
                        int counter = 0;
                        for (Failure failure : failures)
                        {
                            details[counter] = failure.getTestHeader() + " " + failure.getMessage();
                            counter++;
                        }
                        successRate = (testResults.getRunCount() - (double)testResults.getFailureCount())/testResults.getRunCount() * 100;

                    }
                    catch (Exception ex)
                    {
                        successRate = -1;
                    }
                   
                }
                
                MethodModel[] methods = new MethodModel[c.getDeclaredMethods().length];
                int i = 0;
                for (Method method : c.getDeclaredMethods())
                {
                    String[] parameters = new String[method.getParameters().length];
                    int s = 0;
                    for (Parameter parameter : method.getParameters())
                    {
                        parameters[s] = parameter.getType().toString();
                        s++;
                    }
                    Annotation[] annotations = method.getDeclaredAnnotations();
                    int sizeOfAnnotations = annotations.length;
                    String[] annotationsString = new String[method.getDeclaredAnnotations().length];
                    int j = 0;
                    for (Annotation annotation : annotations)
                    {
                        annotationsString[j] = annotation.annotationType().toString();
                        j++;
                    }
                    methods[i] = new MethodModel(method.getName(),method.getReturnType().toString(),parameters,annotationsString);
                }
                Field[] fields = c.getDeclaredFields();
                VariableModel[] variables = new VariableModel[fields.length];
                i = 0;
                for (Field field : fields)
                {
                    variables[i] = new VariableModel(field.getName(),field.getType().toString(),Modifier.toString(field.getModifiers()));
                }
                forms.add(new ResponseDataType(c.getSimpleName(),methods,variables,numOfTestsWritten,testClass != null ? testClass.getSimpleName(): "",details,successRate,"Other"));
                result += c.getDeclaredMethods().length + " " + numOfTestsWritten ;
            }
            jsonObject = JsonOperations.getJsonFromMyFormObject(forms);
            File jarFileObj = new File(path);
            if (jarFileObj.exists())
                if(jarFileObj.delete())
                    System.out.println("deleted");
                else
                    System.out.println("not deleted");
            jarFile.close();
            File ffff = new File("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\build\\zzzzx.jar");
            
            ffff.delete();
            (new JavaFileHandler("")).deleteDirectory("C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\build");
            return 0;
        }
        catch(Exception ex)
        {
            System.out.println("Error:" +ex.getMessage());
            return -1;
        }
    }
    
}
