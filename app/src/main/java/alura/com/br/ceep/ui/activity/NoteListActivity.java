package alura.com.br.ceep.ui.activity;

import static alura.com.br.ceep.ui.activity.NoteActivityConstants.INVALID_POSITION;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.KEY_NOTE;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.KEY_POSITION;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.REQUEST_CODE_INSERT_NOTE;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.RESULT_CODE_CREATED_NOTE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;
import alura.com.br.ceep.model.NoteDAO;
import alura.com.br.ceep.ui.recyclerview.adapter.NoteListAdapter;


public class NoteListActivity extends AppCompatActivity {


    private NoteListAdapter adapter;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        List<Note> allNotes = getAllNotes();

        configureRecyclerView(allNotes);

        buttonInsertNote();
    }

    private void buttonInsertNote() {
        TextView buttonInsertNote = findViewById(R.id.note_list_insert_note);
        buttonInsertNote.setOnClickListener(v -> {
            goToFormNoteActivityInsertNote();
        });
    }

    private void goToFormNoteActivityInsertNote() {
        Intent startFormNoteActivity = new Intent(NoteListActivity.this, FormNoteActivity.class);
        startActivityForResult(startFormNoteActivity, REQUEST_CODE_INSERT_NOTE);
    }

    private List<Note> getAllNotes() {
        NoteDAO noteDao = new NoteDAO();
        for (int i = 0; i < 10; i++) {
            noteDao.insert(new Note("note " + i, "again, its note" + i));
        }
        return noteDao.all();
    }

    private void configureRecyclerView(List<Note> allNotes) {
        RecyclerView noteList = findViewById(R.id.note_list_recyclerview);
        configureAdapter(allNotes, noteList);
    }

    private void configureAdapter(List<Note> allNotes, RecyclerView noteList) {
        adapter = new NoteListAdapter(allNotes);
        noteList.setAdapter(adapter);
        adapter.setOnItemClickListener(this::goToFormNoteActivityChangeNote);
    }

    private void goToFormNoteActivityChangeNote(Note note, Integer position) {
        Intent openFormNote = new Intent(NoteListActivity.this, FormNoteActivity.class);
        openFormNote.putExtra(KEY_NOTE, note);
        openFormNote.putExtra(KEY_POSITION, position);
        startActivityForResult(openFormNote, RESULT_CODE_CREATED_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCreatingNote(requestCode, resultCode, data)) {
            assert data != null;
            Note note = (Note) data.getSerializableExtra(KEY_NOTE);
            addNote(note);
        }

        if (resultEditingNote(requestCode, resultCode, data)) {
            assert data != null;
            Note receivedNote = (Note) data.getSerializableExtra(KEY_NOTE);
            Integer receivedPosition = data.getIntExtra(KEY_POSITION, INVALID_POSITION);
            if(isaValidPosition(receivedPosition)) {
                changeNote(receivedNote, receivedPosition);
                Toast.makeText(this, R.string.save_note_msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.save_note_error_msg, Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeNote(Note note, Integer position) {
        new NoteDAO().change(position, note);
        adapter.change(position, note);
    }

    private boolean isaValidPosition(Integer receivedPosition) {
        return receivedPosition > INVALID_POSITION;
    }

    private boolean resultEditingNote(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != NoteActivityConstants.RESULT_CODE_CREATED_NOTE || resultCode != RESULT_CODE_CREATED_NOTE)
            return false;
        assert data != null;
        return data.hasExtra(KEY_NOTE);
    }

    private boolean resultCreatingNote(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != NoteActivityConstants.REQUEST_CODE_INSERT_NOTE || resultCode != RESULT_CODE_CREATED_NOTE)
            return false;
        assert data != null;
        return data.hasExtra(KEY_NOTE);
    }

    private void addNote(Note note) {
        new NoteDAO().insert(note);
        adapter.add(note);
    }

}