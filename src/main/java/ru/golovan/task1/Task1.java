package ru.golovan.task1;

import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        Data data1 = new Data(234678L, "Иванов Иван Иванович", 2035.34);
        Data data2 = new Data(777777L, "Головань Кирилл Сергеевич", 7777.77);
        Data data3 = new Data(123456L, "Артёмов Артём Артёмович", 1234.12);
        DataCache dataCache = new DataCache();

        //предоставить возможность добавлять новые записи;
        dataCache.addData(data1);
        dataCache.addData(data2);
        dataCache.addData(data3);

        //предоставить возможность удалять более не нужные записи;
        dataCache.removeData(data3);

        //предоставить возможность изменять запись;
        data1.setName("Сергеев Сергей Сергеевич");
        dataCache.updateData(data1);

        //получать полный набор записи по любому из полей с одинаковой алгоритмической сложностью (не медленнее log(n));
        System.out.println("По полю account > ");
        Data dataByAccount = dataCache.getDataByAccount(234678L);
        System.out.println(dataByAccount.getAccount() + " " + dataByAccount.getName());

        System.out.println("По полю name > ");
        List<Data> dataListByName = dataCache.getDataByField("name", "Головань Кирилл Сергеевич");
        dataListByName.forEach(data -> System.out.println(data.getAccount() + " " + data.getName()));

        System.out.println("По полю value > ");
        List<Data> dataListByValue = dataCache.getDataByField("value", 2035.34);
        dataListByValue.forEach(data -> System.out.println(data.getAccount() + " " + data.getName()));

    }
}
