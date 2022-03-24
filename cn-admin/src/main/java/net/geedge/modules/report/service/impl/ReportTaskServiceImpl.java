package net.geedge.modules.report.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.report.dao.ReportTaskDao;
import net.geedge.modules.report.entity.ReportTask;
import net.geedge.modules.report.service.ReportTaskService;

@Service("reportTaskService")
public class ReportTaskServiceImpl extends ServiceImpl<ReportTaskDao, ReportTask> implements ReportTaskService {

}
