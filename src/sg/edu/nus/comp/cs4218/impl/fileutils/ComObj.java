package sg.edu.nus.comp.cs4218.impl.fileutils;

public class ComObj {
	
	public ComObj(String name,String slot){
		this.name = name;
		this.slot = slot;
	}
	
	private String name;
	private String slot;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
}
	