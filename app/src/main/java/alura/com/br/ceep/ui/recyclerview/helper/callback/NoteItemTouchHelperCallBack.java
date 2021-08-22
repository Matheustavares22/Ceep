package alura.com.br.ceep.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import alura.com.br.ceep.model.NoteDAO;
import alura.com.br.ceep.ui.recyclerview.adapter.NoteListAdapter;

public class NoteItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private final NoteListAdapter adapter;
    private NoteDAO noteDAO;

    public NoteItemTouchHelperCallBack(NoteListAdapter adapter) {
        this.adapter = adapter;
        noteDAO = new NoteDAO();
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeDragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        return makeMovementFlags(swipeDragFlags, swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int initialPosition = viewHolder.getBindingAdapterPosition();
        int endPosition = target.getBindingAdapterPosition();
        tradeNotes(initialPosition, endPosition);
        return true;
    }

    private void tradeNotes(int initialPosition, int endPosition) {
        noteDAO.trade(initialPosition, endPosition);
        adapter.trade(initialPosition, endPosition);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getBindingAdapterPosition();
        removeNote(position);
    }

    private void removeNote(int position) {
        noteDAO.remove(position);
        adapter.remove(position);
    }
}
