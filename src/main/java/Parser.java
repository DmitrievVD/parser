import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "https://yandex.ru/pogoda/meleuz/details/10-day-weather?via=ms&lat=52.959141&lon=55.927677#13"; // Адрес страницы
            Document page = Jsoup.parse(new URL(url), 3000); // Получаем HTML страницы
        return page;
    }
    public static void main(String[] args) throws IOException {
        Document page = getPage();
        // css query language
//        FileWriter index = new FileWriter("index.html");
//        index.append(page.toString());
//        index.close();
        Element tableWether = page.select("div[class=forecast-details__day]").first();
//        System.out.println(tableWether);
        String date = tableWether.select("span[class=a11y-hidden]").first().text();
        Element tbodyAll = page.select("tbody[class=weather-table__body]").first();
        Elements tbody = tbodyAll.select("tr[class=weather-table__row]");

//        System.out.println(date + "    Явления     Двление     Влажность      Ветер    Ощущается");
        System.out.println("Погода в Мелеузе\n");
        System.out.format("%20s%32s%20s%20s%20s%20s", date, "Явления", "Двление", "Влажность", "Ветер", "Ощущается");
        System.out.println("");

        for (Element value: tbody){

            String time = value.select("span[class=a11y-hidden]").text().split("°")[0];

            String condition = value.select("td[class=weather-table__body-cell weather-table__body-cell_type_condition]").text();
            String presure = value.select("td[class=weather-table__body-cell weather-table__body-cell_type_air-pressure]").text();
            String humidity = value.select("td[class=weather-table__body-cell weather-table__body-cell_type_humidity]").text();
            Element wraperAll = value.select("td[class=weather-table__body-cell weather-table__body-cell_type_wind weather-table__body-cell_wrapper]").first();
            String wraperSpeed = wraperAll.select("span[class=wind-speed]").text();
            String wraperDer = wraperAll.select("div[class=weather-table__wind-direction]").text();
            String wraper = wraperSpeed + " " + wraperDer;
            Element likeAll = value.select("td[class=weather-table__body-cell weather-table__body-cell_type_feels-like]").first();
            String like = likeAll.select("span[class=temp__value temp__value_with-unit]").text();
//            System.out.println(time + "  |   " + condition + "  |  " + presure + "  |  " + humidity + "  |  " + wraper + " |  " + like);
            System.out.format("%20s%32s%20s%20s%20s%20s", time, condition, presure, humidity, wraper, like);
            System.out.println("");
        }


    }
}
