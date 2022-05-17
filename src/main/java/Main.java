import Models.Database;
import Views.MenuStack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Database.getInstance().deserialize();
        Scanner scanner = new Scanner(System.in);
        MenuStack menuStack = MenuStack.getInstance();
        menuStack.setScanner(scanner);
        menuStack.gotoLoginMenu();
        while (!menuStack.isEmpty()) {
            menuStack.getTopMenu().run();
        }
        Database.getInstance().serialize();
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


/*

Alireza Mosallanezhad, [5/15/22 8:18 AM]

سلام

من دستور های view رو برای info اضافه کردم

بی زحمت تابع‌های info رو تست کن اگر تغییری لازم بود تکمیلشون کن (اسم دستور‌ها توی GameMenu.info) هست

سه دستور info هم اضافه کردم، برای یه شهر خاص، یه تایل خاص و یه یگان خاص

تابع‌های getInfo اینها رو هم بی زحمت بزن

user create -u alireza -p afepAFE232$#$ -n Parsi
user login  -u alireza -p afepAFE232$#$
play game --player1 alireza
select unit noncombat -p 10 10
unit found city
select city -p 10 10
city citizen assign -p 11 10
cheat spawn unit -u SETTLER -p 10 10
cheat spawn unit -u ARCHER -p 10 10
city buy tile -p 12 11
city info
select unit combat -p 10 10
unit alert
select unit combat -p 10 10
unit info
map info -p 10 10

این دستورا هم برای تست می‌تونی استفاده کنی
 */