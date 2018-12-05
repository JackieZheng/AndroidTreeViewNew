package net.admans.androidtreeviewlibrary.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNode
{
    
    private String id;
    private String pId;
    private boolean isExpand = false;
    private boolean isChecked = false;
    /*android checkBox 没有indeterminate（不确定）状态 自定义一个状态码
    * 0 未选中 对应Checked false
    * 1 选中 对应Checked true
    * -1 不确定
    */
    private int checkedState=-1;
    private boolean isHideChecked = true;
    private String name;
    private int level;
    private int icon;
    private List<TreeNode> childrenNodes = new ArrayList<>();
    private TreeNode parent;
    
    public TreeNode() {
    }
    
    public TreeNode(String id, String pId, String name) {
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
    
    public String getpId() {
        return this.pId;
    }
    
    public void setpId(String pId) {
        this.pId = pId;
    }
    
    public boolean isExpand() {
        return this.isExpand;
    }
    
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            Iterator var3 = this.childrenNodes.iterator();
            
            while(var3.hasNext()) {
                TreeNode node = (TreeNode)var3.next();
                node.setExpand(isExpand);
            }
        }
        
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getLevel() {
        return this.parent == null ? 0 : this.parent.getLevel() + 1;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getIcon() {
        return this.icon;
    }
    
    public void setIcon(int icon) {
        this.icon = icon;
    }
    
    public List<TreeNode> getChildrenNodes() {
        return this.childrenNodes;
    }
    
    public void setChildrenNodes(List<TreeNode> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }
    
    public TreeNode getParent() {
        return this.parent;
    }
    
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
    
    public boolean isRoot() {
        return this.parent == null;
    }
    
    public boolean isLeaf() {
        return this.childrenNodes.size() == 0;
    }
    
    public boolean isParentExpand() {
        return this.parent != null && this.parent.isExpand();
    }
    
    public boolean isChecked() {
        return this.isChecked;
    }
    
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    
    public int getCheckedState()
    {
        return checkedState;
    }
    public void setCheckedState(int checkedState)
    {
         this.checkedState= checkedState;
    }
    
    
    public boolean isHideChecked() {
        return this.isHideChecked;
    }
    
    public void setHideChecked(boolean isHideChecked) {
        this.isHideChecked = isHideChecked;
    }
}
