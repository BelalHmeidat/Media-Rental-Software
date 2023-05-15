public class Movie extends Media{
    String rating;
    
    public Movie () {

    }

  
    public Movie (String code, String title, int copies, String rating) {
        super(code, title, copies);
        this.rating = rating;
    }
    @Override
    public String getInfo() {
        return this.code + "; " + this.title + "; " + this.copies + "; " + this.rating;
    }
    @Override
    public String getDetails(){
        return "Type: " + this.type() + ";Code: " + this.code + ";Title: " + this.title  + ";Copies: " + this.copies + ";Rating: " + this.rating;
    }
    // @Override
    // public String getDetails2(){ //multiple lines
    //     return "Type: " + this.type() + "\nCode: " + this.code + "\nTitle: " + this.title  + "\nCopies: " + this.copies + "\nRating: " + this.rating;
    // }
    public String type () {
        return "Movie";
    }
    // @Override
    // public int compareTo(Movie other) {
    //     return this.title.compareTo(other.title);
    // }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        
        return other.title == title && other.copies == copies && other.rating == rating;
    }

}