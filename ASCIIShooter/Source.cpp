#include <iostream>
using namespace std;

#include <windows.h>

int nScreenWidth = 120;
int nScreenHeight = 40;

int main(){
    // Create Screen Buffer
    wchar_t *screen = new wchar_t[nScreenWidth*nScreenHeight];
    HANDLE hConsole = CreateConsoleScreenBuffer(GENERIC_READ | GENERIC_WRITE, 0, NULL, CONSOLE_TEXTMODE_BUFFER, NULL);
    SetConsoleActiveScreenBuffer(hConsole);
    DWORD dwBytesWritten = 0;

    while(1){
        screen[nScreenWidth * nScreenHeight - 1] = '/0';
        WriteConsoleOutputCharacter(hConsole, screen, nScreenWidth*nScreenHeight, {0,0}, &dwBytesWritten); // writes to top right corner (console doesn't scroll down)
    }
    screen[nScreenWidth * nScreenHeight - 1] = '/0';
    WriteConsoleOutputCharacter(hConsole, screen, nScreenWidth*nScreenHeight, {0,0}, &dwBytesWritten); // writes to top right corner (console doesn't scroll down)

    return 0;
}