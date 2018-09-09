package testing;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ParserClass {

    public ArrayList<String> nameOfMethods = new ArrayList<String>();
    public ArrayList<String> returnTypes = new ArrayList<String>();
    public ArrayList<ArrayList<String>> parametersType = new ArrayList<ArrayList<String>>();
    public ArrayList<ArrayList> returns = new ArrayList<ArrayList>();
    
    public ArrayList<ArrayList> parse(String path) throws Exception {

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

        System.out.println(nameOfMethods);
        System.out.println(returnTypes);
        System.out.println(parametersType);
        returns.add(returnTypes);
        returns.add(nameOfMethods);
        returns.add(parametersType);
        return returns;

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

}
