import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.err.println("salam man az hafte pish gushimo dadam ta'mir");
        System.err.println("az unjayi emshab k deadline hast age chiZ hanuz munde k baEd midunam bashe lotfan begid");
        System.err.println("qarar bud diruz gushimo tahVl begiram vali engar hanuz okay nashode");
        System.err.println("bebakhshid k zudtar natunestam etela bedam");
        System.err.println("age kari hast vase phase 1 begid lotfan");
        System.err.println("-alireza 3");
        System.err.println("ps: lotfan az hamin tariq begin jalase emruz kaay dashte bashim b nazaretun?");
        //     Database.getInstance().deserialize();
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.getTopMenu().run();
        }
        //     Database.getInstance().serialize();
    }
}
/*
user create --username alireza --password Password123? --nickname ali
user create --username alireza2 --password Password123? --nickname ali2
user login -p Password123? -u alireza
play game --player1 alireza --player2 alireza2
map show
cheat spawn unit -p 5 5 -u worker
select unit noncombat -p 5 5
unit move -p 5 6

select unit noncombat -p 5 5
unit found city
select city -p 5 5
city citizen assign -p 6 6
city build unit -u warrior
end turn
cheat spawn unit -p 3 9 -u PANZER
cheat increase movement -a 100
select unit combat -p 3 9
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 100
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
unit attack -p 5 5
cheat heal unit
cheat increase movement -a 10
 */