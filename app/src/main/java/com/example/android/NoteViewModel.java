package com.example.android;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private Noterepository noteRepository;
    private LiveData<List<Note>> notelist;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new Noterepository(application);
        notelist = noteRepository.getAllData();
    }

    public void insert(Note note) {
        noteRepository.insertData(note);
    }

    public void update(Note note) {
        noteRepository.updatedata(note);
    }

    public void delete(Note note) {
        noteRepository.deleteData(note);
    }
    public LiveData<List<Note>> getAllNotes(){
        return notelist;
    }

}
