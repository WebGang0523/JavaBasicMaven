package listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pojo.Product;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor //无参
public class ProductListener extends AnalysisEventListener<Product> {

    final List list = new ArrayList();

    //重写子类方法
    @Override
    public void invoke(Product product, AnalysisContext analysisContext) {
        list.add(product);
    }
    //重写子类方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //获取读取到的数据
        for (Object o : list) {
            Product product = (Product) o;
            System.out.println(product);
        }

        System.out.println(JSON.toJSONString(list,true));
    }
}
