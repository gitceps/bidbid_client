package com.aze51.bidbid_client.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aze51.bidbid_client.ApplicationController;
import com.aze51.bidbid_client.MainActivity;
import com.aze51.bidbid_client.Network.Auction;
import com.aze51.bidbid_client.Network.Content;
import com.aze51.bidbid_client.Network.NetworkService;
import com.aze51.bidbid_client.Network.Product;
import com.aze51.bidbid_client.R;
import com.aze51.bidbid_client.SharingActivity;
import com.bumptech.glide.Glide;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jeon3029 on 16. 7. 2..
 */
public class DetailItemFragment extends Fragment {
    View rootViewBasic;
    //ImageView backimage;
    TextView detail_price;
    TextView detail_time;
    TextView detail_title;
    Button detail_bid;
    TextView detail_bidPrice;
    String get_img;
    ImageView detail_img;
    List<Product> products;

    //facebookshare
    ImageView shareImage;
    Bitmap image;

    int position;
    String tmpMessage;
    NetworkService networkService;
    Auction auction;
    //static String user_id = "lkh034";
    Product tmpProduct;
    public DetailItemFragment(){
        //생성자
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        initNetworkService();
        int pos = ApplicationController.getInstance().getPosition();
        rootViewBasic = inflater.inflate(R.layout.detail_item_cardview, container,false);
        products = ApplicationController.getInstance().getProducts(pos);
        position = ApplicationController.getInstance().getPos();
        initView();
        //detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        //detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        //detail_price.setText("120000");
        String tmpName = products.get(position).product_name;
        int tmpPrice = products.get(position).register_minprice;
        String tmpImg = products.get(position).product_img;
        final int tmpRegisterId = products.get(position).register_id; //
        /*getDetailContent(tmpRegisterId);
        String detailName = tmpProduct.product_name;
        String detailImg = tmpProduct.product_img;
        int detailPrice = tmpProduct.register_minprice;*/
        //String tmpPrice = (products.get(position).register_minprice);
        detail_title.setText(tmpName);
        detail_price.setText(Integer.toString(tmpPrice));
        //detail_bidPrice.setText(detail_bidPrice.getText());
        //detail_time.setText("3:45");
        Glide.with(this).load(tmpImg).into(detail_img);
        getDetailContent(position);

       /* detail_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //detail_bidPrice.setText(detail_bidPrice.getText());
                auction = new Auction();
                auction.deal_price = Integer.parseInt(detail_bidPrice.getText().toString());
                auction.register_id = tmpRegisterId;
                auction.user_id = ApplicationController.getUserId();
                ApplicationController.setAuction(auction);
                //postBidResult(auction);
            }
        });*/
        image = BitmapFactory.decodeResource(getResources(),R.drawable.food);
        shareImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("TAG","share clicked");
                /*
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                        .putString("og:type", "fitness.course")
                        .putString("og:title", "Sample Course")
                        .putString("og:description", "This is a sample course.")
                        .putInt("fitness:duration:value", 100)
                        .putString("fitness:duration:units", "s")
                        .putInt("fitness:distance:value", 12)
                        .putString("fitness:distance:units", "km")
                        .putInt("fitness:speed:value", 5)
                        .putString("fitness:speed:units", "m/s")
                        .build();
                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("fitness.runs")
                        .putObject("fitness:course", object)
                        .build();
                ShareOpenGraphContent content2 = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("fitness:course")
                        .setAction(action)
                        .build();*/
                /*
                ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                        .putString("og:type", "books.book")
                        .putString("og:title", "Bid Bid")
                        .putString("og:description", "경매형 마케팅 플랫폼")
                        .putString("books:isbn", "0-553-57340-3")
                        .build();
                ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                        .setActionType("books.reads")
                        .putObject("book", object)
                        .build();
                // Create the content
                ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                        .setPreviewPropertyName("book")
                        .setAction(action)
                        .build();
                //ShareApi.share(content, null);
                //Context ctx = ApplicationController.getInstance().getMainActivityContext()
                ShareDialog.show(getActivity(), content);*/
                Context ctx = ApplicationController.getInstance().getMainActivityContext();
                Intent intent =new Intent(ctx, SharingActivity.class);
                startActivity(intent);
            }
        });
        return rootViewBasic;
    }

    protected void initView(){
        detail_price = (TextView)rootViewBasic.findViewById(R.id.detail_price);
        detail_time = (TextView)rootViewBasic.findViewById(R.id.detail_time);
        detail_title = (TextView)rootViewBasic.findViewById(R.id.detail_item_name);
        detail_img = (ImageView)rootViewBasic.findViewById(R.id.detail_ImageView);
        detail_bid = (Button)rootViewBasic.findViewById(R.id.bidbtn);
        detail_bidPrice = (TextView)rootViewBasic.findViewById(R.id.inputPrice);

        shareImage = (ImageView)rootViewBasic.findViewById(R.id.detail_share_image);

    }
    public void postBidResult(Auction auction){
        Call<Auction> auctionCall = networkService.finishbid(auction);
        auctionCall.enqueue(new Callback<Auction>() {
            @Override
            public void onResponse(Response<Auction> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    tmpMessage = "입찰 성공";
                    Toast.makeText(getContext(), tmpMessage, Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    } // BottomMenuFragment

    private void initNetworkService() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    public void getDetailContent(int id){
        Call<Product> callProduct = networkService.getContent(id);
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Response<Product> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    tmpProduct = new Product();
                    tmpProduct = response.body();
                    detail_title.setText(tmpProduct.product_name);
                    Glide.with(getContext()).load(tmpProduct.product_img).into(detail_img);
                    detail_price.setText(tmpProduct.register_minprice);

                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    } // DetailItemFragment
}
