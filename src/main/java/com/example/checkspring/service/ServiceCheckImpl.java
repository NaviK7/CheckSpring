package com.example.checkspring.service;


import com.example.checkspring.dao.Check;
import com.example.checkspring.dao.CheckLine;
import com.example.checkspring.dao.DiscountCard;
import com.example.checkspring.dao.Product;
import com.example.checkspring.fileResultCheck.WriteFileCheck;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_CEILING;
import static java.math.BigDecimal.ROUND_DOWN;

@AllArgsConstructor
@Service
public class ServiceCheckImpl implements ServiceCheck {
    private final ProductService productService;
    private final CardService cardService;
    private final WriteFileCheck WRITE_FILE_CHECK = new WriteFileCheck();


    @Override
    public CheckLine creatCheckLine(int count, Long idProduct) {
        Optional<Product> productOptional = Optional.ofNullable(productService.getById(idProduct));
        Product product = productOptional.get();
        CheckLine checkLine = new CheckLine();
        checkLine.setProduct(product);
        checkLine.setCountProduct(count);
        BigDecimal result = new BigDecimal(product.getPrice() * count);
        result = result.setScale(2, ROUND_DOWN);
        checkLine.setCommonPrice(result.doubleValue());
        return checkLine;
    }


    public Check creatCheck(List<CheckLine> checkLineList, DiscountCard discountCard) {
        return new Check(checkLineList, discountCard);
    }

    public Check creatCheck(List<CheckLine> checkLineList) {
        return new Check(checkLineList);
    }

    //возвращает общую стоимость всех покупок
    public double getCommonPriceCheck(Check check) {
        double commonPriceCheck = 0;
        for (CheckLine checkLine : check.getCheckLineList()) {
            commonPriceCheck = commonPriceCheck + checkLine.getCommonPrice();
        }
        BigDecimal result = new BigDecimal(commonPriceCheck);
        result = result.setScale(2, ROUND_DOWN);
        commonPriceCheck = result.doubleValue();
        return commonPriceCheck;
    }

    //возвращает скидку от общей стоимости товара
    public double getCommonPriceDiscount(Check check) {
        double commonDiscount = getCommonPriceCheck(check);
        commonDiscount = commonDiscount * check.getDiscountCard().getDiscountPercentage() / 100;
        BigDecimal result = new BigDecimal(commonDiscount);
        result = result.setScale(2, ROUND_CEILING);
        commonDiscount = result.doubleValue();
        return commonDiscount;
    }

    //возвращает разницу от общей общей стоимости и общей скидки,с учетом или без учета скидочной карты
    public double getTotalPrice(Check check) {
        BigDecimal result;
        if (check.getDiscountCard() != null) {
            result = new BigDecimal(getCommonPriceCheck(check) - getCommonPriceDiscount(check));
            result = result.setScale(2, ROUND_DOWN);
        } else {
            result = new BigDecimal(getCommonPriceCheck(check));
            result = result.setScale(2, ROUND_DOWN);
        }
        return result.doubleValue();
    }


    //возвращает скику по акции, если есть аукционный товар и его количество больше 5
    public double getCommonPriceDiscountStock(Check check) {
        double commonPriceDiscountStock = 0;
        int count = 0;
        for (CheckLine checkLine : check.getCheckLineList()) {
            if (checkLine.getProduct().getDiscountStatus() && checkLine.getCountProduct() > 5) {
                commonPriceDiscountStock = commonPriceDiscountStock + checkLine.getCommonPrice();
            }
        }
        commonPriceDiscountStock = commonPriceDiscountStock * 0.1;
        BigDecimal result = new BigDecimal(commonPriceDiscountStock);
        result = result.setScale(2, ROUND_DOWN);
        commonPriceDiscountStock = result.doubleValue();
        return commonPriceDiscountStock;
    }

    //возвращает общую стоимость с учетом всех скидок, если они есть
    public double getTotalPriceWithStock(Check check) {
        BigDecimal result = new BigDecimal(getTotalPrice(check) - getCommonPriceDiscountStock(check));
        result = result.setScale(2, ROUND_DOWN);
        return result.doubleValue();
    }

