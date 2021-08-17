package alura.com.br.ceep.ui.activity;

import static alura.com.br.ceep.ui.activity.NoteActivityConstants.INVALID_POSITION;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.KEY_NOTE;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.KEY_POSITION;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.CODE_CHANGE_NOTE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;

public class FormNoteActivity extends AppCompatActivity {

    private Integer receivedPosition = INVALID_POSITION;
    private TextView tittle;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_note);

        initiateFields();

        Intent receivedData = getIntent();

        if (receivedData.hasExtra(KEY_NOTE)) {
            receivedPosition = receivedData.getIntExtra(KEY_POSITION, INVALID_POSITION);
            Note receivedNote = (Note) receivedData.getSerializableExtra(KEY_NOTE);

            fillFields(receivedNote);
        }
    }

    private void initiateFields() {
        tittle = findViewById(R.id.form_note_tittle);
        description = findViewById(R.id.form_note_description);
    }

    private void fillFields(Note receivedNote) {
        tittle.setText(receivedNote.getTittle());
        description.setText(receivedNote.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_note_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (itsSaveNoteMenu(item)) {
            Note createdNote = createNote();
            returnNote(createdNote);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnNote(Note note) {
        Intent insertResult = new Intent();
        insertResult.putExtra(KEY_NOTE, note);
        insertResult.putExtra(KEY_POSITION, receivedPosition);
        setResult(CODE_CHANGE_NOTE, insertResult);
    }

    private Note createNote() {
        return new Note(tittle.getText().toString(), description.getText().toString());
    }

    private boolean itsSaveNoteMenu(MenuItem item) {
        return item.getItemId() == R.id.menu_form_note_ic_save;
    }
}