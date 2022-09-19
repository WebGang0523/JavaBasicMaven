package pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Product {
    @ExcelProperty("product_id")
    private String product_id;

    @ExcelProperty("name")
    private String name;

    @ExcelProperty("desc")
    private String desc;

    @ExcelProperty("price")
    private String price;

    @ExcelProperty("currency")
    private String currency;

    @ExcelProperty("rmb")
    private String rmb;

    @ExcelProperty("usd")
    private String usd;

    @ExcelProperty("thb")
    private String thb;

    @ExcelProperty("sgd")
    private String sgd;

    @ExcelProperty("idr")
    private String idr;

    @ExcelProperty("twd")
    private String twd;

    @ExcelProperty("myr")
    private String myr;

    @ExcelProperty("php")
    private String php;

    @ExcelProperty("vnd")
    private String vnd;

    @ExcelProperty("brl")
    private String brl;

    @ExcelProperty("krw")
    private String krw;
}
