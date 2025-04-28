package org.example; 
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.Comparator;

class Huffman
{
   public static Map<String, Object> compress(String plaintext)
   {
    Map<Character, Integer> freqMap = buildFrequencyMap(plaintext);
    Node root = buildTree(freqMap);
    Map<Character, String> codeMap = new HashMap<>();
    huffmanCodes(root, "", codeMap);

    StringBuilder compressedText = new StringBuilder();
    for(int i=0; i < plaintext.length(); i++){
        char c = plaintext.charAt(i);
        compressedText.append(codeMap.get(c));
    }

    System.out.println("Char\tFreq\tCode");
        for(char c : codeMap.keySet()){
            System.out.println("'" + c + "'\t" + freqMap.get(c) + "\t" + codeMap.get(c));
        }
    
    Map<String, Object> result = new HashMap<>();
    result.put("compressed", compressedText.toString());
    result.put("codeMap", codeMap);
    result.put("freqMap", freqMap);

    return result;
   }

   public static String decompress(String compressedText, Map<Character, String> codeMap)
   {
    Map<String, Character> charMap = new HashMap<>();
    for (Map.Entry<Character, String> entry : codeMap.entrySet()){
        charMap.put(entry.getValue(), entry.getKey());
    }

    StringBuilder decompressedText = new StringBuilder();
    StringBuilder bits = new StringBuilder();

    for(int i=0; i < compressedText.length(); i++){
        bits.append(compressedText.charAt(i));
        if(charMap.containsKey(bits.toString())){
            decompressedText.append(charMap.get(bits.toString()));
            bits.setLength(0);
        }
    }
    return decompressedText.toString();
   }

   public static Map<Character, Integer> buildFrequencyMap(String txt)
   {
    Map<Character, Integer> freqMap = new HashMap<>();

    char[] textArray = txt.toCharArray();
    for(int i=0; i < textArray.length; i++){
        char c = textArray[i];
        freqMap.put(c, freqMap.getOrDefault(c,0) + 1);
    }
    return freqMap;
   }

   public static Node buildTree(Map<Character, Integer> freqMap)
   {
    PriorityQueue<Node> que = new PriorityQueue<>(Comparator.comparingInt(n -> n.frequency));

    for(Map.Entry<Character, Integer> entry : freqMap.entrySet()){
        que.add(new Node(entry.getKey(), entry.getValue()));
    }

    while(que.size() > 1){
        Node left = que.poll();
        Node right = que.poll();
        Node combined = new Node(left.frequency + right.frequency, left, right);
        que.add(combined);
    }
    return que.poll();
   }

   public static void huffmanCodes(Node root, String currCode, Map<Character, String> codeMap)
   {
    if(root == null) return;
    if (root.left == null && root.right == null){
        codeMap.put(root.character, currCode);
        return;
    }

    huffmanCodes(root.left, currCode + "0", codeMap);
    huffmanCodes(root.right, currCode + "1", codeMap);
   }
}