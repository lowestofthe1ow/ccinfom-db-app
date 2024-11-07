package bocchiTheModel.bocchiTheTable;

public class Genre {
	
	private int id;
    private String genreName;
    private String genreDescription;

    public Genre(String genreName, String genreDescription) {
        this.genreName = genreName;
        this.genreDescription = genreDescription;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreDescription() {
        return genreDescription;
    }

    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription;
    }

}
