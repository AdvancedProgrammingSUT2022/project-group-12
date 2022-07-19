package Project.Models.Cities.Enums;

import java.util.ArrayList;

public enum AvatarURLEnum {
    IMG0("Alexander.png"),
    IMG1("Amanitore.png"),
    IMG2("Ambiorix.png"),
    IMG3("B3F_Tri.png"),
    IMG4("Basil_II.png"),
    IMG5("Catherine_de_Medici.png"),
    IMG6("Cleopatra.png"),
    IMG7("Cyrus.png"),
    IMG8("Dido.png"),
    IMG9("Eleanor_of_Aquitaine.png"),
    IMG10("Frederick_Barbarossa.png"),
    IMG11("Gandhi.png"),
    IMG12("Genghis_Khan.png"),
    IMG13("Gilgamesh.png"),
    IMG14("Gitarja.png"),
    IMG15("Gorgo.png"),
    IMG16("Hammurabi.png"),
    IMG17("Harald_Hardrada.png"),
    IMG18("Hojo_Tokimune.png"),
    IMG19("Jadwiga.png"),
    IMG20("Jayavarman_VII.png"),
    IMG21("Jo3Fo_III.png"),
    IMG22("John_Curtin.png"),
    IMG23("Kristina.png"),
    IMG24("Kublai_Khan.png"),
    IMG25("Kupe.png"),
    IMG26("Lady_Six_Sky.png"),
    IMG27("Lautaro.png"),
    IMG28("Mansa_Musa.png"),
    IMG29("Matthias_Corvinus.png"),
    IMG30("Menelik_II.png"),
    IMG31("Montezuma.png"),
    IMG32("Mvemba_a_Nzinga.png"),
    IMG33("Pachacuti.png"),
    IMG34("Pedro_II.png"),
    IMG35("Pericles.png"),
    IMG36("Peter.png"),
    IMG37("Philip_II.png"),
    IMG38("Poundmaker.png"),
    IMG39("Qin_Shi_Huang.png"),
    IMG40("Robert_the_Bruce.png"),
    IMG41("Saladin.png"),
    IMG42("Seondeok.png"),
    IMG43("Sim3Fn_Bol.png"),
    IMG44("Suleiman.png"),
    IMG45("Tamar.png"),
    IMG46("Teddy_Roosevelt.png"),
    IMG47("Tomyris.png"),
    IMG48("Trajan.png"),
    IMG49("Victoria.png"),
    IMG50("Wilfrid_Laurier.png"),
    IMG51("Wilhelmina.png");
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
}
