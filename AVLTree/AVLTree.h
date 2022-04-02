//
// Created by lijh on 2021/10/30.
//

#ifndef AVLTREE_AVLTREE_H
#define AVLTREE_AVLTREE_H
#include <iostream>
#include <algorithm>
#include <vector>

// 正确的插入位置一定是一个叶子节点
// 由下至上的第一个不平衡节点不平衡的原因
// 一定是因为左子树 或 右子树导致的
// 所以要看被插入的节点

// 等于 0 的情况是不会发生的吧
// 如果等于 0 的情况会发生
// 那说明插入了一个节点后，对这棵树的
// 最大高度没有产生影响
// 所以自然也不会导致根节点的左子树高度发生变化
// 那就说明不会发生不平衡

template<typename K, typename V>
class AVLTree {
private:
    struct Node{
        K key;
        V value;
        Node* left;
        Node* right;
        int height;

        Node(K key, V value){
            this->key = key;
            this->value = value;
            left = nullptr;
            right = nullptr;
            height = 1;
        }
    };

    Node* root;
    int size;

private:
    int getHeight(Node* node){
        if(node == nullptr)
            return 0;
        return node->height;
    }

    int getBalanceFactor(Node* node){
        if(node == nullptr)
            return 0;
        return getHeight(node->left) - getHeight(node->right);
    }

    void inOrder(Node* node){
        if(node == nullptr)
            return;

        inOrder(node->left);
        std::cout << "inOrder value : " << node->value << "   " ;
        std::cout << "balance factor : " << getBalanceFactor(node) << std::endl;
        inOrder(node->right);
    }

