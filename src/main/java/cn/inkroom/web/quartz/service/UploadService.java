package cn.inkroom.web.quartz.service;

import cn.inkroom.web.quartz.dao.AlbumDao;
import cn.inkroom.web.quartz.dao.UploadDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/9/1
 * @Time 20:11
 * @Descorption
 */
@Service
public class UploadService {
    Log logger = LogFactory.getLog(getClass());
    @Autowired
    private UploadDao uploadDao;
    @Autowired
    private AlbumDao albumDao;

    @Transactional
    public boolean insertFile(String path, long albumId, long ownerId) throws Exception {
        try {
            int count = albumDao.updateNumber(ownerId, albumId, 1);
            if (count < 1)
                throw new RuntimeException();
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            count = uploadDao.insertFile(path, albumId);
            if (count < 1) {
                throw new RuntimeException();
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return true;
//            return count < 1;
        } catch (Exception e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    public String getFile(long fileId) throws Exception {
        return uploadDao.getFile(fileId);
    }
}
