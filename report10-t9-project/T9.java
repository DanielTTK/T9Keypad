import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;

public class T9 {
    private class Node {
        public Node[] next;
        public boolean valid;

        public Node() {
            next = new Node[27];
            valid = false; // indicates if the path from root is valid
        }
    }

    private Node root = new Node();

    // 0 for 'a', 26 for 'ö'
    private static int code(char w) {
        switch (w) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            case 'k':
                return 10;
            case 'l':
                return 11;
            case 'm':
                return 12;
            case 'n':
                return 13;
            case 'o':
                return 14;
            case 'p':
                return 15;
            case 'r':
                return 16;
            case 's':
                return 17;
            case 't':
                return 18;
            case 'u':
                return 19;
            case 'v':
                return 20;
            case 'x':
                return 21;
            case 'y':
                return 22;
            case 'z':
                return 23;
            case 'å':
                return 24;
            case 'ä':
                return 25;
            case 'ö':
                return 26;
            default:
                return -1; // Invalid
        }
    }

    // reverse function of code
    private static char reverseCode(int n) {
        switch (n) {
            case 0:
                return 'a';
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            case 4:
                return 'e';
            case 5:
                return 'f';
            case 6:
                return 'g';
            case 7:
                return 'h';
            case 8:
                return 'i';
            case 9:
                return 'j';
            case 10:
                return 'k';
            case 11:
                return 'l';
            case 12:
                return 'm';
            case 13:
                return 'n';
            case 14:
                return 'o';
            case 15:
                return 'p';
            case 16:
                return 'r';
            case 17:
                return 's';
            case 18:
                return 't';
            case 19:
                return 'u';
            case 20:
                return 'v';
            case 21:
                return 'x';
            case 22:
                return 'y';
            case 23:
                return 'z';
            case 24:
                return 'å';
            case 25:
                return 'ä';
            case 26:
                return 'ö';
            default:
                return '0'; // Invalid
        }
    }

    // convert to initial key presses, 12345
    @SuppressWarnings("unused")
    private static char charToKey(char c) {
        switch (c) {
            case 'a':
            case 'b':
            case 'c':
                return '1';
            case 'd':
            case 'e':
            case 'f':
                return '2';
            case 'g':
            case 'h':
            case 'i':
                return '3';
            case 'j':
            case 'k':
            case 'l':
                return '4';
            case 'm':
            case 'n':
            case 'o':
                return '5';
            case 'p':
            case 'r':
            case 's':
                return '6';
            case 't':
            case 'u':
            case 'v':
                return '7';
            case 'x':
            case 'y':
            case 'z':
                return '8';
            case 'å':
            case 'ä':
            case 'ö':
                return '9';
            default:
                return '?'; // Unknown character
        }
    }

    // from key to index, from 1 to 0 for example. fits array
    private int keyToIndex(char key) {
        if (key >= '1' && key <= '9')
            return key - '1'; // subtracts and auto coverts to int
        return -1;
    }

    private void add(String file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                addWord(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" file " + file + " not found");
        }
    }

    private void addWord(String word) {
        // System.err.println("Adding word " + word);
        Node curr = root;
        char c;
        int idx = 0;
        for (int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            idx = code(c);

            if (curr.next[idx] == null) { // if no branch, construct
                curr.next[idx] = new Node();
            }
            curr = curr.next[idx]; // traverse;
        }
        curr.valid = true; // mark end
    }

    // Find a list of strings that fit the key characters
    private ArrayList<String> decode(String key) {
        ArrayList<String> words = new ArrayList<>();

        collect(this.root, "", key, words);

        return words;
    }

    private void collect(Node curr, String word, String key, ArrayList<String> words) {

        if (!key.isEmpty()) {
            char c = key.charAt(0);
            int n = keyToIndex(c);

            String newKey = key.substring(1); // All characters without most recent

            for (int i = 0; i < 3; i++) {
                if (curr.next[n * 3 + i] != null) {
                    c = reverseCode(n * 3 + i); // get char
                    collect(curr.next[n * 3 + i], word + c, newKey, words);
                }
            }
        }

        if (curr.valid) {
            words.add(word);
        }
        return;
    }

    public static void main(String[] args) {
        T9 t9 = new T9();

        t9.add("kelly.txt");
        // first two are from the words andra and bakre, converted into keys.
        System.out.println("Decoding '15261':");
        ArrayList<String> words3 = t9.decode("15261");
        System.out.println("Possible words: " + words3);

        System.out.println("Decoding '11462':");
        ArrayList<String> words = t9.decode("11462");
        System.out.println("Possible words: " + words);

        System.out.println("Decoding '752224', ex from assignment:");
        ArrayList<String> words2 = t9.decode("752224");
        System.out.println("Possible words: " + words2);
    }
}