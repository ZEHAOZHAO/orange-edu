package xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.manage_cms.ManageCmsApplication;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * ${Author}: jason.zhao
 * 2019/3/3 17:22
 **/
@SpringBootTest(classes=ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Test
    public void testFindPage() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //添加
    @Test
    public void testInsert(){
//定义实体类
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);
    }
    @Test
    public void testFindAllByExample(){
        int page=0;
        int size=10;
        Pageable pageable = PageRequest.of(page, size);
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("轮播");

        ExampleMatcher matching = ExampleMatcher.matching();
        matching =  matching.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, matching);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);


    }

    //删除
    @Test
    public void testDelete() {
        cmsPageRepository.deleteById("5b17a2c511fe5e0c409e5eb3");
    }


            //修改
  /*  @Test
    public void testUpdate() {
        Optional<CmsPage> optional = cmsPageRepository.findOne("5b17a34211fe5e2ee8c116c9");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面01");
            cmsPageRepository.save(cmsPage);
        }
    }*/
  @Test
  public   void  testStringUtil(){
      String s ="WangWa";
      System.out.println(StringUtils.upperCase(s));
      System.out.println(StringUtils.reverse(s));

      System.out.println(StringUtils.replaceFirst(s,StringUtils.substring(s,0,1),StringUtils.substring(s,0,1).toLowerCase()));;
  }
}