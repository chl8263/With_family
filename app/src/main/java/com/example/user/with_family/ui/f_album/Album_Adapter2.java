package com.example.user.with_family.ui.f_album;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.with_family.R;
import com.example.user.with_family.util.AlbumDAO;
import com.example.user.with_family.util.AlbumDAO2;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by 안탄 on 2017-12-07.
 */

public class Album_Adapter2 extends RecyclerView.Adapter<Album_Adapter2.ViewHolder>{


    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mdatabaseReference;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private  Album_Fragment album_fragment;

    private StaggeredGridLayoutManager mStgaggeredGridLayoutManager;

    Context mcontext;
    //ArrayList<AlbumDAO> arrayList;

    private LinearLayout album_linearLayout;
    private Album_Adapter album_adapter;
    private ArrayList<AlbumDAO2> save_date = new ArrayList<>();
    private ArrayList<AlbumDAO> arrayList = new ArrayList<>();

    private ArrayList<String> check_date = new ArrayList<>();
    private int count = 0;
    private SharedPreferences sharedPreferences;
    private String room_name;

    public Album_Adapter2(Context context){

        System.out.println("1-5");
        this.mcontext = context;
        //arrayList = new ArrayList<>();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        mdatabaseReference = mfirebaseDatabase.getReference().child("register").child("r_room");

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView;
        public RecyclerView recyclerView;


        // 뷰홀더 -> UI(?)부분에 해당되는 것들을 뷰에 미리 붙여서 고정
        public ViewHolder(View itemview){
            super(itemview);
            System.out.println("1-4");
            recyclerView = (RecyclerView)itemview.findViewById(R.id.album_mid_recycleview);
            textView = (TextView)itemview.findViewById(R.id.first_date);
            album_linearLayout = (LinearLayout)itemview.findViewById(R.id.Album_LinearLayout);

            album_adapter = new Album_Adapter(itemview.getContext());
            recyclerView.setAdapter(album_adapter);
        }

        @Override
        public void onClick(View view) {

        }
    }


    // 포지션값 가져오는 부분
    @Override
    public int getItemCount(){
        return save_date.size();
    }

    /*public void addItem(AlbumDAO albumDAO){
        System.out.println("1-3");
        arrayList.add(albumDAO);
        notifyDataSetChanged();
    }*/

    public void addItem(AlbumDAO2 albumDAO2){

        System.out.println("1-3 : " + albumDAO2.getDate() + " " + albumDAO2.getRoom_name());

        save_date.add(albumDAO2);

        notifyDataSetChanged();
    }

    @Override //아이템을 위한 뷰를 만들어서 뷰홀더에 넣어서 리턴
    public Album_Adapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        System.out.println("1-2");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contatctview = inflater.inflate(R.layout.fragment_album_item2, parent, false);
        Album_Adapter2.ViewHolder viewHolder = new Album_Adapter2.ViewHolder(contatctview);
        return viewHolder;
    }


    @Override   // 뷰홀더의 뷰에 position에 해당되는 데이터를 넣음 = 데이터 처리하는곳
    public void onBindViewHolder(final Album_Adapter2.ViewHolder viewHolder, final int position){

        // 특정 날짜
        viewHolder.textView.setText(save_date.get(position).getDate());
        //viewHolder.textView.setText(Integer.toString(position));
        // 특정 날짜에 해당되는 애들;
        mStgaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        viewHolder.recyclerView.setLayoutManager(mStgaggeredGridLayoutManager);

        //removed();
        System.out.println("크기 : " + Album_Fragment.albumDAOList.size());
        for (int i = 0; i < Album_Fragment.albumDAOList.size(); i++) {
            System.out.println("체크데이터 : " + i + "번째 포지션값 : " + save_date.get(position).getDate() + "현재 앨범 : " + Album_Fragment.albumDAOList.get(i).getImg_ref());
            //해당하는 포지션과 리스트에 들어가있는 데이터값이 같을 때만 다음 어댑터로 사진경로만 보냄
            if (save_date.get(position).getDate().equals(Album_Fragment.albumDAOList.get(i).getDate())) {
                album_adapter.addItem(new AlbumDAO(Album_Fragment.albumDAOList.get(i).getDate(), Album_Fragment.albumDAOList.get(i).getImg_ref()));
                System.out.println("포지션은 : " + position + " 들어간데이터는 : " + Album_Fragment.albumDAOList.get(i).getDate() + " " + i + "번째 : " + Album_Fragment.albumDAOList.get(i).getImg_ref());
            }
        }


    }

    public void removed(){
        try {
            album_adapter.removeItem();
        }
        catch (Exception e){
            System.out.println("이셉션");
        }
    }


    public void removeItem(){
        int size = save_date.size();
        for(int i=0; i<size; i++) {
            save_date.remove(0);
            notifyItemRemoved(0);
        }
        System.out.println("어댑터안 다 지웠다 : " + save_date.size());
    }


}
