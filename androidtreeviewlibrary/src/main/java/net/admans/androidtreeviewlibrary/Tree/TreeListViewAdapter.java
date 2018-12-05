package net.admans.androidtreeviewlibrary.Tree;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import net.admans.androidtreeviewlibrary.Bean.NodeBean;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeListViewAdapter<T> extends BaseAdapter
{
    private List<TreeNode> mNodes;
    protected LayoutInflater mInflater;
    private List<TreeNode> mAllNodes;
    private TreeListViewAdapter.OnTreeNodeClickListener onTreeNodeClickListener;
    
    public void setOnTreeNodeClickListener(TreeListViewAdapter.OnTreeNodeClickListener onTreeNodeClickListener)
    {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }
    
    public TreeListViewAdapter(ListView mTree, Context context, List<NodeBean> datas, int defaultExpandLevel, boolean isHide) throws IllegalArgumentException, IllegalAccessException
    {
        
        this.mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel, isHide);
        this.mNodes = TreeHelper.filterVisibleNode(this.mAllNodes);
        this.mInflater = LayoutInflater.from(context);
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TreeListViewAdapter.this.expandOrCollapse(position);
                if (TreeListViewAdapter.this.onTreeNodeClickListener != null)
                {
                    TreeListViewAdapter.this.onTreeNodeClickListener.onClick(TreeListViewAdapter.this.mNodes.get(position), position);
                }
                
            }
        });
    }
    
    private void expandOrCollapse(int position)
    {
        TreeNode n = this.mNodes.get(position);
        if (n != null && !n.isLeaf())
        {
            n.setExpand(!n.isExpand());
            this.mNodes = TreeHelper.filterVisibleNode(this.mAllNodes);
            this.notifyDataSetChanged();
        }
        
    }
    
    public int getCount()
    {
        return this.mNodes.size();
    }
    
    public Object getItem(int position)
    {
        return this.mNodes.get(position);
    }
    
    public long getItemId(int position)
    {
        return (long) position;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final TreeNode TreeNode = this.mNodes.get(position);
        convertView = this.getConvertView(TreeNode, position, convertView, parent);
        convertView.setPadding(TreeNode.getLevel() * 30, 3, 3, 3);
        if (!TreeNode.isHideChecked())
        {
            RelativeLayout myView = (RelativeLayout) convertView;
            CheckBox cb = (CheckBox) myView.getChildAt(1);
            cb.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                }
            });
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    TreeHelper.setNodeChecked(TreeNode, isChecked);
                    List<TreeNode> checkedNodes = new ArrayList<>();
                    for (TreeNode n : TreeListViewAdapter.this.mAllNodes)
                    {
                        if (n.isChecked())
                        {
                            checkedNodes.add(n);
                        }
                    }
    
                    List<TreeNode> filterNodes = new ArrayList<>();
                    for (TreeNode n : TreeListViewAdapter.this.mAllNodes)
                    {
        
                        boolean hasChildAllChecked = hasChildAllChecked(n);
                        boolean hasParentIsExist = hasParentIsExist(n, filterNodes);
                        if (n.isChecked() && hasChildAllChecked && !hasParentIsExist)
                        {
                            filterNodes.add(n);
                        }
        
        
                    }
                    TreeListViewAdapter.this.onTreeNodeClickListener.onCheckChange(TreeNode, position, checkedNodes, filterNodes);
                    TreeListViewAdapter.this.notifyDataSetChanged();
                }
            });
        }
        
        return convertView;
    }
    
    private boolean hasChildAllChecked(TreeNode node)
    {
        boolean hasChildAllChecked = true;
        if (!node.isChecked())
        {
            hasChildAllChecked = node.isChecked();
        }
        else if (node.isLeaf())
        {
            hasChildAllChecked = node.isChecked();
        }
        else
        {
            for (TreeNode n : node.getChildrenNodes())
            {
                if (n.getChildrenNodes()
                        .size() == 0)
                {
                    hasChildAllChecked = n.isChecked();
                }
                else
                {
                    hasChildAllChecked = hasChildAllChecked(n);
                }
                if (!hasChildAllChecked)
                {
                    break;
                }
            }
        }
        
        return hasChildAllChecked;
        
    }
    
    private boolean hasParentIsExist(TreeNode node, List<TreeNode> filterNodes)
    {
        boolean hasParentIsExist = false;
        if (!node.isRoot())
        {
            if (filterNodes.contains(node.getParent()))
            {
                hasParentIsExist = true;
            }
            else
            {
                hasParentIsExist = hasParentIsExist(node.getParent(), filterNodes);
            }
            
        }
        return hasParentIsExist;
    }
    
    public abstract View getConvertView(TreeNode treeNode, int position, View view, ViewGroup viewGroup);
    
    public void updateView(boolean isHide)
    {
        
        for (TreeNode TreeNode : this.mAllNodes)
        {
            TreeNode.setHideChecked(isHide);
        }
        
        this.notifyDataSetChanged();
    }
    
    public interface OnTreeNodeClickListener
    {
        void onClick(TreeNode treeNode, int pos);
        
        void onCheckChange(TreeNode treeNode, int pos, List<TreeNode> nodeList, List<TreeNode> filterNodes);
    }
}
