package alura.com.br.ceep.ui.activity;

import static alura.com.br.ceep.ui.activity.NoteActivityConstants.KEY_NOTE;
import static alura.com.br.ceep.ui.activity.NoteActivityConstants.RESULT_CODE_CREATED_NOTE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;

public class FormNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_note);
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
        setResult(RESULT_CODE_CREATED_NOTE, insertResult);
    }

    private Note createNote() {
        EditText tittle = findViewById(R.id.form_note_tittle);
        EditText description = findViewById(R.id.form_note_description);
        return new Note(tittle.getText().toString(), description.getText().toString());
    }

    private boolean itsSaveNoteMenu(MenuItem item) {
        return item.getItemId() == R.id.menu_form_note_ic_save;
    }


}