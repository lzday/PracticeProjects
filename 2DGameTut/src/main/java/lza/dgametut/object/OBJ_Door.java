
package lza.dgametut.object;

import lza.dgametut.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{
    GamePanel gp;
    public OBJ_Door(GamePanel gp){
        this.gp = gp;
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/obj/door.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        }catch(Exception e){
            e.printStackTrace();
        }
        collision = true;
    }

}
