package TestingServlet;
import com.github.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ParserClass {

    public ArrayList<String> nameOfMethods = new ArrayList<String>();
    public ArrayList<String> returnTypes = new ArrayList<String>();
    public ArrayList<ArrayList<String>> parametersType = new ArrayList<ArrayList<String>>();


    public ArrayList<String> getNameOfMethods(){
        return nameOfMethods;
    }

    public ArrayList<String> getReturnTypes(){
        return returnTypes;
    }

    public ArrayList<ArrayList<String>> getParametersType(){
        return parametersType;
    }

    public void myParser(String path) throws Exception {

        FileInputStream in = new FileInputStream(path);
        CompilationUnit cu;
        try {
            // parse the file
            cu = com.github.javaparser.JavaParser.parse(in);
        } finally {
            in.close();
        }
        // visit and print the methods names
        cu.accept(new MethodVisitor(), null);

     //   System.out.println(nameOfMethods);
     //   System.out.println(returnTypes);
     //   System.out.println(parametersType);

    }

    public class MethodVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this
            // CompilationUnit, including inner class methods

            ArrayList<String> controlModifier = new ArrayList<String>();
            ArrayList<String> subArrayList = new ArrayList<String>();

            controlModifier.add(n.getModifiers().toString());


            int controlIndex = controlModifier.get(0).indexOf(" ");
            String controlModifierString = "";
            if(controlIndex != -1)
                controlModifierString = controlModifier.get(0).substring(1,controlModifier.get(0).indexOf(" ")-1);

            if(controlModifierString.equals("PRIVATE")) {
                ;
            }
            else {

                nameOfMethods.add(n.getName().toString());
                returnTypes.add(n.getType().toString());
                for(int i=0; i<n.getParameters().size(); i++){
                    subArrayList.add(String.valueOf(n.getParameters().get(i)).substring(0, String.valueOf(n.getParameters().get(i)).indexOf(" ")));
                }
                parametersType.add(subArrayList);
            }
        }
    }
    public static void main(String args[]) throws Exception {

        ParserClass parser = new ParserClass();
        ArrayList<String> nameOfMethods = new ArrayList<String>();
        ArrayList<String> returnTypes = new ArrayList<String>();
        ArrayList<ArrayList<String>> parametersType = new ArrayList<ArrayList<String>>();


        parser.myParser("/home/bilmuhlab/Documents/Testing_group/ParserClass.java");
        nameOfMethods = parser.getNameOfMethods();
        returnTypes = parser.getReturnTypes();
        parametersType = parser.getParametersType();

        System.out.println(nameOfMethods);
        System.out.println(returnTypes);
        System.out.println(parametersType);

    }

}