    public void CheckRunner(String text, String card) throws IOException {
        List<CheckLine> checkLineList = new ArrayList<>();

        //проверяем входной текст на пробелы и убираем лишние
        StringBuilder stringBuilder = new StringBuilder();
        text = text.trim();
        text = text.replaceAll("\\s+", " ");
        stringBuilder.append(text.charAt(0));
        for (int i = 1; i < text.length() - 1; i++) {
            if (text.charAt(i) == ' ' & text.charAt(i - 1) == '-'
                    || text.charAt(i) == ' ' & text.charAt(i + 1) == '-') {
                continue;
            }
            stringBuilder.append(text.charAt(i));
        }
        stringBuilder.append(text.charAt(text.length() - 1));
        text = stringBuilder.toString();
        text = text.replaceAll(" ", ":");
        String[] products = text.split(":");

        //создаем спсок продуктов по id продукта  и его количеству
        for (String s : products) {
            String[] product = s.split("-");
            if (Character.isLetter(product[0].charAt(0)) || Character.isLetter(product[1].charAt(0))) {
                throw new RuntimeException("Вы ввели букву вместо номера товара или его количества");
            }
            checkLineList.add(creatCheckLine(Integer.parseInt(product[1]), Long.parseLong(product[0])));
        }

        //проверяем входной номер скидочной карты
        card = card.replaceAll(" ", "");
        if (card.length() < 5 && !card.equals("")) {
            for (int i = 0; i < card.length(); i++) {
                if (Character.isLetter(card.charAt(i))) {
                    throw new RuntimeException("Вы ввели букву вместо цифры");
                }
            }

            //извлекаем из репозитория нужную карту
            Optional<DiscountCard> discountCardOptional = Optional.ofNullable(cardService.getByName(Integer.parseInt(card)));
            if (discountCardOptional.isPresent()) {
                DiscountCard discountCard = discountCardOptional.get();
                //создаем чек и отправляем в него список требуемых товаров
                Check check = creatCheck(checkLineList, discountCard);
                //вывод вреиени совершения покупки
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat(" yyyy.MM.dd ");
                Date dateNow1 = new Date();
                SimpleDateFormat formatForDateNow1 = new SimpleDateFormat(" HH:mm:ss ");
                WRITE_FILE_CHECK.WriteToFile("DATE: " + formatForDateNow.format(dateNow));
                WRITE_FILE_CHECK.WriteToFile("TIME: " + formatForDateNow1.format(dateNow1));
                WRITE_FILE_CHECK.WriteToFile(check.toString().replace("[", "").replace("]", "").replace(",", ""));

                String firstResult = getCommonPriceCheck(check) + "";
                WRITE_FILE_CHECK.WriteToFile("Суммарная стоимость всех покупок: " + firstResult + " BYN");

                String secondResult = "" + getCommonPriceDiscount(check);
                WRITE_FILE_CHECK.WriteToFile("Суммарная скидка от стоимости всех покупок: " + secondResult + " BYN");
                if (checkingDiscountStatus(check)) {
                    String thirdResult = getTotalPriceWithStock(check) + "";
                    WRITE_FILE_CHECK.WriteToFile("Итого с учетом скидки по акции: " + thirdResult + " BYN");
                } else {
                    String thirdResult = getTotalPrice(check) + "";
                    WRITE_FILE_CHECK.WriteToFile("Итого:  " + thirdResult + " BYN");
                }
                WRITE_FILE_CHECK.WriteToFile("--------------------------------------------------------------------" + "\n");
            } else throw new NullPointerException("Скидочной карты с таким номером нет");
        } else if (card.equals("")) {
            //создаем чек и отправляем в него список требуемых товаров
            Check check = creatCheck(checkLineList);
            //вывод времени совершения покупки
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat(" yyyy.MM.dd ");
            Date dateNow1 = new Date();
            SimpleDateFormat formatForDateNow1 = new SimpleDateFormat(" HH:mm:ss ");
            WRITE_FILE_CHECK.WriteToFile("DATE: " + formatForDateNow.format(dateNow));
            WRITE_FILE_CHECK.WriteToFile("TIME: " + formatForDateNow1.format(dateNow1));
            WRITE_FILE_CHECK.WriteToFile(check.toString().replace("[", "").replace("]", "").replace(",", ""));
            String firstResult = getCommonPriceCheck(check) + "";
            WRITE_FILE_CHECK.WriteToFile("Суммарная стоимость всех покупок: " + firstResult + " BYN");
            if (checkingDiscountStatus(check)) {
                String thirdResult = getTotalPriceWithStock(check) + "";
                WRITE_FILE_CHECK.WriteToFile("Итого с учетом скидки по акции: " + thirdResult + " BYN");
            } else {
                String thirdResult = getTotalPrice(check) + "";
                WRITE_FILE_CHECK.WriteToFile("Итого:  " + thirdResult + " BYN");
            }
            WRITE_FILE_CHECK.WriteToFile("--------------------------------------------------------------------" + "\n");
        } else throw new RuntimeException("Нужно ввести четыре цифры");
    }

    public boolean checkingDiscountStatus(Check check) {
        boolean stockStatus = false;
        for (CheckLine checkLine : check.getCheckLineList()) {
            if (checkLine.getProduct().getDiscountStatus() && checkLine.getCountProduct() > 5) {
                stockStatus = true;
                break;
            }
        }
        return stockStatus;
    }

}
