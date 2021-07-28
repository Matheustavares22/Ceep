package alura.com.br.ceep.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolderNote> {

    private final List<Note> notes;

    public NoteListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolderNote onCreateViewHolder(ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolderNote(createdView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolderNote holder, int position) {
        Note note = notes.get(position);
        ((ViewHolderNote) holder).bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class ViewHolderNote extends RecyclerView.ViewHolder {

        private final TextView tittle;
        private final TextView description;

        public ViewHolderNote(View itemView) {
            super(itemView);
            this.tittle = itemView.findViewById(R.id.item_note_tittle);
            this.description = itemView.findViewById(R.id.item_note_description);
        }

        public void bind(Note note) {
            tittle.setText(note.getTittle());
            description.setText(note.getDescription());
        }

    }
}
