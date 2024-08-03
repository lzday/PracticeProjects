
package lza.dgametut.object;

import lza.dgametut.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject{
    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        this.gp = gp;
        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/obj/chest.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
