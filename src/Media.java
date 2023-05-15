public abstract class Media implements Comparable<Media> {
    String code;
    String title;
    int copies;

    public Media() {
    }

    public Media(String code, String title, int copies) {
        this.code = code;
        this.title = title;
        this.copies = copies;
    }


    public abstract String getInfo();
    public abstract String getDetails();
    // public abstract String getDetails2();


    public abstract String type();

    public abstract boolean equals(Object obj);

    // public abstract int compareTo(Media other);

    public int compareTo(Media other) {
        return this.title.compareTo(other.title);
    }
    
}