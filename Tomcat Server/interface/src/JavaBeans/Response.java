package JavaBeans;

public class Response {
	private  int classNameIndex = 0;
	private  int funcIndex = 0 ;
	private  String parameters="";
	private String deliemeter="";
	private String expectedValue="";
	private String testResult="";
	private String selectedClassName ="";
	private String selectedFuncName = "";
	public int getClassNameIndex() {
		return classNameIndex;
	}
	public void setClassNameIndex(int classNameIndex) {
		this.classNameIndex = classNameIndex;
	}
	public int getFuncIndex() {
		return funcIndex;
	}
	public void setFuncIndex(int funcIndex) {
		this.funcIndex = funcIndex;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getDeliemeter() {
		return deliemeter;
	}
	public void setDeliemeter(String deliemeter) {
		this.deliemeter = deliemeter;
	}
	public String getExpectedValue() {
		return expectedValue;
	}
	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getSelectedClassName() {
		return selectedClassName;
	}
	public void setSelectedClassName(String selectedClassName) {
		this.selectedClassName = selectedClassName;
	}
	public String getSelectedFuncName() {
		return selectedFuncName;
	}
	public void setSelectedFuncName(String selectedFuncName) {
		this.selectedFuncName = selectedFuncName;
	}
	
	
	
	
}
