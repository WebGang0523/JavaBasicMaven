package utils;

import lombok.extern.slf4j.Slf4j;
import pojo.Product;
import com.alibaba.excel.EasyExcel;
import listener.ProductListener;

@Slf4j
public class Excel {
    private static void read2() {
        EasyExcel.read("C:\\Users\\xucg\\Desktop\\valid_price.xlsx", Product.class, new ProductListener()).doReadAll();
    }

    public static void main(String[] args) {
        read2();
    }
}
