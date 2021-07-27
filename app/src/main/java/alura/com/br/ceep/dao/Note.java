package alura.com.br.ceep.dao;

public class Note {

    private final String tittle;
    private final String description;

    public Note(String tittle, String description) {
        this.tittle = tittle;
        this.description = description;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDescription() {
        return description;
    }

}