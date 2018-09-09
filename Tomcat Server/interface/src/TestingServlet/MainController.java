package TestingServlet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import JavaBeans.Class;
import JavaBeans.ClassInformations;
import JavaBeans.Response;
import testing.Test_v3;

import java.util.ArrayList;
import java.util.List;
@WebServlet("/")
public class MainController extends HttpServlet {
	
	
	String commit_id;
	String github_login;
	String github_password;
	String repository_url;
	String project_name;
	String trello_api;
	String trello_token;
	String card_id;
	String object_type;
	String method;
	String status;

	ClassInformations ci;
	String JSONTOSEND = null;
	String projectNameWithAddedMaster;
	String projectName;
	ArrayList<String> results = new ArrayList<>();
	
	String WorkingDirectory = "/home/bilmuhlab/Documents/Testing_group/";
	String readingResultFrom = "";
	
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		//fill project name
		try (BufferedReader br = new BufferedReader(new FileReader(WorkingDirectory +"/request.txt"))) {

			projectName = br.readLine();
			br.readLine(); //read working directory
			
			object_type = br.readLine();
			github_login = br.readLine();
			github_password = br.readLine();
			trello_api = br.readLine();
			trello_token = br.readLine();
			card_id = br.readLine();
			repository_url = br.readLine();
			project_name = br.readLine();
			commit_id = br.readLine();
			method = br.readLine();
			status = br.readLine();


		} catch (IOException e) {
			e.printStackTrace();
		}
		projectNameWithAddedMaster = projectName+"-master";
			
		readingResultFrom = WorkingDirectory+projectNameWithAddedMaster+"/target/classes/test_result.txt";
				
		req.setCharacterEncoding("UTF-8");
	     HttpSession session=req.getSession();  
	     System.out.println("ci path:" + WorkingDirectory+ projectNameWithAddedMaster);

