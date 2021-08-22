package alura.com.br.ceep.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import alura.com.br.ceep.dao.Note;

public class NoteDAO {

    private final static ArrayList<Note> Notes = new ArrayList<>();

    public List<Note> all() {
        return (List<Note>) Notes.clone();
    }

    public void insert(Note... notes) {
        NoteDAO.Notes.addAll(Arrays.asList(notes));
    }

    public void change(int position, Note note) {
        Notes.set(position, note);
    }
    public void remove(int position) {
        Notes.remove(position);
    }

    public void trade(int startPosition, int positionEnd) {
        Collections.swap(Notes, startPosition, positionEnd);
    }

    public void removeAll() {
        Notes.clear();
    }
}
