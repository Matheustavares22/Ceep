package alura.com.br.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;
import alura.com.br.ceep.model.NoteDAO;

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
        if(item.getItemId() == R.id.menu_form_note_ic_save){
            EditText tittle = findViewById(R.id.form_note_tittle);
            EditText description = findViewById(R.id.form_note_description);
            Note createdNote = new Note(tittle.getText().toString(), description.getText().toString());
            new NoteDAO().insert(createdNote);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}