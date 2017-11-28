package com.dh.agus.digitalhousemusic.Model.DAO;

import com.dh.agus.digitalhousemusic.Model.POJO.FavoriteTrack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZetaxMage on 27/11/2017.
 */

public class DAOFirebase {

    public void getFavoriteTracks (final ResultListener<List<FavoriteTrack>> resultListener) {
        // trae user id
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // trae la reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("favoritos");
        // trae la data
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<FavoriteTrack> favoriteTracks = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    favoriteTracks.add(snapshot.getValue(FavoriteTrack.class));
                }
                resultListener.finish(favoriteTracks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
