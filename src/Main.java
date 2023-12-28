import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

public class Main {

    static Banknote[] banknotes = new Banknote[]{
            new Banknote(5000, 10),
            new Banknote(1000, 20),
            new Banknote(500, 30),
            new Banknote(100, 40)
    };

    public static void main(String[] args)  {
        int giveOut = 6000;
        System.out.println("Сумма для снятия - " + giveOut);
        getMoneyFromATM(giveOut);
    }

    private static void getMoneyFromATM(int giveOut) {
        //Получаем общую сумму денег в банке
        int balance = Arrays.stream(banknotes)
                .map(banknote -> banknote.nominal * banknote.count)
                .reduce(Integer::sum)
                .orElse(0);

        //Прервать выдачу купюр если такой суммы нет в банке, сумма отрицательна или не кратна наименьшей единице номинала
        if(balance <= giveOut || giveOut <= 0 || giveOut % banknotes[banknotes.length-1].nominal != 0 ){
            System.out.println("Невозможно выдать данную сумму.");
            return;
        }

        //Выдача купюр
        for (Banknote banknote : banknotes) {
            //Сколько купюр этого номинала можно выдать
            while (giveOut >= banknote.nominal && banknote.count != 0){
                giveOut-=banknote.nominal;
                banknote.count -=1;
            }
            //Если сумма была выдана то завршить выполнение
            if(giveOut==0){
                break;
            }
        }

        System.out.println("Сумма выдана.\nКоличество оставшихся номиналов купюр:");
        Arrays.stream(banknotes).forEach(System.out::println);

    }
}

class Banknote {
    int nominal;
    int count;

    public Banknote(int nominal, int count) {
        this.nominal = nominal;
        this.count = count;
    }

    @Override
    public String toString() {
        return count + " шт номинала " + nominal;
    }
}