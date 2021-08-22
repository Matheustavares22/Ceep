package alura.com.br.ceep.ui.activity;

import static alura.com.br.ceep.ui.activity.NoteActivityConstants.CODE_CHANGE_NOTE;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.CODE_INSERT_NOTE;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.INVALID_POSITION;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.KEY_NOTE;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.KEY_POSITION;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;
import alura.com.br.ceep.model.NoteDAO;
import alura.com.br.ceep.ui.recyclerview.adapter.NoteListAdapter;
import alura.com.br.ceep.ui.recyclerview.helper.callback.NoteItemTouchHelperCallBack;


public class NoteListActivity extends AppCompatActivity {


    private NoteListAdapter adapter;

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

    private void configureAdapter(List<Note> allNotes, RecyclerView noteList) {
        adapter = new NoteListAdapter(allNotes);
        noteList.setAdapter(adapter);
        adapter.setOnItemClickListener(this::goToFormNoteActivityChangeNote);
    }

    private void goToFormNoteActivityInsertNote() {
        Intent startFormNoteActivity = new Intent(NoteListActivity.this, FormNoteActivity.class);
        startActivityForResult(startFormNoteActivity, CODE_INSERT_NOTE);
    }

    private void goToFormNoteActivityChangeNote(Note note, Integer position) {
        Intent startFormNoteActivity = new Intent(NoteListActivity.this, FormNoteActivity.class);
        startFormNoteActivity.putExtra(KEY_NOTE, note);
        startFormNoteActivity.putExtra(KEY_POSITION, position);
        startActivityForResult(startFormNoteActivity, CODE_CHANGE_NOTE);
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
        configureItemTouchHelper(noteList);
    }

    private void configureItemTouchHelper(RecyclerView noteList) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NoteItemTouchHelperCallBack(adapter));
        itemTouchHelper.attachToRecyclerView(noteList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (isaResultCreatingNote(requestCode)) {
            if(isResultOk(resultCode)) {
                Note note = (Note) data.getSerializableExtra(KEY_NOTE);
                addNote(note);
                toastMessage(R.string.save_note_msg);
            }
        }

        if (isaResultChangeNote(requestCode, data)) {
            if(isResultOk(resultCode)) {
                Note receivedNote = (Note) data.getSerializableExtra(KEY_NOTE);
                Integer receivedPosition = data.getIntExtra(KEY_POSITION, INVALID_POSITION);

                if (isaValidPosition(receivedPosition)) {
                    changeNote(receivedNote, receivedPosition);
                    toastMessage(R.string.save_note_msg);
                } else {
                    toastMessage(R.string.save_note_error_msg);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void toastMessage(int messageText) {
        Toast.makeText(this, messageText, Toast.LENGTH_SHORT).show();
    }

    private boolean isaResultChangeNote(int requestCode, Intent data) {
        return isaCodeChangeNote(requestCode) && hasNote(data);
    }

    private boolean isaResultCreatingNote(int requestCode) {
        return isaCodeInsertNote(requestCode);
    }

    private boolean isResultOk(int resultCode) {
        return resultCode == RESULT_OK;
    }

    private boolean isaCodeChangeNote(int code) {
        return code == CODE_CHANGE_NOTE;
    }

    private boolean isaValidPosition(Integer receivedPosition) {
        return receivedPosition > INVALID_POSITION;
    }

    private boolean isaCodeInsertNote(int requestCode) {
        return requestCode == CODE_INSERT_NOTE;
    }

    private boolean hasNote(Intent data) {
        return data.hasExtra(KEY_NOTE);
    }

    private void changeNote(Note note, Integer position) {
        new NoteDAO().change(position, note);
        adapter.change(position, note);
    }

    private void addNote(Note note) {
        new NoteDAO().insert(note);
        adapter.add(note);
    }

}