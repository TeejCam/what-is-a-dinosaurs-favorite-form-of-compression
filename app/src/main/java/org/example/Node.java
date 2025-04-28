package org.example;

class Node
{
    char character;
    int frequency;
    Node left, right;

    Node(char character, int frequency)
    {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    Node(int frequency, Node left, Node right)
    {
        this.character = '\0';
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}