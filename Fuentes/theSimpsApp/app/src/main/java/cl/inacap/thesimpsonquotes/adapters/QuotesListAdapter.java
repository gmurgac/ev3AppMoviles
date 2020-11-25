package cl.inacap.thesimpsonquotes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import cl.inacap.thesimpsonquotes.R;
import cl.inacap.thesimpsonquotes.dto.Quote;

public class QuotesListAdapter extends ArrayAdapter<Quote> {

    private List<Quote> quotes;
    private Activity contexto;
    public QuotesListAdapter(@NonNull Activity context, int resource, @NonNull List<Quote> objects) {
        super(context, resource, objects);
        this.quotes = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.contexto.getLayoutInflater();
        View fila = inflater.inflate(R.layout.list_quotes,null,true);
        TextView nombreTxt = fila.findViewById(R.id.tv_nombre_pj);
        TextView fraseTxt = fila.findViewById(R.id.tv_frase);
        ImageView imagen = fila.findViewById(R.id.iv_personaje);
        nombreTxt.setText(this.quotes.get(position).getCharacter());
        fraseTxt.setText(this.quotes.get(position).getQuote());
        Picasso.get().load(this.quotes.get(position).getImage())
                .resize(500,500)
                .into(imagen);

        return fila;
    }
}
