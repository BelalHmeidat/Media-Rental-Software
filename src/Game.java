public class Game extends Media{
    double grams;

    public Game () {

    }

    public Game (String code, String title, int copies, double grams) {
        super(code, title, copies);
        this.grams = grams;
    }

    @Override
    public String getInfo() {
        return this.code + "; " + this.title + "; " + this.copies + "; " + this.grams;
    }
    @Override
    public String getDetails (){
        return "Type: " + this.type() + ";Code: " + this.code + ";Title: " + this.title  + ";Copies: " + this.copies + ";Weight: " + this.grams;
    }
    // @Override
    // public String getDetails2(){//for multiple lines
    //     return "Type: " + this.type() + "\nCode: " + this.code + "\nTitle: " + this.title  + "\nCopies: " + this.copies + "\nWeight: " + this.grams;
    // }

    public String type () {
        return "Game";
    }
    // @Override
    // public int compareTo(Game o) {
    //     return this.title.compareTo(o.title);
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
        final Game other = (Game) obj;
        
        return other.title == title && other.copies == copies && other.grams == grams;
    }
}