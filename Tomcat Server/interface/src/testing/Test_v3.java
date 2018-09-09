package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Test_v3 {

	public boolean flag = false;
	private String class_name;
	private String function_name;
	private String parameters;
	private String delimeter;
	private String expected_value;
	private String working_directory = "/home/bilmuhlab/Documents/Testing_group/";
	String packages_of_testing_class; //in targes/classes
	private String github_url;
	private String project_name;
	
	@SuppressWarnings("unchecked")
	public Test_v3(String class_path, String function_name, String parameters, String delimeter, String expected_value) {
		super();
		int index = class_path.indexOf("/src/main/java/");
    	int class_name_index = class_path.lastIndexOf("/");
    	int class_name_index_ = class_path.lastIndexOf(".java");
    	System.out.println("class_path name:" + class_path);

		//fill project name
		try (BufferedReader br = new BufferedReader(new FileReader(working_directory +"/request.txt"))) {

			project_name = br.readLine();


		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	String packages_of_testing_class = class_path.substring(index+15,class_name_index+1);
    	String class_name = class_path.substring(class_name_index+1,class_name_index_);
    	System.out.println("package:" + packages_of_testing_class);
    	System.out.println("class name:" + class_name);
		
		
		this.class_name = class_name;
		this.function_name = function_name;
		this.parameters = parameters;
		this.delimeter = delimeter;
		this.expected_value = expected_value;
		
	
		
		String where_downloaded_github_project = working_directory +"master.zip";
		String unZipPath = working_directory ;

        
    	String project_path_on_local = unZipPath+project_name+"-master/";
    	String class_files_path_on_local = project_path_on_local + "target/classes/";
    	String java_files_path_on_local = project_path_on_local + "src/main/java/";
        
        
    	//Parse the selected file to test
    	
    	ArrayList<String> functions = new ArrayList<String>();
    	ArrayList<String> returns = new ArrayList<String>();
    	ArrayList<ArrayList<String>> param_types_of_functions = new ArrayList<ArrayList<String>>();
    	ArrayList<ArrayList> all_datas = null;

    	
    	ParserClass parser = new ParserClass();
		try {
			all_datas = parser.parse(java_files_path_on_local+packages_of_testing_class+class_name+".java");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		returns = all_datas.get(0);
		functions = all_datas.get(1);
		param_types_of_functions = all_datas.get(2);
    	
		
		//find infos of target functions
		
    	int i=0;

    	for(; i<functions.size(); ++i) {
    		if (functions.get(i).equals(function_name))
    			break;
    	}
    	
    	//Split parameters
    	String [] splited_parameters = parameters.split(delimeter);
    	
    	
    	ArrayList<String> testing_parameters = new ArrayList<String>();
    	
    	for(int j=0; j< splited_parameters.length; ++j)
    		testing_parameters.add(splited_parameters[j]);
    	
    	System.out.println("testing params:"+testing_parameters);
    	
    	create_test_code_v2 test = new create_test_code_v2(class_name,function_name,returns.get(i),testing_parameters,
    			param_types_of_functions.get(i),expected_value,class_files_path_on_local,packages_of_testing_class);
    	
    	try {
			autorun runner = new autorun("Junit_test",class_files_path_on_local);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	flag = true;
		
	}
	
	
	
	
}