package database;

public class Record {
	private String s_id;
	private String s_name;
	private String type;
	public Record(String s_id, String s_name, String type){
		this.s_id = s_id;
		this.s_name = s_name;
		this.type = type;
	}
	public String getS_id() {
		return s_id;
	}
	public String getS_name() {
		return s_name;
	}
	public String getType() {
		return type;
	}
	
}