	     ci = giveAllClassInf(WorkingDirectory+ projectNameWithAddedMaster);
	      session.setAttribute("allClassInf", ci); 
		//req.setAttribute("allClassInf", giveAllClassInf("/home/msd/Desktop/qq/"));
		req.getRequestDispatcher("index.jsp").forward(req, resp);

	}
	
	public ClassInformations giveAllClassInf(String path)
	{
		List<String> list = new ArrayList<String>();
		
		ClassInformations ci = new ClassInformations();
		
		listFilesAndFilesSubDirectories(path, list);
		for(String pth: list)
		{
			//System.out.println(pth);
			ci.addNewClassInf(new Class(pth));
		}
		return ci;
	}
    public static void listFilesAndFilesSubDirectories(String directoryName,List<String> list){

        File directory = new File(directoryName);

        //get all the files from a directory

        File[] fList = directory.listFiles();

        for (File file : fList){

            if (file.isFile()){
            	if(file.getAbsolutePath().contains(".java"))
            		list.add(file.getAbsolutePath());

            } else if (file.isDirectory()){

                listFilesAndFilesSubDirectories(file.getAbsolutePath(),list);

            }

        }

    }
    
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Response rsp = new Response();
		String action = request.getParameter("action");
		if("Test!".equals(action)) {
			rsp.setExpectedValue(request.getParameter("expectedValue"));
			rsp.setDeliemeter(request.getParameter("separator"));
			rsp.setParameters(request.getParameter("parameters"));
			rsp.setClassNameIndex(Integer.parseInt(request.getParameter("className")));
			rsp.setFuncIndex(Integer.parseInt(request.getParameter("funcName")));
			rsp.setSelectedClassName(ci.getAllClassInf().get(rsp.getClassNameIndex()).getClassPath());
			rsp.setSelectedFuncName(ci.getAllClassInf().get(rsp.getClassNameIndex()).getNameOfMethods().get((rsp.getFuncIndex())));
			
			//System.out.println(parameters + " " + separator + " " + expectedValue + " "  + " " + className + " " + funcName);		//resp.sendRedirect("/Testing/index.jsp");
			System.out.println(rsp.getFuncIndex() + " " + rsp.getClassNameIndex());
	
			Test_v3 test = new Test_v3(rsp.getSelectedClassName(),rsp.getSelectedFuncName(),rsp.getParameters(),rsp.getDeliemeter(),rsp.getExpectedValue());
			
			///home/osboxes/Documents/Testing_group3/Maven-Project-Example-master/target/classes
			
			String sCurrentLine = "test";
			String sCurrentLine2 = "test";
			String sCurrentLine3 = "test";

			while(!test.flag);
			
			try (BufferedReader br = new BufferedReader(new FileReader(readingResultFrom))) {

				sCurrentLine = br.readLine();
				sCurrentLine = br.readLine();
				sCurrentLine = br.readLine();
				sCurrentLine = br.readLine();
				sCurrentLine2 = br.readLine();
				sCurrentLine3 = br.readLine();
			
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			String asd = sCurrentLine + "\n" + sCurrentLine2 + "\n" + sCurrentLine3;
			asd = asd.replaceAll("<", "< ");
			
			System.out.println("a:"+asd);
			if(asd.contains("OK (")) 
				request.setAttribute("status",asd);
			
			else 
				results.add(ci.getAllClassInf().get(rsp.getClassNameIndex()).getClassPath() + "/"+ ci.getAllClassInf().get(rsp.getClassNameIndex()).getNameOfMethods().get((rsp.getFuncIndex())) );
			
			request.setAttribute("message",asd);

	
	        request.setAttribute("rsp", rsp);
	
	        System.out.println(rsp.getSelectedFuncName());
	        System.out.println(ci.getAllClassInf().get(rsp.getClassNameIndex()).getNameOfMethods().get((rsp.getFuncIndex())));
	
	        request.setAttribute("selectedFuncNames", ci.getAllClassInf().get(rsp.getClassNameIndex()).getNameOfMethods());
	        
			request.getRequestDispatcher("index.jsp").forward(request, resp);
	
		}
	
		
		else if("Send Results".equals(action)) {
			if(results.size() != 0) 	
				System.out.print("geldi");
			
			
		
			
		System.out.println(results.toString());
			
			String result;
			if(results.size() == 0) result = "TRUE"; else result = "FALSE";
			
			String payload = "{"+
					"\"object_type\" : \"response\" ,"+
					"\"method\" : \"check-test-status\" ,"+
					"\"status\" :  \"" + result+ "\","+
					"\"description\" :  \"" + result+ "\","+
					"\"commit_id\"   : \"" + commit_id + "\","+
					"\"card_id\"   : \"" + card_id + "\","+
					"\"github_login\"   : \"" + github_login+  "\","+
					"\"github_password\"   : \"" + github_password+ "\","+
					"\"repository_url\"   : \"" + repository_url +"\","+
					"\"project_name\"   : \"" + project_name+ "\","+
					"\"trello_api\"   : \"" + trello_api+ "\","+
					"\"trello_token\"   : \"" +trello_token +  "\"" +
					"} ";
			System.out.println(payload);
			System.out.printf("dedede");

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(WorkingDirectory +"response.txt"))) {


				bw.write(payload);

			} catch (IOException e) {

				e.printStackTrace();
			}
			
			Runtime.getRuntime().exec("python3 "+ WorkingDirectory + "Connector.py " +WorkingDirectory+"response.txt");
			results.clear();
			request.getRequestDispatcher("index.jsp").forward(request, resp);

			/*
	        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_FORM_URLENCODED);

	        HttpClient httpClient = HttpClientBuilder.create().build();
	        //https://putsreq.com/190E1Djbvbs5aipShEin
	        HttpPost requestPost = new HttpPost("http://localhost:8081/integration");
	        requestPost.setEntity(entity);

	        HttpResponse responsePost = httpClient.execute(requestPost);
	        //System.out.println(responsePost.getStatusLine().getStatusCode());
			System.out.println(payload);
			request.getRequestDispatcher("index.jsp").forward(request, resp);
*/
		}
		
		
			
	}
	
 
 

}