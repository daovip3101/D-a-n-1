package com.example.peter.project1.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.peter.project1.ChiTietActivity;
import com.example.peter.project1.Interface.ILoadMore;
import com.example.peter.project1.Model.SanPham;
import com.example.peter.project1.R;
import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.peter.project1.SanPhamActivity.Addgiohang;

/**
 * Created by daovip on 3/22/2018.
 */
class LoadingViewHoder extends RecyclerView.ViewHolder{
    public LoadingDots progressBar;
    public LoadingViewHoder(View itemView) {
        super(itemView);
        progressBar=itemView.findViewById(R.id.progressBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder{
    public ImageButton btn_giohang_sanpham;
    public ImageView img;
    public TextView txt_ten,txt_gia;
    public ItemViewHolder(View itemView) {
        super(itemView);
        btn_giohang_sanpham=itemView.findViewById(R.id.btn_giohang_sanpham);
        img=itemView.findViewById(R.id.imgV_hinhsp_sanpham);
        txt_ten=itemView.findViewById(R.id.tv_tensp_sanpham);
        txt_gia=itemView.findViewById(R.id.tv_giasp_sanpham);


    }
}

public class adaoter_rc_san_pham extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM=0, VIEW_TYPE_LOADING=1;
    ILoadMore iLoadMore;
    boolean isLoading;
    Activity activity;
    ArrayList<SanPham> arrayList;
    int visibleThreshold =5;
    int lastVisibleItem,totlItemCout;
    int size;
    public  void updateArralist(ArrayList<SanPham> arrayList){
        this.arrayList=arrayList;
    }
    public adaoter_rc_san_pham(RecyclerView recyclerView, final Activity activity, ArrayList<SanPham> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totlItemCout=linearLayoutManager.getItemCount();
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totlItemCout<=(lastVisibleItem+visibleThreshold)){
                    if(iLoadMore !=null)
                        iLoadMore.onLoadMore();
                    isLoading=true;
                }

            }
        });
    }
    //Press ctrl+0


    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore (ILoadMore loadMore){
        this.iLoadMore=loadMore;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType== VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.view1o_sanpham,parent,false);
            return new ItemViewHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHoder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        size=getItemCount();
        if(holder instanceof  ItemViewHolder){
            final SanPham sp;
             sp = arrayList.get(position);
            final String tenSp =sp.getTenSanPha();
             int giaSp=sp.getDongia();
             int hinhSp=sp.getHinh();
            ((ItemViewHolder) holder).txt_gia.setText(giaSp+"");
            ((ItemViewHolder) holder).txt_ten.setText(tenSp);
            ((ItemViewHolder) holder).img.setImageResource(R.drawable.garan);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(activity.getApplicationContext(),ChiTietActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("SanPham",sp);
                    startActivity(activity.getApplicationContext(),i,null);
                }
            });
            ((ItemViewHolder) holder).btn_giohang_sanpham.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Add item to arraylisGioHang
                   Addgiohang(sp);
               }
          });
        }else if (holder instanceof LoadingViewHoder){
            LoadingViewHoder loadingViewHoder = (LoadingViewHoder)holder;
            loadingViewHoder.progressBar.setAutoPlay(true);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}
