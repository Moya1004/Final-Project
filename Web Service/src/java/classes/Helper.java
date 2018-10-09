/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.UUID;
import model.JavaClassModel;

/**
 *
 * @author Moya
 */
public class Helper {
    
    public static JavaClassModel Contains(ArrayList<JavaClassModel> list , String className)
    {
        for (JavaClassModel item: list)
        {
            if (item.getClassName().toLowerCase().equals(className.toLowerCase()))
                return item;
        }
        
        return null;
    }
    
    public static Class contains(ArrayList<Class> list , String className)
    {
        for (Class item: list)
        {
            if (item.getSimpleName().toLowerCase().equals(className.toLowerCase()))
                return item;
        }
        
        return null;
    }
    

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
