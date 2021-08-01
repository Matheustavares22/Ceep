package alura.com.br.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;
import alura.com.br.ceep.model.NoteDAO;
import alura.com.br.ceep.ui.recyclerview.adapter.NoteListAdapter;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        List<Note> allNotes = sampleNotes();

        configureRecyclerView(allNotes);
    }

    private List<Note> sampleNotes() {
        NoteDAO noteDAO = new NoteDAO();

        noteDAO.insert(new Note("first note", "tiny description"), new Note("second note", "a very big description huhhhhhhhhhhhh"));

        TextView buttonInsertNote = findViewById(R.id.note_list_insert_note);
        buttonInsertNote.setOnClickListener(v -> {
            Intent startFormNoteActivity = new Intent(NoteListActivity.this, FormNoteActivity.class);
            startActivity(startFormNoteActivity);
        });



        return noteDAO.all();
    }

    private void configureRecyclerView(List<Note> allNotes) {
        RecyclerView noteList = findViewById(R.id.note_list_recyclerview);
        configureAdapter(allNotes, noteList);
    }

    private void configureAdapter(List<Note> allNotes, RecyclerView noteList) {
        noteList.setAdapter(new NoteListAdapter(allNotes));
    }

    @Override
    protected void onResume() {
        NoteDAO noteDAO = new NoteDAO();
        List<Note> allNotes = noteDAO.all();
        configureRecyclerView(allNotes);
        super.onResume();
    }
}