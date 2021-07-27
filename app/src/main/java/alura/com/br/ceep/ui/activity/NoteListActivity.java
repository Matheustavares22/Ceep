package alura.com.br.ceep.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

        RecyclerView noteList = findViewById(R.id.note_list_recyclerview);
        NoteDAO noteDAO = new NoteDAO();

        for (int i = 0; i < 1000; i++) {
            noteDAO.insert(new Note("note " + i, "hello world!"));
        }
        List<Note> allNotes = noteDAO.all();

        noteList.setAdapter(new NoteListAdapter(allNotes));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        noteList.setLayoutManager(layoutManager);
    }
}