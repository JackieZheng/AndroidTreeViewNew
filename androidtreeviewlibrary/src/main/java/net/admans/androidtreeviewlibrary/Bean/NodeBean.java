package net.admans.androidtreeviewlibrary.Bean;

public class NodeBean
{
    private String id;
    private String pId;
    private String name;
    private String desc;
    private long length;
    
    public NodeBean(String id, String pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPid() {
        return this.pId;
    }
    
    public void setPid(String pId) {
        this.pId = pId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public long getLength() {
        return this.length;
    }
    
    public void setLength(long length) {
        this.length = length;
    }
}
