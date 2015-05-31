package magna.vlt.ui.common;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Node {

	protected static Hashtable<String, ArrayList<Object>> roots = new Hashtable<String, ArrayList<Object>>();
	protected ArrayList<String> features = new ArrayList<String>();
	protected ArrayList<String> projects = new ArrayList<String>();
	protected ArrayList<Object> childs = new ArrayList<Object>();
	protected Object parent;
	
	public Node(String id){
		if(!roots.keySet().contains(id))
			roots.put(id, new ArrayList<Object>());
	}
	
	public Node(){
		
	}
	
	public Object[] listChildren() {
		Object[] children = new Object[this.childs.size()];
		for(int i = 0 ; i < childs.size(); i++)
			children[i] = childs.get(i);
		
		return children;
	}
	
	public Object getParent() {
		return this.parent;
	}
	
	public static void clearRoots(String id) {
		roots.get(id).clear();
	}
	
	public static Object[] listRoots(String id) {
		Object[] elements = new Object[roots.get(id).size()];
		for(int i = 0 ; i < roots.get(id).size(); i++)
			elements[i] = roots.get(id).get(i);
		
		return elements;
	}
	
	public static void addRoots(String id, Object obj) {
		roots.get(id).add(obj); 
	}
	

	public void setParent(Object parent) {
		this.parent = parent;
	}

	public void addChild(Object child){
		this.childs.add(child);
	}
	
}
