package compilers;

import java.util.ArrayList;
import javax.tools.ToolProvider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Moya
 */
public class MyJavaCompiler {
    
        private ArrayList<String> javaFilesTobeCompiled;
        private String classPath;
        
        public MyJavaCompiler(ArrayList<String> files, String classPath)
        {
            this.javaFilesTobeCompiled = files;
            this.classPath = classPath;
        }
        
        public String compileFiles()
        {
            ArrayList<String> listOfArguments = new ArrayList<String>();
            listOfArguments.add("-cp");
            listOfArguments.add(this.classPath);
            for(String file:this.javaFilesTobeCompiled)
            {
                listOfArguments.add(file);
            }
            
            String[] arguments = new String[listOfArguments.size() + 3];
            
            int i = 0;
            for (String argument:listOfArguments)
            {
                arguments[i] = argument;
                i++;
            }
            arguments[i] = "-d";            
            arguments[i+1] = "C:\\Users\\Moya\\Documents\\NetBeansProjects\\FileRepository\\build";
            arguments[i+2] = "-Xlint";

            
              
            int result = -1;
            try
            {
                javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                result = compiler.run(null, null, null,arguments);
            }
            catch(Exception ex)
            {
                String ar = String.join("  ", arguments);
                return "Cause: " + ex.getMessage() +"\n Arguments: " + ar;
            }
            
            return ""+result ;
        }
        
        public String getFilesTobeCompiled()
        {
            String x = "";
            
            for (String file: this.javaFilesTobeCompiled)
            {
                x += file + "\n";
            }
            return x + "\n" + "ClassPath: " + this.classPath;
        }
}
