package Project.Enums;

import Project.App;
import javafx.scene.image.Image;

import java.util.ArrayList;

public enum AvatarURLEnum {
    IMG0(App.class.getResource("/images/avatars/Alexander.png").toString()),
    IMG1(App.class.getResource("/images/avatars/Amanitore.png").toString()),
    IMG2(App.class.getResource("/images/avatars/Ambiorix.png").toString()),
    IMG3(App.class.getResource("/images/avatars/B3F_Tri.png").toString()),
    IMG4(App.class.getResource("/images/avatars/Basil_II.png").toString()),
    IMG5(App.class.getResource("/images/avatars/Catherine_de_Medici.png").toString()),
    IMG6(App.class.getResource("/images/avatars/Cleopatra.png").toString()),
    IMG7(App.class.getResource("/images/avatars/Cyrus.png").toString()),
    IMG8(App.class.getResource("/images/avatars/Dido.png").toString()),
    IMG9(App.class.getResource("/images/avatars/Eleanor_of_Aquitaine.png").toString()),
    IMG10(App.class.getResource("/images/avatars/Frederick_Barbarossa.png").toString()),
    IMG11(App.class.getResource("/images/avatars/Gandhi.png").toString()),
    IMG12(App.class.getResource("/images/avatars/Genghis_Khan.png").toString()),
    IMG13(App.class.getResource("/images/avatars/Gilgamesh.png").toString()),
    IMG14(App.class.getResource("/images/avatars/Gitarja.png").toString()),
    IMG15(App.class.getResource("/images/avatars/Gorgo.png").toString()),
    IMG16(App.class.getResource("/images/avatars/Hammurabi.png").toString()),
    IMG17(App.class.getResource("/images/avatars/Harald_Hardrada.png").toString()),
    IMG18(App.class.getResource("/images/avatars/Hojo_Tokimune.png").toString()),
    IMG19(App.class.getResource("/images/avatars/Jadwiga.png").toString()),
    IMG20(App.class.getResource("/images/avatars/Jayavarman_VII.png").toString()),
    IMG21(App.class.getResource("/images/avatars/Jo3Fo_III.png").toString()),
    IMG22(App.class.getResource("/images/avatars/John_Curtin.png").toString()),
    IMG23(App.class.getResource("/images/avatars/Kristina.png").toString()),
    IMG24(App.class.getResource("/images/avatars/Kublai_Khan.png").toString()),
    IMG25(App.class.getResource("/images/avatars/Kupe.png").toString()),
    IMG26(App.class.getResource("/images/avatars/Lady_Six_Sky.png").toString()),
    IMG27(App.class.getResource("/images/avatars/Lautaro.png").toString()),
    IMG28(App.class.getResource("/images/avatars/Mansa_Musa.png").toString()),
    IMG29(App.class.getResource("/images/avatars/Matthias_Corvinus.png").toString()),
    IMG30(App.class.getResource("/images/avatars/Menelik_II.png").toString()),
    IMG31(App.class.getResource("/images/avatars/Montezuma.png").toString()),
    IMG32(App.class.getResource("/images/avatars/Mvemba_a_Nzinga.png").toString()),
    IMG33(App.class.getResource("/images/avatars/Pachacuti.png").toString()),
    IMG34(App.class.getResource("/images/avatars/Pedro_II.png").toString()),
    IMG35(App.class.getResource("/images/avatars/Pericles.png").toString()),
    IMG36(App.class.getResource("/images/avatars/Peter.png").toString()),
    IMG37(App.class.getResource("/images/avatars/Philip_II.png").toString()),
    IMG38(App.class.getResource("/images/avatars/Poundmaker.png").toString()),
    IMG39(App.class.getResource("/images/avatars/Qin_Shi_Huang.png").toString()),
    IMG40(App.class.getResource("/images/avatars/Robert_the_Bruce.png").toString()),
    IMG41(App.class.getResource("/images/avatars/Saladin.png").toString()),
    IMG42(App.class.getResource("/images/avatars/Seondeok.png").toString()),
    IMG43(App.class.getResource("/images/avatars/Sim3Fn_Bol.png").toString()),
    IMG44(App.class.getResource("/images/avatars/Suleiman.png").toString()),
    IMG45(App.class.getResource("/images/avatars/Tamar.png").toString()),
    IMG46(App.class.getResource("/images/avatars/Teddy_Roosevelt.png").toString()),
    IMG47(App.class.getResource("/images/avatars/Tomyris.png").toString()),
    IMG48(App.class.getResource("/images/avatars/Trajan.png").toString()),
    IMG49(App.class.getResource("/images/avatars/Victoria.png").toString()),
    IMG50(App.class.getResource("/images/avatars/Wilfrid_Laurier.png").toString()),
    IMG51(App.class.getResource("/images/avatars/Wilhelmina.png").toString());
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

    public boolean contains(String url) {
        for (AvatarURLEnum avatarURL : AvatarURLEnum.values()) {
            if (avatarURL.toString().equals(url))
                return true;
        }
        return false;
    }

    public Image getImage() {
        return new Image(url);
    }
}
