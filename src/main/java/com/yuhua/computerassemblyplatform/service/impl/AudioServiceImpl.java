package com.yuhua.computerassemblyplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuhua.computerassemblyplatform.entity.Audio;
import com.yuhua.computerassemblyplatform.mapper.AudioMapper;
import com.yuhua.computerassemblyplatform.service.IAudioService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YuHua
 * @since 2024-01-04
 */
@Service
public class AudioServiceImpl extends ServiceImpl<AudioMapper, Audio> implements IAudioService {

    @Override
    public String findLast() {
        QueryWrapper<Audio> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 1");
        return getOne(queryWrapper).getAudioImg();
    }
}
