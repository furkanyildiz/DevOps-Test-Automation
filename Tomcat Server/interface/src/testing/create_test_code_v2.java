package testing;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class create_test_code_v2 {
	private String expected;
	private String class_name;
	private String test_fuction_name;
	private ArrayList<String> paramaters;
	private String params_return_types;
	private ArrayList<String> params_types;
	private String path_of_test_file;  //maven projesinde target/classes dosyasının pathı olacak.
	private final String CLASSNAME = "Junit_test";
	private String for_package;
	
	
	//private static final String FILENAME = "/home/osboxes/eclipse-workspace/testing/src/testing/test_den.java";
	
	public create_test_code_v2(String class_name, String test_fuction_name, String params_return_type, ArrayList<String> params,
		 ArrayList<String> params_type,String ex,String path, String for_package_) {
		this.expected = ex;
		this.class_name = class_name;
		this.test_fuction_name = test_fuction_name;
		this.paramaters = params;
		this.params_return_types = params_return_type;
		this.params_types = params_type;
		this.for_package = for_package_;
		
		System.out.println("ret typess:: "+ params_types);
		
		path_of_test_file = path;
		
		if(path_of_test_file.endsWith("/") || path_of_test_file.endsWith("\\"))
			path_of_test_file +=CLASSNAME+".java";
		else
			path_of_test_file += "/" + CLASSNAME+".java";
		//yanlıs baks
		for_package = for_package.replace("/", ".");

		if(for_package.endsWith(".."))
			for_package = for_package.substring(0, for_package.length() - 2);
		else if(for_package.endsWith("."))
			for_package = for_package.substring(0, for_package.length() - 1);
		
		System.out.println(for_package);
		System.out.println(class_name);
		for_package = for_package+"."+class_name; //import edilecek package.testingClassName

		write_test_file();
	}
	
	
	private void write_test_file() {
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String content = "import " + for_package + ";\n" 
					+"import java.io.BufferedWriter;\r\n" + 
					"import java.io.FileWriter;\r\n" + 
					"import java.io.IOException;\n" + 
					"import org.junit.*;\n" + 
					"import static org.junit.Assert.assertEquals;"+
					"public class "+CLASSNAME+"{ \n" +
					"	private "+ class_name +" obj;\n" + 
					"	@Before\n" + 
					"    public void setUp() {\n" + 
					"		this.obj = new " + class_name +"();\n" + 
					"    }"+
					
					"@Test\n" + 
			"public void JUnitTest(){\n"
			+params_return_types+" result = obj."+test_fuction_name+"(";
			
			String content2 = "";
			System.out.println(paramaters.size());
			for (int i=0; i<paramaters.size(); ++i) {
				//params_types.get(i)
				content2 += "("+params_types.get(i)+")";
				
				if (params_types.get(i).equals("int") || params_types.get(i).equals("double")||
						params_types.get(i).equals("Integer") || params_types.get(i).equals("Double")) 
					content2 +=paramaters.get(i) ;
				
				else if (params_types.get(i).equals("char") || params_types.get(i).equals("Character")) {
					content2 += "'";
					content2 +=  paramaters.get(i).charAt(0) ;
					content2 += "'";
				}
				else  //string falan
					content2 += "\"" +paramaters.get(i).toString() +"\"";

			
					
				
				if(i != paramaters.size()-1)
					content2 +=",";
				
			}
			content2 +=");\n"+
			"		assertEquals(result,";
			if( params_return_types.equals("int") ||
					params_return_types.equals("Integer") ||  
					params_return_types.equals("boolean") || params_return_types.equals("Boolean"))  //equalstan sonra string yada primitive yazabilmek icin
				content2 +=expected ;
			
			else if(params_return_types.equals("Double") || params_return_types.equals("double") ) {
				System.out.println("loookkkkkkkk11");

				System.out.println(expected);

				String aa = expected.replace(".",",") ;
				content2 +=aa ;
				System.out.println("loookkkkkkkk");
				System.out.println(aa);

			}
			
			else if (params_return_types.equals("char") ||params_return_types.equals("Character")) {
				content2 += "'";
				content2 +=  expected.charAt(0) ;
				content2 += "'";
			}
			
			else  //string falan
				content2 += "\"" +expected.toString() +"\"";
			
			content2 += ");\n}" + 
			
			"}";
			fw = new FileWriter(path_of_test_file);
			System.out.println("dosya suraya yazıldı: "+ path_of_test_file);
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.write(content2);
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		

	}

}