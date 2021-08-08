package alura.com.br.ceep.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;
import alura.com.br.ceep.ui.recyclerview.adapter.listener.OnItemClickListener;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolderNote> {

    private final List<Note> notes;
    private static OnItemClickListener onItemClickListener;

    public NoteListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public static void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        NoteListAdapter.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolderNote onCreateViewHolder(ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_note, parent, false);
        return new ViewHolderNote(createdView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolderNote holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void change(Integer position, Note note) {
        this.notes.set(position, note);
        notifyDataSetChanged();
    }

    static class ViewHolderNote extends RecyclerView.ViewHolder {

        private final TextView tittle;
        private final TextView description;
        private Note note;

        public ViewHolderNote(View itemView) {
            super(itemView);
            this.tittle = itemView.findViewById(R.id.item_note_tittle);
            this.description = itemView.findViewById(R.id.item_note_description);
            itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(note, getAbsoluteAdapterPosition());
            });
        }

        public void bind(Note note) {
            this.note = note;
            fillFields(note);
        }

        private void fillFields(Note note) {
            tittle.setText(note.getTittle());
            description.setText(note.getDescription());
        }
    }

    public void add(Note note) {
        this.notes.add(note);
        notifyDataSetChanged();
    }
}
