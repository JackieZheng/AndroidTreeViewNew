package net.admans.androidtreeviewlibrary.Tree;

import net.admans.androidtreeviewlibrary.Bean.NodeBean;
import net.admans.androidtreeviewlibrary.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeHelper
{
    public TreeHelper()
    {
    }
    
    public static List<TreeNode> filterVisibleNode(List<TreeNode> allNodes)
    {
        List<TreeNode> visibleNodes = new ArrayList<>();
        Iterator iterator = allNodes.iterator();
        
        while (true)
        {
            TreeNode TreeNode;
            do
            {
                if (!iterator.hasNext())
                {
                    return visibleNodes;
                }
                
                TreeNode = (TreeNode) iterator.next();
            } while (!TreeNode.isRoot() && !TreeNode.isParentExpand());
            
            setNodeIcon(TreeNode);
            visibleNodes.add(TreeNode);
        }
    }
    
    public static List<TreeNode> getSortedNodes(List<NodeBean> datas, int defaultExpandLevel, boolean isHide) throws IllegalAccessException, IllegalArgumentException
    {
        List<TreeNode> sortedNodes = new ArrayList<>();
        List<TreeNode> nodes = convertData2Nodes(datas, isHide);
        List<TreeNode> rootNodes = getRootNodes(nodes);
        
        for (TreeNode treeNode : rootNodes)
        {
            addNode(sortedNodes, treeNode, defaultExpandLevel, 1);
        }
        
        return sortedNodes;
    }
    
    private static void addNode(List<TreeNode> nodes, TreeNode TreeNode, int defaultExpandLevel, int currentLevel)
    {
        nodes.add(TreeNode);
        if (defaultExpandLevel >= currentLevel)
        {
            TreeNode.setExpand(true);
        }
        
        if (!TreeNode.isLeaf())
        {
            for (int i = 0; i < TreeNode.getChildrenNodes()
                    .size(); ++i)
            {
                addNode(nodes, TreeNode.getChildrenNodes()
                        .get(i), defaultExpandLevel, currentLevel + 1);
            }
            
        }
    }
    
    private static List<TreeNode> getRootNodes(List<TreeNode> nodes)
    {
        List<TreeNode> rootNodes = new ArrayList<>();
        
        for (TreeNode treeNode : nodes)
        {
            if (treeNode.isRoot())
            {
                rootNodes.add(treeNode);
            }
        }
        
        return rootNodes;
    }
    
    private static List<TreeNode> convertData2Nodes(List<NodeBean> datas, boolean isHide) throws IllegalAccessException, IllegalArgumentException
    {
        List<TreeNode> nodes = new ArrayList<>();
        TreeNode TreeNode;
        
        for (NodeBean node : datas)
        {
            TreeNode = new TreeNode(node.getId(), node.getPid(), node.getName());
            TreeNode.setHideChecked(isHide);
            nodes.add(TreeNode);
        }
        
        for (TreeNode n : nodes)
        {
            for (TreeNode m : nodes)
            {
                if (m.getpId() != null && n.getId()
                        .equals(m.getpId()))
                {
                    if (!n.getChildrenNodes()
                            .contains(m))
                    {
                        n.getChildrenNodes()
                                .add(m);
                    }
                    m.setParent(n);
                }
                else if (n.getpId() != null && n.getpId()
                        .equals(m.getId()))
                {
                    n.setParent(m);
                    if (!m.getChildrenNodes()
                            .contains(n))
                    {
                        m.getChildrenNodes()
                                .add(n);
                    }
                }
            }
            
        }
        
        for (TreeNode n : nodes)
        {
            setNodeIcon(n);
        }
        
        return nodes;
    }
    
    private static void setNodeIcon(TreeNode TreeNode)
    {
        if (TreeNode.getChildrenNodes()
                .size() > 0 && TreeNode.isExpand())
        {
            TreeNode.setIcon(R.drawable.tree_ex);
        }
        else if (TreeNode.getChildrenNodes()
                .size() > 0 && !TreeNode.isExpand())
        {
            TreeNode.setIcon(R.drawable.tree_ec);
        }
        else
        {
            TreeNode.setIcon(-1);
        }
        
    }
    
    public static void setNodeChecked(TreeNode TreeNode, boolean isChecked)
    {
        TreeNode.setChecked(isChecked);
        setChildrenNodeChecked(TreeNode, isChecked);
        setParentNodeChecked(TreeNode);
    }
    
    private static void setChildrenNodeChecked(TreeNode TreeNode, boolean isChecked)
    {
        TreeNode.setChecked(isChecked);
        if (!TreeNode.isLeaf())
        {
            for (TreeNode n : TreeNode.getChildrenNodes())
            {
                setChildrenNodeChecked(n, isChecked);
            }
        }
        
    }
    
    private static void setParentNodeChecked(TreeNode TreeNode)
    {
        if (!TreeNode.isRoot())
        {
            TreeNode rootNode = TreeNode.getParent();
            boolean isAllChecked = true;
            boolean isAllUnchecked=true;
            
            for (TreeNode n : rootNode.getChildrenNodes())
            {
                if (!n.isChecked())
                {
                    isAllChecked = false;
                    break;
                }
               
            }
            for (TreeNode n : rootNode.getChildrenNodes())
            {
                if (n.isChecked())
                {
                    isAllUnchecked = false;
                    break;
                }
            }
            if (isAllChecked)
            {
                rootNode.setChecked(true);
            }
            if(isAllUnchecked)
            {
                rootNode.setChecked(false);
            }
            
            setParentNodeChecked(rootNode);
        }
        
    }
    
  
}
