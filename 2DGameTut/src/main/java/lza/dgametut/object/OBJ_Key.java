package lza.dgametut.object;

import lza.dgametut.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject{
    GamePanel gp;
    
    public OBJ_Key(GamePanel gp){
        this.gp = gp;
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/obj/key.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
