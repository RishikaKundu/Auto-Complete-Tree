package autoComplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up
 * to 26, one per letter). Each child node represents a letter. A path from a root's child node down
 * to a node where isWord is true represents the sequence of characters in a word.
 */
public class PrefixTree {
    private TreeNode root;

    // Number of words contained in the tree
    private int size;

    public PrefixTree() {
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node If the word, is
     * already in the tree, then this has no effect.
     * 
     * @param word
     */
    public void add(String word) {
        TreeNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (!currentNode.children.containsKey(letter)) {

                TreeNode newNode = new TreeNode();
                newNode.letter = letter;
                currentNode.children.put(letter, newNode);

            }
            currentNode = currentNode.children.get(letter);
        }
        if (!(currentNode.isWord == true)) {
            currentNode.isWord = true;
            size += 1;
        }
    }

    /**
     * Checks whether the word has been added to the tree
     * 
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word) {
        TreeNode currentNode = root;

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (!currentNode.children.containsKey(letter)) {
                return false;
            }
            currentNode = currentNode.children.get(letter);


        }
        return currentNode.isWord;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself). The
     * order of the list can be arbitrary.
     * 
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix) {
        TreeNode currentTreeNode = root;

        for (char character : prefix.toCharArray()) {
            currentTreeNode = currentTreeNode.children.get(character);
        }

        ArrayList<String> listString = new ArrayList<>();
        getRecursive(currentTreeNode, prefix.substring(0, prefix.length() - 1), listString);
        return listString;
    }

    /**
     * A recursive function to perform preorder traversal. Used as a helper function for
     * getWordsForPrefix
     * 
     * @param node
     * @param prefixWords
     * @param listString
     */

    public void getRecursive(TreeNode node, String prefixWords, List<String> listString) {
        if (node.isWord) {
            listString.add(prefixWords + node.letter);
        }
        for (Map.Entry<Character, TreeNode> character : node.children.entrySet()) {
            getRecursive(character.getValue(), prefixWords + node.letter, listString);
        }

    }


    /**
     * @return the number of words in the tree
     */
    public int size() {
        return size;
    }

}
