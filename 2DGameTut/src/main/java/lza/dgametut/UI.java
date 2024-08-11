package lza.dgametut;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished;

    public String currentDialogue;

    public UI(GamePanel gp){
        this.gp = gp;
        // instantiate objects before game loop starts
        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            /*is = getClass().getResourceAsStream("/font/PurisaB.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);*/
        } catch (FontFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(maruMonica);
        /* The following can be used for purisaB to make it look nicer (less jagged):
         * g2.setFont(purisaB);
         * g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); */
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState){
            // do playstate stuff
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
    }

    public void drawTitleScreen(){
        /* if changing background color:
         * g2.setColor(new Color(70, 120, 80));
         * g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight); */

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Blue Boy Adventure";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        // SHADOW COLOR
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);
        // MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // BLUE BOY IMAGE
        x = gp.screenWidth/2 - gp.tileSize;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text, x, y);

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
    }

    public void drawPauseScreen(){ // all pausing stuff here
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        // WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) { // graphics2d ignores \n while drawing so it needs to be implemented manually
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 210); // black
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255); // white
        g2.setStroke(new BasicStroke(5));
        g2.setColor(c);
        g2.drawRoundRect(x + 5, y+ 5, width-10, height-10, 25, 25);
    }

    int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
