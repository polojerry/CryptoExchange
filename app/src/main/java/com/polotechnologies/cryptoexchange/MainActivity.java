package com.polotechnologies.cryptoexchange;

/**
 * Created by Polo on 04/11/2017.
 */

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String URL_PRICE="https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=EUR,USD,JPY,GBP,AUD,CAD,CHF,CNY,MXN,SEK,NZD,SGD,HKD,KES,KRW,TRY,INR,RUB,ZAR,NGN&extraParams=CryptExchange";
    private String curren []={"EUR","USD","JPY","GBP","AUD","CAD","CHF","CNY","MXN","SEK","NZD","SGD","HKD","KES","KRW","TRY","INR","RUB","ZAR","NGN"};
    private int images[ ]={R.drawable.eur,R.drawable.usd,R.drawable.jpy,R.drawable.gbp,R.drawable.aus,R.drawable.can,R.drawable.chf,R.drawable.cny,R.drawable.mxp,R.drawable.sdk,R.drawable.nzd,R.drawable.sgd,R.drawable.hkd,R.drawable.ksh,R.drawable.krw,R.drawable.tur,R.drawable.inr,R.drawable.rub,R.drawable.nar,R.drawable.ngn};

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<PriceItem> priceItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        priceItems = new ArrayList<>();

        loadRecyclerPrice();
    }

    public void loadRecyclerPrice(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Prices...");
        progressDialog.show();

        final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_PRICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject cryptoCurrencyBTC =(JSONObject) jsonObject.get("BTC");
                            JSONObject cryptoCurrencyETH = (JSONObject) jsonObject.get("ETH");

                            for (int i = 0; i < curren.length; i++) {
                                String pricee = curren[i];
                                PriceItem price = new PriceItem(
                                        cryptoCurrencyBTC.getDouble(pricee),
                                        cryptoCurrencyETH.getDouble(pricee),
                                        images[i]
                                );
                                priceItems.add(price);
                            }


                            adapter = new Adapter (priceItems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
