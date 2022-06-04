package Project.Enums;

import javafx.scene.image.Image;
import Project.Models.User;

import java.util.ArrayList;

public enum AvatarURLEnum {
    IMG01(User.class.getResource("/images/avatars/cuphead_jump.gif").toString()),
    IMG02(User.class.getResource("/images/avatars/tile017.png").toString()),
    IMG03(User.class.getResource("/images/avatars/tile015.png").toString()),
    IMG04(User.class.getResource("/images/avatars/tile014.png").toString()),
    IMG05(User.class.getResource("/images/avatars/tile021.png").toString()),
    IMG06(User.class.getResource("/images/avatars/tile018.png").toString()),
    IMG07(User.class.getResource("/images/avatars/tile009.png").toString()),
    IMG08(User.class.getResource("/images/avatars/tile000.png").toString()),
    IMG09(User.class.getResource("/images/avatars/tile001.png").toString()),
    IMG10(User.class.getResource("/images/avatars/tile002.png").toString()),
    IMG11(User.class.getResource("/images/avatars/tile003.png").toString()),
    IMG12(User.class.getResource("/images/avatars/tile004.png").toString()),
    IMG13(User.class.getResource("/images/avatars/tile005.png").toString()),
    IMG14(User.class.getResource("/images/avatars/tile007.png").toString()),
    IMG15(User.class.getResource("/images/avatars/tile008.png").toString()),
    IMG16(User.class.getResource("/images/avatars/tile010.png").toString()),
    IMG19(User.class.getResource("/images/avatars/tile016.png").toString()),
    IMG20(User.class.getResource("/images/avatars/tile019.png").toString()),
    IMG23(User.class.getResource("/images/avatars/tile023.png").toString()),
    IMG25(User.class.getResource("/images/avatars/tile025.png").toString()),
    IMG26(User.class.getResource("/images/avatars/tile026.png").toString()),
    WINNING_BIRD(User.class.getResource("/images/avatars/winningBird.png").toString()),
    LOSER_BIRD(User.class.getResource("/images/avatars/loserBird.png").toString()),
    POWERED_BIRD(User.class.getResource("/images/avatars/braveBird.png").toString());
    private String url;

    AvatarURLEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getPaths() {
        ArrayList<String> paths = new ArrayList<>();
        for (AvatarURLEnum url : AvatarURLEnum.values()) {
            paths.add(url.name());
        }
        return paths;
    }

    public boolean contains(String url){
        for (AvatarURLEnum avatarURL: AvatarURLEnum.values()) {
            if (avatarURL.toString().equals(url))
                return true;
        }
        return false;
    }

    public Image getImage() {
        return new Image(url);
    }
}
