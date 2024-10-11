#include <iostream>
using namespace std;

#include <windows.h>
#include <math.h>

int nScreenWidth = 120;
int nScreenHeight = 40;

float fPlayerX = 8.0f;
float fPlayerY = 8.0f;
float fPlayerA = 8.0f;

int nMapHeight = 16;
int nMapWidth = 16;

float fFOV = 3.14159 / 4.0; // the view angle on the map
float fDepth = 16.0f; // maximum scanning distance for a wall

int main() {
    // Create Screen Buffer
    wchar_t* screen = new wchar_t[nScreenWidth * nScreenHeight];
    HANDLE hConsole = CreateConsoleScreenBuffer(GENERIC_READ | GENERIC_WRITE, 0, NULL, CONSOLE_TEXTMODE_BUFFER, NULL);
    SetConsoleActiveScreenBuffer(hConsole);
    DWORD dwBytesWritten = 0;

    // create the map
    wstring map;
    map += L"################";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"#..............#";
    map += L"################";

    while (1) { // the game loop
        for (int x = 0; x < nScreenWidth; x++) {
            // For each column, calculate the projected ray angle into world space
            float fRayAngle = (fPlayerA - fFOV / 2.0f) + ((float)x / (float)nScreenWidth) * fFOV; // bisect the view angle

            float fDistanceToWall = 0;
            bool bHitWall = false;
            float fEyeX = sinf(fRayAngle); // Unit vector for ray in player space
            float fEyeY = cosf(fRayAngle);
            // check distance to wall
            while (!bHitWall && fDistanceToWall < fDepth) {
                fDistanceToWall += 0.1f;
                // increment towards the wall until wall is hit
                int nTestX = (int)(fPlayerX + fEyeX * fDistanceToWall);
                int nTestY = (int)(fPlayerY + fEyeY * fDistanceToWall);
                // Test if ray is out of bounds
                if (nTestX < 0 || nTestX >= nMapWidth || nTestY < 0 || nTestY >= nMapHeight) {
                    bHitWall = true;
                    fDistanceToWall = fDepth; // Just set distance to maximum depth
                }
                else {
                    // Ray is inbounds, so test to see if the ray cell is a wall block
                    if (map[nTestY * nMapWidth + nTestX] == '#') {
                        bHitWall = true;
                    }
                }
            }

            // Calculate distance to ceiling and floor
            int nCeiling = (float)(nScreenHeight / 2.0) - nScreenHeight / ((float)fDistanceToWall); // take midpoint and subtract proportion of screen height relative to distance to wall
            int nFloor = nScreenHeight - nCeiling; // floor is just mirror of the ceiling

            for (int y = 0; y < nScreenHeight; y++) {
                if (y < nCeiling)
                    screen[y * nScreenWidth + x] = ' ';
                else if (y > nCeiling && y <= nFloor)
                    screen[y * nScreenWidth + x] = '#';
                else
                    screen[y * nScreenWidth + x] = ' ';
            }
        }
        screen[nScreenWidth * nScreenHeight - 1] = '/0';
        WriteConsoleOutputCharacter(hConsole, screen, nScreenWidth*nScreenHeight, {0,0}, &dwBytesWritten); // writes to top right corner (console doesn't scroll down)
    }

    return 0;
}