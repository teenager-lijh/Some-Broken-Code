#include <iostream>
#include "AVLTree.h"
using namespace std;




int partition(int arr[], int left, int right){
    int key = arr[left];

    while(left < right){
        while(left < right && arr[right] >= key)
            right--;

        if(left >= right)
            break;

        swap(arr[left++], arr[right]);

        while(left < right && arr[left] < key)
            left++;

        if(left >= right)
            break;

        // Because of the key number being based on swap function,
        // the key number don't need to return to the last postition of left index.
        swap(arr[left], arr[right--]);
    }

    return left;
}

int main() {


    const int a = 10;
    int b = 666;
    int* const pointer_a = &a;


    cout << "first output : " << *pointer_a << endl;

    pointer_a = &b;

    cout << "second output £º" << *pointer_a << endl;








//    int a = 0xBBBB;
//    unsigned int b = 0xBCBBBBBB;
//
//    printf("%d        ", a);
//    printf("%u", b);





    /*
    // 7
    const int n = 20000;

    AVLTree<int, int> tree;

    for(int i = 1 ; i <= n ; ++i){
        tree.add(i, i);
//        std::cout<<"Ìí¼Ó" <<i<<std::endl;
//        tree.inOrder();
//        tree.preOrder();
//        std::cout<<std::endl;
    }

    std::cout << "tree size : " << tree.getSize() << std::endl;
    std::cout << "isBalanced : " << tree.isBalanced() << std::endl;
    std::cout << "is BST : " << tree.isBST() << std::endl;

//    tree.inOrder();
//    tree.inOrder();
    for(int i = 1 ; i <= n ; ++i){
//        std::cout<< i << std::endl;
        tree.remove(i);
//        tree.inOrder();
//        tree.preOrder();
//        std::cout<<std::endl;
        if(!tree.isBalanced()|| !tree.isBST()){
            std::cout <<"error" << std::endl;
            break;
        }
    }

    std::cout << "tree size : " << tree.getSize() << std::endl;
    std::cout << "isBalanced : " << tree.isBalanced() << std::endl;
    std::cout << "is BST : " << tree.isBST() << std::endl;

*/
    return 0;
}
