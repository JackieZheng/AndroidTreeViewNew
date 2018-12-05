package net.admans.androidtreeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.admans.androidtreeviewlibrary.Bean.NodeBean;
import net.admans.androidtreeviewlibrary.Tree.TreeListViewAdapter;
import net.admans.androidtreeviewlibrary.Tree.TreeNode;

import java.util.List;

public class MyTreeListViewAdapter<T> extends TreeListViewAdapter<T>
{
    public MyTreeListViewAdapter(ListView mTree, Context context, List<NodeBean> datas, int defaultExpandLevel, boolean isHide) throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel, isHide);
    }
    
    public View getConvertView(TreeNode node, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.label = convertView.findViewById(R.id.id_treenode_name);
            viewHolder.checkBox = convertView.findViewById(R.id.id_treeNode_check);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        
        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }
        
        if (node.isHideChecked()) {
            viewHolder.checkBox.setVisibility(View.GONE);
        } else {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(node.isChecked());//设置选中状态
            //this.setCheckBoxBg(viewHolder.checkBox, node.isChecked());//如果用背景显示选中状态，修改背景
        }
        
        viewHolder.label.setText(node.getName());
        return convertView;
    }
    
    private void setCheckBoxBg(CheckBox cb, boolean isChecked) {
        if (isChecked) {
            //cb.setBackgroundResource(2130837505);
        } else {
            //cb.setBackgroundResource(2130837504);
        }
        
    }
    
    private final class ViewHolder {
        ImageView icon;
        TextView label;
        CheckBox checkBox;
    
        private ViewHolder() {
        }
    }
}
