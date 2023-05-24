package ru.golovan.task1;


import java.util.*;

public class DataCache {
    private Map<Long, Data> dataMap; // Хранение данных по уникальному идентификатору (account)
    private Map<String, TreeSet<Long>> nameIndex; // Индекс по name
    private Map<Double, TreeSet<Long>> valueIndex; // Индекс по value

    public DataCache() {
        dataMap = new HashMap<>();
        nameIndex = new HashMap<>();
        valueIndex = new HashMap<>();
    }

    public void addData(Data newData) {
        dataMap.put(newData.getAccount(), newData);
        addToIndex(nameIndex, newData.getName(), newData.getAccount());
        addToIndex(valueIndex, newData.getValue(), newData.getAccount());
    }

    public void removeData(Data oldData) {
        Data removedData = dataMap.remove(oldData.getAccount());
        removeFromIndex(nameIndex, removedData.getName(), oldData.getAccount());
        removeFromIndex(valueIndex, removedData.getValue(), oldData.getAccount());
    }

    public void updateData(Data updatedData) {
        long account = updatedData.getAccount();
        Data previousData = dataMap.get(account);
        if (previousData != null) {
            removeFromIndex(nameIndex, previousData.getName(), account);
            removeFromIndex(valueIndex, previousData.getValue(), account);
        }
        dataMap.put(account, updatedData);
        addToIndex(nameIndex, updatedData.getName(), account);
        addToIndex(valueIndex, updatedData.getValue(), account);
    }

    public Data getDataByAccount(long account) {
        return dataMap.get(account);
    }

    public List<Data> getDataByField(String fieldName, Object fieldValue) {
        if (fieldName.equalsIgnoreCase("name")) {
            List<Data> dataList = new ArrayList<>();
            Set<Long> accountSet = nameIndex.getOrDefault((String) fieldValue, new TreeSet<>());
            if (!accountSet.isEmpty()) {
                for (long account : accountSet) {
                    dataList.add(dataMap.get(account));
                }
            }
            return dataList;
        } else if (fieldName.equalsIgnoreCase("value")) {
            List<Data> dataList = new ArrayList<>();
            Set<Long> accountSet = valueIndex.getOrDefault((Double) fieldValue, new TreeSet<>());
            if (!accountSet.isEmpty()) {
                for (long account : accountSet) {
                    dataList.add(dataMap.get(account));
                }
            }
            return dataList;
        } else {
            throw new IllegalArgumentException("Недопустимое название поля");
        }
    }

    private <T> void addToIndex(Map<T, TreeSet<Long>> index, T key, long account) {
        TreeSet<Long> accountSet = index.getOrDefault(key, new TreeSet<>());
        accountSet.add(account);
        index.put(key, accountSet);
    }

    private <T> void removeFromIndex(Map<T, TreeSet<Long>> index, T key, long account) {
        TreeSet<Long> accountSet = index.get(key);
        if (accountSet != null) {
            accountSet.remove(account);
            if (accountSet.isEmpty()) {
                index.remove(key);
            }
        }
    }
}
