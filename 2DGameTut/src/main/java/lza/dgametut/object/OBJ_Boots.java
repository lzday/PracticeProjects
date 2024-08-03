
package lza.dgametut.object;

import lza.dgametut.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
    GamePanel gp;
    public OBJ_Boots(GamePanel gp){
        this.gp = gp;
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/obj/boots.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
