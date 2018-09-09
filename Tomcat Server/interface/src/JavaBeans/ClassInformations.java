package JavaBeans;

import java.util.ArrayList;

public class ClassInformations {
	private ArrayList<Class> allClassInf = new ArrayList<Class>();

	public void addNewClassInf(Class _class)
	{
		allClassInf.add(_class);
	}

	public ArrayList<Class> getAllClassInf() {
		return allClassInf;
	}

	public void setAllClassInf(ArrayList<Class> allClassInf) {
		this.allClassInf = allClassInf;
	}

	
	
	
}
