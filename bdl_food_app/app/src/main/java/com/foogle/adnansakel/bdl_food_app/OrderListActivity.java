package com.foogle.adnansakel.bdl_food_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.foogle.adnansakel.bdl_food_app.Adapters.OrderListAdapter;
import com.foogle.adnansakel.bdl_food_app.DataModel.AppConstants;
import com.foogle.adnansakel.bdl_food_app.DataModel.OrderData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Adnan Sakel on 4/8/2016.
 */
public class OrderListActivity extends Activity implements View.OnClickListener{

    LinearLayout llSettings;
    View viewSettings;
    LinearLayout llNewsFeed;
    View viewNewsFeed;
    LinearLayout llNewPost;
    View viewNewPost;
    LinearLayout llOrders;
    View viewOrders;

    ListView lvOrderList;
    OrderListAdapter orderListAdapter;
    OrderData orderData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        initializeComponent();




    }

    private void initializeComponent(){

        llSettings = (LinearLayout)findViewById(R.id.linear_layout_settings);
        llNewsFeed = (LinearLayout)findViewById(R.id.linear_layout_news_feed);
        llNewPost = (LinearLayout)findViewById(R.id.linear_layout_new_post);
        llOrders = (LinearLayout)findViewById(R.id.llOrder);
        viewSettings = (View)findViewById(R.id.view_settings);
        viewNewsFeed = (View)findViewById(R.id.view_news_feed);
        viewNewPost = (View)findViewById(R.id.view_new_post);
        viewOrders = (View)findViewById(R.id.viewOrders);

        llSettings.setBackgroundColor(Color.parseColor("#00ffffff"));
        viewSettings.setBackgroundResource(R.drawable.settingsblack);

        llOrders.setBackgroundColor(Color.parseColor("#33ffffff"));
        viewOrders.setBackgroundResource(R.drawable.cart_white);

        llNewPost.setBackgroundColor(Color.parseColor("#00ffffff"));
        viewNewPost.setBackgroundResource(R.drawable.add_new_black);

        llNewsFeed.setBackgroundColor(Color.parseColor("#00ffffff"));
        viewNewsFeed.setBackgroundResource(R.drawable.home_black);


        viewSettings.setOnClickListener(this);
        viewNewPost.setOnClickListener(this);
        viewNewsFeed.setOnClickListener(this);

        orderListAdapter = new OrderListAdapter(this);
        lvOrderList = (ListView)findViewById(R.id.lvOrderList);
        lvOrderList.setAdapter(orderListAdapter);
        loadOrderListData();
        //orderData = new OrderData();
    }

    private void loadOrderListData(){
        Firebase.setAndroidContext(this);
        new Firebase(AppConstants.FirebaseUri+"/"+AppConstants.USERS+"/"+AppConstants.FirebaseUserkey+"/"+AppConstants.ORDER_TO)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //System.out.println("OrderTo:\n"+dataSnapshot.getValue());
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            //System.out.println(ds.getValue());
                            orderData = new OrderData();
                            orderData.setDishName(ds.child("DishName").getValue().toString());
                            orderData.setBuyorSell("Buy");//as it is OrderTo
                            orderData.setLocation(ds.child("Location").getValue().toString());
                            orderData.setPostID(ds.child("PostID").getValue().toString());
                            orderData.setPrice(ds.child("Price").getValue().toString());
                            orderData.setUserID(ds.child("UserID").getValue().toString());
                            orderData.setValidTill(ds.child("ValidTill").getValue().toString());
                            //System.out.println("PostID: " + orderData.getPostID().toString());
                            //System.out.println("UserID: "+orderData.getUserID().toString());

                            orderListAdapter.addItem(orderData);


                        }

                        new Firebase(AppConstants.FirebaseUri+"/"+AppConstants.USERS+"/"+AppConstants.FirebaseUserkey+"/"+AppConstants.ORDER_FROM)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //System.out.println("OrderFrom:\n"+dataSnapshot.getValue());
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            //System.out.println(ds.getValue());
                                            orderData = new OrderData();
                                            orderData.setDishName(ds.child("DishName").getValue().toString());
                                            orderData.setBuyorSell("Sell");//as it is OrderTo
                                            orderData.setLocation(ds.child("Location").getValue().toString());
                                            orderData.setPostID(ds.child("PostID").getValue().toString());
                                            orderData.setPrice(ds.child("Price").getValue().toString());
                                            orderData.setUserID(ds.child("UserID").getValue().toString());
                                            orderData.setValidTill(ds.child("ValidTill").getValue().toString());
                                            //System.out.println("PostID: " + orderData.getPostID().toString());
                                            //System.out.println("UserID: " + orderData.getUserID().toString());
                                            orderListAdapter.addItem(orderData);

                                        }

                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if( v == viewSettings )
        {
            startActivity(new Intent(OrderListActivity.this, SettingsMenuActivity.class));
            this.finish();
        }
        if(v == viewNewPost){
            startActivity(new Intent(OrderListActivity.this,NewPostActivity.class));
            this.finish();
        }
        if(v == viewNewsFeed){
            startActivity(new Intent(OrderListActivity.this,NewsFeedActivity.class));
            this.finish();
        }
    }
}
