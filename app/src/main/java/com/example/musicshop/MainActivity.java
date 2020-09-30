package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int quantity = 0;
    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter;
    HashMap goodsMap;
    String goodsName;
    Double price;
    EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.nameEditText);

        createSpinner();

        createMap();

    }

    void createSpinner()
    {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerArrayList = new ArrayList();

        spinnerArrayList.add("guitar");
        spinnerArrayList.add("drums");
        spinnerArrayList.add("keyboard");

        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    void createMap()
    {
        goodsMap = new HashMap();
        goodsMap.put("guitar", 500.0);
        goodsMap.put("drums", 1500.0);
        goodsMap.put("keyboard", 1000.0);
    }

    public void increaseQuantity(View view) {
        quantity = quantity + 1;
        TextView quantityTextView = findViewById(R.id.quantityCountTextView);
        quantityTextView.setText("" + quantity);
        TextView priceCountTextView = findViewById(R.id.priceCountTextView);
        priceCountTextView.setText(""+ quantity * price + "$");
    }

    public void decreaseQuantity(View view) {
        quantity = quantity - 1;
        if (quantity < 0){
            quantity = 0;
        }
        TextView quantityTextView = findViewById(R.id.quantityCountTextView);
        quantityTextView.setText("" + quantity);
        TextView priceCountTextView = findViewById(R.id.priceCountTextView);
        priceCountTextView.setText(""+ quantity * price + "$");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = (double)goodsMap.get(goodsName);
        TextView priceCountTextView = findViewById(R.id.priceCountTextView);
        priceCountTextView.setText(""+ quantity * price + "$");

        ImageView goodsImageView = findViewById(R.id.goodImageView);

        int[] sizesForPicture;
        switch (goodsName){

            case "guitar":
                goodsImageView.setImageResource(R.drawable.guitar);
                 sizesForPicture = changePictureSizeInDP(100,300);
                goodsImageView.getLayoutParams().width = sizesForPicture[0];
                goodsImageView.getLayoutParams().height = sizesForPicture[1];
                goodsImageView.requestLayout();

                break;
            case "drums":
                goodsImageView.setImageResource(R.drawable.drums);
                sizesForPicture = changePictureSizeInDP(300,300);
                goodsImageView.getLayoutParams().width = sizesForPicture[0];
                goodsImageView.getLayoutParams().height = sizesForPicture[1];
                goodsImageView.requestLayout();
                break;
            case "keyboard":
                goodsImageView.setImageResource(R.drawable.keyboard);
                sizesForPicture = changePictureSizeInDP(340,340);
                goodsImageView.getLayoutParams().width = sizesForPicture[0];
                goodsImageView.getLayoutParams().height = sizesForPicture[1];
                goodsImageView.requestLayout();
                break;
            default:
                goodsImageView.setImageResource(R.drawable.guitar);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCart(View view) {

        Order order = new Order();

        order.userName = userNameEditText.getText().toString();
        Log.d("print userName", order.userName);

        order.goodsName = goodsName;
        Log.d("print goodsName", order.goodsName);

        order.quantity = quantity;
        Log.d("print quantity", "" + order.quantity);

        order.price = price;

        order.orderPrice = quantity * price;
        Log.d("print orderPrice", "" + order.orderPrice);

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userNameForIntent", order.userName);
        orderIntent.putExtra("goodsName", order.goodsName);
        orderIntent.putExtra("quantity", order.quantity);
        orderIntent.putExtra("price", order.price);
        orderIntent.putExtra("orderPrice", order.orderPrice);

        startActivity(orderIntent);

    }

    public int[] changePictureSizeInDP(int dimensionInPixelForWidth, int dimensionInPixelForHeight){

        int dimensionInDpForWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixelForWidth, getResources().getDisplayMetrics());
        int dimensionInDpForHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixelForHeight, getResources().getDisplayMetrics());

        return new int[] {dimensionInDpForWidth, dimensionInDpForHeight};
    }
}