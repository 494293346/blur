package cn.asheng.blur;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.asheng.blur.adapter.MyAdapter;
import cn.asheng.blur.model.MyModel;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView listview;

    private MyAdapter myAdapter;
    private List<MyModel> rowsBeanList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        initView();
        getListData();
    }

    private void initView() {
        myAdapter = new MyAdapter(context);
        myAdapter.setDataCache(rowsBeanList);
        listview.setAdapter(myAdapter);
    }

    private void getListData() {
        for (int i = 0; i < 10; i++) {
            MyModel myModel = new MyModel();
            myModel.setBg(i % 2 == 0 ? R.mipmap.gzh_jingxuan_bg : R.mipmap.gzh_zuixin_bg);
            List list = new ArrayList();
            for (int j = 0; j < 10; j++) {
                list.add(j + "");
            }
            myModel.setItems(list);
            rowsBeanList.add(myModel);
            myAdapter.notifyDataSetChanged();
        }
    }
}
