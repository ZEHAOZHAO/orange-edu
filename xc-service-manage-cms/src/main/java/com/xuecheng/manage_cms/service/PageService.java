package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ${Author}: jason.zhao
 * 2019/3/3 17:51
 **/
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 页面列表分页查询
     *
     * @param page             当前页码
     * @param size             页面显示个数
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        ExampleMatcher matching = ExampleMatcher.matching();
        matching = matching.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageId())) {
            cmsPage.setPageId(queryPageRequest.getPageId());
        }

        if (page <= 0) {
            page = 1;
        }
        page = page - 1;//为了适应mongodb的接口将页码减1
        if (size <= 0) {
            size = 20;
        }
        //分页对象
        Pageable pageable = PageRequest.of(page, size);
        Example<CmsPage> of = Example.of(cmsPage, matching);
//分页查询
        Page<CmsPage> all = cmsPageRepository.findAll(of, pageable);
        QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<CmsPage>();
        cmsPageQueryResult.setList(all.getContent());
        cmsPageQueryResult.setTotal(all.getTotalElements());
//返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, cmsPageQueryResult);
    }

    public CmsPageResult add(CmsPage cmsPage) {
        CmsPage model = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (model == null) {
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
            return cmsPageResult;
        } else {
            return new CmsPageResult(CommonCode.FAIL, null);
        }

    }

    public CmsPage getById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage one = getById(id);
        if (one != null) {
            one.setTemplateId(cmsPage.getTemplateId());
//更新所属站点
            one.setSiteId(cmsPage.getSiteId());
//更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
//更新页面名称
            one.setPageName(cmsPage.getPageName());
//更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
//更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
//执行更新
            CmsPage save = cmsPageRepository.save(one);
            if (save != null) {
                CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, save);
                return cmsPageResult;
            }
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }
}
