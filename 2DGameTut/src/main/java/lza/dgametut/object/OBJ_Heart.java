package lza.dgametut.object;

import lza.dgametut.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;

    public OBJ_Heart(GamePanel gp){
        this.gp = gp;
        name = "Heart";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/obj/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/obj/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/obj/heart_blank.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
            uTool.scaledImage(image2, gp.tileSize, gp.tileSize);
            uTool.scaledImage(image3, gp.tileSize, gp.tileSize);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
