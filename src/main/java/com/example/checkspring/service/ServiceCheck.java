package com.example.checkspring.service;


import com.example.checkspring.dao.Check;
import com.example.checkspring.dao.CheckLine;
import com.example.checkspring.dao.DiscountCard;

import java.io.IOException;
import java.util.List;

public interface ServiceCheck {


    CheckLine creatCheckLine(int count, Long idProduct);

    Check creatCheck(List<CheckLine> checkLineList, DiscountCard discountCard);

    Check creatCheck(List<CheckLine> checkLineList);

    double getCommonPriceCheck(Check check);

    double getCommonPriceDiscount(Check check);

    double getTotalPrice(Check check);

    double getCommonPriceDiscountStock(Check check);

    double getTotalPriceWithStock(Check check);

    void CheckRunner(String text, String card) throws IOException;
}
