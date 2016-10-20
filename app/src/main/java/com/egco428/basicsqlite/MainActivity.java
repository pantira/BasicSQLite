package com.egco428.basicsqlite;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private CommentDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new CommentDataSource(this);
        dataSource.open();
        List<Comment> values = dataSource.getAllComments();
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);

    }
    public void onClick(View view){
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>)getListAdapter();
        Comment comment = null;
        switch(view.getId()){
            case R.id.addBtn:
                String[] comments = new String[]{"Good","Cool","Whatever","Very nice"};
                int nextInt = new Random().nextInt(4);
                comment = dataSource.createComment(comments[nextInt]);
                adapter.add(comment);
                break;
            case R.id.deleteBtn:
                if(getListAdapter().getCount()>0){
                    comment = (Comment)getListAdapter().getItem(0);
                    dataSource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onListItemClick(ListView parent, View view, int position, long id){
        super.onListItemClick(parent,view,position,id);
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>)getListAdapter();
        Comment comment = null;
        comment = (Comment)getListAdapter().getItem(position);
        dataSource.deleteComment(comment);
        adapter.remove(comment);

    }
    @Override
    protected void onResume(){
        super.onResume();
        dataSource.open();
    }
    @Override
    protected void onPause(){
        super.onPause();
        dataSource.close();
    }
}
