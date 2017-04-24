# blur

关于高斯模糊处理方法，并非自己造轮子，是对别人现有的框架的使用 

##需求

 完成以下效果——在横向滑动时对背景进行模糊处理

 ![效果图](https://github.com/494293346/blur/blob/master/images/image.gif)

 由于在模拟器上运行，模糊效果并不是很明显，再补上一张真机效果图

 ![图片描述](https://github.com/494293346/blur/blob/master/images/picture.png)
##问题
 ```
 看到这个很多人一看就想到高斯模糊，没错当时我也是这么做的，不过不知道是不是我当时的打开方式不对，
 当时我用的方法是：监听横向滑动距离，然后改变背景的模糊程度，然后就————卡的一笔 = .= 。
 由于改变模糊程度是十分耗性能的操作，并且是放在adapter中，所以造成了十分严重的卡顿现象。
 （关于这个问题，可能也是我的使用方式有误，如果不是我所说的原因，欢迎指出）。
  ```
  ##解决方法
   ```
   为此，我想了一个折中的解决方案：在背景图上再盖上一层图片，相当于有两张背景图，
   在底层的是没有经过任何处理的原图，在顶层的是经过高斯模糊处理的图片，
   同样是监听横向滑动控件的偏移量，但这次改变的是顶层图片的透明度————毕竟改变透明度
   没有改变模糊层度那么耗性能，这样子就实现了需求需要的效果了，并且
   也不会有卡顿的现象了。
```
   ##原料
  ```
  引用库：glide,glide-transformations
   ```
 ##关键代码
```
   public class MyAdapter extends BaseAdapter<MyModel> {
    private float marginLeft;//距离左边距离

    public MyAdapter(Context context) {
        super(context);
        init();
        marginLeft = Utils.dip2px(context, 179);
    }

    @Override
    public void init() {

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_my, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyModel bean = getItem(position);
        //设置图片
        dealWithImg(holder, bean.getBg());
        //设置适配器
        dealWithLinear(holder, bean);
        //处理模糊效果
        dealWithMohu(holder, position);

        return convertView;
    }

    private void dealWithLinear(ViewHolder holder, MyModel bean) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        if (bean.getItems()!= null) {
            MyGridAdapter gzhHomeGridAdapter = new MyGridAdapter(context);
            holder.recyclerView.setAdapter(gzhHomeGridAdapter);
            gzhHomeGridAdapter.updateItems(bean.getItems());
        }
    }

    private void dealWithImg(ViewHolder holder, int bean) {
        Glide
                .with(context)
                .load(bean)
                .placeholder(R.mipmap.loading_spinner_long)
                .crossFade()
                .into(holder.imgBg);
        Glide
                .with(context)
                .load(bean)
                .placeholder(R.mipmap.loading_spinner_long)
                .crossFade()
                .bitmapTransform(new BlurTransformation(context,25))
                .into(holder.imgBgMohu);
    }

    private void dealWithMohu(final ViewHolder holder, final int position) {
        //透明度为0
        holder.imgBgMohu.setAlpha(0.0f);
        holder.recyclerView.setTag(position);
        holder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDx = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDx -= dx;
                int pos = (int) recyclerView.getTag();
                float move = Math.abs(totalDx);
                if (position == pos && move < marginLeft && move > 1) {
                    float alpha = move / 500;
                    holder.imgBgMohu.setAlpha(Math.min(alpha, 1));
                }
            }
        });
    }

    class ViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.imgBg)
        ImageView imgBg;
        @BindView(R.id.imgBgMohu)
        ImageView imgBgMohu;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
```
