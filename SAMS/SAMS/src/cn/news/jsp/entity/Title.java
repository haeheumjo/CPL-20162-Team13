package cn.news.jsp.entity;

public class Title {
    private String id;   
    private String name;
    private String creator;
    private String createTime;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public Title(String id, String name, String creator, String createTime) {
        super();
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.createTime = createTime;
    }
    
}