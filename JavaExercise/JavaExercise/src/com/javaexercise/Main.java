package com.javaexercise;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("SVC.json"));

            JSONObject jsonObj = (JSONObject)obj;
            System.out.print("Please Enter the selector: ");
            Scanner userIn = new Scanner(System.in);

            String selector = userIn.nextLine();
            JSONObject rootJSON = (JSONObject) new JSONParser().parse(new FileReader("SVC.json"));
            JSONArray dataList = (JSONArray) rootJSON.get("subviews");
            JSONObject resultClass= Main.getJson(dataList, selector);
            if (resultClass != null){
                System.out.println("Selector class is: ");
                System.out.println(resultClass.toJSONString());
            }else {
                System.out.println("there is No view for that input");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static JSONObject getJson(JSONArray jsonArr, String selector){
            JSONObject result = null;
            if (jsonArr != null){
                for(Object projectObj: jsonArr.toArray()){
                    JSONObject project = (JSONObject)projectObj;
                    String className = (String) project.get("class");
                    if (className != null){

                        if (className.equals(selector)){
                            result = project;
                            break;
                        }else {
                            JSONArray child = (JSONArray) project.get("subviews");
                            if (child != null){
                                return getJson((JSONArray) project.get("subviews"),selector);
                            }else {
                                JSONObject obj = (JSONObject) project.get("contentView");
                                if(obj != null){
                                    return getJson((JSONArray) obj.get("subviews"),selector);
                                }else {
                                    break;
                                }

                            }

                        }
                    }else {
                        break;
                    }

                }
            }

        return result;
    }
}
