package com.example.user.with_family.ui.f_home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.user.with_family.R;
import com.example.user.with_family.util.Contacts;
import com.example.user.with_family.util.UserDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 2017-11-11.
 */

public class HomeFragment extends Fragment {

    public static Handler mhandler;
    private static final int request_code = 0;
    SharedPreferences sharedPreferences;            // 저장된 현재 접속자 정보
    FirebaseDatabase databaseRef;
    DatabaseReference roominfoRef;                  // 접속 유저 정보 가져오는 레퍼런스
    DatabaseReference userinfoRef2;                  // 접속 유저가 친구 추가할때 쓰는 레퍼런스

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    private RelativeLayout home_relativelayout;
    private FrameLayout home_framelayout;
    private Button room_create_btn;
    private FloatingActionButton floatingActionButton;
    private RecyclerView user_recyclerView ;
    private String login_user;
    private String user_room_name;
    Main_JoinStateAdapter main_joinstateAdapter;
    UserDAO dao;
    private static List<String> room_userDAOList = new ArrayList<>();     // 방에 들어가있는 유저 목록
    private static List<UserDAO> userDAOList = new ArrayList<>();      // 유저 정보들 저장해놓을 리스트


    public HomeFragment(){

        databaseRef = FirebaseDatabase.getInstance();
        roominfoRef = databaseRef.getReference().child("register").child("r_room");         // 해당 방에 있는 사용자들을 가져오는 레퍼런스
        userinfoRef2 = databaseRef.getReference().child("register").child("user");          // 모든 사용자들을 가져오는 레퍼런스
        mFirebaseStorage = FirebaseStorage.getInstance();
        //mStorageReference = mFirebaseStorage.getReferenceFromUrl("gs://ahntanwithfamily.appspot.com/a/a.png");

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        user_recyclerView = (RecyclerView)view.findViewById(R.id.main_joinstate_recycleview);
        home_relativelayout = (RelativeLayout)view.findViewById(R.id.home_relativeLayout);
        home_framelayout = (FrameLayout)view.findViewById(R.id.home_frameLayout);
        return view;
    }

        // 친구 추가버튼에서 넘어온 값
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            String friend_id = "+8210";

            if(data.getStringExtra("Result").equals("delete")){
                Toast.makeText(getContext(), "삭제 : "+ data.getStringExtra("Result"), Toast.LENGTH_LONG).show();
            }
            else if(data.getStringExtra("Result").equals("create_room")){
                String room = data.getStringExtra("room_name").toString();
                dao.setRoom_name(room);
                room_addUser(dao, room);
            }
            else
                friend_id += data.getStringExtra("Result");

