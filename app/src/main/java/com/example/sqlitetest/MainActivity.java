package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dblibrary.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        ArrayList<Integer> ids = dbHelper.getAllEvents();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        idsAdapter adapter = new idsAdapter(ids, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(), DisplayEvents.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

    public class idsAdapter extends RecyclerView.Adapter<idsAdapter.ViewHolder> {
        private Context context;
        ArrayList<Pair<Integer, String>> mStrings;
        public idsAdapter(ArrayList<Integer> ids, Context context) {
            this.context = context;
            mStrings = new ArrayList<>();
            for(Integer i:ids) {
                Cursor rs = dbHelper.getData(i);
                rs.moveToFirst();
                int eventTimeIndex = rs.getColumnIndex(DBHelper.EVENTS_COLUMN_EVENTTIME);
                if(eventTimeIndex >= 0) {
                    mStrings.add(new Pair<>(i, rs.getString(eventTimeIndex)));
                }
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(listItem);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Pair<Integer, String> pair = mStrings.get(holder.getAdapterPosition());
            holder.textView.setText(pair.second);
            //holder.imageView.setImageResource(listData.get(position).getImgId());
            holder.iv_edit.setOnClickListener(view -> editAt(holder.getAdapterPosition()));
            holder.iv_delete.setOnClickListener(view -> {
                if(mStrings.get(holder.getAdapterPosition()) != null) {
                    removeAt(holder.getAdapterPosition());
                }
            });
            holder.setId(pair.first);
        }

        @Override
        public int getItemCount() {
            return mStrings.size();
        }

        public Pair<Integer,String> getPair(int position) {
            return mStrings.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public int id;
            public ImageView iv_edit, iv_delete;
            public TextView textView;
            public CardView cardView;
            public FrameLayout frameLayout;
            public ViewHolder(View itemView) {
                super(itemView);
                this.iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
                this.iv_edit = (ImageView) itemView.findViewById(R.id.iv_edit);
                this.textView = (TextView) itemView.findViewById(R.id.textView);
                cardView = (CardView)itemView.findViewById(R.id.cardView);
                frameLayout = (FrameLayout)itemView.findViewById(R.id.frameLayoutInner);
                frameLayout.setOnClickListener(v -> {
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id);
                    Intent intent = new Intent(getApplicationContext(), DisplayEvents.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                });
            }
            public void setId(int currentId) {
                id = currentId;
            }
        }

        public void editAt(int position) {
            Bundle dataBundle = new Bundle();
            dataBundle.putBoolean("isEditable", true);
            dataBundle.putInt("id", mStrings.get(position).first);
            Intent intent = new Intent(getApplicationContext(), DisplayEvents.class);
            intent.putExtras(dataBundle);
            startActivity(intent);

        }
        public void removeAt(int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.deleteContact)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbHelper.deleteEvents(mStrings.get(position).first);
                            mStrings.remove(position);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                    Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mStrings.size());
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            AlertDialog d = builder.create();
            d.setTitle("Delete");
            d.show();
        }
    }

}



