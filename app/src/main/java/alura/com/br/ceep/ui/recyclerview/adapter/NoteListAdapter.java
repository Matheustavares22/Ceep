package alura.com.br.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alura.com.br.ceep.R;
import alura.com.br.ceep.dao.Note;

public class NoteListAdapter extends RecyclerView.Adapter {

    private final List<Note> notes;
    private static int createdViews = 0;
    private static int usedBindView = 0;

    public NoteListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        Log.i("recycler view adapter", "view holder created: " + createdViews++);
        return new ViewHolderNote(createdView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Note note = notes.get(position);
        loadTextView(holder, R.id.item_note_tittle, note.getTittle(), position);
        loadTextView(holder, R.id.item_note_description, note.getDescription(), position);
    }

    private void loadTextView(RecyclerView.ViewHolder holder, int targetTextView, String data, int position) {
        TextView description = holder.itemView.findViewById(targetTextView);
        description.setText(data);
        Log.i("recycler view adapter", "binds: "+ usedBindView++ + " position " + position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class ViewHolderNote extends RecyclerView.ViewHolder {

        public ViewHolderNote(View itemView) {
            super(itemView);
        }
    }
}
