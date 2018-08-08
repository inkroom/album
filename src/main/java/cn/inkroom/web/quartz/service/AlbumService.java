package cn.inkroom.web.quartz.service;

import cn.inkroom.web.quartz.bean.AlbumBean;
import cn.inkroom.web.quartz.bean.ImageBean;
import cn.inkroom.web.quartz.bean.QuestionBean;
import cn.inkroom.web.quartz.bean.form.AlbumForm;
import cn.inkroom.web.quartz.dao.AlbumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.util.List;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/16
 * @Time 10:38
 * @Descorption 相册服务类
 */
@Service
@Transactional(readOnly = true)
public class AlbumService {
    @Autowired
    private AlbumDao albumDao;

    public List<AlbumBean> getAlbums(long ownerId) throws Exception {
        return albumDao.getAlbums(ownerId);
    }

    public List<ImageBean> getImages(long albumId) throws Exception {
        return albumDao.getImages(albumId);
    }

    public AlbumBean getOneAlbum(long ownerId, long albumId) throws Exception {
        return albumDao.getOneAlbum(ownerId, albumId);
    }

    @Transactional
    public boolean removeImage(AlbumBean album, long imageId, String path) throws Exception {
        long size = 0;
        if (path != null)
            size = new File(path).length();
        try {
            int count = albumDao.updateSize(album.getOwner(), album.getId(), size);
            if (count == 2) {
                count = albumDao.updateNumber(album.getOwner(), album.getId(), -1);
                if (count != 1) {
                    throw new RuntimeException();
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return false;
                }
                count = albumDao.deleteRes(imageId);
                if (count != 1) {
                    throw new RuntimeException();
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
                return count == 1;
            } else {
                throw new RuntimeException();
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return false;
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Transactional
    public boolean removeAlbum(long ownerId, long albumId, long size) throws Exception {
        try {
            long count = albumDao.updateSize(ownerId, albumId, size);
            if (count < 1) {
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
            count = albumDao.deleteAlbum(ownerId, albumId);
            if (count < 1)
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return count >= 1;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Transactional
    public boolean setCover(long albumId, long cover) throws Exception {
        try {
            return albumDao.updateCover(albumId, cover) == 1;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        }
    }

    @Transactional
    public AlbumBean createAlbum(AlbumForm form) throws Exception {
        AlbumBean album = new AlbumBean(form);
        if (album.getAuthority() == 2) {
            QuestionBean question = new QuestionBean();
            question.setAnswer(form.getAnswer());
            question.setQuestion(form.getQuestion());
            int count = albumDao.insertQuestion(question);
            if (count < 1) {
                return null;
            } else {
                album.setAuthority(question.getId());
            }
        }
        long count = albumDao.insertAlbum(album);
        if (count != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }
        return album;
    }

    public AlbumBean changeAlbum(AlbumForm form) throws Exception {
        AlbumBean album = new AlbumBean(form);
        if (album.getAuthority() == 2) {
            QuestionBean question = new QuestionBean();
            question.setAnswer(form.getAnswer());
            question.setQuestion(form.getQuestion());
            question.setOwner(form.getId());
            int count = albumDao.insertQuestion(question);
            if (count < 1) {
                return null;
            } else
                album.setAuthority(question.getId());
        }
        long count = albumDao.updateAlbum(album);
        if (count != 1) {
            try {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } catch (NoTransactionException e) {

            }
            return null;
        }
        return album;
    }


    public QuestionBean getQuestion(long questionId) throws Exception {
        return albumDao.getQuestion(questionId);
    }
}