            // 친구추가 값(전체 회원목록)에서 해당되는 친구가 있으면 추가
            for(int j=0; j<userDAOList.size(); j++){
                if(userDAOList.get(j).getId().equals(friend_id)){
                    room_addUser(userDAOList.get(j), user_room_name);
                }
            }



    }

    // 최종적으로 액티비티에 붙여주는곳
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        user_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //user_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        main_joinstateAdapter = new Main_JoinStateAdapter(getView().getContext());
        user_recyclerView.setAdapter(main_joinstateAdapter);

        // 방생성 버튼
        room_create_btn = (Button)getView().findViewById(R.id.room_create_btn);
        room_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateRoomActivity.class);
                startActivityForResult(intent, request_code);
            }
        });

        sharedPreferences = this.getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        login_user = sharedPreferences.getString("myid", "01012345678");
        user_room_name = sharedPreferences.getString("room_name", "room!!");

        // 접속자 정보만 따온거
        userinfoRef2.child(login_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dao = dataSnapshot.getValue(UserDAO.class);
                mStorageReference = mFirebaseStorage.getReferenceFromUrl(dao.getUserimg());

                // 사용자가 속한 방이없다면, 있다면 원래대로 데이터를 불러와 띄움
                if(dao.getRoom_name().equals("null")){
                    System.out.println("뷰 첫번쨰 들어옴");
                    home_relativelayout.setVisibility(View.VISIBLE);
                    home_framelayout.setVisibility(View.INVISIBLE);
                }
                //방을 생성하면 돌아감
                else if(!dao.getRoom_name().equals("null")){
                    System.out.println("뷰 두번쨰 들어옴");
                    home_relativelayout.setVisibility(View.INVISIBLE);
                    home_framelayout.setVisibility(View.VISIBLE);
                    dataRun();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    public void dataRun(){
        // 모든사용자 저장하는곳
        userinfoRef2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CurrentListRemove(userDAOList);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDAO dao2 = snapshot.getValue(UserDAO.class);
                    userDAOList.add(dao2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // 방에 있는 사람들 목록 저장하는곳
        roominfoRef.child(dao.getRoom_name()).child("user_tree").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CurrentListRemove(room_userDAOList);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String user = snapshot.getKey().toString();
                    System.out.println("무슨 값이니" + user);
                    room_userDAOList.add(user);
                    write();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        floatingActionButton = (FloatingActionButton)getActivity().findViewById(R.id.room_add_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            // 방 추가하는 버튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddFriendActivity.class);
                startActivityForResult(intent, request_code);

            }

        });
    }

    public void write(){
        removed();
        for(int i=0; i<room_userDAOList.size(); i++){
            if(i==0){
                main_joinstateAdapter.addItem(new Contacts(mStorageReference.toString(), R.drawable.day_background, dao.getName(), dao.getNick(), dao.getId())); // 이미지 url, 이름 name
            }
            else {
                System.out.println("사이즈 1 : " + room_userDAOList.size());
                System.out.println("사이즈 2 : " + userDAOList.size());

                for(int j=0; j<userDAOList.size(); j++){
                    System.out.println("룸 리스트 : " + room_userDAOList.get(i).toString());
                    System.out.println("all 리스트 : " + userDAOList.get(j).getId().toString());

                    if(room_userDAOList.get(i).toString().equals(userDAOList.get(j).getId().toString())){
                        main_joinstateAdapter.addItem(new Contacts(userDAOList.get(j).getUserimg(), R.drawable.day_background, userDAOList.get(j).getName(), userDAOList.get(j).getNick(), userDAOList.get(j).getId())); // 이미지 url, 이름 name
                    }
                }
            }
        }
    }

    public void room_addUser(UserDAO userDAO, String room_name){
        //room에 해당되는 데이터베이스에 저장하는 부분
        Map<String, Object> dataValues = new HashMap<>();

        dataValues.put("id", userDAO.getId());
        dataValues.put("pw", userDAO.getPw());
        dataValues.put("name", userDAO.getName());
        dataValues.put("bir", userDAO.getBir());
        dataValues.put("nick", userDAO.getNick());
        dataValues.put("userimg", userDAO.getUserimg());

        DatabaseReference dr = roominfoRef.child(room_name).child("user_tree").child(userDAO.getId());
        dr.setValue(dataValues);

        // 본래 회원정보에 room을 추가해주는 부분
        Map<String, Object> dataValues2 = new HashMap<>();

        dataValues2.put("id", userDAO.getId());
        dataValues2.put("pw", userDAO.getPw());
        dataValues2.put("name", userDAO.getName());
        dataValues2.put("bir", userDAO.getBir());
        dataValues2.put("nick", userDAO.getNick());
        dataValues2.put("userimg", userDAO.getUserimg());
        dataValues2.put("friend1", "null");
        dataValues2.put("friend2", "null");
        dataValues2.put("friend3", "null");
        dataValues2.put("friend4", "null");
        dataValues2.put("room_name", room_name);

        DatabaseReference dr2 = userinfoRef2.child(userDAO.getId());
        dr2.setValue(dataValues2);


    }

    public void removed(){
        main_joinstateAdapter.removeItem();
    }

    public void CurrentListRemove(List arrayList){
        int size = arrayList.size();
        for(int i=0; i<size; i++) {
            arrayList.remove(0);
        }
    }

}
