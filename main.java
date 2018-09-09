package test;

import org.json.JSONObject;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class main {

    private static String login;
    private static String password;
    private static String  url;
    private static String  projectName;
    private static String JsonStr;
    private static String object_type;
    private static String card_id;
    private static String method;
    private static String commit_id;
    private static String github_login;
    private static String github_password;
    private static String trello_api;
    private static String trello_token;
    private static String status;

	private final static String working_directory = "/home/bilmuhlab/Documents/Testing_group/";

    public static void build(String pJson) {

    	try{

            JsonStr = null;

            JsonStr = pJson;
            parse_Json();

            if (url.endsWith(".git")) {
            	url = url.substring(0, url.length() - 4);
        	}
        	url += "/archive/master.zip";
          	System.out.println("login:"+ login);
        	System.out.println("password:"+ password);
        	System.out.println("url:"+ url);
        	System.out.println("projectName:"+ projectName);
        	System.out.println("object_type:"+ object_type);
         
        	//write request.txt
        	String FILENAME = working_directory +"request.txt";
        	try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
    			//String content = projectName  + "\n" + working_directory + "\n" + object_type + "\n" + login + "\n" + password  + "\n" + trello_api + "\n" + trello_token +"\n" + card_id + "\n" + url+"\n" + projectName +"\n" + commit_id +"\n" + method +"\n" + status ;

    			String content = projectName  + "\n" + working_directory + "\n" + object_type + "\n" + login + "\n" + password  + "\n" + card_id + "\n" + url+"\n" + projectName +"\n" + commit_id +"\n" + method +"\n" + status ;
              	System.out.println(content);
    			bw.write(content);
    			bw.close();

    		} catch (IOException e) {
              	System.out.println("exception");

    			e.printStackTrace();

    		}
    		
    		String where_downloaded_github_project = working_directory +"master.zip";
    		String unZipPath = working_directory ;

    		//Download files from github
    		URL url_ = null;
    		try {
    			url_ = new URL(url);
              	System.out.println("url_:"+ url_);
              	System.out.println("where_downloaded_github_project:"+ where_downloaded_github_project);
              	System.out.println("unZipPath:"+ unZipPath);


    		} catch (MalformedURLException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
    		}
    		File file = new File(where_downloaded_github_project);
    		URLReader.copyURLToFile(url_, file);

    		//Unzip github project
    		UnZip u = new UnZip();
            u.unZipIt(where_downloaded_github_project,unZipPath);
            
            
            ProcessBuilder builder = new ProcessBuilder("firefox", "http://localhost:8085/interface/");
            builder.directory(new File(working_directory));
            try {
                Process process = builder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
           
/*

            String json_res = new String();
            json_res += "{"+"\n"+
                    "\"object_type\" : \"response\" ,"+"\n"+
                    "\"operation\" : \"" +"ad"+"\","+"\n"+
                    "\"status\" :  \"" + signal.toString().toUpperCase()+ "\","+"\n"+
                    "\"description\" : \""+report+"\","+"\n"+
                    "\"project_name\" : \""+ projectName +"\","+"\n"+
                    "} ";
            */
           
            //HttpPostRequest http_response = new HttpPostRequest();
            //http_response.post(json_res, "http://localhost:8081/integration");

            
    	}catch(Exception e){
    		
    	}

    }

    

    private static void parse_Json() throws Exception {

        JSONObject obj = new JSONObject(JsonStr);
        object_type = obj.getString("object_type");
        login = obj.getString("github_login");
        password = obj.getString("github_password");
        //trello_api = obj.getString("trello_api");
        //trello_token = obj.getString("trello_token");
        card_id = obj.getString("card_id");
        url = obj.getString("repository_url");
        projectName = obj.getString("project_name");
        commit_id = obj.getString("commit_id");
        method = obj.getString("method");
        //status = obj.getString("status");
   
        //{"github_login":"gtusoftware2017","method":"test","object_type":"general_request","github_password":"software2017","project_name":"Maven_Project_Example_2","card_id":"121","repository_url":"https://github.com/furkanyildiz/Maven_Project_Example_2.git","commit_id":"1234"}

    }


}