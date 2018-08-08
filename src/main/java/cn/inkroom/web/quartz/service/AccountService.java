package cn.inkroom.web.quartz.service;

import cn.inkroom.web.quartz.bean.AccountBean;
import cn.inkroom.web.quartz.dao.AccountDao;
import cn.inkroom.web.quartz.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    /**
     * 登陆
     *
     * @param account   账号
     * @param password  密文
     * @param isEncrypt 密文是否已加密
     * @return 登陆成功返回true
     * @throws Exception sql异常
     */
    public AccountBean login(String account, String password, boolean isEncrypt) throws Exception {
        AccountBean accountBean = accountDao.login(account);
        if (isEncrypt) {
            return password.equals(accountBean.getPassword()) ? accountBean : null;
        }
        return (EncryptUtil.comparePass(password, accountBean.getSalt(), accountBean.getPassword())) ? accountBean : null;
    }

    public AccountBean getAccount(long id) throws Exception {
        return accountDao.getAccount(id);
    }

    /**
     * 注册
     *
     * @param nickName 昵称
     * @param userName 账号
     * @param password 密码，明文
     * @param role     权限
     * @return 注册成功返回实例，否则返回bean
     * @throws Exception sql
     */
    public AccountBean register(String nickName, String userName, String password, String role) throws Exception {

        AccountBean account = new AccountBean();
        account.setNickname(nickName);
        account.setUsername(userName);
        account.setRole(role);
        //密码加密
        String ens[] = EncryptUtil.parsePassToMd5(password);
        account.setPassword(ens[0]);
        account.setSalt(ens[1]);

        return accountDao.register(account) == 0 ? null : account;
    }

    public boolean checkAccount(String userName) throws Exception {
        return accountDao.checkAccount(userName) == 1;
    }
}
