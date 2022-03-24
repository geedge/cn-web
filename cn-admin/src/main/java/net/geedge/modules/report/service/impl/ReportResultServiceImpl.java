package net.geedge.modules.report.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.report.dao.ReportResultDao;
import net.geedge.modules.report.entity.ReportResult;
import net.geedge.modules.report.service.ReportResultService;

@Service("reportResultService")
public class ReportResultServiceImpl extends ServiceImpl<ReportResultDao, ReportResult> implements ReportResultService {

}
