#include <iostream>
using namespace std;



int main() {
    
    const int size = 10;
    int a[size];
    
    for(int i = 0; i < 11; i++) {
        cout << "Masukkan Nilai Ke-[" << i + 1<< "]: ";
        cin >> a[i];
    }


    cout << "\nHasil Input Nilai Array: \n\n";
    for(int i = 0; i < 11; i++) {
        cout << "Nilai Ke-[" << i + 1 << "] = " << a[i] << endl;
    }


}