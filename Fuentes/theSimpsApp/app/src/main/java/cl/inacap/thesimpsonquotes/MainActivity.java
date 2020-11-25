package cl.inacap.thesimpsonquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.inacap.thesimpsonquotes.adapters.QuotesListAdapter;
import cl.inacap.thesimpsonquotes.dto.Quote;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private List<Quote> quotes = new ArrayList<>();
    private QuotesListAdapter adapterLv;
    private ListView listViewQuotes;
    private Spinner spCantidadQuotes;
    private Button solicitarQtsBtn;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.spCantidadQuotes = findViewById(R.id.sp_cantidad_quotes);
        String[] listadoSpiner = new String[10];
        for (int i = 1; i < 11; i++) {
            listadoSpiner[i-1] = "" + i;
        }
        ArrayAdapter<String> adapterSpiner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listadoSpiner);
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCantidadQuotes.setAdapter(adapterSpiner);

        this.listViewQuotes = findViewById(R.id.lv_quotes);
        this.adapterLv = new QuotesListAdapter(this, R.layout.list_quotes, this.quotes);
        this.listViewQuotes.setAdapter(this.adapterLv);


        this.solicitarQtsBtn = findViewById(R.id.btn_solicitar);
        this.solicitarQtsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                queue = Volley.newRequestQueue(MainActivity.this);
                String cantidadQuotes = spCantidadQuotes.getSelectedItem().toString().trim();
                JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET
                        , "https://thesimpsonsquoteapi.glitch.me/quotes?count="+cantidadQuotes
                        ,null
                        , new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            quotes.clear();
                            Quote[] quoteObt = new Gson().fromJson(response.toString(), Quote[].class);
                            quotes.addAll(Arrays.asList(quoteObt));
                        } catch (Exception e) {
                            quotes = null;
                        } finally {
                            adapterLv.notifyDataSetChanged();
                        }

                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        quotes = null;
                        adapterLv.notifyDataSetChanged();
                    }

                });
                queue.add(jsonReq);

            }

        });

    }
}