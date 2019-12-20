package com.eastday.demo;

import com.eastday.demo.utils.CmsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
public class eastdayTest {


    @Test
    public void test2(){
        System.out.println(CmsUtils.generateShortUuid());
    }

}