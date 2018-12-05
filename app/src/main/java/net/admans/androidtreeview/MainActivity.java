package net.admans.androidtreeview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.admans.androidtreeviewlibrary.Bean.NodeBean;
import net.admans.androidtreeviewlibrary.Tree.TreeHelper;
import net.admans.androidtreeviewlibrary.Tree.TreeListViewAdapter;
import net.admans.androidtreeviewlibrary.Tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
{
    private MyTreeListViewAdapter<NodeBean> adapter;
    private List<NodeBean> mDatas = new ArrayList<>();
    private boolean isCheckBoxHide = false;
    
    public MainActivity()
    {
    }
    
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.initDatas();
        ListView treeLv = this.findViewById(R.id.tree_lv);
        Button checkSwitchBtn = this.findViewById(R.id.check_switch_btn);
        final TextView textView=this.findViewById(R.id.sltResult);
        checkSwitchBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                MainActivity.this.isCheckBoxHide = !MainActivity.this.isCheckBoxHide;
                MainActivity.this.adapter.updateView(MainActivity.this.isCheckBoxHide);
            }
        });
        
        try
        {
            this.adapter = new MyTreeListViewAdapter<>(treeLv, this, this.mDatas, 10, this.isCheckBoxHide);
            this.adapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener()
            {
                @SuppressLint("SetTextI18n")
                public void onClick(TreeNode node, int position)
                {
                    if (node.isLeaf())
                    {
                        //Toast.makeText(MainActivity.this.getApplicationContext(), node.getName(), Toast.LENGTH_SHORT).show();
                        Log.i("selectNote:", node.getName());
                        textView.setText("点击了："+node.getName());
                    }
                    
                }
                
                public void onCheckChange(TreeNode node, int position, List<TreeNode> checkedNodes,List<TreeNode> filterNodes)
                {
                    StringBuilder sb = new StringBuilder();
                    String info="";
                    for (TreeNode n : checkedNodes)
                    {
                        sb.append(n.getName())
                                .append(";");
                        
                    }
                    Log.i("selectNotes:", "共选择了" + checkedNodes.size() + "个:" + sb.toString());
                    info+="共选择了：" + checkedNodes.size() + "个:" + sb.toString()+"\r\n";
                    sb=new StringBuilder();
                    for (TreeNode n : filterNodes)
                    {
                        sb.append(n.getName())
                                .append(";");
        
                    }
                    
                    //Toast.makeText(MainActivity.this.getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("selectFilterNotes:", "共选择了" + filterNodes.size() + "个:" + sb.toString());
                    info+="过滤重后：" + filterNodes.size() + "个:" + sb.toString();
                    textView.setText(info);
                }
            });
        } catch (IllegalArgumentException | IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        
        treeLv.setAdapter(this.adapter);
    }
    
    private void initDatas()
    {
        this.mDatas = new ArrayList<>();
        this.mDatas.add(new NodeBean("100", null, "第一章"));
        this.mDatas.add(new NodeBean("110", "100", "第一节"));
        this.mDatas.add(new NodeBean("111", "110", "第一课"));
        this.mDatas.add(new NodeBean("112", "110", "第二课"));
        this.mDatas.add(new NodeBean("113", "110", "第三课"));
        this.mDatas.add(new NodeBean("120", "100", "第二节"));
        this.mDatas.add(new NodeBean("121", "120", "第一课"));
    }
}
