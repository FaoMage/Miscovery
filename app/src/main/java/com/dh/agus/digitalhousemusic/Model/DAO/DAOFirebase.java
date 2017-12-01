package com.dh.agus.digitalhousemusic.Model.DAO;

import com.dh.agus.digitalhousemusic.Model.POJO.Album;
import com.dh.agus.digitalhousemusic.Model.POJO.Track;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by ZetaxMage on 27/11/2017.
 */

public class DAOFirebase {

    public void getFavoriteTracks (final ResultListener<List<Track>> resultListener) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("favoritos");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Track> tracks = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tracks.add(snapshot.getValue(Track.class));
                }
                resultListener.finish(tracks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addFavorite (Track track) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("favoritos");
        reference.child(track.getId()).setValue(track);
    }

    public void removeFavorite (Track track) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("favoritos");
        reference.child(track.getId()).removeValue();
    }

    public void getRecentAlbums (final ResultListener<List<Album>> resultListener) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("recientes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Album> albumList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    albumList.add(snapshot.getValue(Album.class));
                }
                resultListener.finish(albumList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addRecentAlbum (Album album) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("recientes");
        reference.child(album.getId()).setValue(album);
    }

    // todo - esta funcion no fue testeada
    public void removeRecentAlbum (String id) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("recientes");
        reference.child(id).removeValue();
    }

    public void getRecentsQueue (final ResultListener<Queue<String>> resultListener) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("recents_queue");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Queue<String> queue = new LinkedList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    queue.offer(snapshot.getValue(String.class));
                }
                resultListener.finish(queue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateRecentsQueue (Queue<String> queue) {
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(uId).child("recents_queue");
        reference.setValue(queue);
    }
}
