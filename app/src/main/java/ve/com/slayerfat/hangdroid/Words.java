package ve.com.slayerfat.hangdroid;

public class Words {
    private final String[] words;

    public Words(String string) {
        this.words = string.split(" ");
    }

    public String[] getWords() {
        return words;
    }

    public String getWord() {
        int i = (int) (Math.random() * words.length);
        return words[i].toLowerCase();
    }
}
