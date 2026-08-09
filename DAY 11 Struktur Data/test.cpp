#include <iostream>
#include <string>
using namespace std;


struct Produk {
    string Kode;
    string nama;
    float harga;
    int stok;
};

int main() {
    
    int size = 0;

    cout << "Masukkan Jumlah Data: ";
    cin >> size;
    Produk a[size];
    
    for(int i = 0; i < size; i++) {
        cout << "\nMasukkan Data Produk Ke-[" << i + 1 << "]: " << endl;
        cout << "Kode: ";
        cin >> a[i].Kode;
        cin.ignore();
        cout << "Nama: ";
        getline(cin, a[i].nama);
        cin.ignore();
        cout << "Harga: ";
        cin >> a[i].harga;
        cout << "Stok: ";
        cin >> a[i].stok;
    }


    cout << "\nHasil Input Nilai Array: \n\n";
    for(int i = 0; i < size; i++) {
        cout << "Kode: " << a[i].Kode << endl;
        cout << "Nama: " << a[i].nama << endl;
        cout << "Harga: " << a[i].harga << endl;
        cout << "Stok: " << a[i].stok << endl;
        cout << "------------------------------" << endl;
    }
    return 0;
}