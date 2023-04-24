package kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import kob.backend.pojo.Phone;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhoneMapper extends BaseMapper<Phone> {
}
