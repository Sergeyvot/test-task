package com.example.test.credit.model;

import com.example.test.credit.dto.CreditDtoView;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel {

    List<CreditDtoView> dataList;
    String[] headerList = new String[]{"Номер", "Дата выдачи", "Тип кредита", "Лимит кредита", "Задолженность",
            "Следующий платеж", "ПСК", "Рекомендован к погашению"};

    public TableModel(List<CreditDtoView> list) {
        dataList = list;
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public Object getValueAt(int row, int column) {

        CreditDtoView entity = dataList.get(row);
        switch (column) {
            case 0:
                return entity.getUuid();
            case 1:
                return entity.getDateOfIssue().toString();
            case 2:
                return entity.getTypeCredit();
            case 3:
                return entity.getLimit();
            case 4:
                return entity.getArrears();
            case 5:
                return entity.getNextPayment();
            case 6:
                return entity.getPsc();
            case 7:
                return entity.getRecommendation();
        }
        return null;
    }

    public String getColumnName(int col) {
        return headerList[col];
    }
}
