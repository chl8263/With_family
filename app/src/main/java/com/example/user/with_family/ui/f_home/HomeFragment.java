package com.example.user.with_family.ui.f_home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    DatabaseReference userinfoRef;                  // 접속 유저 정보 가져오는 레퍼런스
    DatabaseReference userinfoRef2;                  // 접속 유저가 친구 추가할때 쓰는 레퍼런스

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;


    private FloatingActionButton floatingActionButton;
    private RecyclerView user_recyclerView ;
    private String login_user;
    Main_JoinStateAdapter main_joinstateAdapter;
    UserDAO dao;
    UserDAO friendDao;
    private static List<UserDAO> userDAOList = new ArrayList<>();      // 유저 정보들 저장해놓을 리스트

    public HomeFragment(){
        databaseRef = FirebaseDatabase.getInstance();
        userinfoRef = databaseRef.getReference().child("register").child("user");
        userinfoRef2 = databaseRef.getReference().child("register").child("user");
        mFirebaseStorage = FirebaseStorage.getInstance();
        //mStorageReference = mFirebaseStorage.getReferenceFromUrl("gs://ahntanwithfamily.appspot.com/a/a.png");



        mhandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                int position = bundle.getInt("position");
                updateDao(dao, position, "null");
            }
        };
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
            else
                friend_id += data.getStringExtra("Result");


            System.out.println("으앙 친구 : " + friend_id);
            System.out.println("으앙 친구2 : " + userDAOList.size());
            System.out.println("으앙 친구2 : " + userDAOList.get(0).getId());
            System.out.println("으앙 친구2 : " + userDAOList.get(1).getId());
            System.out.println("으앙 친구2 : " + userDAOList.get(2).getId());
            //Toast.makeText(getContext(), "으앙 : "+ friend_id, Toast.LENGTH_LONG).show();


            for(int i=0; i<userDAOList.size(); i++){
                // 친구 추가 버튼과 일치하는 값이 있다면 추가
                if(userDAOList.get(i).getId().equals(friend_id)){

                    Map<String, Object> dataValues = new HashMap<>();

                    if(dao.getFriend1().equals("null")){
                        DatabaseReference dr = userinfoRef2.child(dao.getId());
                        updateDao(dao, 1, userDAOList.get(i).getId());
                    }
                    else if(dao.getFriend2().equals("null")){
                        DatabaseReference dr = userinfoRef2.child(dao.getId());
                        updateDao(dao, 2, userDAOList.get(i).getId());
                    }
                    else if(dao.getFriend3().equals("null")){
                        DatabaseReference dr = userinfoRef2.child(dao.getId());
                        updateDao(dao, 3, userDAOList.get(i).getId());
                    }
                    else if(dao.getFriend4().equals("null")){
                        DatabaseReference dr = userinfoRef2.child(dao.getId());
                        updateDao(dao, 4, userDAOList.get(i).getId());
                    }
                    //onLoadData(friend_id);
                    break;
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

        sharedPreferences = this.getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        login_user = sharedPreferences.getString("myid", "01012345678");


        userinfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserDAO dao2 = snapshot.getValue(UserDAO.class);
                    userDAOList.add(dao2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // 파이어베이스 로그인 유저데이터(나와 친구들)를 가져와서 저장하는 부분
        //userinfoRef.child(login_user).child("friend1").addValueEventListener(new ValueEventListener() {
        userinfoRef.child(login_user).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                dao = dataSnapshot.getValue(UserDAO.class);
                //mStorageReference = mFirebaseStorage.getReferenceFromUrl(dao.getUserimg());
                mStorageReference = mFirebaseStorage.getReferenceFromUrl(dao.getUserimg());
                //mStorageReference = mFirebaseStorage.getReferenceFromUrl("gs://ahntanwithfamily.appspot.com/821041199582/1");

                System.out.println("이미지URL : " + dao.getUserimg());
                write();
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
        System.out.println("이미지URL22 : " + dao.getUserimg());
        removed();
        for(int i=0; i<=4; i++){
            switch (i) {
                // 0은 나를 추가하는 부분
                case 0:
                    main_joinstateAdapter.addItem(new Contacts(mStorageReference.toString(), R.drawable.day_background, dao.getName(), dao.getNick(), dao.getId())); // 이미지 url, 이름 name
                    break;
                // 1부터는 친구추가 되있는 부분을
                case 1:
                    if (!dao.getFriend1().equals("null")) {
                        userinfoRef.child(dao.getFriend1().toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                friendDao = dataSnapshot.getValue(UserDAO.class);
                                //main_joinstateAdapter.addItem(new Contacts(R.drawable.user_img, R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));
                                main_joinstateAdapter.addItem(new Contacts(friendDao.getUserimg(), R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));

                                //write2(friendDao);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                     break;
                case 2 :
                    if (!dao.getFriend2().equals("null")) {
                        userinfoRef.child(dao.getFriend2().toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                friendDao = dataSnapshot.getValue(UserDAO.class);
                                //main_joinstateAdapter.addItem(new Contacts(R.drawable.user_img, R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));
                                main_joinstateAdapter.addItem(new Contacts(friendDao.getUserimg(), R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));

                                //write2(friendDao);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    break;
                case 3 :
                    if (!dao.getFriend3().equals("null")) {
                        userinfoRef.child(dao.getFriend3().toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                friendDao = dataSnapshot.getValue(UserDAO.class);
                                //main_joinstateAdapter.addItem(new Contacts(R.drawable.user_img, R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));
                                main_joinstateAdapter.addItem(new Contacts(friendDao.getUserimg(), R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));
                                //write2(friendDao);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    break;
                case 4 :
                    if (!dao.getFriend4().equals("null")) {
                        userinfoRef.child(dao.getFriend4().toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                friendDao = dataSnapshot.getValue(UserDAO.class);
                                //main_joinstateAdapter.addItem(new Contacts(R.drawable.user_img, R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));
                                main_joinstateAdapter.addItem(new Contacts(friendDao.getUserimg(), R.drawable.day_background, friendDao.getName(), friendDao.getNick(), friendDao.getId()));

                                //write2(friendDao);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    break;
                default:
                        break;
            }
        }


    }


    public void updateDao(UserDAO currentDAO, int num, String addfriend){
        Map<String, Object> dataValues = new HashMap<>();

        dataValues.put("id", currentDAO.getId());
        dataValues.put("pw", currentDAO.getPw());
        dataValues.put("name", currentDAO.getName());
        dataValues.put("bir", currentDAO.getBir());
        dataValues.put("nick", currentDAO.getNick());
        dataValues.put("userimg", currentDAO.getUserimg());
        switch (num){
            case 1 :
                dataValues.put("friend1", addfriend);
                dataValues.put("friend2", currentDAO.getFriend2());
                dataValues.put("friend3", currentDAO.getFriend3());
                dataValues.put("friend4", currentDAO.getFriend4());
                break;
            case 2:
                dataValues.put("friend1", currentDAO.getFriend1());
                dataValues.put("friend2", addfriend);
                dataValues.put("friend3", currentDAO.getFriend3());
                dataValues.put("friend4", currentDAO.getFriend4());
                break;
            case 3:
                dataValues.put("friend1", currentDAO.getFriend1());
                dataValues.put("friend2", currentDAO.getFriend2());
                dataValues.put("friend3", addfriend);
                dataValues.put("friend4", currentDAO.getFriend4());
                break;
            case 4:
                dataValues.put("friend1", currentDAO.getFriend1());
                dataValues.put("friend2", currentDAO.getFriend2());
                dataValues.put("friend3", currentDAO.getFriend3());
                dataValues.put("friend4", addfriend);
                break;
                default:
                    dataValues.put("friend1", currentDAO.getFriend1());
                    dataValues.put("friend2", currentDAO.getFriend2());
                    dataValues.put("friend3", currentDAO.getFriend3());
                    dataValues.put("friend4", currentDAO.getFriend4());
                    break;
        }

        //Ref2는 없어도 됨
        DatabaseReference dr = userinfoRef2.child(dao.getId());
        dr.setValue(dataValues);
    }

    public void removed(){
        main_joinstateAdapter.removeItem();
    }

}
