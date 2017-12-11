package com.example.user.with_family.ui.f_album;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.with_family.R;
import com.example.user.with_family.util.AlbumDAO;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by 안탄 on 2017-12-07.
 */

public class Album_Adapter extends RecyclerView.Adapter<Album_Adapter.ViewHolder>{

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;


    Context mcontext;
    private ArrayList<AlbumDAO> arrayList = new ArrayList<>();

    public Album_Adapter(Context context){
        this.mcontext = context;

        mFirebaseStorage = FirebaseStorage.getInstance();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //public TextView textView;
        public ImageView imgview;

        // 뷰홀더 -> UI(?)부분에 해당되는 것들을 뷰에 미리 붙여서 고정
        public ViewHolder(View itemview){
            super(itemview);
            imgview = (ImageView)itemview.findViewById(R.id.album_imageview);
            //textView = (TextView)itemview.findViewById(R.id.album_textview);
        }

        @Override
        public void onClick(View view) {

        }
    }


    // 포지션값 가져오는 부분
    @Override
    public int getItemCount(){
        return arrayList.size();
    }

    public void addItem(AlbumDAO albumDAO){
        arrayList.add(albumDAO);
        notifyDataSetChanged();
    }

    @Override //아이템을 위한 뷰를 만들어서 뷰홀더에 넣어서 리턴
    public Album_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contatctview = inflater.inflate(R.layout.fragment_album_item, parent, false);
        Album_Adapter.ViewHolder viewHolder = new Album_Adapter.ViewHolder(contatctview);
        return viewHolder;
    }


    @Override   // 뷰홀더의 뷰에 position에 해당되는 데이터를 넣음 = 데이터 처리하는곳
    public void onBindViewHolder(final Album_Adapter.ViewHolder viewHolder, int position){
        /*viewHolder.textView.setText(arrayList.get(position).getDate().toString());
        System.out.println("마지막 날짜 : " + arrayList.get(position).getDate());*/

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams)viewHolder.itemView.getLayoutParams();
        //layoutParams.setFullSpan(true);
        layoutParams.isFullSpan();


       /* mStorageReference = mFirebaseStorage.getReferenceFromUrl("gs://ahntanwithfamily.appspot.com").child(arrayList.get(position).getImg_ref());
        Glide.with(mcontext).using(new FirebaseImageLoader()).load(mStorageReference).into(viewHolder.imgview);
*/
        mStorageReference = mFirebaseStorage.getReferenceFromUrl("gs://ahntanwithfamily.appspot.com").child(arrayList.get(position).getImg_ref());
        mStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imgURL = uri.toString();
                System.out.println("이미지 가져오냐? : " + imgURL);
                Glide.with(mcontext.getApplicationContext()).load(imgURL).into(viewHolder.imgview);
            }

        });



    }


    public void removeItem(){
        int size = arrayList.size();
        for(int i=0; i<size; i++) {
            arrayList.remove(0);
            notifyItemRemoved(0);
        }
    }


}
