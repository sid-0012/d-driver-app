package com.example.hxds.nebula.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.nebula.db.dao.OrderMonitoringDao;
import com.example.hxds.nebula.db.dao.OrderVoiceTextDao;
import com.example.hxds.nebula.db.pojo.OrderVoiceTextEntity;
import com.example.hxds.nebula.service.MonitoringService;
import com.example.hxds.nebula.task.VoiceTextCheckTask;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
@Slf4j
public class MonitoringServiceImpl implements MonitoringService {
    @Resource
    private OrderVoiceTextDao orderVoiceTextDao;

    @Resource
    private OrderMonitoringDao orderMonitoringDao;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Resource
    private VoiceTextCheckTask voiceTextCheckTask;

    @Override
    @Transactional
    public void monitoring(MultipartFile file, String name, String text) {
        try{
            MinioClient client=new MinioClient.Builder().endpoint(endpoint)
                    .credentials(accessKey,secretKey).build();
            client.putObject(PutObjectArgs.builder().bucket("hxds-record")
                    .object(name).stream(file.getInputStream(),-1,20971520)
                    .contentType("audio/x-mpeg").build());
        }catch (Exception e){
            log.error("上传代驾录音文件失败", e);
            throw new HxdsException("上传代驾录音文件失败");
        }

        OrderVoiceTextEntity entity=new OrderVoiceTextEntity();
        String[] temp = name.substring(0, name.indexOf(".mp3")).split("-");
        Long orderId=Long.parseLong(temp[0]);
        String uuid = IdUtil.simpleUUID();
        entity.setOrderId(orderId);
        entity.setUuid(uuid);
        entity.setRecordFile(name);
        entity.setText(text);
        int rows = orderVoiceTextDao.insert(entity);
        if(rows!=1){
            throw new HxdsException("保存录音文稿失败");
        }
        //执行文稿内容审查
        voiceTextCheckTask.checkText(orderId,text,uuid);

    }

    @Override
    @Transactional
    public int insertOrderMonitoring(long orderId) {
        int rows = orderMonitoringDao.insert(orderId);
        if(rows!=1){
            throw new HxdsException("添加订单监控摘要记录失败");
        }
        return rows;
    }


}
