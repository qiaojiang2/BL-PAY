package cn.jishuyuan.pay.base.mappers;

import cn.jishuyuan.pay.base.model.PayAppKey;
import cn.jishuyuan.pay.base.model.PayAppKeyExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayAppKeyMapper {
    int countByExample(PayAppKeyExample example);

    int deleteByExample(PayAppKeyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PayAppKey record);

    int insertSelective(PayAppKey record);

    List<PayAppKey> selectByExample(PayAppKeyExample example);

    PayAppKey selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PayAppKey record, @Param("example") PayAppKeyExample example);

    int updateByExample(@Param("record") PayAppKey record, @Param("example") PayAppKeyExample example);

    int updateByPrimaryKeySelective(PayAppKey record);

    int updateByPrimaryKey(PayAppKey record);
}