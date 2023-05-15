public class Album extends Media{
    String artist;
    String songs;
    String [] songList;
    public Album () {

    }

    public Album (String code, String title, int copies, String artist, String songs) {
        super(code, title, copies);
        this.artist = artist;
        this.songs = songs;
        this.songList = songs.split(",");
    }

    @Override
    public String getInfo() {
        return this.code + "; " + this.title + "; " + this.copies + "; " + this.artist + "; " + this.songs;
    }

    @Override
    public String getDetails(){
        return "Type: " + this.type() + ";Code: " + this.code + ";Title: " + this.title  + ";Copies: " + this.copies + ";Artist: " + this.artist + ";Songs: " + this.songs.replaceAll(",", " ");
    }

    // @Override
    // public String getDetails2(){
    //     return "Type: " + this.type() + "\nCode: " + this.code + "\nTitle: " + this.title  + "\nCopies: " + this.copies + "\nArtist: " + this.artist + "\nSongs: " + this.songs.replaceAll(",", " ");
    // }

    public String type () {
        return "Album";
    }
    // @Override
    // public int compareTo(Album o) {
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
        final Album other = (Album) obj;
        
        return other.title == title && other.artist == artist && other.songs == songs;
    }

}