package FileHandlers;


import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.JavaClassModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Moya
 */
public class JavaFileHandler {
    
    private String path;
    private ArrayList<String> javaFiles;
    private String classPath;
    private ArrayList<JavaClassModel> classList;
    private ArrayList<String> jarFiles; 
    
    
    
    public JavaFileHandler(String path)
    {
        this.path = path ;
        setClassPath(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
    public String rootPath(String path)
    {
        Path dir = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory())
            {
                if (f.getName().equalsIgnoreCase("src"))
                {
                    return f.getParent();
                }    
                
                getJavaFiles(f.getAbsolutePath());
            }
            else if (f.isFile() && f.getPath().endsWith(".java"))
            {
                javaFiles.add(f.getAbsolutePath());
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
        return null;
    }
    
    public ArrayList<JavaClassModel> getClassFiles()
    {
        classList = new ArrayList();
        getClassFiles(this.rootPath(this.path));
        return classList;
    }
    
    public void deleteClassFiles()
    {
        deleteClassFiles(this.rootPath(this.path));
    }
    
    
    private void deleteClassFiles(String path)
    {
        Path dir = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory())
            {
                
                deleteClassFiles(f.getAbsolutePath());
            }
            else if (f.isFile() && f.getPath().endsWith(".class"))
            {
                f.delete();
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
   }
    
    private ArrayList<JavaClassModel> getClassFiles(String path)
    {
        Path dir = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory())
            {
                
                getClassFiles(f.getAbsolutePath());
            }
            else if (f.isFile() && f.getPath().endsWith(".class"))
            {
                String[] results = findPackageName(f.getAbsolutePath());
                classList.add(new JavaClassModel(f.getName(),results[1],results[0]));
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
        return classList;
   }
    
    
    
    public ArrayList<String> getJavaFiles()
    {
        javaFiles = new ArrayList<>();
        return getJavaFiles(this.path);
    }
       
    
    
    private ArrayList<String> getJavaFiles(String path)
    {
        Path dir = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory())
            {
                getJavaFiles(f.getAbsolutePath());
            }
            else if (f.isFile() && f.getPath().endsWith(".java"))
            {
                javaFiles.add(f.getAbsolutePath());
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
        return javaFiles;
   }
   
    public ArrayList<String> getJarFiles()
    {
        jarFiles = new ArrayList();
        return getJarFiles(this.path);
    }
    
    
     private ArrayList<String> getJarFiles(String path)
    {
        Path dir = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory())
            {
                getJarFiles(f.getAbsolutePath());
            }
            else if (f.isFile() && f.getPath().endsWith(".jar"))
            {
                jarFiles.add(f.getAbsolutePath());
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
        return jarFiles;
   }
    
   public boolean deleteFile(String filePath)
   {
       File file = new File(filePath);
       if (file.isFile() & file.exists())
       {
           file.delete();
           return true;
       }
       return false;
   }
   
   public boolean deleteDirectory(String filePath)
   {
        try {
            Path path = new File(filePath).toPath();
            
            Files.walkFileTree(path, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException
                {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
                {
                    // try to delete the file anyway, even if its attributes
                    // could not be read, since delete-only access is
                    // theoretically possible
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
                {
                    if (exc == null)
                    {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                    else
                    {
                        // directory iteration failed; propagate exception
                        throw exc;
                    }
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(JavaFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
       return true;
    }
   
    public String getClassPath()
    {
        return classPath;
    }
   
    
    
    private void setClassPath(String path)
    {
        Path dir = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory() && f.getName().equalsIgnoreCase("src"))
            {
                classPath = f.getAbsolutePath();
                return;
            }
            else if (f.isDirectory())
            {
                setClassPath(f.getAbsolutePath());
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
    }
    
    
    
    public String getSRCDirectory()
    {
        return getSRCDirectory(this.path);
    }
            
    private String getSRCDirectory(String directory)
    {
        Path dir = Paths.get(directory);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory() && f.getName().equalsIgnoreCase("src"))
                return f.getAbsolutePath();
            else if (f.isDirectory())
            {
                getSRCDirectory(f.getAbsolutePath());
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
        return null;
    }
    
    
    public String getTestDirectory()
    {
        return getTestDirectory(this.path);
    }
            
    private String getTestDirectory(String directory)
    {
        Path dir = Paths.get(directory);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            File f = file.toFile();
            if (f.isDirectory() && f.getName().equalsIgnoreCase("test"))
                return f.getAbsolutePath();
            else if (f.isDirectory())
            {
                getSRCDirectory(f.getAbsolutePath());
            }
         }
         } catch (IOException | DirectoryIteratorException x) {
             // IOException can never be thrown by the iteration.
             // In this snippet, it can only be thrown by newDirectoryStream.
                 System.err.println(x + "Path: " + path);
         }
        return null;
    }
    
    public String[] findPackageName(String classPath)
    {
        String rootpath = this.rootPath(this.path);
        String result = classPath.replace(rootpath, "").substring(1);
        int num = result.indexOf("\\");
        rootpath += "\\"+result.substring(0,num);
        result = result.substring(num+1);
        result = result.replace("\\", ".");
        String[] results = {rootpath , result};
        return results;
    }
    
    
}
