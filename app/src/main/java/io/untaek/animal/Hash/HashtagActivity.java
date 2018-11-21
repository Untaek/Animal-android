package io.untaek.animal.Hash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.untaek.animal.R;
import io.untaek.animal.firebase.HashTagDetail;

public class HashtagActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag_profile);

        EditText txtSearch = (EditText) findViewById(R.id.txtSearch);
        ListView listSearch = (ListView) findViewById(R.id.listSearch);

        Intent intent = new Intent(this.getIntent());
        HashTagDetail hashes = (HashTagDetail) intent.getSerializableExtra("data");

        txtSearch.setText(hashes.getTags());

        for (int i = 0; i < hashes.getCounts(); i++) {
            //listSearch.Items.add
        }

    }

    private void doSearch() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("message");

        myRef.setValue("Hello, world");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<HashTagListItem> listItems() {
        //아이템 뽑아서 리스트화
        return null;
    }


}
