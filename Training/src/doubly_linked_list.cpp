/*
just a simple doubly linked list.
Not for usaco or anything, just for fun.
*/

#include <iostream>

using namespace std;

class Node {
public:
    int data;
    Node *next;
    Node *prev;
    Node(int data) {
        this->data = data;
        next = NULL;
        prev = NULL;
    }
};

class DoublyLinkedList {
public:
    // pointers for head and tail
    Node* head;
    Node* tail;

    DoublyLinkedList() {
        head = NULL;
        tail = NULL;
    }

    void append(int data) {
        Node* node = new Node(data);
        if (tail == NULL) {
            head = node;
            tail = node;
        } else {
            node->prev = tail;
            tail->next = node;
            tail=node;
        }
    }

    void insert(int data) {
        Node* node = new Node(data);
        if (head == NULL) {
            head = node;
            tail = node;
        } else {
            node->next = head;
            head->prev = node;
            head = node;
        }
    }

    Node* pop() {
        if (tail == NULL)
            return NULL;
        Node* node = tail->prev;
        if (node != NULL) {
            node->next = NULL;
        }
        node = tail;
        tail = node->prev;
        return node;
    }

    Node* remove() {
        if (head == NULL)
            return NULL;
        Node* node = head->next;
        if (node != NULL) {
            node->prev = NULL;
        }
        node = head;
        head = node->next;
        return node;
    }

    void print() {
        Node* curr = head;
        while (curr != NULL) {
            cout << curr->data << ",";
            curr = curr->next;
        }
    }

    void print_reverse() {
        Node* curr = tail;
        while (curr != NULL) {
            cout << curr->data << ",";
            curr = curr->prev;
        }
    }
};

int main() {
    DoublyLinkedList* list = new DoublyLinkedList();
    list->append(1);
    list->append(2);
    list->append(3);
    list->insert(0);
    list->print();
    cout << endl;
    list->print_reverse();
    cout << endl;
    list->pop();
    list->print();
    cout << endl;
    list->print_reverse();
    cout << endl;
    list->remove();
    list->print();
    cout << endl;
    list->print_reverse();
    cout << endl;
    return 0;
}