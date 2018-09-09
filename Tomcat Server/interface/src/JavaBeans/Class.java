package JavaBeans;

import java.util.ArrayList;

import TestingServlet.ParserClass;


public class Class {
	private String classPath = "";
	private ArrayList<ArrayList<String>> parameters;
	private ArrayList<String> nameOfMethods;
	private ArrayList<String> returnTypes;
/*
	public Class(ArrayList<String> nameOfMethods, ArrayList<String> returnTypes,
			ArrayList<ArrayList<String>> parameters) {
		this.nameOfMethods = nameOfMethods;
		this.returnTypes = returnTypes;
		this.parameters = parameters;
	}*/
	public Class(String path)
	{
		this.classPath = path;
		parseIt();
	}
	public ArrayList<String> getNameOfMethods()
	{
		return nameOfMethods;		
	}
	public ArrayList<String> getReturnTypes()
	{
		return returnTypes;
	}
	public ArrayList<ArrayList<String>> getParameters()
	{
		return parameters;
	}
	
	public void setClassPath(String path)
	{
		this.classPath = path;
	}
	public String getClassPath()
	{
		return classPath;
	}
	
	private void parseIt()
	{
        ParserClass parser = new ParserClass();
        nameOfMethods = new ArrayList<String>();
        returnTypes = new ArrayList<String>();
        parameters = new ArrayList<ArrayList<String>>();


        try {
			parser.myParser(classPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        nameOfMethods = parser.getNameOfMethods();
        returnTypes = parser.getReturnTypes();
        parameters = parser.getParametersType();

        /*System.out.println(nameOfMethods);
        System.out.println(returnTypes);
        System.out.println(parameters);*/
	}
	
	
}
