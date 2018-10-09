/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.List;
import model.MethodModel;
import model.TestResultModel;
import model.VariableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Moya
 */
public class JsonOperations {
    
    /**
     *
     * @param form
     * @return
     * @throws JSONException
     */
  public static JSONArray getJsonFromMyFormObject(List<ResponseDataType> form) throws JSONException
  {
    JSONArray jsonArray = new JSONArray();

    for (int i = 0; i < form.size(); i++)
    {
      JSONObject formDetailsJson = new JSONObject();
      formDetailsJson.put("fileName", form.get(i).getFileName());
      formDetailsJson.put("methods", form.get(i).getMethods());      
      formDetailsJson.put("variables", form.get(i).getVariables());
      formDetailsJson.put("NumberOfTestsWritten", form.get(i).getNumberOfTestsWritten());
      formDetailsJson.put("successRate", form.get(i).getSuccessRate());      
      formDetailsJson.put("failureMessages", form.get(i).getFailureMessages());
      formDetailsJson.put("Other_details", form.get(i).getOther_details());
      formDetailsJson.put("testClassName", form.get(i).getTestClassName());
      jsonArray.put(formDetailsJson);
    }
    return jsonArray;
  }
  
  public static void main(String[] args) throws JSONException
  {
    List<ResponseDataType> forms = new ArrayList();
    String[] parameters = {"int","String"};
    MethodModel[] methods = {new MethodModel("methodName","returnType",parameters,parameters)};
    VariableModel[] variables = {new VariableModel("public","int","age")};
    
    String[] failureMessages = {"Failure Message"};
    
    forms.add(new ResponseDataType("fileName1",methods,variables,20,"Test1",failureMessages,20,"Other1"));      
    forms.add(new ResponseDataType("fileName2",methods,variables,21,"Test2",failureMessages,21,"Other2"));    
    forms.add(new ResponseDataType("fileName3",methods,variables,22,"Test3",failureMessages,22,"Other3"));    

    JSONArray jsonObject = getJsonFromMyFormObject(forms);
    System.out.println(jsonObject);
  }
    
    
}
