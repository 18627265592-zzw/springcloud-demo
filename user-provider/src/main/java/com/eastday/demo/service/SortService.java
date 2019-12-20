package com.eastday.demo.service;

import com.eastday.demo.dao.ISortDao;
import com.eastday.demo.user.RetDto;
import com.eastday.demo.user.Sort;
import com.eastday.demo.utils.CmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "sortService")
@Slf4j
public class SortService {

    @Autowired
    private ISortDao sortDao;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public RetDto addSort(List<Sort> list){
        RetDto retDto=new RetDto(true, 0, null);// 0：成功
        try {
            //判断父级栏目是否重复
            if(findSortBySort(list.get(0)) != null){
                retDto = new RetDto(false, 2, null);// 2：父栏目重复
            }else{
                list.get(0).setCreateTime(new Date());
                sortDao.insertSelective(list.get(0));
                int sortId=list.get(0).getSortId();
                log.debug("父类id----"+sortId);
                list.subList(0,1).clear();
                for(Sort li:list){
                    li.setCreateTime(new Date());
                    li.setParentId(sortId);
                }
                if(list.size()>0){
                    sortDao.insertList(list);
                }
            }
        }catch (Exception e){
            retDto = new RetDto(false, 1, null);// 1：操作失败
            e.printStackTrace();
        }
        return retDto;
    }

    public Sort findSortBySort(Sort sort){
        return sortDao.selectOne(sort);
    }

    public String getAllSort(){
        List<Sort> allSorts = sortDao.selectAll();
        /*List<Sort> sorts = new ArrayList<>();
        for(Sort sort:allSorts){
            if(sort.getParentId()==0 && sort.getLever()==1){  //1级栏目
                sorts.add(sort);
            }
        }
        for(Sort sort:sorts){
            sort.setChildSorts(getChildSort(sort.getSortId(),allSorts));
        }*/
        return CmsUtils.ObjectToString(allSorts);
    }

    public List<Sort> getChildSort(int sortId,List<Sort> allSorts){
        List<Sort> sorts = new ArrayList<>();
        for(Sort sort:allSorts){
            if(sort.getParentId() == sortId ){
                sorts.add(sort);
            }
        }
        if(sorts.size() == 0){
            return null;
        }
        return sorts;
    }

    public RetDto deleteBySortId(Integer sortId){
        try {
            Sort sort = sortDao.selectByPrimaryKey(sortId);
            if(sort != null){
                if(sort.getLever() == 1 && sort.getParentId() == 0){  //父级
                    sortDao.deleteByPrimaryKey(sortId);
                    Sort sort1 = new Sort();
                    sort1.setParentId(sortId);
                    sortDao.delete(sort1);
                }else {  //子级
                    sortDao.deleteByPrimaryKey(sortId);
                }
                return new RetDto(true, 0, null);// 0：成功
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：操作失败;
    }


    public RetDto addSortSon(List<Sort> list){
        try {
            if(list.size()==0){
                return new RetDto(false, 2, null);// 2：输入有误
            }else{
                int i = sortDao.insertList(list);
                if(i == list.size()){
                    return new RetDto(true, 0, null);// 0：成功
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new RetDto(false, 1, null);// 1：操作失败;
    }
}