    bool isBalanced(Node* node){
        if(node == nullptr)
            return true;

        int balanceFactor = getBalanceFactor(node);
        if(std::abs(balanceFactor) > 1){
//            std::cout<<"factor : " << balanceFactor << "   ";
            return false;
        }


        return isBalanced(node->left) && isBalanced(node->right);
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    Node* leftRotate(Node* y){
        Node* x = y->right;
        Node* T2 = x->left;

        // 旋转
        y->right = T2;
        x->left = y;

        // 更新 height
        y->height = std::max(getHeight(y->left), getHeight(y->right))+1;
        x->height = std::max(getHeight(x->left), getHeight(x->right))+1;

        return x;
    }

    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    Node* rightRotate(Node* y){
        Node* x = y->left;
        Node* T3 = x->right;

        // 旋转
        y->left = T3;
        x->right = y;

        // 更新 height
        y->height = std::max(getHeight(y->left), getHeight(y->right))+1;
        x->height = std::max(getHeight(x->left), getHeight(x->right))+1;

        return x;
    }

    Node* add(Node* node, K key, V value){
//        if(node != nullptr)
//            std::cout <<"value : " << node->value << std::endl;

        if(node == nullptr){
            size ++;
            return new Node(key, value);
        }

        if(key < node->key)
            node->left = add(node->left, key, value);
        else if(key > node->key)
            node->right = add(node->right, key, value);
        else
            node->value = value;

        // 更新 Height
        node->height = 1 + std::max(getHeight(node->left), getHeight(node->right));

        // 更新 balanceFactor
        int balanceFactor = getBalanceFactor(node);

        // 维护节点的平衡性

        // LL

        if(balanceFactor > 1 && getBalanceFactor(node->left) > 0)
            return rightRotate(node);

        // RR

        if(balanceFactor < -1 && getBalanceFactor(node->right) < 0)
            return leftRotate(node);

        // LR
        if(balanceFactor > 1 && getBalanceFactor(node->left) < 0){
            // 对一个节点的孩子进行旋转操作
            // 并不会影响到自己
            node->left = leftRotate(node->left);
            return rightRotate(node);
        }

        // RL
        if(balanceFactor < -1 && getBalanceFactor(node->right) > 0) {
            node->right = rightRotate(node->right);
            return leftRotate(node);
        }



        return node;

    }

    Node* getNode(Node* node, K key){
        if(node == nullptr)
            return nullptr;

        if(node->key == key){
            return node;
        }else if(key < node->key){
            return getNode(node->left, key);
        }else
            return getNode(node->right, key);
    }

    // 传入的 node 不允许为空
    Node* minimum(Node* node){
        if(node->left == nullptr)
            return node;

        return minimum(node->left);
    }

    // 返回以 node 为根得树
    // 删除 key 为键的节点后的该树的根节点
    Node* remove(Node* node, K key){
        if(node == nullptr)
            return nullptr;

        Node* retNode;

        if(key < node->key){
//            if(node->key == 4 && key == 3){
//                std::cout << "test here" << std::endl;
//            }
            node->left = remove(node->left, key);
            retNode = node;
        }else if(key > node->key){
            node->right = remove(node->right, key);
            retNode = node;
        }else{

            // 1 左子树为空
            if(node->left == nullptr){
                retNode = node->right;
                delete node;
                size --;
            }

            // 2 右子树为空
            else if(node->right == nullptr){
                retNode = node->left;
                delete node;
                size --;
            }

            // 3 左子树 和 右子树都不为空
            // 在右子树中找到键值最小的节点
            // 将此节点覆盖当前的 node 节点的 key value
            // 最后递归删除 successor
            else{
                Node* successor = minimum(node->right);
                node->key = successor->key;
                node->value = successor->value;

                node->right = remove(node->right, successor->key);
                retNode = node;
            }

        }

        // 删除节点后树可能为空
        if(retNode == nullptr)
            return nullptr;

        // 更新 height
        retNode->height = std::max(getHeight(retNode->left), getHeight(retNode->right))+1;

        // 计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

//        if(retNode -> key == 4){
//            std::cout << "test balance Factor : " << balanceFactor << std::endl;
//        }

        // 维护平衡

        // LL
        if(balanceFactor > 1 && getBalanceFactor(retNode->left) >= 0)
            return rightRotate(retNode);

        // RR

        if(balanceFactor < -1 && getBalanceFactor(retNode->right) <= 0)
            return leftRotate(retNode);

        // LR
        if(balanceFactor > 1 && getBalanceFactor(retNode->left) < 0){
            // 对一个节点的孩子进行旋转操作
            // 并不会影响到自己
            retNode->left = leftRotate(retNode->left);
            return rightRotate(retNode);
        }

        // RL
        if(balanceFactor < -1 && getBalanceFactor(retNode->right) > 0) {
            retNode->right = rightRotate(retNode->right);
            return leftRotate(retNode);
        }

        return retNode;
    }



public:
    AVLTree(){
        root = nullptr;
        size = 0;
    }

    int getSize(){
        return size;
    }

    V get(K key){
        Node* node = getNode(root, key);
        if(node == nullptr)
            return 0;

        return node->value;
    }

    bool isEmpty(){
        return size == 0;
    }

    void add(K key, V value){
        root = add(root, key, value);

    }

    void inOrder(){
        inOrder(root);
    }

    void inOrder(Node* node, std::vector<K>& keys){
        if(node == nullptr)
            return;

        inOrder(node->left, keys);
        keys.push_back(node->key);
        inOrder(node->right, keys);
    }

    bool isBST(){
        std::vector<K> keys;
        inOrder(root, keys);
//        for(int i = 1 ; i < keys.size()-1 ; ++i){
//            if(keys[i] > keys[i+1])
//                return false;
//        }

        // 原因是，因为当 size == 0 的时候减去 1
        // 而 size 是一个无符号类型的整数，减去 1 会出现问题
        for(int i = 1 ; i < keys.size() ; ++i){
            if(keys[i-1] > keys[i])
                return false;
        }
        return true;
    }

    bool isBalanced(){
        return isBalanced(root);
    }

    void remove(K key){
        Node* node = getNode(root, key);
        if(node == nullptr){
            return;
        }
        V value = node->value;

        root = remove(root, key);
    }

    void preOrder(Node* node){
        if(node == nullptr)
            return;

        std::cout << node->value << ", ";
        preOrder(node->left);
        preOrder(node->right);

    }

    void preOrder(){
        preOrder(root);
    }

};


#endif //AVLTREE_AVLTREE_H
