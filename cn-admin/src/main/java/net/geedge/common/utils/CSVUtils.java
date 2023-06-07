package net.geedge.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.log.Log;

public class CSVUtils {
	private static Log logger = Log.get();

    static final CsvReader reader = CsvUtil.getReader();

    public static CsvData getCsvDataFromMultipartFile(MultipartFile multipartFile) {
        File tempCsvFile = new File(Constant.TEMP_PATH + File.separator + "csv_" + UUID.randomUUID());
        CsvData data = null;
        try {
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), tempCsvFile);
            //data = reader.read(tempCsvFile);
            data = reader.read(ResourceUtil.getReader(tempCsvFile.getPath(), CharsetUtil.CHARSET_UTF_8));
        } catch (IOException e) {
            logger.error(e);
        } finally {
            if (tempCsvFile != null && tempCsvFile.exists()) tempCsvFile.delete();
        }
        return data;
    }

    public static boolean validateCSVHeader(CsvRow headers, List<String> headerInfos) {
    	// 先判断表头数量是否一致  再循环判断每个表头是否存在 表头位置不用一一对应 存在即可
    	if(headers.size() == headerInfos.size()) {
    		for (int i = 0; i < headerInfos.size(); i++) {
                String header = headers.get(i);
                if(header.indexOf("\uFEFF") >= 0) header = header.substring(1); //去除BOM头
                if (!headerInfos.contains(header)) {
                    return false;
                }
            }
    	}else {
    		return false;
    	}
        return true;
    }
}
